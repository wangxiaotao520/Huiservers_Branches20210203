package com.huacheng.huiservers.model;

import com.huacheng.huiservers.utils.NightModeUtils;

/**
 * Description: 主题theme eventbus
 * created by wangxiaotao
 * 2020/3/25 0025 上午 9:31
 */
public class ModelEventTheme {


    NightModeUtils.ThemeMode themeMode=NightModeUtils.ThemeMode.DAY;

    public NightModeUtils.ThemeMode getThemeMode() {
        return themeMode;
    }

    public void setThemeMode(NightModeUtils.ThemeMode themeMode) {
        this.themeMode = themeMode;
    }


}
