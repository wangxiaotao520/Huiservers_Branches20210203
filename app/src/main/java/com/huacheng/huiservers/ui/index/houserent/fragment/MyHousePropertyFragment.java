package com.huacheng.huiservers.ui.index.houserent.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.HouseListBean;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.index.houserent.HouserentDetailActivity;
import com.huacheng.huiservers.ui.index.houserent.adapter.MyHouseRentListAdapter;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.widget.loadmorelistview.PagingListView;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:我的房屋 Fragment
 * Author: badge
 * Create: 2018/11/7 11:52
 */
public class MyHousePropertyFragment extends BaseFragment {


    @BindView(R.id.swipe_myhouse_property)
    SwipeRefreshLayout swipeMyhouseProperty;
    @BindView(R.id.listView)
    PagingListView listView;
    @BindView(R.id.rel_no_data)
    RelativeLayout relNoData;

    Unbinder unbinder;

    private int type = 0;
    private int jump_type = 1;//0跳售房 1跳租房
    private boolean isInit = false;
    private int page = 1;//当前页
    int totalPage = 0;//总页数

    int house_type = 0;
    MyHouseRentListAdapter houseRentListAdapter;
    private List<HouseListBean> mDatas = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle args = getArguments();
        type = args.getInt("type");//0为售房 1为租房
        jump_type = args.getInt("jump_type");//0是第一条 来的0为第二条跳转来的
    }


    /**
     * 选中后自动刷新
     */
    public void selected_init() {
        if (!isInit) {
            isInit = true;
            if (swipeMyhouseProperty != null) {
                swipeMyhouseProperty.setRefreshing(true);
                listenerOnRefresh();
            }

        }
    }

    /**
     * 刷新
     */
    private void listenerOnRefresh() {
        page = 1;
        getHouseList();
    }

    /**
     * 获取列表
     */
    private void getHouseList() {
        HashMap<String, String> params = new HashMap<>();
        SharedPreferences sharedpre = mActivity.getSharedPreferences("login", Context.MODE_PRIVATE);
        String login_uid = sharedpre.getString("login_uid", "");
        if (!StringUtils.isEmpty(login_uid)) {
            params.put("user_id", login_uid);
        }
        if (type == 0) {
            house_type = 2;//售房
        } else {
            house_type = 1;//租房
        }
        params.put("house_type", house_type + "");
        params.put("page", page + "");
        MyOkHttp.get().post(ApiHttpClient.PERSONALHOUSE, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (swipeMyhouseProperty != null) {
                    swipeMyhouseProperty.setRefreshing(false);
                }
                listView.setIsLoading(false);
//                listView.setHasMoreItems(false);
                if (JsonUtil.getInstance().isSuccess(response)) {

                    HouseListBean houseList = (HouseListBean) JsonUtil.getInstance().parseJsonFromResponse(response, "data", HouseListBean.class);
                    if (houseList != null && houseList.getHouseList() != null && houseList.getHouseList().size() > 0) {

                        List<HouseListBean> houseLists = houseList.getHouseList();

                        relNoData.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);

                        if (page == 1) {
                            mDatas.clear();
                        }
                        mDatas.addAll(houseLists);
                        page++;
                        totalPage = houseList.getCountPage();
                        if (page > totalPage) {
                            //listView.setIsLoading(false);
                            listView.setHasMoreItems(false);
                        } else {
//                            listView.setIsLoading(true);
                            listView.setHasMoreItems(true);
                        }

                    } else {
                        if (page == 1) {
                            listView.setVisibility(View.GONE);
                            relNoData.setVisibility(View.VISIBLE);
                            mDatas.clear();
                        }
//                        listView.setIsLoading(false);
                        listView.setHasMoreItems(false);
                    }
                    houseRentListAdapter.notifyDataSetChanged();
//                    listView.setHasMoreItems(true);
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
//                listView.setIsLoading(false);
                listView.setHasMoreItems(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);

        /*if (swipeMyhouseProperty != null) {
            swipeMyhouseProperty.setEnabled(true);
        }*/
        // 设置刷新控件颜色
        swipeMyhouseProperty.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        houseRentListAdapter = new MyHouseRentListAdapter(mActivity, mContext, R.layout.item_myhouse_rent_list, type, mDatas);
        listView.setAdapter(houseRentListAdapter);
        if (houseRentListAdapter != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(mContext, HouserentDetailActivity.class);
                    Bundle bundle = new Bundle();
                    if (type == 0) {
                        house_type = 2;//售房
                    } else {
                        house_type = 1;//租房
                    }
                    bundle.putInt("jump_type", house_type);
                    bundle.putString("id", mDatas.get(i).getHouseid());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        swipeMyhouseProperty.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                listenerOnRefresh();
                // 延时1s关闭下拉刷新
                swipeMyhouseProperty.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeMyhouseProperty != null && swipeMyhouseProperty.isRefreshing()) {
                            swipeMyhouseProperty.setRefreshing(false);
                        }
                    }
                }, 500);

            }
        });

        if (jump_type==1){//首先展示给用户租房
            if (type == 1) {
                swipeMyhouseProperty.setRefreshing(true);
                isInit = true;
                listenerOnRefresh();
            }
        }else {
            //首先展示给用户售房列表
            if (type == 0) {
                swipeMyhouseProperty.setRefreshing(true);
                isInit = true;
                listenerOnRefresh();
            }
        }

        listView.setHasMoreItems(false);
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {

                if (page <= totalPage) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listView.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getHouseList();

                                        }
                                    }, 500);
                                }
                            });
                        }
                    }).start();

                }

            }
        });
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_myhouse_property;
    }
}
