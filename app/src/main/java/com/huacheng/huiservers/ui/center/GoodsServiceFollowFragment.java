package com.huacheng.huiservers.ui.center;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelCollect;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.center.adapter.AdapterGoodsServiceFollow;
import com.huacheng.huiservers.ui.center.presenter.CollectDeletePresenter;
import com.huacheng.huiservers.ui.servicenew.ui.ServiceDetailActivity;
import com.huacheng.huiservers.ui.servicenew.ui.adapter.MerchantServicexAdapter;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
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
 * 类描述：商品服务关注Fragment
 * 时间：2020/12/2 09:28
 * created by DFF
 */
public class GoodsServiceFollowFragment extends BaseFragment implements CollectDeletePresenter.CollectListener  {
    private int total_Page = 1;
    private int type;

    private View rel_no_data;
    private ListView listview;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private boolean isInit = false;       //页面是否进行了初始化
    private boolean isRequesting = false; //是否正在刷新
//    private List<ModelOrderList> mData = new ArrayList();
//    private List<ModelServiceItem> mDatas2 = new ArrayList();
    private List<ModelCollect> datas = new ArrayList();
    MerchantServicexAdapter adapter;
    AdapterGoodsServiceFollow mGoodsServiceFollow;
    private CollectDeletePresenter mDeletePresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
    }

    @Override
    public void initView(View view) {
        mDeletePresenter=new CollectDeletePresenter(mActivity,this);
        listview = view.findViewById(R.id.listview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);

        mGoodsServiceFollow=new AdapterGoodsServiceFollow(mActivity,R.layout.item_shop_service_follow,datas,type);
        listview.setAdapter(mGoodsServiceFollow);

    }

    @Override
    public void initIntentData() {

    }
    /**
     * 请求数据
     */
    private String setUrl = "";

    private void requestData() {
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");
        if (type == 0) {
            setUrl = ApiHttpClient.MY_COLLECT_SHOP;
        } else {
            setUrl = ApiHttpClient.MY_COLLECT_SERVICE;
        }
        MyOkHttp.get().get(setUrl, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                isRequesting = false;
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelCollect info = (ModelCollect) JsonUtil.getInstance().parseJsonFromResponse(response, ModelCollect.class);
                    if (info != null) {
                        inflateContent(info);
                    }
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
    private void inflateContent(ModelCollect modelCollects) {
        if (modelCollects != null) {
            if (modelCollects.getList() != null && modelCollects.getList().size() > 0) {
                rel_no_data.setVisibility(View.GONE);
                List<ModelCollect> list_new = modelCollects.getList();
                if (page == 1) {
                    datas.clear();
                }
                datas.addAll(list_new);
                page++;
                if (page > modelCollects.getTotal_Pages()) {
                    refreshLayout.setEnableLoadMore(false);
                } else {
                    refreshLayout.setEnableLoadMore(true);
                }
                mGoodsServiceFollow.notifyDataSetChanged();
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
                    //img_data.setBackgroundResource(R.mipmap.bg_no_service_order_data);
                    //tv_text.setText("暂无工单");
                    datas.clear();
                }
                refreshLayout.setEnableLoadMore(false);
                mGoodsServiceFollow.notifyDataSetChanged();
            }
        }
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
                if (type == 0) {
                    Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("shop_id", datas.get(position).getP_id());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                    intent.putExtra("service_id", datas.get(position).getP_id());
                    startActivity(intent);
                }
            }
        });
        //长按删除收藏
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new CommomDialog(mActivity, R.style.my_dialog_DimEnabled, "确认要删除吗？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            if (type==0){
                                showDialog(smallDialog);
                                mDeletePresenter.getDeleteCollect(datas.get(position).getCollection_id(),"1");
                            }else{
                                showDialog(smallDialog);
                                mDeletePresenter.getDeleteCollect(datas.get(position).getCollection_id(),"3");
                            }
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).show();
                return true;
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (type == 0) {
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
    /**
     * 选中请求
     */
    public void selected_init() {
        if (type > 0 && isInit == false && isRequesting == false) {
            isInit = true;
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
    }

    /**
     *删除收藏
     * @param status
     * @param collect_id
     * @param msg
     */
    @Override
    public void onDeleteCollect(int status, String collect_id, String msg) {
        hideDialog(smallDialog);
        if (status == 1) {
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).getCollection_id().equals(collect_id)) {
                    datas.remove(i);
                }
            }
            if (datas.size()==0){
                rel_no_data.setVisibility(View.VISIBLE);
            }
            mGoodsServiceFollow.notifyDataSetChanged();
        } else {
            SmartToast.showInfo(msg);
        }

    }
}
