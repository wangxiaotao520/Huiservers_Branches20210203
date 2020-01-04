package com.huacheng.huiservers;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huacheng.huiservers.db.UserSql;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelEventHome;
import com.huacheng.huiservers.model.ModelIndexBottomUI;
import com.huacheng.huiservers.model.ModelLoginOverTime;
import com.huacheng.huiservers.model.ModelQRCode;
import com.huacheng.huiservers.ui.base.ActivityStackManager;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.AboutActivity;
import com.huacheng.huiservers.ui.fragment.CircleFragmentNew;
import com.huacheng.huiservers.ui.fragment.HomeFragmentNew;
import com.huacheng.huiservers.ui.fragment.MyCenterFrament;
import com.huacheng.huiservers.ui.fragment.ServiceFragmentCat;
import com.huacheng.huiservers.ui.fragment.ShopFragment1;
import com.huacheng.huiservers.ui.index.workorder.WorkOrderDetailActivity;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.utils.PermissionUtils;
import com.huacheng.huiservers.utils.QRCodeUtils;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.microquation.linkedme.android.LinkedME;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.huacheng.huiservers.Jump.PHONE_STATE_REQUEST_CODE;


/**
 * 主页Activity
 */
public class HomeActivity extends BaseActivityOld implements  View.OnClickListener {
    private String login_type;
    private SharedPreferences preferencesLogin;
    public static HomeActivity instant;
    @ViewInject(R.id.rg_content_fragment)
    public static RadioGroup mRadioGroup;// 下面的radioGroup
    private ArrayList<Fragment> fragments;

    private int current_fragment = 0;
   // private RadioButton[] rb;
    private int [] drawables_unselected= {R.drawable.home11,R.drawable.shop11,R.drawable.bt_fuwu11,R.drawable.circle11,R.drawable.people11};
    private int [] drawables_selected= {R.drawable.home22,R.drawable.shop22,R.drawable.bt_fuwu22,R.drawable.circle22,R.drawable.people22};
    private ImageView [] imageViews_bottom= new ImageView[5];
    private TextView [] textViews_bottom= new TextView[5];
    View mStatusBar;
    private ModelEventHome modelEventHome;
    private boolean isEvent = false;
    private ModelIndexBottomUI  modelIndexBottomUI;

    private String [] img_unselected= new String[5]; //网络选择的图片
    private String [] img_selected= new String[5];  //网络未选择的图片

    /**
     * 点击切换fragment
     *
     * @param position
     */
    public void switchFragment(int position) {
        //开启事务
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        //遍历集合
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (i == position) {
                //显示fragment
                if (fragment.isAdded()) {
                    //如果这个fragment已经被事务添加,显示
                    fragmentTransaction.show(fragment);
                } else {
                    //如果这个fragment没有被事务添加过,添加
                    fragmentTransaction.add(R.id.fl_container, fragment);
                }
            } else {
                //隐藏fragment
                if (fragment.isAdded()) {
                    //如果这个fragment已经被事务添加,隐藏
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        //提交事务
        fragmentTransaction.commit();

    }


    /**
     * 创建fragment实例并把他们加入集合
     */

    public void addFragment() {

        //fragments.add(new HomeFragment());

        fragments.add(new HomeFragmentNew());
        //fragments.add(new ShopFragment());

      //  fragments.add(new ShopFragmentNew());
        fragments.add(new ShopFragment1());

        fragments.add(new ServiceFragmentCat());
        //fragments.add(new OldFragment());

        fragments.add(new CircleFragmentNew());
        //fragments.add(new CircleFragment());

        fragments.add(new MyCenterFrament());
        //fragments.add(new MyFragmentNew());


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 49374) {//二维码扫描返回（自己打断点试出来的）
                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (intentResult != null) {
                    if (intentResult.getContents() == null) {
                        SmartToast.showInfo("内容为空");
                    } else {
                        String ScanResult = intentResult.getContents();

                        if (!StringUtils.isEmpty(ScanResult)) {
                            if (ScanResult.contains("?type=") && ScanResult.contains("&get=")) {
                                //扫充电桩返回
                                String type = ScanResult.substring((ScanResult.indexOf("type=") + 5), (ScanResult.indexOf("&get=")));
                                String get = ScanResult.substring(ScanResult.indexOf("get=") + 4);
                                if ("1".equals(type)) {//充电桩
                                    requestQr(type, get);
                                }

                            } else if (StringUtils.isJsonValid(ScanResult)) {//服务支付扫码
                                //服务订单支付


                                ModelQRCode modelQRCode = new Gson().fromJson(ScanResult, ModelQRCode.class);
                                //调用二维码解析
                                int type = 2;
                                try {
                                    type = Integer.parseInt(modelQRCode.getType());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                QRCodeUtils.getInstance().parseQrCode(smallDialog, HomeActivity.this, type, modelQRCode);


                            }
//
//                            if (StringUtils.isJsonValid(ScanResult)) {
//                                //其他
//                                try {
//                                    JSONObject jsonObj = new JSONObject(ScanResult);
//                                    int type = jsonObj.getInt("type");
//                                    ModelQRCode modelQRCode = new Gson().fromJson(jsonObj.getString("data"),ModelQRCode.class);
//                                    //调用二维码解析
//                                    QRCodeUtils.getInstance().parseQrCode(smallDialog,this,type,modelQRCode);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                //
//                                SmartToast.showInfo("二维码扫描不正确");
//                            }
                        }

                    }
                }
            } else {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 扫描二维码
     *
     * @param type
     * @param get
     */
    private void requestQr(String type, String get) {
        if (NullUtil.isStringEmpty(type) || NullUtil.isStringEmpty(get)) {
            return;
        }
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("gtel", get + "");

        MyOkHttp.get().post(ApiHttpClient.SCAN_INDEX, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    //其他
                    try {
                        ModelQRCode modelQRCode = new Gson().fromJson(response.getString("data"), ModelQRCode.class);
                        //调用二维码解析
                        int type = 1;
                        try {
                            type = Integer.parseInt(modelQRCode.getType());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        QRCodeUtils.getInstance().parseQrCode(smallDialog, HomeActivity.this, type, modelQRCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    hideDialog(smallDialog);
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "二维码扫描不正确");
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
    public void onCreate(Bundle savedInstanceState) {
        //    requestWindowFeature(Window.FEATURE_NO_TITLE);// 去标�?
        isStatusBar = true;
        super.onCreate(savedInstanceState);
        //设置状态栏字体
        instant = this;
        //SetTransStatus.GetStatus(this);
        View view = View.inflate(this, R.layout.activity_home, null);
        ViewUtils.inject(this, view);// 引入注解
        setContentView(view);

        fragments = new ArrayList<Fragment>();
        addFragment();

        preferencesLogin = this.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");

        switchFragment(0);
        // }
//        mRadioGroup.setOnCheckedChangeListener(this);// 给定监听不解�?
//        mRadioGroup.check(R.id.rb_content_fragment_home);// 默认选择第一�?

//        //定义RadioButton数组用来装RadioButton，改变drawableTop大小
//        rb = new RadioButton[5];
//        //将RadioButton装进数组中
//        rb[0] = (RadioButton) findViewById(R.id.rb_content_fragment_home);
//        rb[1] = (RadioButton) findViewById(R.id.rb_content_fragment_shop);
//        //rb[2] = (RadioButton) findViewById(R.id.rb_content_fragment_fabu);
//        rb[2] = (RadioButton) findViewById(R.id.rb_content_fragment_service);
//        rb[3] = (RadioButton) findViewById(R.id.rb_content_fragment_quanzi);
//
//        rb[4] = (RadioButton) findViewById(R.id.rb_content_fragment_people);
//        //rb[4] = (RadioButton) findViewById(R.id.rb_content_fragment_home);
//        //for循环对每一个RadioButton图片进行缩放
//        for (int i = 0; i < rb.length; i++) {
//            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
//            Drawable[] drawables = rb[i].getCompoundDrawables();
//            //获取drawables，2/5表示图片要缩小的比例
//            // Rect r = new Rect(0, 0, drawables[1].getMinimumWidth() * 1 / 2, drawables[1].getMinimumHeight() * 1 / 2);
//            Rect r = new Rect(0, 0, DeviceUtils.dip2px(this, 21), DeviceUtils.dip2px(this, 21));
//            //定义一个Rect边界
//            drawables[1].setBounds(r);
//            //给每一个RadioButton设置图片大小
//            rb[i].setCompoundDrawables(null, drawables[1], null, null);
//        }

        FrameLayout fl_home = findViewById(R.id.fl_home);
        fl_home.setOnClickListener(this);
        FrameLayout fl_shop = findViewById(R.id.fl_shop);
        fl_shop.setOnClickListener(this);
        FrameLayout fl_service = findViewById(R.id.fl_service);
        fl_service.setOnClickListener(this);
        FrameLayout fl_circle = findViewById(R.id.fl_circle);
        fl_circle.setOnClickListener(this);
        FrameLayout fl_my = findViewById(R.id.fl_my);
        fl_my.setOnClickListener(this);

        ImageView iv_home = findViewById(R.id.iv_home);
        ImageView iv_shop = findViewById(R.id.iv_shop);
        ImageView iv_service = findViewById(R.id.iv_service);
        ImageView iv_circle = findViewById(R.id.iv_circle);
        ImageView iv_my = findViewById(R.id.iv_my);
        imageViews_bottom[0]=iv_home;
        imageViews_bottom[1]=iv_shop;
        imageViews_bottom[2]=iv_service;
        imageViews_bottom[3]=iv_circle;
        imageViews_bottom[4]=iv_my;

        TextView tv_home = findViewById(R.id.tv_home);
        TextView tv_shop = findViewById(R.id.tv_shop);
        TextView tv_service = findViewById(R.id.tv_service);
        TextView tv_circle = findViewById(R.id.tv_circle);
        TextView tv_my = findViewById(R.id.tv_my);
        textViews_bottom[0]=tv_home;
        textViews_bottom[1]=tv_shop;
        textViews_bottom[2]=tv_service;
        textViews_bottom[3]=tv_circle;
        textViews_bottom[4]=tv_my;

        changeBottomUI(0);
        current_fragment=0;
        requestBottomUI();
        EventBus.getDefault().register(this);
        initJpush();

    }

    /**
     * 请求下方tab图片
     */
    private void requestBottomUI() {
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().get(ApiHttpClient.IMG_BOTTOM, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    modelIndexBottomUI= (ModelIndexBottomUI) JsonUtil.getInstance().parseJsonFromResponse(response,ModelIndexBottomUI.class);
                    if(modelIndexBottomUI!=null){
                        //付志斌接口干得好事 2020 01 04
                        img_selected[0]=ApiHttpClient.IMG_URL+modelIndexBottomUI.getApp_index_selected();
                        img_selected[1]=ApiHttpClient.IMG_URL+modelIndexBottomUI.getApp_shop_selected();
                        img_selected[2]=ApiHttpClient.IMG_URL+modelIndexBottomUI.getApp_service_selected();
                        img_selected[3]=ApiHttpClient.IMG_URL+modelIndexBottomUI.getApp_social_selected();
                        img_selected[4]=ApiHttpClient.IMG_URL+modelIndexBottomUI.getApp_my_selected();

                        img_unselected[0]=ApiHttpClient.IMG_URL+modelIndexBottomUI.getApp_index_not_selected();
                        img_unselected[1]=ApiHttpClient.IMG_URL+modelIndexBottomUI.getApp_shop_not_selected();
                        img_unselected[2]=ApiHttpClient.IMG_URL+modelIndexBottomUI.getApp_service_not_selected();
                        img_unselected[3]=ApiHttpClient.IMG_URL+modelIndexBottomUI.getApp_social_not_selected();
                        img_unselected[4]=ApiHttpClient.IMG_URL+modelIndexBottomUI.getApp_my_not_selected();
                        changeBottomUI(current_fragment);
                    }
                } else {
//                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
//                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
//                SmartToast.showInfo("网络异常，请检查网络设置");

            }
        });
    }

    private void initJpush() {
        Intent intent = this.getIntent();
        if (intent != null) {
            // String type = intent.getStringExtra("type");
            String from = intent.getStringExtra("from");
            //推给管理和师傅用这个 1是列表 2是详情 推给慧生活用这个 27是详情
            if ("jpush".equals(from)) {
                String url_type = intent.getStringExtra("url_type");
                if ("27".equals(url_type)) {//详情
                    String j_id = intent.getStringExtra("j_id");
                    if (!StringUtils.isEmpty(j_id)) {
                        Intent it = new Intent();
                        it.setClass(this, WorkOrderDetailActivity.class);
                        it.putExtra("id", j_id);
                        startActivity(it);
                    }
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initJpush();
    }

//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        preferencesLogin = this.getSharedPreferences("login", 0);
//        login_type = preferencesLogin.getString("login_type", "");
//        switch (checkedId) {
//            case R.id.rb_content_fragment_home:
//                switchFragment(0);
//                current_fragment = 0;
//                break;
//            case R.id.rb_content_fragment_shop:
//                switchFragment(1);
//                current_fragment = 1;
//                break;
//            case R.id.rb_content_fragment_service:
//                switchFragment(2);
//                current_fragment = 2;
//                break;
//            case R.id.rb_content_fragment_quanzi:
//                if (isEvent && modelEventHome != null) {//说明是点击的物业公告或者社区公告
//                    CircleFragmentNew fragment = (CircleFragmentNew) fragments.get(3);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("type_position", modelEventHome.getType());
//                    fragment.setArguments(bundle);
//                }
//                switchFragment(3);
//                current_fragment = 3;
//
//                isEvent = false;
//                modelEventHome = null;
//                break;
//            case R.id.rb_content_fragment_people:
//                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
//                    Editor editor = preferencesLogin.edit();
//                    editor.putString("login_shop", "shop_login");
//                    editor.commit();
//                    startActivity(new Intent(this, LoginVerifyCodeActivity.class));
//                    mRadioGroup.check(R.id.rb_content_fragment_home);
//                } else {
//                    switchFragment(4);
//                    current_fragment = 4;
//                }
//
//                break;
//            default:
//                break;
//        }
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                SmartToast.showInfo("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                removeALLActivity();//执行移除所以Activity方法

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginOverTime(ModelLoginOverTime model) {
        //登录失效
        SharedPreferences preferences1 = this.getSharedPreferences("login", 0);
        preferences1.edit().clear().commit();
        ApiHttpClient.setTokenInfo(null, null);
        SmartToast.showInfo("登录失效");
        BaseActivityOld.finishAll();
        BaseActivityOld.destoryActivity();
        ActivityStackManager.getActivityStackManager().finishAllActivity();
        finish();
        startActivity(new Intent(this, HomeActivity.class));
        startActivity(new Intent(this, LoginVerifyCodeActivity.class));
        //清除数据库
        UserSql.getInstance().clear();
        //    ActivityStackManager.getActivityStackManager().finishAllActivity();
        BaseApplication.setUser(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LinkedME.getInstance().setImmediate(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PHONE_STATE_REQUEST_CODE) {
            //点击banner 图的东森易购回调，需获取uuid 故读取手机的权限
            if (PermissionUtils.checkPermissionGranted(this, Manifest.permission.READ_PHONE_STATE)) {
                //调用方法
                String uuid = "";
                uuid = TDevice.getIMEI(this);
                preferencesLogin = this.getSharedPreferences("login", 0);
                login_type = preferencesLogin.getString("login_type", "");
                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Intent intent = new Intent(this, LoginVerifyCodeActivity.class);
                    this.startActivity(intent);
                } else {
                    String login_mobile = preferencesLogin.getString("login_username", "");
                    String sign = "hshObj";
                    Intent intent = new Intent(this, AboutActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tag", "dsyg");
                    bundle.putString("strHouse",
                            "http://www.dsyg42.com/ec/app_index?username=" + login_mobile + "&sign=" + sign + "&uuid=" + uuid);
                    intent.putExtras(bundle);
                    this.startActivity(intent);
                }
            } else {
                // Permission Denied
                SmartToast.showInfo("无法打开东森易购,请获取手机权限");
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventbus(ModelEventHome model) {
        if (model != null) {
            this.modelEventHome = model;
            if (model.getType() >= 0) {
//                if (rb != null && rb.length > 0) {
//                    rb[3].toggle();
//                }
                if (modelEventHome != null) {//说明是点击的物业公告或者社区公告
                    CircleFragmentNew fragment = (CircleFragmentNew) fragments.get(3);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type_position", modelEventHome.getType());
                    fragment.setArguments(bundle);
                }
                switchFragment(3);
                current_fragment = 3;
                modelEventHome = null;
                changeBottomUI(3);
            } else if (model.getType() == -1) {
                //销毁当前页
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_home:
                switchFragment(0);
                current_fragment = 0;
                changeBottomUI(0);
                break;
            case R.id.fl_shop:
                switchFragment(1);
                current_fragment = 1;
                changeBottomUI(1);
                break;
            case R.id.fl_service:
                switchFragment(2);
                current_fragment = 2;
                changeBottomUI(2);
                break;
            case R.id.fl_circle:

                switchFragment(3);
                current_fragment = 3;
                changeBottomUI(3);
                break;
            case R.id.fl_my:
                preferencesLogin = this.getSharedPreferences("login", 0);
                login_type = preferencesLogin.getString("login_type", "");
                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    Editor editor = preferencesLogin.edit();
                    editor.putString("login_shop", "shop_login");
                    editor.commit();
                    startActivity(new Intent(this, LoginVerifyCodeActivity.class));
                } else {
                    switchFragment(4);
                    current_fragment = 4;
                    changeBottomUI(4);
                }

                break;
                default:
                    break;
        }
    }

    /**
     * 更改下方UI
     * @param position
     */
    private void changeBottomUI(int position) {
        // 在这里判断有没有网络图片
        if (modelIndexBottomUI!=null&&!NullUtil.isStringEmpty(modelIndexBottomUI.getApp_dibu_color_selected())&&modelIndexBottomUI.getApp_dibu_color_selected().contains("#")){
            //有网络图片
            for (int i = 0; i < textViews_bottom.length; i++) {
                if (position==i){
                    textViews_bottom[i].setTextColor(Color.parseColor(modelIndexBottomUI.getApp_dibu_color_selected()+""));
                }else {
                    textViews_bottom[i].setTextColor(Color.parseColor(modelIndexBottomUI.getApp_dibu_color_not_selected()+""));
                }
            }
            for (int i = 0; i < imageViews_bottom.length; i++) {
                if (position==i){
                    GlideUtils.getInstance().glideLoad(this,img_selected[i],imageViews_bottom[i],drawables_selected[i]);
                }else {
                    GlideUtils.getInstance().glideLoad(this,img_unselected[i],imageViews_bottom[i],drawables_unselected[i]);
                }
            }
        }else {
            for (int i = 0; i < textViews_bottom.length; i++) {
                if (position==i){
                    textViews_bottom[i].setTextColor(getResources().getColor(R.color.orange_bg));
                }else {
                    textViews_bottom[i].setTextColor(getResources().getColor(R.color.title_color));
                }
            }

            for (int i = 0; i < imageViews_bottom.length; i++) {
                if (position==i){
                    imageViews_bottom[i].setImageResource(drawables_selected[i]);
                }else {
                    imageViews_bottom[i].setImageResource(drawables_unselected[i]);
                }
            }
        }
    }
}