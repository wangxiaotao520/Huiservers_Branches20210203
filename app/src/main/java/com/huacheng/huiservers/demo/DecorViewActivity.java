package com.huacheng.huiservers.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.TDevice;

/**
 * Description: 测试DeocorView
 * created by wangxiaotao
 * 2020/4/17 0017 下午 3:48
 */
public class DecorViewActivity extends BaseActivity {

    private TextView tv_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=false;
        super.onCreate(savedInstanceState);

        // 产生背景变暗效果
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();

        lp.alpha =0.4f;

        ((Activity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        ((Activity)mContext).getWindow().setAttributes(lp);

    }

    @Override
    protected void initView() {
        tv_content = findViewById(R.id.tv_content);

    }

    @Override
    protected void initData() {
        tv_content.postDelayed(new Runnable() {
            @Override
            public void run() {
                int [] screenSize= TDevice.getRealScreenSize(DecorViewActivity.this);

                int decorviewHeight= DecorViewActivity.this.getWindow().getDecorView().getHeight();
                int statusbarHeight = TDevice.getStatuBarHeight(mContext);
                //获取导航栏的高度的时候 ,先判断导航栏是否存在
                int narvigationbarHeight = TDevice.getNavigationBarHeight(mContext);
                int ContentViewHeight=  + TDevice.getContentViewOrRootViewHeight(DecorViewActivity.this);

                String show = "screenHeight:"+screenSize[0]+" screenWidth:"+screenSize[1]+"\r\n";
                show=show+"decorviewHeight:"+decorviewHeight+"\r\n";
                show=show+"statusbarHeight:"+statusbarHeight+"\r\n";
                show=show+"narvigationbarHeight:"+narvigationbarHeight+"\r\n";
                show=show+"isHasnarvigationbarHeight:"+TDevice.isNavigationBarExist(DecorViewActivity.this)+"\r\n";
                show=show+"ContentViewHeight:"+ContentViewHeight+"\r\n";
                tv_content.setText(show);

            }
        },1500);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_decorview;
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
