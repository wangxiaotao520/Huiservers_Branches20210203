package com.huacheng.huiservers.ui.index.workorder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

/**
 * Description:自用部位说明
 * Author: badge
 * Create: 2018/12/20 15:30
 */
public class WorkCateDescActivtiy extends BaseActivity implements View.OnClickListener {

    private TextView titleName;
    private LinearLayout linLeft;
    private TextView tvDesc;

    @Override
    protected void initView() {
        linLeft = findViewById(R.id.lin_left);
        titleName = findViewById(R.id.title_name);
        tvDesc = findViewById(R.id.tv_desc);
        linLeft.setOnClickListener(this);
        String typeName = getIntent().getStringExtra("typeName");
        String context = getIntent().getStringExtra("typeContent");
        titleName.setText(typeName);
        tvDesc.setText(context);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_cate_desc;
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
            case R.id.lin_left:
                finish();
                break;
        }
    }
}
