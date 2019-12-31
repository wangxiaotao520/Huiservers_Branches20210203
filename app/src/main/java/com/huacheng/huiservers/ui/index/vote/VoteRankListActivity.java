package com.huacheng.huiservers.ui.index.vote;

import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

/**
 * Description: 活动排行榜
 * created by wangxiaotao
 * 2019/12/31 0031 下午 6:10
 */
public class VoteRankListActivity<String> extends BaseListActivity{
    @Override
    protected void initView() {
        super.initView();
        titleName.setText("活动排行榜");

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        for (int i = 0; i < 8; i++) {
            mDatas.add("");
        }
        mAdapter = new CommonAdapter<String>(this, R.layout.item_vote_rank_list,mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        };
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {

    }
}
