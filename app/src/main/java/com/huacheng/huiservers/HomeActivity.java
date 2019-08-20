package com.huacheng.huiservers;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huacheng.huiservers.db.UserSql;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelEventHome;
import com.huacheng.huiservers.model.ModelLoginOverTime;
import com.huacheng.huiservers.pay.chinaums.UnifyPayActivity;
import com.huacheng.huiservers.ui.base.ActivityStackManager;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.AboutActivity;
import com.huacheng.huiservers.ui.fragment.CircleFragment;
import com.huacheng.huiservers.ui.fragment.HomeFragment;
import com.huacheng.huiservers.ui.fragment.MyFragmentNew;
import com.huacheng.huiservers.ui.fragment.OldFragment;
import com.huacheng.huiservers.ui.fragment.ShopFragment;
import com.huacheng.huiservers.ui.index.charge.ChargeScanActivity;
import com.huacheng.huiservers.ui.index.charge.ChargingActivity;
import com.huacheng.huiservers.ui.index.workorder.WorkOrderDetailActivity;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.utils.PermissionUtils;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.TDevice;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.microquation.linkedme.android.LinkedME;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.huacheng.huiservers.Jump.PHONE_STATE_REQUEST_CODE;


/**
 * 主页Activity
 */
public class HomeActivity extends BaseActivityOld implements OnCheckedChangeListener {
    private String login_type;
    private SharedPreferences preferencesLogin;
    public static HomeActivity instant;
    @ViewInject(R.id.rg_content_fragment)
    public static RadioGroup mRadioGroup;// 下面的radioGroup
    private ArrayList<Fragment> fragments;

    private int current_fragment=0;
    private RadioButton[] rb;
    View mStatusBar;
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

        fragments.add(new HomeFragment());
        fragments.add(new ShopFragment());
       // fragments.add(new ServiceFragment());
        fragments.add(new OldFragment());
        fragments.add(new CircleFragment());

        fragments.add(new MyFragmentNew());

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
                            if (StringUtils.isJsonValid(ScanResult)) {
                                try {
                                    //服务订单支付
                                    JSONObject jsonObj = new JSONObject(ScanResult);
                                    String type = jsonObj.getString("type");
                                    String oid = jsonObj.getString("o_id");
                                    String price = jsonObj.getString("price");
                                    String order_type = jsonObj.getString("order_type");
                                    //type = "service_new_pay"
                                    Intent intent = new Intent(this, UnifyPayActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("o_id", oid);
                                    bundle.putString("price", price);
                                    bundle.putString("type", type);
                                    bundle.putString("order_type", order_type);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //其他
                            } else {
                                //
                                SmartToast.showInfo("二维码扫描不正确");
                            }
                        }

                    }
                }
            } else {

            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //    requestWindowFeature(Window.FEATURE_NO_TITLE);// 去标�?
        isStatusBar=true;
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
        mRadioGroup.setOnCheckedChangeListener(this);// 给定监听不解�?
        mRadioGroup.check(R.id.rb_content_fragment_home);// 默认选择第一�?

        //定义RadioButton数组用来装RadioButton，改变drawableTop大小
        rb = new RadioButton[5];
        //将RadioButton装进数组中
        rb[0] = (RadioButton) findViewById(R.id.rb_content_fragment_home);
        rb[1] = (RadioButton) findViewById(R.id.rb_content_fragment_shop);
        //rb[2] = (RadioButton) findViewById(R.id.rb_content_fragment_fabu);
        rb[2] = (RadioButton) findViewById(R.id.rb_content_fragment_service);
        rb[3] = (RadioButton) findViewById(R.id.rb_content_fragment_quanzi);

        rb[4] = (RadioButton) findViewById(R.id.rb_content_fragment_people);
        //rb[4] = (RadioButton) findViewById(R.id.rb_content_fragment_home);
        //for循环对每一个RadioButton图片进行缩放
        for (int i = 0; i < rb.length; i++) {
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            Drawable[] drawables = rb[i].getCompoundDrawables();
            //获取drawables，2/5表示图片要缩小的比例
           // Rect r = new Rect(0, 0, drawables[1].getMinimumWidth() * 1 / 2, drawables[1].getMinimumHeight() * 1 / 2);
            Rect r = new Rect(0, 0, DeviceUtils.dip2px(this,21), DeviceUtils.dip2px(this,21));
            //定义一个Rect边界
            drawables[1].setBounds(r);
            //给每一个RadioButton设置图片大小
            rb[i].setCompoundDrawables(null, drawables[1], null, null);
        }
        EventBus.getDefault().register(this);
        initJpush();

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        preferencesLogin = this.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");
        switch (checkedId) {
            case R.id.rb_content_fragment_home:
                switchFragment(0);
                current_fragment=0;
                break;
            case R.id.rb_content_fragment_shop:
                switchFragment(1);
                current_fragment=1;
                break;
            case R.id.rb_content_fragment_service:
                switchFragment(2);
                current_fragment=2;
                startActivity(new Intent(this, ChargeScanActivity.class));
                break;
            case R.id.rb_content_fragment_quanzi:
                switchFragment(3);
                current_fragment=3;
                startActivity(new Intent(this, ChargingActivity.class));
                break;
            case R.id.rb_content_fragment_people:
                if (login_type.equals("")|| ApiHttpClient.TOKEN==null||ApiHttpClient.TOKEN_SECRET==null) {
                    Editor editor = preferencesLogin.edit();
                    editor.putString("login_shop", "shop_login");
                    editor.commit();
                    startActivity(new Intent(this, LoginVerifyCodeActivity.class));
                    mRadioGroup.check(R.id.rb_content_fragment_home);
                } else {
                    switchFragment(4);
                    current_fragment=4;
                }

                break;
            default:
                break;
        }

    }

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
        startActivity( new Intent(this, HomeActivity.class));
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
                if (login_type.equals("")|| ApiHttpClient.TOKEN==null||ApiHttpClient.TOKEN_SECRET==null) {
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
        if (model!=null){
            if (model.getType()==0){
                if (rb!=null&&rb.length>0){
                    rb[3].toggle();
                }

            }else if (model.getType()==1){
                if (rb!=null&&rb.length>0){
                    rb[3].toggle();
                }
            }else if (model.getType()==2){
                //销毁当前页
                finish();
            }
        }
    }

}