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
import com.huacheng.huiservers.dialog.ConfirmOrderDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ConfirmOrderBeanCommit;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.pay.chinaums.UnifyPayActivity;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.AddAddressActivity;
import com.huacheng.huiservers.ui.center.AddressListActivity;
import com.huacheng.huiservers.ui.center.CouponListActivity;
import com.huacheng.huiservers.ui.center.bean.ModelAddressList;
import com.huacheng.huiservers.ui.shop.adapter.ConfirmShopListAdapter;
import com.huacheng.huiservers.ui.shop.bean.AmountBean;
import com.huacheng.huiservers.ui.shop.bean.ConfirmBean;
import com.huacheng.huiservers.ui.shop.bean.ShopDetailBean;
import com.huacheng.huiservers.ui.shop.bean.SubmitOrderBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.ButtonUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseActivityOld implements OnClickListener, ConfirmShopListAdapter.OnClickPeisongListener {
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

    String person_address, person_name, person_mobile, person_address_id;

    ConfirmShopListAdapter adapter;
    private String coupon_price, coupon_id, coupon_name;
    private EditText edt_liuyan;
    double all_money;
    String shop_id_str, shop_cou_Amount;
    private List<ConfirmBean> mDatas = new ArrayList();//集合
    private String  address_id = "";

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.confrim_order);
        //       SetTransStatus.GetStatus(this);//系统栏默认为黑色
        prefrenceUtil = new SharePrefrenceUtil(this);
        pro = (List<SubmitOrderBean>) getIntent().getExtras().getSerializable("pro");


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
        adapter = new ConfirmShopListAdapter(ConfirmOrderActivity.this, mDatas, pro,this);
        list_order_group.setAdapter(adapter);
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
                //跳转到地址列表
                intent = new Intent(this, AddressListActivity.class);
                intent.putExtra("jump_type",1);
                startActivityForResult(intent, 200);
                break;
            case R.id.lin_noadress:
                intent = new Intent(this, AddAddressActivity.class);
                intent.putExtra("jump_type",1);
                startActivityForResult(intent,111);
                break;
            case R.id.lin_jiesuan://立即支付
                if (ButtonUtils.isFastDoubleClick(R.id.lin_jiesuan)) {
                    break;
                }

                for (int i = 0; i < mDatas.size(); i++) {
                    if (mDatas.get(i).getDelivers().size()==0){
                        SmartToast.showInfo("收货地址不能为空");
                        return;
                    }
                }
                if (lin_noadress.getVisibility() == View.VISIBLE || TextUtils.isEmpty(txt_address.getText().toString()) || TextUtils.isEmpty(txt_mobile.getText().toString())
                        || TextUtils.isEmpty(txt_name.getText().toString())) {
                    SmartToast.showInfo("收货地址不能为空");

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
      //  params.addBodyParameter("para_amount", allPrice);
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){
            params.addBodyParameter("m_id", prefrenceUtil.getXiaoQuId());
        }
        params.addBodyParameter("products", gson.toJson(pro) + "");
        if (!NullUtil.isStringEmpty(address_id)){
            params.addBodyParameter("address_id", address_id);
        }
        HttpHelper hh = new HttpHelper(info.submit_order_before, params, ConfirmOrderActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
              //  bean = protocol.getShopOrder(json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");
                    bean = (ShopDetailBean) JsonUtil.getInstance().parseJson(jsonObject.getString("data"), ShopDetailBean.class);
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
//                            txt_peisongmoney.setText("¥" + bean.getSend_amount());
//                            txt_fenpei.setText("您的包裹将分成" + bean.getPro_num() + "个包裹配送给您");

                            if (bean.getAmount() != null && !TextUtils.isEmpty(bean.getAmount())) {
                                all_money = Double.parseDouble(bean.getAmount());
                                txt_all_money.setText("¥" + bean.getAmount());
                            } else {
                                txt_all_money.setText("¥0");
                                all_money = 0.00;
                            }
                            shop_id_str = bean.getShop_id_str();
                            shop_cou_Amount = bean.getAmount();

                            if ("1".equals(bean.getIs_coupon())) {
                                txt_youhuiquan.setText("选择使用优惠券");
                                txt_youhuiquan.setOnClickListener(ConfirmOrderActivity.this);
                            } else {
                                txt_youhuiquan.setText("暂无可用优惠券");
                            }
                            //获取选中
                            for (int i = 0; i < bean.getPro_data().size(); i++) {
                                //配送方式一定不能为空
                                if (bean.getPro_data().get(i).getDelivers()!=null&&bean.getPro_data().get(i).getDelivers().size()>0){
                                    for (int i1 = 0; i1 < bean.getPro_data().get(i).getDelivers().size(); i1++) {
                                        if (bean.getPro_data().get(i).getDelivers().get(i1).getIs_default()==1){//只有一个是默认
                                            ConfirmBean.DeliversBean deliversBean_selectd = new ConfirmBean.DeliversBean();
                                            deliversBean_selectd.setDis_fee(bean.getPro_data().get(i).getDelivers().get(i1).getDis_fee());
                                            deliversBean_selectd.setIs_default(bean.getPro_data().get(i).getDelivers().get(i1).getIs_default());
                                            deliversBean_selectd.setName(bean.getPro_data().get(i).getDelivers().get(i1).getName());
                                            deliversBean_selectd.setSign(bean.getPro_data().get(i).getDelivers().get(i1).getSign());
                                            //设置选中的bean  到时候配送方式从这里取数据
                                            bean.getPro_data().get(i).setDeliversBean_selected(deliversBean_selectd);
                                        }
                                    }
                                }

                            }
                            mDatas.clear();
                            mDatas.addAll(bean.getPro_data());
                            adapter.notifyDataSetChanged();
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
        if (!NullUtil.isStringEmpty(coupon_id)){
            params.addBodyParameter("m_c_id", coupon_id + "");
            params.addBodyParameter("m_c_name", coupon_name + "");
            params.addBodyParameter("m_c_amount", coupon_price + "");
        }

        params.addBodyParameter("description", edt_liuyan.getText().toString() + "");
        String type = "";//商户1id_方式_配送费|商户2id_方式_配送费|商户3id_方式_配送费
        for (int i = 0; i < mDatas.size(); i++) {
            if (i< mDatas.size()-1){
                type=type+mDatas.get(i).getMerchant_id()+"_"+mDatas.get(i).getDeliversBean_selected().getSign()+"_"+mDatas.get(i).getDeliversBean_selected().getDis_fee()+"|";
            }else {
                type=type+mDatas.get(i).getMerchant_id()+"_"+mDatas.get(i).getDeliversBean_selected().getSign()+"_"+mDatas.get(i).getDeliversBean_selected().getDis_fee()+"";
            }
        }
        params.addBodyParameter("type", type+ "");
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
                            EventBus.getDefault().post(new ConfirmBean());
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
                case RESULT_OK:
                    if (requestCode==200){
                        //选择地址返回
                        if (data!=null){
                            ModelAddressList model = (ModelAddressList) data.getSerializableExtra("model");
                            person_name=model.getConsignee_name();
                            person_mobile=model.getConsignee_mobile();
                            person_address=model.getRegion_cn()+" "+model.getCommunity_cn()+" "+model.getDoorplate();
                            lin_yesaddress.setVisibility(View.VISIBLE);
                            lin_noadress.setVisibility(View.GONE);
                            txt_name.setText(person_name);
                            txt_address.setText(person_address);
                            txt_mobile.setText(person_mobile);
                            address_id=model.getId()+"";
                            getShopOrder();
                        }
                    }else if (requestCode==111){
                       //新增地址返回
                        if (data!=null){
                            ModelAddressList model = (ModelAddressList) data.getSerializableExtra("model");
                            person_name=model.getConsignee_name();
                            person_mobile=model.getConsignee_mobile();
                            person_address=model.getRegion_cn()+model.getCommunity_cn()+model.getDoorplate();
                            lin_yesaddress.setVisibility(View.VISIBLE);
                            lin_noadress.setVisibility(View.GONE);
                            txt_name.setText(person_name);
                            txt_address.setText(person_address);
                            txt_mobile.setText(person_mobile);
                            address_id=model.getId()+"";
                            getShopOrder();

                        }
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

    @Override
    public void onClickPeisong(int position) {
        //position 是当前商品listview的位置
        //点击选择配送方式
        if (mDatas.size()==0) {
            return;
        }
        if (mDatas.get(position).getDelivers()==null||mDatas.get(position).getDelivers().size()==0) {
            SmartToast.showInfo("收货地址不能为空");
            return;
        }
        //所有的配送方式
        final List<ConfirmBean.DeliversBean> delivers = mDatas.get(position).getDelivers();
        //当前选中的配送方式
        ConfirmBean.DeliversBean deliversBean_selected = mDatas.get(position).getDeliversBean_selected();

        new ConfirmOrderDialog(this, position, delivers, deliversBean_selected, new ConfirmOrderDialog.OnClickDiliverItemListener() {
            @Override
            public void onClickDiliverItem(int listview_position, int diliver_position) {
                ConfirmBean confirmBean = mDatas.get(listview_position);
                if (diliver_position<mDatas.get(listview_position).getDelivers().size()){
                    //取出选中的bean
                    ConfirmBean.DeliversBean deliversBean_selected = mDatas.get(listview_position).getDelivers().get(diliver_position);
                    //设置给选中
                    ConfirmBean.DeliversBean deliversBean_selected_set = new ConfirmBean.DeliversBean();
                    deliversBean_selected_set.setSign(deliversBean_selected.getSign());
                    deliversBean_selected_set.setName(deliversBean_selected.getName());
                    deliversBean_selected_set.setIs_default(deliversBean_selected.getIs_default());
                    deliversBean_selected_set.setDis_fee(deliversBean_selected.getDis_fee());
                    confirmBean.setDeliversBean_selected(deliversBean_selected_set);
                    //刷新adapter
                    adapter.notifyDataSetChanged();
                }
                //请求总价 拼成一个json
                String commit_json = "";
                ArrayList<ConfirmOrderBeanCommit> confirmOrderBeanCommits_list = new ArrayList<>();
                for (int i = 0; i < mDatas.size(); i++) {
                    ConfirmOrderBeanCommit confirmOrderBeanCommit = new ConfirmOrderBeanCommit();
                    confirmOrderBeanCommit.setMerchant_id(mDatas.get(i).getMerchant_id()+"");
                    confirmOrderBeanCommit.setHalf_amount(mDatas.get(i).getHalf_amount()+"");
                    confirmOrderBeanCommit.setSign(mDatas.get(i).getDeliversBean_selected().getSign());
                    confirmOrderBeanCommit.setDis_fee(mDatas.get(i).getDeliversBean_selected().getDis_fee());
                    confirmOrderBeanCommits_list.add(confirmOrderBeanCommit);
                }
                Gson gson = new Gson();
                commit_json= gson.toJson(confirmOrderBeanCommits_list);
                requestBillAllMoney(commit_json);
            }


        }).show();

    }

    /**
     * 请求总价接口
     * @param json
     */
    private void requestBillAllMoney(String json) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(json)){
            params.put("merchant_str",json+"");
        }
        MyOkHttp.get().post(ApiHttpClient.SET_ORDER_AMOUNT, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    AmountBean bean = (AmountBean) JsonUtil.getInstance().parseJsonFromResponse(response, AmountBean.class);
                    txt_all_money.setText("¥" + bean.getAmount());
                    all_money=Double.parseDouble(bean.getAmount());
                    setFloat();
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
}
