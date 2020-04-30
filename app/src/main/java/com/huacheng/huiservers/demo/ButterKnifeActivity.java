package com.huacheng.huiservers.demo;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.glide.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: 测试ButterKnife
 * created by wangxiaotao
 * 2020/4/25 0025 上午 9:23
 */
public class ButterKnifeActivity extends BaseActivity {
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.iv_img1)
    ImageView ivImg1;
    @BindView(R.id.sdv_image)
    SimpleDraweeView sdvImage;
    @BindView(R.id.ll_linearlayout)
    LinearLayout llLinearlayout;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;


    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        GlideUtils.getInstance().glideLoad(this,"",ivImg1,R.mipmap.home22);


        tv1.setText("我很帅");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_butterknife_demo;
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


    @OnClick({R.id.tv3, R.id.iv_img1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv3:
                break;
            case R.id.iv_img1:
                break;
        }
    }
}
