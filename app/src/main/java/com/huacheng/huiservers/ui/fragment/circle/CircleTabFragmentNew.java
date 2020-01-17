package com.huacheng.huiservers.ui.fragment.circle;

import android.app.Dialog;
import android.content.Context;
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
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.circle.CircleDetailsActivity;
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
 * 类描述：邻里
 * 时间：2019/12/18 09:33
 * created by DFF
 */
public class CircleTabFragmentNew extends BaseFragment implements CircleListAdapter.onItemDeleteListener {
    private String mCid;
    private String type = "0";
    private int mPro = 0;
    private int type_position = 0;//首页哪个点击被选中
    public boolean isInit = false;       //页面是否进行了初始化
    private boolean isRequesting = false; //是否正在刷新
    private ListView mListView;
    private SmartRefreshLayout refreshLayout;
    private int page = 1;
    private RelativeLayout rel_no_data;
    private SharePrefrenceUtil sharePrefrenceUtil;
    private List<ModelCircle> mDatas = new ArrayList<>();
    private CircleListAdapter adapter;

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

    @Override
    public void initView(View view) {
        sharePrefrenceUtil = new SharePrefrenceUtil(getActivity());
        mListView = view.findViewById(R.id.listview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rel_no_data = view.findViewById(R.id.rel_no_data);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(true);

        adapter = new CircleListAdapter(getActivity(), R.layout.item_circle_list, mDatas, this,0);
        mListView.setAdapter(adapter);


    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isRequesting = true;
                requestData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRequesting = true;
                requestData();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(mActivity, CircleDetailsActivity.class);
                intent.putExtra("id", mDatas.get(position).getId());
                intent.putExtra("mPro", mPro+"");
                startActivity(intent);
            }
        });
    }

    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(sharePrefrenceUtil.getXiaoQuId())) {
            params.put("community_id", sharePrefrenceUtil.getXiaoQuId());
        }
        params.put("c_id", mCid);
        params.put("is_pro", mPro + "");
        params.put("p", page + "");
        MyOkHttp.get().get(ApiHttpClient.GET_SOCIAL_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelCircle> mlist = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelCircle.class);
                    if (mlist != null && mlist.size() > 0) {
                        rel_no_data.setVisibility(View.GONE);
                        if (page == 1) {
                            mDatas.clear();
                        }
                        mDatas.addAll(mlist);
                        page++;
                        if (page > mlist.get(0).getTotal_Pages()) {
                            refreshLayout.setEnableLoadMore(false);
                        } else {
                            refreshLayout.setEnableLoadMore(true);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        if (page == 1) {
                            rel_no_data.setVisibility(View.VISIBLE);
                            mDatas.clear();
                        }
                        refreshLayout.setEnableLoadMore(false);
                        adapter.notifyDataSetChanged();
                    }


                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                SmartToast.showInfo("网络异常，请检查网络设置");
                if (page == 1) {
                    refreshLayout.setEnableLoadMore(false);
                }
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        // 获取数据
        if (type.equals("0") && type_position == 0) {//第一页刷新
            isInit = true;
            refreshLayout.autoRefresh();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_circle_list;
    }

    /**
     * 选中后自动刷新
     */
    public void selected_init() {
        if (isInit == false) {
            //初始化
            isInit = true;
            if (refreshLayout != null) {
                refreshLayout.autoRefresh();
            }
        }
    }

    /**
     * 删除我发布的
     *
     * @param id
     */
    @Override
    public void onDeleteClick(final String id) {
        new CommomDialog(getActivity(), R.style.my_dialog_DimEnabled, "确定删除此信息吗？", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    SocialDel(id);
                    dialog.dismiss();
                }
            }
        }).show();//.setTitle("提示")

    }

    @Override
    public void onJumpPinglun(ModelCircle item) {

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

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
