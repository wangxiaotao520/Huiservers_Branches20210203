package com.huacheng.huiservers.ui.shop;

import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.shop.adapter.ShopZQAdapter;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：专区活动列表
 * 时间：2019/11/13 17:03
 * created by DFF
 */
public class ShopZqHuodongActivity extends BaseListActivity<BannerBean> {
    private ShopZQAdapter shopZQAdapter;
    List<BannerBean> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        findTitleViews();
        titleName.setText("专区活动");

        for (int i = 0; i < 6; i++) {
            mDatas.add(new BannerBean());
        }
        shopZQAdapter = new ShopZQAdapter(this, R.layout.circle_list_item, mDatas);
        mListview.setAdapter(shopZQAdapter);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {
        // TODO: 2019/11/13 跳转到圈子详情界面

    }
}
