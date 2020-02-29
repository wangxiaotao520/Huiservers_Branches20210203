package com.huacheng.huiservers.ui.index.coronavirus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：电子通行证列表
 * 时间：2020/2/22 16:09
 * created by DFF
 */
public class PermitListActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    EnhanceTabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    ArrayList<FragmentPermitList> mFragments = new ArrayList<>();
    FragmentPermitList currentFragment;


    String[] mTitle = null;
    String type_back;
    String company_id;
    String community_id;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("电子通行证");
        tv_right = findViewById(R.id.right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextColor(getResources().getColor(R.color.orange));
        tv_right.setText("申请通行证");
        findViewById(R.id.v_head_line).setBackgroundColor(getResources().getColor(R.color.white));
        contentInflate();
    }

    private void contentInflate() {
        mTitle = new String[]{"已通过", "审核中", "已拒绝", "已失效"};
//        for (int i = 0; i < tabTxt.length; i++) {
//            tabLayout.addTab(tabLayout.newTab().setText(tabTxt[i]));
//        }
//
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            if (tab != null) {
//                tab.setCustomView(getTabView(i));
//            }
//        }
        for (int i = 0; i < mTitle.length; i++) {
            tabLayout.addTab(mTitle[i]);
        }

        for (int i = 0; i < mTitle.length; i++) {
            FragmentPermitList ListCommon = new FragmentPermitList();
            Bundle bundle = new Bundle();
           /* if (type_back.equals("type_zf_dfk") || type_back.equals("type_zf_dsh")) {
                bundle.putString("type_back", type_back);
            }*/
            bundle.putInt("type", i);
            bundle.putString("company_id", company_id);
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
                    FragmentPermitList fragmentCommon = (FragmentPermitList) mFragments.get(tab.getPosition());
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
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PermitListActivity.this,AddPermitListActivity.class);
                intent.putExtra("company_id",company_id);
                intent.putExtra("community_id",community_id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_permit_list;
    }


    @Override
    protected void initIntentData() {
        //  type_back = this.getIntent().getExtras().getString("type");
        company_id= this.getIntent().getStringExtra("company_id");
        community_id= this.getIntent().getStringExtra("community_id");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }


}
