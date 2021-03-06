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

import com.bumptech.glide.Glide;
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
import com.huacheng.huiservers.ui.center.presenter.CollectPresenter;
import com.huacheng.huiservers.ui.fragment.adapter.HomeIndexGoodsCommonAdapter;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.utils.LoginUtils;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.AppConstant;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
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

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 类描述：店铺首页
 * 时间：2019/11/8 10:59
 * created by DFF
 */
public class StoreIndexActivity extends BaseActivity implements HomeIndexGoodsCommonAdapter.OnClickCallback, View.OnClickListener ,CollectPresenter.CollectListener{
    View mStatusBar;
    private View headerView;
    protected PagingListView listView;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    private SimpleDraweeView iv_store_head;
    private TextView tv_store_name;
    private TextView tv_tag_kangyang;
    private TextView tv_store_address;
    private ImageView iv_bg;
    private List<ModelShopIndex> mDatas = new ArrayList<>();//数据
    private HomeIndexGoodsCommonAdapter adapter;
    private int page = 1;
    private String store_id = "";
    private LinearLayout ly_all;
    private LinearLayout ly_serch;
    private LinearLayout lin_left;
    private LinearLayout ly_share;
    private ImageView iv_share;
    private ImageView iv_left;
    private ImageView iv_serch;
    private RelativeLayout ry_title;
    private TextView titleName;
    private TextView tv_follow;
    private String share_url;
    private String share_title;
    private String share_desc;
    private String share_icon;
    private ModelShopIndex modelShopIndex;
    private CollectPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mPresenter=new CollectPresenter(this,this);
        listView = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);


        ry_title = findViewById(R.id.ry_title);
        ly_serch = findViewById(R.id.ly_serch);
        iv_serch = findViewById(R.id.iv_serch);
        lin_left = findViewById(R.id.lin_left);
        iv_left = findViewById(R.id.iv_left);
        titleName = findViewById(R.id.titleName);

        ly_share = findViewById(R.id.ly_share);
        iv_share = findViewById(R.id.iv_share);
        iv_left.setBackgroundResource(R.mipmap.ic_arrow_left_white);
        ry_title.setBackground(null);
        iv_share.setBackgroundResource(R.mipmap.ic_share_white);
        iv_serch.setBackgroundResource(R.color.white);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        headerView = LayoutInflater.from(this).inflate(R.layout.header_store_index, null);
        initHeaderView();
        listView.addHeaderView(headerView);
      /*  adapter = new ShopCommonAdapter<>(this, mDatas, this);
        listView.setAdapter(adapter);*/
        adapter = new HomeIndexGoodsCommonAdapter(mContext, mDatas, this);
        listView.setAdapter(adapter);
        listView.setHasMoreItems(false);
        //状态栏
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha(0);
    }

    private void initHeaderView() {
        iv_store_head = headerView.findViewById(R.id.iv_store_head);
        tv_store_name = headerView.findViewById(R.id.tv_store_name);
        tv_tag_kangyang = headerView.findViewById(R.id.tv_tag_kangyang);
        tv_store_address = headerView.findViewById(R.id.tv_store_address);
        ly_all = headerView.findViewById(R.id.ly_all);
        iv_bg = headerView.findViewById(R.id.iv_bg);
        tv_follow = headerView.findViewById(R.id.tv_follow);
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
        ly_share.setOnClickListener(this);
        tv_follow.setOnClickListener(new View.OnClickListener() {//关注店铺
            @Override
            public void onClick(View v) {
                if (!LoginUtils.hasLoginUser()) {
                    Intent intent = new Intent(StoreIndexActivity.this, LoginVerifyCodeActivity.class);
                    startActivity(intent);
                } else {
                    showDialog(smallDialog);
                    mPresenter.getCollect(store_id,"2");
                }
            }
        });
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
            if (scollYHeight >= DeviceUtils.dip2px(this, 10)) {
                alpha = 1;//滑上去就一直显示
            } else {
                alpha = scollYHeight / ((DeviceUtils.dip2px(this, 10)) * 1.0f);
            }
            mStatusBar.setAlpha(alpha);
            if (alpha == 1) {
                titleName.setText(modelShopIndex.getMerchant_name());
                ry_title.setBackgroundColor(getResources().getColor(R.color.white));
                iv_left.setBackgroundResource(R.mipmap.ic_arrow_left_black);
                iv_share.setBackgroundResource(R.mipmap.ic_share_black);
                iv_serch.setBackgroundResource(R.mipmap.ic_search_black);
            } else {
                titleName.setText("");
                iv_left.setBackgroundResource(R.mipmap.ic_arrow_left_white);
                ry_title.setBackground(null);
                iv_share.setBackgroundResource(R.mipmap.ic_share_white);
                iv_serch.setBackgroundResource(R.mipmap.ic_search_white);
            }


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
                ly_share.setClickable(true);
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
                            if ("2".equals(modelShopIndex.getPension_display())){
                                tv_tag_kangyang.setVisibility(View.VISIBLE);
                            }else {
                                tv_tag_kangyang.setVisibility(View.GONE);
                            }
                            //判断是否已收藏
                            if ("1".equals(modelShopIndex.getIs_collection())){
                                tv_follow.setText("已关注");
                                tv_follow.setBackgroundResource(R.drawable.allshape_gray_solid_bb35);
                                tv_follow.setClickable(false);
                            }else {
                                tv_follow.setText("+ 关注");
                                tv_follow.setBackgroundResource(R.drawable.allshape_red_35);

                            }
                           // GlideUtils.getInstance().glideLoad(StoreIndexActivity.this, ApiHttpClient.IMG_URL + shopIndex.getBackground(), iv_bg, R.mipmap.ic_store_bg);
                            Glide.with(mContext).load(ApiHttpClient.IMG_URL + shopIndex.getBackground())
                                    .error(R.color.default_img_color)
                                    // "3":模糊度；"3":图片缩放3倍后再进行模糊，缩放3-5倍个人感觉比较好。
                                    .bitmapTransform(new BlurTransformation(mContext, 3, 2))
                                    .placeholder(R.color.default_img_color).crossFade().into(iv_bg);

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
                ly_share.setClickable(false);
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
                 //   new CommonMethod(mDatas.get(position), null, mContext).getShopLimitTag();
                    showDialog(smallDialog);
                    ShopCartManager.getInstance().getShopLimitTag(mContext, mDatas.get(position), new ShopCartManager.OnAddShopCartResultListener() {
                        @Override
                        public void onAddShopCart(int status, String msg) {
                            hideDialog(smallDialog);
                            SmartToast.showInfo(msg);

                        }
                    });
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ly_serch://搜索
                intent.setClass(this, SearchShopActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("store_id", store_id);
                startActivity(intent);
                break;
            case R.id.lin_left://返回
                finish();
                break;
            case R.id.ly_share://分享
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
        popup.showBottom(ly_share);
    }

    /**
     * 关注店铺
     * @param status
     * @param msg
     */
    @Override
    public void onCollect(int status, String msg) {
        hideDialog(smallDialog);
        if (status==1){
            tv_follow.setText("已关注");
            tv_follow.setBackgroundResource(R.drawable.allshape_gray_solid_bb35);
            tv_follow.setClickable(false);
            SmartToast.showInfo("已关注");
        }else {
            SmartToast.showInfo(msg);
        }

    }
}
