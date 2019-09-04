package com.huacheng.huiservers.ui.index.vote;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelIndexVoteItem;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.vote.adapter.IndexVoteAdapter;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 投票首页Activity
 * （票选最美家庭）
 * created by wangxiaotao
 * 2019/9/3 0003 下午 4:20
 */
public class VoteIndexActivity  extends BaseActivity implements IndexVoteAdapter.OnClickItemListener {

    private ImageView iv_right;
    protected PagingListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    protected IndexVoteAdapter mAdapter;
    private View headerView;
    private List<ModelIndexVoteItem> mDatas=new ArrayList<>();//数据

    @Override
    protected void initView() {
        LinearLayout lin_left = findViewById(com.huacheng.libraryservice.R.id.lin_left);
        lin_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleName = findViewById(com.huacheng.libraryservice.R.id.title_name);
        tv_right = findViewById(com.huacheng.libraryservice.R.id.tv_right);
        iv_right = findViewById(R.id.iv_right);
        titleName.setText("华晟杯“最美家庭”投票啦");

        mListview = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);

        headerView = LayoutInflater.from(this).inflate(R.layout.header_vote_index, null);
        initHeaderView();
        mListview.addHeaderView(headerView);

        mAdapter = new IndexVoteAdapter<>(this, mDatas,this);
        mListview.setAdapter(mAdapter);

    }

    /**
     * 初始化headerview
     */
    private void initHeaderView() {
       //TODO
    }

    @Override
    protected void initData() {
      showDialog(smallDialog);
      requestData();
    }

    @Override
    protected void initListener() {
        mListview.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                requestData();
            }
        });
    }

    private void requestData() {
        //TODO 测试
        for (int i = 0; i <29 ; i++) {
            mDatas.add(new ModelIndexVoteItem());

        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote_index;
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

    @Override
    public void onClickItem(View v, int position) {
        SmartToast.showInfo("点击Item"+position);
    }

    @Override
    public void onClickVote(View v, int position) {
        SmartToast.showInfo("点击vote"+position);
    }
}
