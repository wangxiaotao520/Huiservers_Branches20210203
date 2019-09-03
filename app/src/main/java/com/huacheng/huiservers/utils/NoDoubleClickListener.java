package com.huacheng.huiservers.utils;

import android.view.View;

import java.util.Calendar;

/**
 * Description:  防止重复点击 1s钟只能点击一次 （提交按钮）
 * created by wangxiaotao
 * 2019/9/3 0003 上午 10:38
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);

}