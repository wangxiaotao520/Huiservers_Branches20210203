package com.huacheng.huiservers.ui.circle;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelCircle;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.circle.bean.CircleDetailBean;
import com.huacheng.huiservers.ui.fragment.adapter.CircleListAdapter;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 12/13
 * 我的邻里
 */

public class MyCircleActivity extends BaseActivity implements CircleListAdapter.onItemDeleteListener {

    protected int page = 1;
    protected ListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    private SharePrefrenceUtil sharePrefrenceUtil;
    // private CircleAdapter mcircle5Adapter;
    private CircleListAdapter adapter;
    private List<ModelCircle> mDatas = new ArrayList<>();

    @Override
    protected void initView() {
        findTitleViews();
        sharePrefrenceUtil = new SharePrefrenceUtil(this);
        titleName.setText("我的邻里");
        mListview = findViewById(R.id.listview);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRelNoData = findViewById(R.id.rel_no_data);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);


        adapter = new CircleListAdapter(this, R.layout.item_circle_list, mDatas, this,1);
        mListview.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        requestData();
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mListview.post(new Runnable() {
                    @Override
                    public void run() {
                        mListview.setSelection(0);
                    }
                });
                requestData();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                requestData();
            }
        });
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MyCircleActivity.this, CircleDetailsActivity.class);
                intent.putExtra("id", mDatas.get(position).getId());
                intent.putExtra("mPro", mDatas.get(position).getIs_pro());
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_list_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId())) {
            params.put("community_id", sharePrefrenceUtil.getXiaoQuId());
        }
        params.put("p", page + "");
        MyOkHttp.get().post(ApiHttpClient.GET_USER_SOCIAL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelCircle> mlist = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelCircle.class);
                    if (mlist != null && mlist.size() > 0) {
                        mRelNoData.setVisibility(View.GONE);
                        if (page == 1) {
                            mDatas.clear();
                        }
                        mDatas.addAll(mlist);
                        page++;
                        if (page > mlist.get(0).getTotal_Pages()) {
                            mRefreshLayout.setEnableLoadMore(false);
                        } else {
                            mRefreshLayout.setEnableLoadMore(true);
                        }
                    } else {
                        if (page == 1) {
                            mRelNoData.setVisibility(View.VISIBLE);
                            mDatas.clear();
                        }
                        mRefreshLayout.setEnableLoadMore(false);
                    }
                    adapter.notifyDataSetChanged();

                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    mRefreshLayout.setEnableLoadMore(false);
                }
            }
        });

    }

    @Override
    public void onDeleteClick(final String id) {
        new CommomDialog(MyCircleActivity.this, R.style.my_dialog_DimEnabled, "删除此信息吗？", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    SocialDel(id);
                    dialog.dismiss();
                }

            }
        }).show();//.setTitle("提示")
    }

    /**
     * 删除邻里
     */
    private void SocialDel(final String id) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("social_id", id);
        MyOkHttp.get().post(ApiHttpClient.SOCIAL_DELETE, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    //删除
                    ModelCircle modelCircle_del = null;
                    for (int i = 0; i < mDatas.size(); i++) {
                        ModelCircle modelCircle = mDatas.get(i);
                        if (modelCircle.getId().equals(id)) {
                            modelCircle_del = modelCircle;
                        }
                    }
                    if (modelCircle_del != null) {
                        mDatas.remove(modelCircle_del);
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
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
                    for (int i = 0; i < mDatas.size(); i++) {
                        ModelCircle modelCircle = mDatas.get(i);
                        if (modelCircle.getId().equals(mCirclebean.getId())) {
                            int i1 = Integer.parseInt(modelCircle.getReply_num());
                            modelCircle.setReply_num((i1 + 1) + "");
                        }
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                } else if (type == 1) {
                    //删除评论
                    for (int i = 0; i < mDatas.size(); i++) {
                        ModelCircle modelCircle = mDatas.get(i);
                        if (modelCircle.getId().equals(mCirclebean.getId())) {
                            int i1 = Integer.parseInt(modelCircle.getReply_num());
                            modelCircle.setReply_num((i1 - 1) + "");
                        }
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                } else if (type == 2) {//阅读数
                    for (int i = 0; i < mDatas.size(); i++) {
                        ModelCircle modelCircle = mDatas.get(i);
                        if (modelCircle.getId().equals(mCirclebean.getId())) {
                            int i1 = Integer.parseInt(modelCircle.getClick());
                            modelCircle.setClick((i1 + 1) + "");
                        }
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
