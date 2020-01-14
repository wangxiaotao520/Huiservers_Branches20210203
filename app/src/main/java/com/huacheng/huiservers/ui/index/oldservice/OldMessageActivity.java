package com.huacheng.huiservers.ui.index.oldservice;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelOldMessage;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.index.oldservice.adapter.AdapterOldMessage;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Description: 关联消息
 * created by wangxiaotao
 * 2019/8/20 0020 下午 6:20
 */
public class OldMessageActivity extends BaseListActivity<ModelOldMessage> implements AdapterOldMessage.OnClickMessageListener {
    private boolean isRequesting = false; //是否正在请求

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("关联消息");
        mAdapter = new AdapterOldMessage(this, R.layout.item_old_message,mDatas,this);
        mListview.setAdapter(mAdapter);

        ImageView img_data = findViewById(R.id.img_data);
        img_data.setBackgroundResource(R.mipmap.bg_no_message_data);
        TextView tv_text = findViewById(R.id.tv_text);
        tv_text.setText("暂无消息");

    }

    /**
     * 请求数据
     */
    protected void requestData() {
        HashMap<String, String> params = new HashMap<>();

        params.put("p", page + "");
        MyOkHttp.get().post(ApiHttpClient.PENSION_MSG_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelOldMessage info = (ModelOldMessage) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldMessage.class);
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
    protected void onListViewItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public void onClick(int position, ModelOldMessage item, int state) {
        if (isRequesting==true){
            return;
        }
        audit( position,  item,  state);
    }

    /**
     * 同意或者拒绝
     * @param position
     * @param item
     * @param state
     */
    private void audit(final int position, ModelOldMessage item, final int state) {
        isRequesting=true;
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();

        params.put("m_id",item.getId()+"");
        params.put("state",state+"");
        MyOkHttp.get().post(ApiHttpClient.PENSION_MSG_AUDIT, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                isRequesting=false;
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "操作成功");
                    SmartToast.showInfo(msg);
                    if (state==1){
                        mDatas.get(position).setState_str("已同意");
                    }else if (state==2){
                        mDatas.get(position).setState_str("已拒绝");
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                isRequesting=false;
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");

            }
        });
    }
}
