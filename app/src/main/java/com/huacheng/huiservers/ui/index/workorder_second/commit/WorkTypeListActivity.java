package com.huacheng.huiservers.ui.index.workorder_second.commit;

import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelWorkPersonalCatItem;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.index.workorder_second.adapter.AdapterWorkType;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:工单选择分类页面
 * created by wangxiaotao
 * 2019/4/9 0009 下午 5:22
 */
public class WorkTypeListActivity extends BaseListActivity {
    List <ModelWorkPersonalCatItem> mDatas = new ArrayList<>();
    private AdapterWorkType adapterWorkType;

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("选择分类");
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        adapterWorkType = new AdapterWorkType(this, R.layout.item_work_type, mDatas);
        mListview.setAdapter(adapterWorkType);

    }

    @Override
    protected void requestData() {
        for (int i = 0; i <5; i++) {
            mDatas.add(new ModelWorkPersonalCatItem());
        }
        adapterWorkType.notifyDataSetChanged();
    }

    @Override
    protected void onListViewItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }
}
