package com.huacheng.huiservers.ui.index.charge;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelChargeHistory;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.charge.adapter.ChargeHistoryAdapter;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：充电记录
 * 时间：2019/8/18 11:36
 * created by DFF
 */
public class ChargeHistoryActivity extends BaseActivity {
    protected int page = 1;
    protected ListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    ChargeHistoryAdapter adapter;
    List<ModelChargeHistory> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("充电记录");
        mListview = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        adapter = new ChargeHistoryAdapter(this, R.layout.activity_charge_history_item, mDatas);
        mListview.setAdapter(adapter);

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
                mListview.post(new Runnable() {
                    @Override
                    public void run() {
                        mListview.setSelection(0);
                    }
                });
                requestData();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                requestData();
            }
        });
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent=new Intent(ChargeHistoryActivity.this,ChargeDetailActivity.class);
                if ("1".equals(mDatas.get(position).getStatus())) {
                    Intent intent = new Intent(ChargeHistoryActivity.this, ChargingActivity.class);
                    intent.putExtra("id",(mDatas.get(position).getId()+""));
                    intent.putExtra("jump_from","list");
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(ChargeHistoryActivity.this,ChargeDetailActivity.class);
                    intent.putExtra("id",(mDatas.get(position).getId()+""));
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 请求数据
     */
    private void requestData() {
        HashMap<String, String> params = new HashMap<>();

        params.put("p", page + "");
        MyOkHttp.get().post(ApiHttpClient.PAY_CHARGE_RECORD, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelChargeHistory info = (ModelChargeHistory) JsonUtil.getInstance().parseJsonFromResponse(response, ModelChargeHistory.class);
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
                            adapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                mRelNoData.setVisibility(View.VISIBLE);
                                mDatas.clear();
                            }
                            mRefreshLayout.setEnableLoadMore(false);
                            adapter.notifyDataSetChanged();
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
    protected int getLayoutId() {
        return R.layout.activity_charge_history_list;
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
}
