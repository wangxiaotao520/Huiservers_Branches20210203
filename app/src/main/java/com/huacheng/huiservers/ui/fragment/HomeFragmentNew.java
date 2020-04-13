package com.huacheng.huiservers.ui.fragment;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.huacheng.huiservers.CommunityListActivity;
import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.CircleDetailBean;
import com.huacheng.huiservers.model.ModelAds;
import com.huacheng.huiservers.model.ModelCoummnityList;
import com.huacheng.huiservers.model.ModelEventHome;
import com.huacheng.huiservers.model.ModelHome;
import com.huacheng.huiservers.model.ModelHomeCircle;
import com.huacheng.huiservers.model.ModelHomeIndex;
import com.huacheng.huiservers.model.ModelIndex;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.model.ModelVBaner;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.circle.CircleDetailsActivity;
import com.huacheng.huiservers.ui.fragment.adapter.HomeGridViewCateAdapter;
import com.huacheng.huiservers.ui.fragment.adapter.HomeIndexGoodsCommonAdapter;
import com.huacheng.huiservers.ui.fragment.adapter.VBannerAdapter;
import com.huacheng.huiservers.ui.fragment.indexcat.HouseHandBookActivity;
import com.huacheng.huiservers.ui.index.houserent.HouseRentListActivity;
import com.huacheng.huiservers.ui.index.houserent.RentSellCommissionActivity;
import com.huacheng.huiservers.ui.index.oldservice.OldMessageActivity;
import com.huacheng.huiservers.ui.index.property.HouseListActivity;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.scan.CustomCaptureActivity;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.huiservers.ui.shop.ShopSecKillListActivity;
import com.huacheng.huiservers.ui.shop.ShopZCListActivity;
import com.huacheng.huiservers.ui.shop.ShopZQListActivity;
import com.huacheng.huiservers.utils.CommonMethod;
import com.huacheng.huiservers.utils.LoginUtils;
import com.huacheng.huiservers.utils.MyCornerImageLoader;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.widget.GridViewNoScroll;
import com.huacheng.libraryservice.widget.verticalbannerview.VerticalBannerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.OnDoubleClickListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Description: 5.0.0新版本首页
 * created by wangxiaotao
 * 2019/10/16 0016 上午 10:12
 */
public class HomeFragmentNew extends BaseFragment implements HomeGridViewCateAdapter.OnClickItemListener, AMapLocationListener, PoiSearch.OnPoiSearchListener, HomeIndexGoodsCommonAdapter.OnClickCallback, View.OnClickListener {
    SharePrefrenceUtil prefrenceUtil;
    private ImageView iv_bg_title;//顶部背景图
    private View mStatusBar;

    private TextView tv_xiaoqu;
    private PagingListView listView;

    private ImageView iv_message;
    private ImageView iv_red;
    private ImageView iv_scancode;
    private View view_title_line;
    private SmartRefreshLayout refreshLayout;

    private Banner banner;
    private ImageView iv_bg_banner;//banner背景图
    private GridViewNoScroll gridview_home;
    private MyCornerImageLoader myImageLoader;


    List<Integer> colors = new ArrayList<>();//banner颜色
    //头布局
    private View headerView;
    private int current_banner_position = 0;
    private float alpha;//用来计算滑动
    private HomeGridViewCateAdapter homeGridViewCateAdapter;
    private List<ModelHomeIndex> mcatelist = new ArrayList<>();
    private List<ModelVBaner> mDatas_v_banner = new ArrayList<>();//首页垂直banner数据公告
    private ImageView iv_bg_grid;//grid 背景图
    private ImageView iv_center;
    private LinearLayout ll_center;
    private LinearLayout ly_notice;
    private VerticalBannerView v_banner;
    private VBannerAdapter vBannerAdapter;
    private LinearLayout ll_zixun_container;

    private FrameLayout fl_grid_container;
    private LinearLayout ll_on_sale_container;
    private LinearLayout ll_on_sale_img_root;
    private LinearLayout ll_nearby_food_container;
    private LinearLayout ll_nearby_food_img_root;
    private LinearLayout ll_sec_kill_container;
    private LinearLayout ll_sec_kill_container_root;
    private LinearLayout ll_zixun_container_root;
    HomeIndexGoodsCommonAdapter adapter;
    private List<ModelShopIndex> mDatas = new ArrayList<>();//数据

    private RxPermissions rxPermissions;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private boolean isInitLocaion = false;

    private String location_provice = "", location_district = "", location_city = "";//用户第一次使用时定位
    private ImageView iv_rent;
    private ImageView iv_sell;
    private ImageView iv_release_rent_sell;
    private int current_Color = Color.WHITE;//滑动时的color
    private ImageView iv_title_arrow;
    private ModelHome modelHome;
    private TextView tv_sec_kill_more;
    private ImageView tv_more_sec_kill_arrow;
    private TextView tv_more_sale;
    private ImageView tv_more_sale_arrow;
    List<ModelIndex> mDatas_Article=new ArrayList<>();
    private Banner banner_middle;

    @Override
    public void initView(View view) {
        rxPermissions = new RxPermissions(mActivity);
        prefrenceUtil = new SharePrefrenceUtil(mActivity);
        iv_bg_title = view.findViewById(R.id.iv_bg_title);
        mStatusBar = view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        tv_xiaoqu = view.findViewById(R.id.tv_xiaoqu);
        tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName());
        iv_title_arrow = view.findViewById(R.id.iv_title_arrow);
        iv_message = view.findViewById(R.id.iv_message);
        iv_red = view.findViewById(R.id.iv_red);
        iv_scancode = view.findViewById(R.id.iv_scancode);
        view_title_line = view.findViewById(R.id.view_title_line);
        view_title_line.setVisibility(View.VISIBLE);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        //    recyclerView=view.findViewById(R.id.recyclerview);
        listView = view.findViewById(R.id.listView);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(true);

        initHeaderView();
        adapter = new HomeIndexGoodsCommonAdapter(mContext, mDatas, this);
        listView.setAdapter(adapter);
        listView.setHasMoreItems(false);
        initLocation();


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

    /**
     * 头部的View
     */
    private void initHeaderView() {
        headerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_fragmenthome_header1, null);
        //banner
        banner = headerView.findViewById(R.id.banner);
        setBanner();
        iv_bg_banner = headerView.findViewById(R.id.iv_bg_banner);
        //grid
        fl_grid_container = headerView.findViewById(R.id.fl_grid_container);
        gridview_home = headerView.findViewById(R.id.gridview_home);
        iv_bg_grid = headerView.findViewById(R.id.iv_bg_grid);
        //中部图片
        iv_center = headerView.findViewById(R.id.iv_center);
        ll_center = headerView.findViewById(R.id.ll_center);
        //通知公告
        ly_notice = headerView.findViewById(R.id.ly_notice);
        v_banner = headerView.findViewById(R.id.v_banner);
        //特卖
        ll_on_sale_container = headerView.findViewById(R.id.ll_on_sale_container);
        tv_more_sale = headerView.findViewById(R.id.tv_more_sale);
        tv_more_sale_arrow = headerView.findViewById(R.id.tv_more_sale_arrow);
        ll_on_sale_img_root = headerView.findViewById(R.id.ll_on_sale_img_root);

        //慧秒杀
        ll_sec_kill_container = headerView.findViewById(R.id.ll_sec_kill_container);
        tv_sec_kill_more = headerView.findViewById(R.id.tv_sec_kill_more);
        tv_more_sec_kill_arrow = headerView.findViewById(R.id.tv_more_sec_kill_arrow);
        ll_sec_kill_container_root = headerView.findViewById(R.id.ll_sec_kill_container_root);
        //附近美食
        ll_nearby_food_container = headerView.findViewById(R.id.ll_nearby_food_container);
        TextView tv_more_nearby_food = headerView.findViewById(R.id.tv_more_nearby_food);
        ImageView tv_more_nearby_food_arrow = headerView.findViewById(R.id.tv_more_nearby_food_arrow);
        ll_nearby_food_img_root = headerView.findViewById(R.id.ll_nearby_food_img_root);
        //租售房
        iv_rent = headerView.findViewById(R.id.iv_rent);
        int height_rent = (int) ((DeviceUtils.getWindowWidth(mActivity) - DeviceUtils.dip2px(mActivity, 30)) * 308 * 1f / (501 * 2));
        iv_rent.setLayoutParams(new LinearLayout.LayoutParams((int) ((DeviceUtils.getWindowWidth(mActivity) - DeviceUtils.dip2px(mActivity, 30)) * 1f / 2), height_rent));
        iv_sell = headerView.findViewById(R.id.iv_sell);
        iv_sell.setLayoutParams(new LinearLayout.LayoutParams((int) ((DeviceUtils.getWindowWidth(mActivity) - DeviceUtils.dip2px(mActivity, 30)) * 1f / 2), height_rent));
        iv_release_rent_sell = headerView.findViewById(R.id.iv_release_rent_sell);
        int height_release = (int) ((DeviceUtils.getWindowWidth(mActivity) - DeviceUtils.dip2px(mActivity, 30)) * 307 * 1f / 1010);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height_release);
        iv_release_rent_sell.setLayoutParams(params);
        //资讯
        ll_zixun_container = headerView.findViewById(R.id.ll_zixun_container);
        ll_zixun_container_root = headerView.findViewById(R.id.ll_zixun_container_root);
        //中部广告
        banner_middle = headerView.findViewById(R.id.banner_middle);
        setBannerMiddle();
        headerView.setVisibility(View.INVISIBLE);
        listView.addHeaderView(headerView);

    }

    /**
     * 中部banner(目前只有问卷调查)
     */
    private void setBannerMiddle() {
        if (myImageLoader==null){
            myImageLoader=new MyCornerImageLoader();
        }
        banner_middle.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner_middle.setImageLoader(myImageLoader);
        banner_middle.isAutoPlay(true);//设置是否轮播
        banner_middle.setIndicatorGravity(BannerConfig.CENTER);//小圆点位置
        banner_middle.setDelayTime(4000);
        banner_middle.setImageLoader(myImageLoader).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                // 点击banner
                if (modelHome != null && modelHome.getQi_plan_list() != null && modelHome.getQi_plan_list().size() > 0) {
                    ModelAds ads = modelHome.getQi_plan_list().get(position);
                    //调查问卷
                    String id = ""+ads.getId();//计划id

                    if (!LoginUtils.hasLoginUser()) {
                        Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, HouseListActivity.class);
                        intent.putExtra("type", 1);
                        intent.putExtra("wuye_type", "investigate");
                        intent.putExtra("id", id);
                        mContext. startActivity(intent);
                    }
                }
            }
        }).start();

        banner_middle.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void setBanner() {
        myImageLoader = new MyCornerImageLoader();
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(myImageLoader);
        banner.isAutoPlay(true);//设置是否轮播
        banner.setIndicatorGravity(BannerConfig.CENTER);//小圆点位置
        banner.setDelayTime(4500);
        banner.setImageLoader(myImageLoader).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                // 点击banner
                if (modelHome != null && modelHome.getAd_top_list() != null && modelHome.getAd_top_list().size() > 0) {
                    ModelAds ads = modelHome.getAd_top_list().get(position);
                    if (TextUtils.isEmpty(ads.getUrl())) {
                        if ("0".equals(ads.getUrl_type()) || TextUtils.isEmpty(ads.getUrl_type())) {
                            new Jump(getActivity(), ads.getType_name(), ads.getAdv_inside_url());
                        } else {
                            new Jump(getActivity(), ads.getUrl_type(), ads.getType_name(), "", ads.getUrl_type_cn());
                        }
                    } else {//URL不为空时外链
                        new Jump(getActivity(), ads.getUrl());

                    }
                }
            }
        }).start();

        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int COLOR_START = colors.get(position);
                int COLOR_END = 0;
                if (position + 1 >= colors.size()) {
                    COLOR_END = colors.get(0);
                } else {
                    COLOR_END = colors.get(position + 1);
                }
                ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                int COLOR = (int) (argbEvaluator.evaluate(positionOffset, COLOR_START, COLOR_END));
                current_Color = COLOR;
                if (alpha < 1) {
                    iv_bg_title.setBackgroundColor(COLOR);
                    view_title_line.setBackgroundColor(COLOR);
                }
                iv_bg_banner.setBackgroundColor(COLOR);
            }

            @Override
            public void onPageSelected(int i) {
//                if (alpha<1){
//                    iv_bg_title.setBackgroundColor(getResources().getColor(colors.get(i)));
//                }
//                iv_bg_banner.setBackgroundColor(getResources().getColor(colors.get(i)));
                current_banner_position = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    int scollYHeight = -headerView.getTop();
                    if (scollYHeight < DeviceUtils.dip2px(mContext, 400)) {
                        if (!v_banner.isStarted() && mDatas_v_banner.size() > 0) {
                            v_banner.start();
                        }

//                        if (!v_banner_zixun.isStarted() ) {
//                            v_banner_zixun.start();
//                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (headerView != null) {
                    //设置其透明度
                    alpha = 0;
                    //向上滑动的距离
                    int scollYHeight = -headerView.getTop();
                    if (scollYHeight >= DeviceUtils.dip2px(mActivity, 225)) {
                        alpha = 1;//滑上去就一直显示
                    } else {
                        alpha = scollYHeight / ((DeviceUtils.dip2px(mActivity, 225)) * 1.0f);
                    }
                    if (alpha == 1) {
                        // 滑上去了
                        iv_bg_title.setBackgroundColor(getResources().getColor(R.color.white));
                        tv_xiaoqu.setTextColor(getResources().getColor(R.color.text_special_33_color));
                        iv_message.setBackgroundResource(R.mipmap.ic_index_message_black);
                        iv_scancode.setBackgroundResource(R.mipmap.ic_index_scan_black);
                        iv_title_arrow.setBackgroundResource(R.mipmap.ic_arrow_black_sloid);
                        view_title_line.setBackgroundColor(getResources().getColor(R.color.line));

                    } else {
                        /// 滑下来
                        if (colors.size() > 0 && colors.size() > current_banner_position) {
                            iv_bg_title.setBackgroundColor(current_Color);
                            view_title_line.setBackgroundColor(current_Color);
                        }
                        tv_xiaoqu.setTextColor(getResources().getColor(R.color.text_special_ff_color));
                        iv_message.setBackgroundResource(R.mipmap.ic_index_message_white);
                        iv_scancode.setBackgroundResource(R.mipmap.ic_index_scan_white);
                        iv_title_arrow.setBackgroundResource(R.mipmap.ic_arrow_white_sloid);
                    }

                }
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                requestData();
            }
        });
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {

            }
        });

        tv_xiaoqu.setOnClickListener(this);
        iv_rent.setOnClickListener(this);
        iv_sell.setOnClickListener(this);
        iv_release_rent_sell.setOnClickListener(this);
        iv_scancode.setOnClickListener(this);
        tv_sec_kill_more.setOnClickListener(this);
        tv_more_sec_kill_arrow.setOnClickListener(this);
        tv_more_sale.setOnClickListener(this);
        tv_more_sale_arrow.setOnClickListener(this);
        iv_center.setOnClickListener(this);
        iv_message.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuName())) {
            //1.小区名字不为空的情况
            tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName() + "");
            if (NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())) {
                //两种情况 旧的线上用户覆盖安装肯定没有省市区
                prefrenceUtil.setProvince_cn("山西省");
                prefrenceUtil.setCity_cn("晋中市");
                prefrenceUtil.setRegion_cn("榆次区");
            } else {
                //新用户肯定有省市区也有名字
            }
            showDialog(smallDialog);
            requestData();
        } else {
            //2.小区名字为空的情况(第一次进来)
            requestLocationPermission();
        }

    }

    /**
     * 请求
     */
    private void requestLocationPermission() {
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isGranted) throws Exception {
                        if (isGranted) {
                            //权限同意 ,开始定位
                            showDialog(smallDialog);
                            smallDialog.setTipTextView("定位中...");
                            mlocationClient.startLocation();

                        } else {
                            //权限拒绝 ,默认智慧小区
                            prefrenceUtil.clearPreference(mActivity);
                            prefrenceUtil.setXiaoQuName("智慧小区");
                            prefrenceUtil.setProvince_cn("山西省");
                            prefrenceUtil.setCity_cn("晋中市");
                            prefrenceUtil.setRegion_cn("榆次区");
                            tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName() + "");
                            showDialog(smallDialog);
                            requestData();

                        }
                    }
                });
    }

    /**
     * 请求数据
     */
    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        // 小区id 要判断
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {
            params.put("c_id", prefrenceUtil.getXiaoQuId());
        }
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())) {
            params.put("province_cn", prefrenceUtil.getProvince_cn());
            params.put("city_cn", prefrenceUtil.getCity_cn());
            params.put("region_cn", prefrenceUtil.getRegion_cn());
        }
        MyOkHttp.get().post(ApiHttpClient.INDEX, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                if (JsonUtil.getInstance().isSuccess(response)) {

                    modelHome = (ModelHome) JsonUtil.getInstance().parseJsonFromResponse(response, ModelHome.class);
                    getIndexData(modelHome);
                } else {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                if (refreshLayout != null) {
                    refreshLayout.finishRefresh(false);
                }
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    /**
     * 获取首页的数据
     *
     * @param modelHome
     */
    private void getIndexData(final ModelHome modelHome) {
        if (modelHome != null) {
            headerView.setVisibility(View.VISIBLE);
            //banner
            colors.clear();
            if (modelHome.getAd_top_list() != null && modelHome.getAd_top_list().size() > 0) {

                ArrayList<String> mDatas_img1 = new ArrayList<>();
                for (int i = 0; i < modelHome.getAd_top_list().size(); i++) {
                    mDatas_img1.add(ApiHttpClient.IMG_URL + modelHome.getAd_top_list().get(i).getImg() + "");
                    if (NullUtil.isStringEmpty(modelHome.getAd_top_list().get(i).getIndex_color())) {
                        colors.add(Color.WHITE);
                    } else {
                        colors.add(Color.parseColor(modelHome.getAd_top_list().get(i).getIndex_color()));
                    }
                }
                banner.update(mDatas_img1);
            }
            //分类导航
            if (modelHome.getMenu_list() != null && modelHome.getMenu_list().size() > 0) {
                fl_grid_container.setVisibility(View.VISIBLE);
                if (homeGridViewCateAdapter == null) {
                    for (int i = 0; i < modelHome.getMenu_list().size(); i++) {

                        mcatelist.add(modelHome.getMenu_list().get(i));
                    }
                    homeGridViewCateAdapter = new HomeGridViewCateAdapter(mActivity, mcatelist, 1, this);
                    gridview_home.setAdapter(homeGridViewCateAdapter);
                } else {
                    mcatelist.clear();
                    for (int i = 0; i < modelHome.getMenu_list().size(); i++) {
                        mcatelist.add(modelHome.getMenu_list().get(i));
                    }
                    homeGridViewCateAdapter.notifyDataSetChanged();
                }
            } else {
                fl_grid_container.setVisibility(View.GONE);
            }
            //中部广告
            if (modelHome.getQi_plan_list() != null && modelHome.getQi_plan_list().size() > 0) {
                banner_middle.setVisibility(View.VISIBLE);
                ArrayList<String> mDatas_img1 = new ArrayList<>();
                for (int i = 0; i < modelHome.getQi_plan_list().size(); i++) {
                    mDatas_img1.add(ApiHttpClient.IMG_URL + modelHome.getQi_plan_list().get(i).getImg() + "");
                }
                banner_middle.update(mDatas_img1);
            }else {
                banner_middle.setVisibility(View.GONE);
            }
            //grid背景图
            //  iv_bg_grid.setBackgroundColor(getResources().getColor(R.color.blue));
            //手册协议
            if (modelHome.getArticle_list() != null && modelHome.getArticle_list().size() > 0) {
                iv_center.setVisibility(View.VISIBLE);
                ll_center.setVisibility(View.VISIBLE);
                // 手册协议
              //  iv_center.setImageResource(R.mipmap.bg_jiaofang_shouce);
               // GlideUtils.getInstance().glideLoad(mActivity,ApiHttpClient.IMG_URL+"huacheng/activity/19/04/22/xinfangshouce.gif",iv_center,R.mipmap.bg_jiaofang_shouce);
                mDatas_Article.clear();
                mDatas_Article.addAll(modelHome.getArticle_list());
            } else {
                iv_center.setVisibility(View.GONE);
                ll_center.setVisibility(View.GONE);
            }

            //通知公告
            if (modelHome.getP_social_list() != null && modelHome.getP_social_list().size() > 0) {
                ly_notice.setVisibility(View.VISIBLE);
                mDatas_v_banner.clear();
                mDatas_v_banner.addAll(modelHome.getP_social_list());
                if (!v_banner.isStarted()) {
                    vBannerAdapter = new VBannerAdapter(mDatas_v_banner);
                    v_banner.setAdapter(vBannerAdapter);
                    v_banner.start();
                } else {
                    if (vBannerAdapter != null) {
                        vBannerAdapter.notifyDataChanged();
                    }
                    v_banner.stop();
                    v_banner.start();
                }
            } else {
                ly_notice.setVisibility(View.GONE);
            }
            // 特卖专场
            if (modelHome.getSpecial() != null && modelHome.getSpecial().size() > 0) {
                ll_on_sale_container.setVisibility(View.VISIBLE);
                ll_on_sale_img_root.removeAllViews();
                for (int i = 0; i < modelHome.getSpecial().size(); i++) {
                    View item_home_on_sale = LayoutInflater.from(mActivity).inflate(R.layout.item_home_on_sale, null);
                    SimpleDraweeView sdv_on_sale = item_home_on_sale.findViewById(R.id.sdv_on_sale);
                    final String id = modelHome.getSpecial().get(i).getId();
                    FrescoUtils.getInstance().setImageUri(sdv_on_sale, ApiHttpClient.IMG_URL + modelHome.getSpecial().get(i).getIcon_img());
                    sdv_on_sale.setOnClickListener(new OnDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            Intent intent = new Intent(mActivity, ShopZQListActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }
                    });
                    ll_on_sale_img_root.addView(item_home_on_sale);
                }

            } else {
                ll_on_sale_container.setVisibility(View.GONE);
            }

            //慧秒杀
            List<ModelShopIndex> seckill_list = modelHome.getSeckill();
            if (seckill_list != null && seckill_list.size() > 0) {
                ll_sec_kill_container.setVisibility(View.VISIBLE);
                ll_sec_kill_container_root.removeAllViews();
                for (int i = 0; i < seckill_list.size(); i++) {
                    View item_home_sec_kill = LayoutInflater.from(mActivity).inflate(R.layout.item_home_sec_kill, null);
                    LinearLayout ly_onclick = item_home_sec_kill.findViewById(R.id.ly_onclick);

                    SimpleDraweeView sdv_sec_kill = item_home_sec_kill.findViewById(R.id.sdv_sec_kill);
                    TextView tv_title = item_home_sec_kill.findViewById(R.id.tv_title);
                    TextView tv_sub_title = item_home_sec_kill.findViewById(R.id.tv_sub_title);
                    TextView tv_shop_price = item_home_sec_kill.findViewById(R.id.tv_shop_price);
                    TextView tv_shop_price_original = item_home_sec_kill.findViewById(R.id.tv_shop_price_original);
                    tv_shop_price_original.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    final ModelShopIndex shopIndex = seckill_list.get(i);
                    FrescoUtils.getInstance().setImageUri(sdv_sec_kill, ApiHttpClient.IMG_URL + shopIndex.getTitle_img());
                    tv_title.setText(shopIndex.getTitle() + "");
                    tv_sub_title.setText(shopIndex.getDescription() + "");
                    tv_shop_price.setText("¥ " + shopIndex.getPrice());
                    tv_shop_price_original.setText("¥ " + shopIndex.getOriginal() + "");

                    ly_onclick.setOnClickListener(new OnDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("shop_id", shopIndex.getId());
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        }
                    });
                    ll_sec_kill_container_root.addView(item_home_sec_kill);
                }
            } else {
                ll_sec_kill_container.setVisibility(View.GONE);
            }

            // 附近美食
            ll_nearby_food_container.setVisibility(View.GONE);
//            ll_nearby_food_img_root.removeAllViews();
//            for (int i = 0; i <7; i++) {
//                View item_home_nearby_food = LayoutInflater.from(mActivity).inflate(R.layout.item_home_nearby_food, null);
//                SimpleDraweeView sdv_nearby_food = item_home_nearby_food.findViewById(R.id.sdv_nearby_food);
//                TextView tv_nearby_food_name = item_home_nearby_food.findViewById(R.id.tv_nearby_food_name);
//                TextView tv_nearby_food_price = item_home_nearby_food.findViewById(R.id.tv_nearby_food_price);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                params.setMargins(0,0,DeviceUtils.dip2px(mActivity,10),0);
//                ll_nearby_food_img_root.addView(item_home_nearby_food,params);
//            }
            //租售房服务

            //热门资讯
            ll_zixun_container.setVisibility(View.VISIBLE);
            if (modelHome.getSocial_list() != null && modelHome.getSocial_list().size() > 0) {
                ll_zixun_container.setVisibility(View.VISIBLE);
                ll_zixun_container_root.removeAllViews();
                for (int i = 0; i < modelHome.getSocial_list().size(); i++) {
                    View item_home_circle = LayoutInflater.from(mActivity).inflate(R.layout.item_home_circle, null);
                    TextView tv_title = item_home_circle.findViewById(R.id.tv_title);
                    TextView tv_content = item_home_circle.findViewById(R.id.tv_content);
                    SimpleDraweeView sdv_circle = item_home_circle.findViewById(R.id.sdv_circle);
                    TextView tv_circle_name = item_home_circle.findViewById(R.id.tv_circle_name);
                    TextView tv_read_count = item_home_circle.findViewById(R.id.tv_read_count);
                    TextView tv_time = item_home_circle.findViewById(R.id.tv_time);
                    TextView tv_more_circle = item_home_circle.findViewById(R.id.tv_more_circle);
                    ImageView tv_more_circle_arrow = item_home_circle.findViewById(R.id.tv_more_circle_arrow);

                    ll_zixun_container_root.addView(item_home_circle);
                    //显示
                    final ModelHomeCircle modelHomeCircle = modelHome.getSocial_list().get(i);
                    tv_title.setText(modelHomeCircle.getTitle() + "");
                    byte[] bytes2 = Base64.decode(modelHomeCircle.getList().getTitle(), Base64.DEFAULT);
                    tv_content.setText(new String(bytes2));
                    if (modelHomeCircle.getList().getImg_list() != null && modelHomeCircle.getList().getImg_list().size() > 0) {
                        if (!TextUtils.isEmpty(modelHomeCircle.getList().getImg_list().get(0).getImg())) {

                            FrescoUtils.getInstance().setImageUri(sdv_circle, ApiHttpClient.IMG_URL + modelHomeCircle.getList().getImg_list().get(0).getImg());
                            sdv_circle.setVisibility(View.VISIBLE);
                        } else {
                            sdv_circle.setVisibility(View.GONE);
                        }
                    } else {
                        sdv_circle.setVisibility(View.GONE);
                    }
                    tv_circle_name.setText(modelHomeCircle.getList().getC_name() + "");
                    tv_read_count.setText(modelHomeCircle.getList().getClick() + "阅读");
                    tv_time.setText(modelHomeCircle.getList().getAddtime() + "");

                    tv_more_circle.setOnClickListener(new OnDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            //查看更多
                            // 跳转更多
                            ModelEventHome modelEventHome = new ModelEventHome();
                            modelEventHome.setType(modelHomeCircle.getIndex());
                            EventBus.getDefault().post(modelEventHome);
                        }
                    });
                    tv_more_circle_arrow.setOnClickListener(new OnDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            //查看更多
                            // 跳转更多
                            ModelEventHome modelEventHome = new ModelEventHome();
                            modelEventHome.setType(modelHomeCircle.getIndex());
                            EventBus.getDefault().post(modelEventHome);
                        }
                    });
                    item_home_circle.setOnClickListener(new OnDoubleClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            Intent intent = new Intent(mActivity, CircleDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("id", modelHomeCircle.getList().getId());
                            bundle.putString("mPro", modelHomeCircle.getList().getIs_pro()+"");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
            } else {
                ll_zixun_container.setVisibility(View.GONE);
            }
            //
            //底部商品信息
            if (modelHome.getPro_list() != null && modelHome.getPro_list().size() > 0) {
                mDatas.clear();
                mDatas.addAll(modelHome.getPro_list());
                listView.setHasMoreItems(false);
                adapter.notifyDataSetChanged();
            }
        }

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
                    if (NullUtil.isStringEmpty(location.getDistrict())) {
//                        hideDialog(smallDialog);
//                        // tvResult.setText("定位失败，loc is null");
//                        text_city.setText("定位失败...点击重新定位");
//                        text_city.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startLocation();
//                            }
//                        });
                    } else {
                        isInitLocaion = true;
                        //默认选中
                        mlocationClient.stopLocation();
                        location_provice = location.getProvince() + "";
                        location_city = location.getCity() + "";
                        location_district = location.getDistrict() + "";

                        getPOIsearch(location.getLongitude(), location.getLatitude());

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
                //定位失败 显示智慧小区
                prefrenceUtil.clearPreference(mActivity);
                prefrenceUtil.setXiaoQuName("智慧小区");
                prefrenceUtil.setProvince_cn("山西省");
                prefrenceUtil.setCity_cn("晋中市");
                prefrenceUtil.setRegion_cn("榆次区");
                tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName() + "");
                showDialog(smallDialog);
                requestData();
            }

        } else {
            hideDialog(smallDialog);
            // tvResult.setText("定位失败，loc is null");
            //定位失败 显示智慧小区
            prefrenceUtil.clearPreference(mActivity);
            prefrenceUtil.setXiaoQuName("智慧小区");
            prefrenceUtil.setProvince_cn("山西省");
            prefrenceUtil.setCity_cn("晋中市");
            prefrenceUtil.setRegion_cn("榆次区");
            tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName() + "");
            showDialog(smallDialog);
            requestData();
        }
    }

    private String[] SetTime(long time) {
        long mDay = time / (1000 * 60 * 60 * 24);
        long mHour = (time - mDay * (1000 * 60 * 60 * 24))
                / (1000 * 60 * 60);
        long mMin = (time - mDay * (1000 * 60 * 60 * 24) - mHour
                * (1000 * 60 * 60))
                / (1000 * 60);
        long mSecond = (time - mDay * (1000 * 60 * 60 * 24) - mHour
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_new;
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();

    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }


    @Override
    public void onClickImg(View v, int position, int type) {
        //不做操作
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        mLocationOption = null;

        super.onDestroy();
    }

    /**
     * 调用高德地图搜索周边住宅
     *
     * @param longitude
     * @param latitude
     */
    private void getPOIsearch(double longitude, double latitude) {
        PoiSearch.Query query = new PoiSearch.Query("", "商务住宅", "");
        query.setPageSize(15);
        PoiSearch search = new PoiSearch(mActivity, query);
        search.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 10000));
        search.setOnPoiSearchListener(this);
        search.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult result, int i) {
        PoiSearch.Query query = result.getQuery();
        ArrayList<PoiItem> pois = result.getPois();

        if (pois != null && pois.size() > 0) {

            String community_name = pois.get(0).toString();
            String address = pois.get(0).getSnippet();
            //    prefrenceUtil.clearPreference(mActivity);
            // 选择上了小区
            // 这里要进行匹配
//            prefrenceUtil.setXiaoQuName(community_name);
//            prefrenceUtil.setAddressName(address);
//            tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName()+"");
//            showDialog(smallDialog);
//            smallDialog.setTipTextView("加载中...");
//            requestData();
            ModelCoummnityList modelCoummnityList = new ModelCoummnityList();
            modelCoummnityList.setName(community_name);
            modelCoummnityList.setAddress(address);
            requestCommunityId(modelCoummnityList);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 根据小区名字请求小区id
     *
     * @param item
     */
    private void requestCommunityId(final ModelCoummnityList item) {
        showDialog(smallDialog);
        smallDialog.setTipTextView("加载中...");
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(item.getName())) {
            params.put("community_name", item.getName() + "");
        }
        MyOkHttp.get().post(ApiHttpClient.GET_COMMUNITY_ID, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                //  hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    try {
                        if (response.has("data")) {
                            //匹配成功
                            JSONObject data = response.getJSONObject("data");
                            prefrenceUtil.clearPreference(mContext);
                            if (!NullUtil.isStringEmpty(data.getString("id"))) {
                                prefrenceUtil.setXiaoQuId(data.getString("id"));
                                prefrenceUtil.setCompanyId(data.getString("company_id"));
                            }
                            prefrenceUtil.setXiaoQuName(item.getName());
                            prefrenceUtil.setAddressName(item.getAddress());
                            //保存省市区
                            prefrenceUtil.setProvince_cn(location_provice);
                            prefrenceUtil.setCity_cn(location_city);
                            prefrenceUtil.setRegion_cn(location_district);
                            tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName() + "");
                            showDialog(smallDialog);
                            smallDialog.setTipTextView("加载中...");
                            requestData();
                            getsubmitCommunityId(data.getString("id"));
                        } else {
                            //匹配失败
                            prefrenceUtil.clearPreference(mContext);
                            prefrenceUtil.setXiaoQuId("");
                            prefrenceUtil.setXiaoQuName(item.getName());
                            prefrenceUtil.setAddressName(item.getAddress());
                            //保存省市区
                            prefrenceUtil.setProvince_cn(location_provice);
                            prefrenceUtil.setCity_cn(location_city);
                            prefrenceUtil.setRegion_cn(location_district);
                            tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName() + "");
                            showDialog(smallDialog);
                            smallDialog.setTipTextView("加载中...");
                            requestData();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //匹配失败
                        //  data==null
                        prefrenceUtil.clearPreference(mContext);
                        prefrenceUtil.setXiaoQuId("");
                        prefrenceUtil.setXiaoQuName(item.getName());
                        prefrenceUtil.setAddressName(item.getAddress());
                        //保存省市区
                        prefrenceUtil.setProvince_cn(location_provice);
                        prefrenceUtil.setCity_cn(location_city);
                        prefrenceUtil.setRegion_cn(location_district);
                        tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName() + "");
                        showDialog(smallDialog);
                        smallDialog.setTipTextView("加载中...");
                        requestData();
                    }

                } else {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }

    //提交小区id
    private void getsubmitCommunityId(String community_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("community_id", community_id + "");

        MyOkHttp.get().post(ApiHttpClient.SELECT_COMMUNITY, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    /**
     * 添加和删除评论的Eventbus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateItem(CircleDetailBean mCirclebean) {
//        try {
//            if (mCirclebean != null) {
//                int type = mCirclebean.getType();
//                if (type == 0) {
//                    //添加评论
//                    if (mCirclebean.getId().equals(mSocial.getId())) {
//                        int i1 = Integer.parseInt(mCirclebean.getReply_num());
//                        mSocial.setReply_num((i1 + 1) + "");
//                        tv_circleReply.setText(mSocial.getReply_num());
//                    }
//                } else if (type == 1) {
//                    //删除评论
//                    if (mCirclebean.getId().equals(mSocial.getId())) {
//                        int i1 = Integer.parseInt(mCirclebean.getReply_num());
//                        mSocial.setReply_num((i1 - 1) + "");
//                        tv_circleReply.setText(mSocial.getReply_num());
//                    }
//                } else if (type == 2) {
//                    //阅读数
//                    if (mCirclebean.getId().equals(mSocial.getId())) {
//                        tv_circleViews.setText(mCirclebean.getClick());
//
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onClickImage(int position) {
        // 点击下方商品图片
        if (position == -1) {
            return;
        }
        if (NullUtil.isStringEmpty(mDatas.get((int) position).getInventory()) || 0 >= Integer.valueOf(mDatas.get((int) position).getInventory())) {
            SmartToast.showInfo("商品已售罄");
        } else {
            Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
            Bundle bundle = new Bundle();
            bundle.putString("shop_id", mDatas.get((int) position).getId());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onClickShopCart(int position) {
        //点击购物车
        if (ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
            Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
            mContext.startActivity(intent);

        } else {

            if ("2".equals(mDatas.get(position).getExist_hours())) {
                SmartToast.showInfo("当前时间不在派送时间范围内");
            } else {
                if (mDatas.get(position) != null) {
                    new CommonMethod(mDatas.get(position), null, mContext).getShopLimitTag();
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_xiaoqu:
                intent = new Intent(getActivity(), CommunityListActivity.class);
                startActivityForResult(intent, 111);
                break;
            case R.id.iv_rent:
                //租房
                intent = new Intent(mActivity, HouseRentListActivity.class);
                intent.putExtra("jump_type", 1);
                startActivity(intent);
                break;
            case R.id.iv_sell:
                //售房
                intent = new Intent(mActivity, HouseRentListActivity.class);
                intent.putExtra("jump_type", 2);
                startActivity(intent);
                break;
            case R.id.iv_release_rent_sell:
                //发布租售
                if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
                    intent = new Intent(mActivity, RentSellCommissionActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(mActivity, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.iv_scancode:
                //扫描二维码
                if (LoginUtils.hasLoginUser()) {
                    scanCode();
                } else {
                    intent = new Intent(mActivity, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.tv_sec_kill_more:
            case R.id.tv_more_sec_kill_arrow:
                //秒杀查看更多
                intent = new Intent();
                intent.setClass(mActivity, ShopSecKillListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cateID", "1");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_more_sale:
            case R.id.tv_more_sale_arrow:
                //特卖专场查看更多
                intent = new Intent(mContext, ShopZCListActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_center:
                intent = new Intent(mActivity, HouseHandBookActivity.class);
                intent.putExtra("mDatas", (Serializable) mDatas_Article);
                startActivity(intent);//交房手册
                break;
            case R.id.iv_message:
                //消息（目前只有养老消息）
                if (ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
                    intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                    mContext.startActivity(intent);

                } else {
                startActivity(new Intent(mActivity, OldMessageActivity.class));
                }
                break;
            default:
                break;
        }
    }

    /**
     * 扫二维码
     */
    private void scanCode() {
        new RxPermissions(mActivity).request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isGranted) throws Exception {
                        if (isGranted) {
                            IntentIntegrator intentIntegrator = new IntentIntegrator(mActivity)
                                    .setOrientationLocked(false);
                            intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
                        /*intentIntegrator.setPrompt("将服务师傅的二维码放入框内\n" +
                            "即可扫描付款");*/
                            // 设置自定义的activity是ScanActivity
                            intentIntegrator.initiateScan(); // 初始化扫描
                        } else {

                        }
                    }
                });
    }
}
