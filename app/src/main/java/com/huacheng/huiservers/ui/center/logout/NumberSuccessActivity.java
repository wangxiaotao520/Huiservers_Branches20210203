package com.huacheng.huiservers.ui.center.logout;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelLoginOverTime;
import com.huacheng.huiservers.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * 类描述：绑定成功界面
 * 时间：2020/5/8 15:59
 * created by DFF
 */
public class NumberSuccessActivity extends BaseActivity {
    private TextView tv_content;
    private String phone = "";
    private LinearLayout lin_left;

    @Override
    protected void initView() {
         findTitleViews();

        titleName.setText("绑定成功");
        tv_content = findViewById(R.id.tv_content);
        lin_left = findViewById(R.id.lin_left);
        tv_content.setText("下次登录可使用新手机号： " + phone);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        lin_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModelLoginOverTime modelLoginOverTime = new ModelLoginOverTime();
                modelLoginOverTime.setType(1);
                EventBus.getDefault().post(modelLoginOverTime);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_succsee_number;
    }

    @Override
    protected void initIntentData() {
        phone = this.getIntent().getStringExtra("phone");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
