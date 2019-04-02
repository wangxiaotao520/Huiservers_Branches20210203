package com.huacheng.huiservers.ui.center.geren;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.bestpay.app.PaymentTask;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.EventBusWorkOrderModel;
import com.huacheng.huiservers.model.ModelEventWX;
import com.huacheng.huiservers.model.protocol.GerenProtocol;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.pay.PayResult;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.ShopOrderListActivity;
import com.huacheng.huiservers.ui.center.bean.PayInfoBean;
import com.huacheng.huiservers.ui.center.geren.adapter.PayAdapter;
import com.huacheng.huiservers.ui.center.geren.bean.PayTypeBean;
import com.huacheng.huiservers.ui.center.geren.bean.PayinfoWXBean;
import com.huacheng.huiservers.ui.index.facepay.FacepayHistoryActivity;
import com.huacheng.huiservers.ui.index.facepay.FacepayIndexActivity;
import com.huacheng.huiservers.ui.index.property.PropertyPaymentActivity;
import com.huacheng.huiservers.ui.index.property.bean.EventProperty;
import com.huacheng.huiservers.ui.index.workorder.JpushWorkPresenter;
import com.huacheng.huiservers.ui.index.workorder.WorkOrderListActivity;
import com.huacheng.huiservers.ui.index.wuye.bean.WuYeBean;
import com.huacheng.huiservers.ui.servicenew.ui.order.FragmentOrderListActivity;
import com.huacheng.huiservers.ui.servicenew.ui.order.JpushPresenter;
import com.huacheng.huiservers.ui.shop.bean.BestpayMerchant;
import com.huacheng.huiservers.utils.ParamsUtil;
import com.huacheng.huiservers.utils.StreamUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class ZhifuActivity extends BaseActivityOld implements OnClickListener {
    /**
     * 微信支付业务：入参app_id
     */
    public static final String WXAPPID = "wx8765e31488491eb2";
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016093002017444";
    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088321023487967";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "hc_huizhong@163.com";
    /**
     * 商户私钥，pkcs8格式
     */
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_ALI = 1;
    private static final int SDK_PAY_WX = 2;
    private final static int GOTO_BEST_PAY = 3;
    private TextView title_name, txt_price;
    private LinearLayout lin_left;
    private String payinfo, o_id, payprice, type, data, order_type;
    GerenProtocol protocol = new GerenProtocol();
    List<PayTypeBean> mTypeBeen = new ArrayList<>();
    ShopProtocol protocol2 = new ShopProtocol();
    private ListView pay_list;
    PayInfoBean bean = new PayInfoBean();
    String actionName;
    public static ZhifuActivity sZhifu;
    PayinfoWXBean Wxbean;
    IWXAPI msgApi;
    // 0907-----------------
    /**
     * 商户key,为防止泄漏,建议通过服务端获取,不要放在本地
     */
    /*private final static String KEY = "344C4FB521F5A52EA28FB7FC79AEA889478D4343E4548C02";
     */
    /*private final static String riskControlInfo = "Json字符串，2016.8.30（不包含）以后新商户必填)\n"
            + "翼支付风控组提供（在商户入网的时候会给出）";*/
    private final static String KEY = "3E7B449B4071A353B3028E8CE0DA9AE06842B646F625000D";
    private final static String riskControlInfo = "{\"service_identify\":\"104\",\"subject\":\"商品的标题\",\"product_type\":\"3\",\"boby\":\"请将商品描述字符串累加传给boby\",\"goods_count\":\"3\",\"show_url\":\"http://www.baidu.com\",\"services_type\":\"购物服务\"}";
    BestpayMerchant bestPayInfo;
    BestpayMerchant mModel;

    private PaymentTask mPaymentTask;
    String waterEle;
    WuYeBean wuYeBean = null;

    EventProperty pro_oid;
    // -----------------
    private int result_check_count = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_ALI: // 支付宝请求结果
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    String memo = payResult.getMemo();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        getRelust();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        getRelust();
                    }
                    break;
                case SDK_PAY_WX:// 微信支付
                    Wxbean = (PayinfoWXBean) msg.obj;
                    wxPay();
                    break;
                case GOTO_BEST_PAY://翼支付
                    gotoBestPay();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void init() {
        super.init();
        sZhifu = this;
        setContentView(R.layout.zhifu);

        MyCookieStore.is_notify = 1;
        MyCookieStore.WyJf_notify = 0;
        MyCookieStore.WX_notify = 0;
        // 接收前一个界面传过来的值
        o_id = this.getIntent().getExtras().getString("o_id");
        type = this.getIntent().getExtras().getString("type");
        order_type = this.getIntent().getExtras().getString("order_type");
        payprice = this.getIntent().getExtras().getString("price");


        MyCookieStore.ConfirmWuye = wuYeBean;
        MyCookieStore.type = type;
        MyCookieStore.o_id = o_id;

        title_name = findViewById(R.id.title_name);
        txt_price = findViewById(R.id.txt_price);
        title_name.setText("支付");
        lin_left = findViewById(R.id.lin_left);
        pay_list = findViewById(R.id.pay_list);

        initListener();//点击支付类型监听事件
        mPaymentTask = new PaymentTask(this);
        lin_left.setOnClickListener(this);
        txt_price.setText("¥" + payprice);
        getPayList();//获取支付类型
    }

    private void initListener() {
        pay_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                result_check_count = 1;
                if (mTypeBeen.get(position).getTypename().equals("hcpay")) {//一卡通
                    Intent intent = new Intent(ZhifuActivity.this, CardPayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("oid", o_id);
                    bundle.putString("order_type", order_type);
                    bundle.putString("price", payprice);
                    bundle.putString("type", type);

                    intent.putExtras(bundle);
                    startActivityForResult(intent, 101);
                } else if (mTypeBeen.get(position).getTypename().equals("wxpay")) {//微信
                    if (isWeixinAvilible(ZhifuActivity.this)) {
                        getPayInfo(type, mTypeBeen.get(position).getTypename());
                    } else {
                        SmartToast.showInfo("您还没有安装微信，请先安装微信客户端");
                    }

                } else { //支付宝 翼支付
                    getPayInfo(type, mTypeBeen.get(position).getTypename());
                }

            }
        });
    }

    /**
     * 获取支付方式接口
     */
    private void getPayList() {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        HttpHelper hh = new HttpHelper(info.payment_list, params, ZhifuActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                mTypeBeen = protocol.getPayList(json);
                PayAdapter adapter = new PayAdapter(ZhifuActivity.this, mTypeBeen);
                pay_list.setAdapter(adapter);
                for (int i = 0; i < mTypeBeen.size(); i++) {
                    if (mTypeBeen.get(i).getTypename().equals("wxpay")) {
                        // 商户APP工程中引入微信JAR包，调用API前，需要先向微信注册您的APPID，代码如下：
                        msgApi = WXAPIFactory.createWXAPI(ZhifuActivity.this, mTypeBeen.get(i).getApp_id(), true);
                        // 将该app注册到微信
                        msgApi.registerApp(mTypeBeen.get(i).getApp_id());
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


    /**
     * 支付接口
     * 翼支付 微信 支付宝
     *
     * @param type
     * @param typename
     */
    private void getPayInfo(String type, final String typename) {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", o_id);
        params.addBodyParameter("typename", typename);

        if (type.equals("wuye") || type.equals("wuyeNew")) {
            actionName = info.pay_property_order;// 物业的
        } else if (type.equals("shop") || type.equals("shop_1")) {// shop_1
            // 是从购物流程一路付款成功的
            actionName = info.pay_shopping_order;// 购物的
        } else if (type.equals("huodong")) {
            actionName = info.pay_activity_order;// 活动的
        } else if (type.equals("facepay")) {
            actionName = info.pay_face_order;// 当面付
        } else if (type.equals("wired")) {
            actionName = info.pay_wired_order;// 有线缴费
        } else if ("service_new_pay".equals(type)) {
            actionName = info.service_new_pay;//新服务支付
        } else if ("workorder_yufu".equals(type)) {
            actionName = ApiHttpClient.PAY_WORK_ORDER;//工单预付
            params.addBodyParameter("prepay", "1");
        } else if ("workorder_pay".equals(type)) {
            actionName = ApiHttpClient.PAY_WORK_ORDER;//工单支付
        }

        new HttpHelper(actionName, params,
                ZhifuActivity.this) {
            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(json);
                    String status = jsonObject.getString("status");
                    data = jsonObject.getString("data");
                    String message = jsonObject.getString("msg");
                    if (StringUtils.isEquals(status, "1")) { //成功
                        if (typename.equals("alipay")) {// 支付宝
                            payinfo = data;
                            alipay();
                        } else if (typename.equals("wxpay")) {// 微信
                            // 解析字符串
                            Runnable WXpayRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Wxbean = JSON.parseObject(data,
                                                PayinfoWXBean.class);
                                        Message msg = new Message();
                                        msg.what = SDK_PAY_WX;
                                        msg.obj = Wxbean;
                                        mHandler.sendMessage(msg);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            Thread payThread = new Thread(WXpayRunnable);
                            payThread.start();
                        } else if (typename.equals("bestpay")) {//翼支付
                            bestPayInfo = protocol2.getBestpayMerchant(json);
                            if (bestPayInfo != null) {
                                initBestpay();
                            }
                        }
                    } else {  //status !=0
                        SmartToast.showInfo(message);
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
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
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.lin_left:// 返回
                if (type.equals("shop")) {// 个人中心购物订单列表中付款成功的
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("item_detete_id", "");
                    intent.putExtras(bundle);
                    setResult(100, intent);
                } else if (type.equals("shop_1")) {// 购物车里付款成功

                    Intent intent = new Intent(this, ShopOrderListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "type_zf_dfk");
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (type.equals("facepay")) {// 当面付
                    getRelust();
                } else if (type.equals("wired")) {
                    getRelust();
                } else if (type.equals("wuyeNew")) {
                    getRelust();
                } else if ("workorder_yufu".equals(type)) { //预付
                    Intent intent = new Intent(ZhifuActivity.this, WorkOrderListActivity.class);
                    startActivity(intent);

                } else if ("workorder_pay".equals(type)) {//工单支付
                    Intent intent = new Intent(ZhifuActivity.this, WorkOrderListActivity.class);
                    startActivity(intent);

                }
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
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

    //微信支付的回调
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WXCallback(ModelEventWX event) {
        if (event != null) {
            //微信
            if (event.getType() == 0) {
                getRelust();
            }
        }
    }

    /**
     * 检验支付是否成功的接口
     */
    private void getRelust() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", o_id);
        if (type.equals("wuyeNew")) {
            params.put("type", "property");// 物业的
            params.put("prepay", "0");
        } else if (type.equals("shop") || type.equals("shop_1")) {// shop_1
            // 是从购物流程一路付款成功的
            params.put("type", "shop");// 购物的
            params.put("prepay", "0");
        } else if (type.equals("Yweixiu")) {
            params.put("type", "service");// 维修预付款的
            params.put("prepay", "1");
        } else if (type.equals("weixiu")) {
            params.put("type", "service");// 维修尾款的
            params.put("prepay", "0");
        } else if (type.equals("huodong")) {
            params.put("type", "activity");// 活動
            params.put("prepay", "0");
        } else if (type.equals("facepay")) {
            params.put("type", "face");// 当面付
            params.put("prepay", "0");
        } else if (type.equals("wired")) {
            params.put("type", "wired");// 有线缴费
            params.put("prepay", "0");
        } else if (type.equals("service_new_pay")) {
            params.put("type", "serve");// // 支付成功后+服务付款
            params.put("prepay", "0");
        } else if (type.equals("workorder_yufu")) {
            //工单预付
            params.put("type", "work");//
            params.put("prepay", "1");
        } else if (type.equals("workorder_pay")) {
            // 工单支付
            params.put("type", "work");// 支付
            params.put("prepay", "0");
        }
        MyOkHttp.get().post(Url_info.confirm_order_payment, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {//成功
                    hideDialog(smallDialog);
                    if (type.equals("shop")) {// 个人中心购物订单列表中付款成功的
                        getWuLiu();// 物流分配
                        getPush("1");// 推送接口
                    } else if (type.equals("shop_1")) {// 购物里付款成功
                        getWuLiu();// 物流分配
                        getPush("2");// 推送接口
                        // 支付成功后判断优惠券id不为空的话请求优惠券接口
                    } else if (type.equals("wuyeNew")) {  //新物业
                        EventBus.getDefault().post(new EventProperty());
                        Intent intent = new Intent(ZhifuActivity.this, PropertyPaymentActivity.class);
                        startActivity(intent);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1600);
                    } else if (type.equals("huodong")) {// 活動付款成功
                        MyCookieStore.Activity_notity = 1;
                        Intent intent = new Intent();
                        setResult(2001, intent);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1600);
                    } else if (type.equals("facepay")) {// 当面付成功
                        Intent intent = new Intent(ZhifuActivity.this, FacepayHistoryActivity.class);
                        startActivity(intent);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1600);
//                        FacepayConfirmPaymentActivity.sFacepayConfirm.finish();
                        FacepayIndexActivity.sFacePayIndex.finish();
                    } else if (type.equals("wired")) {// 有线缴费成功
                        Intent intent = new Intent(ZhifuActivity.this, WiredHistoryActivity.class);
                        startActivity(intent);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1600);
                        WiredIndexActivity.instant.finish();
                    } else if (type.equals("service_new_pay")) {// 新服务支付成功
                        Intent intent = new Intent(ZhifuActivity.this, FragmentOrderListActivity.class);
                        intent.putExtra("type", "pay_finish");
                        startActivity(intent);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1600);
                        // 支付成功后调用极光
                        new JpushPresenter().paySuccessJpush(o_id);
                    } else if (type.equals("workorder_yufu")) {//工单预付
                        //预付跳转
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1600);
                        Intent intent = new Intent(ZhifuActivity.this, WorkOrderListActivity.class);
                        startActivity(intent);
                        EventBusWorkOrderModel eventBusModel = new EventBusWorkOrderModel();
                        eventBusModel.setWo_id(o_id);
                        eventBusModel.setEvent_type(1);
                        EventBus.getDefault().post(eventBusModel);

                        //给物业端管理角色推送,用户提交维修信息
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id", o_id);
                        params.put("type", "1");
                        new JpushWorkPresenter().userToWorkerSubmitJpush(params);

                    } else if (type.equals("workorder_pay")) {//工单
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1600);
                        EventBusWorkOrderModel eventBusModel = new EventBusWorkOrderModel();
                        eventBusModel.setWo_id(o_id);
                        eventBusModel.setEvent_type(2);
                        EventBus.getDefault().post(eventBusModel);

                    }
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "支付成功");
                    SmartToast.showInfo(msg);
                } else {
                    if (result_check_count < 4) {//支付失败轮询服务器三次
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getRelust();
                            }
                        }, 1000);
                        result_check_count++;
                        return;
                    }
                    hideDialog(smallDialog);
                    if (type.equals("shop")) {// 个人中心购物订单列表中付款成功的
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("item_detete_id", "");
                        intent.putExtras(bundle);
                        setResult(100, intent);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1600);

                    } else if (type.equals("facepay")) {// 当面付
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1600);
                    } else if (type.equals("wired")) {// 有线缴费
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1600);
                    } else if (type.equals("wuyeNew")) {// 不支付直接返回
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1600);
                    }
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "支付失败");
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (type.equals("shop")) {// 个人中心购物订单列表中付款
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("item_detete_id", "");
                intent.putExtras(bundle);
                setResult(100, intent);
            } else if (type.equals("shop_1")) {// 购物车里付款
                Intent intent = new Intent(this, ShopOrderListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "type_zf_dfk");
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (type.equals("facepay")) {// 当面付
                getRelust();
            } else if (type.equals("wired")) {// 有线缴费
                getRelust();
            } else if ("workorder_yufu".equals(type)) {//预付
                Intent intent = new Intent(ZhifuActivity.this, WorkOrderListActivity.class);
                startActivity(intent);
            } else if ("workorder_pay".equals(type)) { //工单支付
                Intent intent = new Intent(ZhifuActivity.this, WorkOrderListActivity.class);
                startActivity(intent);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    // 物流分配
    protected void getWuLiu() {
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", o_id);
        System.out.println("peisong-------oid^^^^" + o_id);
        HttpHelper hh = new HttpHelper(info.distribution, params,
                ZhifuActivity.this) {

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

    // 购物订单推送接口
    protected void getPush(final String isStr) {
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", o_id);
        HttpHelper hh = new HttpHelper(info.merchant_push, params, ZhifuActivity.this) {

            @Override
            protected void setData(String json) {
                String str_push = protocol2.setShop(json);
                if (str_push.equals("1")) {
                    if (isStr.equals("2")) {
                        Intent intent = new Intent(ZhifuActivity.this,
                                ShopOrderListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "type_zf_dsh");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        // 支付完成后finish掉购物车页 立即支付页
                        finish();
                    } else {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("item_detete_id", "");
                        intent.putExtras(bundle);
                        setResult(333, intent);
                        finish();
                    }
                    System.out.println("^^^^^^^^支付完成推送接口请求成功");
                } else {
                    SmartToast.showInfo(str_push);
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }
    // -------------------------------翼支付-------------------------------------//

    /**
     * 初始化翼支付
     */
    private void initBestpay() {
        mModel = new BestpayMerchant();
        mModel.setMERCHANTID(bestPayInfo.getMERCHANTID());// 商户号*
        mModel.setMERCHANTPWD(bestPayInfo.getMERCHANTPWD());// 商户密码*
        // 下单单位为分

        mModel.setORDERAMOUNT(yuan2Fen(bestPayInfo.getORDERAMOUNT()));// 金额，单位（元）
//        Log.e("bestPayInfo.getORDERAMOUNT()", bestPayInfo.getORDERAMOUNT());
        mModel.setACCOUNTID("");// 翼支付账号//-
        mModel.setBUSITYPE(bestPayInfo.getBUSITYPE());// 业务类型

		/*mModel.setORDERSEQ(new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date(time)));*/
        mModel.setORDERSEQ(bestPayInfo.getORDERSEQ());

        String str6 = getFixLenthString(6);
        //mModel.setORDERREQTRANSEQ(time + str6);// 订单交易流水号
        mModel.setORDERREQTRANSEQ(bestPayInfo.getORDERREQTRANSEQ());

		/*mModel.setORDERTIME(new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date(time)));*/
        mModel.setORDERTIME(bestPayInfo.getORDERTIME());

        mModel.setORDERVALIDITYTIME(bestPayInfo.getORDERTIME());

        mModel.setCUSTOMERID(bestPayInfo.getCUSTOMERID());// 用户手机号-
        mModel.setPRODUCTAMOUNT(bestPayInfo.getORDERAMOUNT());// * 产品金额
        // *产品描述，商品的标题/交易标题/订单标题/订单关键字等该参数最长为 128个汉子
        mModel.setPRODUCTDESC(bestPayInfo.getPRODUCTDESC());
        mModel.setATTACHAMOUNT(bestPayInfo.getATTACHAMOUNT());// 附加金额 单位 0.01 元
        mModel.setCURTYPE(bestPayInfo.getCURTYPE());
        mModel.setBACKMERCHANTURL(bestPayInfo.getBACKMERCHANTURL());
        mModel.setPRODUCTID(bestPayInfo.getPRODUCTID());// 纯业务支付
        mModel.setUSERIP("");// 用户IP
        mModel.setDIVDETAILS("");// 分账明细，分账商户必填,格式见说明

        mModel.setSERVICE("mobile.security.pay");
        mModel.setSIGNTYPE("MD5");
        mModel.setSUBJECT(bestPayInfo.getSUBJECT());// 商品描述 * 翼支付第一行显示
        mModel.setSWTICHACC(bestPayInfo.getSWTICHACC());
        //请求
        orderBestPay();
    }

    private void orderBestPay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL orderUrl = new URL(
                            "https://webpaywg.bestpay.com.cn/order.action");
                    HttpURLConnection urlConnection = (HttpURLConnection) orderUrl
                            .openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("Charset", "UTF-8");
                    // 请求入参
                    urlConnection.getOutputStream().write(
                            ParamsUtil.buildOrderParams(mModel,
                                    MyCookieStore.YI_KEY,
                                    MyCookieStore.RISKCONTROLINFO)
                                    .getBytes());
                    // urlConnection.getOutputStream().flush();

                    InputStream is = urlConnection.getInputStream();
                    String responseCode = StreamUtil.stream2String(is).split(
                            "&")[0];
                    if (TextUtils.equals(responseCode, "00")) {// 1101是什么
                        mHandler.sendEmptyMessage(GOTO_BEST_PAY);
                    } else {
                        if (type.equals("facepay")) {// 当面付
                            finish();
                        }
                        SmartToast.showInfo("下单失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 翼支付调用sdk支付
     */
    private void gotoBestPay() {

        mModel.setORDERAMOUNT(bestPayInfo.getORDERAMOUNT()); // 调用sdk支付，订单金额单位为元

        // 建议将加签步骤放在自己服务端,签名信息从服务端获取
        mModel.setSIGN(ParamsUtil.getSign(mModel, MyCookieStore.YI_KEY));

        mPaymentTask.pay(ParamsUtil.buildPayParams(mModel));
    }

    private String yuan2Fen(String yuan) {
        BigDecimal decimal = new BigDecimal(yuan);
        BigDecimal decimalMultiply = new BigDecimal("100");
        BigDecimal minuteAmt = decimal.multiply(decimalMultiply).setScale(0);
        return minuteAmt.toString();
    }

    /*
     * 返回长度为【strLength】的随机数，在前面补0
     */
    private static String getFixLenthString(int strLength) {

        Random rm = new Random();

        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }


    // -------------------------------翼支付-------------------------------------//

    //-------------------------------微信支付-----------------------------------//

    /**
     * 微信支付业务
     *
     * @param
     */
    private void wxPay() {
        PayReq request = new PayReq();

        request.appId = Wxbean.getAppId();
        // 1402882702
        request.partnerId = Wxbean.getPartnerid();

        request.prepayId = Wxbean.getPrepayid();// 预支付交易会话ID

        request.packageValue = Wxbean.getPackages();// 扩展字段

        request.nonceStr = Wxbean.getNonceStr();// 随机字符串

        request.timeStamp = Wxbean.getTimeStamp();// 时间戳

        request.sign = Wxbean.getPaySign();// 签名
        //  System.out.println("Wxbean.getPrepayid()^^^^" + Wxbean.getPrepayid());
        msgApi.sendReq(request);

        //回调在WXPayEntryActivity
    }

    // 判断是否安装微信
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
    //-------------------------------微信支付-----------------------------------//

    //-------------------------------支付宝------------------------------------//

    /**
     * 支付宝支付业务
     *
     * @param
     */
    public void alipay() {
        /*
         * if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) { new
         * AlertDialog
         * .Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
         * .setPositiveButton("确定", new DialogInterface.OnClickListener() {
         * public void onClick(DialogInterface dialoginterface, int i) { //
         * finish(); } }).show(); return; }
         */
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        /*
         * Map<String, String> params =
         * OrderInfoUtil2_0.buildOrderParamMap(APPID); String orderParam =
         * OrderInfoUtil2_0.buildOrderParam(params); String sign =
         * OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
         * System.out.println("加签的参数******"+params);
         * System.out.println("demo加的签******"+orderParam + "&" + sign); //final
         * String orderInfo = orderParam + "&" + sign;
         * System.out.println("接口返回的签----"+payinfo);
         */
        final String orderInfo = payinfo;
        // final String orderInfo =
        // "app_id=2016093002017444&biz_content=%7B%22body%22%3A%22hj%E6
        // %94%AF%E4%BB%98%E8%B4%AD%E7%89%A9%E8%AE%A2%E5%8D%95%22%2C%22out_trade_
        // no%22%3A%22gw57fedb732b5b2%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%
        // 22%2C%22subject%22%3A%22hj%E7%9A%84%E8%B4%AD%E7%89%A9%E8%AE%A2%E5%8D%95%22%2C%22
        // timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2215.2%22%7D&
        // charset=utf-8&method=alipay.trade.app.pay&sign=OqtFhxPCv4%2B%2B0tk78m82lDFc%2F0CpE%2F
        // z7aJj81pKaKjYRJnNceS9Tkq3j88IJbN0%2F0ItXYfTexOiQ3oLEMguXKozhzskbC6zv8ejltWRjoCZ7LoSBtoxus8BZbGRLs
        // IzWTdTBPRMr6StJgGj%2BXsYO4nCTHp2nvhqzsxLCDfg9X0g%3D&sign_type=RSA&timestamp=2016-10-18+10%3A11%3A42&version=1.0";

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ZhifuActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_ALI;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    //-------------------------------支付宝-----------------------------------//

    /**
     * 翼支付 一卡通的回调走这里
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
            return;

        String result = data.getStringExtra("result");
        result = TextUtils.isEmpty(result) ? "" : result;
        String showStr = String.format("resultCode:%s;result：%s", resultCode,
                result);

        getRelust();

    }
}
