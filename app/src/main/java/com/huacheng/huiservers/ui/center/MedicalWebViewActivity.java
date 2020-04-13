package com.huacheng.huiservers.ui.center;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.TDevice;

/**
 * 医疗 页面
 *
 * 这一页不用暗黑模式
 */
public class MedicalWebViewActivity extends BaseActivity implements OnClickListener {
    private TextView title_name;
    private LinearLayout lin_left;
    private WebView webView;
    String url, name;
    private View title_rel;
    private View mStatusBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setBackgroundColor(Color.WHITE);//医疗这一页状态栏要设置成白色
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        title_rel=findViewById(R.id.title_rel);
        title_rel.setVisibility(View.GONE);
        findViewById(R.id.v_head_line).setVisibility(View.GONE);
        title_name = (TextView) findViewById(R.id.title_name);
        name = this.getIntent().getExtras().getString("name");
        url = this.getIntent().getExtras().getString("url");
        title_name.setText(name);
        lin_left = (LinearLayout) findViewById(R.id.lin_left);
        lin_left.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.wv_about);
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        //webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

        /**
         * 调用loadUrl()方法进行加载内容
         */
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        webView.loadUrl(url);
        webView.requestFocusFromTouch();
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.about;
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.lin_left:
                finish();
                break;
            default:
                break;
        }
    }

}
