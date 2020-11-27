package com.huacheng.huiservers.demo;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.DeviceUtils;

/**
 * Description:
 * created by wangxiaotao
 * 2020/11/26 0026 10:59
 */
public class CoordinatorLayoutWeiPinhuiActivity1 extends BaseActivity {
    private ScrollView scrollview ;
    private ImageView iv_bg_bottom;
    private LinearLayout ll_child1;
    private LinearLayout ll_child2;
    private LinearLayout ll_child3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initView() {
        scrollview=findViewById(R.id.scrollview);
        iv_bg_bottom=findViewById(R.id.iv_bg_bottom);
        ll_child1=findViewById(R.id.ll_child1);
        ll_child2=findViewById(R.id.ll_child2);
        ll_child3=findViewById(R.id.ll_child3);
        //一开始先隐藏
        ll_child3.setAlpha(0);
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollview.scrollTo(0, DeviceUtils.dip2px(mContext,100));

            }
        },20);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initListener() {
        scrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY<=DeviceUtils.dip2px(mContext,100)){
                    ll_child1.setAlpha(1);
                    ll_child3.setAlpha(0);
                    return;
                }
                float alpha_show = 1.0f * ((DeviceUtils.dip2px(mContext,350))-scrollY)/ DeviceUtils.dip2px(mContext,250);
                if (alpha_show>1){
                    alpha_show=1;
                }
                if (alpha_show<0){
                    alpha_show=0;
                }
                ll_child1.setAlpha(alpha_show);

                float alpha_show1 = 1.0f * (scrollY-DeviceUtils.dip2px(mContext,100))/ DeviceUtils.dip2px(mContext,200);
                if (alpha_show1>1){
                    alpha_show1=1;
                }
                if (alpha_show1<0){
                    alpha_show1=0;
                }
                ll_child3.setAlpha(alpha_show1);

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordinatorlayout_weipinhui1;
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
