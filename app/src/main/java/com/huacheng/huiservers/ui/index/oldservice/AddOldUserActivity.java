package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;

/**
 * 类描述：添加关联的用户或者是家庭人员
 * 时间：2019/8/13 18:05
 * created by DFF
 */
public class AddOldUserActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_phone;
    private EditText edt_name;
    private TextView tv_btn;
    private LinearLayout ly_SF;
    private int type = 0;//0为长者 1为关联用户

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("添加关联");

        et_phone = findViewById(R.id.et_phone);
        edt_name = findViewById(R.id.edt_name);
        tv_btn = findViewById(R.id.tv_btn);
        ly_SF = findViewById(R.id.ly_SF);

        if (type == 1) {
            ly_SF.setVisibility(View.GONE);
        } else {
            ly_SF.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_btn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_old_user;
    }

    @Override
    protected void initIntentData() {
        type = getIntent().getIntExtra("type", 0);

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
            case R.id.tv_btn:
                if (NullUtil.isStringEmpty(et_phone.getText().toString())) {
                    SmartToast.showInfo("请输入社区慧生活绑定账号");
                    return;
                }
                if (type == 1) {
                    if (NullUtil.isStringEmpty(edt_name.getText().toString())) {
                        SmartToast.showInfo("请输入绑定者身份");
                        return;
                    }
                }
                Intent intent = new Intent(AddOldUserActivity.this, AddOldRZUserActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
