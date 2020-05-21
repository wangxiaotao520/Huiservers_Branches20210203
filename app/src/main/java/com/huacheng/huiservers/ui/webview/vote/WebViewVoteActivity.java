package com.huacheng.huiservers.ui.webview.vote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.linkme.LinkedMeUtils;
import com.microquation.linkedme.android.log.LMErrorCode;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Description:  webview的投票页(通用webview)
 * created by wangxiaotao
 * 2020/5/16 0016 10:41
 */
public class WebViewVoteActivity extends BaseActivity {

    private WebDelegateVote webDelegateDefault;
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
        //测试
//                    String content = "<html>\n" +
//                    "   <head>\n" +
//                    "      <meta charset=\"utf-8\">\n" +
//                    "      <title>Carson</title>  \n" +
//                    "      <script>\n" +
//                    "         \n" +
//                    "        \n" +
//                    "         function callAndroid(){\n" +
//                    "        // 由于对象映射，所以调用jsWebInterface对象等于调用Android映射的对象\n" +
//                    "            jsWebInterface.share(\"01晟曦苑06号\",\"社区慧生活2020年货节“过年把爱带回家  Vlog直播抢免单\",\"http://img.hui-shenghuo.cn/huacheng/social/20/01/17/5e211e5d30485.PNG\",\"http://test.hui-shenghuo.cn/home/index/vlog_list?linkedme=https://lkme.cc/LQD/WmyqmueIO\");\n" +
//                    "         }\n" +
//                    "      </script>\n" +
//                    "   </head>\n" +
//                    "   <body>\n" +
//                    "      //点击按钮则调用callAndroid函数<br>" +
//                    "      <button type=\"button\" id=\"button1\" onclick=\"callAndroid()\" style=\"width:500px;height:200px;\"></button>\n" +
//                    "   </body>\n" +
//                    "</html>";

        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        findTitleViews();

        //以下数据根据接口判断
        mStatusBar.setBackgroundColor(getResources().getColor(R.color.white));
        findViewById(R.id.title_rel).setBackgroundColor(getResources().getColor(R.color.white));
        lin_right = findViewById(R.id.lin_right);
        lin_right.setVisibility(View.GONE);
        iv_right = findViewById(R.id.iv_right );
        iv_right.setBackgroundResource(R.mipmap.ic_share_black);


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
        MyOkHttp.get().get(ApiHttpClient.GET_SHARE_PARAM, params, new JsonResponseHandler() {
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
                        webDelegateDefault = (WebDelegateVote) WebDelegateVote.create(url);
                        switchFragmentNoBack(webDelegateDefault);
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
                    LinkedMeUtils.getInstance().getLinkedUrl(WebViewVoteActivity.this, share_url, modelShareParam.getShare_title(), params, new LinkedMeUtils.OnGetLinkedmeUrlListener() {
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
        return R.layout.activity_webview_vote;
    }


    @Override
    protected void initIntentData() {
        url_param = getIntent().getStringExtra("url_param");
        if (url_param!=null&&(url_param.contains("http")||url_param.contains("https"))){
            //包含http链接的话
            url=url_param;
        }else {
          url=ApiHttpClient.API_URL+ApiHttpClient.API_VERSION+url_param+"/token/"+ApiHttpClient.TOKEN+"/tokenSecret/"+ApiHttpClient.TOKEN_SECRET;

//          url= "http://test2.yulinapp.com/index.php?app=wap&mod=MallVipGoods&act=index&from=singlemessage";
//            webDelegateDefault = (WebDelegateVote) WebDelegateVote.create(url);
//            switchFragmentNoBack(webDelegateDefault);
        }
    }

    @Override
    protected int getFragmentCotainerId() {
        return R.id.ll_delegate_container;
    }

    @Override
    protected void initFragment() {

    }
    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (NightModeUtils.getThemeMode()== NightModeUtils.ThemeMode.NIGHT){
//
//        }else {
            if (keyCode == KeyEvent.KEYCODE_BACK &&webDelegateDefault!=null) {
                if (webDelegateDefault.onBackPressedSupport()) {
                    return true;
                }
            }
//        }
       return super.onKeyDown(keyCode, event);
    }

}
