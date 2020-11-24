package com.huacheng.huiservers.ui.webview;

import android.webkit.WebView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.MyActivity;

/**
 * @author Created by changyadong on 2020/11/24
 * @description
 */
public class WebviewActivity extends MyActivity {


    WebView webView;
    String url;
    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_webview;
    }

    @Override
    protected void initView() {
        back();
        title(getIntent().getStringExtra("title"));
        webView = findViewById(R.id.webview);

        url = getIntent().getStringExtra("url");

        webView.loadUrl(url);

    }
}
