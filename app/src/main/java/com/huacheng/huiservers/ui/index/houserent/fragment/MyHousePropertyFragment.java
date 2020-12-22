package com.huacheng.huiservers.ui.index.houserent.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelMyHouse;
import com.huacheng.huiservers.model.ModelMyHouseList;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.index.houserent.HouserentDetailActivity;
import com.huacheng.huiservers.ui.index.houserent.RentSellReleaseActivity;
import com.huacheng.huiservers.ui.index.houserent.adapter.MyHouseRentListAdapter;
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
    // private List<HouseListBean> mDatas = new ArrayList<>();
    private List<ModelMyHouseList> mDatasList = new ArrayList<>();

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
        //getHouseList();
        requestData();
    }

    /**
     * 获取列表
     */
//    private void getHouseList() {
//        HashMap<String, String> params = new HashMap<>();
//        SharedPreferences sharedpre = mActivity.getSharedPreferences("login", Context.MODE_PRIVATE);
//        String login_uid = sharedpre.getString("login_uid", "");
//        if (!StringUtils.isEmpty(login_uid)) {
//            params.put("user_id", login_uid);
//        }
//        if (type == 0) {
//            house_type = 2;//售房
//        } else {
//            house_type = 1;//租房
//        }
//        params.put("house_type", house_type + "");
//        params.put("page", page + "");
//        MyOkHttp.get().post(ApiHttpClient.PERSONALHOUSE, params, new JsonResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, JSONObject response) {
//                if (swipeMyhouseProperty != null) {
//                    swipeMyhouseProperty.setRefreshing(false);
//                }
//                listView.setIsLoading(false);
//                if (JsonUtil.getInstance().isSuccess(response)) {
//
//                    HouseListBean houseList = (HouseListBean) JsonUtil.getInstance().parseJsonFromResponse(response, "data", HouseListBean.class);
//                    if (houseList != null && houseList.getHouseList() != null && houseList.getHouseList().size() > 0) {
//
//                        List<HouseListBean> houseLists = houseList.getHouseList();
//
//                        relNoData.setVisibility(View.GONE);
//                        listView.setVisibility(View.VISIBLE);
//
//                        if (page == 1) {
//                            mDatas.clear();
//                        }
//                        mDatas.addAll(houseLists);
//                        page++;
//                        totalPage = houseList.getCountPage();
//                        if (page > totalPage) {
//                            listView.setHasMoreItems(false);
//                        } else {
//                            listView.setHasMoreItems(true);
//                        }
//
//                    } else {
//                        if (page == 1) {
//                            listView.setVisibility(View.GONE);
//                            relNoData.setVisibility(View.VISIBLE);
//                            mDatas.clear();
//                        }
//                        listView.setHasMoreItems(false);
//                    }
//                    houseRentListAdapter.notifyDataSetChanged();
//                } else {
//                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
//                    SmartToast.showInfo(msg);
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, String error_msg) {
//                listView.setHasMoreItems(false);
//                SmartToast.showInfo("网络异常，请检查网络设置");
//            }
//        });
//
//    }

    /**
     * 请求数据
     */
    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (type == 1) {
            params.put("state", "1");
        } else if (type == 0) {
            params.put("state", "2");
        }
        params.put("p", page + "");
        params.put("check", "0,1,2,4,5,6");//  0未审核 1上架  2下降 3删除 4下降已出租 5下架已出售6已拒绝审核
        params.put("add_id", BaseApplication.getUser().getUid() + "");
        params.put("property", "1");//除了和昌都需要传这个参数
        params.put("request", "1");//除了和昌都需要传这个参数
        MyOkHttp.get().get(ApiHttpClient.GET_HOUST_LIST, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (swipeMyhouseProperty != null) {
                    swipeMyhouseProperty.setRefreshing(false);
                }
                listView.setIsLoading(false);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelMyHouse myHouse = (ModelMyHouse) JsonUtil.getInstance().parseJsonFromResponse(response, ModelMyHouse.class);
                    if (myHouse != null) {
                        if (myHouse.getList() != null && myHouse.getList().size() > 0) {
                            relNoData.setVisibility(View.GONE);
                            listView.setVisibility(View.VISIBLE);
                            if (page == 1) {
                                mDatasList.clear();
                            }
                            mDatasList.addAll(myHouse.getList());
                            page++;
                            totalPage = myHouse.getTotal_Pages();
                            if (page > totalPage) {
                                listView.setHasMoreItems(false);
                            } else {
                                listView.setHasMoreItems(true);
                            }
                        } else {
                            if (page == 1) {
                                listView.setVisibility(View.GONE);
                                relNoData.setVisibility(View.VISIBLE);
                                mDatasList.clear();
                            }
                            listView.setHasMoreItems(false);
                        }
                        houseRentListAdapter.notifyDataSetChanged();
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "获取数据失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
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

        houseRentListAdapter = new MyHouseRentListAdapter(mActivity, mContext, R.layout.item_myhouse_rent_list, type, mDatasList);
        listView.setAdapter(houseRentListAdapter);
        if (houseRentListAdapter != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    if (mDatasList.get(i).getCheck() == 0) {
                        SmartToast.showInfo("等待审核");
                    } else if (mDatasList.get(i).getCheck() == 6) {
                        Intent intent = new Intent(mContext, RentSellReleaseActivity.class);
                        if (type == 0) {
                            house_type = 2;//售房
                        } else {
                            house_type = 1;//租房
                        }
                        intent.putExtra("type", house_type);
                        intent.putExtra("tag", "edit");
                        intent.putExtra("house_id", mDatasList.get(i).getId());
                        startActivityForResult(intent,22);
                    } else {
                        Intent intent = new Intent(mContext, HouserentDetailActivity.class);
                        Bundle bundle = new Bundle();
                        if (type == 0) {
                            house_type = 2;//售房
                        } else {
                            house_type = 1;//租房
                        }
                        bundle.putInt("jump_type", house_type);
                        bundle.putString("id", mDatasList.get(i).getId());
                        bundle.putInt("isCommendHouse", 0);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

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

        if (jump_type == 1) {//首先展示给用户租房
            if (type == 1) {
                swipeMyhouseProperty.setRefreshing(true);
                isInit = true;
                listenerOnRefresh();
            }
        } else {
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
                                            //getHouseList();
                                            requestData();

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 22) {
            if (resultCode == mActivity.RESULT_OK) {
               listenerOnRefresh();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
