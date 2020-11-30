package com.huacheng.huiservers.ui.vip;

import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelVipRecord;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

/**
 * Description:  vip开通记录
 * created by wangxiaotao
 * 2020/11/30 0030 08:27
 */
public class VipRecordActivity extends BaseListActivity<ModelVipRecord> {
    @Override
    protected void initView() {
        super.initView();
        titleName.setText("VIP开通记录");
        mAdapter = new CommonAdapter<ModelVipRecord>(this, R.layout.item_vip_record, mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelVipRecord item, int position) {

            }
        };
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        hideDialog(smallDialog);
        for (int i = 0; i < 3; i++) {
            mDatas.add(new ModelVipRecord());
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onListViewItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }
}
