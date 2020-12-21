package com.huacheng.huiservers.ui.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelOldZixun;
import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.webview.defaul.WebDelegateDefault;
import com.huacheng.huiservers.ui.webview.loadhtml.WebDelegateHtml;
import com.huacheng.huiservers.utils.NightModeUtils;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.ToastUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.linkme.LinkedMeUtils;
import com.microquation.linkedme.android.log.LMErrorCode;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Description: 默认WebView显示的Activity
 * created by wangxiaotao
 * 2020/3/17 0017 下午 3:29
 * type  //0是加载网页链接 1是请求网络后加载标签 2是直接加载网络标签
 */
public class WebViewDefaultActivity extends BaseActivity {

    private WebDelegateDefault webDelegateDefault;
    private int web_type = 1;//0是加载网页链接 1是请求网络后加载标签 2是直接加载网络标签
    private int jump_type = 0;// 跳转的类型 如：CONSTANT_ZHUANQU
    private TextView tv_title;
    private ImageView iv_right;
    private LinearLayout lin_right;

    String id = "";
    String sub_type = "";
    ModelOldZixun model;
    View mStatusBar;
    private String title;//
    private String content;//
    private String web_url = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initIntentData() {
        this.jump_type = getIntent().getIntExtra("jump_type", 0);
        this.web_type = getIntent().getIntExtra("web_type", 0);

        if (jump_type == ConstantWebView.CONSTANT_ZHUANQU) {
            //专区详情
            id = this.getIntent().getStringExtra("id");
            sub_type = this.getIntent().getStringExtra("sub_type");
        } else if (jump_type == ConstantWebView.CONSTANT_ZHUANQU_HUODONG) {
            //专区活动详情
            id = this.getIntent().getStringExtra("id");
            sub_type = this.getIntent().getStringExtra("sub_type");
        } else if (jump_type == ConstantWebView.CONSTANT_ZUFANG) {
            //租房小贴士
        } else if (jump_type == ConstantWebView.CONSTANT_SHOUFANG) {
            //售房小贴士
        } else if (jump_type == ConstantWebView.CONSTANT_JIAOFANG) {
            //交房手册
            title = getIntent().getStringExtra("articleTitle");
            content = getIntent().getStringExtra("articleCnt");
        } else if (jump_type == ConstantWebView.CONSTANT_YINGSI) {
            //隐私政策
            web_url = ApiHttpClient.GET_PRIVARY;
        } else if (jump_type == ConstantWebView.CONSTANT_FUWU_XIEYI) {
            //服务协议
            Url_info info = new Url_info(this);
            web_url = info.user_agreement;
        }

    }

    @Override
    protected void initView() {
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        findTitleViews();
        tv_title = findViewById(R.id.tv_title);
        lin_right = findViewById(R.id.lin_right);
        iv_right = findViewById(R.id.iv_right);

        lin_right.setVisibility(View.GONE);
        iv_right.setBackgroundResource(R.mipmap.ic_share_black);

        if (jump_type == ConstantWebView.CONSTANT_ZHUANQU) {
            //专区详情
            lin_right.setVisibility(View.VISIBLE);
            titleName.setText("专区详情");
        } else if (jump_type == ConstantWebView.CONSTANT_ZHUANQU_HUODONG) {
            //专区活动详情
            lin_right.setVisibility(View.VISIBLE);
            titleName.setText("活动详情");
        } else if (jump_type == ConstantWebView.CONSTANT_ZUFANG) {
            //租房小贴士
            titleName.setText("租房小贴士");
        } else if (jump_type == ConstantWebView.CONSTANT_SHOUFANG) {
            //售房小贴士
            titleName.setText("售房小贴士");
        } else if (jump_type == ConstantWebView.CONSTANT_JIAOFANG) {
            //交房手册
            titleName.setText(title + "");
        } else if (jump_type == ConstantWebView.CONSTANT_YINGSI) {
            //隐私政策
            titleName.setText("隐私政策");
        } else if (jump_type == ConstantWebView.CONSTANT_FUWU_XIEYI) {
            //服务协议
            titleName.setText("用户协议");
        } else if (jump_type == ConstantWebView.CONSTANT_BILL) {
            //查看票据
            this.web_url = getIntent().getStringExtra("url");
            Log.d("cyd",web_url);
            titleName.setText("查看票据");
        }

        if (web_type == 0) {
            webDelegateDefault = WebDelegateDefault.create("" + web_url);
            switchFragmentNoBack(webDelegateDefault);
        } else if (web_type == 1) {
            //
            requestData();
        } else if (web_type == 2) {
            loadHtml(content + "");
        }

    }

    /**
     * 加载标签
     */
    private void loadHtml(String content) {
        String css = "";
        if (NightModeUtils.getThemeMode() == NightModeUtils.ThemeMode.NIGHT) {
            //深色模式
            css = "<style type=\"text/css\"> " +
                    "img {" +
                    "max-width: 100% !important;" +//限定图片宽度填充屏幕
                    "height:auto !important;" +//限定图片高度自动
                    "}" + "body" +
                    "  {" +
                    "  color:#efefef;background:#1c1c1e;" +
                    "  }" +
                    "</style>";
        } else {
            css = "<style type=\"text/css\"> " +
                    "img {" +
                    "max-width: 100% !important;" +//限定图片宽度填充屏幕
                    "height:auto !important;" +//限定图片高度自动
                    "}" +
                    "</style>";
        }
        content = "<head>" + css + "</head><body>" + content + "</body></html>";
        webDelegateDefault = WebDelegateHtml.create(content);
        switchFragmentNoBack(webDelegateDefault);
    }

    private void requestData() {

        HashMap<String, String> params = new HashMap<>();
        String url = "";
        if (jump_type == ConstantWebView.CONSTANT_ZHUANQU) {
            //专区详情
            url = ApiHttpClient.SHOP_MARKIING_DETAILS;
            params.put("id", id);
        } else if (jump_type == ConstantWebView.CONSTANT_ZHUANQU_HUODONG) {
            //专区活动详情
            url = ApiHttpClient.SHOP_MARKIING_ARTICE_DETAILS;
            params.put("id", id);
        } else if (jump_type == ConstantWebView.CONSTANT_ZUFANG) {
            //租房小贴士
            url = ApiHttpClient.GET_CAREFUL;
            params.put("house_type", "1");
        } else if (jump_type == ConstantWebView.CONSTANT_SHOUFANG) {
            //售房小贴士
            url = ApiHttpClient.GET_CAREFUL;
            params.put("house_type", "2");

        }
        showDialog(smallDialog);
        MyOkHttp.get().post(url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    model = (ModelOldZixun) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldZixun.class);
                    if (model != null) {
                        String content = "";
                        if (jump_type == ConstantWebView.CONSTANT_ZHUANQU) {
                            //专区详情
                            tv_title.setVisibility(View.VISIBLE);
                            tv_title.setText(model.getTitle());
                            content = model.getContent();
                        } else if (jump_type == ConstantWebView.CONSTANT_ZHUANQU_HUODONG) {
                            //专区活动详情
                            tv_title.setVisibility(View.VISIBLE);
                            tv_title.setText(model.getTitle());
                            content = model.getContent();
                        } else if (jump_type == ConstantWebView.CONSTANT_ZUFANG) {
                            //租房小贴士
                            byte[] bytes = Base64.decode(model.getContent(), Base64.DEFAULT);
                            content = new String(bytes);
                        } else if (jump_type == ConstantWebView.CONSTANT_SHOUFANG) {
                            //售房小贴士
                            byte[] bytes = Base64.decode(model.getContent(), Base64.DEFAULT);
                            content = new String(bytes);
                        }
                        loadHtml(content);
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
    protected void initData() {

    }

    @Override
    protected void initListener() {
        lin_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model == null) {
                    return;
                }
                if (jump_type == ConstantWebView.CONSTANT_ZHUANQU || jump_type == ConstantWebView.CONSTANT_ZHUANQU_HUODONG) {
                    //专区分享 专区活动分享
                    if (NullUtil.isStringEmpty(model.getId())) {
                        return;
                    }
                    final String share_title = model.getTitle() + "";
                    final String share_desc = model.getTitle() + "";
                    String icon = "";
                    if (sub_type.equals("1")) {
                        icon = MyCookieStore.URL + model.getBanner();
                    } else {
                        icon = MyCookieStore.URL + model.getImg();
                    }
                    final String share_icon = icon;
                    final String share_url = ApiHttpClient.API_URL_SHARE + ApiHttpClient.API_VERSION + "shop/share_article_info/id/" + model.getId() + "/article_type/" + sub_type;
                    HashMap<String, String> params = new HashMap<>();
                    params.put("type", "prefecture_details");
                    params.put("id", model.getId());
                    params.put("sub_type", sub_type);
                    showDialog(smallDialog);
                    LinkedMeUtils.getInstance().getLinkedUrl(WebViewDefaultActivity.this, share_url, share_title, params, new LinkedMeUtils.OnGetLinkedmeUrlListener() {
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

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_default;
    }


    @Override
    protected int getFragmentCotainerId() {
        return R.id.ll_delegate_container;
    }

    @Override
    protected void initFragment() {

    }

    protected void switchFragmentNoBack(Fragment fragmentInstance) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (int i = 0; i < fragments.size(); i++) {
                Fragment tempFragment = (Fragment) getSupportFragmentManager().findFragmentByTag(fragments.get(i).getClass().getName());
                if (tempFragment != null) {
                    if (tempFragment.getClass().getName().equals(fragmentInstance.getClass().getName())) {
                        t.show(tempFragment);
                    } else {
                        t.hide(tempFragment);
                    }
                }
            }
        }
        Fragment fragmentTarget = getSupportFragmentManager().findFragmentByTag(fragmentInstance.getClass().getName());
        if (fragmentTarget == null && !fragmentInstance.isAdded()) {
            t.add(getFragmentCotainerId(), fragmentInstance, fragmentInstance.getClass().getName());
        }
        t.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (NightModeUtils.getThemeMode() == NightModeUtils.ThemeMode.NIGHT) {
            //todo 黑夜模式下 第一次acitivty会调用两次，webview会为空，我也不知道为什么
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK && webDelegateDefault != null) {
                if (webDelegateDefault.onBackPressedSupport()) {
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
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
        popup.showBottom(lin_right);
    }
}
