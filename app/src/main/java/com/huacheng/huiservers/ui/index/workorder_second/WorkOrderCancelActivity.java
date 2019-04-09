package com.huacheng.huiservers.ui.index.workorder_second;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

/**
 * 类描述：
 * 时间：2019/4/9 18:49
 * created by DFF
 */
public class WorkOrderCancelActivity extends BaseActivity {
    EditText et_content;
    TextView tv_commit;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("取消工单");
        et_content = findViewById(R.id.et_content);
        tv_commit = findViewById(R.id.tv_commit);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_order_cancel;
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
