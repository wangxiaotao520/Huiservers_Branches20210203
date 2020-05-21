package com.huacheng.huiservers.ui.webview.vote;

import android.os.Bundle;
import android.webkit.WebViewClient;

import com.huacheng.huiservers.ui.webview.defaul.WebDelegateDefault;

/**
 * Description: 投票页的webview Deletegate
 * created by wangxiaotao
 * 2020/5/20 0020 09:26
 */
public class WebDelegateVote extends WebDelegateDefault {

    //必须用这种方式创建WebDelegateDefault 类
    public static WebDelegateVote create(String url) {
        final Bundle bundle = new Bundle();
        //RouteKeys是个枚举类罢了，里面只有一个值URL
        bundle.putString("web_url", url);
        final WebDelegateVote delegate = new WebDelegateVote();
        delegate.setArguments(bundle);

        return delegate;
    }

    //WebDelegateDefault中进行改动
    @Override
    public WebViewClient initWebViewClient() {
        WebViewClientVote clientDefault = new WebViewClientVote(this);
        //设置页面start和end时候的回调，这里我们只是留了个接口，以后之后扩展，所以传入null了
        clientDefault.setIPageLoadListener(this);

        return clientDefault;
    }
}
