package com.huacheng.huiservers.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
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
import com.huacheng.huiservers.sharesdk.PopupWindowShare;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.shop.adapter.ShopCommonAdapter;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.utils.CommonMethod;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.linkme.LinkedMeUtils;
import com.microquation.linkedme.android.log.LMErrorCode;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：店铺首页
 * 时间：2019/11/8 10:59
 * created by DFF
 */
public class StoreIndexActivity extends BaseActivity implements ShopCommonAdapter.OnClickCallback, View.OnClickListener {
    View mStatusBar;
    private View headerView;
    protected PagingListView listView;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    private SimpleDraweeView iv_store_head;
    private TextView tv_store_name;
    private TextView tv_store_address;
    private ImageView iv_bg;
    private List<ModelShopIndex> mDatas = new ArrayList<>();//数据
    private ShopCommonAdapter<ModelShopIndex> adapter;
    private int page = 1;
    private String store_id = "";
    private LinearLayout ly_all;
    private LinearLayout ly_serch;
    private LinearLayout ly_scroll;
    private LinearLayout lin_left;
    private LinearLayout lin_share;
    private String share_url;
    private String share_title;
    private String share_desc;
    private String share_icon;
    private ModelShopIndex modelShopIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        listView = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);

        ly_serch = findViewById(R.id.ly_serch);
        ly_scroll = findViewById(R.id.ly_scroll);
        lin_left = findViewById(R.id.lin_left);
        lin_share = findViewById(R.id.lin_share);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        headerView = LayoutInflater.from(this).inflate(R.layout.header_store_index, null);
        initHeaderView();
        listView.addHeaderView(headerView);
        adapter = new ShopCommonAdapter<>(this, mDatas, this);
        listView.setAdapter(adapter);
        listView.setHasMoreItems(false);

        ly_scroll.setAlpha(0);
        //状态栏
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha((float) 0);
    }

    private void initHeaderView() {
        iv_store_head = headerView.findViewById(R.id.iv_store_head);
        tv_store_name = headerView.findViewById(R.id.tv_store_name);
        tv_store_address = headerView.findViewById(R.id.tv_store_address);
        ly_all = headerView.findViewById(R.id.ly_all);
        iv_bg = headerView.findViewById(R.id.iv_bg);

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

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                scroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
        ly_serch.setOnClickListener(this);
        lin_left.setOnClickListener(this);
        lin_share.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_index;
    }

    @Override
    protected void initIntentData() {
        //store_info = (ShopDetailBean) this.getIntent().getSerializableExtra("store_info");
        store_id = this.getIntent().getStringExtra("store_id");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    private void scroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (headerView != null) {
            //设置其透明度
            float alpha = 0;
            //向上滑动的距离
            int scollYHeight = -headerView.getTop();
            if (scollYHeight >= DeviceUtils.dip2px(this, 100)) {
                alpha = 1;//滑上去就一直显示
            } else {
                alpha = scollYHeight / ((DeviceUtils.dip2px(this, 100)) * 1.0f);
            }
            if (alpha <= 0) {
                alpha = 0;
            }
            ly_scroll.setBackgroundColor(getResources().getColor(R.color.white));
            mStatusBar.setAlpha(alpha);
            ly_scroll.setAlpha(alpha);
        }
    }

    /**
     * 请求数据
     */
    private void requestData() {
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        params.put("id", store_id + "");
        params.put("p", page + "");

        MyOkHttp.get().get(ApiHttpClient.SHOP_IMERCHANT_DETAILS, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                listView.setIsLoading(false);
                lin_share.setClickable(true);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelShopIndex shopIndex = (ModelShopIndex) JsonUtil.getInstance().parseJsonFromResponse(response, ModelShopIndex.class);
                    // List<ModelShopIndex> shopIndexList = (List<ModelShopIndex>) JsonUtil.getInstance().getDataArrayByName(response, "data", ModelShopIndex.class);
                    if (shopIndex != null) {
                        modelShopIndex = shopIndex;
                        if (page == 1) {
                            iv_store_head.setVisibility(View.VISIBLE);
                            ly_all.setVisibility(View.VISIBLE);
                            tv_store_address.setText(shopIndex.getAddress());
                            tv_store_name.setText(shopIndex.getMerchant_name());
                            GlideUtils.getInstance().glideLoad(StoreIndexActivity.this,ApiHttpClient.IMG_URL+shopIndex.getBackground(),iv_bg,R.mipmap.ic_store_bg);
                            FrescoUtils.getInstance().setImageUri(iv_store_head, ApiHttpClient.IMG_URL + shopIndex.getLogo());
                        }
                        inflateContent(shopIndex.getGoods());

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
                lin_share.setClickable(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    mRefreshLayout.setEnableLoadMore(false);
                }
            }
        });
    }

    /**
     * 填充数据
     *
     * @param
     */
    private void inflateContent(List<ModelShopIndex> shopIndexList) {
        if (shopIndexList != null && shopIndexList.size() > 0) {
            mRelNoData.setVisibility(View.GONE);
            List<ModelShopIndex> list_new = shopIndexList;
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
                mRelNoData.setVisibility(View.VISIBLE);
                mDatas.clear();
            }
            listView.setHasMoreItems(false);
            adapter.notifyDataSetChanged();
        }

    }

    /**
     * 购物车点击
     *
     * @param position
     */
    @Override
    public void onClickShopCart(int position) {
        //点击购物车
        if (ApiHttpClient.TOKEN == null || ApiHttpClient.TOKEN_SECRET == null) {
            Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
            mContext.startActivity(intent);

        } else {

            if (mDatas.get(position).getExist_hours().equals("2")) {
                SmartToast.showInfo("当前时间不在派送时间范围内");
            } else {
                if (mDatas.get(position) != null) {
                    new CommonMethod(mDatas.get(position), null, mContext).getShopLimitTag();
                }
            }

        }

    }

    /**
     * 点击商品
     *
     * @param position
     */
    @Override
    public void onClickImage(int position) {
        //点击图片
        Intent intent = new Intent(mContext, ShopDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("shop_id", mDatas.get(position).getId());
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ly_serch://搜索
                intent.setClass(this, SearchShopActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("store_id", store_id);
                startActivity(intent);
                break;
            case R.id.lin_left://返回
                finish();
                break;
            case R.id.lin_share://分享
                if (NullUtil.isStringEmpty(modelShopIndex.getId())) {
                    return;
                }
                share_title = modelShopIndex.getMerchant_name() + "";
                share_desc = modelShopIndex.getAddress() + "";
                share_icon = MyCookieStore.URL + modelShopIndex.getLogo();
                share_url = ApiHttpClient.API_URL_SHARE + ApiHttpClient.API_VERSION + "shop/shops/id/" + modelShopIndex.getId();
                HashMap<String, String> params = new HashMap<>();
                params.put("type", "store_details");
                params.put("id", modelShopIndex.getId());
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
        popup.showBottom(lin_share);
    }

}
