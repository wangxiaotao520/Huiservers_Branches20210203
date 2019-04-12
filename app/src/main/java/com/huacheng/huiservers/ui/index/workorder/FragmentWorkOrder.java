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
import com.huacheng.huiservers.model.ModelWorkOrder;
import com.huacheng.huiservers.model.ModelWorkOrderList;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.index.workorder.adapter.AdapterWorkOrder;
import com.huacheng.huiservers.utils.StringUtils;
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
 * Description:工单Fragment
 * Author: badge
 * Create: 2018/12/10 11:09
 */
public class FragmentWorkOrder extends BaseFragment {

    private SmartRefreshLayout refreshLayout;
    private ListView listview;
    private RelativeLayout rel_no_data;
    private AdapterWorkOrder adapterWorkOrder;
    private List<ModelWorkOrderList> mDatas = new ArrayList<>();

    private int page = 0;
    private int type = 0;
    private boolean isInit = false;
    private String status = "";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
    }


    @Override
    public void initView(View view) {
        listview = view.findViewById(R.id.listview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);

        adapterWorkOrder = new AdapterWorkOrder(mActivity, mDatas, type);
        listview.setAdapter(adapterWorkOrder);
    }


    /**
     * 事件处理
     */
    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                page = 1;
                requestData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                requestData();
            }
        });
        if (adapterWorkOrder != null) {

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //调转工单详情
                    Intent intent = new Intent(mContext, WorkOrderDetailActivity.class);
                    intent.putExtra("work_id", mDatas.get(i).getId());
                    startActivity(intent);

                }
            });

        }
    }


    /**
     * 请求数据
     */
    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (type == 0) {
            status = "1";//待服务
        } else if (type == 1) {
            status = "2";//服务中
        } else if (type == 2) {
            status = "3";//已完成
        }
        params.put("state", status);
        params.put("page", page + "");
        MyOkHttp.get().post(ApiHttpClient.GETWORKLIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelWorkOrder info = (ModelWorkOrder) JsonUtil.getInstance().parseJsonFromResponse(response, ModelWorkOrder.class);
                    if (info != null) {
                        if (info.getList() != null && info.getList().size() > 0) {
                            rel_no_data.setVisibility(View.GONE);
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mDatas.addAll(info.getList());
                            page++;
                            if (page > info.getCountPage()) {
                                refreshLayout.setEnableLoadMore(false);
                            } else {
                                refreshLayout.setEnableLoadMore(true);
                            }
                            adapterWorkOrder.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                rel_no_data.setVisibility(View.VISIBLE);
                                mDatas.clear();
                            }
                            refreshLayout.setEnableLoadMore(false);
                            adapterWorkOrder.notifyDataSetChanged();
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

    /**
     *
     *
     * @param model
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cancelWorkOrder(EventBusWorkOrderModel model) {
        if (model!=null){
            String cancel_wid = model.getWo_id();
            if (model.getEvent_type()==0){//取消订单
                if (!StringUtils.isEmpty(cancel_wid)) {
                    ModelWorkOrderList model_remove=null;
                    for (int i = 0; i < mDatas.size(); i++) {
                        if (cancel_wid.equals(mDatas.get(i).getId())) {
                            model_remove=mDatas.get(i);
                        }
                    }
                    if (model_remove!=null){
                        mDatas.remove(model_remove);
                        adapterWorkOrder.notifyDataSetChanged();
                    }
                }
            }else if (model.getEvent_type()==1){
                //预付
                ModelWorkOrderList model_change=null;
                for (int i = 0; i < mDatas.size(); i++) {
                    if (cancel_wid.equals(mDatas.get(i).getId())) {
                        model_change=mDatas.get(i);
                    }
                }
                if (model_change!=null){
                    if (refreshLayout != null) {
                        refreshLayout.autoRefresh();
                    }
                }
            }else if (model.getEvent_type()==2){
                //预付
                ModelWorkOrderList model_change=null;
                for (int i = 0; i < mDatas.size(); i++) {
                    if (cancel_wid.equals(mDatas.get(i).getId())) {
                        model_change=mDatas.get(i);
                    }
                }
                if (model_change!=null){
                    if (refreshLayout != null) {
                        refreshLayout.autoRefresh();
                    }
                }
            }

        }
    }

    /**
     * 判断是否刷新
     */
    public void isRefresh() {
        if (!isInit) {
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_work_order;
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);//注册EventBus
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);//反注册EventBus
        super.onDestroy();
    }
}
