package com.huacheng.huiservers.ui.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.webview.loadhtml.WebDelegateHtml;
import com.huacheng.libraryservice.utils.TDevice;

import java.util.List;

/**
 * Description:  webview的投票页
 * created by wangxiaotao
 * 2020/5/16 0016 10:41
 */
public class WebViewVoteActivity extends BaseActivity {

    private WebDelegateHtml webDelegateDefault;
    View mStatusBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //当前页设置成永久的白天模式
        this.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        isStatusBar=true;
        super.onCreate(savedInstanceState);
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        findTitleViews();
        findViewById(R.id.title_rel).setVisibility(View.GONE);
    }


    @Override
    protected void initView() {
        //测试
                    String content = "<html>\n" +
                    "   <head>\n" +
                    "      <meta charset=\"utf-8\">\n" +
                    "      <title>Carson</title>  \n" +
                    "      <script>\n" +
                    "         \n" +
                    "        \n" +
                    "         function callAndroid(){\n" +
                    "        // 由于对象映射，所以调用jsWebInterface对象等于调用Android映射的对象\n" +
                    "            jsWebInterface.share(\"01晟曦苑06号\",\"社区慧生活2020年货节“过年把爱带回家  Vlog直播抢免单\",\"http://img.hui-shenghuo.cn/huacheng/social/20/01/17/5e211e5d30485.PNG\",\"http://test.hui-shenghuo.cn/home/index/vlog_list?linkedme=https://lkme.cc/LQD/WmyqmueIO\");\n" +
                    "         }\n" +
                    "      </script>\n" +
                    "   </head>\n" +
                    "   <body>\n" +
                    "      //点击按钮则调用callAndroid函数<br>" +
                    "      <button type=\"button\" id=\"button1\" onclick=\"callAndroid()\" style=\"width:500px;height:200px;\"></button>\n" +
                    "   </body>\n" +
                    "</html>";
        webDelegateDefault = (WebDelegateHtml) WebDelegateHtml.create(content);
      switchFragmentNoBack(webDelegateDefault);

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

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_default;
    }


    @Override
    protected void initIntentData() {

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
