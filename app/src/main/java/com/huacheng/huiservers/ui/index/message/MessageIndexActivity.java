package com.huacheng.huiservers.ui.index.message;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelIndexMessage;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.index.message.adapter.MessageIndexAdapter;
import com.huacheng.huiservers.ui.index.oldservice.OldMessageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：消息大厅
 * 时间：2019/12/12 09:55
 * created by DFF
 */
public class MessageIndexActivity extends BaseListActivity {
    private List<ModelIndexMessage> mDatas = new ArrayList<>();
    private MessageIndexAdapter mAdapter;

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("消息大厅");
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        for (int i = 0; i < 2; i++) {
            mDatas.add(new ModelIndexMessage());
        }

        mAdapter = new MessageIndexAdapter(this, R.layout.layout_message_index, mDatas);
        mListview.setAdapter(mAdapter);

    }

    @Override
    protected void requestData() {
        hideDialog(smallDialog);
    }

    @Override
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {

        if (position == 0) {
            Intent intent = new Intent(this, OldMessageActivity.class);
            startActivity(intent);
        }

    }
}
