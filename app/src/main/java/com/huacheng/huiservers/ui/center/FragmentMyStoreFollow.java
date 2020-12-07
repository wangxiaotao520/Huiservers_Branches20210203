package com.huacheng.huiservers.ui.center;

import android.app.Dialog;
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
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelCollect;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.center.presenter.CollectDeletePresenter;
import com.huacheng.huiservers.ui.servicenew.ui.shop.ServiceStoreActivity;
import com.huacheng.huiservers.ui.shop.StoreIndexActivity;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 服务商 零售商 Fragment
 * created by wangxiaotao
 * 2020/12/2 0002 09:34
 */
public class FragmentMyStoreFollow extends BaseFragment implements View.OnClickListener,CollectDeletePresenter.CollectListener {
    private ListView listview;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private boolean isInit = false;       //页面是否进行了初始化
    private boolean isRequesting = false; //是否正在刷新
    int type;
    //  FragmentOrderAdapter fragmentOrderAdapter;
    RelativeLayout rel_no_data;
    private List<ModelCollect> datas = new ArrayList();
    private ImageView img_data;
    private TextView tv_text;
    private CommonAdapter<ModelCollect> mAdapter;
    private CollectDeletePresenter mDeletePresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(View view) {
        mDeletePresenter=new CollectDeletePresenter(mActivity,this);
        listview = view.findViewById(R.id.listview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        img_data = view.findViewById(R.id.img_data);
        tv_text = view.findViewById(R.id.tv_text);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);
        mAdapter = new CommonAdapter<ModelCollect>(mContext, R.layout.item_follow_store, datas) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelCollect item, int position) {
                FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.iv_store_head), ApiHttpClient.IMG_URL + item.getLogo());
                if (type == 0) {
                    viewHolder.<TextView>getView(R.id.tv_store_name).setText(item.getMerchant_name() + "");
                } else {
                    viewHolder.<TextView>getView(R.id.tv_store_name).setText(item.getName() + "");
                }
                viewHolder.<TextView>getView(R.id.tv_store_address).setText(item.getAddress());
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
                if (type == 0) {
                    Intent intent = new Intent(mContext, StoreIndexActivity.class);
                    intent.putExtra("store_id", datas.get(position).getP_id());
                    mContext.startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext, ServiceStoreActivity.class);
                    intent.putExtra("store_id", datas.get(position).getP_id());
                    mContext.startActivity(intent);
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
                                mDeletePresenter.getDeleteCollect(datas.get(position).getCollection_id(),"2");
                            }else{
                                showDialog(smallDialog);
                                mDeletePresenter.getDeleteCollect(datas.get(position).getCollection_id(),"4");
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

    /**
     * 请求数据
     */
    private String setUrl = "";

    private void requestData() {
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");
        if (type == 0) {
            setUrl = ApiHttpClient.MY_COLLECT_SHOP_STORE;
        } else {
            setUrl = ApiHttpClient.MY_COLLECT_SERVICE_STORE;
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
                    //img_data.setBackgroundResource(R.mipmap.bg_no_service_order_data);
                    //tv_text.setText("暂无工单");
                    datas.clear();
                }
                refreshLayout.setEnableLoadMore(false);
                mAdapter.notifyDataSetChanged();
            }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            mAdapter.notifyDataSetChanged();
        } else {
            SmartToast.showInfo(msg);
        }

    }
}
