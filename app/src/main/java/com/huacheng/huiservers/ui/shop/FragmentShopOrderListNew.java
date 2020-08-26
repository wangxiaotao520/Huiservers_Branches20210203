package com.huacheng.huiservers.ui.shop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.pay.chinaums.UnifyPayActivity;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.model.ShopOrderBeanTypeBean;
import com.huacheng.huiservers.model.XorderDetailBean;
import com.huacheng.huiservers.ui.shop.presenter.ShopOrderDetetePrester;
import com.huacheng.huiservers.ui.shop.adapter.AdapterShopOrderListNew;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
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
public class FragmentShopOrderListNew extends BaseFragment implements AdapterShopOrderListNew.OnClickShopOrderListListener, ShopOrderDetetePrester.ShopOrderListener {
    SharePrefrenceUtil prefrenceUtil;
    private int total_Page = 1;
    private int type;//"0待付款","1待收货","2已完成", "3退款/售后"

    private SmartRefreshLayout refreshLayout;
    private PagingListView listView;
    private View rel_no_data;
    List<ShopOrderBeanTypeBean> mBeanALList = new ArrayList<>();
    private int page = 1;
    private AdapterShopOrderListNew adapter;
    private boolean isInit = false;
    private String mType_back;
    private ImageView img_data;
    private TextView tv_text;
    private ShopOrderDetetePrester mPresenter;

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
        // mType_back = arguments.getString("type_back");
    }

    @Override
    public void initView(View view) {
        mPresenter = new ShopOrderDetetePrester(mActivity, this);
        view.findViewById(R.id.rl_title).setVisibility(View.GONE);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        img_data = view.findViewById(R.id.img_data);
        tv_text = view.findViewById(R.id.tv_text);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        listView = view.findViewById(R.id.listview);
        adapter = new AdapterShopOrderListNew(mActivity, R.layout.item_shop_order_list_new, mBeanALList, this, type);
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
                Intent intent = new Intent(mActivity, ShopOrderDetailActivityNew.class);
                intent.putExtra("id", mBeanALList.get(position).getId());
                intent.putExtra("p_m_id", mBeanALList.get(position).getP_m_id());
                intent.putExtra("status", (type + 1) + "");
                startActivity(intent);


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
                page = 1;
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
       /* if (type == 0 && !NullUtil.isStringEmpty(mType_back) && "type_zf_dfk".equals(mType_back)) {
            isInit = true;
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
        if (type == 1 && !NullUtil.isStringEmpty(mType_back) && "type_zf_dsh".equals(mType_back)) {
            isInit = true;
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }*/
    }

    public void setRefreh() {
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

    /**
     * 请求数据
     */
    private void requestData() {
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        if (type == 0) {
            params.put("status", "1");
        } else if (type == 1) {
            params.put("status", "2");
        } else if (type == 2) {
            params.put("status", "3");
        } else if (type == 3) {
            params.put("status", "4");
        }
        params.put("p", page + "");
        MyOkHttp.get().get(ApiHttpClient.GET_SHOP_ORDER_LIST, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                listView.setIsLoading(false);
                if (JsonUtil.getInstance().isSuccess(response)) {

                    ShopOrderBeanTypeBean shopOrderBeanTypeBean = (ShopOrderBeanTypeBean) JsonUtil.getInstance().parseJsonFromResponse(response, ShopOrderBeanTypeBean.class);
                    List<ShopOrderBeanTypeBean> list = shopOrderBeanTypeBean.getList();
                    if (shopOrderBeanTypeBean != null && shopOrderBeanTypeBean.getList() != null && shopOrderBeanTypeBean.getList().size() > 0) {
                        if (page == 1) {
                            mBeanALList.clear();
                            listView.post(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setSelection(0);
                                }
                            });
                        }
                        mBeanALList.addAll(list);
                        page++;
                        total_Page = shopOrderBeanTypeBean.getTotalPages();// 设置总页数
                        rel_no_data.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        if (page > total_Page) {

                            listView.setHasMoreItems(false);
                        } else {

                            listView.setHasMoreItems(true);
                        }
                    } else {
                        if (page == 1) {
                            mBeanALList.clear();
                            rel_no_data.setVisibility(View.VISIBLE);
                            img_data.setBackgroundResource(R.mipmap.bg_no_shop_order_data);
                            tv_text.setText("暂无订单");
                            adapter.notifyDataSetChanged();
                            total_Page = 0;
                        }
                        listView.setHasMoreItems(false);
                    }


                } else {
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

        } else {
            page = 1;
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
            isInit = true;
        }

    }

    @Override
    public void onClickPay(ShopOrderBeanTypeBean item, int position) {
        //支付
        Intent intent = new Intent(mActivity, UnifyPayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("o_id", mBeanALList.get(position).getId());
        bundle.putString("price", mBeanALList.get(position).getPrice_total() + "");
        bundle.putString("type", "shop");
        bundle.putString("order_type", "gw");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClickReBuy(ShopOrderBeanTypeBean item, int position) {
        //再次购买
        //useless
    }

    @Override
    public void onClickConfirm(ShopOrderBeanTypeBean item, int position) {
        //确认收货
        //useless
    }

    @Override
    public void onClickPinjia(ShopOrderBeanTypeBean item, int position) {
        //评价
        //useless
    }

    @Override
    public void onClickDelete(ShopOrderBeanTypeBean item, final int position) {
        //删除订单
        //删除接口
        new CommomDialog(mActivity, R.style.my_dialog_DimEnabled, "确认要删除吗？", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    mPresenter.getOrderDetete(mBeanALList.get(position).getId());
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        }).show();//.setTitle("提示")

    }

    @Override
    public void onClickItemMultiImage(ShopOrderBeanTypeBean item, int position) {
        Intent intent = new Intent(mActivity, ShopOrderDetailActivityNew.class);
        intent.putExtra("id", mBeanALList.get(position).getId());
        intent.putExtra("p_m_id", mBeanALList.get(position).getP_m_id());
        intent.putExtra("status", (type + 1) + "");
        startActivity(intent);
    }

    /**
     * 详情界面回调退款 评价 删除
     * //
     *
     * @param
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void back(XorderDetailBean info) {
        if (info != null) {
            rel_no_data.setVisibility(View.GONE);
            //1删除 2.评价 3.退款 收货  4 支付成功 5收货
            if (info.getBack_type() == 1) {//删除
                for (int i = 0; i < mBeanALList.size(); i++) {
                    if (mBeanALList.get(i).getId().equals(info.getId())) {
                        mBeanALList.remove(i);
                        if (mBeanALList.size()==1){
                            rel_no_data.setVisibility(View.VISIBLE);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            } else if (info.getBack_type() == 2) {//评价
                //待评价刷新一下
               /* for (int i = 0; i < mBeanALList.size(); i++) {
                    if (mBeanALList.get(i).getImg().size() == 1) {
                        mBeanALList.remove(i);
                    } else {
                        for (int i1 = 0; i1 < mBeanALList.get(i).getImg().size(); i1++) {
                            if (mBeanALList.get(i).getImg().get(i1).getInfo_id().equals(info.getId())) {
                                mBeanALList.get(i).getImg().remove(i1);
                            }
                        }
                    }
                }*/
                // EventBus.getDefault().post("pj");
               // adapter.notifyDataSetChanged();
            } else if (info.getBack_type() == 3) {//退款
//                EventBus.getDefault().post("tk_sh");
//                adapter.notifyDataSetChanged();
                for (int i = 0; i < mBeanALList.size(); i++) {
                    if (mBeanALList.get(i).getImg().size() == 1) {
                        mBeanALList.remove(i);
                        rel_no_data.setVisibility(View.VISIBLE);
                    } else {
                        for (int i1 = 0; i1 < mBeanALList.get(i).getImg().size(); i1++) {
                            if (mBeanALList.get(i).getImg().get(i1).getInfo_id().equals(info.getId())) {
                                mBeanALList.get(i).getImg().remove(i1);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            } else if (info.getBack_type() == 4) {//订单中支付成功
//                EventBus.getDefault().post("zf");
//                adapter.notifyDataSetChanged();
            } else if (info.getBack_type() == 5) {// 收货
//                EventBus.getDefault().post("zf");
//                adapter.notifyDataSetChanged();
                for (int i = 0; i < mBeanALList.size(); i++) {
                    if (mBeanALList.get(i).getImg().size() == 1) {
                        mBeanALList.remove(i);
                        rel_no_data.setVisibility(View.VISIBLE);
                    } else {
                        for (int i1 = 0; i1 < mBeanALList.get(i).getImg().size(); i1++) {
                            if (mBeanALList.get(i).getImg().get(i1).getInfo_id().equals(info.getId())) {
                                mBeanALList.get(i).getImg().remove(i1);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 删除订单
     *
     * @param status
     * @param msg
     */
    @Override
    public void onOrderDelete(int status, String id, String msg) {
        if (status == 1) {
            for (int i = 0; i < mBeanALList.size(); i++) {
                if (mBeanALList.get(i).getId().equals(id)) {
                    mBeanALList.remove(i);
                }
            }
            adapter.notifyDataSetChanged();
          /*  XorderDetailBean XorderDetail = new XorderDetailBean();
            XorderDetail.setId(id);
            XorderDetail.setBack_type(1);
            EventBus.getDefault().post(XorderDetail);*/
        } else {
            SmartToast.showInfo(msg);
        }

    }
}
