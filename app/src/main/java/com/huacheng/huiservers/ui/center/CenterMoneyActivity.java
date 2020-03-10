package com.huacheng.huiservers.ui.center;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.model.protocol.CenterProtocol;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.adapter.PersonMoneyAdapter;
import com.huacheng.huiservers.model.ListBean;
import com.huacheng.huiservers.ui.center.listener.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消费记录
 */
public class CenterMoneyActivity extends BaseActivityOld implements OnClickListener {

    private int page = 1;//当前页
    int totalPage = 0;//总页数
    private int count = 0;
    private TextView title_name;
    private LinearLayout lin_left;
    private CenterProtocol protocol = new CenterProtocol();
    private List<ListBean> beans = new ArrayList<ListBean>();
    private List<ListBean> bean;
    PersonMoneyAdapter adapter;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.rel_no_data)
    RelativeLayout relNoData;
    private boolean is_Requesting=false;
    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.person_my_money);
        ButterKnife.bind(this);
    //    SetTransStatus.GetStatus(this);//系统栏默认为黑色
        title_name = (TextView) findViewById(R.id.title_name);
        title_name.setText("消费记录");
        lin_left = (LinearLayout) findViewById(R.id.lin_left);
        lin_left.setOnClickListener(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonMoneyAdapter(CenterMoneyActivity.this, beans);
        recyclerview.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        showDialog(smallDialog);
        getdata();
        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                page = 1;
                getdata();


            }
        });

        // 设置加载更多监听
        recyclerview.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (page <= totalPage&&!is_Requesting) {
                    adapter.setLoadState(adapter.LOADING);
                    getdata();
                }
            }
        });
    }

    private void getdata() {
        is_Requesting=true;
        Url_info info = new Url_info(this);
        String url = info.my_wallet + "p/" + page + "/";
        new HttpHelper(url, this) {

            @Override
            protected void setData(String json) {
                is_Requesting=false;
                hideDialog(smallDialog);
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                bean = protocol.myMoney(json, "1");//1是个人中心账户
                if (bean.size()> 0) {
                    relNoData.setVisibility(View.GONE);
                    recyclerview.setVisibility(View.VISIBLE);
                    if (page == 1) {
                        beans.clear();
                    }
                    beans.addAll(bean);
                    totalPage = Integer.valueOf(beans.get(0).getTotal_Pages());// 设置总页数

                    page++;
                    totalPage = Integer.valueOf(beans.get(0).getTotal_Pages());
                    if (page > totalPage) {
                        adapter.setLoadState(adapter.LOADING_END);
                    } else {
                        adapter.setLoadState(adapter.LOADING_COMPLETE);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        recyclerview.setVisibility(View.GONE);
                        relNoData.setVisibility(View.VISIBLE);
                    } else {
                        if (adapter != null) {
                            adapter.setLoadState(adapter.LOADING_END);
                        }
                    }
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                is_Requesting=false;
                hideDialog(smallDialog);
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.lin_left:
                finish();
                break;

            default:
                break;
        }

    }

}
