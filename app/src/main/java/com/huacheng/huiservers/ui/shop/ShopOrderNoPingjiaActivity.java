package com.huacheng.huiservers.ui.shop;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.model.XorderDetailBean;
import com.huacheng.huiservers.ui.shop.presenter.ShopOrderCaoZuoPrester;
import com.huacheng.huiservers.ui.shop.adapter.ShopOrderDetailCaoZuoAdapter;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：待评价 申请退款的全部商品界面
 * 时间：2020/1/9 16:28
 * created by DFF
 */
public class ShopOrderNoPingjiaActivity extends BaseListActivity implements ShopOrderDetailCaoZuoAdapter.OnClickShopDetailListener, ShopOrderCaoZuoPrester.ShopOrderListener {
    private ShopOrderDetailCaoZuoAdapter adapter;
    private int type;
    private String type_name;
    private String order_id;//订单id
    private String p_m_id;//商戶id
    private String list_status;//列表的状态标识
    private ShopOrderCaoZuoPrester mCaozuoPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initView() {
        super.initView();
        titleName.setText(type_name);
        mCaozuoPresenter = new ShopOrderCaoZuoPrester(this, this);
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);

        adapter = new ShopOrderDetailCaoZuoAdapter(this, R.layout.item_shop_common, mDatas, type, this);
        mListview.setAdapter(adapter);
    }

    @Override
    protected void requestData() {

        getData();

    }

    private void getData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", order_id);
        params.put("type", type + "");
        params.put("p_m_id", p_m_id);
        params.put("status", list_status);
        mCaozuoPresenter.getCaoZuoShopInfo(params, type + "");
    }


    @Override
    protected void initIntentData() {
        super.initIntentData();
        type = this.getIntent().getIntExtra("type", 0);
        type_name = this.getIntent().getStringExtra("type_name");
        order_id = this.getIntent().getStringExtra("id");
        list_status = this.getIntent().getStringExtra("status");
        p_m_id = this.getIntent().getStringExtra("p_m_id");
    }

    @Override
    protected void initData() {
        super.initData();

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
    public void onClickButton(final int type, final BannerBean item) {
        if (type == 1) {//退款
            if (mDatas.size() == 1) {//只有一条数据的时候
                Intent intent = new Intent(this, ShopOrderPingJiaAddTuikuanActivity.class);
                intent.putExtra("is_type", 1);
                intent.putExtra("data_info", item);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ShopOrderPingJiaAddTuikuanActivity.class);
                intent.putExtra("is_type", 3);
                intent.putExtra("data_info", item);
                startActivity(intent);
            }

        } else if (type == 2) {//收货
            new CommomDialog(this, R.style.my_dialog_DimEnabled, "是否确认收货？", new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        mCaozuoPresenter.getShouHuo(item.getId());
                        dialog.dismiss();
                    }

                }
            }).show();//.setTitle("提示")


        } else if (type == 3) {//评价
            Intent intent = new Intent(this, ShopOrderPingJiaAddTuikuanActivity.class);
            intent.putExtra("is_type", 2);
            intent.putExtra("data_info", item);
            startActivity(intent);

        }

    }

    @Override
    public void onGetCaoZuoInfo(int status, String msg, List<BannerBean> data, String type) {
        hideDialog(smallDialog);
        if (status == 1) {
            if (data != null && data.size() > 0) {
                mDatas.clear();
                mDatas.addAll(data);
                adapter.notifyDataSetChanged();

            } else {
                //不管是这三种哪种  只要数据为空 直接返回
                if ("1".equals(type)) {//退款
                    finish();
                } else if ("2".equals(type)) {//收货


                } else if ("3".equals(type)) {//评价
                    finish();
                }

            }
        } else {
            SmartToast.showInfo(msg);
        }

    }

    /**
     * 确认收货
     *
     * @param status
     * @param msg
     */
    @Override
    public void onGetShouHuo(int status, String msg, String id) {
        if (status == 1) {
            getData();
            XorderDetailBean XorderDetail = new XorderDetailBean();
            XorderDetail.setId(id);
            XorderDetail.setBack_type(5);
            if (mDatas != null && mDatas.size() > 0) {
                XorderDetail.setShouhuo_type(2);
                EventBus.getDefault().post(XorderDetail);
            } else {
                XorderDetail.setShouhuo_type(1);
                EventBus.getDefault().post(XorderDetail);
                finish();
            }

        } else {
            SmartToast.showInfo(msg);
        }

    }

    /**
     * evens回调退款 评价
     *
     * @param
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void back(XorderDetailBean info) {
        if (info != null) {
            if (info.getBack_type() == 2) {//评价
                //评价刷新
                getData();
                //评价不需要判断  因为不管怎么评价 他都在已完成列表里
              /*  if (mDatas != null && mDatas.size() > 0) {
                } else {
                    finish();
                }*/
            } else if (info.getBack_type() == 3) {//退款
                //退款刷新
                getData();
              /*  if (mDatas != null && mDatas.size() > 0) {
                    info.setTuikuan_type(2);
                } else {
                    info.setTuikuan_type(1);
                    finish();
                }*/
            }
        }
    }

}
