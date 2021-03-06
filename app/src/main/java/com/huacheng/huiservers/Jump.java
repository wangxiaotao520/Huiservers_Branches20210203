package com.huacheng.huiservers;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.RadioGroup;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.dialog.SmallDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelIsOld;
import com.huacheng.huiservers.model.ModelLogin;
import com.huacheng.huiservers.ui.center.AboutActivity;
import com.huacheng.huiservers.ui.center.CouponListActivity;
import com.huacheng.huiservers.ui.center.CouponToShopUseActivity;
import com.huacheng.huiservers.ui.center.MedicalWebViewActivity;
import com.huacheng.huiservers.ui.circle.CircleDetailsActivity;
import com.huacheng.huiservers.ui.fragment.presenter.PropertyPrester;
import com.huacheng.huiservers.ui.index.charge.ChargeScanActivity;
import com.huacheng.huiservers.ui.index.huodong.EducationActivity;
import com.huacheng.huiservers.ui.index.huodong.EducationListActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldHardwareActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldServiceIndexActivity;
import com.huacheng.huiservers.ui.index.party.CommunistPartyIndexActivity;
import com.huacheng.huiservers.ui.index.property.HouseListActivity;
import com.huacheng.huiservers.ui.index.property.PropertyBindHomeActivity;
import com.huacheng.huiservers.ui.index.request.CommitRequestActivity;
import com.huacheng.huiservers.ui.index.vote.VoteIndexActivity;
import com.huacheng.huiservers.ui.index.vote.VoteVlogIndexActivity;
import com.huacheng.huiservers.ui.index.workorder.commit.PublicWorkOrderCommitActivity;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.servicenew.ui.MerchantServiceListActivity;
import com.huacheng.huiservers.ui.servicenew.ui.ServiceDetailActivity;
import com.huacheng.huiservers.ui.servicenew.ui.shop.ServiceStoreActivity;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.huiservers.ui.shop.ShopListActivity;
import com.huacheng.huiservers.ui.shop.ShopSecKillListActivity;
import com.huacheng.huiservers.ui.shop.ShopZQListActivity;
import com.huacheng.huiservers.ui.shop.StoreIndexActivity;
import com.huacheng.huiservers.utils.PermissionUtils;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Jump {

    @ViewInject(R.id.rg_content_fragment)
    public static RadioGroup mRadioGroup;// 下面的radioGroup

    private Context mContext;
    private SharePrefrenceUtil sharePrefrenceUtil;
    private SharedPreferences preferencesLogin;
    private SmallDialog smallDialog;

    public static final int PHONE_STATE_REQUEST_CODE = 101;//获取读取手机权限，回调在 A中
    private String phone, urlh5, url_type, login_uid, type;
    private String login_type, id, title, content, img, call_link, login_mobile, is_wuye, url_origin;

    public Jump(final Context context, String type, String url, String img, String name) {//url 不能为空

        this.mContext = context;
        this.url_type = type;
        sharePrefrenceUtil = new SharePrefrenceUtil(context);
        preferencesLogin = context.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");
        login_uid = preferencesLogin.getString("login_uid", "");
        is_wuye = preferencesLogin.getString("is_wuye", "");
        login_mobile = preferencesLogin.getString("login_username", "");
        url_origin = url;
        //没有绑定跳转绑定界面
        if (!"".equals(url)) {
            phone = url;
            if (!type.equals("26")) {
                url = MyCookieStore.SERVERADDRESS + url;
            }
            if (type.equals("index")) {//跳转到app首页
                Intent intent = new Intent(mContext, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } else if (type.equals("1")) {//跳转到商城列表
                if (url.indexOf("is_limit") != -1) {//接口中包含限购字段
                    System.out.println("包含");
                    String cateid = url.substring(url.lastIndexOf("/") + 1, url.length());
                    System.out.println("jump1*******" + cateid);
                    Intent intent = new Intent(mContext, ShopSecKillListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("cateID", cateid);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                } else {
                    String cateid = url.substring(url.lastIndexOf("/") + 1, url.length());
                    System.out.println("不包含");
                    Intent intent = new Intent(mContext, ShopListActivity.class);
                    intent.putExtra("cateID", cateid);
                    mContext.startActivity(intent);
                }

            } else if (type.equals("2")) {//跳转到商城详情
                //跳转到详情页
                final String strurl = url;
                Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", url);
                mContext.startActivity(intent);

            } else if (type.equals("3")) {//跳转到活动列表
                String urlnew = url;
                if (!NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId())) {
                    url = url + "/m_id/" + sharePrefrenceUtil.getXiaoQuId();
                }
                Intent intent = new Intent();
                intent.setClass(mContext, EducationListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", url);
                intent.putExtra("urlnew", urlnew);
                intent.putExtra("name", name);
                mContext.startActivity(intent);
//
            } else if (type.equals("4")) {//跳转到活动详情
                Intent intent = new Intent();
                intent.setClass(mContext, EducationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", url);
                intent.putExtra("name", name);
                mContext.startActivity(intent);

            } else if (type.equals("5")) { //跳转到维修
                preferencesLogin = mContext.getSharedPreferences("login", 0);
                login_type = preferencesLogin.getString("login_type", "");
                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, LoginVerifyCodeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    //判断是否物业绑定
                    smallDialog = new SmallDialog(mContext);
                    smallDialog.show();
                    MyOkHttp.get().post(ApiHttpClient.CHECK_BIND_PROPERTY, null, new JsonResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, JSONObject response) {
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                            if (JsonUtil.getInstance().isSuccess(response)) {
                                ModelLogin bean = (ModelLogin) JsonUtil.getInstance().parseJsonFromResponse(response, ModelLogin.class);
                                if (bean != null && bean.getIs_bind_property().equals("2")) {
                                    Intent intent = new Intent(mContext, PublicWorkOrderCommitActivity.class);
                                    mContext.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(mContext, PropertyBindHomeActivity.class);
                                    intent.putExtra("wuye_type", "person_repair");
                                    mContext.startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, String error_msg) {
                            SmartToast.showInfo("网络异常,请检查网络设置");
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                        }
                    });
                }
            } else if (type.equals("6")) {//跳转到优惠券
                urlh5 = url;
                id = url.substring(url.lastIndexOf("/") + 1, url.length());
                getShare(context, id, "2");

            } else if (type.equals("7")) {//跳转到拨打电话
                new CommomDialog(mContext, R.style.my_dialog_DimEnabled, "确认拨打电话？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"
                                    + phone));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                            dialog.dismiss();
                        }
                    }
                }).show();
            } else if (type.equals("8")) {//个人中心优惠券列表
                preferencesLogin = mContext.getSharedPreferences("login", 0);
                login_type = preferencesLogin.getString("login_type", "");
                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, LoginVerifyCodeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(mContext, CouponListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("url", url);
                    intent.putExtra("tag", "jump");
                    mContext.startActivity(intent);
                }
            } else if (type.equals("9")) {//跳转个人中心优惠券详情
                preferencesLogin = mContext.getSharedPreferences("login", 0);
                login_type = preferencesLogin.getString("login_type", "");
                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, LoginVerifyCodeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    ;
                } else {
                    Intent intent = new Intent();
                    intent.setClass(mContext, CouponToShopUseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    id = url.substring(url.lastIndexOf("/") + 1, url.length());
                    intent.putExtra("coupon_id", id);
                    mContext.startActivity(intent);
                }
            } else if (type.equals("10")) {//跳转到软文
                urlh5 = url;
                id = url.substring(url.lastIndexOf("/") + 1, url.length());
                getShare(context, id, "1");

            } else if (type.equals("11")) {
                // : 2018/8/20  现在不做任何作用
               /* id = url.substring(url.lastIndexOf("/") + 1, url.length());
                Intent intent = new Intent(mContext, ZhengWuDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                bundle.putString("tag", "wuye");
                intent.putExtras(bundle);
                mContext.startActivity(intent);*/

            } else if (type.equals("12")) {//圈子列表
                // CircleFragment fragment=new CircleFragment();
               /* FragmentManager fmanger=getSupportFragmentManager();
                FragmentTransaction transaction=fmanger.beginTransaction();
                transaction.replace(R.id.rb_content_fragment_quanzi,new CircleFragment5());
                transaction.commit();*/

            } else if (type.equals("13")) {//圈子详情
                String isPro = "0";
                String id = "0";
                String[] split = url.split("/");
                for (int i = 0; i < split.length; i++) {
                    if ("id".equals(split[i])) {
                        id = split[i + 1];
                    }
                    if ("is_pro".equals(split[i])) {
                        isPro = split[i + 1];
                    }
                }
                Intent intent = new Intent(mContext, CircleDetailsActivity.class);
                Bundle bundle = new Bundle();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                bundle.putString("id", id);
                bundle.putString("mPro", isPro);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            } else if (type.equals("20")) {  //广告位跳转至东森易购

                if (!PermissionUtils.checkPermissionGranted(context, Manifest.permission.READ_PHONE_STATE)) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.READ_PHONE_STATE}, PHONE_STATE_REQUEST_CODE);
                } else {
                    String uuid = "";
                    uuid = TDevice.getIMEI(mContext);
                    preferencesLogin = mContext.getSharedPreferences("login", 0);
                    login_type = preferencesLogin.getString("login_type", "");
                    if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                        Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        if (login_type.equals("1")) {//个人
                            String sign = "hshObj";
                            Intent intent = new Intent(context, AboutActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("tag", "dsyg");
                            bundle.putString("strHouse",
                                    "http://www.dsyg42.com/ec/app_index?username=" + login_mobile + "&sign=" + sign + "&uuid=" + uuid);
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        } else {
                            SmartToast.showInfo("当前账号不是个人账号");

                        }
                    }
                }
            } else if (type.equals("22")) {//服务模块_服务列表
                String cateid = "";
                String i_id = "";
                Intent intent = new Intent();
                int i_idLastIndex = url.lastIndexOf("/i_id/");
                int cateLastIndex = url.lastIndexOf("category/");

                if (i_idLastIndex >= 0 && cateLastIndex >= 0) {
                    cateid = url.substring(cateLastIndex + 9, i_idLastIndex);
                    if (!NullUtil.isStringEmpty(cateid)) {
                        intent.putExtra("sub_id", cateid);
                    }
                    i_id = url.substring(i_idLastIndex + 6, url.length());
                    if (!NullUtil.isStringEmpty(i_id)) {
                        intent.putExtra("store_id", i_id);
                    }
                } else {
                    if (i_idLastIndex >= 0) {
                        i_id = url.substring(i_idLastIndex + 6, url.length());
                        if (!NullUtil.isStringEmpty(i_id)) {
                            intent.putExtra("store_id", i_id);
                        }
                    }
                    if (cateLastIndex >= 0) {
                        cateid = url.substring(cateLastIndex + 9, url.length());
                        if (!NullUtil.isStringEmpty(cateid)) {
                            intent.putExtra("sub_id", cateid);
                        }
                    }

                }

                intent.setClass(mContext, MerchantServiceListActivity.class);
                intent.putExtra("tabType", "service");
                mContext.startActivity(intent);

            } else if (type.equals("23")) {//服务模块_服务详情
                String cateid = url.substring(url.lastIndexOf("/") + 1, url.length());
                Intent intent = new Intent();
                intent.setClass(mContext, ServiceDetailActivity.class);
                intent.putExtra("service_id", cateid);
                mContext.startActivity(intent);

            } else if (type.equals("24")) {//服务模块_商家列表
                String cateid = url.substring(url.lastIndexOf("category/") + 9, url.length());
                Intent intent = new Intent();
                intent.setClass(mContext, MerchantServiceListActivity.class);
                intent.putExtra("tabType", "");
                if (!NullUtil.isStringEmpty(cateid)) {
                    intent.putExtra("sub_id", cateid);
                }

                mContext.startActivity(intent);

            } else if (type.equals("25")) {//服务模块_商家详情
                String cateid = url.substring(url.lastIndexOf("/") + 1, url.length());
                Intent intent = new Intent(mContext, ServiceStoreActivity.class);
                intent.putExtra("store_id", cateid);
                mContext.startActivity(intent);
            } else if (type.equals("26")) {//所有直接展示webview的
                //医疗
                Intent intent = new Intent();
                intent.setClass(mContext, MedicalWebViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", url);
                intent.putExtra("name", name);
                mContext.startActivity(intent);

            } else if (type.equals("32")) {
                //店铺
                Intent intent = new Intent();
                String cateid = url.substring(url.lastIndexOf("/") + 1, url.length());
                intent = new Intent(mContext, StoreIndexActivity.class);
                intent.putExtra("store_id", cateid);
                mContext.startActivity(intent);
            } else if (type.equals("33")) {
                //专区
                Intent intent = new Intent();
                String cateid = url.substring(url.lastIndexOf("/") + 1, url.length());
                intent = new Intent(mContext, ShopZQListActivity.class);
                intent.putExtra("id", cateid);
                mContext.startActivity(intent);
            } else if (type.equals("30")) {//活动投票
                if ("".equals(login_type) || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, VoteIndexActivity.class);
                    mContext.startActivity(intent);
                }
            } else if (type.equals("34")) {
                //公共报修
                if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
                    if (NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId() + "")) {
                        SmartToast.showInfo("该小区暂未开通此服务");

                    }
                    //判断是否物业绑定
                    smallDialog = new SmallDialog(mContext);
                    smallDialog.show();
                    MyOkHttp.get().post(ApiHttpClient.CHECK_BIND_PROPERTY, null, new JsonResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, JSONObject response) {
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                            if (JsonUtil.getInstance().isSuccess(response)) {
                                ModelLogin bean = (ModelLogin) JsonUtil.getInstance().parseJsonFromResponse(response, ModelLogin.class);
                                if (bean != null && bean.getIs_bind_property().equals("2")) {
                                    Intent intent = new Intent(mContext, PublicWorkOrderCommitActivity.class);
                                    mContext.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(mContext, PropertyBindHomeActivity.class);
                                    intent.putExtra("wuye_type", "public_repair");
                                    mContext.startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, String error_msg) {
                            SmartToast.showInfo("网络异常,请检查网络设置");
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                        }
                    });

                } else {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                }
            } else if (type.equals("35")) {
                //自用报修
                if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
                    if (NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId() + "")) {
                        SmartToast.showInfo("该小区暂未开通此服务");

                    }
                    //判断是否物业绑定
                    smallDialog = new SmallDialog(mContext);
                    smallDialog.show();
                    MyOkHttp.get().post(ApiHttpClient.CHECK_BIND_PROPERTY, null, new JsonResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, JSONObject response) {
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                            if (JsonUtil.getInstance().isSuccess(response)) {
                                ModelLogin bean = (ModelLogin) JsonUtil.getInstance().parseJsonFromResponse(response, ModelLogin.class);
                                if (bean != null && bean.getIs_bind_property().equals("2")) {
//                                    Intent intent = new Intent(mContext, PersonalWorkOrderCommitActivity.class);
                                    Intent intent = new Intent(mContext, HouseListActivity.class);
                                    intent.putExtra("type",1);
                                    intent.putExtra("wuye_type", "ziyongbaoxiu");
                                    mContext.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(mContext, PropertyBindHomeActivity.class);
                                    intent.putExtra("wuye_type", "person_repair");
                                    mContext.startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, String error_msg) {
                            SmartToast.showInfo("网络异常,请检查网络设置");
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                        }
                    });

                } else {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                }
            } else if (type.equals("36")) {
                String url_param = url_origin;
                String id = "0";//计划id
                String[] split = url_param.split("/");
                for (int i = 0; i < split.length; i++) {
                    if ("id".equals(split[i])) {
                        id = split[i + 1];
                    }
                }

                //VLOG 投票
                if ("".equals(login_type) || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, VoteVlogIndexActivity.class);
                    intent.putExtra("id", id);
                    mContext.startActivity(intent);
                }
//
//             //   String url_new = ApiHttpClient.API_URL+ApiHttpClient.API_VERSION+url_origin;
//
//                if ( ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
//                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
//                    mContext.startActivity(intent);
//                } else {
//                    Intent intent = new Intent(mContext, WebViewVoteActivity.class);
//                  //  intent.putExtra("url",url_new+"/token/"+ApiHttpClient.TOKEN+"/tokenSecret/"+ApiHttpClient.TOKEN_SECRET);
//                    intent.putExtra("url_param",url_param);
//                    mContext.startActivity(intent);
//                }
            } else if (type.equals("37")) {
                //调查问卷
                String id = "0";//计划id
                String[] split = url.split("/");
                for (int i = 0; i < split.length; i++) {
                    if ("id".equals(split[i])) {
                        id = split[i + 1];
                    }
                }
                if ("".equals(login_type) || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, HouseListActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("wuye_type", "investigate");
                    intent.putExtra("id", id);
                    mContext.startActivity(intent);
                }
            } else if (type.equals("38")) {
                if ("".equals(login_type) || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, HouseListActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("wuye_type", "permit");
                    mContext.startActivity(intent);
                }
            } else if (type.equals("39")) {
                //社区党建
                Intent intent = new Intent(mContext, CommunistPartyIndexActivity.class);
                mContext.startActivity(intent);

            } else if (type.equals("40")) {
                //商家服务详情
                String id = "0";//计划id
                String[] split = url.split("/");
                for (int i = 0; i < split.length; i++) {
                    if ("id".equals(split[i])) {
                        id = split[i + 1];
                    }
                }
                Intent intent = new Intent(mContext, ServiceStoreActivity.class);
                intent.putExtra("store_id", id);
                mContext.startActivity(intent);

            }else if (type.equals("41")) {
                //智能硬件
                if ("".equals(login_type) || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                } else {
                    smallDialog = new SmallDialog(mContext);
                    smallDialog.show();
                    MyOkHttp.get().post(ApiHttpClient.IS_OLD, null, new JsonResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, JSONObject response) {
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                            if (JsonUtil.getInstance().isSuccess(response)) {
                                ModelIsOld bean = (ModelIsOld) JsonUtil.getInstance().parseJsonFromResponse(response, ModelIsOld.class);
                                if ("1".equals(bean.getOld_type())){
                                    Intent intent1 = new Intent(mContext, OldHardwareActivity.class);
                                    intent1.putExtra("par_uid", bean.getUid()+"");
                                    intent1.putExtra("jump_type",0);
                                    mContext.startActivity(intent1);
                                }else {
                                    Intent intent1 = new Intent(mContext, OldHardwareActivity.class);
                                    intent1.putExtra("jump_type",1);
                                    mContext.startActivity(intent1);
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, String error_msg) {
                            SmartToast.showInfo("网络异常,请检查网络设置");
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                        }
                    });
                }
            }
        } else {
            if (type.equals("30")) {//活动投票
                if ("".equals(login_type) || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, VoteIndexActivity.class);
                    mContext.startActivity(intent);
                }
            } else if (type.equals("34")) {
                //公共报修
                if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
                    if (NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId() + "")) {
                        SmartToast.showInfo("该小区暂未开通此服务");

                    }
                    //判断是否物业绑定
                    smallDialog = new SmallDialog(mContext);
                    smallDialog.show();
                    MyOkHttp.get().post(ApiHttpClient.CHECK_BIND_PROPERTY, null, new JsonResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, JSONObject response) {
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                            if (JsonUtil.getInstance().isSuccess(response)) {
                                ModelLogin bean = (ModelLogin) JsonUtil.getInstance().parseJsonFromResponse(response, ModelLogin.class);
                                if (bean != null && bean.getIs_bind_property().equals("2")) {
                                    Intent intent = new Intent(mContext, PublicWorkOrderCommitActivity.class);
                                    mContext.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(mContext, PropertyBindHomeActivity.class);
                                    intent.putExtra("wuye_type", "public_repair");
                                    mContext.startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, String error_msg) {
                            SmartToast.showInfo("网络异常,请检查网络设置");
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                        }
                    });

                } else {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                }
            } else if (type.equals("35")) {
                //自用报修
                if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
                    if (NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId() + "")) {
                        SmartToast.showInfo("该小区暂未开通此服务");

                    }
                    //判断是否物业绑定
                    smallDialog = new SmallDialog(mContext);
                    smallDialog.show();
                    MyOkHttp.get().post(ApiHttpClient.CHECK_BIND_PROPERTY, null, new JsonResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, JSONObject response) {
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                            if (JsonUtil.getInstance().isSuccess(response)) {
                                ModelLogin bean = (ModelLogin) JsonUtil.getInstance().parseJsonFromResponse(response, ModelLogin.class);
                                if (bean != null && bean.getIs_bind_property().equals("2")) {
//                                    Intent intent = new Intent(mContext, PersonalWorkOrderCommitActivity.class);
                                    Intent intent = new Intent(mContext, HouseListActivity.class);
                                    intent.putExtra("type",1);
                                    intent.putExtra("wuye_type", "ziyongbaoxiu");
                                    mContext.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(mContext, PropertyBindHomeActivity.class);
                                    intent.putExtra("wuye_type", "person_repair");
                                    mContext.startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, String error_msg) {
                            SmartToast.showInfo("网络异常,请检查网络设置");
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                        }
                    });

                } else {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                }
            } else if (type.equals("36")) {
                //VLOG 投票
//             if ("".equals(login_type) || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
//                 Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
//                 mContext.startActivity(intent);
//             } else {
//                 Intent intent = new Intent(mContext, VoteVlogIndexActivity.class);
//                 mContext.startActivity(intent);
//             }
            } else if (type.equals("37")) {
                //调查问卷
            } else if (type.equals("38")) {
                if ("".equals(login_type) || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, HouseListActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("wuye_type", "permit");
                    mContext.startActivity(intent);
                }
            } else if (type.equals("39")) {
                //社区党建
                Intent intent = new Intent(mContext, CommunistPartyIndexActivity.class);
                mContext.startActivity(intent);

            }else if (type.equals("41")) {
                //智能硬件
                if ("".equals(login_type) || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);
                } else {


                    smallDialog = new SmallDialog(mContext);
                    smallDialog.show();
                    MyOkHttp.get().post(ApiHttpClient.IS_OLD, null, new JsonResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, JSONObject response) {
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                            if (JsonUtil.getInstance().isSuccess(response)) {
                                ModelIsOld bean = (ModelIsOld) JsonUtil.getInstance().parseJsonFromResponse(response, ModelIsOld.class);
                                if ("1".equals(bean.getOld_type())){
                                    Intent intent1 = new Intent(mContext, OldHardwareActivity.class);
                                    intent1.putExtra("par_uid", bean.getUid()+"");
                                    intent1.putExtra("jump_type",0);
                                    mContext.startActivity(intent1);
                                }else {
                                    Intent intent1 = new Intent(mContext, OldHardwareActivity.class);
                                    intent1.putExtra("jump_type",1);
                                    mContext.startActivity(intent1);
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, String error_msg) {
                            SmartToast.showInfo("网络异常,请检查网络设置");
                            if (smallDialog != null) {
                                smallDialog.dismiss();
                            }
                        }
                    });
                }

            }
        }
    }

    public Jump(final Context context, String type, String url, String name) {//不需要接口值，根据type值判断
        this.mContext = context;
        this.url_type = type;
        sharePrefrenceUtil = new SharePrefrenceUtil(context);
        preferencesLogin = context.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");
        login_uid = preferencesLogin.getString("login_uid", "");
        is_wuye = preferencesLogin.getString("is_wuye", "");
        login_mobile = preferencesLogin.getString("login_username", "");
        if (type.equals("14")) {//紫光物联

            if (isZGWL(context, "com.uiot.smarthome.mobile")) {
                doStartApplicationWithPackageName(context, "com.uiot.smarthome.mobile");
            } else {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                // Uri content_url = Uri.parse(MyCookieStore.SERVERADDRESS + "site/uiot");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                mContext.startActivity(intent);
            }

        } else if (type.equals("15")) { //有线缴费


        } else if (type.equals("16")) {//物业缴费
            if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                mContext.startActivity(intent);
            } else {
                //Intent intent = new Intent(mContext, PropertyNewActivity.class);
                Intent intent = new Intent(mContext, HouseListActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("wuye_type", "property");
                mContext.startActivity(intent);

            }

        } else if (type.equals("17")) {//一健开门
            if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                mContext.startActivity(intent);
            } else {
                // Intent intent = new Intent(mContext, PropertyNewActivity.class);
                Intent intent = new Intent(mContext, HouseListActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("wuye_type", "open_door");
                mContext.startActivity(intent);
            }

        } else if (type.equals("18")) {//当面付


        } else if (type.equals("19")) {//居家养老
            Intent intent = new Intent(mContext, OldServiceIndexActivity.class);

            mContext.startActivity(intent);
        } else if (type.equals("20")) {//东森易购

            if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                mContext.startActivity(intent);
            } else {
                if (login_type.equals("1")) {//个人
                    String sign = "hshObj";
                    String uuid = "";
                    uuid = TDevice.getIMEI(mContext);
                    Intent intent = new Intent(context, AboutActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tag", "dsyg");
                    bundle.putString("strHouse",
                            "http://www.dsyg42.com/ec/app_index?username=" + login_mobile + "&sign=" + sign + "&uuid=" + uuid);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                } else {
                    SmartToast.showInfo("当前账号不是个人账号");
                }
            }

        } else if (type.equals("21")) {//新房手册
//            Intent intent = new Intent(mContext, NewHouseHandbookActivity.class);
//            mContext.startActivity(intent);

        } else if (type.equals("26")) {//所有直接展示webview的
            //医疗
            Intent intent = new Intent();
            intent.setClass(mContext, MedicalWebViewActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("url", url);
            intent.putExtra("name", name);
            mContext.startActivity(intent);

        } else if (type.equals("28")) {//投诉建议
            if ("".equals(login_type) || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                mContext.startActivity(intent);
            } else {
                smallDialog = new SmallDialog(mContext);
                smallDialog.show();
                new PropertyPrester(mContext, new PropertyPrester.PropertyListener() {
                    @Override
                    public void onProperty(int status, ModelLogin bean, String jump_type, String msg) {
                        if (smallDialog != null) {
                            smallDialog.dismiss();
                        }
                        if (status == 1) {
                            if (bean != null) {
                                if (bean.getIs_bind_property().equals("2")) {
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, CommitRequestActivity.class);
                                    mContext.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(mContext, PropertyBindHomeActivity.class);
                                    intent.putExtra("wuye_type", "public_repair");
                                    mContext.startActivity(intent);
                                }
                            }
                        } else {
                            SmartToast.showInfo(msg);
                        }
                    }
                }).getProperty(null);

            }
        } else if (type.equals("29")) {//充电桩
            if ("".equals(login_type) || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                mContext.startActivity(intent);
            } else {
                Intent intent = new Intent(mContext, ChargeScanActivity.class);
                mContext.startActivity(intent);
            }
        }
    }


    // 判断是否安装紫光物联
    public static boolean isZGWL(Context context, String packagename) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packagename);
    }

    private void doStartApplicationWithPackageName(Context mcontext, String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = mcontext.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = mcontext.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            mcontext.startActivity(intent);
        }
    }

    //外链
    public Jump(Context context, String url) {
        this.mContext = context;
        /*Intent intent = new Intent();
        intent.setClass(mContext, ShopWBActivity.class);
		intent.putExtra("url", url); 
		intent.putExtra("uid", uid);
		mContext.startActivity(intent);*/
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException a) {
            a.getMessage();
        }

    }

    //软文链接
    public Jump(Context context, String urlh5, String type) {
        this.type = type;//1为软文  2为优惠券
        this.mContext = context;
        this.urlh5 = MyCookieStore.SERVERADDRESS + urlh5;
        preferencesLogin = context.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");

        id = urlh5.substring(urlh5.lastIndexOf("/") + 1, urlh5.length());
        if (!StringUtils.isEmpty(id)) {
            getShare(context, id, type);

        }

    }

    //软新房链接详情/活动
    public Jump(Context context, String h5, int i) {
        this.mContext = context;
        preferencesLogin = context.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");
        Intent intent = new Intent(context, AboutActivity.class);
        Bundle bundle = new Bundle();
        if (i == 333) {
            bundle.putString("tag", "activity");
        } else {
            bundle.putString("tag", "house");
        }
        bundle.putString("strHouse", h5);
        System.out.println("h5******" + h5);
        intent.putExtras(bundle);
        mContext.startActivity(intent);

    }

    //分享
    //TODO 这个接口后台已经删了
    private void getShare(Context activity, String id, final String type) {
        Url_info info = new Url_info(activity);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", id);
        params.addBodyParameter("type", type);
        HttpHelper hh = new HttpHelper(info.share_return, params, activity) {

            @Override
            protected void setData(String json) {
                JSONObject jsonObject, jsonData;
                try {
                    jsonObject = new JSONObject(json);
                    String status = jsonObject.getString("status");
                    String data = jsonObject.getString("data");
                    if (StringUtils.isEquals(status, "1")) {
                        jsonData = new JSONObject(data);
                        title = jsonData.getString("title");
                        content = jsonData.getString("content");
                        img = jsonData.getString("img");
                        call_link = jsonData.getString("call_link");
//                        Intent intent = new Intent();
//                        intent.setClass(mContext, ShopWBActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra("url", urlh5);
//                        intent.putExtra("title", title);
//                        intent.putExtra("content", content);
//                        intent.putExtra("img", img);
//                        intent.putExtra("call_link", call_link);
//                        intent.putExtra("type", type);
//                        mContext.startActivity(intent);
                    } else if (StringUtils.isEquals(status, "0")) {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {

            }
        };
    }


}
