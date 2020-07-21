package com.huacheng.huiservers.ui.servicenew1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelAddressList;
import com.huacheng.huiservers.model.ModelServicePurchaseNote;
import com.huacheng.huiservers.pay.chinaums.CanstantPay;
import com.huacheng.huiservers.pay.chinaums.UnifyPayActivity;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.AddressListActivity;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceDetail;
import com.huacheng.huiservers.utils.NoDoubleClickListener;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 服务2.0下单页面
 * created by wangxiaotao
 * 2020/6/17 0017 08:26
 */
public class ServiceConfirmOrderActivityNew extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_mobile)
    TextView txtMobile;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.lin_yesaddress)
    LinearLayout linYesaddress;
    @BindView(R.id.lin_noadress)
    LinearLayout linNoadress;
    @BindView(R.id.tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.tv_unit_price)
    TextView tvUnitPrice;
    @BindView(R.id.tv_reduce)
    TextView tvReduce;
    @BindView(R.id.edt_num)
    EditText edtNum;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.edt_beizhu)
    EditText edtBeizhu;
    @BindView(R.id.ll_note_root)
    LinearLayout llNoteRoot;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_confirm_pay)
    TextView tvConfirmPay;
    private int num_commit= 1;  //提交数
    private String address_id= "";
    private String i_id= "";//商户id
    String tag_id, tag_name, tag_price, service_id, service_name;
    private String person_name;
    private String person_mobile;
    private String person_address;
    private float f_total_price_new;//总价

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("提交订单");

    }

    @Override
    protected void initData() {
        //模拟请求
        showDialog(smallDialog);
        llRoot.setVisibility(View.INVISIBLE);
        tvConfirmPay.setClickable(false);

        requestNote();
    }

    /**
     * 请求购买须知
     */
    private void requestNote() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();

        MyOkHttp.get().post(ApiHttpClient.COMFIRM_ORDER_PURCHASE_NOTE, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(final int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List <ModelServicePurchaseNote>data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelServicePurchaseNote.class);
                    llRoot.setVisibility(View.VISIBLE);
                    tvUnitPrice.setText(tag_price+"");
                    num_commit=1;
                    changePriceAndButtonStatus(num_commit);
                    tvConfirmPay.setClickable(true);
                    for (int i = 0; i < data.size(); i++) {
                        TextView textView = new TextView(mContext);
                        textView.setTextColor(getResources().getColor(R.color.title_sub_color));
                        textView. setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                        textView.setText("• "+data.get(i).getC_name()+"："+data.get(i).getC_text()+" ");
                        llNoteRoot.addView(textView);
                    }
                    edtNum.setSelection(edtNum.length());

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
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

    @Override
    protected void initListener() {
//        edtNum.setFilters(new InputFilter[]{
//
//                        new InputFilter() {
//                            int decimalNumber = 2;//小数点后保留位数
//
//                            @Override
//                            //source:即将输入的内容 dest：原来输入的内容
//                            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                                String sourceContent = source.toString();
//                                String lastInputContent = dest.toString();
//
//                                //验证删除等按键
//                                if (TextUtils.isEmpty(sourceContent)) {
//                                    return "1";
//                                }
//
//
//                                return null;
//                            }
//                        }
//
//                }
//
//        );

        edtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s!=null){
                    String s1 = s.toString();

                        if (NullUtil.isStringEmpty(s1)){
                            changePriceAndButtonStatus(0);
//                            edtNum.setText("1");
//                            edtNum.setSelection(edtNum.length());
                            num_commit=0;
                            return;
                        }else {
                            try {
                                int s1_int = Integer.parseInt(s1);
                                changePriceAndButtonStatus(s1_int);
                                num_commit=s1_int;
                                edtNum.setSelection(edtNum.length());
                                //下面的写法会导致重复调用死循环
//                            if (s1_int<=1){
//                                edtNum.setText("1");
//                                edtNum.setSelection(edtNum.length());
//                                return;
//                            }else if (s1_int>=9999){
//                                edtNum.setText("9999");
//                                edtNum.setSelection(edtNum.length());
//                                return;
//                            }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvReduce.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                //减号
                if (num_commit==1){
                    num_commit=1;
                }else {
                    num_commit=num_commit-1;
                }
               edtNum.setText(num_commit+"");
            }
        });
        tvAdd.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                //加号
                if (num_commit==9999){
                    num_commit=9999;
                }else {
                    num_commit=num_commit+1;
                }
                edtNum.setText(num_commit+"");
            }
        });
        linYesaddress.setOnClickListener(this);
        linNoadress.setOnClickListener(this);
        tvConfirmPay.setOnClickListener(this);
    }

    /**
     * 修改价格及按钮状态
     * @param num
     */
    private void changePriceAndButtonStatus(int num) {
        //获取单价
        String s_unit_price = tag_price;
        float f_total_price = Float.parseFloat(s_unit_price);
        f_total_price_new = f_total_price*num;
        BigDecimal b = new BigDecimal(f_total_price_new);
        f_total_price_new = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        tvTotalPrice.setText(f_total_price_new +"");
        if (num<=1){
            tvReduce.setTextColor(getResources().getColor(R.color.text_color_hint));
        }else {
            tvReduce.setTextColor(getResources().getColor(R.color.title_color));
        }
        if (num>=9999){
            tvAdd.setTextColor(getResources().getColor(R.color.text_color_hint));
        }else {
            tvAdd.setTextColor(getResources().getColor(R.color.title_color));
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_confirm_order_new;
    }

    @Override
    protected void initIntentData() {
        service_id = getIntent().getStringExtra("service_id");
        service_name = getIntent().getStringExtra("service_name");
        tag_id = getIntent().getStringExtra("tag_id");
        tag_name = getIntent().getStringExtra("tag_name");
        tag_price = getIntent().getStringExtra("tag_price");
        i_id = getIntent().getStringExtra("i_id");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.lin_yesaddress:
                //跳转到地址列表
                intent = new Intent(this, AddressListActivity.class);
                intent.putExtra("jump_type", 2);
                intent.putExtra("service_id", i_id + "");//这里传的是商户id
                startActivityForResult(intent, 200);
            case R.id.lin_noadress:

                intent = new Intent(this, AddressListActivity.class);
                intent.putExtra("jump_type", 2);
                intent.putExtra("service_id", i_id + "");//这里传的是商户id
                startActivityForResult(intent, 200);
                break;
            case R.id.tv_confirm_pay:
                //判断地址
                //判断数量
                if (linNoadress.getVisibility()== View.VISIBLE) {
                    SmartToast.showInfo("请选择地址");
                    return;
                }
                if (num_commit<=0){
                    SmartToast.showInfo("购买数量不可为0");
                    return;
                }
                getSubmmit();
                break;
        }
    }

    /**
     * 提交
     */
    private void getSubmmit() {

        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("s_id", service_id);
        params.put("s_tag_id", tag_id);
        params.put("s_tag_cn", tag_name);
        params.put("address_id", address_id);
        params.put("address", person_address);
        params.put("contacts", person_name);
        params.put("mobile", person_mobile);
        params.put("number",num_commit+"");
        params.put("price", tag_price);
        params.put("amount",f_total_price_new+"");
        params.put("description", edtBeizhu.getText().toString().trim()+"");

        MyOkHttp.get().post(ApiHttpClient.GET_SSERVICE_RESERVE, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(final int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelServiceDetail mModelOrdetDetail = (ModelServiceDetail) JsonUtil.getInstance().parseJsonFromResponse(response, ModelServiceDetail.class);
//                    order_id = mModelOrdetDetail.getId();
//                    //下单成功后请求接口给机构端推送订单信息`
//                    setpush(order_id);
//                    new ServiceSuccessDialog(mContext, R.style.my_dialog_DimEnabled, new ServiceSuccessDialog.OnCloseListener() {
//                        @Override
//                        public void onClick(Dialog dialog, String str) {
//
//                            Intent intent = new Intent(mContext, FragmentOrderListActivity.class);
//                            startActivity(intent);
//                            dialog.dismiss();
//                            finish();
//                        }
//                    }).show();
                    //todo 跳转支付

                    Intent intent = new Intent(ServiceConfirmOrderActivityNew.this, UnifyPayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("o_id", mModelOrdetDetail.getId()+"");
                    bundle.putString("price",  f_total_price_new+ "");
                    bundle.putString("type", CanstantPay.PAY_SERVICE);
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == 200) {
                    //选择地址返回
                    if (data != null) {
                        ModelAddressList model = (ModelAddressList) data.getSerializableExtra("model");
                        person_name = model.getConsignee_name();
                        person_mobile = model.getConsignee_mobile();
                        person_address = model.getRegion_cn() + " " + model.getCommunity_cn() + " " + model.getDoorplate();
                        linYesaddress.setVisibility(View.VISIBLE);
                        linNoadress.setVisibility(View.GONE);
                        txtName.setText(person_name);
                        txtAddress.setText(person_address);
                        txtMobile.setText(person_mobile);
                        address_id = model.getId() + "";

                    }
                }

            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
