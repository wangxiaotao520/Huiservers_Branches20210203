package com.huacheng.huiservers.ui.index.houserent;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.houserent.fragment.MyHouseFragmentAdapter;
import com.huacheng.huiservers.ui.index.houserent.fragment.MyHousePropertyFragment;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:我的房产 页面
 * Author: badge
 * Create: 2018/11/7 10:43
 */
public class MyHousePropertyActivity extends BaseActivity {


    @BindView(R.id.tablayout)
    EnhanceTabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    View v_head_line;
    ArrayList<MyHousePropertyFragment> mFragmentList = new ArrayList<>();
    MyHousePropertyFragment currentFragment;

    MyHouseFragmentAdapter pagerAdapter;
    String[] tabTxt = null;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("我的房产");
        //   titleName.setTypeface(Typeface.DEFAULT_BOLD);//set title bold
        v_head_line=findViewById(R.id.v_head_line);
        v_head_line.setVisibility(View.GONE);
        /*TextPaint tp = titleName.getPaint();
        tp.setFakeBoldText(true);*/
        contentInflate();
    }

    private void contentInflate() {
        tabTxt = new String[]{"售房信息", "租房信息"};
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
        for(int i=0;i<tabTxt.length;i++){
            tabLayout.addTab(tabTxt[i]);
        }

        for (int i = 0; i < tabTxt.length; i++) {
            MyHousePropertyFragment tabFragment = new MyHousePropertyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("jump_type", jump_type);
            bundle.putInt("type", i);//0为售房 1为租房
            tabFragment.setArguments(bundle);
            mFragmentList.add(tabFragment);
        }
       // updateTabTextView(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()), true);
        pagerAdapter = new MyHouseFragmentAdapter(getSupportFragmentManager(), tabTxt, mFragmentList);
        viewpager.setAdapter(pagerAdapter);


        viewpager.setOffscreenPageLimit(tabTxt.length); //0 设置缓存页面，当前页面的相邻N各页面都会被缓存

        tabLayout.setupWithViewPager(viewpager);
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout.getTabLayout()));

        if (1 == jump_type) {//租房
            viewpager.setCurrentItem(1);
//            updateTabTextView(tabLayout.getTabAt(0), false);
//            updateTabTextView(tabLayout.getTabAt(1), true);

        } else {
            currentFragment = mFragmentList.get(0);
        }

//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                //在选中的顶部标签时，为viewpager设置currentitem
//                viewpager.setCurrentItem(tab.getPosition());
//                updateTabTextView(tab, true);
//
//                currentFragment = mFragmentList.get(tab.getPosition());
//                if (currentFragment != null) {
//                    currentFragment.selected_init();
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                updateTabTextView(tab, false);
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()<mFragmentList.size()){
                    //在这里传入参数
                    MyHousePropertyFragment fragmentCommon = (MyHousePropertyFragment) mFragmentList.get(tab.getPosition());
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


    /**
     * 引用tab item
     *
     * @param currentPosition
     * @return
     */
    private View getTabView(int currentPosition) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.circle_tab_item, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_textview);
        textView.setText(tabTxt[currentPosition]);
        return view;
    }


    /**
     * 字体加粗
     *
     * @param tab
     * @param isSelect
     */
    private void updateTabTextView(TabLayout.Tab tab, boolean isSelect) {

        if (isSelect) {
            //选中加粗
            TextView tabSelect = (TextView) tab.getCustomView().findViewById(R.id.tab_item_textview);
//            tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tabSelect.setTextColor(getResources().getColor(R.color.colorPrimary));
            tabSelect.setTextSize(16);
            tabSelect.setText(tab.getText());
        } else {
            TextView tabUnSelect = (TextView) tab.getCustomView().findViewById(R.id.tab_item_textview);
//            tabUnSelect.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tabUnSelect.setTextColor(getResources().getColor(R.color.text_color));
            tabUnSelect.setTextSize(16);
            tabUnSelect.setText(tab.getText());
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_house_property;
    }

    int jump_type = 1;//1是租房，2是售房

    @Override
    protected void initIntentData() {
        jump_type = getIntent().getIntExtra("jump_type", 0);

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }


}
