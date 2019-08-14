package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

/**
 * 类描述：添加关联的用户或者是家庭人员
 * 时间：2019/8/13 18:05
 * created by DFF
 */
public class AddOldUserActivity extends BaseActivity {
    private EditText et_phone;
    private EditText edt_name;
    private TextView tv_btn;
    private int type;//0为长者 1为关联用户

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("添加关联");

        et_phone = findViewById(R.id.et_phone);
        edt_name = findViewById(R.id.edt_name);
        tv_btn = findViewById(R.id.tv_btn);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddOldUserActivity.this, AddOldRZUserActivity.class);
                startActivity(intent);
            }
        });
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
}
