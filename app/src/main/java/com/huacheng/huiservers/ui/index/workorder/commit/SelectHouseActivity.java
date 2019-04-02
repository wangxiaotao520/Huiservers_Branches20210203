package com.huacheng.huiservers.ui.index.workorder.commit;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.index.workorder.adapter.SelectHouseAdapter;
import com.huacheng.huiservers.ui.index.wuye.bean.WuYeBean;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：工单选择房屋列表
 * 时间：2018/12/12 16:35
 * created by DFF
 */
public class SelectHouseActivity extends BaseListActivity {

    List<WuYeBean> mDatas = new ArrayList();
    private SelectHouseAdapter houseAdapter;

    @Override
    protected void initView() {
        super.initView();
        titleName.setText("选择房屋");

        houseAdapter = new SelectHouseAdapter(this, R.layout.workorder_fw_layout, mDatas);
        mListview.setAdapter(houseAdapter);
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected void requestData() {


        MyOkHttp.get().post(ApiHttpClient.GET_WORK_ADDRESS, null, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                if (JsonUtil.getInstance().isSuccess(response)) {

                    List<WuYeBean> data = JsonUtil.getInstance().getDataArrayByName(response, "data", WuYeBean.class);
                    if (data != null && data.size() > 0) {
                        mRelNoData.setVisibility(View.GONE);
                        mDatas.clear();
                        mDatas.addAll(data);
                        houseAdapter.notifyDataSetChanged();
                    } else {
                        mRelNoData.setVisibility(View.VISIBLE);
                    }

                } else {
                    mRelNoData.setVisibility(View.VISIBLE);
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    @Override
    protected void onListViewItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        WuYeBean model_house = mDatas.get(position);
        Intent intent = new Intent();
        intent.putExtra("model_house", model_house);
        setResult(RESULT_OK, intent);
        finish();
    }

}
