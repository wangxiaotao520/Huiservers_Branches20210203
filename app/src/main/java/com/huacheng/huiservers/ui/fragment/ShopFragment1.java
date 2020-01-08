package com.huacheng.huiservers.ui.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
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
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.shop.SearchShopActivity;
import com.huacheng.huiservers.ui.shop.ShopCartActivityNew;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.ui.shop.bean.ShopDetailBean;
import com.huacheng.huiservers.utils.MyCornerImageLoader;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.huiservers.view.widget.EnhanceTabLayout;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.widget.CustomScrollViewPager;
import com.huacheng.libraryservice.widget.GridViewNoScroll;
import com.lzy.widget.HeaderViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.OnDoubleClickListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 过渡版本商城首页（2019年最后一版）
 * created by wangxiaotao
 * 2019/12/28 0028 下午 5:16
 */
public class ShopFragment1 extends BaseFragment {
    public SmartRefreshLayout refreshLayout;
    View mStatusBar;
    private List<String> mDatas = new ArrayList<>();
    private List<ModelShopIndex> mDatasCate = new ArrayList<>();


    private GridViewNoScroll gridview_shop;
    private LinearLayout ll_youxuan_container;
    private LinearLayout ll_youxuan_img_root;
    private AdapterShopIndexGridCate adapterShopIndexGridCate;
    private Banner banner;
    private MyCornerImageLoader myImageLoader;
    private ImageView iv_shop_search;
    private RelativeLayout ry_gouwu;
    private TextView txt_shop_num;
    private HeaderViewPager scrollableLayout;
    private EnhanceTabLayout mTabLayout;
    private CustomScrollViewPager mViewPager;
    private List<FragmentShopCommon> mFragmentList = new ArrayList();
    private FragmentShopCommon currentFragment;
    private boolean is_Refresh = false;

    private SharePrefrenceUtil prefrenceUtil;
    private View ll_youxuan_layout;//优选布局
    private View ll_biguang_layout;//必逛布局
    private MyGridview grid_center_ad;
    String[] mTitle = new String[0];
    private float alpha;  //透明度 标志滑动到的位置状态
    private List<ModelAds> adHead;
    private View view_tab_line;
    private View view_title_line;

    @Override
    public void initView(View view) {
        mStatusBar=view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));

        iv_shop_search = view.findViewById(R.id.iv_shop_search);
        ry_gouwu = view.findViewById(R.id.ry_gouwu);
        txt_shop_num = view.findViewById(R.id.txt_shop_num);

        refreshLayout=view.findViewById(R.id.refreshLayout);
        // 一开始先不要让加载更多,防止网络错误时，加载更多报错
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        scrollableLayout = view.findViewById(R.id.scrollableLayout);
        scrollableLayout.setTopOffset(DeviceUtils.dip2px(mContext,0));
        scrollableLayout.setVisibility(View.INVISIBLE);
        prefrenceUtil = new SharePrefrenceUtil(mActivity);



        gridview_shop = view.findViewById(R.id.gridview_shop);
        //banner
        banner = view.findViewById(R.id.banner);
        setBanner();
        //优选
        ll_youxuan_layout = view.findViewById(R.id.ll_youxuan_layout);
        ll_youxuan_container = view.findViewById(R.id.ll_youxuan_container);
        ll_youxuan_img_root = view.findViewById(R.id.ll_youxuan_img_root);
        //每日必逛
        ll_biguang_layout = view.findViewById(R.id.ll_biguang_layout);
        grid_center_ad = view.findViewById(R.id.grid_ad);

        mTabLayout = view.findViewById(R.id.tl_tab);
        mViewPager = view.findViewById(R.id.vp_pager);
        view_tab_line = view.findViewById(R.id.view_tab_line);
        view_title_line = view.findViewById(R.id.view_title_line);
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
                if (alpha>=1){
                    mTabLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    view_tab_line.setVisibility(View.VISIBLE);
                    view_title_line.setBackgroundColor(getResources().getColor(R.color.white));
                }else {
                    mTabLayout.setBackgroundColor(getResources().getColor(R.color.transparents));
                    view_tab_line.setVisibility(View.INVISIBLE);
                    view_title_line.setBackgroundColor(getResources().getColor(R.color.windowbackground));
                }

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
        iv_shop_search.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {

               Intent intent =new Intent();
                intent  .setClass(mActivity, SearchShopActivity.class);
                intent.putExtra("type",0);
                startActivity(intent);
            }
        });
        ry_gouwu.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (getLinshi()) {
                    Intent intent =new Intent();
                    intent.setClass(mActivity, ShopCartActivityNew.class);
                    startActivity(intent);
                } else {
                    Intent intent =new Intent();
                    intent.setClass(mActivity, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        showDialog(smallDialog);
        requestData();


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
                scrollableLayout.setVisibility(View.VISIBLE);
                if (JsonUtil.getInstance().isSuccess(response)) {
                     ModelShopNew info = (ModelShopNew) JsonUtil.getInstance().parseJsonFromResponse(response, ModelShopNew.class);
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

                                scrollableLayout.setCan_scroll(true);
                                initTabLayout();
                            }else {
                                // 没数据的情况
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
    //初始化tab布局
    private void initTabLayout() {
        //初始化title
        for(int i=0;i<mTitle.length;i++){
            mTabLayout.addTab(mTitle[i]);
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout.getTabLayout()));

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
      //  mTabLayout.setBackgroundColor(getResources().getColor(R.color.white));
        mTabLayout.setupWithViewPager(mViewPager);
     //   mViewPager.setOnPageChangeListener(onPageChangeListener);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()<mFragmentList.size()){
                    //在这里传入参数
                    FragmentShopCommon fragmentCommon = (FragmentShopCommon) mFragmentList.get(tab.getPosition());
                    currentFragment = fragmentCommon;
                    scrollableLayout.setCurrentScrollableContainer(currentFragment);
                    fragmentCommon.init(alpha);
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
    /**
     * 判断是否登录了
     */
    private boolean getLinshi() {
        SharedPreferences preferencesLogin= mActivity.getSharedPreferences("login", 0);
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
        //  params.addBodyParameter("c_id", prefrenceUtil.getXiaoQuId());
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())){
            params.addBodyParameter("province_cn", prefrenceUtil.getProvince_cn());
            params.addBodyParameter("city_cn", prefrenceUtil.getCity_cn());
            params.addBodyParameter("region_cn", prefrenceUtil.getRegion_cn());
        }
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
    /**
     * 填充数据
     *
     * @param info
     */
    private void inflateContent(ModelShopNew info) {

        if (info.getCate_list() != null && info.getCate_list().size() > 0) {//商品分类

            //分类
            gridview_shop.setVisibility(View.VISIBLE);
            mDatasCate.clear();
            mDatasCate.addAll(info.getCate_list());
            //todo
            adapterShopIndexGridCate = new AdapterShopIndexGridCate(mContext, R.layout.item_shop_cate_new, mDatasCate);
            gridview_shop.setAdapter(adapterShopIndexGridCate);

            adapterShopIndexGridCate.notifyDataSetChanged();
        } else {
            gridview_shop.setVisibility(View.INVISIBLE);
        }

        //banner
        adHead = info.getAd_hc_shopindex();
        if (adHead != null && adHead.size() > 0) {//头部广告

            ArrayList<String> mDatas_img1 = new ArrayList<>();
            for (int i = 0; i < info.getAd_hc_shopindex().size(); i++) {
                mDatas_img1.add(ApiHttpClient.IMG_URL+info.getAd_hc_shopindex().get(i).getImg() + "");
            }
            banner.update(mDatas_img1);
        }

        // 优选(之前的限时抢购)
        ModelShopIndex discountList = info.getPro_discount_list();//限时抢购
        if (discountList != null) {
            List<ModelShopIndex> limits = discountList.getList();
           if (limits != null && limits.size() > 0) {
               ll_youxuan_layout.setVisibility(View.VISIBLE);
               ll_youxuan_container .setVisibility(View.VISIBLE);
               ll_youxuan_img_root.setVisibility(View.VISIBLE);
               ll_youxuan_img_root.removeAllViews();
               for (int i = 0; i < limits.size(); i++) {
                   View item_img_view = LayoutInflater.from(mContext).inflate(R.layout.item_item_shop_goods, null);
                   LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                   params.setMargins(0,0, DeviceUtils.dip2px(mContext,10),0);
                   item_img_view.setLayoutParams(params);

                   SimpleDraweeView sdv_nearby_food = item_img_view.findViewById(R.id.sdv_nearby_food);
                   TextView tv_nearby_food_name = item_img_view.findViewById(R.id.tv_nearby_food_name);
                   TextView tv_nearby_food_price = item_img_view.findViewById(R.id.tv_nearby_food_price);
                   TextView tv_origin_price = item_img_view.findViewById(R.id.tv_origin_price);

                   final ModelShopIndex shopIndex = limits.get(i);
                   FrescoUtils.getInstance().setImageUri(sdv_nearby_food, ApiHttpClient.IMG_URL + shopIndex.getTitle_img());
                   tv_nearby_food_name.setText(shopIndex.getTitle()+"");
                   tv_nearby_food_price.setText("¥ " + shopIndex.getPrice());
                   tv_origin_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
                   tv_origin_price.setText("¥ " + shopIndex.getOriginal());
                   tv_origin_price.setVisibility(View.VISIBLE);
                   ll_youxuan_img_root.addView(item_img_view);
                   item_img_view.setOnClickListener(new OnDoubleClickListener() {
                       @Override
                       public void onNoDoubleClick(View v) {
                           Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
                           Bundle bundle = new Bundle();
                           bundle.putString("shop_id", shopIndex.getId());
                           intent.putExtras(bundle);
                           mContext.startActivity(intent);
                       }
                   });

               }

            } else {
               ll_youxuan_layout.setVisibility(View.GONE);
            }
        } else {
            ll_youxuan_layout.setVisibility(View.GONE);
        }

       //每日必逛
        List<BannerBean> shopCenter = info.getAd_hc_shop_center();
        if (shopCenter != null && shopCenter.size() > 0) {
            ll_biguang_layout.setVisibility(View.VISIBLE);
            HomeCenterGirdAdapter mCenterAdapter = new HomeCenterGirdAdapter(mActivity, shopCenter);
            grid_center_ad.setAdapter(mCenterAdapter);
        } else {
            ll_biguang_layout.setVisibility(View.GONE);
        }


    }

    private void setBanner() {
        myImageLoader= new MyCornerImageLoader();
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(myImageLoader);
        banner.isAutoPlay(true);//设置是否轮播
        banner.setIndicatorGravity(BannerConfig.CENTER);//小圆点位置
        banner.setDelayTime(4500);
        banner.setImageLoader(myImageLoader).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                // 点击banner

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
    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop1;
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

}

