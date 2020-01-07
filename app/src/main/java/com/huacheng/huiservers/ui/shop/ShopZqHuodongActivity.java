package com.huacheng.huiservers.ui.shop;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelZQInfo;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.shop.adapter.ShopZQAdapter;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：专区活动列表
 * 时间：2019/11/13 17:03
 * created by DFF
 */
public class ShopZqHuodongActivity extends BaseListActivity<BannerBean> {
    private ShopZQAdapter shopZQAdapter;
    List<ModelZQInfo> mDatas = new ArrayList<>();
    private String id = "";

    @Override
    protected void initView() {
        super.initView();
        findTitleViews();
        titleName.setText("专区活动");

        //shopZQAdapter = new ShopZQAdapter(this, R.layout.circle_list_item, mDatas);
        shopZQAdapter = new ShopZQAdapter(this, R.layout.item_circle_list, mDatas);
        mListview.setAdapter(shopZQAdapter);
    }

    @Override
    protected void initIntentData() {
        super.initIntentData();
        id = this.getIntent().getStringExtra("id");
    }

    @Override
    protected void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("m_id", id);
        params.put("p", page + "");
        MyOkHttp.get().get(ApiHttpClient.SHOP_MARKIING_ARTICE, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelZQInfo info = (ModelZQInfo) JsonUtil.getInstance().parseJsonFromResponse(response, ModelZQInfo.class);
                    if (info != null) {
                        if (info.getList() != null && info.getList().size() > 0) {
                            mRelNoData.setVisibility(View.GONE);
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mDatas.addAll(info.getList());
                            page++;
                            if (page > info.getTotal_Pages()) {
                                mRefreshLayout.setEnableLoadMore(false);
                            } else {
                                mRefreshLayout.setEnableLoadMore(true);
                            }
                            shopZQAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                mRelNoData.setVisibility(View.VISIBLE);
                                mDatas.clear();
                            }
                            mRefreshLayout.setEnableLoadMore(false);
                            shopZQAdapter.notifyDataSetChanged();
                        }
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
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    mRefreshLayout.setEnableLoadMore(false);
                }
            }
        });
    }

    @Override
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {
        Intent intent=new Intent();
        intent.setClass(this, ShopZQWebActivity.class);
        intent.putExtra("id", mDatas.get(position).getId());
        intent.putExtra("sub_type", "2");
        startActivity(intent);
    }
}
