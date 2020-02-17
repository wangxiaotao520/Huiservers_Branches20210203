package com.huacheng.huiservers.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.model.ModelVBaner;
import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.adapter.HomeListViewAdapter;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.shop.adapter.VBannerZQAdapter;
import com.huacheng.huiservers.utils.CommonMethod;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.linkme.LinkedMeUtils;
import com.huacheng.libraryservice.widget.verticalbannerview.VerticalBannerView;
import com.microquation.linkedme.android.log.LMErrorCode;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：商品专区列表（首页）
 * 时间：2019/11/13 15:30
 * created by DFF
 */
public class ShopZQListActivity extends BaseActivity implements View.OnClickListener, HomeListViewAdapter.OnAddCartClickListener {
    View mStatusBar;
    private View headerView;
    protected PagingListView listView;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    private SimpleDraweeView iv_store_head;
    private TextView tv_store_name;
    private TextView tv_store_address;
    private List<ModelShopIndex> mDatas = new ArrayList<>();//数据
    private SharePrefrenceUtil prefrenceUtil;
    private HomeListViewAdapter adapter;
    private int page = 1;
    // private ImageView iv_bg;
    private SimpleDraweeView sdv_bg;
    private LinearLayout ly_serch;
    private LinearLayout ly_zq;
    private LinearLayout ly_scroll;
    private LinearLayout lin_left;
    private LinearLayout ly_share;
    private RelativeLayout ry_all;
    private String share_url;
    private String share_title;
    private String share_desc;
    private String share_icon;
    private String id = "";
    ModelShopIndex modelIndex;
    private VerticalBannerView v_banner;
    private ModelShopIndex modelindex;
    private VBannerZQAdapter vBannerAdapter;
    private List<ModelVBaner> mDatas_v_banner = new ArrayList<>();//垂直banner数据公告
    String title = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        prefrenceUtil = new SharePrefrenceUtil(this);
        listView = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);

        ly_serch = findViewById(R.id.ly_serch);
        ly_scroll = findViewById(R.id.ly_scroll);
        lin_left = findViewById(R.id.lin_left);
        ly_share = findViewById(R.id.ly_share);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        headerView = LayoutInflater.from(this).inflate(R.layout.header_zq_index, null);
        initHeaderView();
        listView.addHeaderView(headerView);
        // adapter = new HomeListViewAdapter(this, R.layout.shop_list_item_home, mDatas, this);
        adapter = new HomeListViewAdapter(this, R.layout.item_home_sec_kill, mDatas, this);
        listView.setAdapter(adapter);
        listView.setHasMoreItems(false);

        /*ly_scroll.setAlpha(0);*/
        //状态栏
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha((float) 0);

        titleName=findViewById(R.id.titleName);
        if (NullUtil.isStringEmpty(title)){
            titleName.setText("");
        }else {
            titleName.setText(title+"");
        }
    }

    private void initHeaderView() {
        ly_zq = headerView.findViewById(R.id.ly_zq);
        v_banner = headerView.findViewById(R.id.v_banner);
        ry_all = headerView.findViewById(R.id.ry_all);
        sdv_bg = headerView.findViewById(R.id.sdv_bg);//根据比例来显示
//        iv_bg = headerView.findViewById(R.id.iv_bg);//根据比例来显示
//        final int gridWidth = DeviceUtils.getWindowWidth(ShopZQListActivity.this);
//        int nWidth = gridWidth;
//        int nHeight = (int) (1.5 * nWidth);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, nHeight);
//        iv_bg.setLayoutParams(layoutParams);
//        iv_bg.setScaleType(ImageView.ScaleType.FIT_XY);
//        iv_bg.setImageResource(R.color.windowbackground);

    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
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
                    if (scollYHeight < DeviceUtils.dip2px(mContext, 400)) {
                        if (!v_banner.isStarted() && mDatas_v_banner.size() > 0) {
                            v_banner.start();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        ly_serch.setOnClickListener(this);
        lin_left.setOnClickListener(this);
        ly_share.setOnClickListener(this);
        ly_zq.setOnClickListener(this);
        //iv_bg.setOnClickListener(this);
        sdv_bg.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id<0){
                    return;
                }
                Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
                Bundle bundle = new Bundle();
                bundle.putString("shop_id", mDatas.get((int) id).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_zq_index;
    }

    @Override
    protected void initIntentData() {
        id = this.getIntent().getStringExtra("id");
        title = this.getIntent().getStringExtra("title");

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    /**
     * 请求数据
     */
    private void requestData() {
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("p", page + "");
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())) {
            params.put("province_cn", prefrenceUtil.getProvince_cn());
            params.put("city_cn", prefrenceUtil.getCity_cn());
            params.put("region_cn", prefrenceUtil.getRegion_cn());
        }
        MyOkHttp.get().get(ApiHttpClient.SHOP_MARKIING_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                listView.setIsLoading(false);
                ly_share.setClickable(true);
                if (JsonUtil.getInstance().isSuccess(response)) {

                    modelindex = (ModelShopIndex) JsonUtil.getInstance().parseJsonFromResponse(response, ModelShopIndex.class);
                    if (modelindex != null) {
                        modelIndex = modelindex;
                        titleName.setText(modelindex.getTitle()+"");
                        if (page == 1) {
                            String imageUrl = ApiHttpClient.IMG_SERVICE_URL + modelindex.getBanner();
                            FrescoUtils.getInstance().setImageUri(sdv_bg, imageUrl);
                            setBanner(modelindex);
                            if (Integer.valueOf(modelindex.getIs_article()) > 0) {//显示专区活动栏
                                ly_zq.setVisibility(View.VISIBLE);
                                mDatas_v_banner.clear();
                                mDatas_v_banner.addAll(modelindex.getArticle_one());
                                if (!v_banner.isStarted()) {
                                    vBannerAdapter = new VBannerZQAdapter(mDatas_v_banner);
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
                                ly_zq.setVisibility(View.GONE);
                            }
                        }
                        inflateContent(modelindex);
                    }

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                listView.setHasMoreItems(false);
                listView.setIsLoading(false);
                ly_share.setClickable(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    mRefreshLayout.setEnableLoadMore(false);
                }
            }
        });
    }

    private void setBanner(ModelShopIndex modelindex) {
      /*  String imageUrl = ApiHttpClient.IMG_SERVICE_URL + modelindex.getBanner();
        final int gridWidth = DeviceUtils.getWindowWidth(ShopZQListActivity.this);
        Glide.with(getApplicationContext()).load(imageUrl).placeholder(R.drawable.ic_default_rectange).error(R.drawable.ic_default_rectange).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                int width = resource.getIntrinsicWidth();
                int height = resource.getIntrinsicHeight();
                int nWidth = gridWidth;
                int nHeight = (int) (2 * nWidth);
                float scale = (float) height / width;
                if (scale < 2) {
                    nHeight = (int) (scale * nWidth);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, nHeight);
                //layoutParams.setMargins(0, 0, 0, 15);;
                iv_bg.setLayoutParams(layoutParams);
                iv_bg.setScaleType(ImageView.ScaleType.FIT_XY);
                iv_bg.setImageDrawable(resource);
            }
        });*/
    }

    /**
     * 填充数据
     *
     * @param
     */
    private void inflateContent(ModelShopIndex modelindex) {
        ry_all.setVisibility(View.VISIBLE);
        if (modelindex.getPro_list() != null && modelindex.getPro_list().size() > 0) {
            mRelNoData.setVisibility(View.GONE);
            List<ModelShopIndex> list_new = modelindex.getPro_list();
            if (page == 1) {
                mDatas.clear();
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setSelection(0);
                    }
                });
            }
            mDatas.addAll(list_new);
            page++;

            if (page > Integer.parseInt(mDatas.get(0).getTotal_Pages())) {
                listView.setHasMoreItems(false);
            } else {
                listView.setHasMoreItems(true);
            }
            adapter.notifyDataSetChanged();

        } else {
            if (page == 1) {
                // 占位图显示出来
               // mRelNoData.setVisibility(View.VISIBLE);
                mDatas.clear();
            }
            listView.setHasMoreItems(false);
            adapter.notifyDataSetChanged();
        }

    }

    /**
     * 购物车点击
     *
     * @param item
     */
    @Override
    public void onAddCartClick(ModelShopIndex item, int position) {
        if (item != null) {
            if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
                if (NullUtil.isStringEmpty(item.getInventory()) || 0 >= Integer.valueOf(item.getInventory())) {
                    SmartToast.showInfo("商品已售罄");
                } else {
                    new CommonMethod(item, mContext).getShopLimitTag();
                }
            } else {
                Intent intent = new Intent(this, LoginVerifyCodeActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ly_serch://搜索
                intent.setClass(this, SearchShopActivity.class);
                intent.putExtra("type", 2);
                intent.putExtra("act_id", id);
                startActivity(intent);
                break;
            case R.id.ly_zq://专区活动
                intent.setClass(this, ShopZqHuodongActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.lin_left://返回
                finish();
                break;
            case R.id.ly_share://分享
                if (modelIndex == null) {
                    return;
                }
                if (NullUtil.isStringEmpty(id)) {
                    return;
                }
                share_title = modelIndex.getTitle() + "";
                share_desc = modelIndex.getContent() + "";
                share_icon = MyCookieStore.URL + modelIndex.getBanner();
                share_url = ApiHttpClient.API_URL_SHARE + ApiHttpClient.API_VERSION + "shop/special/id/" + id+ "/hui_community_id/" +prefrenceUtil.getXiaoQuId() ;
                HashMap<String, String> params = new HashMap<>();
                params.put("type", "prefecture_list");
                params.put("id", id);
                showDialog(smallDialog);
                LinkedMeUtils.getInstance().getLinkedUrl(this, share_url, share_title, params, new LinkedMeUtils.OnGetLinkedmeUrlListener() {
                    @Override
                    public void onGetUrl(String url, LMErrorCode error) {
                        hideDialog(smallDialog);
                        if (error == null) {
                            String share_url_new = share_url + "?linkedme=" + url;
                            showSharePop(share_title, share_desc, share_icon, share_url_new);
                        } else {
                            //可以看报错
                            String share_url_new = share_url + "?linkedme=" + "";
                            showSharePop(share_title, share_desc, share_icon, share_url_new);
                        }
                    }
                });


                break;
            case R.id.sdv_bg://背景点击
                intent.setClass(this, ShopZQWebActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("sub_type", "1");
                startActivity(intent);
                break;
        }

    }

    /**
     * 显示分享弹窗
     *
     * @param share_title
     * @param share_desc
     * @param share_icon
     * @param share_url_new
     */
    private void showSharePop(String share_title, String share_desc, String share_icon, String share_url_new) {
        PopupWindowShare popup = new PopupWindowShare(this, share_title, share_desc, share_icon, share_url_new, AppConstant.SHARE_COMMON);
        popup.showBottom(ly_share);
    }
}
