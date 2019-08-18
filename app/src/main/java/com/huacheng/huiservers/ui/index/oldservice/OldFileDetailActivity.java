package com.huacheng.huiservers.ui.index.oldservice;

import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

/**
 * 类描述：老人档案体检记录详情
 * 时间：2019/8/17 15:56
 * created by DFF
 */
public class OldFileDetailActivity extends BaseActivity {
    private TextView tv_tijian_type, tv_tijian_time, tv_xueya, tv_xueyang, tv_xuetang, tv_xinlv,tv_bingli_content;
    private ImageView iv_img;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("体检记录");

        tv_tijian_type = findViewById(R.id.tv_tijian_type);
        tv_tijian_time = findViewById(R.id.tv_tijian_time);
        tv_xueya = findViewById(R.id.tv_xueya);
        tv_xueyang = findViewById(R.id.tv_xueyang);
        tv_xuetang = findViewById(R.id.tv_xuetang);
        tv_xinlv = findViewById(R.id.tv_xinlv);
        iv_img = findViewById(R.id.iv_img);
        tv_bingli_content = findViewById(R.id.tv_bingli_content);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_file_detail;
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
