package com.huacheng.huiservers.ui.index.coronavirus.investigate;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelInvestigateList;
import com.huacheng.huiservers.ui.base.BaseListActivity;

/**
 * Description: 问卷调查历史记录
 * created by wangxiaotao
 * 2020/2/21 0021 下午 4:32
 */
public class InvestHistoryListActivity extends BaseListActivity<ModelInvestigateList> {

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("历史记录");
        mAdapter = new InvestigateHistoryAdapter(this, R.layout.item_investigate_history,mDatas);
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        //TODO 测试
        hideDialog(smallDialog);
        for (int i = 0; i < 5; i++) {
            mDatas.add(new ModelInvestigateList()) ;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {
        Intent intent;
        intent = new Intent(mContext, InvestigateActivity.class);
        intent.putExtra("jump_type",2);
       // intent.putExtra("room_id", mDatas.get(position).getRoom_id());
        startActivity(intent);
    }
}
