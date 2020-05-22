package com.huacheng.huiservers.ui.fragment.indexcat;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelAds;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：共享商圈
 * 时间：2020/5/21 18:49
 * created by DFF
 */
public class IndexShareSQActivity extends BaseListActivity {
    List<ModelAds> mDatas = new ArrayList<>();
    private String id = "";
    SharePrefrenceUtil prefrenceUtil;
    private CommonAdapter<ModelAds> adapter;
    private RelativeLayout mRelData;

    @Override
    protected void initView() {
        super.initView();
        findTitleViews();
        prefrenceUtil = new SharePrefrenceUtil(this);
        titleName.setText("共享商圈");

        adapter = new CommonAdapter<ModelAds>(this, R.layout.item_index_share_sq, mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelAds item, int position) {
                viewHolder.<TextView>getView(R.id.tv_name).setText(item.getText());
                FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_shangquan), ApiHttpClient.IMG_URL+item.getImg());

            }
        };
        mListview.setAdapter(adapter);
    }

    @Override
    protected void initIntentData() {
        super.initIntentData();
        id = this.getIntent().getStringExtra("id");
    }

    @Override
    protected void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("c_name", "hc_business_more");
        params.put("community_id", prefrenceUtil.getXiaoQuId());
        params.put("p", page + "");
        MyOkHttp.get().get(ApiHttpClient.GET_ADVERTISING, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelAds> info = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelAds.class);
                    if (info != null && info.size() > 0) {
                        mRelNoData.setVisibility(View.GONE);
                        if (page == 1) {
                            mDatas.clear();
                        }
                        mDatas.addAll(info);
                        page++;
                        if (page > info.get(0).getTotal_Pages()) {
                            mRefreshLayout.setEnableLoadMore(false);
                        } else {
                            mRefreshLayout.setEnableLoadMore(true);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        if (page == 1) {
                            mRelNoData.setVisibility(View.VISIBLE);
                            mDatas.clear();
                        }
                        mRefreshLayout.setEnableLoadMore(false);
                        adapter.notifyDataSetChanged();
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
        if (id == -1) {
            return;
        }
        ModelAds ads = mDatas.get(position);
        if (TextUtils.isEmpty(ads.getUrl())) {
            if ("0".equals(ads.getUrl_type()) || TextUtils.isEmpty(ads.getUrl_type())) {
                new Jump(this, ads.getType_name(), ads.getAdv_inside_url());
            } else {
                new Jump(this, ads.getUrl_type(), ads.getType_name(), "", ads.getUrl_type_cn());
            }
        } else {//URL不为空时外链
            new Jump(this, ads.getUrl());

        }


    }
}
