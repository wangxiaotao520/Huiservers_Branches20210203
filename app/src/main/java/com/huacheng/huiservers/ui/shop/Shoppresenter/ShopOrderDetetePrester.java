package com.huacheng.huiservers.ui.shop.Shoppresenter;

import android.content.Context;

import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：商城订单删除
 * 时间：2020/1/12 11:21
 * created by DFF
 */
public class ShopOrderDetetePrester {

    Context mContext;
    ShopOrderListener listener;

    public ShopOrderDetetePrester(Context mContext, ShopOrderListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public interface ShopOrderListener {
        void onOrderDelete(int status, String id, String msg);
    }

    public void getOrderDetete(final String order_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", order_id);
        //订单删除按钮
        MyOkHttp.get().post(ApiHttpClient.GET_SHOP_ORDER_DEL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    //  ModelLogin bean = (ModelLogin) JsonUtil.getInstance().parseJsonFromResponse(response, ModelLogin.class);
                    if (listener != null) {
                        listener.onOrderDelete(1, order_id, "请求成功");
                    }

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    if (listener != null) {
                        listener.onOrderDelete(0, order_id, msg);
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener != null) {
                    listener.onOrderDelete(-1, order_id, "网络异常,请检查网络设置");
                }
            }
        });
    }
}
