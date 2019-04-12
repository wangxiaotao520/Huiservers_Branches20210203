package com.huacheng.huiservers.ui.index.workorder_second;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.ToolUtils;

/**
 * 类描述：
 * 时间：2019/4/9 18:49
 * created by DFF
 */
public class WorkOrderCancelActivity extends BaseActivity {
    EditText et_content;
    TextView tv_commit;
    LinearLayout  lin_left;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("取消工单");
        lin_left = findViewById(com.huacheng.libraryservice.R.id.lin_left);
        et_content = findViewById(R.id.et_content);
        tv_commit = findViewById(R.id.tv_commit);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        lin_left .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToolUtils(WorkOrderCancelActivity.this, et_content).closeInputMethod();
                finish();
            }
        });
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
