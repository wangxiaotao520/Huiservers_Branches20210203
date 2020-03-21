package com.huacheng.huiservers.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.MyInfoCircleFragment;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.TDevice;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: CoordinatorLayout +appbarlayout +CollapsingToolbarLayout+tablayout+的demo
 * created by wangxiaotao
 * 2020/3/20 0020 上午 9:50
 *
 *只使用appbarlayout的demo参照OldFragment
 */
public class CoordinatorLayoutActivity extends BaseActivity {
    private String[] mTitles = {"我的邻里1","我的邻里2"};
    EnhanceTabLayout mEnhanceTabLayout;
    ViewPager mViewPager;
    private SmartRefreshLayout refreshLayout;
    private AppBarLayout appBarLayout;

    private List<MyInfoCircleFragment> mFragments = new ArrayList<>();
    private CollapsingToolbarLayout collapsing_toolbar;
    private Toolbar toolbar;
    private View lin_left;
    private TextView title_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);

        appBarLayout = findViewById(R.id.appbarlayout);
        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        toolbar = findViewById(R.id.toolbar);

        /**
         * 这里有两种方式设置全屏（前提已经使用了沉浸式布局） 这里我使用的第二种，第一种是因为我把状态栏的颜色改成了别的颜色
         * 1.设置fitsSystemWindows的方式（注意要给image所在的布局设置fitsSystemWindows=true）
         * 2.给toolbar设置一个状态栏高度的margin
         */
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.setMargins(0, TDevice.getStatuBarHeight(this),0,0);
        toolbar.setLayoutParams(layoutParams);
        setSupportActionBar(toolbar);
        lin_left = findViewById(R.id.lin_left);
        lin_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_name = findViewById(R.id.title_name);
        title_name.setText("TITLE");
        mEnhanceTabLayout = findViewById(R.id.enhance_tab_layout);
        mViewPager = findViewById(R.id.vp_pager);
        for (int i = 0; i < mTitles.length; i++) {
            mEnhanceTabLayout.addTab(mTitles[i]);

            MyInfoCircleFragment myInfoCircleFragment = new MyInfoCircleFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            myInfoCircleFragment.setArguments(bundle);
            mFragments.add(myInfoCircleFragment);

        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position % mTitles.length];
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position % mTitles.length);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }
        });
        mEnhanceTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mEnhanceTabLayout.getTabLayout()));

        currentFragment = mFragments.get(0);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                verticalOffset = Math.abs(verticalOffset);
                if (verticalOffset==0){
                    if (refreshLayout!=null){
                        refreshLayout.setEnableRefresh(true);
                    }
                }else {
                    if (refreshLayout!=null){
                        refreshLayout.setEnableRefresh(false);
                    }
                }
                float alpha_show = 1.0f * verticalOffset / DeviceUtils.dip2px(mContext,50);
                if (alpha_show>1){
                    alpha_show=1;
                }
                if (title_name!=null){
                    title_name.setAlpha(alpha_show);
                }
            }
        });
        mEnhanceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()<mFragments.size()){
                    //在这里传入参数
                    MyInfoCircleFragment fragmentCommon = (MyInfoCircleFragment) mFragments.get(tab.getPosition());
                    currentFragment = fragmentCommon;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordinatorlayout_demo;
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
