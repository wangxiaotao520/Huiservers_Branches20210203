package com.huacheng.huiservers.ui.fragment.circle;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.model.protocol.CommonProtocol;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseFragmentOld;
import com.huacheng.huiservers.ui.circle.CircleDetailsActivity;
import com.huacheng.huiservers.ui.circle.bean.CircleDetailBean;
import com.huacheng.huiservers.ui.fragment.adapter.CircleAdapter;
import com.huacheng.huiservers.ui.fragment.listener.EndlessRecyclerOnScrollListener;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.utils.LogUtils;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.RecyclerViewLayoutManager;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */

public class CircleTabFragment extends BaseFragmentOld {
    private RecyclerView mRecyclerView;
    private RelativeLayout rel_no_data;

    private String login_type;
    private SharePrefrenceUtil sharePrefrenceUtil;
    private SharedPreferences preferencesLogin;
    List<BannerBean> bean = new ArrayList<BannerBean>();
    List<BannerBean> beans2 = new ArrayList<BannerBean>();
    ShopProtocol protocol = new ShopProtocol();
    private CircleAdapter mcircle5Adapter;

    int totalPage = 0;// 总页数
    private int page = 1;// 当前页
    private LinearLayout lin_circle5;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Dialog waitDialog2;

    private String mCid;
    String isFirst = "";
    public boolean isInit = false;
    private String type = "0";
    private int mPro = 0;
    private boolean is_Requesting=false;
    private int type_position=0;//首页哪个点击被选中

    public CircleTabFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        mCid = arguments.getString("mCid");
        type = arguments.getString("type");
        mPro = arguments.getInt("mPro");
        type_position = arguments.getInt("type_position");
    }
//
//    public CircleTabFragment(String cid) {
//        super();
//        this.mCid = cid;
//    }

    @Override
    protected void initView() {
        lin_circle5 = (LinearLayout) findViewById(R.id.lin_circle5);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        rel_no_data = (RelativeLayout) findViewById(R.id.rel_no_data);

        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        sharePrefrenceUtil = new SharePrefrenceUtil(getActivity());
        preferencesLogin = getActivity().getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");

        mRecyclerView.setLayoutManager(new RecyclerViewLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mcircle5Adapter = new CircleAdapter(beans2, getActivity());
        mRecyclerView.setAdapter(mcircle5Adapter);
        isFirst = "1";


        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(refreshlistener);

        // 设置加载更多监听
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (page <= totalPage&&!is_Requesting) {
                    mcircle5Adapter.setLoadState(mcircle5Adapter.LOADING);
                    getSocialList(mCid, page);
                }

            }
        });
        mcircle5Adapter.setItemClickListener(new CircleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (beans2!=null&&beans2.size()>0){
                    Intent intent = new Intent();
                    intent.setClass(mActivity, CircleDetailsActivity.class);
                    intent.putExtra("id", beans2.get(position).getId());
                    intent.putExtra("mPro", mPro);
                    startActivity(intent);
                }
            }
        });
        mcircle5Adapter.setmOnItemDeleteListener(new CircleAdapter.onItemDeleteListener() {
            @Override
            public void onDeleteClick(final int i) {
                new CommomDialog(getActivity(), R.style.my_dialog_DimEnabled, "删除此信息吗？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            if (beans2!=null&&beans2.size()>0){
                                SocialDel(beans2.get(i).getId());
                                dialog.dismiss();
                            }
                        }

                    }
                }).show();//.setTitle("提示")

            }
        });
        // 获取数据
        if (type.equals("0")&&type_position==0) {//第一页刷新
            // showDialog(smallDialog);
            //   getSocialList(mCid, page);
            //   isInit = true;
            isInit = true;
            swipeRefreshLayout.setRefreshing(true);
            refreshlistener.onRefresh();
        }
    }

    /**
     * 传入 XiaoQuId，c_id，p
     *
     * @param mCid
     */
    private void getSocialList(String mCid, final int total_Page) {
        is_Requesting=true;
        Url_info info = new Url_info(getActivity());

        if (!StringUtils.isEmpty(mCid)) {
            String url="";
            if (NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId())){
                url = info.getSocialList + "c_id/" + mCid + "/is_pro/" + mPro + "/p/" + total_Page;
            }else {
                 url = info.getSocialList + "community_id/" + sharePrefrenceUtil.getXiaoQuId() + "/c_id/" + mCid + "/is_pro/" + mPro + "/p/" + total_Page;
            }

            LogUtils.d("url==" + url);
            new HttpHelper(url, getActivity()) {
                @Override
                protected void setData(String json) {
                    is_Requesting=false;
                    hideDialog(smallDialog);
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                //    bean = protocol.getSocialList(json);
                    try {
                        JSONObject response=null;
                        response = new JSONObject(json);
                        bean= JsonUtil.getInstance().getDataArrayByName(response,"data",BannerBean.class);
                        if (JsonUtil.getInstance().isSuccess(response)){
                            if (bean!=null&&bean.size() > 0) {

                                rel_no_data.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.VISIBLE);
                                if (page == 1) {
                                    beans2.clear();
                                }

                                if (isFirst.equals("1")) {
                                    isFirst = "";
                                }

                                if (isFirst.equals("2")) {
                                    isFirst = "";
                                }
                                beans2.addAll(bean);
                                page++;
                                totalPage = beans2.get(0).getTotal_Pages();
                                if (page > totalPage) {
                                    if (mcircle5Adapter != null) {
                                        mcircle5Adapter.setLoadState(mcircle5Adapter.LOADING_END);
                                    }
                                } else {
                                    if (mcircle5Adapter != null) {
                                        mcircle5Adapter.setLoadState(mcircle5Adapter.LOADING_COMPLETE);
                                    }
                                }

                                mcircle5Adapter.notifyDataSetChanged();

                            } else {
                                if (isFirst.equals("1")) {
                                    isFirst = "";
                                }
                                if (isFirst.equals("2")) {
                                    isFirst = "";
                                }
                                if (page == 1) {
                                    mRecyclerView.setVisibility(View.GONE);
                                    rel_no_data.setVisibility(View.VISIBLE);
                                } else {
                                    if (mcircle5Adapter != null) {
                                        mcircle5Adapter.setLoadState(mcircle5Adapter.LOADING_END);
                                    }
                                }
                            }
                        }else {
                            if (page == 1) {
                                mRecyclerView.setVisibility(View.GONE);
                                rel_no_data.setVisibility(View.VISIBLE);
                            } else {
                                if (mcircle5Adapter != null) {
                                    mcircle5Adapter.setLoadState(mcircle5Adapter.LOADING_END);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                protected void requestFailure(Exception error, String msg) {
                    is_Requesting=false;
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    if (mcircle5Adapter != null) {
                        mcircle5Adapter.setLoadState(mcircle5Adapter.LOADING_COMPLETE);
                    }
                    hideDialog(smallDialog);
                    if (isFirst.equals("1")) {
                        isFirst = "";
                    }
                    if (isFirst.equals("2")) {
                        isFirst = "";
                    }
                    SmartToast.showInfo("网络异常，请检查网络设置");
                }
            };
        } else {
            if (isFirst.equals("1")) {
                isFirst = "";
            }
            if (isFirst.equals("2")) {
                isFirst = "";
            }
            rel_no_data.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

    }

    /**
     *
     *删除邻里
     * @param socialID
     */
    private void SocialDel(final String socialID) {
        showDialog(smallDialog);
        Url_info urlInfo = new Url_info(getActivity());
        RequestParams params = new RequestParams();
        params.addBodyParameter("social_id", socialID);

        new HttpHelper(urlInfo.SocialDel, params, getActivity()) {
            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                String result = new CommonProtocol().getResult(json);
                if (result.equals("1")) {
                    //    onResume();
                    //删除
                    BannerBean bannerBean_del = null;
                    for (int i = 0; i < beans2.size(); i++) {
                        BannerBean bannerBean = beans2.get(i);
                        if (bannerBean.getId().equals(socialID)) {
                            bannerBean_del = bannerBean;
                        }
                    }
                    if (bannerBean_del != null) {
                        beans2.remove(bannerBean_del);
                        if (mcircle5Adapter != null) {
                            mcircle5Adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    SmartToast.showInfo("删除失败");
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_circle_list;
    }

    /**
     * 选中后自动刷新
     */
    public void selected_init() {
        if (isInit == false) {
            //初始化
            isInit = true;
            if (swipeRefreshLayout!=null) {
                swipeRefreshLayout.setRefreshing(true);
                refreshlistener.onRefresh();
            }
        }
    }

    private SwipeRefreshLayout.OnRefreshListener refreshlistener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            lin_circle5.setFocusable(true);
            lin_circle5.requestFocus();
            lin_circle5.setFocusableInTouchMode(true);
            // 刷新数据
            page = 1;
            getSocialList(mCid, page);

        }
    };

    /**
     * 添加和删除评论的Eventbus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateItem(CircleDetailBean mCirclebean) {
        try {
            if (mCirclebean != null) {
                int type = mCirclebean.getType();
                if (type == 0) {
                    //添加评论
                    for (int i = 0; i < beans2.size(); i++) {
                        BannerBean bannerBean = beans2.get(i);
                        if (bannerBean.getId().equals(mCirclebean.getId())) {
                            int i1 = Integer.parseInt(bannerBean.getReply_num());
                            bannerBean.setReply_num((i1 + 1) + "");
                        }
                    }
                    if (mcircle5Adapter != null) {
                        mcircle5Adapter.notifyDataSetChanged();
                    }
                } else if (type == 1) {
                    //删除评论
                    for (int i = 0; i < beans2.size(); i++) {
                        BannerBean bannerBean = beans2.get(i);
                        if (bannerBean.getId().equals(mCirclebean.getId())) {
                            int i1 = Integer.parseInt(bannerBean.getReply_num());
                            bannerBean.setReply_num((i1 - 1) + "");
                        }
                    }
                    if (mcircle5Adapter != null) {
                        mcircle5Adapter.notifyDataSetChanged();
                    }
                } else if (type == 2) {//阅读数
                    for (int i = 0; i < beans2.size(); i++) {
                        BannerBean bannerBean = beans2.get(i);
                        if (bannerBean.getId().equals(mCirclebean.getId())) {
                            int i1 = Integer.parseInt(bannerBean.getClick());
                            bannerBean.setClick((i1 + 1) + "");
                        }
                    }
                    if (mcircle5Adapter != null) {
                        mcircle5Adapter.notifyDataSetChanged();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
