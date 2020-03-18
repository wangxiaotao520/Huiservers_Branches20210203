package com.huacheng.huiservers.ui.webview.common;

import android.webkit.URLUtil;
import android.webkit.WebView;

import com.huacheng.huiservers.http.okhttp.ApiHttpClient;

/**
 * @function 路由截断, 线程安全的惰性单例模式
 * Created by wangxiaotao
 */

public class Router {
    private Router() {
    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("WebView is null!");
        }
    }

    //在assets文件夹中的本地页面（和res文件夹同级）
    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public final void loadPage(WebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }

    /**
     * 加载网页标签
     * @param webView
     * @param content
     */
    public void loadDataWithBaseURL(WebView webView, String content) {
        if (webView != null) {
            webView.loadDataWithBaseURL(ApiHttpClient.API_URL,content,"text/html", "utf-8", null);
        } else {
            throw new NullPointerException("WebView is null!");
        }
    }
}