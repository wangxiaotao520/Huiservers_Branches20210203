package com.huacheng.huiservers.pay.chinaums;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.EventBusWorkOrderModel;
import com.huacheng.huiservers.model.EventModelVip;
import com.huacheng.huiservers.model.PayTypeBean;
import com.huacheng.huiservers.model.XorderDetailBean;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.charge.ChargingActivity;
import com.huacheng.huiservers.ui.index.property.PropertyPaymentActivity;
import com.huacheng.huiservers.ui.index.property.bean.EventProperty;
import com.huacheng.huiservers.ui.servicenew.ui.order.JpushPresenter;
import com.huacheng.huiservers.ui.servicenew1.ServiceOrderDetailNew;
import com.huacheng.huiservers.ui.shop.ShopOrderListActivityNew;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.NullUtil;
import com.stx.xhb.xbanner.OnDoubleClickListener;
import com.unionpay.UPPayAssistEx;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Description: 支付结果 支付等待页
 * created by wangxiaotao
 * 2019/11/27 0027 上午 8:25
 */
public class UnifyPayResultActivity extends BaseActivity implements OnUnifyPayListener {
    private UnifyPayPresenter payPresenter;
    private ImageView iv_status_img;
    private TextView tv_status;
    private TextView tv_price;
    private TextView tv_pay_type;
    private TextView tv_confirm;

    private boolean isGotoPayback = false;//阿里的支付调用了
    private int typetag = 1;
    /**
     * 微信支付
     */
    private final int TYPE_WEIXIN = 3;
    /**
     * 支付宝支付
     */
    private final int TYPE_ALIPAY = 1;
    /**
     * 云闪付
     */
    private final int TYPE_CLOUD_QUICK_PAY = 2;
    /**
     * 积分支付
     */
    private final int TYPE_POINTS = 2;

    private String appPayRequest = "";
    private String type = "";
    private String order_id = "";
    private String price = "";

    private boolean isFirstIn = true;
    private int request_count=0;
    private int pay_result_type = 1;//1支付中.. 2.支付成功 3.支付失败

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //尽力了
        if (!isFirstIn) {
            if (isGotoPayback && typetag == TYPE_ALIPAY) {
              //  isGotoPayback = false;
                //注：支付宝渠道如果支付请求发送成功，则会跳转至支付宝APP，
                // 并且支付完成后会停留在支付宝，因此商户 APP无法通过UnifyPayListener
                // 收到支付结果，请以后台的支付结果为准。
                tv_status.setText("等待支付结果");
                iv_status_img.setBackgroundResource(R.mipmap.ic_pay_waiting);
                request_count=0;
                tv_confirm.setVisibility(View.GONE);
                payResultCallBack();
            }
        } else {
            if (typetag == TYPE_ALIPAY) {
                isFirstIn = false;
                showDialog(smallDialog);
                payAliPay(appPayRequest);
            }
        }

    }

    @Override
    protected void initView() {
        payPresenter = new UnifyPayPresenter(this, this);
        iv_status_img = findViewById(R.id.iv_status_img);
        tv_status = findViewById(R.id.tv_status);
        tv_price = findViewById(R.id.tv_price);
        tv_pay_type = findViewById(R.id.tv_pay_type);
        tv_confirm = findViewById(R.id.tv_confirm);
        tv_confirm.setVisibility(View.GONE);

        smallDialog.setCanceledOnTouchOutside(false);
        this.appPayRequest = getIntent().getStringExtra("appPayRequest");
        this.typetag = getIntent().getIntExtra("typetag", 1);
        this.type = this.getIntent().getExtras().getString("type") + "";
        this.order_id=this.getIntent().getExtras().getString("o_id") + "";
        this.price = this.getIntent().getExtras().getString("price");
        tv_status.setText("支付中...");
        iv_status_img.setBackgroundResource(R.mipmap.ic_pay_waiting);
        tv_price.setText(price+"");


        if (typetag == TYPE_ALIPAY) {
            //   SmartToast.showInfo("alipay");
            tv_pay_type.setText("支付宝");

        } else if (typetag == TYPE_CLOUD_QUICK_PAY) {
            //  showDialog(smallDialog);
            tv_pay_type.setText("云闪付");
            payCloudQuickPay(appPayRequest);
        }else if (typetag == TYPE_POINTS){
            //Todo
            tv_pay_type.setText("积分");
        }

    }

    /**
     * 支付宝
     *
     * @param params
     */
    private void payAliPay(String params) {
        UnifyPayRequest msg = new UnifyPayRequest();
        msg.payChannel = UnifyPayRequest.CHANNEL_ALIPAY;
        msg.payData = params;
        UnifyPayPlugin.getInstance(this).sendPayRequest(msg);
        isGotoPayback = true;
        //    hideDialog(smallDialog);
    }

    /**
     * 云闪付
     *
     * @param appPayRequest
     */
    private void payCloudQuickPay(String appPayRequest) {
        String tn = "";
        try {
            JSONObject e = new JSONObject(appPayRequest);
            tn = e.getString("tn");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        /**
         * activity —— 用于启动支付控件的活动对象
         * spId —— 保留使用，这里输入null
         * sysProvider —— 保留使用，这里输入null
         * orderInfo —— 订单信息为交易流水号，即TN，为商户后台从银联后台获取。
         * mode —— 银联后台环境标识，“00”将在银联正式环境发起交易,“01”将在银联测试环境发起交易
         */
        UPPayAssistEx.startPay(this, null, null, tn, "00");
        //     hideDialog(smallDialog);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideDialog(smallDialog);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_confirm.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (pay_result_type==1){
                    return;//支付中
                }else if (pay_result_type==3){
                    //支付失败
                    if (type.equals(CanstantPay.PAY_SHOP_CONFIRM_ORDER)){
                        Intent intent = new Intent(mContext, ShopOrderListActivityNew.class);
                        startActivity(intent);
                    }
                    finish();
                }else if (pay_result_type==2){
                    //支付成功

                    if (type.equals(CanstantPay.PAY_SHOP_CONFIRM_ORDER)){
                        // 支付成功后判断优惠券id不为空的话请求优惠券接口

                Intent intent = new Intent(mContext,
                        ShopOrderListActivityNew.class);
                startActivity(intent);
                // 支付完成后finish掉购物车页 立即支付页
                finish();
                    }else if (type.equals(CanstantPay.PAY_SHOP_ORDER_DETAIL)){
                        XorderDetailBean XorderDetail = new XorderDetailBean();
                        XorderDetail.setId(order_id);
                        XorderDetail.setBack_type(4);
                        EventBus.getDefault().post(XorderDetail);
                        finish();
                    }else if(type.equals(CanstantPay.PAY_SERVICE)){
                        Intent intent = new Intent(mContext, ServiceOrderDetailNew.class);
                        intent.putExtra("order_id", order_id);
                        intent.putExtra("jump_type", 1);
                        startActivity(intent);
                // 支付成功后调用极光
                new JpushPresenter().paySuccessJpush(order_id);
                finish();

                    }else if(type.equals(CanstantPay.PAY_PROPERTY)) {
                EventBus.getDefault().post(new EventProperty());
                Intent intent = new Intent(mContext, PropertyPaymentActivity.class);
                startActivity(intent);
                finish();
                    }else if(type.equals(CanstantPay.PAY_WORKORDER)){//工单
                EventBusWorkOrderModel eventBusModel = new EventBusWorkOrderModel();
                eventBusModel.setWork_id(order_id);
                eventBusModel.setEvent_back_type(2);
                EventBus.getDefault().post(eventBusModel);
                finish();
                    }else if(type.equals(CanstantPay.PAY_HUODONG)){

                    }else if(type.equals(CanstantPay.PAY_FACE)){

                    }else if(type.equals(CanstantPay.PAY_CHARGE_YX)){
                Intent intent = new Intent(mContext, ChargingActivity.class);
                intent.putExtra("jump_from","pay");
                intent.putExtra("id",order_id);
                startActivity(intent);
                finish();

                    }else if (type.equals(CanstantPay.PAY_VIP)){
                        EventBus.getDefault().post(new EventModelVip());
                        finish();
                    }
                }

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_unify_pay_result;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         //             * 处理银联云闪付手机支付控件返回的支付结果
         //             */
        if (data == null) {
            return;
        }
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        /**
         * 处理银联云闪付手机支付控件返回的支付结果
         */
        if (!NullUtil.isStringEmpty(str) && typetag == TYPE_CLOUD_QUICK_PAY) {
            if (str.equalsIgnoreCase("success")) {
                //如果想对结果数据校验确认，直接去商户后台查询交易结果，
                //校验支付结果需要用到的参数有sign、data、mode(测试或生产)，sign和data可以在result_data获取到
                /**
                 * result_data参数说明：
                 * sign —— 签名后做Base64的数据
                 * data —— 用于签名的原始数据
                 *      data中原始数据结构：
                 *      pay_result —— 支付结果success，fail，cancel
                 *      tn —— 订单号
                 */
                msg = "云闪付支付成功";
                //请求本地服务器
                tv_status.setText("等待支付结果");
                payResultCallBack();
            } else if (str.equalsIgnoreCase("fail")) {
                msg = "云闪付支付失败！";
          //      SmartToast.showInfo("支付失败");
                pay_result_type=3;
                tv_confirm.setVisibility(View.VISIBLE);
                iv_status_img.setBackgroundResource(R.mipmap.ic_fail_pay);
                tv_status.setText("支付失败");
                tv_confirm.setText("重新支付");
            } else if (str.equalsIgnoreCase("cancel")) {
                msg = "用户取消了云闪付支付";
                pay_result_type=3;
                tv_confirm.setVisibility(View.VISIBLE);
                iv_status_img.setBackgroundResource(R.mipmap.ic_fail_pay);
                tv_status.setText("支付失败");
                tv_confirm.setText("重新支付");
            }
        }
    }


    /**
     * 回调接口
     */
    private void payResultCallBack() {
        if (payPresenter != null) {
            String type_params = "";
            if (type.equals(CanstantPay.PAY_SHOP_CONFIRM_ORDER) || type.equals(CanstantPay.PAY_SHOP_ORDER_DETAIL)) {
                type_params = "shop";
            } else if (type.equals(CanstantPay.PAY_SERVICE)) {
                type_params = "serve";
            } else if (type.equals(CanstantPay.PAY_PROPERTY)) {
                type_params = "property";
            } else if (type.equals(CanstantPay.PAY_WORKORDER)) {
                type_params = "work";
            } else if (type.equals(CanstantPay.PAY_HUODONG)) {
                type_params = "activity";
            } else if (type.equals(CanstantPay.PAY_FACE)) {
                type_params = "face";
            } else if (type.equals(CanstantPay.PAY_CHARGE_YX)) {
                type_params = "yxcd";
            }else if (type.equals(CanstantPay.PAY_VIP)) {
                type_params = "vip";
            }
            showDialog(smallDialog);
            payPresenter.confirmOrderPayment(order_id, type_params);
        }
    }

    @Override
    public void onGetPayTypeDatas(int status, String msg, List<PayTypeBean> mDatas) {

    }

    @Override
    public void onGetOrderInformation(int status, String msg, String json, int typetag) {

    }

    @Override
    public void onGetOrderResult(int status, String msg, String json, final String type_params) {
        // 本地服务器的回调
        if (status==1){
            request_count=0;
            hideDialog(smallDialog);
            isGotoPayback = false;
            tv_confirm.setText("确定");
            if (type.equals(CanstantPay.PAY_SHOP_CONFIRM_ORDER)){
                getWuLiu();// 物流分配
                getPush("2");// 推送接口
                pay_result_type=2;
                tv_confirm.setVisibility(View.VISIBLE);
                iv_status_img.setBackgroundResource(R.mipmap.ic_success_pay);
                tv_status.setText("支付成功");
            }else if (type.equals(CanstantPay.PAY_SHOP_ORDER_DETAIL)){
                getWuLiu();// 物流分配
                getPush("1");// 推送接口

                tv_confirm.setVisibility(View.VISIBLE);
                pay_result_type=2;
                iv_status_img.setBackgroundResource(R.mipmap.ic_success_pay);
                tv_status.setText("支付成功");
            }else if(type.equals(CanstantPay.PAY_SERVICE)){

                tv_confirm.setVisibility(View.VISIBLE);
                pay_result_type=2;
                iv_status_img.setBackgroundResource(R.mipmap.ic_success_pay);
                tv_status.setText("支付成功");
            }else if(type.equals(CanstantPay.PAY_PROPERTY)) {

                tv_confirm.setVisibility(View.VISIBLE);
                pay_result_type=2;
                iv_status_img.setBackgroundResource(R.mipmap.ic_success_pay);
                tv_status.setText("支付成功");
            }else if(type.equals(CanstantPay.PAY_WORKORDER)){//工单

                tv_confirm.setVisibility(View.VISIBLE);
                pay_result_type=2;
                iv_status_img.setBackgroundResource(R.mipmap.ic_success_pay);
                tv_status.setText("支付成功");
            }else if(type.equals(CanstantPay.PAY_HUODONG)){

            }else if(type.equals(CanstantPay.PAY_FACE)){

            }else if(type.equals(CanstantPay.PAY_CHARGE_YX)){

                tv_confirm.setVisibility(View.VISIBLE);
                pay_result_type=2;
                iv_status_img.setBackgroundResource(R.mipmap.ic_success_pay);
                tv_status.setText("支付成功");
            }else if(type.equals(CanstantPay.PAY_VIP)){
                tv_confirm.setVisibility(View.VISIBLE);
                pay_result_type=2;
                iv_status_img.setBackgroundResource(R.mipmap.ic_success_pay);
                tv_status.setText("支付成功");
            }
   //         SmartToast.showInfo(msg);
        }else {
            //轮询服务器三次
            if (request_count>=2){
                hideDialog(smallDialog);
                request_count=0;
           //     SmartToast.showInfo(msg);
                //支付失败
                pay_result_type=3;
                tv_confirm.setVisibility(View.VISIBLE);
                iv_status_img.setBackgroundResource(R.mipmap.ic_fail_pay);
                tv_status.setText("支付失败");
                tv_confirm.setText("重新支付");
                return;
            }
            request_count++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    payPresenter.confirmOrderPayment(order_id,type_params);
                }
            },2000);
        }
    }

    // 物流分配
    protected void getWuLiu() {
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", order_id);
        MyOkHttp.get().post(info.distribution, params.getParams(), new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){

                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });

//        HttpHelper hh = new HttpHelper(info.distribution, params,
//                this) {
//
//            @Override
//            protected void setData(String json) {
//                String strWL = new ShopProtocol().setShop(json);
//                if (strWL.equals("1")) {
//                } else {
//
//                }
//            }
//
//            @Override
//            protected void requestFailure(Exception error, String msg) {
//                //  SmartToast.showInfo("网络异常，请检查网络设置");
//            }
//        };
    }

    // 购物订单推送接口
    protected void getPush(final String isStr) {
        // showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", order_id+"");

        MyOkHttp.get().post(info.merchant_push, params.getParams(), new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)){

                }

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
//        HttpHelper hh = new HttpHelper(info.merchant_push, params, this) {
//
//            @Override
//            protected void setData(String json) {
//                hideDialog(smallDialog);
//                String str_push = new ShopProtocol().setShop(json);
//                if (str_push.equals("1")) {
//
//                } else {
//                    // SmartToast.showInfo(str_push);
//                }
//            }
//
//            @Override
//            protected void requestFailure(Exception error, String msg) {
//                hideDialog(smallDialog);
//                //  SmartToast.showInfo("网络异常，请检查网络设置");
//            }
//        };
    }
}