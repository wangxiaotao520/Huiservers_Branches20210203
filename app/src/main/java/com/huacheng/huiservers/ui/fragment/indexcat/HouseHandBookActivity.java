package com.huacheng.huiservers.ui.fragment.indexcat;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelIndex;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.fragment.adapter.HouseHandBookAdapter;
import com.huacheng.huiservers.ui.webview.ConstantWebView;
import com.huacheng.huiservers.ui.webview.WebViewDefaultActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：交房手册
 * 时间：2019/12/17 11:54
 * created by DFF
 */
public class HouseHandBookActivity extends BaseListActivity {

    List<ModelIndex> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("交房手册");

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);

        mAdapter = new HouseHandBookAdapter(this, R.layout.item_house_handbook, mDatas);
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        hideDialog(smallDialog);

    }

    @Override
    protected void initIntentData() {
        super.initIntentData();
       mDatas = (List<ModelIndex>) this.getIntent().getSerializableExtra("mDatas");
    }

    @Override
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {
//        Intent intent = new Intent(mContext, HomeArticleWebviewActivity.class);
//        intent.putExtra("articleTitle", mDatas.get(position).getTitle());
//        intent.putExtra("articleCnt",  mDatas.get(position).getContent());
//        startActivity(intent);
        Intent intent = new Intent();
        intent.setClass(this, WebViewDefaultActivity.class);
        intent.putExtra("web_type",2);
        intent.putExtra("jump_type", ConstantWebView.CONSTANT_JIAOFANG);
        intent.putExtra("articleTitle", mDatas.get(position).getTitle());
        intent.putExtra("articleCnt", mDatas.get(position).getContent());
        startActivity(intent);
    }
}
