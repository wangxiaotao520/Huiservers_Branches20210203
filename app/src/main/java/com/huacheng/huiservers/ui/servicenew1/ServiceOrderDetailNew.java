package com.huacheng.huiservers.ui.servicenew1;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.servicenew1.adapter.ServiceOrderDetailAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 2.0服务订单详情
 * created by wangxiaotao
 * 2020/6/17 0017 17:21
 */
public class ServiceOrderDetailNew extends BaseActivity {
    private ListView listView;
    private SmartRefreshLayout refreshLayout;
    private View headerView;
    private View footerView;
    private List<String > mDatas = new ArrayList<>();
    private ServiceOrderDetailAdapter mAdapter;
    private FrameLayout fl_bottom;
    private TextView tv_btn;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("订单详情");
        listView = findViewById(R.id.listview);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        headerView = LayoutInflater.from(this).inflate(R.layout.layout_service_order_detail_header, null);
        initHeader(headerView);
        footerView = LayoutInflater.from(this).inflate(R.layout.layout_service_order_detail_footer, null);
        initFooter(footerView);
        listView.addHeaderView(headerView);
        listView.addFooterView(footerView);
        mAdapter = new ServiceOrderDetailAdapter(this, R.layout.item_service_order, mDatas);
        listView.setAdapter(mAdapter);

        fl_bottom = findViewById(R.id.fl_bottom);
        tv_btn = findViewById(R.id.tv_btn);

    }

    private void initHeader(View headerView) {
        //头部相关布局
    }
    private void initFooter(View footerView) {
        //footer相关布局
    }
    @Override
    protected void initData() {
        //TODO 测试
        for (int i = 0; i < 6; i++) {
            mDatas.add("1")  ;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_order_detail_new;
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
