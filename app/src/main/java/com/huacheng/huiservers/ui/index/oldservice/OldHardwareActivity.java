package com.huacheng.huiservers.ui.index.oldservice;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

/**
 * Description:养老智能硬件 Activity
 * created by wangxiaotao
 * 2019/8/22 0022 下午 4:02
 */
public class OldHardwareActivity  extends BaseActivity{

    private RelativeLayout rel_no_data;
    private ImageView img_data;
    private TextView tv_text;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("智能数据");
        rel_no_data = findViewById(R.id.rel_no_data);
        rel_no_data.setVisibility(View.VISIBLE);
        img_data = findViewById(R.id.img_data);
        tv_text = findViewById(R.id.tv_text);

        img_data.setBackgroundResource(R.mipmap.bg_oldservice_empty);
        tv_text.setText("暂时没有数据哦");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_hardware;
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
