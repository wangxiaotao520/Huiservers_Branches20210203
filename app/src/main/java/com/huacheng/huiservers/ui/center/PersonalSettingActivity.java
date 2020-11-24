package com.huacheng.huiservers.ui.center;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelLoginOverTime;
import com.huacheng.huiservers.model.ModelUser;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.update.AppUpdate;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 个人信息设置页面
 * created by wangxiaotao
 * 2020/11/24 0024 09:42
 */
public class PersonalSettingActivity extends BaseActivity {
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.sdv_head)
    SimpleDraweeView sdvHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.ll_head_title)
    LinearLayout llHeadTitle;
    @BindView(R.id.ll_qr_code)
    LinearLayout llQrCode;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_safe)
    LinearLayout llSafe;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.tv_login_out)
    TextView tvLoginOut;
    ModelUser user ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        titleName.setText("个人信息");

        user= BaseApplication.getUser();
        FrescoUtils.getInstance().setImageUri(sdvHead, StringUtils.getImgUrl(user.getAvatars()));
        tvName.setText(user.getNickname()+"");
        tvVersion.setText("当前版本号：v" + AppUpdate.getVersionName(this));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        linLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 我的二维码
            }
        });
        llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 收货地址
            }
        });
        llSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 账号与安全
            }
        });
        llUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 检查更新
            }
        });
        tvLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出登录
                new CommomDialog(mContext, R.style.my_dialog_DimEnabled, "确定退出登录？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            loginOut();
                            dialog.dismiss();
                        }
                    }
                }).show();
            }
        });
    }

    /**
     * 退出登录
     */
    private void loginOut() {// 退出登陆
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        MyOkHttp.get().post(info.site_logout, params.getParams(), new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    // 清除登陆保存的值

                    ModelLoginOverTime modelLoginOverTime = new ModelLoginOverTime();
                    modelLoginOverTime.setType(1);
                    EventBus.getDefault().post(modelLoginOverTime);
                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response,"获取数据失败"));
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
        return R.layout.activity_personal_setting;
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
