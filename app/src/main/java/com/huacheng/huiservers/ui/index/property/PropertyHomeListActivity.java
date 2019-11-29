package com.huacheng.huiservers.ui.index.property;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.property.adapter.PropertyWYInfoAdapter1;
import com.huacheng.huiservers.ui.index.property.bean.EventProperty;
import com.huacheng.huiservers.ui.index.property.bean.ModelPropertyWyInfo;
import com.huacheng.huiservers.ui.index.property.bean.ModelWuye;
import com.huacheng.huiservers.ui.index.property.inter.OnCheckJFListener;
import com.huacheng.huiservers.ui.index.property.inter.OnCheckJFListener1;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.NullUtil;
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
public class PropertyHomeListActivity extends BaseActivity implements OnCheckJFListener, OnCheckJFListener1 {

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
//    PropertyWYInfoAdapter wyInfoAdapter;
    ModelPropertyWyInfo propertyInfo;
//    List<List<ModelWuye>> wyListData = new ArrayList<>();
    PropertyWYInfoAdapter1 wyInfoAdapter1;
    List<ModelWuye> wyListData1 = new ArrayList<>();
    private float total_wuye_price;//选中的物业费
    private String company_id ="";

    private String selected_invoice_type = "";//选中的账单类型 如果该参数为0，能多选账单，且只能选该参数为0的账单，如果该参数为1，只能单选，不可选其他任何账单)
    private String selected_bill_id = ""; //选中的账单id 且只有在 selected_invoice_type=“1”时有值 只能选择它
    private String selected_type_id  = "";//选中的费项id 且只有在 selected_invoice_type=“1”时有值 只能选择它


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        titleName.setText("缴费");

        titleName.setFocusable(true);
        titleName.setFocusableInTouchMode(true);
        titleName.requestFocus();

//        wyInfoAdapter = new PropertyWYInfoAdapter(PropertyHomeListActivity.this, wyListData, true);
//        wyInfoAdapter.setListener(this);
//        mLsit.setAdapter(wyInfoAdapter);
        wyInfoAdapter1 = new PropertyWYInfoAdapter1(PropertyHomeListActivity.this, R.layout.property_homelist_item1,wyListData1,this);
        mLsit.setAdapter(wyInfoAdapter1);
    }

    @Override
    protected void initData() {

        getBillOrderInfo(room_id);
    }

    private void getBillOrderInfo(String room_id) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("room_id", room_id);
        params.put("company_id", company_id);

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
                    SmartToast.showInfo("获取失败");
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
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
        room_id = this.getIntent().getExtras().getString("room_id");
        company_id = this.getIntent().getExtras().getString("company_id");
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
                    SmartToast.showInfo(propertyInfo.getIs_property_cn());
                    break;
                }
                if (propertyInfo.getWuye() != null) {
                    if (selected_price_list.size() == 0 || total_wuye_price == 0) {
                        SmartToast.showInfo("请先选择缴费项");
                        break;
                    }
                    Intent intent = new Intent(PropertyHomeListActivity.this, PropertyFrimOrderActivity.class);
                    intent.putExtra("room_id", room_id);
                    intent.putExtra("bill_id", sb_bill_ids.toString());
                    intent.putExtra("company_id",company_id);
                    startActivity(intent);
                    finish();
                } else {
                    SmartToast.showInfo("费用已缴清");
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
//        if (wyListData.get(parentPosition).get(childPosition).isChecked()) {//本身是选中状态 反选
//            //先反选
//            wyListData.get(parentPosition).get(childPosition).setChecked(false);
//            selected_invoice_type="";
//            selected_bill_id="";
//            //遍历所有集合，判断有无选中
//            Loop:
//            for (int i = 0; i < wyListData.get(parentPosition).size(); i++) {
//                List<ModelWuye> modelWuyes = wyListData.get(parentPosition);
//                for (int i1 = 0; i1 < modelWuyes.size(); i1++) {
//                    if (modelWuyes.get(i1).isChecked()){
//                        selected_invoice_type=modelWuyes.get(i1).getIs_invoice();
//                        if (selected_invoice_type.equals("1")){
//                            selected_bill_id= modelWuyes.get(i1).getBill_id();
//                        }
//                        break Loop;
//                    }
//                }
//            }
//
//        } else { //本身是没选中
//            if (NullUtil.isStringEmpty(selected_invoice_type)){
//                //从来没有选过
//                wyListData.get(parentPosition).get(childPosition).setChecked(true);
//                selected_invoice_type= wyListData.get(parentPosition).get(childPosition).getIs_invoice();
//                if ("1".equals(selected_invoice_type)){
//                    selected_bill_id= wyListData.get(parentPosition).get(childPosition).getBill_id()+"";
//                }else {
//                    selected_bill_id="";
//                }
//            }else if ("0".equals(selected_invoice_type)){
//                //可多选
//                if ("0".equals(wyListData.get(parentPosition).get(childPosition).getIs_invoice())) {
//                    wyListData.get(parentPosition).get(childPosition).setChecked(true);
//                }else if ("1".equals(wyListData.get(parentPosition).get(childPosition).getIs_invoice())){//单选账单
//                    SmartToast.showInfo("该账单不可合并支付");
//                    return;
//                }
//            }else if ("1".equals(selected_invoice_type)){//单选账单
//                if ("0".equals(wyListData.get(parentPosition).get(childPosition).getIs_invoice())) {
//                    SmartToast.showInfo("该账单只能单独支付");
//                    return;
//                }else if ("1".equals(wyListData.get(parentPosition).get(childPosition).getIs_invoice())){//单选账单
//                    SmartToast.showInfo("该账单只能单独支付");
//                    return;
//                }
//            }
//
//        }
//        wyInfoAdapter.setSelected_bill_id(selected_bill_id);
//        wyInfoAdapter.setSelected_invoice_type(selected_invoice_type);
//        getWuyeInfo();
//        wyInfoAdapter.notifyDataSetChanged();
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

//                wyListData.clear();
//                wyListData.addAll(wyList);
//                wyInfoAdapter.notifyDataSetChanged();
                wyListData1.clear();
                for (int i = 0; i < wyList.size(); i++) {
                    for (int i1 = 0; i1 < wyList.get(i).size(); i1++) {
                        wyList.get(i).get(i1).setPosition(i1);
                        wyListData1.add( wyList.get(i).get(i1));
                    }
                }
                wyInfoAdapter1.notifyDataSetChanged();
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
                total_wuye_price = 0;
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

    private float setFloat(float totalPrice) {//保留两位小数点
        BigDecimal b = new BigDecimal(totalPrice);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    @Override
    public void onClickChildItem(int childPosition) {
        if (wyListData1.get(childPosition).isChecked()) {//本身是选中状态 反选
            //先反选
            wyListData1.get(childPosition).setChecked(false);
            selected_invoice_type="";
            selected_bill_id="";
            selected_type_id="";
            //遍历所有集合，判断有无选中
            Loop:
            for (int i = 0; i <wyListData1.size(); i++) {
                ModelWuye modelWuye =  wyListData1.get(i);
                if (modelWuye.isChecked()){
                    selected_invoice_type=modelWuye.getIs_invoice();
                    if (selected_invoice_type.equals("1")){
                        selected_bill_id= modelWuye.getBill_id();
                        selected_type_id=modelWuye.getType_id();
                    }
                    break Loop;
                }

            }
        } else { //本身是没选中
            if (NullUtil.isStringEmpty(selected_invoice_type)){
                //从来没有选过
                wyListData1.get(childPosition).setChecked(true);
                selected_invoice_type= wyListData1.get(childPosition).getIs_invoice();
                if ("1".equals(selected_invoice_type)){
                    selected_bill_id= wyListData1.get(childPosition).getBill_id()+"";
                    selected_type_id=wyListData1.get(childPosition).getType_id()+"";
                }else {
                    selected_bill_id="";
                    selected_type_id="";
                }
            }else if ("0".equals(selected_invoice_type)){
                //可多选
                if ("0".equals(wyListData1.get(childPosition).getIs_invoice())) {
                    wyListData1.get(childPosition).setChecked(true);
                }else if ("1".equals(wyListData1.get(childPosition).getIs_invoice())){//单选账单
                    SmartToast.showInfo(wyListData1.get(childPosition).getCharge_type()+"设置为单独开票,不能与其他收费标准合并收费");
                    return;
                }
            }else if ("1".equals(selected_invoice_type)){//单选账单
                if ("0".equals(wyListData1.get(childPosition).getIs_invoice())) {
                    SmartToast.showInfo("所选收费标准设置为单独开票,不能与其他收费标准合并收费");
                    return;
                }else if ("1".equals(wyListData1.get(childPosition).getIs_invoice())){//单选账单
                    if (selected_type_id.equals(wyListData1.get(childPosition).getType_id())){
                        //费项相同 可选
                        wyListData1.get(childPosition).setChecked(true);
                    }else {
                        SmartToast.showInfo("所选收费标准设置为单独开票,不能与其他收费标准合并收费");
                        return;
                    }
                }
            }

        }
        wyInfoAdapter1.setSelected_bill_id(selected_bill_id);
        wyInfoAdapter1.setSelected_invoice_type(selected_invoice_type);
        wyInfoAdapter1.setSelected_type_id(selected_type_id);
        wyInfoAdapter1.notifyDataSetChanged();
        sumValue();
    }

    private void sumValue() {
        selected_id_str.clear();
        selected_price_list.clear();
        for (int i = 0; i < wyListData1.size(); i++) {
            String str = wyListData1.get(i ).getBill_id();
            if (wyListData1.get(i ).isChecked()) {//如果被选中
                selected_id_str.add(str);
                selected_price_list.add(wyListData1.get(i ).getSumvalue());
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
        total_wuye_price = (float) 0;
        for (int i = 0; i < selected_price_list.size(); i++) {
            total_wuye_price += selected_price_list.get(i);
        }
        mTvAccountPrice.setText("¥ " + setFloat(total_wuye_price));
    }
}
