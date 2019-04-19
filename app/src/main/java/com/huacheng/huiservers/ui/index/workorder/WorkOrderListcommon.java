package com.huacheng.huiservers.ui.index.workorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.huacheng.huiservers.model.EventBusWorkOrderModel;
import com.huacheng.huiservers.model.ModelNewWorkOrder;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.index.workorder.adapter.WorkOrderListSecondAdapter;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：新版工单Fragment
 * 时间：2019/4/8 16:14
 * created by DFF
 */
public class WorkOrderListcommon extends BaseFragment {
    private ListView mListView;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private boolean isInit = false;       //页面是否进行了初始化
    private boolean isRequesting = false; //是否正在刷新
    int type;
    RelativeLayout rel_no_data;
    List<ModelNewWorkOrder> mDatas = new ArrayList<>();
    WorkOrderListSecondAdapter workListAdapter;
    private String status = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(View view) {

        mListView = view.findViewById(R.id.listview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);

        workListAdapter = new WorkOrderListSecondAdapter(mActivity, R.layout.item_workorder_second_list, mDatas);
        mListView.setAdapter(workListAdapter);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isRequesting = true;
                requestData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRequesting = true;
                requestData();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, WorkOrderDetailActivity.class);
                intent.putExtra("id", mDatas.get(position).getId());
                intent.putExtra("work_status", mDatas.get(position).getWork_status());
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (type == 0) {
            isInit = true;
            page = 1;
            showDialog(smallDialog);
            requestData();
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
    }

    /**
     * 请求数据
     */
    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (type == 0) {
            status = "wait";//待服务
        } else if (type == 1) {
            status = "service";//服务中
        } else if (type == 2) {
            status = "payment";//待支付
        } else if (type == 3) {
            status = "over";//已完成
        }
        params.put("list_type", status);
        params.put("p", page + "");
        MyOkHttp.get().post(ApiHttpClient.GET_WORK_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelNewWorkOrder info = (ModelNewWorkOrder) JsonUtil.getInstance().parseJsonFromResponse(response, ModelNewWorkOrder.class);
                    if (info != null) {
                        if (info.getList() != null && info.getList().size() > 0) {
                            rel_no_data.setVisibility(View.GONE);
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mDatas.addAll(info.getList());
                            page++;
                            if (page > info.getTotalPages()) {
                                refreshLayout.setEnableLoadMore(false);
                            } else {
                                refreshLayout.setEnableLoadMore(true);
                            }
                            workListAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                rel_no_data.setVisibility(View.VISIBLE);
                                mDatas.clear();
                            }
                            refreshLayout.setEnableLoadMore(false);
                            workListAdapter.notifyDataSetChanged();
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
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    refreshLayout.setEnableLoadMore(false);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_work_order;
    }


    /**
     * 取消工单 评价 支付返回
     *
     * @param model
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshWorkOrder(EventBusWorkOrderModel model) {
        if (model != null) {
            ModelNewWorkOrder model_change = null;
            for (int i = 0; i < mDatas.size(); i++) {
                if (model.getWork_id().equals(mDatas.get(i).getId())) {
                    model_change = mDatas.get(i);
                }
            }
            if (model_change != null) {
                if (refreshLayout != null) {
                    refreshLayout.autoRefresh();
                }
            }
        }
    }

    /**
     * 选中时才调用
     *
     * @param param
     */
    public void onTabSelectedRefresh(String param) {
        if (type > 0 && isInit == false && isRequesting == false) {
            isInit = true;
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
    }
    /**
     * 直接刷新
     */
    public void refreshIndeed() {
        if (isInit) {
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
    }

}
