package com.huacheng.huiservers.ui.index.workorder_second.commit;

import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.center.geren.bean.GroupMemberBean;
import com.huacheng.huiservers.ui.index.workorder_second.adapter.AdapterHouseList;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:选择房屋列表
 * created by wangxiaotao
 * 2019/4/9 0009 下午 5:46
 */
public class HouseListActivity  extends BaseListActivity {
    List<GroupMemberBean> mDatas = new ArrayList<>();
    AdapterHouseList adapterHouseList ;

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("选择房屋地址");
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        adapterHouseList = new AdapterHouseList(this, R.layout.item_house_list,mDatas);
        mListview.setAdapter(adapterHouseList);
    }

    @Override
    protected void requestData() {
        for (int i = 0; i <5; i++) {
            mDatas.add(new GroupMemberBean());
        }
        adapterHouseList.notifyDataSetChanged();
    }

    @Override
    protected void onListViewItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }
}
