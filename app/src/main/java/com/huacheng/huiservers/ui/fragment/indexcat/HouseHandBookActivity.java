package com.huacheng.huiservers.ui.fragment.indexcat;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.center.bean.HouseBean;
import com.huacheng.huiservers.ui.index.HomeArticleWebviewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：交房手册
 * 时间：2019/12/17 11:54
 * created by DFF
 */
public class HouseHandBookActivity extends BaseListActivity {

    List<HouseBean> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("交房手册");

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        for (int i = 0; i < 8; i++) {
            mDatas.add(new HouseBean());
        }

        mAdapter = new HouseHandBookAdapter(this, R.layout.item_house_handbook, mDatas);
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        hideDialog(smallDialog);

    }

    @Override
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {
        Intent intent = new Intent(mContext, HomeArticleWebviewActivity.class);
        // TODO: 2019/12/17 跳转传参
        intent.putExtra("articleTitle", "");
        intent.putExtra("articleCnt", "");
        startActivity(intent);
    }
}
