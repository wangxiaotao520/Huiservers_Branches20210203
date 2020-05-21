package com.huacheng.huiservers.ui.webview.vote;

import android.content.Intent;
import android.webkit.WebView;

import com.huacheng.huiservers.ui.webview.common.WebDelegate;
import com.huacheng.huiservers.ui.webview.defaul.WebViewClientDefault;
import com.huacheng.libraryservice.utils.NullUtil;

/**
 * Description: 投票页Webviewclient
 * created by wangxiaotao
 * 2020/5/20 0020 09:28
 */
public class WebViewClientVote extends WebViewClientDefault {
    public WebViewClientVote(WebDelegate DELEGATE) {
        super(DELEGATE);
    }

    //表示重定向和url跳转，由这个webView自己来处理，不会打开外部浏览器
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //     view.loadUrl(url);
        // 这块如果有特殊业务需求就自己做处理,因为这里存在重定向的问题
        if (!NullUtil.isStringEmpty(url)&&(url.contains("http")||url.contains("https"))){
            Intent intent = new Intent(DELEGATE.getContext(),WebViewVoteActivity.class);
            intent.putExtra("url_param",url);
            DELEGATE.startActivity(intent);

            return true;
        }else {
            return super.shouldOverrideUrlLoading(view,url);
        }

    }
}
