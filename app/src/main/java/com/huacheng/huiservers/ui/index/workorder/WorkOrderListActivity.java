package com.huacheng.huiservers.ui.index.workorder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.EventBusWorkOrderModel;
import com.huacheng.huiservers.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:物业工单列表
 * created by wangxiaotao
 * 2018/12/12 0012 上午 10:53
 */
public class WorkOrderListActivity extends BaseActivity {

    private List<String> mTitles = new ArrayList<>();
    private List<FragmentWorkOrder> mFragments = new ArrayList<>();

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("订单中心");

        mTabLayout = findViewById(R.id.tl_tab);
        mViewPager = findViewById(R.id.vp_pager);

    }

    @Override
    protected void initData() {
        requestData();
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            FragmentWorkOrder fragmentCommon = (FragmentWorkOrder) mFragments.get(position);
            currentFragment = fragmentCommon;
            fragmentCommon.isRefresh();
//            updateTabCount();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    /**
     * 请求数据
     */
    private void requestData() {
        mTitles.clear();
        mTitles.add("待服务");
        mTitles.add("服务中");
        mTitles.add("已完成");
        getCategoryFragemt();
    }

    private void getCategoryFragemt() {
        for (int i = 0; i < mTitles.size(); i++) {
            FragmentWorkOrder fragmentCommon = new FragmentWorkOrder();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            fragmentCommon.setArguments(bundle);
            mFragments.add(fragmentCommon);
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position % mTitles.size());
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position % mTitles.size());
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }
        });
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        currentFragment = (FragmentWorkOrder) mFragments.get(0);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setCustomView(getTabView(mTitles.get(i)));

        }
        updateTabTextView(mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()), true);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        TextView tabSelect = tab.getCustomView().findViewById(R.id.tabtext);
                        tabSelect.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tabSelect.setTextSize(15);

                        break;
                    case 1:
                        TextView tabSelect1 = tab.getCustomView().findViewById(R.id.tabtext);
                        tabSelect1.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tabSelect1.setTextSize(15);

                        break;
                    case 2:
                        TextView tabSelect2 = tab.getCustomView().findViewById(R.id.tabtext);
                        tabSelect2.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tabSelect2.setTextSize(15);
                        break;
                    case 3:
                        TextView tabSelect3 = tab.getCustomView().findViewById(R.id.tabtext);
                        tabSelect3.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tabSelect3.setTextSize(15);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        TextView tabSelect = tab.getCustomView().findViewById(R.id.tabtext);
                        tabSelect.setTextColor(getResources().getColor(R.color.text_color));
                        tabSelect.setTextSize(15);

                        break;
                    case 1:
                        TextView tabSelect1 = tab.getCustomView().findViewById(R.id.tabtext);
                        tabSelect1.setTextColor(getResources().getColor(R.color.text_color));
                        tabSelect1.setTextSize(15);

                        break;
                    case 2:
                        TextView tabSelect2 = tab.getCustomView().findViewById(R.id.tabtext);
                        tabSelect2.setTextColor(getResources().getColor(R.color.text_color));
                        tabSelect2.setTextSize(15);
                        break;
                    case 3:
                        TextView tabSelect3 = tab.getCustomView().findViewById(R.id.tabtext);
                        tabSelect3.setTextColor(getResources().getColor(R.color.text_color));
                        tabSelect3.setTextSize(15);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 字体变色
     *
     * @param tab
     * @param isSelect
     */
    private void updateTabTextView(TabLayout.Tab tab, boolean isSelect) {

        if (isSelect) {
            TextView tabSelect = (TextView) tab.getCustomView().findViewById(R.id.tabtext);
            tabSelect.setTextColor(getResources().getColor(R.color.colorPrimary));
            tabSelect.setTextSize(15);
            tabSelect.setText(tab.getText());
        } else {
            TextView tabUnSelect = (TextView) tab.getCustomView().findViewById(R.id.tabtext);
            tabUnSelect.setTextColor(getResources().getColor(R.color.text_color));
            tabUnSelect.setTextSize(15);
            tabUnSelect.setText(tab.getText());
        }
    }

    public View getTabView(String cateName) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.worder_tab_view, null);
        TextView tv = v.findViewById(R.id.tabtext);
        tv.setText(cateName);

        return v;
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

    /**
     * @param model
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshWorkOrder(EventBusWorkOrderModel model) {
        if (model != null) {
            String cancel_wid = model.getWo_id();
            if (model.getEvent_type() == 0) {//取消订单
                mViewPager.setCurrentItem(2);
                //   // 跳到第三列刷新
                mFragments.get(2).refreshIndeed();
            } else if (model.getEvent_type() == 1) {//预付

            } else if (model.getEvent_type() == 2) {
                // 付款
                mViewPager.setCurrentItem(2);
                // 跳到第三列刷新
                mFragments.get(2).refreshIndeed();
            }

        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
