package com.huacheng.huiservers.ui.fragment.old;

import android.os.Bundle;
import android.view.View;

import com.huacheng.huiservers.ui.base.BaseFragment;

/**
 * Description: 养老模块下方Fragment
 * created by wangxiaotao
 * 2019/8/14 0014 下午 4:50
 */
public class FragmentOldCommonImp extends BaseFragment{
    protected  boolean isInit=false;
    @Override
    public void initView(View view) {

    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    /**
     * 刷新
     */
    public void isRefresh() {
        if (!isInit){
            isInit=true;
        }
    }
}
