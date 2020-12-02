package com.huacheng.huiservers.ui.center;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;
import com.huacheng.libraryservice.utils.NullUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 店铺关注列表
 * created by wangxiaotao
 * 2020/12/2 0002 09:23
 */
public class MyStoreFollowListActivity extends BaseActivity {
    @BindView(R.id.left)
    ImageView left;
    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.tl_tab)
    EnhanceTabLayout mTabLayout;
    @BindView(R.id.vp_pager)
    ViewPager mViewPager;
    String[] mTitle = new String[]{"零售商", "服务商"};
    List<BaseFragment> mFragments = new ArrayList<>();
    @Override
    protected void initView() {
        ButterKnife.bind(this);

        for (int i = 0; i < mTitle.length; i++) {
            mTabLayout.addTab(mTitle[i]);
        }
        for (int i = 0; i < mTitle.length; i++) {
            FragmentMyStoreFollow fragmentCommon = new FragmentMyStoreFollow();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);

            fragmentCommon.setArguments(bundle);
//            fragmentCommon.setListViewOnTouchListener(this);
            mFragments.add(fragmentCommon);
        }


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
        mViewPager.setOffscreenPageLimit(1); //0 设置缓存页面，当前页面的相邻N各页面都会被缓存

        mTabLayout.setupWithViewPager(mViewPager);
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout.getTabLayout()));
      /*  mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);*/
        currentFragment = (FragmentMyStoreFollow) mFragments.get(0);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        linLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() < mFragments.size()) {
                    //在这里传入参数
                    FragmentMyStoreFollow fragmentCommon = (FragmentMyStoreFollow) mFragments.get(tab.getPosition());
                    //MyHousePropertyFragment fragmentCommon = (MyHousePropertyFragment) mFragments.get(tab.getPosition());
                    currentFragment = fragmentCommon;
                    fragmentCommon.onTabSelectedRefresh("");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        String tabType = getIntent().getStringExtra("tabType");
        if (!NullUtil.isStringEmpty(tabType)) {
            if ("service".equals(tabType) || "servicePop".equals(tabType)) {
                // mTabLayout.
                //  mTabLayout.getTabAt(1).select();
                mViewPager.setCurrentItem(1);

            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_store_follow_list;
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
