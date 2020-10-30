package com.huacheng.huiservers;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.db.UserSql;
import com.huacheng.huiservers.dialog.PrivacyPolicyDialog;
import com.huacheng.huiservers.dialog.UpdateDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelSplashImg;
import com.huacheng.huiservers.model.PayInfoBean;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.download.DownLoadDialogActivityNew;
import com.huacheng.huiservers.utils.CacheUtils;
import com.huacheng.huiservers.utils.NoDoubleClickListener;
import com.huacheng.huiservers.utils.PermissionUtils;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.ucrop.ImgCropUtil;
import com.huacheng.huiservers.utils.update.AppUpdate;
import com.huacheng.huiservers.utils.update.Updateprester;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tinkerpatch.sdk.TinkerPatch;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.functions.Consumer;

/**
 * 启动页
 *
 * @author
 */
public class SplashActivity extends BaseActivity implements Updateprester.UpdateListener {
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
    Updateprester updateprester;
    String apkPath = "";
    int type;
    UpdateDialog dialog;

    private String token;
    private String tokenSecret;
    public static final int ACT_REQUEST_DOWNLOAD = 101;
    private RxPermissions rxPermission;
    private TextView tv_tiaoguo;
    private Handler handler = new Handler(); //倒计时跳过
    private Runnable runnable_goOn=new Runnable() {
        @Override
        public void run() {
            goOn();
        }
    };
    private ImageView iv_image_top;
    private RelativeLayout rl_bottom;
    private ModelSplashImg modelSplashImg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        isStatusBar = true;
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initConfigBeforeSetContentView() {
        super.initConfigBeforeSetContentView();
        //   super.init();
        //前提这两种需要在你的rootactivity，也就是启动界面去加，
        //
        //同时 在super.onCreate(savedInstanceState);之后
        //
        //setContentView(）；之前
        //
        //调用！！！
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
////            // Activity was brought to front and not created,
////            // Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }

    }

    @Override
    protected void initView() {

        isFirstOpen = CacheUtils.getBoolean(SplashActivity.this, IS_FIRST_OPEN, true);
        updateprester = new Updateprester(this, this);

        tv_tiaoguo = findViewById(R.id.tv_tiaoguo);
        tv_tiaoguo.setVisibility(View.GONE);
        tv_tiaoguo.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //点击跳过
                if (handler!=null){
                    handler.removeCallbacks(runnable_goOn);
                }
                //一定要调用这个方法
                hasLoginUser();
                if (isFirstOpen){
                    //第一次打开进引导页
                    CacheUtils.putBoolean(SplashActivity.this, SplashActivity.IS_FIRST_OPEN, false);
                    isFirstOpen=false;
                    Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                // 无论怎样热更新下载补丁
                if (BuildConfig.TINKER_ENABLE) {
                    TinkerPatch.with().fetchPatchUpdate(true);
                }
            }
        });
        iv_image_top = findViewById(R.id.iv_image_top);
        iv_image_top.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                //点击启动页图
                if (modelSplashImg!=null){
                    if (!"0".equals(modelSplashImg.getGuide_url_type_id())&&!NullUtil.isStringEmpty(modelSplashImg.getGuide_type_name())){
                        //点击跳过
                        if (handler!=null){
                            handler.removeCallbacks(runnable_goOn);
                        }
                        //一定要调用这个方法
                        hasLoginUser();
                        if (isFirstOpen){
                            //第一次打开进引导页
                            CacheUtils.putBoolean(SplashActivity.this, SplashActivity.IS_FIRST_OPEN, false);
                            isFirstOpen=false;
                            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                            // 带参数的跳转
                            intent.putExtra("from","ad"); // jpush 和ad
                            intent.putExtra("guide_url_type_id",modelSplashImg.getGuide_url_type_id());
                            intent.putExtra("guide_type_name",modelSplashImg.getGuide_type_name());
                            startActivity(intent);
                            finish();
                        }
                        // 无论怎样热更新下载补丁
                        if (BuildConfig.TINKER_ENABLE) {
                            TinkerPatch.with().fetchPatchUpdate(true);
                        }
                    }
                }
            }
        });
        rl_bottom = findViewById(R.id.rl_bottom);
        int height =  (int) ((DeviceUtils.getWindowWidth(SplashActivity.this) ) * 1920 * 1f / 1080);
        int height_bottom = DeviceUtils.dip2px(SplashActivity.this,64);//底部布局的高度

        if ((TDevice.getScreenHeight(this) -DeviceUtils.dip2px(SplashActivity.this,64))>=height){
            //下方显示专门空出来至少64dp显示
            //计算下方布局的高度
            //   height_bottom= (int) (TDevice.getScreenHeight(this)-height);
            iv_image_top.setImageResource(R.color.white);
        }else {
            height= ViewGroup.LayoutParams.MATCH_PARENT;
            //否则就是全屏显示
            //先让上方图片布局显示这个
            iv_image_top.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv_image_top.setImageResource(R.mipmap.bg_splash_img1);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        iv_image_top.setLayoutParams(params);
        //设置底部布局的高度
//        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height_bottom);
//        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        rl_bottom.setLayoutParams(params1);

        SimpleDraweeView sdv_logo = findViewById(R.id.sdv_logo);
        FrescoUtils.getInstance().setImageUri(sdv_logo,"");
        goinit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.splash;
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

    private void goinit() {
        sharePrefrenceUtil = new SharePrefrenceUtil(SplashActivity.this);
        preferencesLogin = SplashActivity.this.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");

        token = preferencesLogin.getString("token", "");
        tokenSecret = preferencesLogin.getString("tokenSecret", "");


        // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
        //        //获取极光注册id并保存  开启推送
        JPushInterface.init(SplashActivity.this);
        JPushInterface.resumePush(SplashActivity.this);
        regId = JPushInterface.getRegistrationID(SplashActivity.this);
        SharedPreferences preferences1 = SplashActivity.this.getSharedPreferences(
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
                     //   CacheUtils.putBoolean(SplashActivity.this, SplashActivity.IS_FIRST_OPEN, false);
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
//                            if (!isWifi(SplashActivity.this)) {//判断是否在wifi状态下
//                                SignOnDialog d = new SignOnDialog(SplashActivity.this, apkPath, "v" + info.getVersion() + ".apk");
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
                                                    intent.setClass(SplashActivity.this, DownLoadDialogActivityNew.class);
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
                           // goOn();
                           getSplashImg();
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
          //  goOn();
          getSplashImg();
        }
    }

    private void goOn() {
        //一定要调用这个方法
        hasLoginUser();
        // 新版修改 无论怎样先跳到首页
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirstOpen){
                    //第一次打开进引导页
                    CacheUtils.putBoolean(SplashActivity.this, SplashActivity.IS_FIRST_OPEN, false);
                    isFirstOpen=false;
                    Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent_jpush = getIntent();
                    if (intent_jpush != null) {
                        // String type = intent.getStringExtra("type");
                        String from = intent_jpush.getStringExtra("from");
                        //推给管理和师傅用这个 1是列表 2是详情 推给慧生活用这个 27是详情
                        if ("jpush".equals(from)) {
                            String url_type = intent_jpush.getStringExtra("url_type");
                            if ("27".equals(url_type)) {//详情
                                String j_id = intent_jpush.getStringExtra("j_id");
                                if (!StringUtils.isEmpty(j_id)) {
                                    Intent it = new Intent();
                                    it.setClass(SplashActivity.this, HomeActivity.class);
                                    it.putExtra("id", j_id);
                                    startActivity(it);
                                }
                            }else if ("42".equals(url_type)){
                                //老人智能设备
                                String par_uid = intent_jpush.getStringExtra("par_uid");
                                String toView = intent_jpush.getStringExtra("toView");

                                Intent it = new Intent(SplashActivity.this, HomeActivity.class);
                                it.putExtra("par_uid",par_uid+"");
                                it.putExtra("from", "jpush");
                                it.putExtra("toView",toView+"");
                                startActivity(it);

                            }
                        }else {
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        },1500);

        // 无论怎样热更新下载补丁
        if (BuildConfig.TINKER_ENABLE) {
            TinkerPatch.with().fetchPatchUpdate(true);
        }
    }

    /**
     * 获取首页img
     */
    private void getSplashImg() {
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().get(ApiHttpClient.GET_GUIDE_IMG, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    modelSplashImg = (ModelSplashImg) JsonUtil.getInstance().parseJsonFromResponse(response, ModelSplashImg.class);
                    if ("1".equals(modelSplashImg.getGuide_img_display())){
                        //从服务器获取图片
                        // 获取到首页图片后
                        Glide.with(getApplicationContext()).load(ApiHttpClient.IMG_URL+ modelSplashImg.getGuide_android_img()).placeholder(R.drawable.white).error(R.color.white).into(new SimpleTarget<GlideDrawable>() {
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                iv_image_top.setScaleType(ImageView.ScaleType.FIT_XY);
                                iv_image_top.setImageDrawable(resource);
                                //判断是否有倒计时
                                if ("1".equals(modelSplashImg.getGuide_time_display())){
                                    //有倒计时 倒计时5s
                                    tv_tiaoguo.setVisibility(View.VISIBLE);
                                    handler.postDelayed(runnable_goOn,4000);

                                }else {
                                    //无倒计时
                                    goOn();
                                }
                                //bg_splash_img1
                            }

                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                // 开始加载图片

                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                //图片加载失败
                                showLocalImgAndGoOn();

                            }
                        });
                    }else {
                        //网络上没有图片
                        showLocalImgAndGoOn();
                    }

                } else {
                    // 请求失败 显示本地图片
                    showLocalImgAndGoOn();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
//                SmartToast.showInfo("网络异常，请检查网络设置");
                // 请求失败 显示本地图片
                showLocalImgAndGoOn();

            }
        });
    }

    /**
     * 显示本地图片
     */
    private void showLocalImgAndGoOn() {
        // iv_image_top 保持原样儿就行
       // iv_image_top.setImageResource(R.mipmap.bg_splash_img1);
        goOn();
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
        if (handler!=null){
            handler.removeCallbacks(runnable_goOn);
        }
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
