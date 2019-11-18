package com.huacheng.huiservers.ui.shop;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelOldZixun;
import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.ToastUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.linkme.LinkedMeUtils;
import com.microquation.linkedme.android.log.LMErrorCode;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：专区详情（banner点击）
 * 时间：2019/11/14 20:40
 * created by DFF
 */
public class ShopZQWebActivity extends BaseActivity {
    private String id = "";
    private String url = "";
    private String type = "";//2活动详情 1是专区详情
    private WebView mWebview;
    private TextView tv_title;
    private ImageView right_share;
    ModelOldZixun infos;
    private String share_url;
    private String share_title;
    private String share_desc;
    private String share_icon;
    private TextView right;
    private LinearLayout lin_left;
    private LinearLayout lin_right;

    @Override
    protected void initView() {
        findTitleViews();
        mWebview = findViewById(R.id.wv_about);
        lin_left = findViewById(R.id.lin_left);
        tv_title = findViewById(R.id.tv_title);
        right_share = findViewById(R.id.right_share);
        right_share.setVisibility(View.VISIBLE);
       // right_share.setBackground(getResources().getDrawable(R.mipmap.ic_share));
        titleName.setText("活动详情");
    }

    @Override
    protected void initData() {
        getdata();

    }

    @Override
    protected void initListener() {
        lin_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        right_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NullUtil.isStringEmpty(infos.getId())) {
                    return;
                }
                share_title = infos.getTitle() + "";
                share_desc = infos.getTitle() + "";
                if (type.equals("1")){
                    share_icon = MyCookieStore.URL + infos.getBanner();
                }else {
                    share_icon = MyCookieStore.URL + infos.getImg();
                }
                share_url = ApiHttpClient.API_URL_SHARE + ApiHttpClient.API_VERSION + "shop/share_article_info/id/" + infos.getId();
                HashMap<String, String> params = new HashMap<>();
                params.put("type", "prefecture_details");
                params.put("id", infos.getId());
                params.put("sub_type", type);
                showDialog(smallDialog);
                LinkedMeUtils.getInstance().getLinkedUrl(ShopZQWebActivity.this, share_url, share_title, params, new LinkedMeUtils.OnGetLinkedmeUrlListener() {
                    @Override
                    public void onGetUrl(String url, LMErrorCode error) {
                        hideDialog(smallDialog);
                        if (error == null) {
                            String share_url_new = share_url + "?linkedme=" + url;
                            showSharePop(share_title, share_desc, share_icon, share_url_new);
                        } else {
                            //可以看报错
                            String share_url_new = share_url + "?linkedme=" + "";
                            showSharePop(share_title, share_desc, share_icon, share_url_new);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_webview;
    }

    private void getdata() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        if (type.equals("2")) {
            url = ApiHttpClient.SHOP_MARKIING_ARTICE_DETAILS;
        } else {
            url = ApiHttpClient.SHOP_MARKIING_DETAILS;
        }

        MyOkHttp.get().post(url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelOldZixun info = (ModelOldZixun) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldZixun.class);
                    if (info != null) {
                        infos = info;
                        tv_title.setText(info.getTitle());
                        //能够的调用JavaScript代码
                        mWebview.getSettings().setJavaScriptEnabled(true);
                        // 设置允许JS弹窗
                        //        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
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
                        //   byte[] bytes = Base64.decode(info.getContent(), Base64.DEFAULT);
                        //   String content = new String(bytes);
                        if (!"".equals(info.getContent())) {
                            String css = "<style type=\"text/css\"> " +
                                    "img {" +
                                    "max-width: 100% !important;" +//限定图片宽度填充屏幕
                                    "height:auto !important;" +//限定图片高度自动
                                    "}" +
                                    "</style>";
                            String content1 = "<head>" + css + "</head><body>" + info.getContent() + "</body></html>";
                            mWebview.loadDataWithBaseURL(null, content1, "text/html", "utf-8", null);

                        }
//                        if (info.getIs_collection() == 1) {//未收藏
//                            tv_fav.setBackground(getResources().getDrawable(R.drawable.shape_gray_stroke20));
//                            tv_fav.setTextColor(getResources().getColor(R.color.gray_66));
//                            tv_fav.setText("收藏");
//                        } else {
//                            tv_fav.setBackground(getResources().getDrawable(R.drawable.shape_blue_stroke20));
//                            tv_fav.setTextColor(getResources().getColor(R.color.blue));
//                            tv_fav.setText("取消收藏");
//                        }
                        //发evevttype 增加阅读量
                        ModelOldZixun modelOldZixun = new ModelOldZixun();
                        modelOldZixun.setClick(info.getClick());
                        modelOldZixun.setEvevt_type(1);
                        modelOldZixun.setId(info.getId());
                        EventBus.getDefault().post(modelOldZixun);
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    ToastUtils.showShort(mContext.getApplicationContext(), msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                ToastUtils.showShort(mContext.getApplicationContext(), "网络异常，请检查网络设置");
            }
        });
    }

    @Override
    protected void initIntentData() {
        id = this.getIntent().getStringExtra("id");
        type = this.getIntent().getStringExtra("sub_type");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

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
        popup.showBottom(right_share);
    }
}
