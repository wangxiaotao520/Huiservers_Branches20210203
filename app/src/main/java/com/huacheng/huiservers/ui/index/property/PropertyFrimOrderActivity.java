package com.huacheng.huiservers.ui.index.property;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.pay.chinaums.UnifyPayActivity;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.property.adapter.PropertyWYInfoAdapter;
import com.huacheng.huiservers.ui.index.property.bean.EventProperty;
import com.huacheng.huiservers.ui.index.property.bean.ModelPropertyWyInfo;
import com.huacheng.huiservers.ui.index.property.bean.ModelWuye;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：property 物业确认订单
 * 时间：2018/8/3 19:13
 * created by DFF
 */
public class PropertyFrimOrderActivity extends BaseActivity {
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.tv_pay_time)
    TextView mTvPayTime;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.txt_btn)
    TextView mTxtBtn;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;
    @BindView(R.id.list)
    MyListView mList;

    String oid;
    private String bill_id, room_id,company_id,fullName;
    PropertyWYInfoAdapter wyInfoAdapter;
    List<List<ModelWuye>> wyListData = new ArrayList<>();
    ModelPropertyWyInfo propertyInfo;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mTitleName.setText("缴费订单");

        wyInfoAdapter = new PropertyWYInfoAdapter(this, wyListData,false);
        mList.setAdapter(wyInfoAdapter);
        ll_container.setVisibility(View.INVISIBLE);
        mTxtBtn.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void initData() {
        room_id = this.getIntent().getExtras().getString("room_id");
        bill_id = this.getIntent().getExtras().getString("bill_id");
        company_id = this.getIntent().getExtras().getString("company_id");
        fullName = this.getIntent().getExtras().getString("fullName");
        getOrderInfo();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_frim_order;
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

    private void getOrderInfo() {
        Url_info info = new Url_info(this);
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("room_id", room_id);
        params.put("bill_id", bill_id);
        params.put("company_id", company_id);

        MyOkHttp.get().post(info.make_property_order, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    propertyInfo = (ModelPropertyWyInfo) JsonUtil.getInstance().parseJsonFromResponse(response, ModelPropertyWyInfo.class);
                    if (propertyInfo != null) {
                        ll_container.setVisibility(View.VISIBLE);
                        mTxtBtn.setVisibility(View.VISIBLE);

                    //    mTvName.setText(propertyInfo.getNickname());
                        if (!NullUtil.isStringEmpty(fullName)){
                            mTvName.setText(fullName);
                        }else {
                            mTvName.setText(propertyInfo.getNickname());
                        }
                        mTvAddress.setText("" + propertyInfo.getAddress());
                        mTvOrderNumber.setText("" + propertyInfo.getOrder_num());
                        mTvPayTime.setText("" + StringUtils.getDateToString(propertyInfo.getAddtime(), "7"));
                        mTvAmount.setText(""+ propertyInfo.getTot_sumvalue());
                        if (propertyInfo.getList() != null && propertyInfo.getList().size() > 0) {
                            List<List<ModelWuye>> wyList = propertyInfo.getList();
                            for (int i = 0; i < wyList.size(); i++) {
                                for (int i1 = 0; i1 < wyList.get(i).size(); i1++) {
                                    oid = wyList.get(i).get(i1).getOid();
                                }
                            }
                            wyListData.clear();
                            wyListData.addAll(wyList);
                            wyInfoAdapter.notifyDataSetChanged();
                        } else {
                            wyListData.clear();
                            if (wyInfoAdapter != null) {
                                wyInfoAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"账单生成失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }


    @OnClick({R.id.lin_left, R.id.txt_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_left:
                finish();
                break;
            case R.id.txt_btn:
                Intent intent = new Intent(PropertyFrimOrderActivity.this, UnifyPayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("o_id", oid);
                bundle.putString("price", propertyInfo.getTot_sumvalue() + "");
                bundle.putString("type", "wuyeNew");
                bundle.putString("order_type", "wy");
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
        }
    }


}
