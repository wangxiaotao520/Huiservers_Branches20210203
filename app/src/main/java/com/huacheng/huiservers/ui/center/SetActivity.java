package com.huacheng.huiservers.ui.center;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.HomeActivity;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.db.UserSql;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.model.protocol.CenterProtocol;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.bean.PayInfoBean;
import com.huacheng.huiservers.utils.update.AppUpdate;
import com.huacheng.huiservers.utils.update.Updateprester;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class SetActivity extends BaseActivityOld implements OnClickListener, Updateprester.UpdateListener {
    ShopProtocol protocol2 = new ShopProtocol();
    PayInfoBean infoBean = new PayInfoBean();
    CenterProtocol protocol = new CenterProtocol();
    private String apkpath;
    private TextView title_name, txt_verson;
    private LinearLayout lin_left;
    private RelativeLayout rel_zhanghao, rel_siteout,
            rel_gengxin, rl_changepwd, rel_address;
    private Handler myHandler = new myHandler();
    Updateprester updateprester;

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
                    //TODO 有新版本就把补丁删掉
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
                System.out.println("-----00000000000");
                DecimalFormat df = new DecimalFormat("#0.00");
                System.out.println("aok_size------" + mLengTh);
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
    protected void init() {
        super.init();

        setContentView(R.layout.set_info);
        updateprester = new Updateprester(this, this);
        //     SetTransStatus.GetStatus(this);
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


        Set<String> set = new HashSet<>();
        set.add("15535406024");
        set.add("99906501020101");
        JPushInterface.resumePush(this);
        JPushInterface.setTags(getApplicationContext(), set, null);

        rel_siteout.setOnClickListener(this);
        rel_gengxin.setOnClickListener(this);
        rl_changepwd.setOnClickListener(this);
        rel_address.setOnClickListener(this);
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
    public void onUpdate(int status, PayInfoBean info, String msg) {
        if (status == 1) {
            if (info != null) {
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
                    SharedPreferences preferences1 = SetActivity.this.getSharedPreferences("login", 0);
                    preferences1.edit().clear().commit();
                    HomeActivity.instant.finish();
                    BaseApplication.removeALLActivity_();
                    ApiHttpClient.setTokenInfo(null, null);
                    Intent intent = new Intent(SetActivity.this, HomeActivity.class);
                    startActivity(intent);
                    //    SmartToast.showInfo("退出登录");
                    //清除数据库
                    UserSql.getInstance().clear();
                    //    ActivityStackManager.getActivityStackManager().finishAllActivity();
                    BaseApplication.setUser(null);

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
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
