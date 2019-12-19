package com.huacheng.huiservers.ui.shop;

import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.shop.adapter.ShopZCAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：特卖专场
 * 时间：2019/12/13 10:54
 * created by DFF
 */
public class ShopZCListActivity extends BaseListActivity {
    private List<ModelShopIndex> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("特卖专场");

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        for (int i = 0; i < 5; i++) {
            mDatas.add(new ModelShopIndex());
        }

        mAdapter = new ShopZCAdapter(this, R.layout.item_shop_zc, mDatas);
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        hideDialog(smallDialog);
    }

    @Override
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {

    }
}
