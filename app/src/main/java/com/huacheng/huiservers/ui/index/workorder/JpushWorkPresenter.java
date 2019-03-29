package com.huacheng.huiservers.ui.index.workorder;

import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Description: 工单推送
 * Author: badge
 * Create: 2018/12/25 12:07
 */
public class JpushWorkPresenter {

    /**
     * 用户提交/取消工单推送接口
     * @param params
     */
    public void userToWorkerSubmitJpush(HashMap<String, String> params) {
        MyOkHttp.get().post(ApiHttpClient.USERTOWORKERSUBMIT, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {

                }else{

                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
}
