package com.huacheng.huiservers.ui.center.geren;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.model.protocol.FacePayProtocol;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.geren.bean.WiredBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.UIUtils;
import com.huacheng.huiservers.view.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/8.
 */

public class WiredIndexActivity extends BaseActivityOld {

    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.lin_right)
    LinearLayout linRight;
    @BindView(R.id.cet_cabetelID)
    ClearEditText cetCabetelID;
    @BindView(R.id.cet_cabetelName)
    ClearEditText cetCabetelName;
    @BindView(R.id.cet_cabetelPrice)
    ClearEditText cetCabetelPrice;

    SharePrefrenceUtil prefrenceUtil;
    FacePayProtocol mProtocol = new FacePayProtocol();
    WiredBean mWiredBean = new WiredBean();
    private static final int DECIMAL_DIGITS = 2;//小数的位数
    public static WiredIndexActivity instant;

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.wired_index);
        instant = this;
        //    SetTransStatus.GetStatus(this);
        ButterKnife.bind(this);
        titleName.setText("有线电视缴费");
        linRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.ic_order5);

        //获取小区id
        prefrenceUtil = new SharePrefrenceUtil(this);
        cetCabetelPrice.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        cetCabetelPrice.setFilters(new InputFilter[]{new InputFilter() {
            int decimalNumber = 2;//小数点后保留位数

            @Override
            //source:即将输入的内容 dest：原来输入的内容
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String sourceContent = source.toString();
                String lastInputContent = dest.toString();

                //验证删除等按键
                if (TextUtils.isEmpty(sourceContent)) {
                    return "";
                }
                //以小数点"."开头，默认为设置为“0.”开头
                if (sourceContent.equals(".") && lastInputContent.length() == 0) {
                    return "0.";
                }
                /*//输入“0”，默认设置为以"0."开头
                if (sourceContent.equals("0") && lastInputContent.length() == 0) {
                    return "0.";
                }*/
                //小数点后保留两位
                if (lastInputContent.contains(".")) {
                    int index = lastInputContent.indexOf(".");
                    if (dend - index >= decimalNumber + 1) {
                        return "";
                    }

                }
                return null;
            }
        }});

        cetCabetelPrice.addTextChangedListener(new TextWatcher() {
            String temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > DECIMAL_DIGITS) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + DECIMAL_DIGITS + 1);
                        cetCabetelPrice.setText(s);
                        cetCabetelPrice.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    cetCabetelPrice.setText(s);
                    cetCabetelPrice.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        cetCabetelPrice.setText(s.subSequence(0, 1));
                        cetCabetelPrice.setSelection(1);
                        return;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick({R.id.lin_left, R.id.lin_right, R.id.tv_indexNext})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.lin_left:
                UIUtils.closeInputMethod(this, cetCabetelPrice);
                finish();
                break;
            case R.id.lin_right:
                startActivity(new Intent(this, WiredHistoryActivity.class));
                UIUtils.closeInputMethod(this, cetCabetelPrice);
                break;
            case R.id.tv_indexNext:

                if (cetCabetelID.getText().toString().trim().equals("")) {
                    SmartToast.showInfo("请输入有线电视卡号");
                } else if (cetCabetelName.getText().toString().trim().equals("")) {
                    SmartToast.showInfo("请输入您的姓名");
                } else if (cetCabetelPrice.getText().toString().trim().equals("")) {
                    SmartToast.showInfo("请输入缴费金额");
                } else if (Double.parseDouble(cetCabetelPrice.getText().toString()) == 0) {
                    SmartToast.showInfo("缴费金额不能是0元");
                } else {
                    getSubmit();
                }

                break;
        }
    }

    private void getSubmit() {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("c_id", prefrenceUtil.getXiaoQuId());
        params.addBodyParameter("c_name", prefrenceUtil.getXiaoQuNanme());
        params.addBodyParameter("fullname", cetCabetelName.getText().toString().trim());
        params.addBodyParameter("wired_num", cetCabetelID.getText().toString().trim());
        //params.addBodyParameter("wired_num","8354002344334470");
        params.addBodyParameter("amount", cetCabetelPrice.getText().toString().trim());

        System.out.println("c_id==" + prefrenceUtil.getXiaoQuId() + "/n c_name==" + prefrenceUtil.getXiaoQuNanme()
                + "/n fullname==" + cetCabetelName.getText().toString().trim() + "/n wired_num==" + cetCabetelID.getText().toString().trim()
                + "/n amount==" + cetCabetelPrice.getText().toString().trim());
        HttpHelper hh = new HttpHelper(info.add_wired_order, params, this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(json);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        mWiredBean = mProtocol.getsubmit(json);

                        Intent intent = new Intent(WiredIndexActivity.this, WiredConfirmActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("mWiredBean", mWiredBean);
                        bundle.putString("wiredlID", cetCabetelID.getText().toString().trim());
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 1);

                    } else {
                        SmartToast.showInfo(jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case 1://确认订单页直接返回掉回调接口
                String typeCB = data.getExtras().getString("typeCallback");
                String o_idCB = data.getExtras().getString("o_idCallback");
                if (!StringUtils.isEmpty(typeCB) && !StringUtils.isEmpty(o_idCB)) {
                    facepayCallBack(typeCB, o_idCB);
                } else {
                    SmartToast.showInfo("回调type+o_id异常");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    ShopProtocol protocol2 = new ShopProtocol();

    private void facepayCallBack(String typebk, String oidbk) {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", oidbk);
        System.out.println("8888" + oidbk);
        System.out.println("8888" + typebk);
        if (typebk.equals("wuye")) {
            params.addBodyParameter("type", "property");// 物业的
            params.addBodyParameter("prepay", "0");
        } else if (typebk.equals("shop") || typebk.equals("shop_1")) {// shop_1
            // 是从购物流程一路付款成功的
            params.addBodyParameter("type", "shop");// 购物的
            params.addBodyParameter("prepay", "0");
        } else if (typebk.equals("Yweixiu")) {
            params.addBodyParameter("type", "service");// 维修预付款的
            params.addBodyParameter("prepay", "1");
        } else if (typebk.equals("weixiu")) {
            params.addBodyParameter("type", "service");// 维修尾款的
            params.addBodyParameter("prepay", "0");
        } else if (typebk.equals("huodong")) {
            params.addBodyParameter("type", "activity");// 活動
            params.addBodyParameter("prepay", "0");
        } else if (typebk.equals("facepay")) {
            params.addBodyParameter("type", "face");// 当面付
            params.addBodyParameter("prepay", "0");
        } else if (typebk.equals("wired")) {
            params.addBodyParameter("type", "wired");// 有线缴费
            params.addBodyParameter("prepay", "0");
        }
        new HttpHelper(info.confirm_order_payment, params,
                this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                String str = protocol2.setShop(json);
                if (str.equals("1")) {
                    SmartToast.showInfo(str);

                } else {
                    SmartToast.showInfo(str);

                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };

    }
}
