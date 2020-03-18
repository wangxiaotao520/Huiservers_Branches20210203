package com.huacheng.huiservers.ui.webview.defaul;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huacheng.huiservers.ui.webview.common.IPageLoadListener;
import com.huacheng.huiservers.ui.webview.common.WebDelegate;

/**
 * @function 默认的 WebViewClient实体类,页面内跳转，带loading
 * Created by wangxiaotao
 */

public class WebViewClientDefault extends WebViewClient {
    protected final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;


    public void setIPageLoadListener(IPageLoadListener mIPageLoadListener) {
        this.mIPageLoadListener = mIPageLoadListener;
    }

    public WebViewClientDefault(WebDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    //表示重定向和url跳转，由这个webView自己来处理，不会打开外部浏览器
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
   //     view.loadUrl(url);
        // 这块如果有特殊业务需求就自己做处理,因为这里存在重定向的问题
        return super.shouldOverrideUrlLoading(view,url);
    }

    //页面开始加载时回调（页面后退也会回调）
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(mIPageLoadListener!=null){
            mIPageLoadListener.onLoadStart();
        }
        // 有其他业务需要可以在这里处理

    }

    //页面完成加载时回调（返回页面也会回调）
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(mIPageLoadListener != null){
            mIPageLoadListener.onLoadEnd();
        }
        //有其他业务需要可以在这里处理


    }
}