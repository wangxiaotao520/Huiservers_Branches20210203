package com.huacheng.huiservers.ui.webview.loadhtml;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.huacheng.huiservers.ui.webview.common.Router;
import com.huacheng.huiservers.ui.webview.defaul.WebDelegateDefault;

/**
 * Description: 加载标签的delegate
 * created by wangxiaotao
 * 2020/3/17 0017 下午 5:28
 */
public class WebDelegateHtml extends WebDelegateDefault{
    String  webContent = "";

    //必须用这种方式创建WebDelegateDefault 类
    public static WebDelegateDefault create(String url) {
        final Bundle bundle = new Bundle();
        //RouteKeys是个枚举类罢了，里面只有一个值URL
        bundle.putString("web_url", url);
        final WebDelegateHtml delegate = new WebDelegateHtml();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        webContent = args.getString("web_url");
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        //进行页面加载
      Router.getInstance().loadDataWithBaseURL(this.getWebView(), webContent);
    }

}