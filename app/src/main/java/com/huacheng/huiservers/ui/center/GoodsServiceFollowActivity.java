package com.huacheng.huiservers.ui.center;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：商品服务关注
 * 时间：2020/12/2 09:28
 * created by DFF
 */
public class GoodsServiceFollowActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    EnhanceTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;

    ArrayList<GoodsServiceFollowFragment> mFragments = new ArrayList<>();
    GoodsServiceFollowFragment currentFragment;

    String[] mTitle = new String[]{"商品", "服务"};


    @Override
    protected void initView() {
        ButterKnife.bind(this);

        for (int i = 0; i < mTitle.length; i++) {
            tabLayout.addTab(mTitle[i]);
        }

        for (int i = 0; i < mTitle.length; i++) {
            GoodsServiceFollowFragment ListCommon = new GoodsServiceFollowFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
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
                    GoodsServiceFollowFragment fragmentCommon = (GoodsServiceFollowFragment) mFragments.get(tab.getPosition());
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
        mLinLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_follow_goods_service;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
