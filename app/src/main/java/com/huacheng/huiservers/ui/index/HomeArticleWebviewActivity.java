package com.huacheng.huiservers.ui.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.TDevice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/31.
 */

public class HomeArticleWebviewActivity extends BaseActivity {

    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.wv_about)
    WebView mWebview;
    View mStatusBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }

    private void proccessData(String articleCnt) {

        mWebview.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        mWebview.getSettings().setSupportZoom(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
        // 设置隐藏缩放工具控制条
        mWebview.getSettings().setDisplayZoomControls(false);
        //扩大比例的缩放
        mWebview.getSettings().setUseWideViewPort(false);
        //自适应屏幕
        mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebview.getSettings().setLoadWithOverviewMode(true);
        //设置字体大小
        mWebview.getSettings().setTextSize(WebSettings.TextSize.NORMAL);

        //mWebview.loadDataWithBaseURL(null, getHtmlContent(articleCnt), "text/html", "utf-8", null);
        String content = articleCnt;
        if (!"".equals(content)) {
            String css = "<style type=\"text/css\"> " +
                    "img {" +
                    "max-width: 100% !important;" +//限定图片宽度填充屏幕
                    "height:auto !important;" +//限定图片高度自动
                    "}" +
                    "</style>";
            String content1 = "<head>" + css + "</head><body>" + content + "</body></html>";
            mWebview.loadDataWithBaseURL(null, content1, "text/html", "utf-8", null);
        }

    }

    //webview   图片适配
    private String getHtmlContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }

        return doc.toString();
    }

    @OnClick(R.id.lin_left)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.lin_left:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //       SetTransStatus.GetStatus(this);
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        String articleTitle = getIntent().getStringExtra("articleTitle");
        titleName.setText(articleTitle);

        String articleCnt = getIntent().getStringExtra("articleCnt");
        if (!StringUtils.isEmpty(articleCnt)) {
            proccessData(articleCnt);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.shop_webview;
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
}
