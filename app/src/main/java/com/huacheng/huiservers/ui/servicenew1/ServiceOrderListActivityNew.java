package com.huacheng.huiservers.ui.servicenew1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 新的服务订单
 * created by wangxiaotao
 * 2020/6/17 0017 15:55
 */
public class ServiceOrderListActivityNew extends BaseActivity {

    @BindView(R.id.tablayout)
    EnhanceTabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    ArrayList<FragmentServiceOrderCommon> mFragments = new ArrayList<>();
    FragmentServiceOrderCommon currentFragment;


    String[] mTitle = null;



    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("服务订单");
        contentInflate();
    }

    private void contentInflate() {
        mTitle = new String[]{"全部", "待服务","待评价","退款/售后"};

        for (int i = 0; i < mTitle.length; i++) {
            tabLayout.addTab(mTitle[i]);
        }

        for (int i = 0; i < mTitle.length; i++) {
            FragmentServiceOrderCommon fragmentCommon = new FragmentServiceOrderCommon();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            fragmentCommon.setArguments(bundle);
            mFragments.add(fragmentCommon);
        }
        // updateTabTextView(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()), true);
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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

        viewpager.setOffscreenPageLimit(mTitle.length); //0 设置缓存页面，当前页面的相邻N各页面都会被缓存

        tabLayout.setupWithViewPager(viewpager);
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout.getTabLayout()));

        currentFragment = mFragments.get(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() < mFragments.size()) {
                    //在这里传入参数
                    FragmentServiceOrderCommon fragmentCommon = (FragmentServiceOrderCommon) mFragments.get(tab.getPosition());
                    currentFragment = fragmentCommon;
                    currentFragment.onTabSelectedRefresh(null);
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
    protected void initData() {


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_permit_list;
    }


    @Override
    protected void initIntentData() {
        //  type_back = this.getIntent().getExtras().getString("type");

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }


}

