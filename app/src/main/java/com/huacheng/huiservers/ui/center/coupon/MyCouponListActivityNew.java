package com.huacheng.huiservers.ui.center.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelCouponNew;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.huiservers.ui.shop.ShopListActivity;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.json.JsonUtil;
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
 * Description: 我的优惠券 更多优惠券
 * created by wangxiaotao
 * 2020/6/5 0005 14:59
 */
public class MyCouponListActivityNew extends BaseActivity implements CouponListAdapter.OnClickRightBtnListener {
    private int total_Page = 1;

    private SmartRefreshLayout refreshLayout;
    private PagingListView listView;
    private View rel_no_data;
    List<ModelCouponNew> mData = new ArrayList<>();
    private int page = 1;
    CouponListAdapter adapter ;
    private RelativeLayout rl_coupon_history;
    private RelativeLayout rl_more_coupon;
    private int jump_type = 1;//1.我的优惠券 2.更多优惠券
    private LinearLayout ll_bottom;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        findTitleViews();
        if (jump_type==1){
            titleName.setText("我的优惠券");
        }else {
            titleName.setText("优惠券");
        }

        refreshLayout = findViewById(R.id.refreshLayout);
        rel_no_data = findViewById(R.id.rel_no_data);

        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        listView = findViewById(R.id.listview);
        if(jump_type==1){
            adapter= new CouponListAdapter(this,R.layout.item_coupon_list_new,mData,0,this);
        }else {
            adapter= new CouponListAdapter(this,R.layout.item_coupon_list_new,mData,1,this);
        }
        listView.setAdapter(adapter);
        ll_bottom = findViewById(R.id.ll_bottom);
        rl_coupon_history = findViewById(R.id.rl_coupon_history);
        rl_more_coupon = findViewById(R.id.rl_more_coupon);
        if (jump_type==2){
            ll_bottom.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (jump_type==1){
                    //TODO 点击去使用
                }else {
                    // 立即领取
                }

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
        rl_coupon_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //优化券历史
                Intent intent = new Intent(mContext, CouponHistoryActivity.class);
                startActivity(intent);
            }
        });
        rl_more_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更多优化券
                Intent intent = new Intent(mContext, MyCouponListActivityNew.class);
                intent.putExtra("jump_type",2);
                startActivity(intent);
            }
        });
    }
    /**
     * 请求数据
     */
    private void requestData() {
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        if (jump_type==1){
            //1是我的优惠券接口,2是更多优化券接口
            params.put("type","1");
        }else {
            params.put("type","2");
        }
        MyOkHttp.get().get(ApiHttpClient.MY_COUPON_LIST, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                listView.setIsLoading(false);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List <ModelCouponNew>list = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelCouponNew.class);
                    if (list != null && list.size() > 0) {
                        mData.clear();
                        listView.post(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setSelection(0);
                                }
                            });
                        mData.addAll(list);
                        rel_no_data.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        listView.setHasMoreItems(false);
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
    protected int getLayoutId() {
        return R.layout.activity_my_coupon_list;
    }

    @Override
    protected void initIntentData() {
       jump_type= this.getIntent().getIntExtra("jump_type",1);
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 通行证event
     *
     * @param info
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void back(ModelCouponNew info) {
        if(jump_type==1){
            //我的优惠券时才刷新
            if (refreshLayout!=null){
                refreshLayout.autoRefresh();
            }
        }
    }

    @Override
    public void onClickRightBtn(ModelCouponNew item, int position, int type) {

        if (jump_type==1){
            // 点击去使用
            gotoUseCoupon(item);
        }else {
            //立即领取
            couponAdd( item,  position,  type);
        }
    }

    private void gotoUseCoupon(ModelCouponNew item) {
        if (!"0".equals(item.getShop_id())) {//跳转到商品详情页
            Intent intent=new Intent();
            intent.setClass(this, ShopDetailActivityNew.class);
            Bundle bundle = new Bundle();
            bundle.putString("shop_id", item.getShop_id()+"");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (!"0".equals(item.getShop_cate())) {//跳转到商城列表相应的分类列表页
            Intent intent=new Intent();
            intent.setClass(mContext, ShopListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("cateID", item.getShop_cate()+"");
                /*bundle.putString("shop_name", couponBean.getCategory_name());
                bundle.putString("isbool", "cate");*/
            intent.putExtras(bundle);
            startActivity(intent);
        } else {//跳转到商城列表所有商品列表页
            Intent intent=new Intent();
            intent.setClass(this, ShopListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("cateID", "");
                /*bundle.putString("shop_name", "所有商品");
                bundle.putString("isbool", "zhuanqu");*/
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    /**
     * 领取优惠券
     * @param item
     * @param position
     * @param type
     */
    private void couponAdd(ModelCouponNew item, final int position, int type) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("coupon_id",item.getId()+"");
        MyOkHttp.get().post( ApiHttpClient.COUPON_ADD, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response,"领取成功"));
                   mData.remove(position);
                   adapter.notifyDataSetChanged();
                   if (mData.size()==0){
                       rel_no_data.setVisibility(View.VISIBLE);
                   }
                   EventBus.getDefault().post(new ModelCouponNew());
                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response,"领取失败"));
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }
}
