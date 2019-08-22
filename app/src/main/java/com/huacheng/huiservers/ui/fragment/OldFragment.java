package com.huacheng.huiservers.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.fragment.old.FragmentOldArticle;
import com.huacheng.huiservers.ui.fragment.old.FragmentOldCommonImp;
import com.huacheng.huiservers.ui.fragment.old.FragmentOldHuodong;
import com.huacheng.huiservers.ui.index.oldservice.CalendarViewActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldFileActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldHardwareActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldServiceWarmActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldUserActivity;
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
public class OldFragment extends BaseFragment implements View.OnClickListener {

    private TextView title_name;
    View mStatusBar;
    private String[] mTitles = {"资讯","活动"};
    EnhanceTabLayout mEnhanceTabLayout;
    ViewPager mViewPager;
    private SmartRefreshLayout refreshLayout;
    private AppBarLayout appBarLayout;

    private List<FragmentOldCommonImp> mFragments = new ArrayList<>();
    private FragmentOldCommonImp currentFragment;

    private LinearLayout ll_header;
    private RelativeLayout rl_title_container;
    private RelativeLayout rl_healthy; //健康档案
    private RelativeLayout rl_data;  //智能数据
    private RelativeLayout rl_warm;  //亲情关怀
    private RelativeLayout rl_medicine;  //用药提醒
    private SimpleDraweeView sdv_head; //头像
    private TextView tv_dad_mom; //身份
    private TextView tv_name;  //姓名
    private ImageView iv_sex;  //性别
    private LinearLayout ll_age_address; //年龄地址
    private TextView tv_age;  //年龄
    private TextView tv_address; //地址
    private TextView tv_change_person;  //切换长者
    private LinearLayout ll_change_person;

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

        initHeader(view);

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

        currentFragment = mFragments.get(0);
    }

    /**
     * 初始化头布局
     */
    private void initHeader(View view) {
        ll_header = view.findViewById(R.id.ll_header);
        rl_title_container = view.findViewById(R.id.rl_title_container);
        sdv_head = view.findViewById(R.id.sdv_head);
        tv_dad_mom = view.findViewById(R.id.tv_dad_mom);
        tv_name = view.findViewById(R.id.tv_name);
        iv_sex = view.findViewById(R.id.iv_sex);
        ll_age_address = view.findViewById(R.id.ll_age_address);
        tv_age = view.findViewById(R.id.tv_age);
        tv_address = view.findViewById(R.id.tv_address);
        ll_change_person = view.findViewById(R.id.ll_change_person);
        tv_change_person = view.findViewById(R.id.tv_change_person);

//        rl_title_container.setBackgroundResource(R.mipmap.bg_old_orange);
//        ll_change_person.setBackgroundResource(R.drawable.all_shape_round_shadow_left_orange);
        iv_sex.setVisibility(View.GONE);
        tv_dad_mom.setVisibility(View.GONE);
        ll_age_address.setVisibility(View.GONE);
        tv_name.setText("暂未认证");

        rl_healthy = view.findViewById(R.id.rl_healthy);
        rl_data = view.findViewById(R.id.rl_data);
        rl_warm = view.findViewById(R.id.rl_warm);
        rl_medicine = view.findViewById(R.id.rl_medicine);
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
        rl_healthy.setOnClickListener(this);
        rl_data.setOnClickListener(this);
        rl_warm.setOnClickListener(this);
        rl_medicine.setOnClickListener(this);
        ll_change_person.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_old;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_healthy:
                startActivity(new Intent(mActivity, OldFileActivity.class));
                break;
            case R.id.rl_data:
                startActivity(new Intent(mActivity, OldHardwareActivity.class));
                break;
            case R.id.rl_warm:
                startActivity(new Intent(mActivity, OldServiceWarmActivity.class));
                break;
            case R.id.rl_medicine:
                startActivity(new Intent(mActivity, CalendarViewActivity.class));
                break;
            case R.id.ll_change_person:
                startActivity(new Intent(mActivity, OldUserActivity.class));
                break;
                default:
                    break;
        }
    }
}
