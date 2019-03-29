package com.huacheng.huiservers.ui.center.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


/**
 * @Author: pyz
 * @Package: com.pyz.viewpagerdemo.adapter
 * @Description:
 * @Project: ViewPagerDemo
 * @Date: 2016/8/18 11:49
 */
public class ShopXSTimeFragmentAdapter extends FragmentStatePagerAdapter {
    String[] mTitles;
    private List<Fragment> fragments ;

    public ShopXSTimeFragmentAdapter(FragmentManager fm, String[] mTitles, List<Fragment> fragments) {
        super(fm);
        this.mTitles = mTitles;
        this.fragments=fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }
}
