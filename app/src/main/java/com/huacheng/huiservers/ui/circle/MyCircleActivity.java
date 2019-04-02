package com.huacheng.huiservers.ui.circle;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.model.protocol.CommonProtocol;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.circle.bean.CircleDetailBean;
import com.huacheng.huiservers.ui.fragment.adapter.CircleAdapter;
import com.huacheng.huiservers.ui.fragment.listener.EndlessRecyclerOnScrollListener;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.view.RecyclerViewLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的邻里
 */

public class MyCircleActivity extends BaseActivityOld {

    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.title_name)
    TextView titleName;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.rel_no_data)
    RelativeLayout rel_no_data;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lin_top)
    LinearLayout linTop;

    private SharePrefrenceUtil sharePrefrenceUtil;

    List<BannerBean> bean = new ArrayList<BannerBean>();
    List<BannerBean> beans2 = new ArrayList<BannerBean>();
    ShopProtocol protocol = new ShopProtocol();
    private SharedPreferences preferencesLogin;

    int totalPage = 0;// 总页数
    private int page = 1;// 当前页
    private String login_type;
    private CircleAdapter mcircle5Adapter;
    private Dialog waitDialog;
    private boolean is_Requesting=false;
    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.circle_wo);
        ButterKnife.bind(this);
 //       SetTransStatus.GetStatus(this);
//        MyCookieStore.MyCircle_notify = 0;
        MyCookieStore.Circle_notify = 0;
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new RecyclerViewLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mcircle5Adapter = new CircleAdapter(beans2, MyCircleActivity.this);
        mRecyclerView.setAdapter(mcircle5Adapter);
        linTop.setFocusable(true);
        linTop.setFocusableInTouchMode(true);
        linTop.requestFocus();

        titleName.setText("我的邻里");
        sharePrefrenceUtil = new SharePrefrenceUtil(this);
        preferencesLogin = this.getSharedPreferences("login", 0);
        login_type = preferencesLogin.getString("login_type", "");
   //     waitDialog = WaitDialog.createLoadingDialog(this, "正在加载...");
        showDialog(smallDialog);
        getMySocialList("1");
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                linTop.setFocusable(true);
                linTop.setFocusableInTouchMode(true);
                linTop.requestFocus();
                // 刷新数据
                page=1;
                getMySocialList("1");

            }
        });

        // 设置加载更多监听
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (page <= totalPage&&!is_Requesting) {
                    mcircle5Adapter.setLoadState(mcircle5Adapter.LOADING);
                    getMySocialList(page + "");
                }
            }
        });

        linLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyCookieStore.MyCircle_notify = 0;
                finish();
            }
        });
    }



    // 我的邻里
    private void getMySocialList(String total_Pages) {
        is_Requesting=true;

        Url_info info = new Url_info(this);
        String url = info.get_user_Social + "community_id/" + sharePrefrenceUtil.getXiaoQuId()+"/p/"+page;
        RequestParams requestParams = new RequestParams();

        MyOkHttp.get().post(url, null, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                is_Requesting=false;
                hideDialog(smallDialog);
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                bean = protocol.getWoSocial(response);
                if (bean.size() > 0) {
                    rel_no_data.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (page == 1) {
                        beans2.clear();
                    }
                    beans2.addAll(bean);
                    totalPage = beans2.get(0).getTotal_Pages();
                    page++;
                    totalPage = Integer.valueOf(beans2.get(0).getTotal_Pages());
                    if (page > totalPage) {
                        mcircle5Adapter.setLoadState(mcircle5Adapter.LOADING_END);
                    } else {
                        mcircle5Adapter.setLoadState(mcircle5Adapter.LOADING_COMPLETE);
                    }
                    mcircle5Adapter.setmOnItemDeleteListener(new CircleAdapter.onItemDeleteListener() {
                        @Override
                        public void onDeleteClick(final int i) {
                            new CommomDialog(MyCircleActivity.this, R.style.my_dialog_DimEnabled, "删除此信息吗？", new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    if (confirm) {
                                        SocialDel(beans2.get(i).getId());
                                        dialog.dismiss();
                                    }

                                }
                            }).show();//.setTitle("提示")
                        }
                    });
                    mcircle5Adapter.setItemClickListener(new CircleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {

                            Intent intent = new Intent();
                            intent.setClass(MyCircleActivity.this, CircleDetailsActivity.class);
                            intent.putExtra("id", beans2.get(position).getId());
                            startActivity(intent);
                        }
                    });

                    mcircle5Adapter.notifyDataSetChanged();

                } else {

                    if (page == 1) {
                        mRecyclerView.setVisibility(View.GONE);
                        rel_no_data.setVisibility(View.VISIBLE);
                    } else {
                        if (mcircle5Adapter != null) {
                            mcircle5Adapter.setLoadState(mcircle5Adapter.LOADING_END);
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                is_Requesting=false;
                hideDialog(smallDialog);
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });



    }

    /**
     * 删除邻里
     *
     * @param socialID
     */
    private void SocialDel(final String socialID) {
        showDialog(smallDialog);
        Url_info urlInfo = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("social_id", socialID);

        new HttpHelper(urlInfo.SocialDel, params, this) {

            @Override
            protected void setData(String json) {

                String result = new CommonProtocol().getResult(json);
                if ("1".equals(result)) {
                //    getMySocialList("1");
                    BannerBean remove_bean = null;
                    for (int i = 0; i < beans2.size(); i++) {
                        BannerBean bannerBean = beans2.get(i);
                        if (bannerBean.getId().equals(socialID)) {
                            remove_bean=bannerBean;
                        }
                    }
                    if (remove_bean!=null){
                        beans2.remove(remove_bean);
                    }
                    if (beans2.size()==0){
                        page=1;
                        getMySocialList("1");
                    }else {
                        hideDialog(smallDialog);
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

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 11) {
                page = 1;
                beans2.clear();
                getMySocialList("1");
                mcircle5Adapter.notifyDataSetChanged();
            }
        }
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
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


}
