package com.huacheng.huiservers.ui.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.shop.adapter.ShopCommonAdapter;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：店铺首页
 * 时间：2019/11/8 10:59
 * created by DFF
 */
public class StoreIndexActivity extends BaseActivity implements ShopCommonAdapter.OnClickCallback {
    View mStatusBar;
    private View headerView;
    protected PagingListView listView;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    private List<ModelShopIndex> mDatas = new ArrayList<>();//数据
    private ShopCommonAdapter<ModelShopIndex> adapter;
    private int page = 1;

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

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        headerView = LayoutInflater.from(this).inflate(R.layout.header_store_index, null);
        initHeaderView();
        listView.addHeaderView(headerView);
       /* for (int i = 0; i < 15; i++) {
            mDatas.add(new ModelShopIndex());
        }*/
        adapter = new ShopCommonAdapter<>(this, mDatas, this);
        listView.setAdapter(adapter);
        listView.setHasMoreItems(false);

        //状态栏
        mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha((float) 1);
    }

    private void initHeaderView() {
    }

    @Override
    protected void initData() {
        requestData();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_index;
    }

    @Override
    protected void initIntentData() {

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
        //dsm->待上门  wfk->未付款 dpj->待评价 ypj->已评价 qxdd->取消订单
        HashMap<String, String> params = new HashMap<>();
        params.put("c_id", 5 + "");
        params.put("id", 1 + "");
        params.put("p", page + "");

        MyOkHttp.get().get(ApiHttpClient.SHOP_INDEX_MORE, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {

                hideDialog(smallDialog);
                listView.setIsLoading(false);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelShopIndex> shopIndexList = (List<ModelShopIndex>) JsonUtil.getInstance().getDataArrayByName(response, "data", ModelShopIndex.class);

                    inflateContent(shopIndexList);

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                listView.setIsLoading(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    setEnableLoadMore(false);
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
                setEnableLoadMore(false);
            } else {
                setEnableLoadMore(true);
            }
            adapter.notifyDataSetChanged();

        } else {
            if (page == 1) {
                // 占位图显示出来
                mRelNoData.setVisibility(View.VISIBLE);
                mDatas.clear();
            }
            setEnableLoadMore(false);
            adapter.notifyDataSetChanged();
        }

    }

    private void setEnableLoadMore(boolean isloadmore) {
        if (isloadmore) {
            listView.setHasMoreItems(true);
        } else {
            listView.setHasMoreItems(false);
        }
    }

    @Override
    public void onClickShopCart(int position) {

    }

    @Override
    public void onClickImage(int position) {

    }
}
