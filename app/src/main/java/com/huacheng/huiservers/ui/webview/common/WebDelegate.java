package com.huacheng.huiservers.ui.webview.common;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.utils.NightModeUtils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @function 网络服务的抽象基类
 * Created by wangxiaotao
 */

//这里继承的BaseDelegate基类理解为封装过的Fragment，
public abstract class WebDelegate extends BaseDelegate {

    private WebView mWebView = null;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String mUrl = null;
    private boolean mIsWebViewAvailable = false;

    public WebDelegate() {
    }

    public abstract IWebViewInitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
       mUrl = args.getString("web_url");
        initWebView();
    }

    //初始化webview
    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        if(mWebView !=null){
            mWebView.removeAllViews();
            mWebView.destroy();
        }else {
            final IWebViewInitializer initializer = setInitializer();
            if(initializer !=null){
                final WeakReference<WebView> webViewWeakReference =
                        new WeakReference<WebView>(new WebView(getContext()),WEB_VIEW_QUEUE);
                mWebView = webViewWeakReference.get();
                //TODO 深色模式的话
                if (NightModeUtils.getThemeMode()== NightModeUtils.ThemeMode.NIGHT){
                    mWebView.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
                }
                mWebView = initializer.initWebViewSettings(mWebView);
                mWebView.setWebViewClient(initializer.initWebViewClient());
                mWebView.setWebChromeClient(initializer.initWebChromeClient());
                mWebView.addJavascriptInterface(JSWebInterface.create(this),"jsWebInterface");
                //webview可用了
                mIsWebViewAvailable = true;
            }else {
                throw new NullPointerException("Initializer is null!");
            }
        }
    }
    
    public WebView getWebView() {
        if (mWebView == null) {
            throw new NullPointerException("WebView IS NULL!");
        }
        return mIsWebViewAvailable ? mWebView : null;
    }

    public String getUrl() {
        if (mUrl == null) {
            throw new NullPointerException("WebView IS NULL!");
        }
        return mUrl;
    }
    
    @Override
    public void onPause() {
        super.onPause();
        if(mWebView !=null){
            mWebView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
