package com.huacheng.huiservers.ui.index.property;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.property.adapter.PropertyWYInfoAdapter;
import com.huacheng.huiservers.ui.index.property.bean.EventProperty;
import com.huacheng.huiservers.ui.index.property.bean.ModelPropertyWyInfo;
import com.huacheng.huiservers.ui.index.property.bean.ModelWuye;
import com.huacheng.huiservers.ui.index.property.inter.OnCheckJFListener;
import com.huacheng.huiservers.utils.XToast;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: 缴费
 * Author: badge
 * Create: 2018/8/3 19:08
 */
public class PropertyHomeListActivity extends BaseActivity implements OnCheckJFListener {

    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.lsit)
    MyListView mLsit;
    @BindView(R.id.tv_house)
    TextView mTvHouse;
    @BindView(R.id.tv_verifyID)
    TextView mTvVerifyID;
    @BindView(R.id.tv_account_price)
    TextView mTvAccountPrice;
    @BindView(R.id.tv_brn)
    TextView mTvBrn;
    @BindView(R.id.rel_no_data)
    RelativeLayout mRelNoData;
    @BindView(R.id.ll_payment)
    LinearLayout llPayment;

    private StringBuilder sb_bill_ids;
    private List<String> selected_id_str = new ArrayList<String>();//选中的id
    private List<Float> selected_price_list = new ArrayList<>();//选中的价格集合
    double realPriceTotal = 0;
    String room_id;//, fullname
    PropertyWYInfoAdapter wyInfoAdapter;
    ModelPropertyWyInfo propertyInfo;
    List<List<ModelWuye>> wyListData = new ArrayList<>();
    private Double total_wuye_price;//选中的物业费

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        titleName.setText("缴费");

        titleName.setFocusable(true);
        titleName.setFocusableInTouchMode(true);
        titleName.requestFocus();

        wyInfoAdapter = new PropertyWYInfoAdapter(PropertyHomeListActivity.this, wyListData, true);
        wyInfoAdapter.setListener(this);
        mLsit.setAdapter(wyInfoAdapter);

    }

    @Override
    protected void initData() {

        room_id = this.getIntent().getExtras().getString("room_id");
        getBillOrderInfo(room_id);
    }

    private void getBillOrderInfo(String room_id) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("room_id", room_id);

        MyOkHttp.get().post(ApiHttpClient.GET_ROOM_BILL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    propertyInfo = (ModelPropertyWyInfo) JsonUtil.getInstance().parseJsonFromResponse(response, ModelPropertyWyInfo.class);
                    if (propertyInfo != null) {
                        if (propertyInfo.getRoom_info() != null) {
                            mTvHouse.setText(propertyInfo.getRoom_info().getAddress());
                            mTvVerifyID.setText(propertyInfo.getRoom_info().getFullname());
                        }

                        getWuyeInfo();
                    }
                } else {
                    XToast.makeText(PropertyHomeListActivity.this, "获取失败", XToast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                XToast.makeText(PropertyHomeListActivity.this, "网络异常，请检查网络设置", XToast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_homelist;
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

    @OnClick({R.id.lin_left, R.id.tv_brn})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.lin_left:
                finish();
                break;
            case R.id.tv_brn:

                if (propertyInfo == null) {
                    break;
                }
                if (propertyInfo.getIs_property() == 1) {
                    XToast.makeText(PropertyHomeListActivity.this, propertyInfo.getIs_property_cn(), XToast.LENGTH_SHORT).show();
                    break;
                }
                if (propertyInfo.getWuye() != null) {
                    if (selected_price_list.size() == 0 || total_wuye_price == 0) {
                        XToast.makeText(this, "请先选择缴费项", XToast.LENGTH_SHORT).show();
                        break;
                    }
                    Intent intent = new Intent(PropertyHomeListActivity.this, PropertyFrimOrderActivity.class);
                    intent.putExtra("room_id", room_id);
                    intent.putExtra("bill_id", sb_bill_ids.toString());
                    startActivity(intent);
                } else {
                    XToast.makeText(PropertyHomeListActivity.this, "费用已缴清", XToast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //新物业回调
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WYCallback(EventProperty eventPro) {
        finish();
    }

    /**
     * 点击选择
     *
     * @param parentPosition
     * @param childPosition
     */
    @Override
    public void onClickChildItem(int parentPosition, int childPosition) {
        if (wyListData.get(parentPosition).get(childPosition).isChecked()) {
            wyListData.get(parentPosition).get(childPosition).setChecked(false);
        } else {
            wyListData.get(parentPosition).get(childPosition).setChecked(true);
        }
        getWuyeInfo();
        wyInfoAdapter.notifyDataSetChanged();
    }

    /**
     * 获取物业信息
     */
    private void getWuyeInfo() {
        if (propertyInfo.getWuye() != null) {
            if (propertyInfo.getWuye().getList() != null && propertyInfo.getWuye().getList().size() > 0) {
                List<List<ModelWuye>> wyList = propertyInfo.getWuye().getList();

                llPayment.setVisibility(View.VISIBLE);
                mRelNoData.setVisibility(View.GONE);
                mLsit.setVisibility(View.VISIBLE);

                wyListData.clear();
                wyListData.addAll(wyList);
                wyInfoAdapter.notifyDataSetChanged();
                selected_id_str.clear();
                selected_price_list.clear();
                for (int i = 0; i < wyList.size(); i++) {
                    for (int i1 = 0; i1 < wyList.get(i).size(); i1++) {
                        String str = wyList.get(i).get(i1).getBill_id();
                        if (wyList.get(i).get(i1).isChecked()) {//如果被选中
                            selected_id_str.add(str);
                            selected_price_list.add(wyList.get(i).get(i1).getSumvalue());
                        }
                    }
                }
                sb_bill_ids = new StringBuilder();
                for (int i = 0; i < selected_id_str.size(); i++) {
                    if (i == 0) {
                        sb_bill_ids.append(String.valueOf(selected_id_str.get(i)));
                    } else {
                        sb_bill_ids.append("," + String.valueOf(selected_id_str.get(i)));
                    }
                }
                //计算选中的物业费
                total_wuye_price = 0.00;
                for (int i = 0; i < selected_price_list.size(); i++) {
                    total_wuye_price += selected_price_list.get(i);
                }
                mTvAccountPrice.setText("¥ " + setFloat(total_wuye_price));

                //   mTvAccountPrice.setText("¥ " + propertyInfo.getWuye().getTot_sumvalue());
            }
        } else {
            llPayment.setVisibility(View.GONE);
            mTvAccountPrice.setText("¥ 0.00");
            mRelNoData.setVisibility(View.VISIBLE);
            mLsit.setVisibility(View.GONE);
        }
    }

    private double setFloat(Double totalPrice) {//保留两位小数点
        BigDecimal b = new BigDecimal(totalPrice);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
