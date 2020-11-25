package com.huacheng.huiservers.ui.center;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelUser;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.vip.LogoffActivity;
import com.huacheng.huiservers.ui.webview.ConstantWebView;
import com.huacheng.huiservers.ui.webview.WebViewDefaultActivity;

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
    @BindView(R.id.ll_phone_number)
    LinearLayout llPhoneNumber;
    @BindView(R.id.ll_phone_wechat)
    LinearLayout llPhoneWechat;
    @BindView(R.id.ll_secret)
    LinearLayout llSecret;
    @BindView(R.id.ll_cancel_account)
    LinearLayout llCancelAccount;
    ModelUser modelUser;

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

    }

    @Override
    protected void initListener() {
        llPhoneWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //微信
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
                startActivity(intent);
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
