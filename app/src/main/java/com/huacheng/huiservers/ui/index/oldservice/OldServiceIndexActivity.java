package com.huacheng.huiservers.ui.index.oldservice;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.OldFragment;

/**
 * Description:居家养老首页
 * created by wangxiaotao
 * 2019/11/15 0015 下午 9:39
 */
public class OldServiceIndexActivity extends BaseActivity{
    OldFragment oldFragment ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        oldFragment=new OldFragment();
        switchFragmentNoBack(oldFragment);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_service_index;
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected int getFragmentCotainerId() {
        return R.id.fl_container;
    }

    @Override
    protected void initFragment() {

    }
}
