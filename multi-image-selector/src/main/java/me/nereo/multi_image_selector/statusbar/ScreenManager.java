package me.nereo.multi_image_selector.statusbar;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;



/**
 * Description: 屏幕管理类
 * Author: wangxiaotao
 * Create: 2018/6/9 0009 17:21
 */
public class ScreenManager {
    private static ScreenManager instance;

    private ScreenManager() {
    }

    public synchronized static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    /**
     * 窗口全屏
     */
    public void setFullScreen(boolean isChange,AppCompatActivity mActivity) {
        if(!isChange){
            return;
        }
        mActivity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * [沉浸状态栏]
     */
    public void setStatusBar(boolean isChange,AppCompatActivity mActivity) {
//        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
//        StatusBarUtil.setRootViewFitsSystemWindows(mActivity,true);
//        //设置状态栏透明
//        StatusBarUtil.setTranslucentStatus(mActivity);


        if (isChange){
            StatusBarUtil.setRootViewFitsSystemWindows(mActivity,false);
            StatusBarUtil.setTranslucentStatus(mActivity);
            if (!StatusBarUtil.setStatusBarDarkTheme(mActivity, true)) {//修改的是状态栏的字体颜色
                //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
                //这样半透明+白=灰, 状态栏的文字能看得清
                StatusBarUtil.setStatusBarColor(mActivity,0x55000000);
            }
        }else {
            if (!StatusBarUtil.setStatusBarDarkTheme(mActivity, true)) {
                //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
                //这样半透明+白=灰, 状态栏的文字能看得清
                StatusBarUtil.setStatusBarColor(mActivity,0x55000000);
            }
        }
    }

    /**
     * 旋转屏幕
     **/
    public void setScreenRoate(boolean isChange, AppCompatActivity mActivity) {
        if (!isChange) {
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}