package com.huacheng.huiservers.ui.center;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.model.UcenterIndex;
import com.huacheng.huiservers.model.UserIndex;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.model.PersoninfoBean;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：我的信息圈子
 * 时间：2019/12/31 19:04
 * created by DFF
 */
public class MyInfoCircleActivity extends BaseActivity {
    private AppBarLayout appbar;
    private CollapsingToolbarLayout collapsing_toolbar;
    private Toolbar toolbar;
    private EnhanceTabLayout mEnhanceTabLayout;
    private String[] mTitles = {"我的邻里"};
    private ViewPager mViewPager;


    TextView nameTx,addressTx ,signatureTx;
    ImageView isVip,vipLevel;
    private SimpleDraweeView sdv_image;
    UcenterIndex.DataBean infoBean;
    List<BaseFragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar = true;
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initView() {

        appbar = findViewById(R.id.appbar);
        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        toolbar = findViewById(R.id.toolbar);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.setMargins(0, TDevice.getStatuBarHeight(this), 0, 0);
        toolbar.setLayoutParams(layoutParams);
        toolbar.setTitle(infoBean.getNickname());
        setSupportActionBar(toolbar);
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                float range = appBarLayout.getTotalScrollRange();
                float offset = Math.abs(verticalOffset);

                int alpha = (int) (0xff * (offset / range));
                int color = 51;
                toolbar.setTitleTextColor(Color.argb(alpha, color, color, color));

            }
        });
        nameTx = findViewById(R.id.name);
        addressTx = findViewById(R.id.address);
        signatureTx = findViewById(R.id.signature);
        isVip = findViewById(R.id.isvip);
        vipLevel = findViewById(R.id.vip_level);

        nameTx.setText(infoBean.getNickname());
        signatureTx.setText(infoBean.getSignature());
        isVip.setVisibility(infoBean.getIs_vip().equals("1") ? View.VISIBLE : View.GONE);
         // todo  Address
         addressTx.setText(infoBean.getAddress());
        sdv_image = findViewById(R.id.sdv_image);
        if (!StringUtils.isEmpty(infoBean.getAvatars())) {
            FrescoUtils.getInstance().setImageUri(sdv_image, StringUtils.getImgUrl(infoBean.getAvatars()));
        }



        mEnhanceTabLayout = findViewById(R.id.enhance_tab_layout);

        for (int i = 0; i < mTitles.length; i++) {
            mEnhanceTabLayout.addTab(mTitles[i]);

            MyInfoCircleFragment myInfoCircleFragment = new MyInfoCircleFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            myInfoCircleFragment.setArguments(bundle);
            mFragments.add(myInfoCircleFragment);

        }
        mViewPager = findViewById(R.id.vp_pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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

    @Override
    protected void initData() {

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initListener() {

        //返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_my_info_circle;
    }

    @Override
    protected void initIntentData() {
        infoBean = (UcenterIndex.DataBean) getIntent().getSerializableExtra("infoBean");

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(PersoninfoBean bean) {
        //2.更换个人信息后刷新
        if (!StringUtils.isEmpty(bean.getAvatars())) {
            FrescoUtils.getInstance().setImageUri(sdv_image, StringUtils.getImgUrl(bean.getAvatars()));
        }
    }
}
