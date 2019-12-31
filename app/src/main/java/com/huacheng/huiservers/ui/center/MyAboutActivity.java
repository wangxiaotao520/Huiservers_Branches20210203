package com.huacheng.huiservers.ui.center;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.login.ResigerShengmingActivity;

/**
 * 类描述：关于我们
 * 时间：2019/12/30 11:32
 * created by DFF
 */
public class MyAboutActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rel_yijian, rel_dial_number, rel_privacy;
    private TextView tv_call_number;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("关于我们");

        rel_yijian = findViewById(R.id.rel_yijian);// 意见反馈
        rel_privacy = findViewById(R.id.rel_privacy);// 隐私政策
        rel_dial_number = findViewById(R.id.rel_dial_number);//拨打电话
        tv_call_number = findViewById(R.id.tv_call_number);//
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        rel_yijian.setOnClickListener(this);
        rel_privacy.setOnClickListener(this);
        rel_dial_number.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_my_about;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_yijian:// 意见反馈
                // KeepAliveService.connectServer(this,"99906501020101");
                startActivity(new Intent(MyAboutActivity.this, YiJianActivity.class));
                break;
            case R.id.rel_dial_number:// 拨打电话
                new CommomDialog(this, R.style.my_dialog_DimEnabled, "确认拨打电话？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"
                                    + tv_call_number.getText().toString().trim()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }
                }).show();//.setTitle("提示")
                break;
            case R.id.rel_privacy:
                Intent intent = new Intent(this, ResigerShengmingActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
