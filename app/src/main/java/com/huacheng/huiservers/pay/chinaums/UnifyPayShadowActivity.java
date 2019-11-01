package com.huacheng.huiservers.pay.chinaums;

import android.content.Intent;
import android.support.annotation.Nullable;

import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description: 支付shadow
 * created by wangxiaotao
 * 2019/10/31 0031 下午 5:22
 */
public class UnifyPayShadowActivity extends BaseActivity{
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
    private String appPayRequest="";
    private boolean isFirstIn = true;

    @Override
    protected void onStart() {
        super.onStart();
            //尽力了
        if (!isFirstIn){
            if (isGotoPayback&&typetag==TYPE_ALIPAY){
                isGotoPayback=false;
                //注：支付宝渠道如果支付请求发送成功，则会跳转至支付宝APP，
                // 并且支付完成后会停留在支付宝，因此商户 APP无法通过UnifyPayListener
                // 收到支付结果，请以后台的支付结果为准。

                Intent intent = new Intent();
                intent.putExtra("typetag",TYPE_ALIPAY);
                setResult(RESULT_OK,intent);
                finish();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstIn&&typetag==TYPE_ALIPAY) {
            isFirstIn=false;
            showDialog(smallDialog);
            payAliPay(appPayRequest);
        }else {
            //处理支付宝调用过程那个间隙一直按屏幕的问题，这个时候支付宝有可能就调不起来,让页面退回去
            if (isGotoPayback&&typetag==TYPE_ALIPAY){
            isGotoPayback=false;
            setResult(RESULT_OK);
            finish();
       }
        }

    }
    @Override
    protected void initView() {
        smallDialog.setCanceledOnTouchOutside(false);
        this. appPayRequest = getIntent().getStringExtra("appPayRequest");
        this. typetag = getIntent().getIntExtra("typetag",1);
        if (typetag == TYPE_ALIPAY) {
            //   SmartToast.showInfo("alipay");


        }else if (typetag==TYPE_CLOUD_QUICK_PAY){
            showDialog(smallDialog);
            payCloudQuickPay(appPayRequest);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_unify_pay_shadow;
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
        isGotoPayback=true;
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
    protected void onStop() {
        super.onStop();
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
            if (!NullUtil.isStringEmpty(str)&&typetag==TYPE_CLOUD_QUICK_PAY){
                Intent intent = new Intent();
                intent.putExtra("typetag",TYPE_CLOUD_QUICK_PAY);
                intent.putExtra("pay_result",str);
                setResult(RESULT_OK,intent);

            }
        finish();
    }
}
