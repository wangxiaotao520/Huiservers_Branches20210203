package com.huacheng.huiservers.ui.index.charge;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldFile;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.charge.adapter.ChargeHistoryAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：充电记录
 * 时间：2019/8/18 11:36
 * created by DFF
 */
public class ChargeHistoryActivity extends BaseActivity {
    protected int page = 1;
    protected ListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    ChargeHistoryAdapter adapter;
    List<ModelOldFile> mdata = new ArrayList<>();

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("充电记录");
        mListview = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        mdata.add(new ModelOldFile());
        adapter = new ChargeHistoryAdapter(this, R.layout.activity_charge_history_item, mdata);
        mListview.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mListview.post(new Runnable() {
                    @Override
                    public void run() {
                        mListview.setSelection(0);
                    }
                });
                requestData();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                requestData();
            }
        });
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent=new Intent(ChargeHistoryActivity.this,ChargeDetailActivity.class);
                Intent intent=new Intent(ChargeHistoryActivity.this,ChargeDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void requestData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_history_list;
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
