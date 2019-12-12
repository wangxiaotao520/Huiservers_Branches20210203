package com.huacheng.huiservers.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.fragment.shop.ShopFragmentCommonImp;
import com.huacheng.huiservers.ui.fragment.shop.ShopFragmentSeckill;
import com.huacheng.huiservers.ui.fragment.shop.ShopFragmentStore;
import com.huacheng.huiservers.ui.fragment.shop.ShopFragmentTakeOut;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.TDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 商城首页
 * created by wangxiaotao
 * 2019/12/11 0011 上午 9:27
 */
public class ShopFragmentNew extends BaseFragment{

    private View mStatusBar;

    TabLayout mTabLayout;
    ViewPager mViewPager;
    private List<View> mCustomViewList;
    String[] mTitle = new String[]{"商城", "秒杀","外卖"};
    private RelativeLayout ly_search;
    private ShopFragmentCommonImp currentFragment;
    private List<ShopFragmentCommonImp> mFragments = new ArrayList<>();

    @Override
    public void initView(View view) {
        mStatusBar=view.findViewById(R.id.status_bar);
        mStatusBar.setBackgroundColor(getResources().getColor(R.color.white));
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        ly_search = view.findViewById(R.id.ly_search);
        mTabLayout =view. findViewById(R.id.tl_tab);
        mViewPager =view. findViewById(R.id.vp_pager);
        initTab();
    }
    /**
     * 初始化tab
     */
    private void initTab() {
        mCustomViewList = new ArrayList<>();
        for (int i = 0; i < mTitle.length; i++) {
            addTab(mTitle[i], i);
        }
        ShopFragmentStore shopFragmentStore = new ShopFragmentStore();
        mFragments.add(shopFragmentStore);
        ShopFragmentSeckill shopFragmentSeckill = new ShopFragmentSeckill();
        mFragments.add(shopFragmentSeckill);
        ShopFragmentTakeOut shopFragmentTakeOut = new ShopFragmentTakeOut();
        mFragments.add(shopFragmentTakeOut);


        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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

        mViewPager.setOffscreenPageLimit(1);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(listener);

        listener.onTabSelected(mTabLayout.getTabAt(0));
        currentFragment = mFragments.get(0);
    }

    /**
     * 添加tab
     *
     * @param tab
     */
    public void addTab(String tab, int position) {

        View customView = getTabView(mContext, tab);
        mCustomViewList.add(customView);
        if (position == 0) {
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(customView), 0, true);
        } else {
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(customView));
        }
    }

    /**
     * 获取Tab 显示的内容
     *
     * @param context
     * @param
     * @return
     */
    public View getTabView(Context context, String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
        TextView tabText = (TextView) view.findViewById(R.id.tab_item_text);
        View tab_item_indicator = (View) view.findViewById(R.id.tab_item_indicator);
        ViewGroup.LayoutParams layoutParams = tab_item_indicator.getLayoutParams();
        layoutParams.width  = DeviceUtils.dip2px(mContext,25);
        layoutParams.height = DeviceUtils.dip2px(mContext,2);
        tab_item_indicator.setLayoutParams(layoutParams);
        tabText.setText(text);
        return view;
    }

    TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
            currentFragment = mFragments.get(tab.getPosition());
            //TODO
        //    currentFragment.init(w_id,1);
            // onTabItemSelected(tab.getPosition());
            // Tab 选中之后，改变各个Tab的状态
            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                View view = mTabLayout.getTabAt(i).getCustomView();
                if (view == null) {
                    return;
                }
                TextView text = (TextView) view.findViewById(R.id.tab_item_text);
                View tab_item_indicator = (View) view.findViewById(R.id.tab_item_indicator);
                if (i == tab.getPosition()) { // 选中状态
                    text.setTextColor(mContext.getResources().getColor(R.color.title_color));
                    tab_item_indicator.setBackgroundColor(mContext.getResources().getColor(R.color.orange_bg));
                    tab_item_indicator.setVisibility(View.VISIBLE);
                    text.getPaint().setFakeBoldText(true);
                    text.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

                } else {// 未选中状态
                    text.setTextColor(mContext.getResources().getColor(R.color.gray_66));
                    tab_item_indicator.setBackgroundColor(mContext.getResources().getColor(R.color.orange_bg));
                    tab_item_indicator.setVisibility(View.INVISIBLE);
                    text.getPaint().setFakeBoldText(false);
                    text.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                }
            }

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };
    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop_new1;
    }
}
