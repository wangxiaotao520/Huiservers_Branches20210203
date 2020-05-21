package com.huacheng.huiservers.ui.webview.common;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @function 对传入的webView进行各种settings，返回setting好的webView
 * Created by wangxiaotao
 */

public class WebViewSettingsInitializer {

    @SuppressLint("SetJavaScriptEnabled")
    public WebView createWebView(final WebView webView) {
      //api>=21时才能开启
//        WebView.setWebContentsDebuggingEnabled(true);
        //不能横向滚动
        webView.setHorizontalScrollBarEnabled(false);
        //不能纵向滚动
        webView.setVerticalScrollBarEnabled(false);
        //允许截图
        webView.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //初始化WebSettings
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        final String ua = settings.getUserAgentString();
        //FIXME 后面的名字不知道是啥
        settings.setUserAgentString(ua + "Latte");

        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(false); //将图片调整到适合webview的大小
      //  settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
       // settings.setTextZoom(100);

        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        //禁止缩放
        settings.setSupportZoom(false);
        //文件权限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        //缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

      //  settings.setTextSize(WebSettings.TextSize.NORMAL);

        //todo 还需要其他setting在这里设置

        return webView;
    }
}