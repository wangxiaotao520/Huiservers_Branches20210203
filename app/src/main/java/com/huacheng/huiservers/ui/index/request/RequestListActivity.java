package com.huacheng.huiservers.ui.index.request;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

/**
 * Description:投诉建议
 * created by wangxiaotao
 * 2019/5/8 0008 上午 8:26
 */
public class RequestListActivity extends BaseActivity {



    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("投诉建议");
        FragmentRequestList fragmentRequestList = new FragmentRequestList();
        switchFragmentNoBack(fragmentRequestList);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_request_list;
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected int getFragmentCotainerId() {
        return R.id.ll_container;
    }

    @Override
    protected void initFragment() {

    }

}
