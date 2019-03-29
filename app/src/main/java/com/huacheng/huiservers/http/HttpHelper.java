package com.huacheng.huiservers.http;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.HomeActivity;
import com.huacheng.huiservers.db.UserSql;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyCookieStore_New;
import com.huacheng.huiservers.ui.base.ActivityStackManager;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.UIUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class HttpHelper {
    HttpUtils utils = new HttpUtils(100000);
    public Context context;
    public String tag;
    public String str;
    public static String murl;
    public static String url="";
    private SharePrefrenceUtil sharePrefrenceUtil;
    private Dialog mDialog;
    public static String token;
    public static String tokenSecret;
    public static SharedPreferences login_pre;

    public HttpHelper(String url, RequestParams params, Context context) {
        this.context = context;
        this.url = url;
        if (NullUtil.isStringEmpty(token)||NullUtil.isStringEmpty(tokenSecret)){
            login_pre = context.getSharedPreferences("login", 0);
            token = login_pre.getString("token", "");
            tokenSecret = login_pre.getString("tokenSecret", "");
        }
         dopost(params,url);

    }
    public HttpHelper(String url, RequestParams params, Context context, Dialog dialog) {
        this.context = context;
        this.url = url;
        this.mDialog=dialog;
        if (NullUtil.isStringEmpty(token)||NullUtil.isStringEmpty(tokenSecret)){
            login_pre = context.getSharedPreferences("login", 0);
            token = login_pre.getString("token", "");
            tokenSecret = login_pre.getString("tokenSecret", "");
        }
        dopost(params,url);

    }

    public HttpHelper(String url, Context context) {
        this.context = context;
     //   murl = url;
        if (NullUtil.isStringEmpty(token)||NullUtil.isStringEmpty(tokenSecret)){
            login_pre = context.getSharedPreferences("login", 0);
            token = login_pre.getString("token", "");
            tokenSecret = login_pre.getString("tokenSecret", "");
        }
        dpGet(url);
    }


    private void dpGet(String url2) {

        sharePrefrenceUtil = new SharePrefrenceUtil(context);
        if (!NullUtil.isStringEmpty(token)&&!NullUtil.isStringEmpty(tokenSecret)){
            ApiHttpClient.TOKEN=token;
            ApiHttpClient.TOKEN_SECRET=tokenSecret;
            url2=url2+"/token/"+ApiHttpClient.TOKEN+"/tokenSecret/"+ApiHttpClient.TOKEN_SECRET;
        }
        if (!NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId())){
            url2=url2+"/hui_community_id/" +sharePrefrenceUtil.getXiaoQuId();
        }
        utils.configCookieStore(MyCookieStore.cookieStore);
        utils.configCurrentHttpCacheExpiry(1000);
        final String finalUrl = url2;
        utils.send(HttpMethod.GET, url2, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    DefaultHttpClient dh = (DefaultHttpClient) utils.getHttpClient();
                    if (MyCookieStore.cookieStore==null){
                        MyCookieStore.cookieStore = dh.getCookieStore();
                        MyCookieStore_New.cookieStore = dh.getCookieStore();
                    }
                    JSONObject jsonObject;
                    jsonObject = new JSONObject(responseInfo.result);
                    String status = jsonObject.getString("status");
                    String data = jsonObject.getString("data");
                    String msg = jsonObject.getString("msg");
                    if (status!=null){
                        //加入账号互顶
                        if (status.equals("2")){

                            SharedPreferences preferences1 = context.getSharedPreferences("login", 0);
                            preferences1.edit().clear().commit();
                            ApiHttpClient.setTokenInfo(null, null);
                            // XToast.makeText(this, "登录失效", XToast.LENGTH_SHORT).show();
                            com.huacheng.libraryservice.utils.ToastUtils.showShort(context,"登录失效");
                            HttpHelper.tokenSecret=null;
                            HttpHelper.token=null;
                            BaseApplication.removeALLActivity_();
                            ActivityStackManager.getActivityStackManager().finishAllActivity();
                            context.startActivity( new Intent(context, HomeActivity.class));
                            context.startActivity(new Intent(context, LoginVerifyCodeActivity.class));
                            //清除数据库
                            UserSql.getInstance().clear();
                            BaseApplication.setUser(null);
                            return;
                        }else if (status.equals("1")){
                            //我也没办法只能这样过滤，放过我吧，我尽力了 以后写的时候用Okhttp
                            setData(responseInfo.result);
                        } else if (finalUrl.contains(Url_info.my_wallet)){
                            setData(responseInfo.result);
                        }else if (finalUrl.contains(Url_info.my_renvation)){
                            setData(responseInfo.result);
                        }else if (finalUrl.contains(Url_info.pro_discount_list)){
                            setData(responseInfo.result);
                        }else if (finalUrl.contains(Url_info.goods_search)){
                            setData(responseInfo.result);
                        }else if (finalUrl.contains(Url_info.pro_list)){
                            setData(responseInfo.result);
                        }else if (finalUrl.contains("property/")||finalUrl.contains("activity/")){
                            setData(responseInfo.result);
                        }else if (finalUrl.contains(Url_info.getSocialList)){//邻里列表
                            setData(responseInfo.result);
                        }else if (finalUrl.contains(Url_info.goods_review)){//商品评价列表
                            setData(responseInfo.result);
                        }else {
                            UIUtils.showToastSafe(msg);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                      requestFailure( error, msg);
            }
        });

    }

    private void dopost(RequestParams params2,String url2) {
        sharePrefrenceUtil = new SharePrefrenceUtil(context);
        utils.configCookieStore(MyCookieStore.cookieStore);
        if (!NullUtil.isStringEmpty(token)&&!NullUtil.isStringEmpty(tokenSecret)){
            ApiHttpClient.TOKEN=token;
            ApiHttpClient.TOKEN_SECRET=tokenSecret;
            if (params2==null){
                params2=new RequestParams();
            }
            params2.addBodyParameter("token",ApiHttpClient.TOKEN+"");
            params2.addBodyParameter("tokenSecret",ApiHttpClient.TOKEN_SECRET+"");
        }
        if (!NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId())){
            params2.addBodyParameter("hui_community_id",sharePrefrenceUtil.getXiaoQuId()+"");
        }
        utils.configSoTimeout(5 * 1000);
        utils.configTimeout(5 * 1000);
        utils.configCurrentHttpCacheExpiry(1000 * 10);
        utils.send(HttpMethod.POST, url, params2,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            DefaultHttpClient dh = (DefaultHttpClient) utils.getHttpClient();
                            if (MyCookieStore.cookieStore==null){
                                MyCookieStore.cookieStore = dh.getCookieStore();
                                MyCookieStore_New.cookieStore = dh.getCookieStore();
                            }
                            System.out.println("数据：" + responseInfo.result);
                            JSONObject jsonObject;
                            jsonObject = new JSONObject(responseInfo.result);
                            String status = jsonObject.getString("status");
                            String data = jsonObject.getString("data");
                            String msg = jsonObject.getString("msg");
                            //加入账号互顶
                            if (status!=null){
                                if (status.equals("2")){
//                                    SharedPreferences preferences1 = context.getSharedPreferences("login", 0);
//                                    preferences1.edit().clear().commit();
//                                    UIUtils.showToastSafe(msg);
//                                    ApiHttpClient.setTokenInfo(null, null);
//                                    context.startActivity(new Intent(context, LoginVerifyCodeActivity.class));
//                                    requestFailure(null,msg);
//                                    token=null;
//                                    tokenSecret=null;
//                                    login_pre=null;

                                    SharedPreferences preferences1 = context.getSharedPreferences("login", 0);
                                    preferences1.edit().clear().commit();
                                    ApiHttpClient.setTokenInfo(null, null);
                                    // XToast.makeText(this, "登录失效", XToast.LENGTH_SHORT).show();
                                    com.huacheng.libraryservice.utils.ToastUtils.showShort(context,"登录失效");
                                    HttpHelper.tokenSecret=null;
                                    HttpHelper.token=null;
                                    login_pre=null;
                                    BaseApplication.removeALLActivity_();
                                    ActivityStackManager.getActivityStackManager().finishAllActivity();
                                    context.startActivity( new Intent(context, HomeActivity.class));
                                    context.startActivity(new Intent(context, LoginVerifyCodeActivity.class));
                                    //清除数据库
                                    UserSql.getInstance().clear();
                                    BaseApplication.setUser(null);
                                    return;
                                }else if (status.equals("1")){
                                    setData(responseInfo.result);
                                }else {
                                    if (mDialog!=null&&mDialog.isShowing()){
                                        mDialog.dismiss();
                                    }
                                    //我也没办法只能这样过滤，放过我吧，我尽力了 以后写的时候用Okhttp
                                    if (url.equals(Url_info.version_update)){//更新
                                        setData(responseInfo.result);
                                    }else if (url.equals(Url_info.coupon_over_40)){//优惠券列表
                                        setData(responseInfo.result);
                                    }else if (url.contains("property/")||url.contains("activity/")){//服务订单列表
                                        setData(responseInfo.result);
                                    }else if (url.contains(Url_info.get_Advertising)){//我的装修
                                        setData(responseInfo.result);
                                    }else if (url.contains("old_index_new")){//老年风采信息
                                        setData(responseInfo.result);
                                    } else if (url.contains(Url_info.get_user_Social)){//我的邻里
                                        setData(responseInfo.result);
                                    }else if (url.contains(Url_info.get_memorandum)){//新房手册备注
                                        setData(responseInfo.result);
                                    }else if (url.contains(Url_info.confirm_order_payment)){//支付成功返回
                                        setData(responseInfo.result);
                                    }else if (url.contains("userCenter/get_user_address/")){//收货地址
                                        setData(responseInfo.result);
                                    } else if (url.contains(Url_info.pay_property_order)) {// 物业的支付
                                        setData(responseInfo.result);
                                    } else if (url.contains(Url_info.pay_shopping_order)) {// 是从购物流程一路付款成功的
                                        setData(responseInfo.result); // 购物的

                                    } else if (url.contains(Url_info.pay_activity_order)) { // 活动的
                                        setData(responseInfo.result);
                                    } else if (url.contains(Url_info.pay_face_order) ){// 当面付
                                        setData(responseInfo.result);
                                    } else if (url.contains(Url_info.pay_wired_order) ) { // 有线缴费
                                        setData(responseInfo.result);
                                    }else if (url.contains(Url_info.service_new_pay) ){//服务支付
                                        setData(responseInfo.result);
                                    }else if (url.contains(Url_info.pay_shopping_check) ){//一卡通支付
                                        setData(responseInfo.result);
                                    }else if (url.contains(Url_info.shop_order_accept) ){//确认收货
                                        setData(responseInfo.result);
                                    }else if (url.contains(Url_info.del_order) ){//删除订单
                                        setData(responseInfo.result);
                                    }else if (url.contains(Url_info.shop_index_new) ){//商城首页
                                        setData(responseInfo.result);
                                    } else if (url.contains(Url_info.get_Advertising) ){//商城中部广告
                                        setData(responseInfo.result);
                                    }else if (url.contains(Url_info.get_social) ){//商城中部广告
                                        setData(responseInfo.result);
                                    } else if (url.contains(Url_info.submit_order) ){//提交商城订单
                                        setData(responseInfo.result);
                                    } else if (url.contains(Url_info.checkIsAjb) ){//访客邀请
                                        setData(responseInfo.result);
                                    } else if (url.contains(Url_info.coupon_add) ){//领取优惠券
                                        setData(responseInfo.result);
                                    }else if (url.contains(Url_info.area_topclass) ){//获取商品分类
                                        setData(responseInfo.result);
                                    }else {
                                        UIUtils.showToastSafe(msg);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        requestFailure( error, msg);

                    }
                });
    }

    protected abstract void setData(String json);

    protected  abstract void requestFailure(Exception error, String msg);
}
