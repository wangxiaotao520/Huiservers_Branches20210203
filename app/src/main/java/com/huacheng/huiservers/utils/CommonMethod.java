package com.huacheng.huiservers.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.dialog.SmallDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.model.protocol.CommonProtocol;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.shop.bean.ShopDetailBean;
import com.huacheng.libraryservice.utils.NullUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/5/31.
 */

public class CommonMethod {

    //public static XGBean mXgBean;

    public static ModelShopIndex mShopIndexBean;

    public static Context mContext;

    public SharePrefrenceUtil prefrenceUtil;
    SmallDialog smallDialog ;

    TextView tv;

    public CommonMethod(ModelShopIndex shopIndexBean, Context context) {
        this.mShopIndexBean = shopIndexBean;
        this.mContext = context;
        smallDialog=new SmallDialog(mContext);
    }

    public CommonMethod(ModelShopIndex shopIndexBean, TextView tv, Context context) {
        this.mShopIndexBean = shopIndexBean;
        this.mContext = context;
        prefrenceUtil = new SharePrefrenceUtil(mContext);
        this.tv = tv;
        smallDialog=new SmallDialog(mContext);
    }

    public CommonMethod(TextView tv, Context context) {
        this.mContext = context;
        prefrenceUtil = new SharePrefrenceUtil(mContext);
        this.tv = tv;
        smallDialog=new SmallDialog(mContext);
    }

    public void getShopLimitTag() {   //获取商品标签
        //获取限购商品数量
        if (smallDialog!=null){
            smallDialog.show();
        }
        Url_info info = new Url_info(mContext);
        RequestParams params = new RequestParams();
        params.addBodyParameter("p_id", mShopIndexBean.getId());
        params.addBodyParameter("tagid", mShopIndexBean.getTagid());
        params.addBodyParameter("num","1");
        new HttpHelper(info.shop_limit, params, mContext) {

            @Override
            protected void setData(String json) {
                try {
                    JSONObject obj = new JSONObject(json);
                    String status = obj.getString("status");
//                    WaitDialog.closeDialog(WaitDialog);
                    if (status.equals("1")) {
                     //   mXgBean = new ShopProtocol().getAGNum(json);
                        if (TextUtils.isEmpty(mShopIndexBean.getInventory()) || 0 >= Integer.valueOf(mShopIndexBean.getInventory()) ) {
                            if (smallDialog!=null){
                                smallDialog.dismiss();
                            }
                            SmartToast.showInfo("商品已售罄");
                        } else {

                            getAddShop(mShopIndexBean);

                        }
                    }else {
                        if (smallDialog!=null){
                            smallDialog.dismiss();
                        }
                        String msg = obj.getString("msg");
                        SmartToast.showInfo(msg+"");
                    }
                } catch (JSONException e) {
                    if (smallDialog!=null){
                        smallDialog.dismiss();
                    }
                    e.printStackTrace();
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                if (smallDialog!=null){
                    smallDialog.dismiss();
                }
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };

    }

    private void getAddShop(ModelShopIndex detail) {//加入购物车
        Url_info info = new Url_info(mContext);
        RequestParams params = new RequestParams();
        params.addBodyParameter("p_id", detail.getId());
        params.addBodyParameter("p_title", detail.getTitle());
        params.addBodyParameter("p_title_img", detail.getTitle_img());
        params.addBodyParameter("price", detail.getPrice());
        params.addBodyParameter("number", "1");

        params.addBodyParameter("tagid", detail.getTagid());
        params.addBodyParameter("tagname", detail.getTagname());
        new HttpHelper(info.add_shop_cart, params, mContext) {

            @Override
            protected void setData(String json) {
                if (smallDialog!=null){
                    smallDialog.dismiss();
                }
                str = new CommonProtocol().getResult(json);
                if (str.equals("1")) {
                    SmartToast.showInfo("加入购物车成功");
                    if (tv != null) {
                        getCartNum();
                    }
                } else {
                    SmartToast.showInfo(str);

                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                if (smallDialog!=null){
                    smallDialog.dismiss();
                }
                SmartToast.showInfo("网络异常，请检查网络设置");

            }
        };
    }

    public void getCartNum() {// 购物车商品数量
        Url_info info = new Url_info(mContext);

        RequestParams params = new RequestParams();
//        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){
//            params.addBodyParameter("c_id", prefrenceUtil.getXiaoQuId());
//        }
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())){
            params.addBodyParameter("province_cn", prefrenceUtil.getProvince_cn());
            params.addBodyParameter("city_cn", prefrenceUtil.getCity_cn());
            params.addBodyParameter("region_cn", prefrenceUtil.getRegion_cn());
        }
        MyOkHttp.get().post(info.cart_num, params.getParams(), new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                ShopDetailBean cartnum = new ShopProtocol().getCartNum(response);
                if (cartnum != null) {
                    if ("0".equals(cartnum.getCart_num())) {
                        tv.setVisibility(View.GONE);
                    } else {
                        tv.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }
}
