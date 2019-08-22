package com.huacheng.huiservers.ui.index.oldservice;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.index.oldservice.adapter.AdapterOldMessage;

/**
 * Description: 关联消息
 * created by wangxiaotao
 * 2019/8/20 0020 下午 6:20
 */
public class OldMessageActivity extends BaseListActivity<String> {



    @Override
    protected void initView() {
        super.initView();
        titleName.setText("关联消息");
        mAdapter = new AdapterOldMessage(this, R.layout.item_old_message,mDatas);
        mListview.setAdapter(mAdapter);

        ImageView img_data = findViewById(R.id.img_data);
        img_data.setBackgroundResource(R.mipmap.bg_message_empty);


    }

    @Override
    protected void requestData() {
//        for (int i = 0; i < 10; i++) {
//            mDatas.add("");
//        }
//        mAdapter.notifyDataSetChanged();
        mRelNoData.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onListViewItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }
}
