package com.huacheng.huiservers.ui.vip;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.MyListActivity;

/**
 * @author Created by changyadong on 2020/11/27
 * @description
 */
public class PointDetailActivity extends MyListActivity {


    PointDetailAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.actvivity_point_detail;
    }

    @Override
    protected void initData() {

        adapter = new PointDetailAdapter();
        listView.setAdapter(adapter);

    }

    @Override
    public void getData(int page) {


    }

    @Override
    protected void initView() {
        super.initView();

    }
}
