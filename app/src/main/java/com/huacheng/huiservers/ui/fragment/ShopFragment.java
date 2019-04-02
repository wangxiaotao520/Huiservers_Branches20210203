package com.huacheng.huiservers.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelAds;
import com.huacheng.huiservers.model.ModelShopNew;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.fragment.adapter.HomeCenterGirdAdapter;
import com.huacheng.huiservers.ui.fragment.bean.ModelShopIndex;
import com.huacheng.huiservers.ui.fragment.shop.FragmentShopCommon;
import com.huacheng.huiservers.ui.fragment.shop.adapter.ShopLimitAdapter;
import com.huacheng.huiservers.ui.fragment.shop.adapter.ShopMyGirdCateAdapter;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.shop.SearchShopActivity;
import com.huacheng.huiservers.ui.shop.ShopCartActivityTwo;
import com.huacheng.huiservers.ui.shop.ShopCateActivity;
import com.huacheng.huiservers.ui.shop.ShopXSTimeListActivity;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.ui.shop.bean.ShopDetailBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.utils.UIUtils;
import com.huacheng.huiservers.utils.XToast;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.huiservers.view.widget.FunctionAdvertise;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.widget.CustomScrollViewPager;
import com.lzy.widget.HeaderViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 商城首页(新)
 * created by wangxiaotao
 * 2018/12/21 0021 上午 11:15
 */
public class ShopFragment extends BaseFragment implements View.OnClickListener {
    String[] mTitle = new String[0];
    public SmartRefreshLayout refreshLayout;
    private HeaderViewPager scrollableLayout;
    private LinearLayout ly_top;
    private TabLayout mTabLayout;
    private CustomScrollViewPager mViewPager;
    private List<FragmentShopCommon> mFragmentList = new ArrayList();
    private FragmentShopCommon currentFragment;
    private boolean is_Refresh = false;
    private View headerView;
    private SharePrefrenceUtil prefrenceUtil;
    private FunctionAdvertise fc_ads;
    private LinearLayout lin_cate, lin_search, lin_car;
    private TextView txt_shop_num;

    private LinearLayout lin_shop_limit;
    private HorizontalScrollView horizontalSV;
    private GridView gridview_limit;
    private ShopLimitAdapter limitAdapter;
    private ModelShopNew info;
    private MyGridview my_grid_cate;
    private MyGridview grid_center_ad;
    private LinearLayout ll_shop_center;
    private TextView tv_discount_more;
    private SharedPreferences preferencesLogin;
    private LinearLayout ll_grid_cate;
    private float alpha;  //透明度 标志滑动到的位置状态
    private RelativeLayout rl_more_goods_title;
    View mStatusBar;
    @Override
    public void initView(View view) {
        ly_top = view.findViewById(R.id.ly_top);
        initTopView(view);
        headerView = view.findViewById(R.id.rl_head);
        initHeaderView();
        refreshLayout = view.findViewById(R.id.refreshLayout);
        // 一开始先不要让加载更多,防止网络错误时，加载更多报错
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        scrollableLayout = view.findViewById(R.id.scrollableLayout);
        //设置偏移量，只能在这里设置
        scrollableLayout.setTopOffset(DeviceUtils.dip2px(mContext, 48)+TDevice.getStatuBarHeight(mActivity));

        prefrenceUtil = new SharePrefrenceUtil(mActivity);

        mTabLayout = view.findViewById(R.id.tl_tab);
        mViewPager = view.findViewById(R.id.vp_pager);
        ll_grid_cate = view.findViewById(R.id.ll_grid_cate);
        ly_top.setAlpha((float)0.6);

        //设置statusbar
        mStatusBar=view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        mStatusBar.setAlpha((float)0.6);

    }

    private void initHeaderView() {
        // 初始化头部布局
        headerView.setVisibility(View.INVISIBLE);
    }

    //初始化tab布局
    private void initTabLayout() {
        //初始化title

        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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
        mViewPager.setOffscreenPageLimit(mTitle.length - 1);
        mTabLayout.setBackgroundColor(getResources().getColor(R.color.white));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(onPageChangeListener);

    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

                FragmentShopCommon fragmentCommon = (FragmentShopCommon) mFragmentList.get(position);
                currentFragment = fragmentCommon;
                scrollableLayout.setCurrentScrollableContainer(currentFragment);
                fragmentCommon.init(alpha);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void initTopView(View view) {
        // 初始化顶部布局
        lin_cate = view.findViewById(R.id.lin_cate);
        lin_search = view.findViewById(R.id.lin_search);
        lin_car = view.findViewById(R.id.lin_car);
        txt_shop_num = view.findViewById(R.id.txt_shop_num);

        fc_ads = view.findViewById(R.id.fc_ads);
        my_grid_cate = view.findViewById(R.id.my_grid_cate);
        lin_shop_limit = view.findViewById(R.id.lin_shop_limit);
        horizontalSV = view.findViewById(R.id.horizontalSV);
        gridview_limit = view.findViewById(R.id.gridview_limit);
        tv_discount_more = view.findViewById(R.id.tv_discount_more);
        grid_center_ad = view.findViewById(R.id.grid_ad);
        ll_shop_center = view.findViewById(R.id.ll_shop_center);

        //广告点击跳转
        fc_ads.setListener(new FunctionAdvertise.OnClickAdsListener() {
            @Override
            public void OnClickAds(ModelAds ads) {
                if (TextUtils.isEmpty(ads.getUrl())) {
                    if (ads.getUrl_type().equals("0") || TextUtils.isEmpty(ads.getUrl_type())) {
                        new Jump(mActivity, ads.getType_name(), ads.getAdv_inside_url());
                    } else {
                        new Jump(mActivity, ads.getUrl_type(), ads.getType_name(), "", ads.getUrl_type_cn());
                    }
                } else {//URL不为空时外链
                    new Jump(mActivity, ads.getUrl());

                }
            }
        });
        rl_more_goods_title = view.findViewById(R.id.rl_more_goods_title);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        scrollableLayout.setOnScrollListener(new HeaderViewPager.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                //让头部具有差速动画,如果不需要,可以不用设置
                //    ly_top.setTranslationY(currentY / 2);
                //动态改变标题栏的透明度,注意转化为浮点型
                alpha = 1.0f * currentY / maxY;
                refreshLayout.setEnableRefresh(alpha > 0 ? false : true);
         //       mViewPager.setIsCanScroll(alpha ==1?true:false);
                // 设置渐变到多少后不渐变
                if (alpha < 0.6f) {
                    ly_top.setAlpha((float) 0.6f);
                    mStatusBar.setAlpha(0.6f);
                } else {
                    ly_top.setAlpha(alpha);
                    mStatusBar.setAlpha(alpha);
                }

                //注意头部局的颜色也需要改变
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //请求完以后，刷新当前fragment
                is_Refresh = true;

                requestData();

            }
        });
        lin_cate.setOnClickListener(this);
        lin_search.setOnClickListener(this);
        lin_car.setOnClickListener(this);
        tv_discount_more.setOnClickListener(this);
    }


    /**
     * 填充数据
     *
     * @param info
     */
    private void inflateContent(ModelShopNew info) {
        List<ModelAds> adHead = info.getAd_hc_shopindex();
        if (adHead != null && adHead.size() > 0) {//头部广告

            fc_ads.getLayoutParams().width = ToolUtils.getScreenWidth(mActivity);
            Double d = Double.valueOf(ToolUtils.getScreenWidth(mActivity) / 2.5);
            fc_ads.getLayoutParams().height = (new Double(d)).intValue();

            fc_ads.initAds(adHead);
        }
        if (info.getCate_list() != null && info.getCate_list().size() > 0) {//商品分类
            ll_grid_cate.setVisibility(View.VISIBLE);
            ShopMyGirdCateAdapter myGirdCateAdapter = new ShopMyGirdCateAdapter(mActivity, info.getCate_list());
            my_grid_cate.setAdapter(myGirdCateAdapter);
        } else {
            ll_grid_cate.setVisibility(View.GONE);
        }

        ModelShopIndex discountList = info.getPro_discount_list();//限时抢购
        if (discountList != null) {
            List<ModelShopIndex> limits = discountList.getList();
            if (limits != null && limits.size() > 0) {
                lin_shop_limit.setVisibility(View.VISIBLE);

                horizontalSV.setVisibility(View.VISIBLE);
                setGridViewLimit(limits, limits.size());//
            } else {
                lin_shop_limit.setVisibility(View.GONE);
                horizontalSV.setVisibility(View.GONE);
            }

        } else {
            lin_shop_limit.setVisibility(View.GONE);
        }

        List<BannerBean> shopCenter = info.getAd_hc_shop_center();//每日必逛
        if (shopCenter != null && shopCenter.size() > 0) {
            ll_shop_center.setVisibility(View.VISIBLE);
            HomeCenterGirdAdapter mCenterAdapter = new HomeCenterGirdAdapter(mActivity, shopCenter);
            grid_center_ad.setAdapter(mCenterAdapter);
        } else {
            ll_shop_center.setVisibility(View.GONE);
        }


    }

    /**
     * 限时秒杀 GridView
     * @param mIndex
     * @param size_
     */
    private void setGridViewLimit(List<ModelShopIndex> mIndex, int size_) {
        int size = size_;
        int length = 145;
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridview_limit.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridview_limit.setColumnWidth(itemWidth); // 设置列表项宽
        gridview_limit.setHorizontalSpacing(5); // 设置列表项水平间距//5
        gridview_limit.setStretchMode(GridView.NO_STRETCH);
        gridview_limit.setNumColumns(size); // 设置列数量=列表集合数

        limitAdapter = new ShopLimitAdapter(mIndex, mActivity);
        gridview_limit.setAdapter(limitAdapter);
    }

    //请求数据
    private void requestData() {
        HashMap<String, String> mParams = new HashMap<>();
        mParams.put("c_id", prefrenceUtil.getXiaoQuId());
        MyOkHttp.get().post(ApiHttpClient.SHOP_INDEX, mParams, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                headerView.setVisibility(View.VISIBLE);
                scrollableLayout.setVisibility(View.VISIBLE);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    info = (ModelShopNew) JsonUtil.getInstance().parseJsonFromResponse(response, ModelShopNew.class);
                    if (info != null) {
                        inflateContent(info);
                        // 请求结束后
                        if (is_Refresh&&mTitle.length>0) {
                            //刷新
                            //表明切换的时候要刷新
                            for (int i = 0; i < mFragmentList.size(); i++) {
                                mFragmentList.get(i).setInit(false);
                            }
                            if (currentFragment != null) {
                                //当前fragment直接刷新
                                currentFragment.refreshIndeed();
                            }
                        } else {
                            // 第一次加载 根据服务器返回的数据填充title
                            List<ModelShopIndex> hot_cate_list = info.getHot_cate_list();
                            mTitle = new String[hot_cate_list.size()];
                            for (int i = 0; i < hot_cate_list.size(); i++) {
                                mTitle[i] = hot_cate_list.get(i).getCate_name() + "";
                            }

                            for (int i = 0; i < mTitle.length; i++) {
                                FragmentShopCommon fragmentShopCommon = new FragmentShopCommon();
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", i);
                                bundle.putString("c_id", prefrenceUtil.getXiaoQuId());
                                bundle.putString("cat_id", hot_cate_list.get(i).getId() + "");
                                fragmentShopCommon.setArguments(bundle);
                                mFragmentList.add(fragmentShopCommon);
                            }
                            // 根据服务器返回的数据给第一页列表填充数据
                            if (hot_cate_list.size() > 0 && hot_cate_list.get(0) != null) {
                                mFragmentList.get(0).setFirstFragmentData(hot_cate_list.get(0).getList());
                            }

                            if (mFragmentList.size() > 0) {
                                currentFragment = mFragmentList.get(0);
                                scrollableLayout.setCurrentScrollableContainer(currentFragment);
                                rl_more_goods_title.setVisibility(View.VISIBLE);
                                scrollableLayout.setCan_scroll(true);
                                initTabLayout();
                            }else {
                                //todo 没数据的情况
                                rl_more_goods_title.setVisibility(View.GONE);
                                //设置不可滑动
                                scrollableLayout.setCan_scroll(false);
                            }
                        }

                    }
                    if (getLinshi()) {// 登陆之后获取数量
                        getCartNum();
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "");
                    XToast.makeText(mContext, msg, XToast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                if (scrollableLayout!=null&&mTitle.length==0){
                    scrollableLayout.setVisibility(View.INVISIBLE);
                }
                refreshLayout.finishRefresh();
                XToast.makeText(mContext, "网络异常，请检查网络设置", XToast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //请求数据

        showDialog(smallDialog);
        requestData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop_new;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lin_cate://分类
                intent.setClass(mActivity, ShopCateActivity.class);
                startActivity(intent);
                break;
            case R.id.lin_car://购物车
                if (getLinshi()) {
                    intent.setClass(mActivity, ShopCartActivityTwo.class);
                    startActivity(intent);
                } else {
                    intent.setClass(mActivity, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.lin_search://搜索
                intent.setClass(mActivity, SearchShopActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_discount_more://限时抢购
                intent.setClass(mActivity, ShopXSTimeListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cateID", "1");
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }
    }


    /**
     * 判断是否登录了
     */
    private boolean getLinshi() {
        preferencesLogin = mActivity.getSharedPreferences("login", 0);
        String login_type = preferencesLogin.getString("login_type", "");
        if (login_type.equals("") || ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 购物车商品数量
     */
    private void getCartNum() {
        Url_info info = new Url_info(getActivity());
        RequestParams params = new RequestParams();
        params.addBodyParameter("c_id", prefrenceUtil.getXiaoQuId());
        new HttpHelper(info.cart_num, params, mContext) {

            @Override
            protected void setData(String json) {
                ShopDetailBean cartnum = new ShopProtocol().getCartNum(json);
                if (cartnum != null) {
                    if (("0").equals(cartnum.getCart_num())) {
                        txt_shop_num.setVisibility(View.GONE);
                    } else {
                        txt_shop_num.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                UIUtils.showToastSafe("网络异常，请检查网络设置");
            }
        };

    }

    @Override
    public void onPause() {
        super.onPause();
        fc_ads.stopAutoCycle();//头图广告，循环滚动停止
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!fc_ads.isCycling()) {
            fc_ads.startCycle();//头图广告，循环滚动开始
        }
    }

}
