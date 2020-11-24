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

public abstract class MyListFragment extends MyFragment {
    public int mPage = 1;

    public ListView listView;
    public TextView emptyTx;
    public SmartRefreshLayout refreshLayout;
    public String title = "";

    @Override
    public void initView(View view) {
        initListView();
    }



    public void initListView() {
        refreshLayout = getView().findViewById(R.id.refreshLayout);

        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        listView = getView().findViewById(R.id.listview);

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


    public void loadComplete() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        smallDialog.dismiss();
    }

    public abstract void getData(int page);



    public void setEmpty(String msg) {

        emptyTx = getView().findViewById(R.id.txt_empty);
        emptyTx.setText(msg);

    }

    public void setEmpty(MyAdapter adapter) {

        emptyTx = getView().findViewById(R.id.txt_empty);
        emptyTx.setText(adapter.getEmptyMsg());
        emptyTx.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
        getView().findViewById(R.id.empty).setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}