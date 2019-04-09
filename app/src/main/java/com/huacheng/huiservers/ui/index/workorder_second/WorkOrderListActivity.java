package com.huacheng.huiservers.ui.index.workorder_second;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：新版工单列表
 * 时间：2019/4/8 16:01
 * created by DFF
 */
public class WorkOrderListActivity extends BaseActivity {
    String[] mTitle = new String[]{"待服务", "服务中", "待支付", "已完成"};
    List<BaseFragment> mFragments = new ArrayList<>();
    TabLayout mTabLayout;
    ViewPager mViewPager;
    WorkOrderListcommon currentFragment;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("工单管理");
        for (int i = 0; i < mTitle.length; i++) {
            WorkOrderListcommon workOrderListSecondcommon = new WorkOrderListcommon();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            workOrderListSecondcommon.setArguments(bundle);
            mFragments.add(workOrderListSecondcommon);
        }
        mTabLayout = findViewById(R.id.tl_tab);
        mViewPager = findViewById(R.id.vp_pager);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position % mTitle.length);
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        });
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        currentFragment = (WorkOrderListcommon) mFragments.get(0);

    }
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            WorkOrderListcommon fragmentCommon = (WorkOrderListcommon) mFragments.get(position);
            currentFragment = fragmentCommon;
            // 到时候根据不同的参数请求
            currentFragment.onTabSelectedRefresh(null);

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mViewPager.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_order;
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
