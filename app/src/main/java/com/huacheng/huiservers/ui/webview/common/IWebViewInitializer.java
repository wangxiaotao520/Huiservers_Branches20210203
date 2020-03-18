package com.huacheng.huiservers.ui.webview.common;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * IWebViewInitializer回调接口
 * Created by wangxiaotao
 *
 */
public interface IWebViewInitializer {

    WebView initWebViewSettings(WebView webView);

    //针对浏览器本身行为的控制，如前进后退的回调
    WebViewClient initWebViewClient();

    //针对页面的控制,如js交互
    WebChromeClient initWebChromeClient();
}