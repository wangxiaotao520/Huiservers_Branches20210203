package com.huacheng.huiservers.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelEventHome;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.center.geren.adapter.MyFragmentPagerAdapter;
import com.huacheng.huiservers.ui.circle.bean.ModelRefreshCircle;
import com.huacheng.huiservers.ui.fragment.circle.CircleTabFragmentNew;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：圈子
 * 时间：2019/12/17 18:08
 * created by DFF
 */
public class CircleFragmentNew extends BaseFragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private RelativeLayout rel_editText;

    List<BannerBean> tabs = new ArrayList<>();
    private ArrayList<CircleTabFragmentNew> mFragmentList = new ArrayList<CircleTabFragmentNew>();
    private CircleTabFragmentNew currentFragment;
    private SharedPreferences preferencesLogin;
    private String login_type;

    MyFragmentPagerAdapter pager;

    View mStatusBar;
    private int type_position = 0;//首页哪个点击被选中  //默认是0

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            type_position = getArguments().getInt("type_position");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(View view) {
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tablayout);
        rel_editText = view.findViewById(R.id.rel_editText);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //状态栏
        mStatusBar = view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        mStatusBar.setAlpha((float) 1);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    /**
     * 邻里分类
     *
     * @param savedInstanceState
     */
    @Override
    public void initData(Bundle savedInstanceState) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("sign", "0");
        MyOkHttp.get().get(ApiHttpClient.GET_SOCIAL_CAT, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<BannerBean> modelCatTabs = (List<BannerBean>) JsonUtil.getInstance().getDataArrayByName(response, "data", BannerBean.class);
                    if (modelCatTabs != null && modelCatTabs.size() > 0) {
                        tabs.clear();
                        tabs.addAll(modelCatTabs);
                        setTabLayout(tabs);
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
    public int getLayoutId() {
        return R.layout.fragment_circle;
    }

    /**
     * 处理内容
     *
     * @param tabs
     */
    private void setTabLayout(final List<BannerBean> tabs) {
        for (int i = 0; i < tabs.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabs.get(i).getC_name()));
        }
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        updateTabTextView(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()), true);
        //这里注意的是，因为我是在fragment中创建MyFragmentStatePagerAdapter，所以要传getChildFragmentManager()
        for (int i = 0; i < tabs.size(); i++) {
            CircleTabFragmentNew tabFragment = new CircleTabFragmentNew();
            Bundle bundle = new Bundle();
            bundle.putString("mCid", tabs.get(i).getId());
            bundle.putInt("mPro", tabs.get(i).getIs_pro());//是否是物业公告字段
            bundle.putString("type", i + "");
            bundle.putInt("type_position", type_position);
            tabFragment.setArguments(bundle);
            mFragmentList.add(tabFragment);
        }
        currentFragment = mFragmentList.get(0);
        pager = new MyFragmentPagerAdapter(getChildFragmentManager(), tabs, mFragmentList);
        viewPager.setAdapter(pager);
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(tabs.size()); //0 设置缓存页面，当前页面的相邻N各页面都会被缓存

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //在选中的顶部标签时，为viewpager设置currentitem
                viewPager.setCurrentItem(tab.getPosition());
                updateTabTextView(tab, true);
                currentFragment = mFragmentList.get(tab.getPosition());
                if (currentFragment != null) {
                    currentFragment.selected_init();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabTextView(tab, false);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setCurrentItem(type_position);
        currentFragment = mFragmentList.get(type_position);
    }

    /**
     * 引用tab item
     *
     * @param currentPosition
     * @return
     */
    private View getTabView(int currentPosition) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.circle_tab_item, null);
        TextView textView = view.findViewById(R.id.tab_item_textview);
        textView.setText(tabs.get(currentPosition).getC_name());
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
            TextView tabSelect = tab.getCustomView().findViewById(R.id.tab_item_textview);
            View selectTab = tab.getCustomView().findViewById(R.id.view);
            tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tabSelect.setTextColor(getResources().getColor(R.color.title_color));
            selectTab.setBackgroundColor(mContext.getResources().getColor(R.color.orange_bg));
            selectTab.setVisibility(View.VISIBLE);
            //tabSelect.setTextSize(20);
            tabSelect.setText(tab.getText());
        } else {
            TextView tabUnSelect = tab.getCustomView().findViewById(R.id.tab_item_textview);
            View selectTab = tab.getCustomView().findViewById(R.id.view);
            tabUnSelect.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tabUnSelect.setTextColor(getResources().getColor(R.color.text_color));
            selectTab.setVisibility(View.INVISIBLE);
            // tabUnSelect.setTextSize(16);
            tabUnSelect.setText(tab.getText());
        }
    }

    /**
     * 定位到指定tab并刷新
     *
     * @param modelRefreshCircle
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshCircle(ModelRefreshCircle modelRefreshCircle) {
        if (modelRefreshCircle != null) {
            String tab_id = modelRefreshCircle.getTab_id();
            int position = 0;
            if (tabs != null) {
                for (int i = 0; i < tabs.size(); i++) {
                    if (tabs.get(i).getId().equals(tab_id)) {
                        position = i;
                    }
                }
                if (mFragmentList.size() > 0 && position < mFragmentList.size()) {
                    CircleTabFragmentNew tabFragment = mFragmentList.get(position);
                    if (currentFragment == tabFragment) {//是当前fragment直接刷新
                        currentFragment.isInit = false;
                        currentFragment.selected_init();
                    } else {
                        tabFragment.isInit = false;//其他的切换即可
                        viewPager.setCurrentItem(position);
                    }

                }

            }
        }
    }

    //首页点击跳转
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventbusSwitch(ModelEventHome model) {
        if (model != null) {
            //跳转到对应页面 这里针对的是已经加载过邻里的情况
            if (model.getType() < mFragmentList.size()) {
                CircleTabFragmentNew tabFragment = mFragmentList.get(model.getType());
                if (currentFragment == tabFragment) {//是当前fragment直接刷新
                    currentFragment.isInit = false;
                    currentFragment.selected_init();
                } else {
                    tabFragment.isInit = false;//其他的切换即可
                    viewPager.setCurrentItem(model.getType());
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
