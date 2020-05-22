package com.huacheng.huiservers.ui.webview.vote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelShareParam;
import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.webview.common.JSWebInterface;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.linkme.LinkedMeUtils;
import com.microquation.linkedme.android.log.LMErrorCode;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Description:  投票页webview新的
 * created by wangxiaotao
 * 2020/5/21 0021 19:44
 */
public class WebViewVoteActivityNew extends BaseActivity {

    private WebView mWebView;

    View mStatusBar;
    private ImageView iv_right;
    private LinearLayout lin_right;
    String  url = "";
    String  url_param = "";
    private ModelShareParam modelShareParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //当前页设置成永久的白天模式
        this.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mWebView = findViewById(R.id.webView);
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        findTitleViews();

        //以下数据根据接口判断
        mStatusBar.setBackgroundColor(getResources().getColor(R.color.white));
        findViewById(R.id.title_rel).setBackgroundColor(getResources().getColor(R.color.white));
        lin_right = findViewById(R.id.lin_right);
        lin_right.setVisibility(View.GONE);
        iv_right = findViewById(R.id.iv_right);
        iv_right.setBackgroundResource(R.mipmap.ic_share_black);

        initWebViewSetting();
        initWebViewClient();
        initWebViewChromeClient();
        initJs();
//        String url = "http://test2.yulinapp.com/index.php?app=wap&mod=MallVipGoods&act=index&from=singlemessage";
//        mWebView.loadUrl(url);
    }


    //设置websetting
    private void initWebViewSetting() {
        mWebView.setHorizontalScrollBarEnabled(false);
        //不能纵向滚动
        mWebView.setVerticalScrollBarEnabled(false);
        //允许截图
        mWebView.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //初始化WebSettings
        final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        final String ua = settings.getUserAgentString();
        //FIXME 后面的名字不知道是啥
        settings.setUserAgentString(ua + "Latte");

        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(false); //将图片调整到适合webview的大小
        //  settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        // settings.setTextZoom(100);

        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        //禁止缩放
        settings.setSupportZoom(false);
        //文件权限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        //缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    //初始化client
    private void initWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient(){
            //表示重定向和url跳转，由这个webView自己来处理，不会打开外部浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //     view.loadUrl(url);
                // 这块如果有特殊业务需求就自己做处理,因为这里存在重定向的问题
                //     view.loadUrl(url);
                // 这块如果有特殊业务需求就自己做处理,因为这里存在重定向的问题
                if (!NullUtil.isStringEmpty(url)&&(url.contains("http")||url.contains("https"))){
                    Intent intent = new Intent(mContext,WebViewVoteActivityNew.class);
                    intent.putExtra("url_param",url);
                    startActivity(intent);
                    return true;
                }else {
                    return super.shouldOverrideUrlLoading(view,url);
                }
            }

            //页面开始加载时回调（页面后退也会回调）
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 有其他业务需要可以在这里处理
                showDialog(smallDialog);

            }

            //页面完成加载时回调（返回页面也会回调）
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //有其他业务需要可以在这里处理
                hideDialog(smallDialog);

            }
        });


    }

    //
    private void initWebViewChromeClient() {
        mWebView.setWebChromeClient(new WebChromeClient(){

        });
    }

    private void initJs() {
        mWebView.addJavascriptInterface(new JSWebInterface(this),"jsWebInterface");
    }
    @Override
    protected void initData() {
        requestShareAndTitle();
    }
    /**
     * 请求title
     */
    private void requestShareAndTitle() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("shares/share", url_param);
        MyOkHttp.get().post(ApiHttpClient.GET_SHARE_PARAM, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    modelShareParam = (ModelShareParam) JsonUtil.getInstance().parseJsonFromResponse(response, ModelShareParam.class);
                    if (modelShareParam !=null){
                        titleName.setText(modelShareParam.getTitle()+"");
                        if (1==modelShareParam.getIsShare_exist()){
                            lin_right.setVisibility(View.VISIBLE);
                        }else {
                            lin_right.setVisibility(View.GONE);
                        }
                        mWebView.loadUrl(url);
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");

            }
        });
    }
    @Override
    protected void initListener() {
        lin_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享
                if (modelShareParam!=null){
                    final String share_url=ApiHttpClient.API_URL+ApiHttpClient.API_VERSION+modelShareParam.getShare_link();
                    HashMap<String, String> params = new HashMap<>();
                    //  params.put("url", url);
                    params.put("url_param", url_param);
                    params.put("type", "webview_share");
                    showDialog(smallDialog);
                    LinkedMeUtils.getInstance().getLinkedUrl(WebViewVoteActivityNew.this, share_url, modelShareParam.getShare_title(), params, new LinkedMeUtils.OnGetLinkedmeUrlListener() {
                        @Override
                        public void onGetUrl(String url, LMErrorCode error) {
                            hideDialog(smallDialog);
                            if (error == null) {
                                String share_url_new = share_url + "?linkedme=" + url;
                                showSharePop(modelShareParam.getShare_title(), modelShareParam.getShare_desc(), modelShareParam.getShare_img(), share_url_new);
                            } else {
                                //可以看报错
                                String share_url_new = share_url + "?linkedme=" + "";
                                showSharePop(modelShareParam.getShare_title(), modelShareParam.getShare_desc(), modelShareParam.getShare_img(), share_url_new);
                            }
                        }
                    });
                }
            }
        });
    }
    /**
     * 显示分享弹窗
     *
     * @param share_title
     * @param share_desc
     * @param share_icon
     * @param share_url_new
     */
    private void showSharePop(String share_title, String share_desc, String share_icon, String share_url_new) {
        PopupWindowShare popup = new PopupWindowShare(this, share_title, share_desc, share_icon, share_url_new, AppConstant.SHARE_COMMON);
        popup.showBottom(iv_right);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_vote_new;
    }

    @Override
    protected void initIntentData() {
        url_param = getIntent().getStringExtra("url_param");
        if (url_param!=null&&(url_param.contains("http")||url_param.contains("https"))){
            //包含http链接的话
            url=url_param;
        }else {
            url= ApiHttpClient.API_URL+ApiHttpClient.API_VERSION+url_param+"/token/"+ApiHttpClient.TOKEN+"/tokenSecret/"+ApiHttpClient.TOKEN_SECRET;

        }
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

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
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
