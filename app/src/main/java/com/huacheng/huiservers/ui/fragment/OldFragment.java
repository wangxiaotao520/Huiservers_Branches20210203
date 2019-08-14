package com.huacheng.huiservers.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.fragment.old.FragmentOldArticle;
import com.huacheng.huiservers.ui.fragment.old.FragmentOldCommonImp;
import com.huacheng.huiservers.ui.fragment.old.FragmentOldHuodong;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;
import com.huacheng.libraryservice.utils.TDevice;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 居家养老
 * created by wangxiaotao
 * 2019/8/13 0013 上午 10:58
 */
public class OldFragment extends BaseFragment{

    private TextView title_name;
    View mStatusBar;
    private String[] mTitles = {"资讯","活动"};
    EnhanceTabLayout mEnhanceTabLayout;
    ViewPager mViewPager;
    private SmartRefreshLayout refreshLayout;
    private AppBarLayout appBarLayout;

    private List<FragmentOldCommonImp> mFragments = new ArrayList<>();
    private FragmentOldCommonImp currentFragment;
    @Override
    public void initView(View view) {
        //状态栏
        mStatusBar=view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        mStatusBar.setAlpha((float)1);
        view.findViewById(R.id.lin_left).setVisibility(View.GONE);
        title_name = view.findViewById(R.id.title_name);
        title_name.setText("居家养老");

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadMore(false);

        appBarLayout = view.findViewById(R.id.appbarlayout);



        mEnhanceTabLayout = view.findViewById(R.id.enhance_tab_layout);
        mViewPager =view. findViewById(R.id.vp_pager);
        for(int i=0;i<mTitles.length;i++){
            mEnhanceTabLayout.addTab(mTitles[i]);
        }
        //初始化数据
        mFragments.add(new FragmentOldArticle());
        mFragments.add(new FragmentOldHuodong());

        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
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
            }
        });
        mEnhanceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //在这里传入参数
                FragmentOldCommonImp fragmentCommon = (FragmentOldCommonImp) mFragments.get(tab.getPosition());
                currentFragment = fragmentCommon;
                fragmentCommon.isRefresh();
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
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_old;
    }
}
