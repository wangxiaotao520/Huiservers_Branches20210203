package com.huacheng.huiservers;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.huacheng.huiservers.db.UserSql;
import com.huacheng.huiservers.dialog.DownLoadDialog;
import com.huacheng.huiservers.dialog.PrivacyPolicyDialog;
import com.huacheng.huiservers.dialog.UpdateDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.bean.PayInfoBean;
import com.huacheng.huiservers.utils.CacheUtils;
import com.huacheng.huiservers.utils.PermissionUtils;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.ucrop.ImgCropUtil;
import com.huacheng.huiservers.utils.update.AppUpdate;
import com.huacheng.huiservers.utils.update.Updateprester;
import com.huacheng.libraryservice.utils.NullUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tinkerpatch.sdk.TinkerPatch;

import java.io.File;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.functions.Consumer;

/**
 * 启动页
 *
 * @author
 */
public class SplashUI extends BaseActivityOld implements Updateprester.UpdateListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String IS_FIRST_OPEN = "is_first_open";
    private SharePrefrenceUtil sharePrefrenceUtil;
    private boolean isFirstOpen;
    private String regId, login_type;
    SharedPreferences preferencesLogin;
    public static SplashUI intents;
    Updateprester updateprester;
    String apkPath = "";
    int type;
    UpdateDialog dialog;

    private String token;
    private String tokenSecret;
    public static final int ACT_REQUEST_DOWNLOAD = 101;
    private RxPermissions rxPermission;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        isStatusBar = true;
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppCompatTheme_Base);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // Activity was brought to front and not created,
            // Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }
    }

    @Override
    protected void init() {
        //   super.init();
        intents = this;
        isFirstOpen = CacheUtils.getBoolean(SplashUI.this, IS_FIRST_OPEN, true);
        setContentView(R.layout.splash);
        //  SetTransStatus.GetStatus(SplashUI.this);//系统栏默认为黑色

        updateprester = new Updateprester(this, this);

        goinit();

    }


    private void goinit() {
        sharePrefrenceUtil = new SharePrefrenceUtil(SplashUI.this);
        preferencesLogin = SplashUI.this.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");

        token = preferencesLogin.getString("token", "");
        tokenSecret = preferencesLogin.getString("tokenSecret", "");


        // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
        //        //获取极光注册id并保存  开启推送
        JPushInterface.init(SplashUI.this);
        JPushInterface.resumePush(SplashUI.this);
        regId = JPushInterface.getRegistrationID(SplashUI.this);
        SharedPreferences preferences1 = SplashUI.this.getSharedPreferences(
                "jpush_id", 0);
        Editor editor = preferences1.edit();
        editor.putString("reg_id", regId);
        editor.commit();

        //更新版本
        verifyStoragePermissions(this);
    }

    public void verifyStoragePermissions(Activity activity) {
        rxPermission = new RxPermissions(this);
        if (isFirstOpen) {
             PrivacyPolicyDialog dialog = new PrivacyPolicyDialog(this, new PrivacyPolicyDialog.OnCustomDialogListener() {
                @Override
                public void back(String tag, Dialog dialog1) {
                    if (tag.equals("2")) {//同意
                        getUpdate();
                        dialog1.dismiss();
                        CacheUtils.putBoolean(SplashUI.this, SplashUI.IS_FIRST_OPEN, false);
                    } else {//退出APP
                        finish();
                    }
                }
            });
            dialog.show();
        } else {
            getUpdate();
        }

    }

    /**
     * 更新接口
     */
    private void getUpdate() {
        HashMap<String, String> mParams = new HashMap<>();
        mParams.put("version", "v" + AppUpdate.getVersionName(this));
        mParams.put("type", "1");
        mParams.put("app_type", "1");
        updateprester.getUpdate(mParams);
        //删除原文件夹
        ImgCropUtil.deleteCacheFile(new File(Environment.getExternalStorageDirectory() + "/hui_download/"));

    }

    @Override
    public void onUpdate(int status, final PayInfoBean info, String msg) {
        if (status == 1) {
            if (info != null) {
                apkPath = info.getPath();
                if (info.getCompel().equals("1")) {//强制更新
                    type = 1;
                } else {
                    type = 2;
                }
                dialog = new UpdateDialog(this, type, info, new UpdateDialog.OnCustomDialogListener() {
                    @Override
                    public void back(String tag) {
                        if (tag.equals("1")) {
//                            if (!isWifi(SplashUI.this)) {//判断是否在wifi状态下
//                                SignOnDialog d = new SignOnDialog(SplashUI.this, apkPath, "v" + info.getVersion() + ".apk");
//                                d.show();
//                            } else {
                                rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                        , Manifest.permission.READ_PHONE_STATE)
                                        .subscribe(new Consumer<Boolean>() {
                                            @Override
                                            public void accept(Boolean isGranted) throws Exception {
                                                if (isGranted) {
                                                    Intent intent = new Intent();
                                                    intent.putExtra("file_name", info.getVersion() + ".apk");
                                                    intent.putExtra("download_src", apkPath);
                                                    intent.setClass(SplashUI.this, DownLoadDialog.class);
                                                    startActivityForResult(intent, ACT_REQUEST_DOWNLOAD);
                                                    dialog.dismiss();
                                                } else {
                                                //请求权限用户点取消
                                                }
                                            }
                                        });

                         //   }
                        } else {
                            //非强制更新点取消
                            dialog.dismiss();
                            goOn();
                        }
                    }
                });
                dialog.show();
                // 将对话框的大小按屏幕大小的百分比设置
                WindowManager windowManager = this.getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.width = (int) (display.getWidth() * 0.8); //设置宽度
                dialog.getWindow().setAttributes(lp);
                if (type == 1) {//强制更新
                    dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {//强制更新返回键直接退出程序

                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (event.getAction() == KeyEvent.ACTION_UP
                                    && keyCode == KeyEvent.KEYCODE_BACK
                                    && event.getRepeatCount() == 0) {
                                dialog.dismiss();
                                System.exit(0);
                            }
                            return false;
                        }
                    });
                }
            }

        } else {
            //没有新版本
            goOn();
        }
    }

    private void goOn() {
//        if (!hasLoginUser()) {//未登录
//            if (sharePrefrenceUtil.getIsChooseXiaoqu().equals("1")) {
//                // 选择了小区 要根据小区判断域名
//                ConfigUtils.get().getApiConfig(sharePrefrenceUtil.getXiaoQuId(), new ConfigUtils.OnGetConfigListener() {
//                    @Override
//                    public void onGetConfig(int status, ModelConfig modelConfig) {
//                        if (status == 1) {
//                            if (modelConfig != null) {
//                                ApiHttpClient.API_URL = modelConfig.getHui_domain_name() + "/";
//                                ApiHttpClient.invalidateApi();
//                                Url_info.invalidateApi();
//                                //保存企业id
//                                sharePrefrenceUtil.setCompanyId(modelConfig.getCompany_id() + "");
//                                Intent intent = new Intent(SplashUI.this, HomeActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//
//                        } else if (status == -1) {
//                            //网络错误
//                            SmartToast.showInfo("网络错误，请检查网络设置");
//                        }
//                    }
//                });
//
//            } else {
//                Intent intent = new Intent(SplashUI.this, XiaoquActivity.class);
//                intent.putExtra("type", "splash");
//                startActivity(intent);
//                finish();
//
//            }
//        } else {//登录了
//            //登录了，要根据小区判断域名
//            ConfigUtils.get().getApiConfig(sharePrefrenceUtil.getXiaoQuId(), new ConfigUtils.OnGetConfigListener() {
//                @Override
//                public void onGetConfig(int status, ModelConfig modelConfig) {
//                    if (status == 1) {
//                        if (modelConfig != null) {
//                            ApiHttpClient.API_URL = modelConfig.getHui_domain_name() + "/";
//                            ApiHttpClient.invalidateApi();
//                            Url_info.invalidateApi();
//                            //保存企业id
//                            sharePrefrenceUtil.setCompanyId(modelConfig.getCompany_id() + "");
//                            Intent intent = new Intent(SplashUI.this, HomeActivity.class);
//                            Intent intent_come = getIntent();
//                            if (intent_come != null && intent_come.hasExtra("from")) {
//                                intent.putExtra("from", "jpush");
//                                if (intent_come.hasExtra("type")) {
//                                    intent.putExtra("url_type", intent_come.getStringExtra("url_type"));
//                                    // intentTo.putExtra("type", intent_come.getStringExtra("type"));
//                                    intent.putExtra("j_id", intent_come.getStringExtra("j_id"));
//                                }
//                            }
//                            startActivity(intent);
//                            finish();
//                        }
//                    } else if (status == -1) {
//                        //网络错误
//                        SmartToast.showInfo("网络错误，请检查网络设置");
//                    }
//                }
//            });
//
//        }
        //todo 新版修改 无论怎样先跳到首页
        Intent intent = new Intent(SplashUI.this, HomeActivity.class);
        startActivity(intent);
        finish();

        //todo 无论怎样热更新下载补丁
        if (BuildConfig.TINKER_ENABLE) {
            TinkerPatch.with().fetchPatchUpdate(true);
        }
    }

    private boolean hasLoginUser() {
        UserSql.getInstance().hasLoginUser();
        if (!NullUtil.isStringEmpty(token) && !NullUtil.isStringEmpty(tokenSecret)) {
            ApiHttpClient.TOKEN = token;
            ApiHttpClient.TOKEN_SECRET = tokenSecret;
            return true;
        }
        return false;
    }

    @Override

    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (PermissionUtils.checkPermissionGranted(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //更新
                getUpdate();
            } else {
                // 直接退出程序
                // Toast.makeText(this, "不能打开定位，无法进行开门", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ACT_REQUEST_DOWNLOAD) {
                getUpdate();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
