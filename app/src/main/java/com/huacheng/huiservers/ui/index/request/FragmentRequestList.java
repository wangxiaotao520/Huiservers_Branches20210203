package com.huacheng.huiservers.ui.index.request;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelRequest;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.index.request.adapter.AdapterRequestList;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 投诉建议列表
 * created by wangxiaotao
 * 2019/5/8 0008 上午 9:30
 */
public class FragmentRequestList extends BaseFragment implements AdapterView.OnItemClickListener {
    protected int page = 1;
    protected ListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    List<ModelRequest> mDatas = new ArrayList<>();
    private AdapterRequestList adapter;
    private int type = 1;
    private View title;
    private boolean isInit = false;
    String community_id = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      //  Bundle arguments = getArguments();
      //  type = arguments.getInt("type");
    }

    @Override
    public void initView(View view) {
        title = view.findViewById(R.id.title_bar);
        title.setVisibility(View.GONE);
        mListview = view.findViewById(R.id.listview);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRelNoData = view.findViewById(R.id.rel_no_data);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        adapter = new AdapterRequestList(mActivity, R.layout.item_request_list, mDatas);
        mListview.setAdapter(adapter);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isInit = true;
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
        mListview.setOnItemClickListener(this);
    }

    private void requestData() {
        mListview.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                for (int i = 0; i < 5; i++) {
                    mDatas.add(new ModelRequest());
                }
                adapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //  showDialog(smallDialog);
        // requestData();
        if (type == 1) {
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_list_layout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mActivity, RequesDetailActivity.class);
        intent.putExtra("id","");
        startActivity(intent);

    }

    public void init(String community_id) {
        if (!isInit) {
            this.community_id = community_id;
            if (mRefreshLayout != null) {
                mRefreshLayout.autoRefresh();
            }
        } else {
            if (!this.community_id.equals(community_id)) {
                this.community_id = community_id;
                if (mRefreshLayout != null) {
                    mRefreshLayout.autoRefresh();
                }
            }
        }
    }
}
