package com.huacheng.huiservers.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelAds;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.model.ModelShopNew;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.fragment.adapter.HomeCenterGirdAdapter;
import com.huacheng.huiservers.ui.fragment.shop.FragmentShopCommon;
import com.huacheng.huiservers.ui.fragment.shop.adapter.ShopLimitAdapter;
import com.huacheng.huiservers.ui.fragment.shop.adapter.ShopMyGirdCateAdapter;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.shop.SearchShopActivity;
import com.huacheng.huiservers.ui.shop.ShopCartActivityTwo;
import com.huacheng.huiservers.ui.shop.ShopCateActivity;
import com.huacheng.huiservers.ui.shop.ShopDetailActivity;
import com.huacheng.huiservers.ui.shop.ShopXSTimeListActivity;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.ui.shop.bean.ShopDetailBean;
import com.huacheng.huiservers.utils.MyCornerImageLoader;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.timer.CountDownTimer;
import com.huacheng.libraryservice.widget.CustomScrollViewPager;
import com.lzy.widget.HeaderViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Description: 商城首页(新)
 * created by wangxiaotao
 * 2018/12/21 0021 上午 11:15
 */
public class ShopFragment extends BaseFragment implements View.OnClickListener, AMapLocationListener {
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
   // private FunctionAdvertise fc_ads;
    private LinearLayout lin_cate, lin_search, lin_car;
    private TextView txt_shop_num;

    private LinearLayout lin_shop_limit;
    private HorizontalScrollView horizontalSV;
    //private GridView gridview_limit;
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
    private View view_alpha;
    private Banner banner;
    private MyCornerImageLoader myImageLoader;
    private List<ModelAds> adHead;
    private LinearLayout ll_limit_container;
    private SparseArray<CountDownTimer> countDownCounters =new SparseArray<>();

    private RxPermissions rxPermissions;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private boolean isInitLocaion = false;

    @Override
    public void initView(View view) {
        rxPermissions= new RxPermissions(mActivity);
        ly_top = view.findViewById(R.id.ly_top);
        view_alpha = view.findViewById(R.id.view_alpha);
        initTopView(view);
        headerView = view.findViewById(R.id.rl_head);
        initHeaderView();
        refreshLayout = view.findViewById(R.id.refreshLayout);
        // 一开始先不要让加载更多,防止网络错误时，加载更多报错
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        scrollableLayout = view.findViewById(R.id.scrollableLayout);
        //设置偏移量，只能在这里设置
       // scrollableLayout.setTopOffset(DeviceUtils.dip2px(mContext, 48)+TDevice.getStatuBarHeight(mActivity));
        scrollableLayout.setTopOffset(0);
        prefrenceUtil = new SharePrefrenceUtil(mActivity);

        mTabLayout = view.findViewById(R.id.tl_tab);
        mViewPager = view.findViewById(R.id.vp_pager);
        ll_grid_cate = view.findViewById(R.id.ll_grid_cate);
        view_alpha.setAlpha(1);

        //设置statusbar
        mStatusBar=view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        mStatusBar.setAlpha((float)1);

    }
    private void setBanner() {
        myImageLoader= new MyCornerImageLoader();
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(myImageLoader);
        banner.isAutoPlay(true);//设置是否轮播
        banner.setIndicatorGravity(BannerConfig.CENTER);//小圆点位置
        banner.setDelayTime(4000);
        banner.setImageLoader(myImageLoader).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (adHead!=null&&adHead.size() > 0) {
                    ModelAds ads=adHead.get(position);
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
            }
        }).start();

        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


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

      //  fc_ads = view.findViewById(R.id.fc_ads);
        banner = view.findViewById(R.id.banner);
        setBanner();
        my_grid_cate = view.findViewById(R.id.my_grid_cate);
        lin_shop_limit = view.findViewById(R.id.lin_shop_limit);
        horizontalSV = view.findViewById(R.id.horizontalSV);
      //  gridview_limit = view.findViewById(R.id.gridview_limit);
        ll_limit_container = view.findViewById(R.id.ll_limit_container);
        tv_discount_more = view.findViewById(R.id.tv_discount_more);
        grid_center_ad = view.findViewById(R.id.grid_ad);
        ll_shop_center = view.findViewById(R.id.ll_shop_center);

        //广告点击跳转
//        fc_ads.setListener(new FunctionAdvertise.OnClickAdsListener() {
//            @Override
//            public void OnClickAds(ModelAds ads) {
//                if (TextUtils.isEmpty(ads.getUrl())) {
//                    if (ads.getUrl_type().equals("0") || TextUtils.isEmpty(ads.getUrl_type())) {
//                        new Jump(mActivity, ads.getType_name(), ads.getAdv_inside_url());
//                    } else {
//                        new Jump(mActivity, ads.getUrl_type(), ads.getType_name(), "", ads.getUrl_type_cn());
//                    }
//                } else {//URL不为空时外链
//                    new Jump(mActivity, ads.getUrl());
//
//                }
//            }
//        });
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
//                if (alpha < 0.6f) {
//                    view_alpha.setAlpha((float) 0.6f);
//                    mStatusBar.setAlpha(0.6f);
//                } else {
//                    view_alpha.setAlpha(alpha);
//                    mStatusBar.setAlpha(alpha);
//                }

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
        adHead = info.getAd_hc_shopindex();
        if (adHead != null && adHead.size() > 0) {//头部广告
//
//            fc_ads.getLayoutParams().width = ToolUtils.getScreenWidth(mActivity);
//            Double d = Double.valueOf(ToolUtils.getScreenWidth(mActivity) / 2.5);
//            fc_ads.getLayoutParams().height = (new Double(d)).intValue();
//
//            fc_ads.initAds(adHead);

            ArrayList<String> mDatas_img1 = new ArrayList<>();
            for (int i = 0; i < info.getAd_hc_shopindex().size(); i++) {
                mDatas_img1.add(ApiHttpClient.IMG_URL+info.getAd_hc_shopindex().get(i).getImg() + "");
            }
            banner.update(mDatas_img1);
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
               setGridViewLimit(limits, limits.size());//设置限时抢购

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
//        int size = size_;
//        int length = 145;
//        DisplayMetrics dm = new DisplayMetrics();
//        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        float density = dm.density;
//        int gridviewWidth = (int) (size * (length + 4) * density);
//        int itemWidth = (int) (length * density);
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
//        gridview_limit.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
//        gridview_limit.setColumnWidth(itemWidth); // 设置列表项宽
//        gridview_limit.setHorizontalSpacing(5); // 设置列表项水平间距//5
//        gridview_limit.setStretchMode(GridView.NO_STRETCH);
//        gridview_limit.setNumColumns(size); // 设置列数量=列表集合数
//
//        limitAdapter = new ShopLimitAdapter(mIndex, mActivity);
//        gridview_limit.setAdapter(limitAdapter);
        ll_limit_container.removeAllViews();
        for (int i = 0; i < mIndex.size(); i++) {
            View  v = LayoutInflater.from(mContext).inflate(R.layout.commodity_limit_item, null);
            ImageView iv_item_image = (ImageView) v.findViewById(R.id.iv_item_image);
            ImageView  iv_shop_list_flag = (ImageView) v.findViewById(R.id.iv_shop_list_flag);

            final TextView txt_time_type = (TextView) v.findViewById(R.id.txt_time_type);
            final TextView tv_limit_day = (TextView) v.findViewById(R.id.tv_limit_day);
            final TextView tv_limit_hour = (TextView) v.findViewById(R.id.tv_limit_hour);
            final TextView tv_limit_minute = (TextView) v.findViewById(R.id.tv_limit_minute);
            final TextView tv_limit_second = (TextView) v.findViewById(R.id.tv_limit_second);

            TextView tv_item_name = (TextView) v.findViewById(R.id.tv_item_name);
            TextView  txt_price_limit = (TextView) v.findViewById(R.id.txt_price_limit);
            TextView txt_shop_unit = (TextView) v.findViewById(R.id.txt_shop_unit);
            TextView txt_price_original = (TextView) v.findViewById(R.id.txt_price_original);
            RelativeLayout linear = v.findViewById(R.id.linear);

            final ModelShopIndex shopIndex = mIndex.get(i);
            String discount = shopIndex.getDiscount();
            if ("1".equals(discount)) {
//
                iv_shop_list_flag.setBackgroundResource(R.drawable.ic_shoplist_spike);
            } else {
                if ("1".equals(shopIndex.getIs_hot())) {
//
                    iv_shop_list_flag.setBackgroundResource(R.drawable.ic_shoplist_hotsell);
                } else if ("1".equals(shopIndex.getIs_new())) {
                   iv_shop_list_flag.setBackgroundResource(R.drawable.ic_shoplist_newest);
                } else {
//
                }
            }
            //  gettime(holder, shopIndex);

            String distanceEnd = shopIndex.getDistance_end();
            if (!NullUtil.isStringEmpty(distanceEnd)) {
                long distance_int = 0;
                String distance_str = "";
                distance_str = "距结束";
                distance_int = Long.parseLong(distanceEnd) * 1000;

                txt_time_type.setText(distance_str);
                if ("1".equals(shopIndex.getDiscount())) {
//                    long timer = 0;
//                    if (shopIndex.getCurrent_times() == 0) {
//                        timer = distance_int;
//                        shopIndex.setCurrent_times(System.currentTimeMillis());
//                    } else {
//                        timer = distance_int - (System.currentTimeMillis() - shopIndex.getCurrent_times());
//                    }
                    CountDownTimer countDownTimer=null;
                    if (countDownCounters.size()==mIndex.size()) {
                        cancelAllTimers();
                        countDownCounters.clear();
                    }
                        long timer = distance_int;
                        //expirationTime 与系统时间做比较，timer 小于零，则此时倒计时已经结束。
                        if (timer > 0) {
                            countDownTimer = new CountDownTimer(timer, 1000) {
                                public void onTick(long millisUntilFinished) {
                                    String[] times = SetTime(millisUntilFinished);
                                    tv_limit_day.setText(fillZero(times[0]));
                                    tv_limit_hour.setText(fillZero(times[1]));
                                    tv_limit_minute.setText(fillZero(times[2]));
                                    tv_limit_second.setText(fillZero(times[3]));
                                }

                                public void onFinish(String redpackage_id) {
                                    //结束了该轮倒计时
                                    txt_time_type.setText("已结束");
                                    tv_limit_day.setText("00");
                                    tv_limit_hour.setText("00");
                                    tv_limit_minute.setText("00");
                                    tv_limit_second.setText("00");

                                }
                            }.start();
                            //将此 countDownTimer 放入list.
                            countDownCounters.put(i, countDownTimer);
                        } else {
                            //结束
                            txt_time_type.setText("已结束");
                            tv_limit_day.setText("00");
                            tv_limit_hour.setText("00");
                            tv_limit_minute.setText("00");
                            tv_limit_second.setText("00");
                        }

                }
            }


            GlideUtils.getInstance().glideLoad(mContext,MyCookieStore.URL + shopIndex.getTitle_img(),iv_item_image,R.color.windowbackground);
            tv_item_name.setText(shopIndex.getTitle());
            txt_price_limit.setText("¥" + shopIndex.getPrice());
            String unit = shopIndex.getUnit();
            if (!NullUtil.isStringEmpty(unit)) {
                txt_shop_unit.setText("/" + shopIndex.getUnit());
            } else {
                txt_shop_unit.setText("");
            }
            txt_price_original.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            txt_price_original.setText("¥" + shopIndex.getOriginal());

            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ShopDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("shop_id", shopIndex.getId());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0,0, DeviceUtils.dip2px(mActivity,5),0);
            ll_limit_container.addView(v,params);

        }

    }
    private String[] SetTime(long time) {
       long mDay = time / (1000 * 60 * 60 * 24);
        long  mHour = (time - mDay * (1000 * 60 * 60 * 24))
                / (1000 * 60 * 60);
        long  mMin = (time - mDay * (1000 * 60 * 60 * 24) - mHour
                * (1000 * 60 * 60))
                / (1000 * 60);
        long   mSecond = (time - mDay * (1000 * 60 * 60 * 24) - mHour
                * (1000 * 60 * 60) - mMin * (1000 * 60))
                / 1000;
        String[] str = new String[4];
        str[0] = mDay + "";
        str[1] = mHour + "";
        str[2] = mMin + "";
        str[3] = mSecond + "";
        return str;
    }

    private String fillZero(String time) {
        String timeStr = "";
        if (time.length() == 1)
            return "0" + time;
        else
            return time;
    }
    //请求数据
    private void requestData() {
        HashMap<String, String> mParams = new HashMap<>();
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())){
            mParams.put("c_id", prefrenceUtil.getXiaoQuId());
        }
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())){
            mParams.put("province_cn", prefrenceUtil.getProvince_cn());
            mParams.put("city_cn", prefrenceUtil.getCity_cn());
            mParams.put("region_cn", prefrenceUtil.getRegion_cn());
        }
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
                                // 没数据的情况
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
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                if (scrollableLayout!=null&&mTitle.length==0){
                    scrollableLayout.setVisibility(View.INVISIBLE);
                }
                refreshLayout.finishRefresh();
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }
    private void initLocation() {
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(mActivity);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置需要地理位置信息
            mLocationOption.isNeedAddress();
            //     mLocationOption.setOnceLocation(true);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mLocationOption.setOnceLocation(true);

        }

    }
    @Override
    public void initData(Bundle savedInstanceState) {

        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())){
            //1.有省市区数据的情况（新用户下载安装第一次进来，选择切换过小区，）
            showDialog(smallDialog);
            requestData();
        }else {
            //2.没有省市区的情况（旧用户覆盖安装，选择了智慧小区）
            initLocation();
            rxPermissions.request( Manifest.permission.ACCESS_COARSE_LOCATION)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean isGranted) throws Exception {
                            if (isGranted) {
                                //权限同意 ,开始定位
                                showDialog(smallDialog);
                                smallDialog.setTipTextView("定位中...");
                                mlocationClient.startLocation();
                            } else {
                                //权限拒绝
                                showDialog(smallDialog);
                                requestData();

                            }
                        }
                    });
        }

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
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };

    }

    @Override
    public void onPause() {
        super.onPause();
        //结束轮播
        if (banner!=null){
            banner.stopAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner!=null){
            //开始轮播
            banner.startAutoPlay();
        }

    }
    /**
     * 清空当前 CountTimeDown 资源
     */
    public void cancelAllTimers() {
        if (countDownCounters == null) {
            return;
        }
        Log.e("TAG", "size :  " + countDownCounters.size());
        for (int i = 0, length = countDownCounters.size(); i < length; i++) {
            CountDownTimer cdt = countDownCounters.get(countDownCounters.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
        for (int i = 0, length = countDownCounters.size(); i < length; i++) {
            CountDownTimer cdt = countDownCounters.get(countDownCounters.keyAt(i));
            cdt=null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelAllTimers();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mLocationOption = null;
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (null != location) {
            StringBuffer sb = new StringBuffer();
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.getErrorCode() == 0) {
                sb.append("定位成功" + "\n");
                sb.append("定位类型: " + location.getLocationType() + "\n");
                sb.append("经    度    : " + location.getLongitude() + "\n");
                sb.append("纬    度    : " + location.getLatitude() + "\n");
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                sb.append("提供者    : " + location.getProvider() + "\n");

                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : " + location.getSatellites() + "\n");
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("城市编码 : " + location.getCityCode() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("区域 码   : " + location.getAdCode() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
                sb.append("兴趣点    : " + location.getPoiName() + "\n");

                /*sharePrefrenceUtil.setLongitude(location.getLongitude() + "");
                sharePrefrenceUtil.setAtitude(location.getLatitude() + "");*/

                //定位完成的时间
                //  sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                if (!isInitLocaion) {
                    if (NullUtil.isStringEmpty(location.getDistrict())){
//                        hideDialog(smallDialog);
//                        // tvResult.setText("定位失败，loc is null");
//                        text_city.setText("定位失败...点击重新定位");
//                        text_city.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startLocation();
//                            }
//                        });
                    }else {
                        isInitLocaion = true;
                        //默认选中
                        mlocationClient.stopLocation();
                        String  location_provice = location.getProvince() + "";
                        String  location_city = location.getCity() + "";
                        String  location_district = location.getDistrict() + "";
                        prefrenceUtil.setProvince_cn(location_provice);
                        prefrenceUtil.setCity_cn(location_city);
                        prefrenceUtil.setRegion_cn(location_district);
                        showDialog(smallDialog);
                        smallDialog.setTipTextView("加载中...");
                        requestData();
                    }
                }
            } else {
                //定位失败
                sb.append("定位失败" + "\n");
                sb.append("错误码:" + location.getErrorCode() + "\n");
                sb.append("错误信息:" + location.getErrorInfo() + "\n");
                sb.append("错误描述:" + location.getLocationDetail() + "\n");
                hideDialog(smallDialog);
                // tvResult.setText("定位失败，loc is null");
                showDialog(smallDialog);

            }

        } else {
            hideDialog(smallDialog);
            // tvResult.setText("定位失败，loc is null");
            showDialog(smallDialog);
            requestData();
        }
    }
}
