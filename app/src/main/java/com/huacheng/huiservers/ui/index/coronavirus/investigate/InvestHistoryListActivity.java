package com.huacheng.huiservers.ui.index.coronavirus.investigate;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelInvestigateList;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Description: 问卷调查历史记录
 * created by wangxiaotao
 * 2020/2/21 0021 下午 4:32
 */
public class InvestHistoryListActivity extends BaseListActivity<ModelInvestigateList> {

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("历史记录");
        mAdapter = new InvestigateHistoryAdapter(this, R.layout.item_investigate_history,mDatas);
        mListview.setAdapter(mAdapter);
    }

    /**
     * 请求数据
     */
    @Override
    protected void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("p", page + "");

        MyOkHttp.get().post(ApiHttpClient.GET_INVESTIGATE_HISTORY_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelInvestigateList info = (ModelInvestigateList) JsonUtil.getInstance().parseJsonFromResponse(response, ModelInvestigateList.class);
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
                            mAdapter.notifyDataSetChanged();
                        } else {
                            if (page == 1) {
                                mRelNoData.setVisibility(View.VISIBLE);
                                mDatas.clear();
                            }
                            mRefreshLayout.setEnableLoadMore(false);
                            mAdapter.notifyDataSetChanged();
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
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {
        Intent intent;
        intent = new Intent(mContext, InvestigateActivity.class);
        intent.putExtra("jump_type",2);
       // intent.putExtra("room_id", mDatas.get(position).getRoom_id());
        intent.putExtra("plan_id",mDatas.get(position).getPlan_id());
        intent.putExtra("plan_info_id",mDatas.get(position).getPlan_info_id());
        intent.putExtra("record_id",mDatas.get(position).getId()+"");
        startActivity(intent);
    }
}
