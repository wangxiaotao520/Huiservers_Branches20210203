package com.huacheng.huiservers.utils.update;

import android.content.Context;

import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.center.bean.PayInfoBean;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：更新接口
 * 时间：2018/12/25 15:24
 * created by DFF
 */
public class Updateprester {

    Context mContext;
    UpdateListener listener;

    public Updateprester(Context mContext, UpdateListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public interface UpdateListener {
        void onUpdate(int status, PayInfoBean info, String msg);
    }

    public void getUpdate(HashMap<String, String> params) {
        MyOkHttp.get().post(ApiHttpClient.APP_UPDATE, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {

                if (JsonUtil.getInstance().isSuccess(response)) {
                    PayInfoBean info = (PayInfoBean) JsonUtil.getInstance().parseJsonFromResponse(response, PayInfoBean.class);
                    if (listener != null) {
                        listener.onUpdate(1, info, "请求成功");
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"数据异常");
                    if (listener != null) {
                        listener.onUpdate(0, null, msg);
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener != null) {
                    listener.onUpdate(-1, null, "网络异常，请检查网络错误");
                }
            }
        });

    }

}
