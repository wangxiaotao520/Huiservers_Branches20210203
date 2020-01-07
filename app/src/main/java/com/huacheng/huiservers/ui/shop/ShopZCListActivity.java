package com.huacheng.huiservers.ui.shop;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：特卖专场
 * 时间：2019/12/13 10:54
 * created by DFF
 */
public class ShopZCListActivity extends BaseListActivity {
    private List<ModelShopIndex> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("特卖专场");

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);

        mAdapter = new CommonAdapter<ModelShopIndex>(this, R.layout.item_shop_zc, mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelShopIndex item, int position) {
                FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_bg), ApiHttpClient.IMG_URL + item.getIcon_img());
            }
        };
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void initIntentData() {
        super.initIntentData();
    }

    @Override
    protected void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");
        MyOkHttp.get().post(ApiHttpClient.SHOP_MARKIING_ARTICE_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    // List<ModelShopIndex>  list = (List<ModelShopIndex>) JsonUtil.getInstance().getDataArrayByName(response, "data",ModelShopIndex.class);
                    ModelShopIndex info = (ModelShopIndex) JsonUtil.getInstance().parseJsonFromResponse(response, ModelShopIndex.class);
                    if (info != null) {
                        if (info.getList() != null && info.getList().size() > 0) {
                            mRelNoData.setVisibility(View.GONE);
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mDatas.addAll(info.getList());
                            page++;
                            if (page > info.getTotalPages()) {
                                mRefreshLayout.setEnableLoadMore(false);
                            } else {
                                mRefreshLayout.setEnableLoadMore(true);
                            }
                            mAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                mRelNoData.setVisibility(View.VISIBLE);
                                mDatas.clear();
                            }
                            mRefreshLayout.setEnableLoadMore(false);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    java.lang.String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, java.lang.String error_msg) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    mRefreshLayout.setEnableLoadMore(false);
                }
            }
        });
    }

    @Override
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {
        Intent intent = new Intent(mContext, ShopZQListActivity.class);
        intent.putExtra("id", mDatas.get(position).getId());
        startActivity(intent);
    }
}
