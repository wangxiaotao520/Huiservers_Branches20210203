package com.huacheng.huiservers.ui.index.workorder_second;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelWorkOrderList;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.index.workorder_second.adapter.WorkOrderListSecondAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：新版工单Fragment
 * 时间：2019/4/8 16:14
 * created by DFF
 */
public class WorkOrderListcommon extends BaseFragment {
    private ListView mListView;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private boolean isInit = false;       //页面是否进行了初始化
    private boolean isRequesting = false; //是否正在刷新
    int type;
    RelativeLayout rel_no_data;
    List<ModelWorkOrderList> mDatas = new ArrayList<>();
    WorkOrderListSecondAdapter workListAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
    }

    @Override
    public void initView(View view) {

        mListView = view.findViewById(R.id.listview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);
        mDatas.add(new ModelWorkOrderList());
        mDatas.add(new ModelWorkOrderList());
        workListAdapter = new WorkOrderListSecondAdapter(mActivity, R.layout.item_workorder_second_list, mDatas);
        mListView.setAdapter(workListAdapter);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isRequesting = true;
                requestData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRequesting = true;
                requestData();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, WorkOrderDetailActivity.class);
                intent.getIntExtra("type",type);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (type == 0) {
            isInit = true;
            page = 1;
            showDialog(smallDialog);
            requestData();
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
        if (type==0){//待服务

        }else if (type==1){//服务中

        }else if (type==2){//待支付

        }else if (type==3){//已完成

        }
    }

    /**
     * 请求数据
     */
    private void requestData() {
        hideDialog(smallDialog);
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_work_order;
    }

    /**
     * 选中时才调用
     *
     * @param param
     */
    public void onTabSelectedRefresh(String param) {
        if (type > 0 && isInit == false && isRequesting == false) {
            isInit = true;
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
    }
}
