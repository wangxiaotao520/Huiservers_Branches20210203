package com.huacheng.huiservers.view.X5WebView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * Description:  封装的X5webview
 * created by wangxiaotao
 * 2020/5/26 0026 15:01
 */
public class MyX5WebView extends WebView {


    public MyX5WebView(Context context) {
        super(context);
        initWebSetting();
    }

    public MyX5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initWebSetting();
    }

    public MyX5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initWebSetting();
    }


    private void initWebSetting() {
        this.getView().setClickable(true);
        WebSettings webSetting = getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setDomStorageEnabled(true);//开启本地DOM存储
        webSetting.setTextZoom(100);
        //解决webview加载图片不显示的问题
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(0);//参考取用 原生WebView的 WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webSetting.setSupportZoom(true);
        webSetting.setPluginState(WebSettings.PluginState.ON);// 支持所有版本  参考原生WebView设置参数
        //android 默认是可以打开_bank的，是因为它默认设置了WebSettings.setSupportMultipleWindows(false)
        //在false状态下，_bank也会在当前页面打开……
        //而x5浏览器，默认开启了WebSettings.setSupportMultipleWindows(true)，
        // 所以打不开……主动设置成false就可以打开了
        //需要支持多窗体还需要重写WebChromeClient.onCreateWindow
        webSetting.setSupportMultipleWindows(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });
    }

}
