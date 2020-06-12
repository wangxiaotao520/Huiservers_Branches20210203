package com.huacheng.huiservers.demo;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

/**
 * Description: 工行支付demo
 * created by wangxiaotao
 * 2020/6/3 0003 10:44
 */
public class ICBCDemoActivity extends BaseActivity {
    @Override
    protected void initView() {
//        UnionPayReq unionPayReq = new UnionPayReq();
//        WXPayAPI.init(getApplicationContext(), WXConstants.APP_ID); //注册appid
//        WXPayAPI.getInstance().doSdk(ICBCDemoActivity.this, unionPayReq);
//        ICBCAPI.getInstance().sendReq(ICBCDemoActivity.this, unionPayReq);
//        AliPayAPI.getInstance().doSdk(ICBCDemoActivity.this, unionPayReq, new AliPayAPI.AliPayResultCallBack() {
//            @Override
//            public void onResp(String resultcode) {
//                if ("9000".equals(resultcode)) {
//                    Toast.makeText(ICBCDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                    //支付成功
//
//                } else if ("6001".equals(resultcode)) {
//                    Toast.makeText(ICBCDemoActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
//                    //支付取消
//
//                } else {
//                    Toast.makeText(ICBCDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
//                    //支付失败
//                }
//
//            }
//        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_icbc_demo;
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
}
