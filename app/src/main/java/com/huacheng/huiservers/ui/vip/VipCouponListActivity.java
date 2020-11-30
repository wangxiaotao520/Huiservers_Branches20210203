package com.huacheng.huiservers.ui.vip;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.coupon.FragmentCouponHistory;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: vip专属好券
 * created by wangxiaotao
 * 2020/11/30 0030 09:16
 */
public class VipCouponListActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    EnhanceTabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    ArrayList<FragmentCouponHistory> mFragments = new ArrayList<>();
    FragmentCouponHistory currentFragment;


    String[] mTitle = null;



    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("VIP专属好券");
        contentInflate();
    }

    private void contentInflate() {
        mTitle = new String[]{"待使用","已使用", "已过期"};
//        for (int i = 0; i < tabTxt.length; i++) {
//            tabLayout.addTab(tabLayout.newTab().setText(tabTxt[i]));
//        }
//
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            if (tab != null) {
//                tab.setCustomView(getTabView(i));
//            }
//        }
        for (int i = 0; i < mTitle.length; i++) {
            tabLayout.addTab(mTitle[i]);
        }

        for (int i = 0; i < mTitle.length; i++) {
            FragmentCouponHistory ListCommon = new FragmentCouponHistory();
            Bundle bundle = new Bundle();
           /* if (type_back.equals("type_zf_dfk") || type_back.equals("type_zf_dsh")) {
                bundle.putString("type_back", type_back);
            }*/
            bundle.putInt("type", i+6);
            ListCommon.setArguments(bundle);
            mFragments.add(ListCommon);
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
                    FragmentCouponHistory fragmentCommon = (FragmentCouponHistory) mFragments.get(tab.getPosition());
                    currentFragment = fragmentCommon;
                    currentFragment.selected_init();
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

