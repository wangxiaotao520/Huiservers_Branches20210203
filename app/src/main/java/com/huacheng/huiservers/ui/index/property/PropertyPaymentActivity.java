package com.huacheng.huiservers.ui.index.property;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.property.adapter.PropertyPaymentAdapter;
import com.huacheng.huiservers.ui.index.property.bean.ModelPropertyWyInfo;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 类描述：property 订单缴费记录
 * 时间：2018/8/4 10:49
 * created by DFF
 */
public class PropertyPaymentActivity extends BaseActivity {

    private ListView mListView;
    private SmartRefreshLayout refreshLayout;

    RelativeLayout mRelNoData;
    PropertyPaymentAdapter paymentAdapter;
    List<ModelPropertyWyInfo> mdatas = new ArrayList<>();
    private int page = 1;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("缴费记录");
        mListView = findViewById(R.id.listview);
        refreshLayout =findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        paymentAdapter = new PropertyPaymentAdapter(this, mdatas);
        mListView.setAdapter(paymentAdapter);
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        getPaymentList();
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;

              getPaymentList();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                getPaymentList();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_list_layout;
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

    private void getPaymentList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("p",page+"");
        MyOkHttp.get().get(ApiHttpClient.GET_USER_BILL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelPropertyWyInfo> mlist = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelPropertyWyInfo.class);
//                    if (mlist != null && mlist.size() > 0) {
//                        mRelNoData.setVisibility(View.GONE);
//                        mList.setVisibility(View.VISIBLE);
//                        mdatas.clear();
//                        mdatas.addAll(mlist);
//                        paymentAdapter.notifyDataSetChanged();
//                    } else {
//                        mdatas.clear();
//                        mRelNoData.setVisibility(View.VISIBLE);
//                        mList.setVisibility(View.GONE);
//                    }

                    if (mlist != null && mlist.size() > 0) {
                        mRelNoData.setVisibility(View.GONE);
                        if (page == 1) {
                            mdatas.clear();
                        }
                        mdatas.addAll(mlist);
                        page++;
                        if (page > mlist.get(0).getTotalPages()) {
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            refreshLayout.setEnableLoadMore(true);
                        }
                    } else {
                        if (page == 1) {
                            mRelNoData.setVisibility(View.VISIBLE);
                            mdatas.clear();
                        }
                        refreshLayout.setEnableLoadMore(false);
                    }
                    paymentAdapter.notifyDataSetChanged();
                } else {
                    mdatas.clear();
                    mRelNoData.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.GONE);
                    paymentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
