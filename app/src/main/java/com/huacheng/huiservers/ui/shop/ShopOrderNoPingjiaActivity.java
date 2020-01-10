package com.huacheng.huiservers.ui.shop;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.center.bean.XorderDetailBean;
import com.huacheng.huiservers.ui.shop.adapter.ShopOrderDetailAdapter;

/**
 * 类描述：待评价 申请退款的全部商品界面
 * 时间：2020/1/9 16:28
 * created by DFF
 */
public class ShopOrderNoPingjiaActivity extends BaseListActivity implements ShopOrderDetailAdapter.OnClickShopDetailListener {
    private ShopOrderDetailAdapter adapter;
    private int type;
    private String type_name;

    @Override
    protected void requestData() {
        titleName.setText(type_name);

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);

        for (int i = 0; i < 5; i++) {
            mDatas.add(new XorderDetailBean());
        }
        adapter = new ShopOrderDetailAdapter(this, R.layout.item_shop_order_detail, mDatas, type, this);
        mListview.setAdapter(adapter);
    }

    @Override
    protected void initIntentData() {
        super.initIntentData();
        type = this.getIntent().getIntExtra("type", 0);
        type_name = this.getIntent().getStringExtra("type_name");
    }

    @Override
    protected void initData() {
        super.initData();
        hideDialog(smallDialog);
    }

    @Override
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {

    }

    /**
     * 申请退款、评价、确认收货
     *
     * @param type
     * @param item
     */
    @Override
    public void onClickButton(int type, XorderDetailBean item) {
        if (type == 1) {//退款
            Intent intent = new Intent(this, ShopOrderPingJiaAddTuikuanActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
        } else if (type == 2) {//评价
            Intent intent = new Intent(this, ShopOrderPingJiaAddTuikuanActivity.class);
            intent.putExtra("type", 2);
            startActivity(intent);
        } else if (type == 3) {//确认收货
            // TODO: 2020/1/10 这里是弹窗还是什么？
        }


    }
}
