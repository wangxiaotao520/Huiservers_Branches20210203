package com.huacheng.huiservers.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelEventHome;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.center.geren.adapter.MyFragmentPagerAdapter;
import com.huacheng.huiservers.ui.circle.CircleReleaseActivity;
import com.huacheng.huiservers.ui.circle.MyCircleActivity;
import com.huacheng.huiservers.ui.circle.bean.CircleDetailBean;
import com.huacheng.huiservers.ui.circle.bean.ModelRefreshCircle;
import com.huacheng.huiservers.ui.fragment.circle.CircleTabFragmentNew;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.utils.PopupWindowUtil;
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
    PopupWindow mPopupWindow;
    List<BannerBean> tabs = new ArrayList<>();
    private ArrayList<CircleTabFragmentNew> mFragmentList = new ArrayList<CircleTabFragmentNew>();
    private CircleTabFragmentNew currentFragment;
    private SharedPreferences preferencesLogin;
    private String login_type;
    private LinearLayout ly_add;

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
        ly_add = view.findViewById(R.id.ly_add);
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
        ly_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferencesLogin = getActivity().getSharedPreferences("login", 0);
                login_type = preferencesLogin.getString("login_type", "");

                if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    startActivity(new Intent(getActivity(), LoginVerifyCodeActivity.class));
                } else {
                    showPopupWindow(ly_add);
                }
            }
        });
    }

    private void showPopupWindow(final View anchorView){
        showDialog(smallDialog);
        final Url_info info = new Url_info(getActivity());
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().get(info.getSocialNum, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    CircleDetailBean info = (CircleDetailBean) JsonUtil.getInstance().parseJsonFromResponse(response, CircleDetailBean.class);
                    if (info != null) {
                        String num = info.getSocial_num();
                        View contentView;
                        if (!num.equals("0")) {
                            contentView = getPopupWindowContentView(num);
                        } else {
                            contentView = getPopupWindowContentView(num);
                        }
                        mPopupWindow = new PopupWindow(contentView,
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
                        //   mPopupWindow.setBackgroundDrawable(new ColorDrawable());
                        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
                        mPopupWindow.setFocusable(true);
                        ColorDrawable dw = new ColorDrawable(0xb0000000);
                        mPopupWindow.setBackgroundDrawable(dw);
                        //设置屏幕外阴影
                        //  popOutShadow(mPopupWindow);
                        // 设置好参数之后再show
                        int windowPos[] = PopupWindowUtil.calculatePopWindowPos(anchorView, contentView);
                        int xOff = 20; // 可以自己调整偏移
                        windowPos[0] -= xOff;
                        mPopupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
                        backgroundAlpha(0.4f);
                        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                backgroundAlpha(1f);
                            }
                        });
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
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(final float bgAlpha) {
        final Window window = (mActivity).getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        (mActivity).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bgAlpha == 1) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
                } else {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
                }
                window.setAttributes(lp);
            }
        });

    }
    private View getPopupWindowContentView(String num) {
        // 一个自定义的布局，作为显示的内容
        int layoutId = R.layout.popup_content_layout;   // 布局ID
        final View contentView = LayoutInflater.from(getActivity()).inflate(layoutId, null);
        final TextView item1 = (TextView) contentView.findViewById(R.id.menu_item1_num);
        if (!num.equals("0")) {
            item1.setText(num);
        } else {
            item1.setText("");
        }
        contentView.findViewById(R.id.lin_menu_item1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyCircleActivity.class));
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        });
        contentView.findViewById(R.id.menu_item2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), FaBuActivity.class));
                startActivity(new Intent(getActivity(), CircleReleaseActivity.class));
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        });
        return contentView;
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
