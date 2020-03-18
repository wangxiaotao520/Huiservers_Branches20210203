package com.huacheng.huiservers.ui.webview.defaul;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huacheng.huiservers.ui.webview.common.IPageLoadListener;
import com.huacheng.huiservers.ui.webview.common.IWebViewInitializer;
import com.huacheng.huiservers.ui.webview.common.Router;
import com.huacheng.huiservers.ui.webview.common.WebDelegate;
import com.huacheng.huiservers.ui.webview.common.WebViewSettingsInitializer;

/**
 * @function WebDelegate的默认子类，点击链接会在webView内部跳转
 * 使用这种写法相当于 WebDelegate相当于WebView的代理  ,WebView相当于被代理的对象  IWebViewInitializer相当于业务接口
 *
 * 具体使用的时候,默认使用WebDelegateDefault,当遇到以实现目的复杂的业务需求时写一个类继承自WebDelegate或者WebDelegateDefault,
 *  * 重写接口IWebViewInitializer中的相关方法
 *
 * Created by wangxiaotao
 */

public class WebDelegateDefault extends WebDelegate implements IWebViewInitializer, IPageLoadListener {


    //必须用这种方式创建WebDelegateDefault 类
    public static WebDelegateDefault create(String url) {
        final Bundle bundle = new Bundle();
      //RouteKeys是个枚举类罢了，里面只有一个值URL
        bundle.putString("web_url", url);
        final WebDelegateDefault delegate = new WebDelegateDefault();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public WebView initWebViewSettings(WebView webView) {
        return new WebViewSettingsInitializer().createWebView(webView);
    }

    //WebDelegateDefault中进行改动
    @Override
    public WebViewClient initWebViewClient() {
        WebViewClientDefault clientDefault = new WebViewClientDefault(this);
        //设置页面start和end时候的回调，这里我们只是留了个接口，以后之后扩展，所以传入null了
        clientDefault.setIPageLoadListener(this);
        return clientDefault;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }

    //基类Delegate中封装的方法，Fragment会加载这个方法返回的view或者layout布局
    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if (getUrl() != null) {
            //进行页面加载
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public IWebViewInitializer setInitializer() {
        //自身实现接口，向上转型返回自身给父类，父类获取到了初始化三部曲后进行初始化
        return this;
    }

    //这是第三方库fragmentation自带的方法，用来重写返回键，表示返回上一个页面而非退出webView。
    //若没用这个第三方库，可以在webView的三部曲之一settings时调用 webView.setOnKeyListener来设置。

    public boolean onBackPressedSupport() {
        if (getWebView().canGoBack()) {  //表示按返回键时的操作
            getWebView().goBack();   //后退
            //webview.goForward();//前进
            return true;    //已处理
        }
        return false;
    }

    @Override
    public void onLoadStart() {
        showDialog(smallDialog);
    }

    @Override
    public void onLoadEnd() {
        hideDialog(smallDialog);
    }
}