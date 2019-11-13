package com.huacheng.huiservers.ui.shop;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.pay.chinaums.UnifyPayActivity;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.CouponListActivity;
import com.huacheng.huiservers.ui.center.AddressListActivity;
import com.huacheng.huiservers.ui.shop.adapter.ConfirmShopListAdapter;
import com.huacheng.huiservers.ui.shop.bean.ShopDetailBean;
import com.huacheng.huiservers.ui.shop.bean.SubmitOrderBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.ButtonUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseActivityOld implements OnClickListener {
    private LinearLayout lin_left, lin_jiesuan, lin_noadress, lin_yesaddress;
    private RelativeLayout title_rel;
    private TextView title_name, txt_address, txt_name, txt_mobile, txt_all_money, txt_fenpei,
            txt_peisongmoney, txt_youhuiquan;
    ShopProtocol protocol = new ShopProtocol();
    ShopDetailBean beanzhifu = new ShopDetailBean();
    ShopDetailBean bean = new ShopDetailBean();
    List<SubmitOrderBean> pro = new ArrayList<SubmitOrderBean>();
    private MyListView list_order_group;
    SharePrefrenceUtil prefrenceUtil;
    private StringBuilder sb;
    private String allPrice;
    private List<String> list_id = new ArrayList<String>();
    String person_address, person_name, person_mobile, person_address_id;
    public static ConfirmOrderActivity instant;
    private List<String> strlist = new ArrayList<String>();
    ConfirmShopListAdapter adapter;
    private String coupon_price, coupon_id, coupon_name;
    private EditText edt_liuyan;
    double all_money;
    String shop_id_str, shop_cou_Amount;

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.confrim_order);
        //       SetTransStatus.GetStatus(this);//系统栏默认为黑色
        prefrenceUtil = new SharePrefrenceUtil(this);
        pro = (List<SubmitOrderBean>) getIntent().getExtras().getSerializable("pro");
        allPrice = getIntent().getExtras().getString("all");

        lin_left = (LinearLayout) findViewById(R.id.lin_left);
        lin_left.setOnClickListener(this);
        title_name = (TextView) findViewById(R.id.title_name);
        title_name.setText("确认订单");

        //地址
        lin_yesaddress = (LinearLayout) findViewById(R.id.lin_yesaddress);
        lin_noadress = (LinearLayout) findViewById(R.id.lin_noadress);
        txt_address = (TextView) findViewById(R.id.txt_address);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_mobile = (TextView) findViewById(R.id.txt_mobile);
        //分配
        txt_fenpei = (TextView) findViewById(R.id.txt_fenpei);
        txt_youhuiquan = (TextView) findViewById(R.id.txt_youhuiquan);
        list_order_group = (MyListView) findViewById(R.id.list_order_group);
        txt_peisongmoney = (TextView) findViewById(R.id.txt_peisongmoney);
        edt_liuyan = (EditText) findViewById(R.id.edt_liuyan);
        //底部栏
        lin_jiesuan = (LinearLayout) findViewById(R.id.lin_jiesuan);
        lin_jiesuan.setOnClickListener(this);
        txt_all_money = (TextView) findViewById(R.id.txt_all_money);
        lin_noadress.setOnClickListener(this);
        lin_yesaddress.setOnClickListener(this);
        smallDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initData() {
        super.initData();
        getShopOrder();
    }

    @Override
    public void onClick(View arg0) {
        Intent intent;
        Bundle bundle = new Bundle();
        switch (arg0.getId()) {

            case R.id.lin_left://返回
                finish();
                break;

            case R.id.lin_yesaddress:
                intent = new Intent(this, AddressListActivity.class);
                bundle.putString("address", "shopyes");
                bundle.putString("type", "order");
                bundle.putStringArrayList("list_id", new ArrayList<String>(list_id));
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
                break;
            case R.id.lin_noadress:
                intent = new Intent(this, AddressListActivity.class);
                bundle.putString("address", "shopyes");
                bundle.putString("type", "order");
                bundle.putStringArrayList("list_id", new ArrayList<String>(list_id));
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
                break;
            case R.id.lin_jiesuan://立即支付
                if (ButtonUtils.isFastDoubleClick(R.id.lin_jiesuan)) {
                    break;
                }
                strlist.clear();
                for (int i = 0; i < MyCookieStore.Confirmlist.size(); i++) {
                    String str = MyCookieStore.Confirmlist.get(i).getId() + "." + MyCookieStore.Confirmlist.get(i).getStyle();
                    strlist.add(str);
                }
                sb = new StringBuilder();
                for (int i = 0; i < strlist.size(); i++) {
                    if (i == 0) {
                        sb.append(String.valueOf(strlist.get(i)));
                    } else {
                        sb.append("|" + String.valueOf(strlist.get(i)));
                    }
                }
                if (lin_noadress.getVisibility() == View.VISIBLE || TextUtils.isEmpty(txt_address.getText().toString()) || TextUtils.isEmpty(txt_mobile.getText().toString())
                        || TextUtils.isEmpty(txt_name.getText().toString())) {
                    SmartToast.showInfo("地址不能为空");

                } else {

                    if (txt_youhuiquan.getText().toString().equals("选择使用优惠券")) {

                        new CommomDialog(this, R.style.my_dialog_DimEnabled, "您有可使用的优惠券，是否使用？", new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    Intent intent = new Intent(ConfirmOrderActivity.this, CouponListActivity.class);//CouponListActivity
                                    Bundle bundle = new Bundle();
                                    bundle.putString("tag", "order");
                                    bundle.putString("all_id", shop_id_str);
                                    bundle.putString("all_shop_money", shop_cou_Amount);
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, 100);
                                    dialog.dismiss();
                                } else {
                                    dialog.dismiss();
                                    getShopconfirm();//立即生成支付订单
                                }
                            }
                        }).show();//.setTitle("提示")
                    } else {

                        getShopconfirm();//立即生成支付订单

                    }
                }
                break;

            case R.id.txt_youhuiquan://选择使用优惠券
                intent = new Intent(this, CouponListActivity.class);//CouponListActivity
                bundle.putString("tag", "order");
                bundle.putString("all_id", shop_id_str);
                bundle.putString("all_shop_money", shop_cou_Amount);
                intent.putExtras(bundle);
                startActivityForResult(intent, 100);
                break;
            default:
                break;
        }
    }


    private void getShopOrder() {//确认订单信息
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        Gson gson = new Gson();
        gson.toJson(pro);

        RequestParams params = new RequestParams();
        params.addBodyParameter("para_amount", allPrice);
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){
            params.addBodyParameter("m_id", prefrenceUtil.getXiaoQuId());
        }
        params.addBodyParameter("products", gson.toJson(pro) + "");
        HttpHelper hh = new HttpHelper(info.submit_order_before, params, ConfirmOrderActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                bean = protocol.getShopOrder(json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");
                    if ("1".equals(status)) {
                        if (bean != null) {
                            if (TextUtils.isEmpty(bean.getContact()) && TextUtils.isEmpty(bean.getMobile()) &&
                                    TextUtils.isEmpty(bean.getAddress())) {
                                lin_noadress.setVisibility(View.VISIBLE);
                                lin_yesaddress.setVisibility(View.GONE);
                            } else {
                                lin_noadress.setVisibility(View.GONE);
                                lin_yesaddress.setVisibility(View.VISIBLE);
                                txt_address.setText(bean.getAddress());
                                person_address_id = bean.getAddress_id();
                                txt_name.setText(bean.getContact());
                                txt_mobile.setText(bean.getMobile());
                            }
                            txt_peisongmoney.setText("¥" + bean.getSend_amount());
                            txt_fenpei.setText("您的包裹将分成" + bean.getPro_num() + "个包裹配送给您");


                            if (bean.getAmount() != null && !TextUtils.isEmpty(bean.getAmount())) {
                                all_money = Double.parseDouble(bean.getAmount());
                                txt_all_money.setText("¥" + bean.getAmount());
                            } else {
                                txt_all_money.setText("¥0");
                                all_money = 0.00;
                            }
                            shop_id_str = bean.getShop_id_str();
                            shop_cou_Amount = bean.getAmount();

                            if (bean.getIs_coupon().equals("1")) {
                                txt_youhuiquan.setText("选择使用优惠券");
                                txt_youhuiquan.setOnClickListener(ConfirmOrderActivity.this);
                            } else {
                                txt_youhuiquan.setText("暂无可用优惠券");
                            }
                            for (int i = 0; i < bean.getPro_data().size(); i++) {
                                String str = bean.getPro_data().get(i).getMerchant_id();
                                list_id.add(str);
                            }
                            adapter = new ConfirmShopListAdapter(ConfirmOrderActivity.this, bean.getPro_data(),
                                    pro);
                            list_order_group.setAdapter(adapter);
                        }
                    } else {
                        SmartToast.showInfo(msg);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1500);
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

    private void getShopconfirm() {//立即支付
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        Gson gson = new Gson();
        gson.toJson(pro);
        RequestParams params = new RequestParams();
        params.addBodyParameter("address_id", person_address_id + "");
        params.addBodyParameter("address", txt_address.getText().toString() + "");
        params.addBodyParameter("contact", txt_name.getText().toString() + "");
        params.addBodyParameter("mobile", txt_mobile.getText().toString() + "");
        params.addBodyParameter("m_c_id", coupon_id + "");
        params.addBodyParameter("m_c_name", coupon_name + "");
        params.addBodyParameter("m_c_amount", coupon_price + "");
        params.addBodyParameter("description", edt_liuyan.getText().toString() + "");
        params.addBodyParameter("type", sb.toString() + "");
        params.addBodyParameter("products", gson.toJson(pro) + "");
        HttpHelper hh = new HttpHelper(info.submit_order, params, ConfirmOrderActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                if (!NullUtil.isStringEmpty(json)) {
                    try {
                        JSONObject response = new JSONObject(json);
                        if (JsonUtil.getInstance().isSuccess(response)) {
                            beanzhifu = protocol.getShopConfirm(json);
                            Intent intent = new Intent(ConfirmOrderActivity.this, UnifyPayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("o_id", beanzhifu.getOrder_id());
                            //bundle.putString("coupon_id", coupon_id);
                            bundle.putString("price", all_money + "");
                            bundle.putString("type", "shop_1");
                            bundle.putString("order_type", "gw");
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        } else {
                            String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交失败");
                            SmartToast.showInfo(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        SmartToast.showInfo("提交失败");
                    }


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
        switch (resultCode) {
            case 200://地址返回
                person_name = data.getExtras().getString("person_name");
                person_mobile = data.getExtras().getString("person_mobile");
                person_address = data.getExtras().getString("address");
                person_address_id = data.getExtras().getString("address_id");
                if (!person_address.equals("")) {
                    lin_yesaddress.setVisibility(View.VISIBLE);
                    lin_noadress.setVisibility(View.GONE);
                    txt_address.setText(person_address);
                    txt_mobile.setText(person_mobile);
                    txt_name.setText(person_name);
                }
                break;
            case 100://优惠券返回
                coupon_price = data.getExtras().getString("coupon_price");
                coupon_id = data.getExtras().getString("coupon_id");
                coupon_name = data.getExtras().getString("coupon_name");
                txt_youhuiquan.setText(coupon_name);
                System.out.println("--------" + coupon_name);
                System.out.println("parseDouble======" + Double.parseDouble(bean.getAmount()));
                System.out.println("parseDouble======" + Double.parseDouble(coupon_price));
                Double double1 = Double.parseDouble(bean.getAmount());
                Double double2 = Double.parseDouble(coupon_price);
                all_money = double1 - double2;
                setFloat();
                //txt_youhuiquan.setText("使用le优惠券");
                txt_all_money.setText("¥" + all_money);
                break;
            case 10:
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setFloat() {//保留两位小数点
        BigDecimal b = new BigDecimal(all_money);
        all_money = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
