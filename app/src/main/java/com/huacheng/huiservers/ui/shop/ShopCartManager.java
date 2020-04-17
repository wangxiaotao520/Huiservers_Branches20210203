package com.huacheng.huiservers.ui.shop;

import android.content.Context;
import android.text.TextUtils;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.model.ModelEventShopCart;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.model.protocol.CommonProtocol;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.shop.bean.ShopDetailBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.NullUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description: 加入购物车工具类
 * created by wangxiaotao
 * 2020/4/17 0017 上午 8:48
 */
public class ShopCartManager {

    private static ShopCartManager instance;

    private ShopCartManager() {
    }

    public synchronized static ShopCartManager getInstance() {
        if (instance == null) {
            instance = new ShopCartManager();
        }
        return instance;
    }

    /**
     * 获取限购数量
     * @param mContext
     * @param mShopIndexBean
     * @param listener
     */
    public void getShopLimitTag(final Context mContext, final ModelShopIndex mShopIndexBean, final OnAddShopCartResultListener listener) {   //获取商品标签
        RequestParams params = new RequestParams();
        params.addBodyParameter("p_id", mShopIndexBean.getId());
        params.addBodyParameter("tagid", mShopIndexBean.getTagid());
        params.addBodyParameter("num","1");
        new HttpHelper(Url_info.shop_limit, params, mContext) {

            @Override
            protected void setData(String json) {
                try {
                    JSONObject obj = new JSONObject(json);
                    String status = obj.getString("status");
//                    WaitDialog.closeDialog(WaitDialog);
                    if (status.equals("1")) {
                        //   mXgBean = new ShopProtocol().getAGNum(json);
                        if (TextUtils.isEmpty(mShopIndexBean.getInventory()) || 0 >= Integer.valueOf(mShopIndexBean.getInventory()) ) {

                            if (listener!=null){
                                listener.onAddShopCart(1,"商品已售罄");
                            }
                        } else {
                            getAddShop(mShopIndexBean,mContext,listener);
                        }
                    }else {
                        String msg = obj.getString("msg");
                      //  SmartToast.showInfo(msg+"");
                        if (listener!=null){
                            listener.onAddShopCart(0,msg);
                        }
                    }
                } catch (JSONException e) {
                    if (listener!=null){
                        listener.onAddShopCart(0,"加入购物车失败");
                    }
                    e.printStackTrace();
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (listener!=null){
                    listener.onAddShopCart(-1,"网络异常，请检查网络设置");
                }
            }
        };

    }

    /**
     * 加入购物车
     * @param detail
     * @param mContext
     */
    private void getAddShop(ModelShopIndex detail,Context mContext,final OnAddShopCartResultListener listener) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("p_id", detail.getId());
        params.addBodyParameter("p_title", detail.getTitle());
        params.addBodyParameter("p_title_img", detail.getTitle_img());
        params.addBodyParameter("price", detail.getPrice());
        params.addBodyParameter("number", "1");

        params.addBodyParameter("tagid", detail.getTagid());
        params.addBodyParameter("tagname", detail.getTagname());
        new HttpHelper(Url_info.add_shop_cart, params, mContext) {

            @Override
            protected void setData(String json) {
                str = new CommonProtocol().getResult(json);
                if (str.equals("1")) {
                    if (listener!=null){
                        listener.onAddShopCart(1,"加入购物车成功");
                    }
                    //加入购物车后，发送eventbus
                    EventBus.getDefault().post(new ModelEventShopCart());
                } else {
                    if (listener!=null){
                        listener.onAddShopCart(0,str);
                    }

                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                if (listener!=null){
                    listener.onAddShopCart(-1,"网络异常，请检查网络设置");
                }

            }
        };
    }


    public interface OnAddShopCartResultListener{
        /**
         * 1.表示成功 其他表示失败
         * @param status
         */
        void onAddShopCart(int status ,String msg);


    }

    /**
     * 获取购物车商品数量
     */
    public interface OnGetShopCartNumListener{
        /**
         * 1.表示成功 其他表示失败
         * @param status
         */
        void onGetShopCartNum(int status ,String msg,String num);

    }
    /**
     *  购物车商品数量
     * @param mContext
     */
    public void getCartNum(Context mContext, final OnGetShopCartNumListener listener) {

        RequestParams params = new RequestParams();
//        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){
//            params.addBodyParameter("c_id", prefrenceUtil.getXiaoQuId());
//        }
        if (!NullUtil.isStringEmpty(new SharePrefrenceUtil(mContext).getProvince_cn())){
            params.addBodyParameter("province_cn", new SharePrefrenceUtil(mContext).getProvince_cn());
            params.addBodyParameter("city_cn", new SharePrefrenceUtil(mContext).getCity_cn());
            params.addBodyParameter("region_cn", new SharePrefrenceUtil(mContext).getRegion_cn());
        }
        MyOkHttp.get().post(Url_info.cart_num, params.getParams(), new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                ShopDetailBean cartnum = new ShopProtocol().getCartNum(response);
                if (cartnum != null) {
//                    if ("0".equals(cartnum.getCart_num())) {
//
//                    } else {
//
//                    }

                    if (listener!=null){
                        listener.onGetShopCartNum(1,"获取购物车数量成功",cartnum.getCart_num());
                    }
                }else {
                    if (listener!=null){
                        listener.onGetShopCartNum(0,"获取购物车数量失败","0");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

                if (listener!=null){
                    listener.onGetShopCartNum(-1,"网络异常，请检查网络设置","0");
                }
            }
        });
    }
}




