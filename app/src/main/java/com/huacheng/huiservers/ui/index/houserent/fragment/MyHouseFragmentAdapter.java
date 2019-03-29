package com.huacheng.huiservers.ui.index.houserent.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:我的房产 Adapter
 * Author: badge
 * Create: 2018/11/7 14:50
 */
public class MyHouseFragmentAdapter extends FragmentPagerAdapter {

    private String[] mTabs;
    private List<MyHousePropertyFragment> mFragmentList;

    public MyHouseFragmentAdapter(FragmentManager supportFragmentManager, String[] tabs
            , ArrayList<MyHousePropertyFragment> fragmentList) {
        super(supportFragmentManager);
        this.mTabs = tabs;
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position];
    }

    /*默认返回POSITION_UNCHANGED，所以如果你不对Adapter的getItemPosition进行重写的画，就会出现无法更改内部视图效果的问题。
    下面对从PagerAdapter继承的代码进行改造，加上对getItemPosition的修改，让他直接返回POSITION_NONE，

    POSITION_NONE每次数据发生变化，
    都会引起视图的重建，比较消耗内存，所以不需要变化内部视图时，避免使用。*/
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
