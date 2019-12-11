package com.huacheng.huiservers.ui.fragment.indexcat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.EventBusWorkOrderModel;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：通知公告
 * 时间：2019/10/19 10:33
 * created by DFF
 */
public class IndexNoticeListActivity extends BaseActivity{
    String[] mTitle = new String[]{"物业通知", "活动通知"};
    List<BaseFragment> mFragments = new ArrayList<>();
    TabLayout mTabLayout;
    ViewPager mViewPager;
    IndexNoticeListCommon currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

        for (int i = 0; i < mTitle.length; i++) {
            IndexNoticeListCommon indexNoticeListCommon = new IndexNoticeListCommon();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            indexNoticeListCommon.setArguments(bundle);
            mFragments.add(indexNoticeListCommon);
        }
        mTabLayout = findViewById(R.id.tl_tab);
        mViewPager = findViewById(R.id.vp_pager);

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
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);

        currentFragment = (IndexNoticeListCommon) mFragments.get(0);

    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            IndexNoticeListCommon fragmentCommon = (IndexNoticeListCommon) mFragments.get(position);
            currentFragment = fragmentCommon;
            // 到时候根据不同的参数请求
            //currentFragment.onTabSelectedRefresh(null);

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mViewPager.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_index_notice_list;
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
     * 全部跳转到已完成
     * @param model
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshLIst(EventBusWorkOrderModel model) {
        if (model != null) {
            if (model.getEvent_back_type() == 0) {//取消订单跳转到已完成
                mViewPager.setCurrentItem(3);
                // mFragments.
            }else  if (model.getEvent_back_type() == 1||model.getEvent_back_type() == 2) {//评价订单跳转到已完成 支付完成
                mViewPager.setCurrentItem(3);
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
