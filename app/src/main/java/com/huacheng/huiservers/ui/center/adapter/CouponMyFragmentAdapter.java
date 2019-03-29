package com.huacheng.huiservers.ui.center.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.huacheng.huiservers.ui.center.CouponFragment;
import com.huacheng.huiservers.ui.center.CouponToShopFragment;


/**
 * @Author: pyz
 * @Package: com.pyz.viewpagerdemo.adapter
 * @Description:
 * @Project: ViewPagerDemo
 * @Date: 2016/8/18 11:49
 */
public class CouponMyFragmentAdapter extends FragmentStatePagerAdapter {
    private String[] mTitles;

    public CouponMyFragmentAdapter(FragmentManager fm, String[] titles) {
        super(fm);

        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (mTitles[position].equals("优惠券")) {
            return new CouponFragment(mTitles[position]);
        } else if (mTitles[position].equals("到店券")) {
            return new CouponToShopFragment(mTitles[position]);
        }
        return null;
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
