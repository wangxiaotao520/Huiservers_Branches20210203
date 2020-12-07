package com.huacheng.huiservers.ui.center.presenter;

import android.content.Context;

import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：删除收藏
 * 时间：2020/12/7 15:24
 * created by DFF
 */
public class CollectDeletePresenter {
    Context mContext;
    CollectListener listener;

    public CollectDeletePresenter(Context mContext, CollectListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public interface CollectListener {
        void onDeleteCollect(int status, String collect_id,String msg);
    }

    public void getDeleteCollect(final String id, final String type) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("type", type);
        MyOkHttp.get().post(ApiHttpClient.MY_COLLECT_DELETE, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    if (listener != null) {
                        listener.onDeleteCollect(1,id, "请求成功");
                    }

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    if (listener != null) {
                        listener.onDeleteCollect(0,id, msg);
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener != null) {
                    listener.onDeleteCollect(-1,id, "网络异常,请检查网络设置");
                }
            }
        });
    }

}
