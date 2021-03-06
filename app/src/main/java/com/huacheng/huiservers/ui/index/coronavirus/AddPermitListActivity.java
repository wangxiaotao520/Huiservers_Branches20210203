package com.huacheng.huiservers.ui.index.coronavirus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.EventModelPass;
import com.huacheng.huiservers.model.ModelPermit;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * 类描述：申请通行证列表
 * 时间：2020/2/25 09:21
 * created by DFF
 */
public class AddPermitListActivity extends BaseListActivity<ModelPermit> {
    private String company_id;
    private String community_id;
    private String community_name;
    private String room_id;
    private String room_info;

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("通行证申请");

        mAdapter = new AddPermitListAdapter(this, R.layout.layout_add_permit_list_item, mDatas);
        mListview.setAdapter(mAdapter);

    }

    @Override
    protected void initIntentData() {
        super.initIntentData();
        company_id = this.getIntent().getStringExtra("company_id");
        community_id = this.getIntent().getStringExtra("community_id");
        community_name = this.getIntent().getStringExtra("community_name");
        room_id = this.getIntent().getStringExtra("room_id");
        room_info = this.getIntent().getStringExtra("room_info");
    }

    @Override
    protected void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("company_id", company_id);
        params.put("community_id", community_id);
        MyOkHttp.get().post(ApiHttpClient.GET_PERMIT_SET_LIST, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelPermit> data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelPermit.class);
                    mDatas.clear();
                    mDatas.addAll(data);
                    mAdapter.notifyDataSetChanged();
                    if (mDatas.size() == 0) {
                        mRelNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "获取数据失败");
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
    protected void onListViewItemClick(AdapterView adapterView, View view, int position, long id) {

        if ("2".equals(mDatas.get(position).getType())) {//长期
            Intent intent = new Intent(this, LongTermPassCheckActivity.class);
            intent.putExtra("company_id", company_id);
            intent.putExtra("id", mDatas.get(position).getId());
            intent.putExtra("community_id", community_id);
            intent.putExtra("community_name", community_name);
            intent.putExtra("room_id", room_id);
            intent.putExtra("room_info", room_info);
            intent.putExtra("jump_type", 1);
            intent.putExtra("status", mDatas.get(position).getStatus());
            startActivity(intent);
        } else {//访客 临时
            Intent intent = new Intent(this, SubmitPermitActivity.class);
            intent.putExtra("company_id", company_id);
            intent.putExtra("id", mDatas.get(position).getId());
            intent.putExtra("community_id", community_id);
            intent.putExtra("community_name", community_name);
            intent.putExtra("room_id", room_id);
            intent.putExtra("room_info", room_info);
            intent.putExtra("type", mDatas.get(position).getType());
            intent.putExtra("jump_type", 1);
            intent.putExtra("status", mDatas.get(position).getStatus());
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    /**
     * 通行证提交返回
     *
     * @param info
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void back(EventModelPass info) {
        finish();
    }

}
