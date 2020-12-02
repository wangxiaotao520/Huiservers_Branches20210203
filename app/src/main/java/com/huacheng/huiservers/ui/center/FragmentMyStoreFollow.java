package com.huacheng.huiservers.ui.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.servicenew.model.ModelOrderList;
import com.huacheng.huiservers.ui.servicenew.ui.order.ServiceOrderDetailActivity;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 服务商 零售商 Fragment
 * created by wangxiaotao
 * 2020/12/2 0002 09:34
 */
public class FragmentMyStoreFollow extends BaseFragment implements View.OnClickListener {
    private ListView listview;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private boolean isInit = false;       //页面是否进行了初始化
    private boolean isRequesting = false; //是否正在刷新
    int type;
  //  FragmentOrderAdapter fragmentOrderAdapter;
    RelativeLayout rel_no_data;
    private List<ModelOrderList> datas = new ArrayList();
    private ImageView img_data;
    private TextView tv_text;
    private CommonAdapter<ModelOrderList> mAdapter;

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

        listview = view.findViewById(R.id.listview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        img_data = view.findViewById(R.id.img_data);
        tv_text = view.findViewById(R.id.tv_text);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);
        mAdapter= new CommonAdapter<ModelOrderList>(mContext,R.layout.item_follow_store,datas) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelOrderList item, int position) {
              //  viewHolder.<TextView>getView(R.id.tv_time).setText(item.getEatime()+"");
            }
        };
        listview.setAdapter(mAdapter);

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

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (datas.get(position).getStatus().equals("6")) {
                    SmartToast.showInfo("订单已取消");
                } else {
                    //   详情跳转
                    ModelOrderList modelOrderList = datas.get(position);
                    Intent intent = new Intent(mActivity, ServiceOrderDetailActivity.class);
                    intent.putExtra("order_id", modelOrderList.getId());
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * 请求数据
     */
    private void requestData() {
        // 根据接口请求数据
        //dsm->待上门  wfk->未付款 dpj->待评价 ypj->已评价 qxdd->取消订单
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "qb");
        params.put("p", page + "");

        MyOkHttp.get().get(ApiHttpClient.GET_MY_ORDER, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                isRequesting = false;
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelOrderList> modelOrderList = (List<ModelOrderList>) JsonUtil.getInstance().getDataArrayByName(response, "data", ModelOrderList.class);
                    inflateContent(modelOrderList);
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                isRequesting = false;
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
     * 填充数据
     *
     * @param
     */
    private void inflateContent(List<ModelOrderList> modelOrderList) {
        if (modelOrderList != null && modelOrderList.size() > 0) {
            rel_no_data.setVisibility(View.GONE);
            List<ModelOrderList> list_new = modelOrderList;
            if (page == 1) {
                datas.clear();
            }
            datas.addAll(list_new);
            page++;
            if (page > modelOrderList.get(0).getTotal_Pages()) {
                refreshLayout.setEnableLoadMore(false);
            } else {
                refreshLayout.setEnableLoadMore(true);
            }
            mAdapter.notifyDataSetChanged();
            if (page == 2) {
                listview.post(new Runnable() {
                    @Override
                    public void run() {
                        listview.setSelection(0);
                    }
                });
            }
        } else {
            if (page == 1) {
                // 占位图显示出来
                rel_no_data.setVisibility(View.VISIBLE);
                img_data.setBackgroundResource(R.mipmap.bg_no_service_order_data);
                tv_text.setText("暂无工单");
                datas.clear();
            }
            refreshLayout.setEnableLoadMore(false);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //第一页做特殊处理
        if (type == 0) {
            isInit = true;
//            page = 1;
//            isRequesting = true;
//            showDialog(smallDialog);
//            requestData();
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
/*
        //支付完成后传过来的值
        Bundle arguments = getArguments();
        if (arguments != null) {
            String typeReceipt = arguments.getString("typeReceipt", "0");
            if ("2".equals(typeReceipt) && type == 2) {
                page = 1;
                isInit = true;
                refreshLayout.autoRefresh();
            }
        }*/

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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_listcommon;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 回调
     *
     * @param
     * @param
     */

   /* @Override
    public void click(final ModelOrderList.ListBean listBean, final int type) {

        if (type == 0) {//上门
            new CommomDialog(mContext, R.style.dialog, "确定上门", new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        getSend(listBean, type);
                        dialog.dismiss();
                    }
                }
            }).show();
        } else if (type == 1) {//收款
            Intent intent = new Intent(mActivity, ReceiptActivity.class);
            String oid = listBean.getId();
            String onum = listBean.getOrder_number();
            intent.putExtra("oid", oid);
            intent.putExtra("onum", onum);
            startActivity(intent);

        }
    }
*/
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 更新数据（取消订单，评价完成）
     *
     * @param modelOrderList
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateOrderList(ModelOrderList modelOrderList) {
        ModelOrderList modelOrderList_remove = null;

    }
}
