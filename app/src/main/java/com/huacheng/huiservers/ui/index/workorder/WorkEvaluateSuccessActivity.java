package com.huacheng.huiservers.ui.index.workorder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:工单-评价成功
 * Author: badge
 * Create: 2018/12/14 10:43
 */
public class WorkEvaluateSuccessActivity extends BaseActivity {

    @BindView(R.id.tv_back_order)
    TextView tvBackOrder;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_evaluate_success;
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
    protected void onCreate(Bundle savedInstanceState) {
        isFullScreen = true;
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.tv_back_order)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_back_order:
              //  startActivity(new Intent(this, WorkOrderListActivity.class));
                finish();
                break;
        }
    }
}
