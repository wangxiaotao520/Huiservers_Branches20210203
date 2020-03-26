package com.huacheng.huiservers.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.db.UserSql;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.model.ModelLogin;
import com.huacheng.huiservers.model.ModelUser;
import com.huacheng.huiservers.model.protocol.LoginProtocol;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.utils.WXConstants;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.mob.MobSDK;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.sina.helper.MD5;

import org.apache.http.impl.client.DefaultHttpClient;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import cn.com.chinatelecom.account.lib.auth.AuthResultListener;
import cn.com.chinatelecom.account.lib.auth.CtAuth;
import cn.com.chinatelecom.account.lib.model.AuthResultModel;

/**
 * 登录页
 */
public class LoginVerifyCodeActivity extends BaseActivity implements OnClickListener {
    private TextView txt_btn, txt_getcode, txt_shengming, tv_txt2, tv_txt1, tv_txt3;
   // HttpUtils utils = new HttpUtils();
    private EditText et_mobile, et_getcode, et_yq;
    private LinearLayout ly_back, ly_bottom;
    private ImageView iv_mm, iv_wx;
    private CtAuth ctAuth;
    public static LoginVerifyCodeActivity instant;
    private String login_shop, push_id, timestamp, jmStr, dialog_type;
    private SharedPreferences preferences;
    private SharePrefrenceUtil sharePrefrenceUtil;
    private SharedPreferences preferencesLogin;
    private Message msg;
    public String accessToken;
    private Handler myHandler = new myHandler();

    long ld;
    private ArrayList<String> accessTokenList = new ArrayList<>();
    public static boolean isForeground = false;
    ModelLogin loginBean = new ModelLogin();
    LoginProtocol protocol = new LoginProtocol();
    DefaultHttpClient dh;
    String wx_info;//微信登录返回的用户信息
    String login_type;  // "yz";//默认验证码登录 "wx"微信登录
    private static final String TAG = "Activity";
    JSONObject jsonObject, jsonData;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    new MyCount(60000, 1000).start();
                    break;
                default:
                    break;
            }
        }
    };

    class myHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    AuthResultModel successAuthResult = (AuthResultModel) msg.obj;
                    if (successAuthResult != null) {
                        accessToken = successAuthResult.accessToken;
                    }
                    sharePrefrenceUtil.setRefreshToken(successAuthResult.refreshToken);
                    sharePrefrenceUtil.setAcceessToken(successAuthResult.accessToken);
                    sharePrefrenceUtil.setOpenId(successAuthResult.openId);
                    sharePrefrenceUtil.setTimeStamp(String.valueOf(successAuthResult.timeStamp));
                    Log.e("accessToken", successAuthResult.toString());
                    Log.e("accessToken", "ip-" + getIPAddress(LoginVerifyCodeActivity.this));
                    Log.e("MD55555", ToolUtils.encrypt("d12d54c4d613e38e8481df7852133a861508743755171"));
                    getUserInfo();
                    break;
                case 2:
                    //登录失败
                    break;
                case 0:
                    //自定义处理方式
                    break;
            }
        }
    }

    @Override
    protected void initView() {

        getSharedPre();
        login_type = "yz";//默认验证码登录

        et_mobile = findViewById(R.id.et_mobile);
        et_getcode = findViewById(R.id.et_getcode);
        et_yq = findViewById(R.id.et_yq);
        txt_getcode = findViewById(R.id.txt_getcode);
        txt_btn = findViewById(R.id.txt_btn);
        txt_shengming = findViewById(R.id.txt_shengming);
        iv_mm = findViewById(R.id.iv_mm);
        iv_wx = findViewById(R.id.iv_wx);
        ly_bottom = findViewById(R.id.ly_bottom);
        ly_back = findViewById(R.id.ly_back);
        tv_txt1 = findViewById(R.id.tv_txt1);
        tv_txt2 = findViewById(R.id.tv_txt2);
        tv_txt3 = findViewById(R.id.tv_txt3);
        tv_txt3.setVisibility(View.GONE);

        ctAuth = CtAuth.getInstance();
        ctAuth.init(this, "tW0N2VEpc7BrAUaF2Y9XoWiY2bzjXtZM");

        txt_getcode.setTextColor(getResources().getColor(R.color.colorPrimary));
        txt_getcode.setBackground(getResources().getDrawable(R.drawable.cornes_bgwhite_login_orange));
        txt_getcode.setEnabled(true);
        txt_btn.setTextColor(getResources().getColor(R.color.white));
        txt_btn.setBackground(getResources().getDrawable(R.drawable.allshape_orange_10));
        txt_btn.setEnabled(true);

        txt_getcode.setOnClickListener(this);
        txt_btn.setOnClickListener(this);
        iv_mm.setOnClickListener(this);
        iv_wx.setOnClickListener(this);
        txt_shengming.setOnClickListener(this);
        ly_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_verify_code;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        MobSDK.init(this, getString(R.string.MobAppkey), getString(R.string.MobAppkey));
        super.onCreate(savedInstanceState);
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
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View arg0) {
        Intent intent;
        switch (arg0.getId()) {
            case R.id.ly_back:// 返回
                closeInputMethod();
                finish();
                break;
            case R.id.iv_wx:// 微信登录
                // 将该app注册到微信
                WXConstants.wx_api = WXAPIFactory.createWXAPI(this, WXConstants.APP_ID, true);
                WXConstants.wx_api.registerApp(WXConstants.APP_ID);
                if (!WXConstants.wx_api.isWXAppInstalled()) {
                    SmartToast.showInfo("您还没有安装微信，请先安装微信客户端");

                } else {
                    if (!ToolUtils.isNetworkAvailable(this)) {
                        SmartToast.showInfo("网络异常，请检查网络设置");
                        return;
                    }
                    showDialog(smallDialog);
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    WXConstants.wx_api.sendReq(req);
                }

                break;
            case R.id.iv_mm://免密
                //如果移动数据未开启，提示开启
                if (ToolUtils.getMobileDataState(this, null)) {
                    ctAuth.openAuthActivity(LoginVerifyCodeActivity.this, "", accessTokenList, new AuthResultListener() {
                        @Override
                        public void onSuccess(AuthResultModel entity) {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = entity;
                            myHandler.sendMessage(msg);
                        }

                        @Override
                        public void onFail(AuthResultModel entity) {
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = entity;
                            myHandler.sendMessage(msg);
                        }

                        @Override
                        public void onCustomDeal() {
                            Message msg = new Message();
                            msg.what = 0;
                            myHandler.sendMessage(msg);
                        }
                    });
                } else {
                    SmartToast.showInfo("使用免密登录，需要开启数据流量！");
                }
                break;
            case R.id.txt_getcode:// 获取验证码
                if (et_mobile.getText().toString().equals("")) {
                    SmartToast.showInfo("请输入手机号");
                } else {
                    // 获取当前网络时间戳//耗时操作 需放在子线程中
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            URL url = null;//取得资源对象
                            try {
                                url = new URL("http://www.baidu.com");
                                URLConnection uc = url.openConnection();//生成连接对象
                                uc.connect(); //发出连接
                                ld = uc.getDate() / 1000; //取得网站日期时间、
                                System.out.println("ld------" + ld);
                                timestamp = String.valueOf(ld);
                                //获取加密字符串
                                String ttttt = MD5.hexdigest("hui-shenghuo.api_sms").substring(8, 24);
                                jmStr = MD5.hexdigest(timestamp + ttttt + timestamp).substring(0, 16);
                                //getCode();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getcode();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }
                break;
            case R.id.txt_btn:// 登录
                if (et_mobile.getText().toString().equals("")) {
                    SmartToast.showInfo("请输入手机号");
                } else if (et_getcode.getText().toString().trim().equals("")) {
                    SmartToast.showInfo("请输入验证码");
                } else {
                    txt_btn.setTextColor(getResources().getColor(R.color.white));
                    txt_btn.setBackground(getResources().getDrawable(R.drawable.corners_bg_transparent_primary));
                    txt_btn.setEnabled(false);
                    txt_btn.setText("登录中...");
                    if (login_type.equals("wx")) {
                        getWXsubmit(wx_info);//微信绑定
                    } else {
                        login();//验证码直接登录成功
                    }
                }
                break;
            case R.id.txt_shengming:// 声明
                intent = new Intent(this, ResigerShengmingActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    //获取用户信息 -> 电信免密登录
    private void getUserInfo() {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("accessToken", sharePrefrenceUtil.getAcceessToken());
        params.addBodyParameter("openId", sharePrefrenceUtil.getOpenId());
        params.addBodyParameter("clientIp", getIPAddress(LoginVerifyCodeActivity.this));
        params.addBodyParameter("clientId", "8015489963");
        HttpHelper hh = new HttpHelper(info.get_userInfo, params, this) {

            @Override
            protected void setData(String json) {
                try {
                    jsonObject = new JSONObject(json);
                    String status = jsonObject.getString("status");
                    String data = jsonObject.getString("data");
                    if (StringUtils.isEquals(status, "1")) {
                        jsonData = new JSONObject(data);
                        sharePrefrenceUtil.setTel(jsonData.getString("mobileName"));
                        // 获取当前网络时间戳//耗时操作 需放在子线程中
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                URL url = null;//取得资源对象
                                try {
                                    url = new URL("http://www.baidu.com");
                                    URLConnection uc = url.openConnection();//生成连接对象
                                    uc.connect(); //发出连接
                                    ld = uc.getDate() / 1000; //取得网站日期时间、
                                    System.out.println("ld------" + ld);
                                    timestamp = String.valueOf(ld);
                                    //获取加密字符串
                                    String ttttt = MD5.hexdigest("hui-shenghuo.api_sms").substring(8, 24);
                                    jmStr = MD5.hexdigest(timestamp + ttttt + timestamp).substring(0, 16);
                                    getFreeLogin(jsonData.getString("mobileName"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                System.out.println("timestamp------" + timestamp);
                            }
                        }).start();

                    } else if (StringUtils.isEquals(status, "0")) {

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

    //登录/注册接口 -> 电信免密登录
    private void getFreeLogin(final String tel) {
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("username", tel);
        params.addBodyParameter("token", ToolUtils.encrypt(sharePrefrenceUtil.getOpenId() + sharePrefrenceUtil.getTimeStamp()));
        params.addBodyParameter("openId", sharePrefrenceUtil.getOpenId());
        params.addBodyParameter("timeStamp", sharePrefrenceUtil.getTimeStamp());
        params.addBodyParameter("phone_name", push_id);
        Log.e("push_id", push_id);
        params.addBodyParameter("phone_type", "1");
        params.addBodyParameter("ApiSmstokentime", timestamp);// 时间戳
        params.addBodyParameter("ApiSmstoken", jmStr);// 加密字符串
        if (!NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId())){
            params.addBodyParameter("community_id",sharePrefrenceUtil.getXiaoQuId());
        }
        MyOkHttp.get().post(info.free_login, params.getParams(), new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                sharePrefrenceUtil.setLoginType("0");
                String res = response;
                loginBean = protocol.DataState(res);
                Intent intent = null;
                if (loginBean.getUid() != null) {
                    // 临时文件存储
                    SharedPreferences preferences1 = LoginVerifyCodeActivity.this.getSharedPreferences("login", 0);
                    SharedPreferences.Editor editor = preferences1.edit();
                    //  editor.putString("login_type", loginBean.getUtype());
                    editor.putString("login_type", "1");
                    editor.putString("login_username", tel);
                    editor.putString("login_password", "");
                    editor.putString("is_wuye", loginBean.getIs_bind_property());
                    editor.putString("login_shop", login_shop);
                    editor.putString("login_uid", loginBean.getUid());
                    //加入token
                    editor.putString("token", loginBean.getToken());
                    editor.putString("tokenSecret", loginBean.getTokenSecret());
                    ApiHttpClient.TOKEN = loginBean.getToken();
                    ApiHttpClient.TOKEN_SECRET = loginBean.getTokenSecret();
                    editor.commit();
                    //判断小区id
                    get_community_id(loginBean.getCommunity_id());
                    System.out.println("88******" + login_shop);
                    dialog_type = preferencesLogin.getString("dialog_type", "");
                    if (login_shop.equals("shop_login")) {
                        if (loginBean.getUtype().equals("1")) {// 个人登陆成功后获取物业验证字段。。目前现在没有首页暂不获取
                            closeInputMethod();
                            MyCookieStore.Circle_notify = 3;
                            finish();
                            SmartToast.showInfo("登录成功");
                           /* if (dialog_type.equals("shanghu")) {
                                intent = new Intent(LoginVerifyCodeActivity.this, ElectronicMerchantActivity.class);
                                startActivity(intent);
                            } else if (dialog_type.equals("yezhu")) {
                                intent = new Intent(LoginVerifyCodeActivity.this, OwnerAcitivity.class);
                                startActivity(intent);
                            }*/
                        } else {// 企业
                            txt_btn.setText("登录");
                            txt_btn.setClickable(true);
                            SmartToast.showInfo("当前账号不是个人账号,请重新登录");
                        }
                    } else {
                        if (loginBean.getUtype().equals("1")) {// 个人登陆成功后获取物业验证字段。。目前现在没有首页暂不获取
                            closeInputMethod();
                            finish();
                           SmartToast.showInfo("登录成功");
                            //     BaseActivityOld.destoryActivity();
                          /*  if (dialog_type.equals("shanghu")) {
                                intent = new Intent(LoginVerifyCodeActivity.this, ElectronicMerchantActivity.class);
                                startActivity(intent);
                            } else if (dialog_type.equals("yezhu")) {
                                intent = new Intent(LoginVerifyCodeActivity.this, OwnerAcitivity.class);
                                startActivity(intent);
                            }*/
                        } else {// 企业
                            txt_btn.setText("登录");
                            txt_btn.setClickable(true);
                            SmartToast.showInfo("当前账号不是个人账号,请重新登录");
                        }
                    }
                } else {
                    txt_btn.setText("登录");
                    txt_btn.setClickable(true);
                }
                //保存到modelUser中
                try {
                    JSONObject respose = new JSONObject(response);
                    if (JsonUtil.getInstance().isSuccess(respose)) {
                        ModelUser user = (ModelUser) JsonUtil.getInstance().parseJsonFromResponse(respose, ModelUser.class);
                        if (user!=null){
                            // 把数据保存在数据库中
                            UserSql.getInstance().clear();
                            UserSql.getInstance().insertObject(user);
                            BaseApplication.setUser(user);
                            EventBus.getDefault().post(loginBean);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                txt_btn.setText("登录");
                txt_btn.setClickable(true);
                SmartToast.showInfo("网络异常，请检查网络设置");

            }
        });
    }

    //发送验证码接口
    private void getcode() {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("username", et_mobile.getText().toString());
        params.put("sms_type", "login");
        params.put("ApiSmstokentime", timestamp);// 时间戳
        params.put("ApiSmstoken", jmStr);// 加密字符串
        MyOkHttp.get().post(info.reg_send_sms, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                try {
                    String str = response.getString("status");
                    String json = response.getString("msg");
                    if (str.equals("1")) {
                        SmartToast.showInfo(json);
                        msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } else {
                        txt_getcode.setEnabled(true);
                        SmartToast.showInfo(json);
                        System.out.println("json--------" + json);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络错误,请检查网络设置");
            }
        });
    }

    //验证码注册登录
    public void login() {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("username", et_mobile.getText().toString());
        params.put("mobile_vcode", et_getcode.getText().toString());
        params.put("phone_name", push_id);
        params.put("phone_type", "1");
        if (!NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId())){
            params.put("community_id",sharePrefrenceUtil.getXiaoQuId());
        }
        MyOkHttp.get().post(info.login_verify, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                System.out.println("response***" + response);
                try {
                    int status = response.getInt("status");
                    String msg = response.getString("msg");
                    if (status == 1) {
                        ModelLogin loginBean = (ModelLogin) JsonUtil.getInstance().parseJsonFromResponse(response, ModelLogin.class);
                        if (loginBean != null) {
                            MyCookieStore.Circle_notify = 3;
                            SharedPreferences preferences1 = LoginVerifyCodeActivity.this.getSharedPreferences("login", 0);
                            SharedPreferences.Editor editor = preferences1.edit();
                            editor.putString("login_uid", loginBean.getUid());
                           // editor.putString("login_type", loginBean.getUtype());
                            editor.putString("login_type", "1");
                            editor.putString("login_username", et_mobile.getText().toString());
                            editor.putString("password", loginBean.getPwd());
                            editor.putString("is_wuye", loginBean.getIs_bind_property());
                            editor.putString("login_verifytype", "vcode");
                            //TODO加入token
                            editor.putString("token", loginBean.getToken());
                            editor.putString("tokenSecret", loginBean.getTokenSecret());
                            ApiHttpClient.TOKEN = loginBean.getToken();
                            ApiHttpClient.TOKEN_SECRET = loginBean.getTokenSecret();
                            editor.commit();
                            //判断小区id
                            get_community_id(loginBean.getCommunity_id());

                            closeInputMethod();
                            finish();
                        }
                        //保存到modelUser中
                        ModelUser user = (ModelUser) JsonUtil.getInstance().parseJsonFromResponse(response, ModelUser.class);
                        // 把数据保存在数据库中
                        if (user!=null){
                            UserSql.getInstance().clear();
                            UserSql.getInstance().insertObject(user);
                            BaseApplication.setUser(user);
                            EventBus.getDefault().post(loginBean);
                        }
                    } else {
                        txt_btn.setText("登录");
                        txt_btn.setEnabled(true);
                        txt_btn.setTextColor(getResources().getColor(R.color.white));
                        txt_btn.setBackground(getResources().getDrawable(R.drawable.allshape_orange_10));
                        SmartToast.showInfo(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                txt_btn.setText("登录");
                txt_btn.setEnabled(true);
                txt_btn.setTextColor(getResources().getColor(R.color.white));
                txt_btn.setBackground(getResources().getDrawable(R.drawable.allshape_orange_10));
                SmartToast.showInfo("网络异常，请检查网络错误");
            }
        });
    }

    /**
     * 判断小区id是否有值
     */
    private void get_community_id(String community_id) {
        if (!NullUtil.isStringEmpty(community_id)) {
            getsubmitCommunityId();
        }

    }

    //提交小区id
    private void getsubmitCommunityId() {
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId())){
            params.put("community_id", sharePrefrenceUtil.getXiaoQuId());
        }
        MyOkHttp.get().post(ApiHttpClient.SELECT_COMMUNITY, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    /**
     * 微信登录回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void WXCallback(BaseResp baseResp) {
        int result = 0;
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                // result = R.string.errcode_success;
                SharedPreferences preferences1 = this.getSharedPreferences("jpush_id", 0);
                push_id = preferences1.getString("reg_id", "");
                String code = ((SendAuth.Resp) baseResp).code;
                //                //获取用户信息
                getWXLogin(code, push_id);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                finish();
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                finish();
                break;
            default:
                finish();
                result = R.string.errcode_unknown;
                break;
        }
        //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    //微信登录第2步没有绑定验证绑定提交
    private void getWXsubmit(String info) {
        SharedPreferences preferences1 = this.getSharedPreferences("jpush_id", 0);
        String push_id = preferences1.getString("reg_id", "");
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("username", et_mobile.getText().toString().trim());
        params.put("recommended_uid", et_yq.getText().toString().trim());
        params.put("phone_name", push_id);
        params.put("mobile_vcode", et_getcode.getText().toString().trim());
        params.put("phone_type", "1");
        params.put("wx_userinfo", info);
        if (!NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId())){
            params.put("community_id",sharePrefrenceUtil.getXiaoQuId());
        }
        MyOkHttp.get().post(ApiHttpClient.WX_BIND, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                System.out.println("json#########" + response);
                hideDialog(smallDialog);
                try {
                    int status = response.getInt("status");
                    if (status == 1) {
                        ModelLogin loginBean = (ModelLogin) JsonUtil.getInstance().parseJsonFromResponse(response, ModelLogin.class);
                        SharedPreferences preferences1 = LoginVerifyCodeActivity.this.getSharedPreferences("login", 0);
                        SharedPreferences.Editor editor = preferences1.edit();
                        editor.putString("login_uid", loginBean.getUid());
                      //  editor.putString("login_type", loginBean.getUtype());
                        editor.putString("login_type", "1");
                        editor.putString("login_username", et_mobile.getText().toString());
                        editor.putString("password", loginBean.getPwd());
                        editor.putString("is_wuye", loginBean.getIs_bind_property());
                        editor.putString("login_verifytype", "vcode");
                        //TODO加入token
                        editor.putString("token", loginBean.getToken());
                        editor.putString("tokenSecret", loginBean.getTokenSecret());
                        ApiHttpClient.TOKEN = loginBean.getToken();
                        ApiHttpClient.TOKEN_SECRET = loginBean.getTokenSecret();
                        editor.commit();

                        //判断小区id
                        get_community_id(loginBean.getCommunity_id());
                        closeInputMethod();
                        finish();
                        //保存到modelUser中
                        ModelUser user = (ModelUser) JsonUtil.getInstance().parseJsonFromResponse(response, ModelUser.class);
                        // 把数据保存在数据库中
                        if (user!=null){
                            UserSql.getInstance().clear();
                            UserSql.getInstance().insertObject(user);
                            BaseApplication.setUser(user);
                            EventBus.getDefault().post(loginBean);
                        }
                    } else {
                        SmartToast.showInfo(response.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    //微信登录第一步
    private void getWXLogin(String code, String push_id) {
        //showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("phone_name", push_id);
        params.put("phone_type", "1");
        params.put("code", code);

        MyOkHttp.get().post(ApiHttpClient.WX_LOGIN, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                System.out.println("json#########" + response);
                hideDialog(smallDialog);
                String json = JSON.toJSONString(response);
                try {
                    int status = response.getInt("status");
                    if (status == 100) {
                        String datainfo = response.getString("data");
                        login_type = "wx";//微信登录验证
                        wx_info = datainfo;
                        ly_bottom.setVisibility(View.GONE);
                        tv_txt2.setVisibility(View.GONE);
                        tv_txt1.setVisibility(View.GONE);
                        tv_txt3.setVisibility(View.VISIBLE);
                    } else if (status == 1) {//不用绑定  直接登录成功
                        ModelLogin loginBean = (ModelLogin) JsonUtil.getInstance().parseJsonFromResponse(response, ModelLogin.class);
                        // if (loginbean != null) {

                        MyCookieStore.Circle_notify = 3;
                        SharedPreferences preferences1 = LoginVerifyCodeActivity.this.getSharedPreferences("login", 0);
                        SharedPreferences.Editor editor = preferences1.edit();
                        editor.putString("login_uid", loginBean.getUid());
                     //   editor.putString("login_type", loginBean.getUtype());
                        editor.putString("login_type", "1");
                        editor.putString("login_username", loginBean.getUsername());
                        // editor.putString("password", loginBean.getPwd());
                        editor.putString("is_wuye", loginBean.getIs_bind_property());
                        editor.putString("login_verifytype", "vcode");
                        //TODO加入token
                        editor.putString("token", loginBean.getToken());
                        editor.putString("tokenSecret", loginBean.getTokenSecret());
                        ApiHttpClient.TOKEN = loginBean.getToken();
                        ApiHttpClient.TOKEN_SECRET = loginBean.getTokenSecret();
                        editor.commit();
                        get_community_id(loginBean.getCommunity_id());
                        finish();

                        ModelUser user = (ModelUser) JsonUtil.getInstance().parseJsonFromResponse(response, ModelUser.class);
                        // 把数据保存在数据库中
                        if (user!=null){
                            UserSql.getInstance().clear();
                            UserSql.getInstance().insertObject(user);
                            BaseApplication.setUser(user);
                            //发送eventbus
                            EventBus.getDefault().post(loginBean);
                        }
                        // }
                    } else {
                        SmartToast.showInfo(response.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    /**
     * 获取本地缓存
     */
    private void getSharedPre() {
        sharePrefrenceUtil = new SharePrefrenceUtil(this);
        preferences = LoginVerifyCodeActivity.this.getSharedPreferences("jpush_id", 0);
        push_id = preferences.getString("reg_id", "");
        preferencesLogin = LoginVerifyCodeActivity.this.getSharedPreferences("login", 0);
        login_shop = preferencesLogin.getString("login_shop", "");
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //可以根据多个请求代码来作相应的操作
        if (20 == resultCode) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            txt_getcode.setText("重新发送");
            txt_getcode.setEnabled(true);
            txt_getcode.setOnClickListener(LoginVerifyCodeActivity.this);
            txt_getcode.setTextColor(getResources().getColor(R.color.colorPrimary));
            txt_getcode.setBackground(getResources().getDrawable(R.drawable.cornes_bgwhite_edge_orange));
          /*  txt_btn.setText("登录");
            txt_btn.setTextColor(getResources().getColor(R.color.white));
            txt_btn.setBackground(getResources().getDrawable(R.drawable.allshape_orange_10));
            txt_btn.setEnabled(true);*/
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished / 1000 >= 1) {
                txt_getcode.setEnabled(false);
            }
            txt_getcode.setTextColor(getResources().getColor(R.color.title_third_color));
            txt_getcode.setBackground(getResources().getDrawable(R.drawable.cornes_bgcode_login_gray));
            txt_getcode.setText(millisUntilFinished / 1000 + "秒后重发");
        }

    }

    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // imm.toggleSoftInput(0,
            // InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(et_mobile.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = StringUtils.intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }
}
