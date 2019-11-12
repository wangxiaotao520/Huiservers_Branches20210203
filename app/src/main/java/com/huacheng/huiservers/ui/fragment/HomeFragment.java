package com.huacheng.huiservers.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.huacheng.huiservers.CommunityListActivity;
import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.HouseRentDetail;
import com.huacheng.huiservers.model.ModelAds;
import com.huacheng.huiservers.model.ModelEventHome;
import com.huacheng.huiservers.model.ModelHome;
import com.huacheng.huiservers.model.ModelHomeIndex;
import com.huacheng.huiservers.model.ModelIndex;
import com.huacheng.huiservers.model.ModelLogin;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.model.ModelVBaner;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.circle.CircleDetailsActivity;
import com.huacheng.huiservers.ui.circle.bean.CircleDetailBean;
import com.huacheng.huiservers.ui.fragment.adapter.HomeAdcertlAdapter;
import com.huacheng.huiservers.ui.fragment.adapter.HomeArticleGridAdapter;
import com.huacheng.huiservers.ui.fragment.adapter.HomeArticleListAdapter;
import com.huacheng.huiservers.ui.fragment.adapter.HomeListViewAdapter;
import com.huacheng.huiservers.ui.fragment.adapter.HouseRentSellAdapter;
import com.huacheng.huiservers.ui.fragment.adapter.VBannerAdapter;
import com.huacheng.huiservers.ui.fragment.presenter.PropertyPrester;
import com.huacheng.huiservers.ui.index.houserent.HouseRentListActivity;
import com.huacheng.huiservers.ui.index.houserent.RentSellCommissionActivity;
import com.huacheng.huiservers.ui.index.property.PropertyBindHomeActivity;
import com.huacheng.huiservers.ui.index.property.PropertyNewActivity;
import com.huacheng.huiservers.ui.index.workorder.commit.PersonalWorkOrderCommitActivity;
import com.huacheng.huiservers.ui.index.workorder.commit.PublicWorkOrderCommitActivity;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.servicenew.ui.scan.CustomCaptureActivity;
import com.huacheng.huiservers.ui.shop.ShopDetailActivity;
import com.huacheng.huiservers.ui.shop.adapter.MyGridViewAdpter;
import com.huacheng.huiservers.ui.shop.adapter.MyViewPagerAdapter;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.utils.CommonMethod;
import com.huacheng.huiservers.utils.LoginUtils;
import com.huacheng.huiservers.utils.MyCornerImageLoader;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.CircularImage;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.widget.GridViewNoScroll;
import com.huacheng.libraryservice.widget.verticalbannerview.VerticalBannerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * Description: 首页新版
 * created by wangxiaotao
 * 2018/12/19 0019 下午 2:46
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeListViewAdapter.OnAddCartClickListener,
        PropertyPrester.PropertyListener, AMapLocationListener, PoiSearch.OnPoiSearchListener {
    private SmartRefreshLayout refreshLayout;
    //  private RecyclerView recyclerView;
    private PagingListView listView;
    private RelativeLayout ry_onclick;

   // private FunctionAdvertise fc_ads;
    private Banner banner;
    private RelativeLayout ry_daohang;
    private GridViewNoScroll gridViewHouse;
    private GridViewNoScroll gridViewGoods;
    private LinearLayout ly_community_agreement;
    private LinearLayout ly_property_notice;
    private LinearLayout ly_circle;
    private MyGridview gridView_article;
    private MyListView listview_article;
    private ViewPager viewpager_cate;
    private RelativeLayout ly_property_payment,ly_person_repair,ly_public_repair;
    private LinearLayout ly_circle_onclick, ly_circle_more, ly_houseRent, ly_houseSell, ly_houseCommit;
    private ImageView iv_img;
    private TextView tv_time, tv_name, tv_content, tv_circleViews, tv_circleReply, tv_title;
    private CircularImage iv_head;
    private ModelHome modelHome;
    private Jump jump;
    private List<ModelShopIndex> mDatas = new ArrayList<>();//首页item数据
    private List<ModelVBaner> mDatas_v_banner = new ArrayList<>();//首页垂直banner数据
    private List<ModelHomeIndex> mcatelist = new ArrayList<>();//导航分类
    private List<ModelIndex> mArticle_list = new ArrayList<>();//协议数据
    private BannerBean mSocial = new BannerBean();//圈子数据
    private List<HouseRentDetail> mHouses_list = new ArrayList<>();//租售房数据
    private List<ModelAds> mAd_Centerlist = new ArrayList<>();//精选商品下方广告
    //  private HomeRecyclerAdapter<ModelHomeItem> adapter;
    private View headerView;
    //  private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    //   private LoadMoreWrapper mLoadMoreWrapper;//刷新的时候拿它刷
    int page = 1;
    private HomeListViewAdapter adapter;
    private HouseRentSellAdapter mRentSellAdapter;
    private HomeAdcertlAdapter mAdcertlAdapter;
    private HomeArticleGridAdapter article12GridAdapter;
    private HomeArticleListAdapter articleAdapter;
    private VerticalBannerView v_banner;
    int headerHeight = 0;
    private LinearLayout group;//圆点指示器
    private ImageView[] ivPoints;//小圆点图片的集合
    SharePrefrenceUtil prefrenceUtil;
    private int catePage; //总的页数
    private int mPageSize = 8; //每页显示的最大的数量
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private TextView tv_xiaoqu;
    private View view_alpha;
    PropertyPrester mPrester;

    View mStatusBar;
    private ImageView iv_scancode;
    private MyCornerImageLoader myImageLoader;
    private RxPermissions rxPermissions;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private boolean isInitLocaion = false;

    @Override
    public void initView(View view) {
        rxPermissions=new RxPermissions(mActivity);
        mPrester = new PropertyPrester(mActivity, this);
        prefrenceUtil = new SharePrefrenceUtil(mActivity);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        //    recyclerView=view.findViewById(R.id.recyclerview);
        listView = view.findViewById(R.id.listView);
        ry_onclick = view.findViewById(R.id.ry_onclick);
        tv_xiaoqu = view.findViewById(R.id.tv_xiaoqu);

        view_alpha = view.findViewById(R.id.view_alpha);
        view_alpha.setAlpha(1);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(true);

        initHeaderView();
        adapter = new HomeListViewAdapter(mActivity, R.layout.shop_list_item_home, mDatas, this);
        listView.setAdapter(adapter);
        listView.setHasMoreItems(false);
        //设置statusbar
        mStatusBar=view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        mStatusBar.setAlpha(1);

        iv_scancode = view.findViewById(R.id.iv_scancode);

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
     * 添加头部
     */
    private void initHeaderView() {
        headerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_fragmenthome_header, null);
        v_banner = headerView.findViewById(R.id.v_banner);
       // fc_ads = headerView.findViewById(R.id.fc_ads);
        banner = headerView.findViewById(R.id.banner);
        setBanner();
        ry_daohang = headerView.findViewById(R.id.ry_daohang);
        viewpager_cate = headerView.findViewById(R.id.viewpager_cate);
        group = headerView.findViewById(R.id.points);
        ly_community_agreement = headerView.findViewById(R.id.ly_community_agreement);
        ly_property_notice = headerView.findViewById(R.id.ly_property_notice);
        ly_circle = headerView.findViewById(R.id.ly_circle);
        gridView_article = headerView.findViewById(R.id.gridView_article);
        listview_article = headerView.findViewById(R.id.listview_article);
        gridViewHouse = headerView.findViewById(R.id.gridview_House);
        gridViewGoods = headerView.findViewById(R.id.gridview_goods);
        ly_property_payment = headerView.findViewById(R.id.ly_property_payment);
        int height_property=(int)((DeviceUtils.getWindowWidth(mActivity)-DeviceUtils.dip2px(mActivity,30))*312*1f/1014);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height_property);
        ly_property_payment.setLayoutParams(params);
        ly_person_repair = headerView.findViewById(R.id.ly_person_repair);
        int height_repair=(int)((DeviceUtils.getWindowWidth(mActivity)-DeviceUtils.dip2px(mActivity,30))*312*1f/(506*2));
        ly_person_repair.setLayoutParams(new LinearLayout.LayoutParams((int)((DeviceUtils.getWindowWidth(mActivity)-DeviceUtils.dip2px(mActivity,30))*1f/2), height_repair));
        ly_public_repair = headerView.findViewById(R.id.ly_public_repair);
        ly_public_repair.setLayoutParams(new LinearLayout.LayoutParams((int)((DeviceUtils.getWindowWidth(mActivity)-DeviceUtils.dip2px(mActivity,30))*1f/2), height_repair));
        ly_circle_onclick = headerView.findViewById(R.id.ly_circle_onclick);
        ly_circle_more = headerView.findViewById(R.id.ly_circle_more);
        ly_houseRent = headerView.findViewById(R.id.ly_houseRent);
        ly_houseSell = headerView.findViewById(R.id.ly_houseSell);
        ly_houseCommit = headerView.findViewById(R.id.ly_houseCommit);
        //圈子信息
        iv_img = headerView.findViewById(R.id.iv_img);
        iv_head = headerView.findViewById(R.id.iv_head);
        tv_time = headerView.findViewById(R.id.tv_time);
        tv_title = headerView.findViewById(R.id.tv_title);
        tv_name = headerView.findViewById(R.id.tv_name);
        tv_content = headerView.findViewById(R.id.tv_content);
        tv_circleViews = headerView.findViewById(R.id.tv_circleViews);
        tv_circleReply = headerView.findViewById(R.id.tv_circleReply);
        //手册协议前两条
        article12GridAdapter = new HomeArticleGridAdapter(mArticle_list, getActivity());
        gridView_article.setAdapter(article12GridAdapter);
        //手册协议后两条
        articleAdapter = new HomeArticleListAdapter(mArticle_list, getActivity());
        listview_article.setAdapter(articleAdapter);
        //租售房
        mRentSellAdapter = new HouseRentSellAdapter(mActivity, R.layout.activity_index_rentsell_item, mHouses_list);
        gridViewHouse.setAdapter(mRentSellAdapter);
        //中部广告
        mAdcertlAdapter = new HomeAdcertlAdapter(mActivity, R.layout.activity_index_advert_item, mAd_Centerlist);
        gridViewGoods.setAdapter(mAdcertlAdapter);

        listView.addHeaderView(headerView);
        headerView.setVisibility(View.INVISIBLE);

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
                if (modelHome!=null&&modelHome.getAd_top_list() != null && modelHome.getAd_top_list().size() > 0) {
                    ModelAds ads=modelHome.getAd_top_list().get(position);
               if (TextUtils.isEmpty(ads.getUrl())) {
                    if (ads.getUrl_type().equals("0") || TextUtils.isEmpty(ads.getUrl_type())) {
                        jump = new Jump(getActivity(), ads.getType_name(), ads.getAdv_inside_url());
                    } else {
                        jump = new Jump(getActivity(), ads.getUrl_type(), ads.getType_name(), "", ads.getUrl_type_cn());
                    }
                } else {//URL不为空时外链
                    jump = new Jump(getActivity(), ads.getUrl());

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
    public void initIntentData() {
    }

    @Override
    public void initListener() {
        ly_property_payment.setOnClickListener(this);
        ly_person_repair.setOnClickListener(this);
        ly_public_repair.setOnClickListener(this);
        ly_circle_onclick.setOnClickListener(this);
        ly_houseRent.setOnClickListener(this);
        ly_houseSell.setOnClickListener(this);
        ly_houseCommit.setOnClickListener(this);
//        fc_ads.setListener(new FunctionAdvertise.OnClickAdsListener() {
//            @Override
//            public void OnClickAds(ModelAds ads) {
//                if (TextUtils.isEmpty(ads.getUrl())) {
//                    if (ads.getUrl_type().equals("0") || TextUtils.isEmpty(ads.getUrl_type())) {
//                        jump = new Jump(getActivity(), ads.getType_name(), ads.getAdv_inside_url());
//                    } else {
//                        jump = new Jump(getActivity(), ads.getUrl_type(), ads.getType_name(), "", ads.getUrl_type_cn());
//                    }
//                } else {//URL不为空时外链
//                    jump = new Jump(getActivity(), ads.getUrl());
//
//                }
//            }
//        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (NullUtil.isStringEmpty(mDatas.get((int) id).getInventory()) || 0 >= Integer.valueOf(mDatas.get((int) id).getInventory())) {
                    SmartToast.showInfo("商品已售罄");
                } else {
                    Intent intent = new Intent(mContext, ShopDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("shop_id", mDatas.get((int) id).getId());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
              //  view_alpha.setAlpha(0);
            //    mStatusBar.setAlpha(0);
                requestData();
            }
        });
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                requestData();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    int scollYHeight = -headerView.getTop();
                    if (scollYHeight < DeviceUtils.dip2px(mContext, 380)) {
                        if (!v_banner.isStarted() && mDatas_v_banner.size() > 0) {
                            v_banner.start();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                scroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
        ry_onclick.setOnClickListener(this);
        ly_circle_more.setOnClickListener(this);
        iv_scancode.setOnClickListener(this);
    }

    private void scroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (headerView != null) {
//            //设置其透明度
//            float alpha = 0;
//            //向上滑动的距离
//            int scollYHeight = -headerView.getTop();
//            if (scollYHeight >= DeviceUtils.dip2px(mActivity, 150) - DeviceUtils.dip2px(mActivity, 48)) {
//                alpha = 1;//滑上去就一直显示
//            } else {
//                alpha = scollYHeight / ((DeviceUtils.dip2px(mActivity, 150) - DeviceUtils.dip2px(mActivity, 48)) * 1.0f);
//            }
//            if (alpha < 0.6f) {
//                alpha = 0.6f;
//            }
//            view_alpha.setAlpha(alpha);
//            mStatusBar.setAlpha(alpha);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuName())){
            //1.小区名字不为空的情况
            tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName()+"");
            showDialog(smallDialog);
            requestData();
        }else {
            //2.小区名字为空的情况
            requestLocationPermission();
        }

    }

    /**
     * 请求
     */
    private void requestLocationPermission() {
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
                            //权限拒绝 ,默认智慧小区
                            prefrenceUtil.clearPreference(mActivity);
                            prefrenceUtil.setXiaoQuName("智慧小区");
                            //todo 不知道智慧小区是否需要id 登录的时候还有提交一下
                            prefrenceUtil.setXiaoQuId("66");
                            tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName()+"");
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
        //TODO 小区id 要判断
        params.put("c_id", prefrenceUtil.getXiaoQuId());

        MyOkHttp.get().post(ApiHttpClient.INDEX, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
             //   view_alpha.setAlpha(0.6f);
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                if (JsonUtil.getInstance().isSuccess(response)) {

                    ModelHome modelHome = (ModelHome) JsonUtil.getInstance().parseJsonFromResponse(response, ModelHome.class);
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
             //   view_alpha.setAlpha(0.6f);
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    private void getIndexData(ModelHome modelHome) {
        if (modelHome != null) {
            this.modelHome=modelHome;
            headerView.setVisibility(View.VISIBLE);
            //顶部广告
//            if (modelHome.getAd_top_list() != null && modelHome.getAd_top_list().size() > 0) {
//                fc_ads.setVisibility(View.VISIBLE);
//
//                fc_ads.getLayoutParams().width = ToolUtils.getScreenWidth(mActivity);
//                Double d = Double.valueOf(ToolUtils.getScreenWidth(mActivity) / 2.5);
//                fc_ads.getLayoutParams().height = (new Double(d)).intValue();
//
//                fc_ads.initAds(modelHome.getAd_top_list());
//
//            } else {
//                fc_ads.setVisibility(View.GONE);
//            }
            if (modelHome.getAd_top_list() != null && modelHome.getAd_top_list().size() > 0) {

                ArrayList<String> mDatas_img1 = new ArrayList<>();
                for (int i = 0; i < modelHome.getAd_top_list().size(); i++) {
                    mDatas_img1.add(ApiHttpClient.IMG_URL+modelHome.getAd_top_list().get(i).getImg() + "");
                }
                banner.update(mDatas_img1);
            }
            //导航
            if (modelHome.getMenu_list() != null && modelHome.getMenu_list().size() > 0) {
                ry_daohang.setVisibility(View.VISIBLE);
                mcatelist.clear();
                mcatelist.addAll(modelHome.getMenu_list());
                if (mcatelist.size() < 4) {
                    int p_height = StringUtils.dip2px(160);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ry_daohang.getLayoutParams();
                    params.height = p_height / 2;
                    ry_daohang.setLayoutParams(params);
                }
                getMenu_list();
            } else {
                ry_daohang.setVisibility(View.GONE);
            }
            //物业公告
            if (modelHome.getP_social_list() != null && modelHome.getP_social_list().size() > 0) {
                ly_property_notice.setVisibility(View.VISIBLE);
                mDatas_v_banner.clear();
                mDatas_v_banner.addAll(modelHome.getP_social_list());
                VBannerAdapter vBannerAdapter = new VBannerAdapter(mDatas_v_banner);
                if (!v_banner.isStarted()) {
                    v_banner.setAdapter(vBannerAdapter);
                    v_banner.start();
                } else {
                    v_banner.stop();
                    v_banner.start();
                }
            } else {
                ly_property_notice.setVisibility(View.GONE);

            }
            //手册协议
            if (modelHome.getArticle_list() != null && modelHome.getArticle_list().size() > 0) {
                ly_community_agreement.setVisibility(View.VISIBLE);
                mArticle_list.clear();
                mArticle_list.addAll(modelHome.getArticle_list());
                articleAdapter.notifyDataSetChanged();
                article12GridAdapter.notifyDataSetChanged();

            } else {
                ly_community_agreement.setVisibility(View.GONE);
            }
            //圈子 只有一条数据
            if (modelHome.getSocial_list() != null) {
                ly_circle.setVisibility(View.VISIBLE);
                mSocial = modelHome.getSocial_list();
                byte[] bytes1 = Base64.decode(modelHome.getSocial_list().getTitle(), Base64.DEFAULT);
                tv_title.setText(new String(bytes1));
                tv_name.setText(modelHome.getSocial_list().getNickname() + "   发布于  " + modelHome.getSocial_list().getC_name());
                tv_time.setText(modelHome.getSocial_list().getAddtime());
                byte[] bytes2 = Base64.decode(modelHome.getSocial_list().getContent(), Base64.DEFAULT);
                tv_content.setText(new String(bytes2));
                tv_circleViews.setText(modelHome.getSocial_list().getClick());
                tv_circleReply.setText(modelHome.getSocial_list().getReply_num());
                if (modelHome.getSocial_list().getImg_list() != null && modelHome.getSocial_list().getImg_list().size() > 0) {
                    if (!TextUtils.isEmpty(modelHome.getSocial_list().getImg_list().get(0).getImg())) {
                        GlideUtils.getInstance().glideLoad(mActivity, ApiHttpClient.IMG_URL + modelHome.getSocial_list().getImg_list().get(0).getImg(),
                                iv_img, R.drawable.ic_default_rectange);
                        iv_img.setVisibility(View.VISIBLE);
                    } else {
                        iv_img.setVisibility(View.GONE);
                    }
                } else {
                    iv_img.setVisibility(View.GONE);
                }
                GlideUtils.getInstance().glideLoad(mActivity, ApiHttpClient.IMG_URL + modelHome.getSocial_list().getAvatars(),
                        iv_head, R.drawable.ic_default_head);

            } else {
                ly_circle.setVisibility(View.GONE);
            }
            //租售房信息
            if (modelHome.getHouses_list() != null && modelHome.getHouses_list().size() > 0) {
                gridViewHouse.setVisibility(View.VISIBLE);
                mHouses_list.clear();
                mHouses_list.addAll(modelHome.getHouses_list());
                mRentSellAdapter.notifyDataSetChanged();
            } else {
                gridViewHouse.setVisibility(View.GONE);
            }
            //中部广告
            if (modelHome.getAd_center_list() != null && modelHome.getAd_center_list().size() > 0) {
                gridViewGoods.setVisibility(View.VISIBLE);
                mAd_Centerlist.clear();
                mAd_Centerlist.addAll(modelHome.getAd_center_list());
                mAdcertlAdapter.notifyDataSetChanged();
            } else {
                gridViewGoods.setVisibility(View.GONE);
            }
            //底部商品信息
            if (modelHome.getPro_list() != null && modelHome.getPro_list().size() > 0) {
                mDatas.clear();
                mDatas.addAll(modelHome.getPro_list());
                listView.setHasMoreItems(false);

            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        SharedPreferences preferences1 = mActivity.getSharedPreferences("login", 0);
        String is_wuye = preferences1.getString("is_wuye", "");
        switch (v.getId()) {
            case R.id.ly_property_payment://生活缴费
                if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
                    intent = new Intent(mActivity, PropertyNewActivity.class);
                    intent.putExtra("wuye_type", "property");
                    startActivity(intent);
                } else {
                    intent = new Intent(mActivity, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ly_person_repair://家用报修
                if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
                    showDialog(smallDialog);//判断是否物业绑定
                    mPrester.getProperty("person");

                } else {
                    intent = new Intent(mActivity, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ly_public_repair://公共报修
                if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
                    showDialog(smallDialog);//判断是否物业绑定
                    mPrester.getProperty("public");
                } else {
                    intent = new Intent(mActivity, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ly_circle_onclick://圈子详情
                intent = new Intent(mActivity, CircleDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", mSocial.getId());
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.ly_houseRent://租房
                intent = new Intent(mActivity, HouseRentListActivity.class);
                intent.putExtra("jump_type", 1);
                startActivity(intent);
                break;
            case R.id.ly_houseSell://售房
                intent = new Intent(mActivity, HouseRentListActivity.class);
                intent.putExtra("jump_type", 2);
                startActivity(intent);
                break;
            case R.id.ly_houseCommit://发布房源
                if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
                    intent = new Intent(mActivity, RentSellCommissionActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(mActivity, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ry_onclick://切换小区
//                Intent intent1 = new Intent(getActivity(), XiaoquActivity.class);
//                intent1.putExtra("type", "home");
//                startActivityForResult(intent1, 3);
                Intent intent1 = new Intent(getActivity(), CommunityListActivity.class);
                startActivityForResult(intent1, 111);
                break;
            case R.id.ly_circle_more://点击查看更多
                ModelEventHome modelEventHome = new ModelEventHome();
                modelEventHome.setType(1);
                EventBus.getDefault().post(modelEventHome);
                break;
            case R.id.iv_scancode://扫二维码
                if (LoginUtils.hasLoginUser()){
                    scanCode();
                }else {
                    intent = new Intent(mActivity, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                }
                break;
        }

    }

    /**
     * 扫二维码
     */
    private void scanCode() {
        new RxPermissions(mActivity).request( Manifest.permission.CAMERA)
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

    //导航
    private void getMenu_list() {
        //总的页数向上取整
        //catePage=Integer.valueOf(catebeans.get(0).getTotal_Pages());
        catePage = (int) Math.ceil(mcatelist.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<View>();
        for (int i = 0; i < catePage; i++) {
            //每个页面都是inflate出一个新实例
            final GridView gridView = (GridView) View.inflate(mActivity, R.layout.item_gridview, null);
            gridView.setAdapter(new MyGridViewAdpter(mActivity, mcatelist, i, mPageSize));

            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        viewpager_cate.setAdapter(new MyViewPagerAdapter(viewPagerList));

        //添加小圆点
        if (catePage == 1) {
         group.setVisibility(View.GONE);
//            // 测试
//            ivPoints = new ImageView[2];
//
//            group.removeAllViews();
//            for (int i = 0; i < 2; i++) {
//                //循坏加入点点图片组
//                ivPoints[i] = new ImageView(mActivity);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DeviceUtils.dip2px(mActivity, 6), DeviceUtils.dip2px(mActivity, 6));
//                params.setMargins(0,0,DeviceUtils.dip2px(mActivity, 5),0);
//                ivPoints[i].setLayoutParams(params);
//                if (i == 0) {
//                    ivPoints[i].setImageResource(R.drawable.allshape_oval_orange);
//                } else {
//                    ivPoints[i].setImageResource(R.drawable.shape_oval_grey);
//                }
//               // ivPoints[i].setPadding(8, 8, 8, 8);
//
//                group.addView(ivPoints[i]);
//            }

        } else {
            group.setVisibility(View.VISIBLE);
            ivPoints = new ImageView[catePage];
            group.removeAllViews();
            for (int i = 0; i < catePage; i++) {
                //循坏加入点点图片组
                ivPoints[i] = new ImageView(mActivity);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DeviceUtils.dip2px(mActivity, 6), DeviceUtils.dip2px(mActivity, 6));
                params.setMargins(0,0,DeviceUtils.dip2px(mActivity, 5),0);
                ivPoints[i].setLayoutParams(params);
                if (i == 0) {
                    ivPoints[i].setImageResource(R.drawable.allshape_oval_orange);
                } else {
                    ivPoints[i].setImageResource(R.drawable.shape_oval_grey);
                }
               // ivPoints[i].setPadding(8, 8, 8, 8);
                group.addView(ivPoints[i]);
            }
        }
        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
        viewpager_cate.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //currentPage = position;
                for (int i = 0; i < catePage; i++) {
                    if (i == position) {
                        ivPoints[i].setImageResource(R.drawable.allshape_oval_orange);
                    } else {
                        ivPoints[i].setImageResource(R.drawable.shape_oval_grey);
                    }
                }
            }
        });
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

    @Override
    public void onPause() {
        super.onPause();
      //  fc_ads.stopAutoCycle();

        //结束轮播
        if (banner!=null){
            banner.stopAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (!fc_ads.isCycling()) {
////			fc_ads.moveToFirstPosition(true);
//            fc_ads.startCycle();
//        }
        if (banner!=null){
            //开始轮播
            banner.startAutoPlay();
        }

    }

    /**
     * 点击加入购物车
     *
     * @param item
     */
    @Override
    public void onAddCartClick(ModelShopIndex item) {
        if (item != null) {
            if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
                if (NullUtil.isStringEmpty(item.getInventory()) || 0 >= Integer.valueOf(item.getInventory())) {
                    SmartToast.showInfo("商品已售罄");
                } else {
                    new CommonMethod(item, mContext).getShopLimitTag();
                }
            } else {
                Intent intent = new Intent(mActivity, LoginVerifyCodeActivity.class);
                startActivity(intent);
            }
        }
    }

    /**
     * 添加和删除评论的Eventbus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateItem(CircleDetailBean mCirclebean) {
        try {
            if (mCirclebean != null) {
                int type = mCirclebean.getType();
                if (type == 0) {
                    //添加评论
                    if (mCirclebean.getId().equals(mSocial.getId())) {
                        int i1 = Integer.parseInt(mCirclebean.getReply_num());
                        mSocial.setReply_num((i1 + 1) + "");
                        tv_circleReply.setText(mSocial.getReply_num());
                    }
                } else if (type == 1) {
                    //删除评论
                    if (mCirclebean.getId().equals(mSocial.getId())) {
                        int i1 = Integer.parseInt(mCirclebean.getReply_num());
                        mSocial.setReply_num((i1 - 1) + "");
                        tv_circleReply.setText(mSocial.getReply_num());
                    }
                } else if (type == 2) {
                    //阅读数
                    if (mCirclebean.getId().equals(mSocial.getId())) {
                        tv_circleViews.setText(mCirclebean.getClick());

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断物业是否绑定
     *
     * @param status
     * @param bean
     * @param msg
     */
    @Override
    public void onProperty(int status, ModelLogin bean, String jump_type, String msg) {
        hideDialog(smallDialog);
        if (status == 1) {
            if (bean != null) {
                if (bean.getIs_bind_property().equals("2")) {
                    if (jump_type.equals("person")) {//家用报修
                        Intent intent = new Intent(mActivity, PersonalWorkOrderCommitActivity.class);
                        startActivity(intent);
                    } else {//公共报修
                        Intent intent = new Intent(mActivity, PublicWorkOrderCommitActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(mActivity, PropertyBindHomeActivity.class);
                    if (jump_type.equals("person")) {
                        intent.putExtra("wuye_type", "person_repair");
                    } else {
                        intent.putExtra("wuye_type", "public_repair");
                    }
                    startActivity(intent);
                }
            }
        } else {
            SmartToast.showInfo(msg);
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
                        getPOIsearch(location.getLongitude(),location.getLatitude());

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
                //todo 不知道智慧小区是否需要id 登录的时候还有提交一下
                prefrenceUtil.setXiaoQuId("66");
                tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName()+"");
                showDialog(smallDialog);
                requestData();
            }

        } else {
            hideDialog(smallDialog);
            // tvResult.setText("定位失败，loc is null");
            //定位失败 显示智慧小区
            prefrenceUtil.clearPreference(mActivity);
            prefrenceUtil.setXiaoQuName("智慧小区");
            //todo 不知道智慧小区是否需要id 登录的时候还有提交一下
            prefrenceUtil.setXiaoQuId("66");
            tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName()+"");
            showDialog(smallDialog);
            requestData();
        }
    }

    /**
     * 调用高德地图搜索周边住宅
     * @param longitude
     * @param latitude
     */
    private void getPOIsearch(double longitude, double latitude) {
        PoiSearch.Query query = new PoiSearch.Query("", "住宅区", "");
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

        if (pois!=null&&pois.size()>0){

            String community_name=pois.get(0).toString();
            String address = pois.get(0).getSnippet();
            prefrenceUtil.clearPreference(mActivity);
            //todo 选择上了小区
            prefrenceUtil.setXiaoQuName(community_name);
            prefrenceUtil.setAddressName(address);
            //todo 这里暂时使用智慧小区的小区id 到时候肯定没有小区id
            prefrenceUtil.setXiaoQuId("66");
            tv_xiaoqu.setText(prefrenceUtil.getXiaoQuName()+"");
            showDialog(smallDialog);
            smallDialog.setTipTextView("加载中...");
            requestData();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


//    /**
//     * 初始化头布局
//     */
//    private void initHeader() {
//        //这个布局只能是固定的布局，所以别在这里操作
//        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
//        headerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_fragmenthome_header, null);
//        headerView.setVisibility(View.VISIBLE);
//
//        // 初始化头部布局
//        mHeaderAndFooterWrapper.addHeaderView(headerView);
//       // mHeaderAndFooterWrapper.addHeaderView(t2);
//    }

//    /**
//     * 初始化加载更多
//     */
//    private void initLoadMoreFooter() {
//        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
//        //
//      //  mLoadMoreWrapper.setLoadMoreView(R.layout.layout_refresh_footer);
//        setEnableLoadMore(false);
//        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener()
//        {
//            @Override
//            public void onLoadMoreRequested() {
//                //加载更多
//                // 测试
//                requestData();
//            }
//        });
//    }

//    /**
//     * 设置是否可以加载更多
//     * @param canLoadMore
//     */
//    private void setEnableLoadMore(boolean canLoadMore) {
//        if (mLoadMoreWrapper!=null){
//            if (canLoadMore){
//                mLoadMoreWrapper.setLoadMoreView(R.layout.layout_refresh_footer);
//            }else {
//                mLoadMoreWrapper.setLoadMoreView(0);
//            }
//        }
//
//    }

}
