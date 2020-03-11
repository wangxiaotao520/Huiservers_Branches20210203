package com.huacheng.huiservers.ui.fragment.shop;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.ui.fragment.adapter.AdapterShopIndexGridCate;
import com.huacheng.huiservers.ui.fragment.shop.adapter.ShopFragmentStoreAdapter;
import com.huacheng.huiservers.utils.MyCornerImageLoader;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.widget.GridViewNoScroll;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 商城首页商城
 * created by wangxiaotao
 * 2019/12/11 0011 上午 10:19
 */
public class ShopFragmentStore extends ShopFragmentCommonImp{
    public SmartRefreshLayout refreshLayout;
    private PagingListView listView;
    private List<String> mDatas = new ArrayList<>();
    private List<ModelShopIndex> mDatasCate = new ArrayList<>();
    private ShopFragmentStoreAdapter shopFragmentStoreAdapter;
    private View headerView;
    private GridViewNoScroll gridview_shop;
    private LinearLayout ll_youxuan_container;
    private LinearLayout ll_youxuan_img_root;
    private AdapterShopIndexGridCate adapterShopIndexGridCate;
    private Banner banner;
    private MyCornerImageLoader myImageLoader;

    @Override
    public void initView(View view) {
        refreshLayout=view.findViewById(R.id.refreshLayout);
        // 一开始先不要让加载更多,防止网络错误时，加载更多报错
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        listView=view.findViewById(R.id.listview);
        initHeaderView(view);

        shopFragmentStoreAdapter = new ShopFragmentStoreAdapter(mContext, R.layout.item_shop_fragment_store, mDatas);
        listView.setAdapter(shopFragmentStoreAdapter);
        setEnableLoadMore(false);

    }

    /**
     * 初始化头布局
     * @param view
     */
    private void initHeaderView(View view) {
        headerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_fragmentshop_header1, null);
        gridview_shop = headerView.findViewById(R.id.gridview_shop);
        banner = headerView.findViewById(R.id.banner);
        setBanner();
        ll_youxuan_container = headerView.findViewById(R.id.ll_youxuan_container);
        ll_youxuan_img_root = headerView.findViewById(R.id.ll_youxuan_img_root);
        listView.addHeaderView(headerView);
        headerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {


        headerView.setVisibility(View.VISIBLE);

        //分类
        gridview_shop.setVisibility(View.VISIBLE);
        adapterShopIndexGridCate = new AdapterShopIndexGridCate(mContext, R.layout.item_shop_cate_new, mDatasCate);
        gridview_shop.setAdapter(adapterShopIndexGridCate);
        for (int i = 0; i < 10; i++) {
            mDatasCate.add(new ModelShopIndex());
        }
        adapterShopIndexGridCate.notifyDataSetChanged();
        //
        //banner
        ArrayList<String> mDatas_img = new ArrayList<>();
        mDatas_img.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        mDatas_img.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        mDatas_img.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        mDatas_img.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        mDatas_img.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        banner.update(mDatas_img);

        //优选
        ll_youxuan_container.setVisibility(View.VISIBLE);
        ll_youxuan_img_root.setVisibility(View.VISIBLE);
        for (int i = 0; i < 7; i++) {
            View item_img_view = LayoutInflater.from(mContext).inflate(R.layout.item_item_shop_goods, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,0, DeviceUtils.dip2px(mContext,10),0);
            item_img_view.setLayoutParams(params);
            ll_youxuan_img_root.addView(item_img_view);
        }
        //商品
        for (int i = 0; i <15 ; i++) {
            mDatas.add("");
        }
        shopFragmentStoreAdapter.notifyDataSetChanged();
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
        return R.layout.fragment_shop_store_index;
    }

    private void setEnableLoadMore(boolean isloadmore) {
        if (isloadmore){
            listView.setHasMoreItems(true);
        }else {
            listView.setHasMoreItems(false);
        }
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
