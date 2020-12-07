package com.huacheng.huiservers.http.okhttp;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Created by changyadong on 2020/12/4
 * @description
 */
public abstract class GsonCallback<T> extends StringCallback {


    @Override
    public void onSuccess(String resp) {
        Gson gson = new Gson();

        ParameterizedType p = (ParameterizedType) getClass().getGenericSuperclass();
        Type type = p.getActualTypeArguments()[0];
        T result = gson.fromJson(resp, type);
        onSuccess(result);
    }

    public abstract void onSuccess(T t);
}
