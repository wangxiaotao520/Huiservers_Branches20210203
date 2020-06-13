package com.huacheng.huiservers.ui.index.party;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.circle.CircleTabFragmentNew;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 党建首页
 * created by wangxiaotao
 * 2020/6/12 0012 09:55
 */
public class CommunistPartyIndexActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    EnhanceTabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private ArrayList<CircleTabFragmentNew> mFragmentList = new ArrayList<CircleTabFragmentNew>();
    private CircleTabFragmentNew currentFragment;

    String[] mTitle = null;



    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("社区党建");
      //  contentInflate();
    }

    private void contentInflate( List<BannerBean> tabs) {
 //       mTitle = new String[]{"资讯", "微视频"};
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

        for (int i = 0; i < tabs.size(); i++) {
            CircleTabFragmentNew tabFragment = new CircleTabFragmentNew();
            Bundle bundle = new Bundle();
            bundle.putString("mCid", tabs.get(i).getId());
            bundle.putInt("mPro", tabs.get(i).getIs_pro());//是否是物业公告字段
            bundle.putString("type", i + "");
            bundle.putInt("type_position", 0);
            tabFragment.setArguments(bundle);
            mFragmentList.add(tabFragment);
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
                return mFragmentList.get(position % mTitle.length);
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

        currentFragment = mFragmentList.get(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() < mFragmentList.size()) {
                    //在这里传入参数
                    currentFragment = mFragmentList.get(tab.getPosition());
                    if (currentFragment != null) {
                        currentFragment.selected_init();
                    }
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
     * 邻里分类
     *
     */
    @Override
    public void initData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("sign", "0");
        params.put("type", "2");
        MyOkHttp.get().get(ApiHttpClient.GET_SOCIAL_CAT, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<BannerBean> modelCatTabs = (List<BannerBean>) JsonUtil.getInstance().getDataArrayByName(response, "data", BannerBean.class);
                    if (modelCatTabs != null && modelCatTabs.size() > 0) {
                        mTitle = new String[modelCatTabs.size()];
                        for (int i = 0; i < modelCatTabs.size(); i++) {
                            mTitle[i]=modelCatTabs.get(i).getC_name()+"";
                        }
                        contentInflate(modelCatTabs);
                    }

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }
    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_party_index;
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


}
