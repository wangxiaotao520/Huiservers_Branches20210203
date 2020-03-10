package com.huacheng.huiservers.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.WXPayResultListener;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.model.EventBusWorkOrderModel;
import com.huacheng.huiservers.model.ModelEventWX;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.pay.ZhifuActivity;
import com.huacheng.huiservers.ui.index.property.bean.EventProperty;
import com.huacheng.huiservers.ui.servicenew.ui.order.FragmentOrderListActivity;
import com.huacheng.huiservers.ui.servicenew.ui.order.JpushPresenter;
import com.huacheng.huiservers.ui.shop.ShopOrderListActivityNew;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler, OnClickListener {
    private TextView title_name, txt_result;
    private Button btn;
    private LinearLayout lin_left;
    //private WXPayEntryActivity.code ClickInterface interfaces;

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    ShopProtocol protocol2 = new ShopProtocol();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
//        SetTransStatus.GetStatus(this);//系统栏默认为黑色
        title_name = (TextView) findViewById(R.id.title_name);
        txt_result = (TextView) findViewById(R.id.txt_result);
        lin_left = (LinearLayout) findViewById(R.id.lin_left);
        title_name.setText("支付结果");
        lin_left.setVisibility(View.GONE);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, ZhifuActivity.WXAPPID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {


    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {

        if (resp.errCode == 0) {
            MyCookieStore.WX_dialog = 1;
        } else if (resp.errCode == -2) {
            MyCookieStore.WX_dialog = 0;
        } else {
            MyCookieStore.WX_dialog = 0;
        }
        ModelEventWX modelEventWX = new ModelEventWX();
        modelEventWX.setType(0);
        EventBus.getDefault().post(modelEventWX);
        finish();
        // }
        // 配置微信回调到onResult
        WXPayResultListener wxListener = UnifyPayPlugin.getInstance(WXPayEntryActivity.this).getWXListener();
        wxListener.onResponse(WXPayEntryActivity.this, resp);
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.btn:
                if (MyCookieStore.type.equals("Yweixiu")) {

                }
                if (MyCookieStore.type.equals("weixiu")) {

                }
                if (MyCookieStore.type.equals("shop")) {//个人中心购物订单列表中付款成功的
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("item_detete_id", "");
                    intent.putExtras(bundle);
                    setResult(100, intent);
                }
                if (MyCookieStore.type.equals("shop_1")) {//购物车里付款成功

                }
                if (MyCookieStore.type.equals("wuye")) {
                    ZhifuActivity.sZhifu.finish();

                }
                if (MyCookieStore.type.equals("huodong")) {//活動付款成功

                }
                if (MyCookieStore.type.equals("facepay")) {//当面付
                    ZhifuActivity.sZhifu.finish();
                }
                if (MyCookieStore.type.equals("wired")) {//当面付
                    ZhifuActivity.sZhifu.finish();
                }
                finish();
                break;
        }

    }

    private void getRelust() {
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", MyCookieStore.o_id);
        if (MyCookieStore.type.equals("wuyeNew")) {
            params.addBodyParameter("type", "property");//物业的
            params.addBodyParameter("prepay", "0");
        } else if (MyCookieStore.type.equals("shop") || MyCookieStore.type.equals("shop_1")) {//shop_1 是从购物流程一路付款成功的
            params.addBodyParameter("type", "shop");//购物的
            params.addBodyParameter("prepay", "0");
            System.out.println("s11111111111&&&&");
        } else if (MyCookieStore.type.equals("Yweixiu")) {
            params.addBodyParameter("type", "service");//维修预付款的
            params.addBodyParameter("prepay", "1");
        } else if (MyCookieStore.type.equals("weixiu")) {
            params.addBodyParameter("type", "service");//维修尾款的
            params.addBodyParameter("prepay", "0");
        } else if (MyCookieStore.type.equals("huodong")) {
            params.addBodyParameter("type", "activity");//活動
            params.addBodyParameter("prepay", "0");
        } else if (MyCookieStore.type.equals("facepay")) {
            params.addBodyParameter("type", "face");// 当面付
            params.addBodyParameter("prepay", "0");
        } else if (MyCookieStore.type.equals("wired")) {
            params.addBodyParameter("type", "wired");// 有线缴费
            params.addBodyParameter("prepay", "0");
        } else if (MyCookieStore.type.equals("service_new_pay")) {
            params.addBodyParameter("type", "serve");// // 新服务
            params.addBodyParameter("prepay", "0");
        } else if (MyCookieStore.type.equals("workorder_yufu")) {

            params.addBodyParameter("type", "work");// // 预付
            params.addBodyParameter("prepay", "1");
        } else if (MyCookieStore.type.equals("workorder_pay")) {
            // 工单支付
            params.addBodyParameter("type", "work");// 工单支付
            params.addBodyParameter("prepay", "0");
        }
        HttpHelper hh = new HttpHelper(info.confirm_order_payment, params, WXPayEntryActivity.this) {

            @Override
            protected void setData(String json) {
                String str = protocol2.setShop(json);
                if (str.equals("1")) {
                    SmartToast.showInfo("支付成功");
                    if (MyCookieStore.type.equals("shop")) {//个人中心购物订单列表中付款成功的
                        System.out.println("***********%%%%%%@@@@@@");
                        getWuLiu();//物流分配
                        getPush("1");//推送接口

                    }
                    if (MyCookieStore.type.equals("shop_1")) {//购物里付款成功
                        //MyCookieStore.WX_dialog=0;
                        MyCookieStore.is_notify = 1;
                        System.out.println("22222222222*********");
                        //支付完成后finish掉购物车页  立即支付页
                        getWuLiu();//物流分配
                        getPush("2");//推送接口
                        //支付成功后判断优惠券id不为空的话请求优惠券接口
                        /*if (!TextUtils.isEmpty(MyCookieStore.coupon_id)) {
                            getcoupon();
						}*/

                    }

                    if (MyCookieStore.type.equals("wuyeNew")) {
                        EventBus.getDefault().post(new EventProperty());
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1200);
                    }
                    if (MyCookieStore.type.equals("huodong")) {//活動付款成功
                        MyCookieStore.Activity_notity = 1;
                        Intent intent = new Intent();
                        setResult(2001, intent);
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1200);
                    }

                    if (MyCookieStore.type.equals("service_new_pay")) {// 新服务支付成功
                        Intent intent = new Intent(WXPayEntryActivity.this, FragmentOrderListActivity.class);
                        intent.putExtra("type", "pay_finish");
                        startActivity(intent);
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1200);
                        // 调一下支付成功推送的接口
                        new JpushPresenter().paySuccessJpush(MyCookieStore.o_id);
                    }
                    if (MyCookieStore.type.equals("workorder_pay")) {
                        //支付跳转
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1200);
                        EventBusWorkOrderModel eventBusModel = new EventBusWorkOrderModel();
                        eventBusModel.setWo_id(MyCookieStore.o_id);
                        eventBusModel.setEvent_type(2);
                        EventBus.getDefault().post(eventBusModel);
                    }
                    ZhifuActivity.sZhifu.finish();
                } else {
                    if (MyCookieStore.type.equals("Yweixiu")) {

                    }
                    if (MyCookieStore.type.equals("weixiu")) {

                    }
                    if (MyCookieStore.type.equals("shop")) {//个人中心购物订单列表中付款
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("item_detete_id", "");
                        intent.putExtras(bundle);
                        setResult(100, intent);
                        ZhifuActivity.sZhifu.finish();
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1200);
                        return;

                    }
                    if (MyCookieStore.type.equals("shop_1")) {//购物车里付款

                    }
                    if (MyCookieStore.type.equals("facepay")) {
                        ZhifuActivity.sZhifu.finish();
                    }
                    if (MyCookieStore.type.equals("wired")) {
                        ZhifuActivity.sZhifu.finish();
                    }
                    if (MyCookieStore.type.equals("huodong")) {//活動付款

                    }
                    if (MyCookieStore.type.equals("wuyeNew")) {
                        ZhifuActivity.sZhifu.finish();
                    }
                    SmartToast.showInfo(str);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1200);
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");

            }
        };

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (MyCookieStore.type.equals("Yweixiu")) {

            }
            if (MyCookieStore.type.equals("weixiu")) {

            }
            if (MyCookieStore.type.equals("shop")) {//个人中心购物订单列表中付款成功的
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("item_detete_id", "");
                intent.putExtras(bundle);
                setResult(100, intent);
            }
            if (MyCookieStore.type.equals("shop_1")) {//购物车里付款成功

            }
            if (MyCookieStore.type.equals("facepay")) {
                ZhifuActivity.sZhifu.finish();
            }
            if (MyCookieStore.type.equals("wired")) {
                ZhifuActivity.sZhifu.finish();
            }
            if (MyCookieStore.type.equals("huodong")) {//活動付款成功

            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    protected void getWuLiu() {//物流分配
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", MyCookieStore.o_id);
        HttpHelper hh = new HttpHelper(info.distribution, params, WXPayEntryActivity.this) {

            @Override
            protected void setData(String json) {
                String strWL = protocol2.setShop(json);
                if (strWL.equals("1")) {
                    System.out.println("********支付完成物流分配请求成功");
                } else {
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");

            }
        };
    }

    protected void getPush(final String push_tag) {//推送
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", MyCookieStore.o_id);
        HttpHelper hh = new HttpHelper(info.merchant_push, params, WXPayEntryActivity.this) {

            @Override
            protected void setData(String json) {
                String strPush = protocol2.setShop(json);
                if (strPush.equals("1")) {
                    if (push_tag.equals("2")) {
                        Intent intent = new Intent(WXPayEntryActivity.this, ShopOrderListActivityNew.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "type_zf_dsh");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        MyCookieStore.WX_notify = 1;
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("item_detete_id", "");
                        intent.putExtras(bundle);
                        setResult(333, intent);
                        //startActivity(intent);
                    //    NewShopOrderDetailActivity.instant.finish();
                        finish();
                    }
                    System.out.println("********支付完成推送接口请求成功");
                } else {
                    SmartToast.showInfo(strPush);
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");

            }
        };
    }
}

