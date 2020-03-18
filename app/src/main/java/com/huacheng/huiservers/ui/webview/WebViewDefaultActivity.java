package com.huacheng.huiservers.ui.webview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.webview.defaul.WebDelegateDefault;

import java.util.List;

/**
 * Description: 默认WebView显示的Activity
 * created by wangxiaotao
 * 2020/3/17 0017 下午 3:29
 * type  0是加载网页 1是加载标签
 */
public class WebViewDefaultActivity extends BaseActivity {

    private WebDelegateDefault webDelegateDefault;
    private int type = 1;//0是加载网页 1是加载标签

    @Override
    protected void initView() {
        findTitleViews();
        if (type==0){
            webDelegateDefault = WebDelegateDefault.create("https://www.jianshu.com/p/3c94ae673e2a");
            switchFragmentNoBack(webDelegateDefault);
        }else {
            //todo 请求网络数据回来后
//            String content = "<html>\n" +
//                    "   <head>\n" +
//                    "      <meta charset=\"utf-8\">\n" +
//                    "      <title>Carson</title>  \n" +
//                    "      <script>\n" +
//                    "         \n" +
//                    "        \n" +
//                    "         function callAndroid(){\n" +
//                    "        // 由于对象映射，所以调用test对象等于调用Android映射的对象\n" +
//                    "            jsWebInterface.jumpToGoodsDetail(\"6310\");\n" +
//                    "         }\n" +
//                    "      </script>\n" +
//                    "   </head>\n" +
//                    "   <body>\n" +
//                    "      //点击按钮则调用callAndroid函数<br>" +
//                    "      <button type=\"button\" id=\"button1\" onclick=\"callAndroid()\" style=\"width:500px;height:500px;\"></button>\n" +
//                    "   </body>\n" +
//                    "</html>";
            //测试
//            String content ="<head><style type=\"text/css\"> img {max-width: 100% !important;height:auto !important;}</style></head><body><p class=\"MsoNormal\">\n" +
//                    "\t<br />\n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\">\n" +
//                    "\t<img src=\"http://img.hui-shenghuo.cn/huacheng/editor/image/20200305/20200305172402_76629.jpg\" alt=\"\" style=\"width:340px;height:auto;\"> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">精致，是一个女人爱自己的最好方式。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">愿你做一个精致入骨的女人，把优雅和从容，融入生活的方方面面。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">一辈子都要象十七岁的女孩子那样干净爽洁，体面的着装，微醺的香水，这是一切修炼的最最基础。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">多给自己一些时间打扮，少给自己一些时间慵懒；</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">多给自己一些坚强，少给自己一些怯懦；</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">多给自已一些实事求是，少给自己一些无事生非！</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\">\n" +
//                    "\t<img src=\"http://img.hui-shenghuo.cn/huacheng/editor/image/20200305/20200305172419_92828.jpg\" alt=\"\" style=\"width:340px;height:auto;\"> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">学习搭配衣服，把自己打扮得优雅大方，干净利落。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">买点精致的首饰，可以搭配衣服，起点缀效果。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">控制好自己的体重，不要太胖。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">平时化点淡妆，看起来会更有精神。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">偶尔换换发型，变换不一样的心情。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">包里记得放一个粉饼和口红，方便补妆。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">头发毛躁，擦点护发精油，会更柔顺有光泽。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">定期修理指甲，保持干净美观。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">保持衣服洁净如新，没有异味，衬衫没有褶皱。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">养成运动的习惯，锻炼身体，也能释放压力，放松心情。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">保持断舍离的心态，定期整理衣柜，打扫卫生，把不需要的东西清理掉。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">......</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\" style=\"text-indent:28pt;\">\n" +
//                    "\t<span style=\"font-size:18px;\">小慧特别为广大女性朋友精选超值洗护、日化、护肤品</span><span style=\"font-size:18px;\">......贴心呵护你的每一个阶段，助力你的精致女人修炼之旅。</span> \n" +
//                    "</p>\n" +
//                    "<p class=\"MsoNormal\">\n" +
//                    "\t<img src=\"http://img.hui-shenghuo.cn/huacheng/editor/image/20200305/20200305172434_25265.jpg\" alt=\"\" style=\"width:340px;height:auto;\"> \n" +
//                    "</p>\n" +
//                    "<span style=\"font-size:18px;\"></span> \n" +
//                    "<p>\n" +
//                    "\t<br />\n" +
//                    "</p></body></html>";
//            webDelegateDefault= WebDelegateHtml.create(content);
//            switchFragmentNoBack(webDelegateDefault);
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
        if (keyCode == KeyEvent.KEYCODE_BACK &&webDelegateDefault!=null) {
            if (webDelegateDefault.onBackPressedSupport()) {
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
