package com.huacheng.huiservers.ui.fragment.presenter;

import android.content.Context;

import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelLogin;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

/**
 * 类描述：判断是否绑定物业
 * 时间：2018/12/27 15:03
 * created by DFF
 */
public class PropertyPrester {

    Context mContext;
    PropertyListener listener;

    public PropertyPrester(Context mContext, PropertyListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public interface PropertyListener {
        void onProperty(int status, ModelLogin bean, String jump_type, String msg);
    }

    public void getProperty(final String jump_type) {
        //判断是否物业绑定
        MyOkHttp.get().post(ApiHttpClient.CHECK_BIND_PROPERTY, null, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelLogin bean = (ModelLogin) JsonUtil.getInstance().parseJsonFromResponse(response, ModelLogin.class);
                    if (listener != null) {
                        listener.onProperty(1, bean,jump_type, "请求成功");
                    }

                } else {
                    if (listener != null) {
                        listener.onProperty(0, null, null,"数据异常");
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener != null) {
                    listener.onProperty(-1, null,null, "网络异常,请检查网络设置");
                }
            }
        });
    }
}
