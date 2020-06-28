package com.huacheng.huiservers.ui.center;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.dialog.PermitDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.model.ModelEventTheme;
import com.huacheng.huiservers.model.ModelLoginOverTime;
import com.huacheng.huiservers.model.PayInfoBean;
import com.huacheng.huiservers.model.protocol.CenterProtocol;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.logout.OldNumberActivity;
import com.huacheng.huiservers.ui.download.DownLoadDialogActivityNew;
import com.huacheng.huiservers.utils.NightModeUtils;
import com.huacheng.huiservers.utils.update.AppUpdate;
import com.huacheng.huiservers.utils.update.Updateprester;
import com.huacheng.huiservers.view.SwitchButton;
import com.huacheng.libraryservice.utils.TDevice;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.functions.Consumer;

/**
 * 设置界面
 */
public class SetActivity extends BaseActivity implements OnClickListener, Updateprester.UpdateListener {
    ShopProtocol protocol2 = new ShopProtocol();
    PayInfoBean infoBean = new PayInfoBean();
    CenterProtocol protocol = new CenterProtocol();
    private String apkpath;
    private TextView title_name, txt_verson, tv_number;
    private LinearLayout lin_left;
    private RelativeLayout rel_zhanghao, rel_siteout,
            rel_gengxin, rl_changepwd, rel_address, ry_number, ry_logoff;
    private Handler myHandler = new myHandler();
    Updateprester updateprester;
    public static final int ACT_REQUEST_DOWNLOAD = 101;
    private SwitchButton switch_theme;
    View mStatusBar;
    RelativeLayout rl_dark_mode;
    private String username="";

    class myHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3:
                    // 安装Apk
                    System.out.println("========");
                    Intent intent = new Intent();
                    // 执行动作
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction(Intent.ACTION_VIEW);
                    // 执行的数据类型
                    intent.setDataAndType(Uri.fromFile((File) msg.obj), "application/vnd.android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
                    startActivity(intent);
                    //有新版本就把补丁删掉
//                    if (BuildConfig.TINKER_ENABLE){
//                        TinkerPatch.with().cleanAll();
//                    }
                    break;
            }
        }
    }

    /*
     * 从服务器中下载APK
     */
    @SuppressLint("HandlerLeak")
    protected void downLoadApk() {
        final ProgressDialog pds; // 进度条对话框
        pds = new ProgressDialog(SetActivity.this);
        pds.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pds.setCancelable(false);
        final Handler tipHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                double mLengTh = msg.arg1 / 1024f / 1024f;
                DecimalFormat df = new DecimalFormat("#0.00");
                pds.setMessage("正在下载更新-文件大小" + df.format(mLengTh) + "M");
                pds.show();
            }
        };
        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("apkpath=======" + apkpath);
                    File file = AppUpdate.getFileFromServer(apkpath, pds, tipHandler);
                    Message msg = new Message();
                    msg.what = 3;
                    msg.obj = file;
                    myHandler.sendMessage(msg);
                } catch (Exception e) {

                } finally {
                    pds.dismiss();// 结束掉进度条对话框
                }
            }
        }.start();
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.lin_left:
                finish();
                break;
            case R.id.rel_siteout:// 注销登陆
                new CommomDialog(this, R.style.my_dialog_DimEnabled, "确定退出登录？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            getsiteout();
                            dialog.dismiss();
                        }
                    }
                }).show();//.setTitle("提示")

                break;
            case R.id.rel_gengxin:// 更新版本
                //getResult();// 获取是否有新版本
                getUpdate();
                break;
            case R.id.rl_changepwd:
                intent = new Intent(this, ChangePwdVerifyActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_address:
                intent = new Intent(this, AddressListActivity.class);
                intent.putExtra("jump_type", 3);
                startActivity(intent);
                break;
            case R.id.ry_number:
                intent = new Intent(this, OldNumberActivity.class);
                startActivity(intent);
                break;
            case R.id.ry_logoff:
                //注销登录
                break;
            default:
                break;
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
    }

    @Override
    public void onUpdate(int status, final PayInfoBean info, String msg) {
        if (status == 1) {
            if (info != null) {
                apkpath = info.getPath();
                new CommomDialog(SetActivity.this, R.style.my_dialog_DimEnabled, "发现有新版本，是否立即更新？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(final Dialog dialog, boolean confirm) {
                        if (confirm) {
                            // downLoadApk();
                            new RxPermissions(SetActivity.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                    , Manifest.permission.READ_PHONE_STATE)
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean isGranted) throws Exception {
                                            if (isGranted) {
                                                Intent intent = new Intent();
                                                intent.putExtra("file_name", info.getVersion() + ".apk");
                                                intent.putExtra("download_src", apkpath);
                                                intent.setClass(SetActivity.this, DownLoadDialogActivityNew.class);
                                                startActivityForResult(intent, ACT_REQUEST_DOWNLOAD);
                                                dialog.dismiss();
                                            } else {
                                                //请求权限用户点取消
                                            }
                                        }
                                    });

                            //  dialog.dismiss();
                        }

                    }
                }).show();//.setTitle("提示")
            } else {
                SmartToast.showInfo("当前已是最新版本");
            }

        } else {
            new PermitDialog(this, msg + "").show();
        }
    }

    /*  private void getResult() {// 版本更新接口
          showDialog(smallDialog);
          Url_info info = new Url_info(this);
          RequestParams params = new RequestParams();
          params.addBodyParameter("version", "v" + AppUpdate.getVersionName(SetActivity.this));
          System.out.println("version-------" + "v" + AppUpdate.getVersionName(SetActivity.this));
          params.addBodyParameter("type", "1");
          params.addBodyParameter("app_type", "1");
          HttpHelper hh = new HttpHelper(info.version_update, params, SetActivity.this) {

              @Override
              protected void setData(String json) {
                  hideDialog(smallDialog);
                  infoBean = protocol.getApk(json);
                  System.out.println("info-------" + infoBean);
                  System.out.println("infos-------" + infoBean.getPath());
                  if (infoBean.getPath() != null) {
                      System.out.println("77777777777");
                      apkpath = infoBean.getPath();
                      new CommomDialog(SetActivity.this, R.style.my_dialog_DimEnabled, "发现有新版本，是否立即更新？", new CommomDialog.OnCloseListener() {
                          @Override
                          public void onClick(Dialog dialog, boolean confirm) {
                              if (confirm) {
                                  downLoadApk();
                                  dialog.dismiss();
                              }

                          }
                      }).show();//.setTitle("提示")
                  } else {
                      SmartToast.showInfo("当前已是最新版本");
                  }
              }

              @Override
              protected void requestFailure(Exception error, String msg) {
                  hideDialog(smallDialog);
                  SmartToast.showInfo("网络异常，请检查网络设置");
              }
          };
      }
  */
    private void getsiteout() {// 退出登陆
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        HttpHelper hh = new HttpHelper(info.site_logout, params, SetActivity.this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                str = protocol2.setShop(json);
                if (str.equals("1")) {
                    // 清除登陆保存的值
//                    SharedPreferences preferences1 = SetActivity.this.getSharedPreferences("login", 0);
//                    preferences1.edit().clear().commit();
//                    ActivityStackManager.getActivityStackManager().finishAllActivity();
//
//                    ApiHttpClient.setTokenInfo(null, null);
//                    Intent intent = new Intent(SetActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    //    SmartToast.showInfo("退出登录");
//                    //清除数据库
//                    UserSql.getInstance().clear();
//                    //    ActivityStackManager.getActivityStackManager().finishAllActivity();
//                    BaseApplication.setUser(null);

                    ModelLoginOverTime modelLoginOverTime = new ModelLoginOverTime();
                    modelLoginOverTime.setType(1);
                    EventBus.getDefault().post(modelLoginOverTime);

                } else {
                    SmartToast.showInfo(str);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        updateprester = new Updateprester(this, this);
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        lin_left = (LinearLayout) findViewById(R.id.lin_left);
        lin_left.setOnClickListener(this);
        title_name = (TextView) findViewById(R.id.title_name);
        txt_verson = (TextView) findViewById(R.id.txt_verson);
        rl_changepwd = (RelativeLayout) findViewById(R.id.rl_changepwd);// 修改密码
        title_name.setText("设置");
        txt_verson.setText("当前版本号：v" + AppUpdate.getVersionName(SetActivity.this));
        rel_gengxin = (RelativeLayout) findViewById(R.id.rel_gengxin);// 更新
        rel_siteout = (RelativeLayout) findViewById(R.id.rel_siteout);// 退出登陆
        rel_address = (RelativeLayout) findViewById(R.id.rel_address);// 收货地址管理
        ry_number = findViewById(R.id.ry_number);// 手机号更换
        tv_number = findViewById(R.id.tv_number);// 手机号
        tv_number.setText(username);
        ry_logoff = findViewById(R.id.ry_logoff);// 注销登录
        switch_theme = findViewById(R.id.switch_theme);
        rl_dark_mode = findViewById(R.id.rl_dark_mode); //深色模式容器

        Set<String> set = new HashSet<>();
        set.add("15535406024");
        set.add("99906501020101");
        JPushInterface.resumePush(this);
        JPushInterface.setTags(getApplicationContext(), set, null);

        rel_siteout.setOnClickListener(this);
        rel_gengxin.setOnClickListener(this);
        rl_changepwd.setOnClickListener(this);
        rel_address.setOnClickListener(this);
        ry_number.setOnClickListener(this);
        ry_logoff.setOnClickListener(this);
        if (NightModeUtils.getThemeMode() == NightModeUtils.ThemeMode.NIGHT) {
            switch_theme.setChecked(true);
        } else {
            switch_theme.setChecked(false);
        }
        switch_theme.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //选中后
                ModelEventTheme modelEventTheme = new ModelEventTheme();
                if (isChecked) {
                    NightModeUtils.setThemeMode(NightModeUtils.ThemeMode.NIGHT);
                    modelEventTheme.setThemeMode(NightModeUtils.ThemeMode.NIGHT);
                } else {
                    NightModeUtils.setThemeMode(NightModeUtils.ThemeMode.DAY);
                    modelEventTheme.setThemeMode(NightModeUtils.ThemeMode.DAY);
                }
                recreate();
                //点击一次切换一次
                NightModeUtils.setIsClickChangeMode(!NightModeUtils.isIsClickChangeMode());
            }
        });
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            //>28说明是AndroidQ
            rl_dark_mode.setVisibility(View.GONE);
        } else {
            rl_dark_mode.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.set_info;
    }

    @Override
    protected void initIntentData() {
        username = this.getIntent().getStringExtra("username");

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void finish() {
        super.finish();
        //如果切换了和刚进页面时不同的主题 则调用
        if (NightModeUtils.isIsClickChangeMode()) {
            NightModeUtils.setIsClickChangeMode(false);
            EventBus.getDefault().post(new ModelEventTheme());

        }
    }
}
