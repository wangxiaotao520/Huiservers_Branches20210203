package com.huacheng.huiservers.ui.index.workorder;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

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
 * 类描述：新版工单列表
 * 时间：2019/4/8 16:01
 * created by DFF
 */
public class WorkOrderListActivity extends BaseActivity {
    String[] mTitle = new String[]{"待服务", "服务中", "待支付", "已完成"};
    List<BaseFragment> mFragments = new ArrayList<>();
    TabLayout mTabLayout;
    ViewPager mViewPager;
    WorkOrderListcommon currentFragment;
    private int type;
    View v_head_line;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        findTitleViews();
        v_head_line=findViewById(R.id.v_head_line);
        v_head_line.setVisibility(View.GONE);
        titleName.setText("工单管理");
        for (int i = 0; i < mTitle.length; i++) {
            WorkOrderListcommon workOrderListSecondcommon = new WorkOrderListcommon();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            workOrderListSecondcommon.setArguments(bundle);
            mFragments.add(workOrderListSecondcommon);
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
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        currentFragment = (WorkOrderListcommon) mFragments.get(0);

    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            WorkOrderListcommon fragmentCommon = (WorkOrderListcommon) mFragments.get(position);
            currentFragment = fragmentCommon;
            // 到时候根据不同的参数请求
            currentFragment.onTabSelectedRefresh(null);

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(type);
            }
        },100);
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
        type = this.getIntent().getIntExtra("type", 0);
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
