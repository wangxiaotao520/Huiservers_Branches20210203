package com.huacheng.huiservers.ui.index.charge;

import android.widget.ListView;
import android.widget.RelativeLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldFile;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.charge.adapter.ChargeMessageListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 时间：2019/8/19 08:56
 * created by DFF
 */
public class ChargeMessageActivity extends BaseActivity {

    protected int page = 1;
    protected ListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    ChargeMessageListAdapter messageListAdapter;
    List<ModelOldFile> mdata = new ArrayList<>();

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("消息");
        mListview = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        mdata.add(new ModelOldFile());
        mdata.add(new ModelOldFile());
        mdata.add(new ModelOldFile());
        messageListAdapter = new ChargeMessageListAdapter(this, R.layout.activity_charge_message_item, mdata);
        mListview.setAdapter(messageListAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_message_list;
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
}
