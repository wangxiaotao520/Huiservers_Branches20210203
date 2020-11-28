package com.huacheng.huiservers.ui.base;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * @author Created by changyadong on 2020/11/27
 * @description
 */
public abstract class MyListActivity extends MyActivity{

    public int mPage = 1;

    public ListView listView;
    public TextView emptyTx;
    public SmartRefreshLayout refreshLayout;
    @Override
    protected void initView() {


        refreshLayout = findViewById(R.id.refreshLayout);

        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        listView = findViewById(R.id.listview);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getData(1);

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getData(mPage + 1);

            }
        });

    }

    public abstract void getData(int page);

    public void setEmpty(String msg) {

        emptyTx = findViewById(R.id.txt_empty);
        emptyTx.setText(msg);

    }

    public void setEmpty(MyAdapter adapter) {

        emptyTx = findViewById(R.id.txt_empty);
        emptyTx.setText(adapter.getEmptyMsg());
        emptyTx.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
        findViewById(R.id.empty).setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);

    }

}
