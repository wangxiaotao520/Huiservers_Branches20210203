package com.huacheng.huiservers.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.widget.ImageView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.DeviceUtils;

import static android.support.v4.view.ViewCompat.TYPE_NON_TOUCH;

/**
 * Description: 仿唯品会个人中心
 * created by wangxiaotao
 * 2020/11/26 0026 10:07
 */
public class CoordinatorLayoutWeiPinhuiActivity extends BaseActivity {
    private AppBarLayout appBarLayout;


    private CollapsingToolbarLayout collapsing_toolbar;
    private CoordinatorLayout coordinatorLayout;
    private ImageView bg1;
    private NestedScrollView nestedscrollview ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        appBarLayout = findViewById(R.id.appbarlayout);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        nestedscrollview=findViewById(R.id.nestedscrollview);

    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollToTop();
            }
        },20);
    }
    public void scrollToTop() {
        //拿到 appbar 的 behavior,让 appbar 滚动
//        ViewGroup.LayoutParams layoutParams = appBarLayout.getLayoutParams();
//        CoordinatorLayout.Behavior behavior =
//                ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
//        if (behavior instanceof AppBarLayout.Behavior) {
//            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
//            //拿到下方tabs的y坐标，即为我要的偏移量
//            float y = DeviceUtils.dip2px(this,50);
//            //注意传递负值
//            appBarLayoutBehavior.setTopAndBottomOffset((int) -y);
//        }
        CoordinatorLayout.Behavior behavior1 = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
        if (behavior1 != null) {
            behavior1.onNestedPreScroll(coordinatorLayout, appBarLayout, appBarLayout, 0, DeviceUtils.dip2px(this,160), new int[]{0, 0}, TYPE_NON_TOUCH);
        }
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordinatorlayout_weipinhui;
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
