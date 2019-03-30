package com.huacheng.huiservers.http.okhttp;

import java.util.HashMap;

/**
 * Description: 给HttpHelper 用来的
 * created by wangxiaotao
 * 2019/3/30 0030 上午 9:38
 */
public class RequestParams {

    private  HashMap<String, String> params = new HashMap<>();


    public void addBodyParameter(String name, String value) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }

        this.params.put(name, value);
    }
    public HashMap<String, String> getParams() {
        return params;
    }

}
