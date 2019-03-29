package com.huacheng.huiservers.http.okhttp;

import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelConfig;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Description: 获取域名
 * created by wangxiaotao
 * 2019/3/13 0013 上午 10:32
 */
public class ConfigUtils {

    private static ConfigUtils instance;
    private  ConfigUtils(){};

    /**
     * 获取实例
     *
     * @return
     */
    public static ConfigUtils get() {
        if (instance == null) {
            instance = new ConfigUtils();
        }

        return instance;
    }

    /**
     * 获取域名
     * @param community_id
     * @param listener
     */
    public void getApiConfig(String community_id, final OnGetConfigListener listener){
        HashMap<String, String> params = new HashMap<>();
        params.put("community_id", community_id);
        MyOkHttp.get().post(ApiHttpClient.GET_CONFIG, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){
                    ModelConfig config = (ModelConfig) JsonUtil.getInstance().parseJsonFromResponse(response, ModelConfig.class);
                    if (listener!=null){
                        listener.onGetConfig(1,config);
                    }
                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"");
                    if (listener!=null){
                        listener.onGetConfig(0, null);
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (listener!=null){
                    listener.onGetConfig(-1, null);
                }
            }
        });
    }

    public interface OnGetConfigListener{
        void onGetConfig(int status, ModelConfig modelConfig);
    }
}
