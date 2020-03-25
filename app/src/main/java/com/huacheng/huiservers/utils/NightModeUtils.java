package com.huacheng.huiservers.utils;

import android.support.v7.app.AppCompatDelegate;

import com.huacheng.huiservers.BaseApplication;

/**
 * Description: 夜间模式工具类
 * created by wangxiaotao
 * 2020/3/25 0025 上午 8:36
 */
public class NightModeUtils {
    // 默认是日间模式
    private static ThemeMode mThemeMode = ThemeMode.DAY;

    /**
     * 主题模式，分为日间模式和夜间模式
     */
    public enum ThemeMode {
        DAY, NIGHT
    }

    /**
     * 设置主题模式
     *
     * @param themeMode
     */
    public static void setThemeMode(ThemeMode themeMode) {
        if (mThemeMode != themeMode) {
            mThemeMode = themeMode;
            //保存到sp中
            new SharePrefrenceUtil(BaseApplication.getContext()).setNightMode(mThemeMode==ThemeMode.NIGHT?true:false);
            AppCompatDelegate.setDefaultNightMode(mThemeMode==ThemeMode.NIGHT?AppCompatDelegate.MODE_NIGHT_YES:AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    /**
     * 得到主题模式
     *
     * @return
     */

    public static ThemeMode getThemeMode() {
        return mThemeMode;
    }

}
