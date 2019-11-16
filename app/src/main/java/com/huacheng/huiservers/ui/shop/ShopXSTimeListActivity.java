package com.huacheng.huiservers.ui.shop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.center.ShopXSIngFragment;
import com.huacheng.huiservers.ui.center.ShopXSReadyFragment;
import com.huacheng.huiservers.ui.center.adapter.ShopXSTimeFragmentAdapter;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.shop.bean.CateBean;
import com.huacheng.huiservers.ui.shop.inter.OnTabSelectListener;
import com.huacheng.huiservers.utils.CommonMethod;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.huacheng.huiservers.R.id.tablayout;

/**
 * 类：
 * 时间：2018/5/25 14:53
 * 功能描述:Huiservers
 */

public class ShopXSTimeListActivity extends BaseActivityOld {

    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(tablayout)
    TabLayout mTablayout;
    @BindView(R.id.lin_right)
    LinearLayout mLinRight;

    @BindView(R.id.vp_shop)
    ViewPager viewpagerShop;
    @BindView(R.id.txt_shop_num)
    TextView txtShopNum;
    @BindView(R.id.lin_car)
    LinearLayout linCar;

    private String[] titles = {"正在抢购", "准备活动"};
    SharePrefrenceUtil sharedPreferenceUtil;

    String login_type;
    List<CateBean> cates;
    private List<Fragment> fragments = new ArrayList<>();
    private ShopXSIngFragment shopXS4TimeListFragment;//正在抢购
    private ShopXSReadyFragment shopXS4TimeListFragment1;//准备活动
    private OnTabSelectListener currentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_xs_list);
        ButterKnife.bind(this);
//        SetTransStatus.GetStatus(this);
        sharedPreferenceUtil = new SharePrefrenceUtil(this);
        mTitleName.setText("限时抢购");
        linCar.setVisibility(View.VISIBLE);
        getLinshi();
        if (!login_type.equals("")) {// 登陆之后获取数量
            new CommonMethod(txtShopNum, this).getCartNum();
        }
        initFragment();
        setTabLayoutTwo(titles);

    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        shopXS4TimeListFragment = new ShopXSIngFragment();
        shopXS4TimeListFragment1 = new ShopXSReadyFragment();
        fragments.add(shopXS4TimeListFragment);
        fragments.add(shopXS4TimeListFragment1);
        currentFragment = shopXS4TimeListFragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLinshi();
        if (!login_type.equals("")) {// 登陆之后获取数量
            new CommonMethod(txtShopNum, this).getCartNum();
        }
    }

    private void getLinshi() {
        SharedPreferences preferencesLogin = this.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");
    }


    private void setTabLayoutTwo(String[] titles) {
        for (int i = 0; i < titles.length; i++) {
            mTablayout.addTab(mTablayout.newTab().setText(titles[i]));
        }
        for (int i = 0; i < mTablayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTablayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
        }

//        mTablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        updateTabTextView(mTablayout.getTabAt(mTablayout.getSelectedTabPosition()), true);
        //这里注意的是，因为我是在fragment中创建MyFragmentStatePagerAdapter，所以要传getChildFragmentManager()

        viewpagerShop.setAdapter(new ShopXSTimeFragmentAdapter(getSupportFragmentManager(), titles, fragments));
        /*if (cates != null) {
            //String limitTimeTab = cates.get(mTablayout.getSelectedTabPosition()).getId();

        } else {
            viewpagerShop.setAdapter(new ShopXSTimeFragmentAdapter(getSupportFragmentManager(),  titles));
        }*/
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        viewpagerShop.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
        viewpagerShop.setOffscreenPageLimit(titles.length);
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //在选中的顶部标签时，为viewpager设置currentitem
               /* if (tab.getPosition() == 0) {
                    mTvType.setText("抢单中，先到先得哦");
                    mTvSign.setText("距结束");
                } else {
                    mTvType.setText("准备中，先到先得哦");
                    mTvSign.setText("距开始");
                }*/
                viewpagerShop.setCurrentItem(tab.getPosition());
                updateTabTextView(tab, true);
                currentFragment = (OnTabSelectListener) fragments.get(tab.getPosition());
                currentFragment.onCatSelect("");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabTextView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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
            tabSelect.setTextColor(getResources().getColor(R.color.orange_jain));
            tabSelect.setText(tab.getText());
        } else {
            TextView tabUnSelect = (TextView) tab.getCustomView().findViewById(R.id.tab_item_textview);
            tabUnSelect.setTextColor(getResources().getColor(R.color.black_jain_87));
            tabUnSelect.setText(tab.getText());
        }
    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.circle_tab_item, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_textview);
        textView.setText(titles[position]);
        return view;
    }


    /**
     * 引用tab item
     *
     * @return
     */
    private View getTabView(String cateName) {
        View view = LayoutInflater.from(this).inflate(R.layout.circle_tab_item, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_textview);
        textView.setText(cateName);
        return view;
    }


    @OnClick({R.id.lin_left, R.id.lin_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_left:
                finish();
                break;
            case R.id.lin_car:
                getLinshi();
                if (!login_type.equals("")) {// 登陆之后获取数量
                 //   startActivity(new Intent(this, ShopCartActivityTwo.class));
                   Intent intent = new Intent(this, ShopCartActivityNew.class);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(new Intent(this, LoginVerifyCodeActivity.class));
                }
                break;

        }
    }

}
