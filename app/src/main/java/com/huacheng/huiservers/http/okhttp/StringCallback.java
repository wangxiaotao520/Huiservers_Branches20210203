package com.huacheng.huiservers.http.okhttp;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author Created by changyadong on 2020/12/4
 * @description
 */
public abstract class StringCallback implements Callback {


    public abstract void onFailure(int code);
    public abstract void onSuccess(String resp);

    Handler handler ;

    public StringCallback() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, IOException e) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(1);
            }
        });


    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {


        if (response.isSuccessful()) {
            final String result = response.body().string();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(result);
                }
            });

        } else onFailure(response.code());
    }
}
