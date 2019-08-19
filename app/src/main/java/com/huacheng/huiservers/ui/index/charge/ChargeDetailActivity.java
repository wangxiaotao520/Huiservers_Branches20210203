package com.huacheng.huiservers.ui.index.charge;

import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

/**
 * 类描述：充电记录详情
 * 时间：2019/8/18 11:56
 * created by DFF
 */
public class ChargeDetailActivity extends BaseActivity {
    private TextView tv_biaozhun, tv_gonglv, tv_time, tv_yuanyin, tv_bianhao, tv_zhandian, tv_chongdianzhuang, tv_chongdianzuo, tv_kefu;
    private TextView tv_shifu, tv_yufu, tv_tuihuan;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("消息");

        tv_shifu = findViewById(R.id.tv_shifu);
        tv_yufu = findViewById(R.id.tv_yufu);
        tv_tuihuan = findViewById(R.id.tv_tuihuan);
        tv_biaozhun = findViewById(R.id.tv_biaozhun);
        tv_biaozhun = findViewById(R.id.tv_biaozhun);
        tv_biaozhun = findViewById(R.id.tv_biaozhun);
        tv_gonglv = findViewById(R.id.tv_gonglv);
        tv_time = findViewById(R.id.tv_time);
        tv_yuanyin = findViewById(R.id.tv_yuanyin);
        tv_bianhao = findViewById(R.id.tv_bianhao);
        tv_zhandian = findViewById(R.id.tv_zhandian);
        tv_chongdianzhuang = findViewById(R.id.tv_chongdianzhuang);
        tv_chongdianzuo = findViewById(R.id.tv_chongdianzuo);
        tv_kefu = findViewById(R.id.tv_kefu);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_detail;
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
