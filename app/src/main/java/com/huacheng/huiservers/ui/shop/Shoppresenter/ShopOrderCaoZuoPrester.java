package com.huacheng.huiservers.ui.shop.Shoppresenter;

import android.content.Context;

import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：商城订单操作页
 * 时间：2020/1/12 11:21
 * created by DFF
 */
public class ShopOrderCaoZuoPrester {

    Context mContext;
    ShopOrderListener listener;

    public ShopOrderCaoZuoPrester(Context mContext, ShopOrderListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public interface ShopOrderListener {
        void onGetCaoZuoInfo(int status, String msg, List<BannerBean> data, String type);
        void onGetShouHuo(int status, String msg,String id);
    }

    //购物订单操作页面(退款/收货/评价) 接口
    public void getCaoZuoShopInfo(HashMap<String, String> params, final String type) {
        // HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().post(ApiHttpClient.GET_SHOP_ORDER_OPERTE, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<BannerBean> data = JsonUtil.getInstance().getDataArrayByName(response, "data", BannerBean.class);
                    if (listener != null) {
                        listener.onGetCaoZuoInfo(1, "请求成功", data, type);
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "获取失败");
                    if (listener != null) {
                        listener.onGetCaoZuoInfo(0, msg, null, "");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener != null) {
                    listener.onGetCaoZuoInfo(-1, "网络异常,请检查网络设置", null, "");
                }
            }
        });

    }

    /**
     * 确认收货
     */
    public void getShouHuo(final String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        MyOkHttp.get().post(Url_info.shop_order_accept, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    if (listener != null) {
                        listener.onGetShouHuo(1, "请求成功",id);
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "获取失败");
                    if (listener != null) {
                        listener.onGetShouHuo(0, msg,"");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener != null) {
                    listener.onGetShouHuo(-1, "网络异常,请检查网络设置","");
                }
            }
        });
    }
}
