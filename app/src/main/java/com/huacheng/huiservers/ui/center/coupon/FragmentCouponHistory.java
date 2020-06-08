package com.huacheng.huiservers.ui.center.coupon;

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
import com.huacheng.huiservers.model.ModelCouponNew;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description:优惠券历史记录
 * created by wangxiaotao
 * 2020/6/5 0005 16:44
 */
public class FragmentCouponHistory extends BaseFragment implements CouponListAdapter.OnClickRightBtnListener {
    SharePrefrenceUtil prefrenceUtil;
    private int total_Page = 1;
    private int type;


    private SmartRefreshLayout refreshLayout;
    private PagingListView listView;
    private View rel_no_data;
    List<ModelCouponNew> mData = new ArrayList<>();
    private int page = 1;
    private CouponListAdapter adapter;
    private boolean isInit = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
    }

    @Override
    public void initView(View view) {
        prefrenceUtil=new SharePrefrenceUtil(mActivity);
        view.findViewById(R.id.status_bar).setVisibility(View.GONE);
        view.findViewById(R.id.rl_title).setVisibility(View.GONE);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        listView = view.findViewById(R.id.listview);
        //2是已使用优化券 3是已过期优化券
        adapter= new CouponListAdapter(mContext,R.layout.item_coupon_list_new,mData,type==0?2:3,this);
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
        if (type==0){
            params.put("type","1");
        }else {
            params.put("type","2");
        }
        params.put("p", page + "");
        MyOkHttp.get().get(ApiHttpClient.COUPON_HISTORY, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                listView.setIsLoading(false);
                if (JsonUtil.getInstance().isSuccess(response)) {

                    ModelCouponNew model = (ModelCouponNew) JsonUtil.getInstance().parseJsonFromResponse(response, ModelCouponNew.class);
                    if (model!=null&&model.getList() != null &&model.getList().size() > 0) {
                        if (page == 1) {
                            mData.clear();
                            listView.post(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setSelection(0);
                                }
                            });
                        }
                        mData.addAll(model.getList());
                        page++;
                        total_Page = model.getTotalPages();// 设置总页数
                        rel_no_data.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        if (page > total_Page) {

                            listView.setHasMoreItems(false);
                        } else {

                            listView.setHasMoreItems(true);
                        }
                    } else {
                        if (page == 1) {
                            mData.clear();
                            rel_no_data.setVisibility(View.VISIBLE);
//                            img_data.setBackgroundResource(R.mipmap.bg_no_shop_order_data);
//                            tv_text.setText("暂无订单");
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
    public void onClickRightBtn(ModelCouponNew item, int position, int type) {
        //useless
    }
}
