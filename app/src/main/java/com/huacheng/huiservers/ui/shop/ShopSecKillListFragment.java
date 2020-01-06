package com.huacheng.huiservers.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.fragment.adapter.HomeListViewAdapter;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.shop.bean.ModelSeckill;
import com.huacheng.huiservers.utils.CommonMethod;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.json.JsonUtil;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 秒杀列表Fragment
 * created by wangxiaotao
 * 2020/1/6 0006 上午 8:50
 */
public class ShopSecKillListFragment extends BaseFragment implements HomeListViewAdapter.OnAddCartClickListener {
    SharePrefrenceUtil prefrenceUtil;
    private int total_Page=1;
    private int type;
    private String class_id;
    ModelSeckill modelSeckillBean;
    private SmartRefreshLayout refreshLayout;
    private PagingListView listView;
    private View rel_no_data;
    List<ModelShopIndex> mBeanALList = new ArrayList<>();
    private int page = 1;
    private HomeListViewAdapter adapter;
    private boolean isInit=false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
        class_id=arguments.getString("class_id");

    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.rl_title).setVisibility(View.GONE);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        listView = view.findViewById(R.id.listview);
        adapter = new HomeListViewAdapter(mActivity, R.layout.item_home_sec_kill, mBeanALList, this);
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
                Intent intent = new Intent(mContext, ShopDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("shop_id", mBeanALList.get(position).getId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
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
        if (type==0){
          refreshLayout.autoRefresh();
          isInit=true;
        }

    }

    /**
     * 请求数据
     */
    private void requestData() {
        prefrenceUtil= new SharePrefrenceUtil(mActivity);
        Url_info info = new Url_info(mActivity);
        String url=info.pro_discount_list  + "/is_star/" + "1" + "/p/" + page+"/class_id/"+class_id+"/province_cn/"+prefrenceUtil.getProvince_cn()+"/city_cn/"+prefrenceUtil.getCity_cn()+"/region_cn/"+prefrenceUtil.getRegion_cn();
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().get(url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                listView.setIsLoading(false);
                if (JsonUtil.getInstance().isSuccess(response)){

                    modelSeckillBean = (ModelSeckill) com.huacheng.libraryservice.utils.json.JsonUtil.getInstance().parseJsonFromResponse(response, ModelSeckill.class);
                    if (modelSeckillBean !=null){
                            if (modelSeckillBean.getList()!=null&&modelSeckillBean.getList().size()>0){
                                if (page==1){
                                    mBeanALList.clear();
                                    listView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            listView.setSelection(0);
                                        }
                                    });
                                }
                                mBeanALList.addAll(modelSeckillBean.getList());
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

                    }
                }else {
                    String msg = com.huacheng.libraryservice.utils.json.JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
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
    public void onAddCartClick(ModelShopIndex item,int position) {
        //点击添加购物车
        if ( ApiHttpClient.TOKEN==null||ApiHttpClient.TOKEN_SECRET==null) {
            Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
            mContext.startActivity(intent);

        } else {

            if (mBeanALList.get(position).getExist_hours().equals("2")) {
                SmartToast.showInfo("当前时间不在派送时间范围内");
            } else {
                if (mBeanALList.get(position) != null) {
                    new CommonMethod(mBeanALList.get(position), null, mContext).getShopLimitTag();
                }
            }

        }
    }
}
