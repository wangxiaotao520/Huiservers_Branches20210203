package com.huacheng.huiservers.ui.shop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.center.bean.ShopOrderBeanTypeBean;
import com.huacheng.huiservers.ui.center.bean.XorderDetailBean;
import com.huacheng.huiservers.ui.shop.adapter.AdapterShopOrderListNew;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.NullUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 订单列表
 * created by wangxiaotao
 * 2020/1/8 0008 上午 8:54
 */
public class FragmentShopOrderListNew extends BaseFragment implements AdapterShopOrderListNew.OnClickShopOrderListListener {
    SharePrefrenceUtil prefrenceUtil;
    private int total_Page=1;
    private int type;//"0全部","1待付款","2待收货","3已完成", "4退款/售后"

    private SmartRefreshLayout refreshLayout;
    private PagingListView listView;
    private View rel_no_data;
    List<ShopOrderBeanTypeBean> mBeanALList = new ArrayList<>();
    private int page = 1;
    private AdapterShopOrderListNew adapter;
    private boolean isInit=false;
    private String mType_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
        mType_back = arguments.getString("type_back");
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.rl_title).setVisibility(View.GONE);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        listView = view.findViewById(R.id.listview);
        adapter = new AdapterShopOrderListNew(mActivity, R.layout.item_shop_order_list_new, mBeanALList, this);
        listView.setAdapter(adapter);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //商品详情

            }
        });
        //一开始先设置成false onScrollListener
        listView.setHasMoreItems(false);
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                requestData();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                requestData();
            }
        });
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
        //从支付成功页跳转到订单
        if (type == 1 && !NullUtil.isStringEmpty(mType_back) && "type_zf_dfk".equals(mType_back)) {
            isInit = true;
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
        if (type == 2 && !NullUtil.isStringEmpty(mType_back) && "type_zf_dsh".equals(mType_back)) {
            isInit = true;
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
    }

    /**
     * 请求数据
     */
    private void requestData() {
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        if (type == 0) {
            params.put("status", "0");
        } else if (type == 1) {
            params.put("status", "1");
        } else if (type == 2) {
            params.put("status", "2");
        } else if (type == 3) {
            params.put("status", "3");
        }else if (type == 4) {
            //TODO
            params.put("status", "3");
        }

        params.put("p", page + "");

        MyOkHttp.get().get(ApiHttpClient.GET_SHOP_ORDER_LIST, params,  new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                listView.setIsLoading(false);
                if (JsonUtil.getInstance().isSuccess(response)){

                    List<ShopOrderBeanTypeBean> modelOrderList = (List<ShopOrderBeanTypeBean>) com.huacheng.libraryservice.utils.json.JsonUtil.getInstance().getDataArrayByName(response, "data", ShopOrderBeanTypeBean.class);

                        if (modelOrderList!=null&&modelOrderList.size()>0){
                            if (page==1){
                                mBeanALList.clear();
                                listView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        listView.setSelection(0);
                                    }
                                });
                            }
                            mBeanALList.addAll(modelOrderList);
                            page++;
                            total_Page=Integer.valueOf(mBeanALList.get(0).getTotal_Pages());// 设置总页数
                            rel_no_data.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            if (page>total_Page){

                                listView.setHasMoreItems(false);
                            }else {

                                listView.setHasMoreItems(true);
                            }
                        }else {
                            if (page==1){
                                mBeanALList.clear();
                                rel_no_data.setVisibility(View.VISIBLE);
                                adapter.notifyDataSetChanged();
                                total_Page=0;
                            }
                            listView.setHasMoreItems(false);
                        }


                }else {
                    String msg = com.huacheng.libraryservice.utils.json.JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                listView.setIsLoading(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vote_index;
    }

    /**
     * 选中请求
     */
    public void selected_init() {
        if (isInit) {

        }else {
            page=1;
            if (refreshLayout!=null){
                refreshLayout.autoRefresh();
            }
            isInit=true;
        }

    }

    @Override
    public void onClickPay(ShopOrderBeanTypeBean item, int position) {
        //支付
    }

    @Override
    public void onClickReBuy(ShopOrderBeanTypeBean item, int position) {
        //再次购买
    }

    @Override
    public void onClickConfirm(ShopOrderBeanTypeBean item, int position) {
        //确认收货
    }

    @Override
    public void onClickPinjia(ShopOrderBeanTypeBean item, int position) {
        //评价
    }

    /**
     * 详情界面回调退款 评价 删除
     * //todo eventbus 还没处理
     *
     * @param
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void back(XorderDetailBean info) {
        if (info != null) {
            if (info.getBack_type() == 1) {//删除
//                for (int i = 0; i < mBeanALList.size(); i++) {
//                    if (mBeanALList.get(i).getId().equals(info.getId())) {
//                        mBeanALList.remove(i);
//                    }
//                }
//                adapter.notifyDataSetChanged();
            } else if (info.getBack_type() == 2) {//评价
                //待评价刷新一下
//                for (int i = 0; i < mBeanALList.size(); i++) {
//                    if (mBeanALList.get(i).getImg().size() == 1) {
//                        mBeanALList.remove(i);
//                    } else {
//                        for (int i1 = 0; i1 < mBeanALList.get(i).getImg().size(); i1++) {
//                            if (mBeanALList.get(i).getImg().get(i1).getInfo_id().equals(info.getId())) {
//                                mBeanALList.get(i).getImg().remove(i1);
//                            }
//                        }
//                    }
//                }
//                EventBus.getDefault().post("pj");
//                adapter.notifyDataSetChanged();
            } else if (info.getBack_type() == 3) {//退款 以及收货
//                EventBus.getDefault().post("tk_sh");
//                adapter.notifyDataSetChanged();
            } else if (info.getBack_type() == 4) {//订单中支付成功
//                EventBus.getDefault().post("zf");
//                adapter.notifyDataSetChanged();
            }
        }
    }
}
