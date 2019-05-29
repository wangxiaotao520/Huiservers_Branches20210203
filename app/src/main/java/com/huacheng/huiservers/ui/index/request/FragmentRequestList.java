package com.huacheng.huiservers.ui.index.request;

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
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelRequest;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.huiservers.ui.index.request.adapter.AdapterRequestList;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description: 投诉建议列表
 * created by wangxiaotao
 * 2019/5/8 0008 上午 9:30
 */
public class FragmentRequestList extends BaseFragment implements AdapterView.OnItemClickListener {
    protected int page = 1;
    protected ListView mListview;
    protected SmartRefreshLayout mRefreshLayout;
    protected RelativeLayout mRelNoData;
    List<ModelRequest> mDatas = new ArrayList<>();
    private AdapterRequestList adapter;
    private int type = 1;
    private View title;
    private boolean isInit = false;
    String community_id = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      //  Bundle arguments = getArguments();
      //  type = arguments.getInt("type");
    }

    @Override
    public void initView(View view) {
        title = view.findViewById(R.id.title_bar);
        title.setVisibility(View.GONE);
        mListview = view.findViewById(R.id.listview);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        mRelNoData = view.findViewById(R.id.rel_no_data);

        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
        adapter = new AdapterRequestList(mActivity, R.layout.item_request_list, mDatas);
        mListview.setAdapter(adapter);
    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isInit = true;
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
        mListview.setOnItemClickListener(this);
    }

    private void requestData() {
        HashMap<String, String> params = new HashMap<>();


        MyOkHttp.get().post(ApiHttpClient.FEED_BACK_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelRequest info = (ModelRequest) JsonUtil.getInstance().parseJsonFromResponse(response, ModelRequest.class);
                    if (info != null) {
                        if (info.getList() != null && info.getList().size() > 0) {
                            mRelNoData.setVisibility(View.GONE);
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mDatas.addAll(info.getList());
                            page++;
                            if (page > info.getTotalPages()) {
                                mRefreshLayout.setEnableLoadMore(false);
                            } else {
                                mRefreshLayout.setEnableLoadMore(true);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                mRelNoData.setVisibility(View.VISIBLE);
                                mDatas.clear();
                            }
                            mRefreshLayout.setEnableLoadMore(false);
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
    public void initData(Bundle savedInstanceState) {
        //  showDialog(smallDialog);
        // requestData();
        if (type == 1) {
            mRefreshLayout.autoRefresh();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_list_layout;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mActivity, RequesDetailActivity.class);
        intent.putExtra("id",mDatas.get(position).getId()+"");
        startActivity(intent);

    }

    public void init(String community_id) {
        if (!isInit) {
            this.community_id = community_id;
            if (mRefreshLayout != null) {
                mRefreshLayout.autoRefresh();
            }
        } else {
            if (!this.community_id.equals(community_id)) {
                this.community_id = community_id;
                if (mRefreshLayout != null) {
                    mRefreshLayout.autoRefresh();
                }
            }
        }
    }
}
