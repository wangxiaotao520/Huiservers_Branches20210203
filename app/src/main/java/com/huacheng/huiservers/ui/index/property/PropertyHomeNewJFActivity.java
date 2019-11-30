package com.huacheng.huiservers.ui.index.property;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.pay.chinaums.UnifyPayActivity;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.property.adapter.PropertyWYInfoAdapter1;
import com.huacheng.huiservers.ui.index.property.bean.EventProperty;
import com.huacheng.huiservers.ui.index.property.bean.ModelPropertyWyInfo;
import com.huacheng.huiservers.ui.index.property.bean.ModelWuye;
import com.huacheng.huiservers.ui.index.property.inter.OnCheckJFListener;
import com.huacheng.huiservers.ui.index.property.inter.OnCheckJFListener1;
import com.huacheng.huiservers.ui.index.wuye.bean.ProperyGetOrderBean;
import com.huacheng.huiservers.utils.NoDoubleClickListener;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：缴费充值水电费
 * 时间：2018/8/13 16:49
 * created by DFF
 */
public class PropertyHomeNewJFActivity extends BaseActivity implements OnCheckJFListener, OnCheckJFListener1 {
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.tv_house)
    TextView mTvHouse;
    @BindView(R.id.rel_selectaddress)
    RelativeLayout mRelSelectaddress;
    @BindView(R.id.tv_verifyID)
    TextView mTvVerifyID;
    @BindView(R.id.tv_wuye)
    TextView mTvWuye;
    @BindView(R.id.ly_wuye)
    LinearLayout mLyWuye;
    @BindView(R.id.tv_shuifei)
    TextView mTvShuifei;
    @BindView(R.id.ly_shuifei)
    LinearLayout mLyShuifei;
    @BindView(R.id.tv_dianfei)
    TextView mTvDianfei;
    @BindView(R.id.ly_dianfei)
    LinearLayout mLyDianfei;
    @BindView(R.id.list)
    MyListView mList;
    @BindView(R.id.tv_type_name)
    TextView mTvTypeName;
    @BindView(R.id.tv_account_price)
    TextView mTvAccountPrice;
    @BindView(R.id.tv_cz_name)
    TextView mTvCzName;
    @BindView(R.id.et_price)
    EditText mEtPrice;
    @BindView(R.id.lin_list_costs)
    LinearLayout mLinListCosts;
    @BindView(R.id.view_divider_line)
    View mViewDividerLine;
    @BindView(R.id.ly_other)
    LinearLayout mLyOther;
    @BindView(R.id.tv_jf)
    TextView mTvJf;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.rel_no_data)
    RelativeLayout mRelNoData;
    @BindView(R.id.ll_payment)
    LinearLayout llPayment;

    private int type;
    String room_id, fullname;
    private StringBuilder sb_bill_ids;
    private List<String> selected_id_str = new ArrayList<String>();//选中的id
    private List<Float> selected_price_list = new ArrayList<>();//选中的价格集合
//    PropertyWYInfoAdapter wyInfoAdapter;
//    List<List<ModelWuye>> wyListData = new ArrayList<>();

    PropertyWYInfoAdapter1 wyInfoAdapter1;
    List<ModelWuye> wyListData1 = new ArrayList<>();

    ModelPropertyWyInfo propertyInfo;

    private Float total_wuye_price;//选中的物业费

    private String last_ShuiFei = "";//上次水费的值
    private String last_Dianfei = "";//上次电费的值
    private boolean isShuifei = true;
    private String company_id="";

    private String selected_invoice_type = "";//选中的账单类型 如果该参数为0，能多选账单，且只能选该参数为0的账单，如果该参数为1，只能单选，不可选其他任何账单)
    private String selected_bill_id = ""; //选中的账单id 且只有在 selected_invoice_type=“1”时有值 只能选择它
    private String selected_type_id  = "";//选中的费项id 且只有在 selected_invoice_type=“1”时有值 只能选择它
    private View view_shuifei;
    private View view_dianfei;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mTitleName.setText("缴费");
        mTitleName.setFocusable(true);
        mTitleName.setFocusableInTouchMode(true);
        mTitleName.requestFocus();

        mTvWuye.setTextColor(getResources().getColor(R.color.colorPrimary));
        mTvShuifei.setTextColor(getResources().getColor(R.color.gray_55));
        mTvDianfei.setTextColor(getResources().getColor(R.color.gray_55));

        llPayment.setVisibility(View.VISIBLE);
        //ToolUtils.setPriceInput(mEtPrice);
        mEtPrice.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
//        wyInfoAdapter = new PropertyWYInfoAdapter(PropertyHomeNewJFActivity.this, wyListData, true);
//        wyInfoAdapter.setListener(this);
//        mList.setAdapter(wyInfoAdapter);
        wyInfoAdapter1 = new PropertyWYInfoAdapter1(PropertyHomeNewJFActivity.this, R.layout.property_homelist_item1,wyListData1,this);
        mList.setAdapter(wyInfoAdapter1);
        view_shuifei = findViewById(R.id.view_shuifei);
        view_dianfei = findViewById(R.id.view_dianfei);
        view_shuifei.setVisibility(View.GONE);
        view_dianfei.setVisibility(View.GONE);
        mLyShuifei.setVisibility(View.GONE);
        mLyDianfei.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        type = 0;
        mTvJf.setClickable(false);
        getBillOrderInfo(room_id);
    }

    @Override
    protected void initListener() {
        getEditText();
        mTvJf.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (propertyInfo == null) {
                    return;
                }
                if (propertyInfo.getIs_property() == 1) {
                    SmartToast.showInfo(propertyInfo.getIs_property_cn());
                    return;
                }
                if (type == 0) {//缴物业费
                    if (propertyInfo.getWuye() != null) {
                        if (selected_price_list.size() == 0 || total_wuye_price == 0) {
                            SmartToast.showInfo("请先选择缴费项");
                            return;
                        }
                        Intent intent = new Intent(PropertyHomeNewJFActivity.this, PropertyFrimOrderActivity.class);
                        intent.putExtra("room_id", room_id);
                        intent.putExtra("bill_id", sb_bill_ids.toString());
                        intent.putExtra("company_id",company_id);
                        startActivity(intent);
                        finish();
                    } else {
                        SmartToast.showInfo("物业费已缴清");
                    }

                } else if (type == 1) {//充值水费

                    if (propertyInfo.getIs_available() == 0) {//值为0 可以充值水费
                        if (!TextUtils.isEmpty(mEtPrice.getText().toString().trim())) {
                            if (propertyInfo.getShuifei() != null&&propertyInfo.getShuifei().getInfo()!=null) {

                                double aDouble1 = Double.valueOf(propertyInfo.getShuifei().getInfo().getUpper_limit());
                                double aDouble2 = Double.valueOf(propertyInfo.getShuifei().getInfo().getSMay_acc());
                                //如果所剩余额大于等于上限的金额
                                if (aDouble2 >= aDouble1) {
                                    SmartToast.showInfo("您剩余充值金额上限为：0元");
                                } else {
                                    //输入的金额小于等于上限金额减去所剩金额的差
                                    if (Double.valueOf(mEtPrice.getText().toString().trim()) <= (aDouble1 - aDouble2)) {
                                        //立即生成支付订单
                                        getSubmitOrder(propertyInfo.getRoom_info(), propertyInfo.getShuifei().getType(), propertyInfo.getShuifei().getType_cn());
                                    } else {
                                        SmartToast.showInfo("您剩余充值金额上限为：" + (aDouble1 - aDouble2) + "元");
                                    }
                                }
                            }
                        } else {
                            SmartToast.showInfo("请输入充值金额");
                        }

                    } else {
                        SmartToast.showInfo(propertyInfo.getIs_available_cn());
                    }

                } else {

                    //充值电费
                    if (propertyInfo.getIs_available() == 0) {//值为0 可以充值电费

                        if (!TextUtils.isEmpty(mEtPrice.getText().toString().trim())) {
                            if (propertyInfo.getDianfei() != null&&propertyInfo.getDianfei().getInfo()!=null) {

                                double aDouble1 = Double.valueOf(propertyInfo.getDianfei().getInfo().getUpper_limit());
                                double aDouble2 = Double.valueOf(propertyInfo.getDianfei().getInfo().getDMay_acc());
                                //如果所剩余额大于等于上限的金额
                                if (aDouble2 >= aDouble1) {
                                    SmartToast.showInfo("您剩余充值金额上限为：0元");
                                } else {
                                    //输入的金额小于等于上限金额减去所剩金额的差
                                    if (Double.valueOf(mEtPrice.getText().toString().trim()) <= (aDouble1 - aDouble2)) {
                                        //立即生成支付订单
                                        getSubmitOrder(propertyInfo.getRoom_info(), propertyInfo.getDianfei().getType(), propertyInfo.getDianfei().getType_cn());
                                    } else {
                                        SmartToast.showInfo("您剩余充值金额上限为：" + (aDouble1 - aDouble2) + "元");
                                    }
                                }
                            }
                        } else {
                            SmartToast.showInfo("请输入充值金额");
                        }
                    } else {
                        SmartToast.showInfo(propertyInfo.getIs_available_cn());
                    }

                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_homelist_new;
    }

    @Override
    protected void initIntentData() {
        room_id = this.getIntent().getExtras().getString("room_id");
        fullname = this.getIntent().getExtras().getString("fullname");
        company_id = this.getIntent().getExtras().getString("company_id");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void finish() {
        super.finish();
        new ToolUtils(mTvVerifyID, this).closeInputMethod();
        mTvVerifyID.clearFocus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        smallDialog.setCanceledOnTouchOutside(false);
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

    private void getBillOrderInfo(String room_id) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("room_id", room_id);
        params.put("company_id", company_id);

        MyOkHttp.get().post(ApiHttpClient.GET_ROOM_BILL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mTvJf.setClickable(true);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    propertyInfo = (ModelPropertyWyInfo) JsonUtil.getInstance().parseJsonFromResponse(response, ModelPropertyWyInfo.class);
                    if (propertyInfo != null) {
                        if (propertyInfo.getRoom_info() != null) {

                            mTvHouse.setText("地址：" + propertyInfo.getRoom_info().getAddress());
                            mTvVerifyID.setText(propertyInfo.getRoom_info().getFullname());

                        }
                        //物业费用
                        getWuyeInfo();
                    }
                } else {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    @OnClick({R.id.lin_left, R.id.ly_wuye, R.id.ly_shuifei, R.id.ly_dianfei, R.id.ly_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_left:
                finish();
                break;
            case R.id.ly_wuye:
                type = 0;
                mTvWuye.setTextColor(getResources().getColor(R.color.colorPrimary));
                mTvShuifei.setTextColor(getResources().getColor(R.color.gray_55));
                mTvDianfei.setTextColor(getResources().getColor(R.color.gray_55));
                if (propertyInfo != null) {
                    getWuyeInfo();
                }
                break;
            case R.id.ly_shuifei://物业费缴费
                if (propertyInfo == null) {
                    break;
                }
                type = 1;
                mTvWuye.setTextColor(getResources().getColor(R.color.gray_55));
                mTvShuifei.setTextColor(getResources().getColor(R.color.colorPrimary));
                mTvDianfei.setTextColor(getResources().getColor(R.color.gray_55));

                mList.setVisibility(View.GONE);
                mLyOther.setVisibility(View.VISIBLE);
                mRelNoData.setVisibility(View.GONE);

                mTvAccountPrice.setText("¥ 0.00");
                mTvTypeName.setText("水费");
                mTvCzName.setText("水费充值：");
                if (propertyInfo != null && propertyInfo.getShuifei() != null&&propertyInfo.getShuifei().getInfo()!=null) {
                    if (!TextUtils.isEmpty(propertyInfo.getShuifei().getInfo().getSMay_acc())) {
                        mTvPrice.setText("¥ " + propertyInfo.getShuifei().getInfo().getSMay_acc());
                    } else {
                        mTvPrice.setText("¥ 0.00");
                    }
                } else {
                    mTvPrice.setText("¥ 0.00");
                }
                isShuifei = true;
                //输入框和下方价格的显示
                if (!NullUtil.isStringEmpty(last_ShuiFei)) {
                    mTvAccountPrice.setText("¥ " + last_ShuiFei);
                    mEtPrice.setText(last_ShuiFei);
                    mEtPrice.setSelection(last_ShuiFei.length());
                } else {
                    mTvAccountPrice.setText("¥ 0.00");
                    mEtPrice.setText("");
                }
                break;
            case R.id.ly_dianfei:
                if (propertyInfo == null) {
                    break;
                }
                type = 2;
                mTvWuye.setTextColor(getResources().getColor(R.color.gray_55));
                mTvShuifei.setTextColor(getResources().getColor(R.color.gray_55));
                mTvDianfei.setTextColor(getResources().getColor(R.color.colorPrimary));

                mList.setVisibility(View.GONE);
                mLyOther.setVisibility(View.VISIBLE);
                mRelNoData.setVisibility(View.GONE);

                mTvAccountPrice.setText("¥ 0.00");
                mTvTypeName.setText("电费");
                mTvCzName.setText("电费充值：");
                if (propertyInfo.getDianfei() != null) {
                    if (propertyInfo.getDianfei().getInfo()!=null&&!TextUtils.isEmpty(propertyInfo.getDianfei().getInfo().getDMay_acc())) {
                        mTvPrice.setText("¥ " + propertyInfo.getDianfei().getInfo().getDMay_acc());
                    } else {
                        mTvPrice.setText("¥ 0.00");
                    }

                } else {
                    mTvPrice.setText("¥ 0.00");
                }
                isShuifei = false;
                //输入框和下方价格的显示
                if (!NullUtil.isStringEmpty(last_Dianfei)) {
                    mTvAccountPrice.setText("¥ " + last_Dianfei);
                    mEtPrice.setText(last_Dianfei);
                    mEtPrice.setSelection(last_Dianfei.length());
                } else {
                    mTvAccountPrice.setText("¥ 0.00");
                    mEtPrice.setText("");
                }

                break;
            case R.id.ly_other:
                break;
            case R.id.tv_jf://点击缴费及充值
                if (propertyInfo == null) {
                    break;
                }
                if (propertyInfo.getIs_property() == 1) {
                    SmartToast.showInfo(propertyInfo.getIs_property_cn());
                    break;
                }
                if (type == 0) {//缴物业费
                    if (propertyInfo.getWuye() != null) {
                        if (selected_price_list.size() == 0 || total_wuye_price == 0) {
                            SmartToast.showInfo("请先选择缴费项");
                            break;
                        }
                        Intent intent = new Intent(PropertyHomeNewJFActivity.this, PropertyFrimOrderActivity.class);
                        intent.putExtra("room_id", room_id);
                        intent.putExtra("bill_id", sb_bill_ids.toString());
                        intent.putExtra("company_id",company_id);
                        startActivity(intent);
                        finish();
                    } else {
                        SmartToast.showInfo("物业费已缴清");
                    }

                } else if (type == 1) {//充值水费

                    if (propertyInfo.getIs_available() == 0) {//值为0 可以充值水费
                        if (!TextUtils.isEmpty(mEtPrice.getText().toString().trim())) {
                            if (propertyInfo.getShuifei() != null&&propertyInfo.getShuifei().getInfo()!=null) {

                                double aDouble1 = Double.valueOf(propertyInfo.getShuifei().getInfo().getUpper_limit());
                                double aDouble2 = Double.valueOf(propertyInfo.getShuifei().getInfo().getSMay_acc());
                                //如果所剩余额大于等于上限的金额
                                if (aDouble2 >= aDouble1) {
                                    SmartToast.showInfo("您剩余充值金额上限为：0元");
                                } else {
                                    //输入的金额小于等于上限金额减去所剩金额的差
                                    if (Double.valueOf(mEtPrice.getText().toString().trim()) <= (aDouble1 - aDouble2)) {
                                        //立即生成支付订单
                                        getSubmitOrder(propertyInfo.getRoom_info(), propertyInfo.getShuifei().getType(), propertyInfo.getShuifei().getType_cn());
                                    } else {
                                        SmartToast.showInfo("您剩余充值金额上限为：" + (aDouble1 - aDouble2) + "元");
                                    }
                                }
                            }
                        } else {
                            SmartToast.showInfo("请输入充值金额");
                        }

                    } else {
                        SmartToast.showInfo(propertyInfo.getIs_available_cn());
                    }

                } else {

                    //充值电费
                    if (propertyInfo.getIs_available() == 0) {//值为0 可以充值电费

                        if (!TextUtils.isEmpty(mEtPrice.getText().toString().trim())) {
                            if (propertyInfo.getDianfei() != null&&propertyInfo.getDianfei().getInfo()!=null) {

                                double aDouble1 = Double.valueOf(propertyInfo.getDianfei().getInfo().getUpper_limit());
                                double aDouble2 = Double.valueOf(propertyInfo.getDianfei().getInfo().getDMay_acc());
                                //如果所剩余额大于等于上限的金额
                                if (aDouble2 >= aDouble1) {
                                    SmartToast.showInfo("您剩余充值金额上限为：0元");
                                } else {
                                    //输入的金额小于等于上限金额减去所剩金额的差
                                    if (Double.valueOf(mEtPrice.getText().toString().trim()) <= (aDouble1 - aDouble2)) {
                                        //立即生成支付订单
                                        getSubmitOrder(propertyInfo.getRoom_info(), propertyInfo.getDianfei().getType(), propertyInfo.getDianfei().getType_cn());
                                    } else {
                                        SmartToast.showInfo("您剩余充值金额上限为：" + (aDouble1 - aDouble2) + "元");
                                    }
                                }
                            }
                        } else {
                            SmartToast.showInfo("请输入充值金额");
                        }
                    } else {
                        SmartToast.showInfo(propertyInfo.getIs_available_cn());
                    }

                }
                break;
        }
    }

    /**
     * 水费电费生成支付订单
     */
    private void getSubmitOrder(final ModelPropertyWyInfo.RoomInfoBean roomInfoBean, final String type, String type_cn) {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("room_id", roomInfoBean.getRoom_id());
        params.put("category_id", type);
        params.put("category_name", type_cn);
        params.put("amount", mEtPrice.getText().toString().trim());
        params.put("company_id",company_id+"");
     /*   params.put("community_id", roomInfoBean.getCommunity_id());
        params.put("community_name", roomInfoBean.getCommunity_name());
        params.put("building_name", roomInfoBean.getBuilding_name());
        params.put("unit", roomInfoBean.getUnit());
        params.put("code", roomInfoBean.getCode());*/

        MyOkHttp.get().post(info.property_createorder, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ProperyGetOrderBean orderBean = (ProperyGetOrderBean) JsonUtil.getInstance().parseJsonFromResponse(response, ProperyGetOrderBean.class);
                    Intent intent = new Intent(PropertyHomeNewJFActivity.this, UnifyPayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("o_id", orderBean.getOid());
                    bundle.putString("price", mEtPrice.getText().toString().trim());
                    bundle.putString("type", "wuyeNew");
                    bundle.putString("order_type", "wy");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else {
                    //String msg = JsonUtil.getInstance().getMsgFromResponse(response,"提交失败");
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
                llPayment.setVisibility(View.GONE);
            }
        });

    }


    private void getWuyeInfo() {
        if (propertyInfo.getWuye() != null) {
            if (propertyInfo.getWuye().getList() != null && propertyInfo.getWuye().getList().size() > 0) {
                List<List<ModelWuye>> wyList = propertyInfo.getWuye().getList();

                mList.setVisibility(View.VISIBLE);
                mLyOther.setVisibility(View.GONE);
                mRelNoData.setVisibility(View.GONE);

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
                total_wuye_price = (float) 0;
                for (int i = 0; i < selected_price_list.size(); i++) {
                    total_wuye_price += selected_price_list.get(i);
                }
                mTvAccountPrice.setText("¥ " + setFloat(total_wuye_price));
                //  mTvAccountPrice.setText("¥ " + propertyInfo.getWuye().getTot_sumvalue());
            }
            //判断水电费的显示
            if (propertyInfo.getIs_android_electric()==0){
                mLyDianfei.setVisibility(View.VISIBLE);
                view_dianfei.setVisibility(View.VISIBLE);
            }else {
                mLyDianfei.setVisibility(View.GONE);
                view_dianfei.setVisibility(View.GONE);
            }

            if (propertyInfo.getIs_android_water()==0){
                mLyShuifei.setVisibility(View.VISIBLE);
                view_shuifei.setVisibility(View.VISIBLE);
            }else {
                mLyShuifei.setVisibility(View.GONE);
                view_shuifei.setVisibility(View.GONE);
            }
        } else {
            //llPayment.setVisibility(View.GONE);
            mLyOther.setVisibility(View.GONE);
            mList.setVisibility(View.GONE);
            mRelNoData.setVisibility(View.VISIBLE);
            mTvAccountPrice.setText("¥0.00 ");
        }
    }

    private float setFloat(float totalPrice) {//保留两位小数点
        BigDecimal b = new BigDecimal(totalPrice);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 充值金额监听
     */
    private void getEditText() {
        mEtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (NullUtil.isStringEmpty(mEtPrice.getText().toString().trim())) {
                    mTvAccountPrice.setText("¥ 0.00");
                } else {
                    mTvAccountPrice.setText("¥ " + mEtPrice.getText().toString().trim() + "");
                    if (isShuifei) {
                        //保存上次水费的值
                        last_ShuiFei = mEtPrice.getText().toString().trim() + "";
                    } else {
                        //保存上次电费的值
                        last_Dianfei = mEtPrice.getText().toString().trim() + "";
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                    SmartToast.showInfo("所选账单设置为单独开票,不能与其他收费标准合并收费");
                    return;
                }else if ("1".equals(wyListData1.get(childPosition).getIs_invoice())){//单选账单
                    if (selected_type_id.equals(wyListData1.get(childPosition).getType_id())){
                        //费项相同 可选
                        wyListData1.get(childPosition).setChecked(true);
                    }else {
                        SmartToast.showInfo("所选账单设置为单独开票,不能与其他收费标准合并收费");
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
