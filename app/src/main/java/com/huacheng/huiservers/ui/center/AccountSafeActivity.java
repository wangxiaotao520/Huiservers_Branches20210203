package com.huacheng.huiservers.ui.center;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.dialog.PermitDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelUser;
import com.huacheng.huiservers.model.PersoninfoBean;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.logout.OldNumberActivity;
import com.huacheng.huiservers.ui.vip.LogoffActivity;
import com.huacheng.huiservers.ui.webview.ConstantWebView;
import com.huacheng.huiservers.ui.webview.WebViewDefaultActivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:  账号与安全
 * created by wangxiaotao
 * 2020/11/24 0024 15:37
 */
public class AccountSafeActivity extends BaseActivity {
    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_wechat_name)
    TextView tv_wechat_name;
    @BindView(R.id.ll_phone_number)
    LinearLayout llPhoneNumber;
    @BindView(R.id.ll_phone_wechat)
    LinearLayout llPhoneWechat;
    @BindView(R.id.ll_secret)
    LinearLayout llSecret;
    @BindView(R.id.ll_cancel_account)
    LinearLayout llCancelAccount;
    ModelUser modelUser;
    private boolean isBindWechat=false;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        modelUser= BaseApplication.getUser();
        linLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleName.setText("账号与安全");
        //手机号码
    //    tvPhoneNumber.setText(modelUser.getUsername()+"");
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();

        MyOkHttp.get().post(ApiHttpClient.ACCOUNT_SECURITY, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    PersoninfoBean   bean = (PersoninfoBean) JsonUtil.getInstance().parseJsonFromResponse(response, PersoninfoBean.class);
                    tvPhoneNumber.setText(bean.getUsername());
                    if (!NullUtil.isStringEmpty(bean.getWeixin_nick())){
                        isBindWechat=true;
                        tv_wechat_name.setText(bean.getWeixin_nick());
                    }else {
                        isBindWechat=false;
                        tv_wechat_name.setText("未绑定");
                    }
                } else {

                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
    protected void initListener() {
        llPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //手机号码
               Intent  intent = new Intent(mContext, OldNumberActivity.class);
                startActivity(intent);
            }
        });
        llPhoneWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //微信
                if (isBindWechat) {
                    //已绑定
                    new CommomDialog(mContext, R.style.my_dialog_DimEnabled, "要解除微信账号的绑定吗？ 解除后将不能使用微信快速登录？", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                getUnbindWx();
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                            }
                        }
                    }).show();
                }else {
                    //未绑定
                    new PermitDialog(mContext,"请通过微信登录重新绑定").show();
                }
            }
        });
        llSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐私政策
                Intent intent = new Intent(mContext, WebViewDefaultActivity.class);
                intent.putExtra("web_type",0);
                intent.putExtra("jump_type", ConstantWebView.CONSTANT_YINGSI);
                startActivity(intent);
            }
        });
        llCancelAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注销账号
                Intent intent = new Intent(mContext, LogoffActivity.class);
                intent.putExtra("number",tvPhoneNumber.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    /**
     * 解绑微信
     */
    private void getUnbindWx() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();

        MyOkHttp.get().post(ApiHttpClient.UNBIND_WX, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    initData();
                } else {
                    hideDialog(smallDialog);
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
    protected int getLayoutId() {
        return R.layout.activity_account_safe;
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


}
