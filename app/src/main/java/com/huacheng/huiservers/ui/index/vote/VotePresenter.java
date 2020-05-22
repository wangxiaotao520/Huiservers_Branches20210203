package com.huacheng.huiservers.ui.index.vote;

import android.content.Context;

import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：活动投票
 * 时间：2019/9/7 16:24
 * created by DFF
 */
public class VotePresenter {

    Context mContext;

    OnGetDataListener listener;

    public VotePresenter(Context mContext, OnGetDataListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public interface OnGetDataListener {
        void onGetData(int status,String family_id ,String msg);
    }

    /**
     * 投票
     */
    public void getTouPiao(final String family_id) {

        HashMap<String, String> params = new HashMap<>();
        params.put("family_id", family_id);
        MyOkHttp.get().post(ApiHttpClient.FAMILY_POLL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "成功");
                    if (listener != null) {
                        listener.onGetData(1,family_id, msg);
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "数据获取失败");
                    if (listener != null) {
                        listener.onGetData(0,null, msg);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener != null) {
                    listener.onGetData(-1, null,"网络错误,请检查网络设置");
                }
            }
        });

    }

    /**
     * 通用活动投票
     * @param id  活动id
     * @param vote_member_id 人员id
     */
    public void getTouPiaoVlog(final String id,final String vote_member_id) {

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("vote_member_id", vote_member_id);
        MyOkHttp.get().post(ApiHttpClient.VLOG_POLL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "成功");
                    if (listener != null) {
                        listener.onGetData(1,vote_member_id, msg);
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "数据获取失败");
                    if (listener != null) {
                        listener.onGetData(0,null, msg);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener != null) {
                    listener.onGetData(-1, null,"网络错误,请检查网络设置");
                }
            }
        });

    }
}
