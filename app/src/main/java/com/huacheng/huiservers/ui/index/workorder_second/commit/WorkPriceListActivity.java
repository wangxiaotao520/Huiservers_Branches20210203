package com.huacheng.huiservers.ui.index.workorder_second.commit;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelWorkPersonalCatItem;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.workorder_second.adapter.AdapterPriceList;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 工单价目表列表
 * created by wangxiaotao
 * 2019/4/10 0010 下午 3:08
 */
public class WorkPriceListActivity extends BaseActivity{
    protected int page = 1;
    protected ExpandableListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    private AdapterPriceList adapterPriceList;
    List<ModelWorkPersonalCatItem> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("价目表");
        mListview = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        //TODO 设置数据
        adapterPriceList = new AdapterPriceList(this);
        mListview.setAdapter(adapterPriceList);
    }

    @Override
    protected void initData() {
   //     第一次加载就展开所有的子类
        for (int i = 0; i < adapterPriceList.getGroupCount(); i++) {
            mListview.expandGroup(i);
        }
    }

    @Override
    protected void initListener() {
          //  不能点击收缩
            mListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return true;
                }
            });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_price_list;
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
