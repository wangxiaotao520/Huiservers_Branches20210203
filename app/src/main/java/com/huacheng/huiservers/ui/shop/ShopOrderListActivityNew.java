package com.huacheng.huiservers.ui.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.model.XorderDetailBean;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 新版订单详情
 * created by wangxiaotao
 * 2020/1/8 0008 上午 8:43
 */
public class ShopOrderListActivityNew extends BaseActivity {


    @BindView(R.id.tablayout)
    EnhanceTabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    ArrayList<FragmentShopOrderListNew> mFragments = new ArrayList<>();
    FragmentShopOrderListNew currentFragment;


    String[] mTitle = null;
    String type_back;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("商城订单");
        findViewById(R.id.v_head_line).setBackgroundColor(getResources().getColor(R.color.white));
        contentInflate();
    }

    private void contentInflate() {
        mTitle = new String[]{"待付款", "待收货", "已完成", "退款/售后"};
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
            FragmentShopOrderListNew shopOrderListCommon = new FragmentShopOrderListNew();
            Bundle bundle = new Bundle();
           /* if (type_back.equals("type_zf_dfk") || type_back.equals("type_zf_dsh")) {
                bundle.putString("type_back", type_back);
            }*/
            bundle.putInt("type", i);
            shopOrderListCommon.setArguments(bundle);
            mFragments.add(shopOrderListCommon);
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

      /*  if (type_back.equals("type_zf_dfk")) {
            viewpager.setCurrentItem(0);
        } else if (type_back.equals("type_zf_dsh")) {
            viewpager.setCurrentItem(0);
        } else {*/
        currentFragment = mFragments.get(0);
        // }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() < mFragments.size()) {
                    //在这里传入参数
                    FragmentShopOrderListNew fragmentCommon = (FragmentShopOrderListNew) mFragments.get(tab.getPosition());
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

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_shop_order_list;
    }


    @Override
    protected void initIntentData() {
        //  type_back = this.getIntent().getExtras().getString("type");

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    /**
     * 评价完成 申请退款 订单中支付成功
     *
     * @param info
     */
    //1删除 2.评价 3.退款   4 支付成功 5收货
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void back(XorderDetailBean info) {
        if (info != null) {
            if (info.getBack_type() == 4) {//支付成功
                viewpager.setCurrentItem(0);
                currentFragment.setRefreh();
            } else if (info.getBack_type() == 2) {//评价
                //viewpager.setCurrentItem(2);
            } else if (info.getBack_type() == 3) {//退款
                if (info.getTuikuan_type() == 1) {
                    viewpager.setCurrentItem(3);
                    currentFragment.setRefreh();
                }
            } else if (info.getBack_type() == 5) {//收货
                if (info.getShouhuo_type() == 1) {
                    viewpager.setCurrentItem(2);
                    currentFragment.setRefreh();
                }
            }

        }
    }
}

