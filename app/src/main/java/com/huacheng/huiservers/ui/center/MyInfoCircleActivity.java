package com.huacheng.huiservers.ui.center;

import android.content.Intent;
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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.center.bean.PersoninfoBean;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;
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
    private View mStatusBar;
    private TextView tv_edit;
    private TextView tv_title;
    private RelativeLayout ry_left;
    private SimpleDraweeView sdv_image;
    PersoninfoBean infoBean;
    List<BaseFragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //isStatusBar = true;
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        //状态栏
       /* mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha(0);*/

        appbar = findViewById(R.id.appbar);
        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        toolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.tv_title);
        mEnhanceTabLayout = findViewById(R.id.enhance_tab_layout);
        setSupportActionBar(toolbar);
        toolbar.setTitle(infoBean.getNickname());
        tv_title.setText(infoBean.getNickname());
//      设置标题
        collapsing_toolbar.setTitle(infoBean.getNickname());
        sdv_image = findViewById(R.id.sdv_image);
        if (!StringUtils.isEmpty(infoBean.getAvatars())) {
            FrescoUtils.getInstance().setImageUri(sdv_image, StringUtils.getImgUrl(infoBean.getAvatars()));
        }

        ry_left = findViewById(R.id.ry_left);
        mViewPager = findViewById(R.id.vp_pager);
        tv_edit = findViewById(R.id.tv_edit);

        for (int i = 0; i < mTitles.length; i++) {
            mEnhanceTabLayout.addTab(mTitles[i]);

            MyInfoCircleFragment myInfoCircleFragment = new MyInfoCircleFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            myInfoCircleFragment.setArguments(bundle);
            mFragments.add(myInfoCircleFragment);

        }
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

    /* ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
         @Override
         public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
         }

         @Override
         public void onPageSelected(int position) {
             MyInfoCircleFragment fragmentCommon = (MyInfoCircleFragment) mFragments.get(position);
             currentFragment = fragmentCommon;
             // 到时候根据不同的参数请求onTabSelectedRefresh
             currentFragment.(null);

         }

         @Override
         public void onPageScrollStateChanged(int state) {
         }
     };
 */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initListener() {
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyInfoCircleActivity.this, MyInfoActivity.class));
            }
        });
        //返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ry_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        appbar.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > 0) {
                    mStatusBar.setAlpha(1);
                } else {
                    mStatusBar.setAlpha(0);
                }
            }
        });
        // mViewPager.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_my_info_circle;
    }

    @Override
    protected void initIntentData() {
        infoBean = (PersoninfoBean) this.getIntent().getSerializableExtra("infoBean");

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
        collapsing_toolbar.setTitle(bean.getNickname());
        if (!StringUtils.isEmpty(bean.getAvatars())) {
            FrescoUtils.getInstance().setImageUri(sdv_image, StringUtils.getImgUrl(bean.getAvatars()));
        }
    }
}
