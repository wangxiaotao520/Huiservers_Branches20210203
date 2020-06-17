package com.huacheng.huiservers.ui.servicenew1;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelAddressList;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.AddressListActivity;
import com.huacheng.huiservers.utils.NoDoubleClickListener;
import com.huacheng.libraryservice.utils.NullUtil;

import java.math.BigDecimal;

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
        llRoot.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideDialog(smallDialog);
                llRoot.setVisibility(View.VISIBLE);
                num_commit=1;
                changePriceAndButtonStatus(num_commit);
                tvConfirmPay.setClickable(true);
                for (int i = 0; i < 3; i++) {
                    TextView textView = new TextView(mContext);
                    textView.setTextColor(getResources().getColor(R.color.title_sub_color));
                    textView. setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    if (i==0){
                        textView.setText("• 预约须知：下单后请联系商家提前预约服务 ");
                    }else {
                        textView.setText("• 服务须知：商家可能会主动联系您资讯服务地址及时间， 请注意接听电话，并核对商家信息 ");
                    }
                    llNoteRoot.addView(textView);
                }
                edtNum.setSelection(edtNum.length());
            }
        },1500);

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
                //加好
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
        //todo 测试 到时候从服务器获取单价
        String s_unit_price = "299.00";
        float f_total_price = Float.parseFloat(s_unit_price);
        float f_total_price_new=f_total_price*num;
        BigDecimal b = new BigDecimal(f_total_price_new);
        f_total_price_new = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        tvTotalPrice.setText(f_total_price_new+"");
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
            case R.id.lin_noadress:

                intent = new Intent(this, AddressListActivity.class);
                intent.putExtra("jump_type", 2);
                intent.putExtra("service_id", i_id + "");//这里传的是商户id
                startActivityForResult(intent, 200);
                break;
            case R.id.tv_confirm_pay:
                //todo 跳转支付
                //判断地址
                //判断数量
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == 200) {
                    //选择地址返回
                    if (data != null) {
                        ModelAddressList model = (ModelAddressList) data.getSerializableExtra("model");
                      String   person_name = model.getConsignee_name();
                        String  person_mobile = model.getConsignee_mobile();
                        String   person_address = model.getRegion_cn() + " " + model.getCommunity_cn() + " " + model.getDoorplate();
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
