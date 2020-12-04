package com.huacheng.huiservers.ui.vip;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelVipRecord;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:  vip开通记录
 * created by wangxiaotao
 * 2020/11/30 0030 08:27
 */
public class VipRecordActivity extends BaseListActivity<ModelVipRecord> {
    @Override
    protected void initView() {
        super.initView();

        titleName.setText("VIP开通记录");

        mAdapter = new CommonAdapter<ModelVipRecord>(this, R.layout.item_vip_record, mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, ModelVipRecord item, int position) {
                viewHolder.<TextView>getView(R.id.tv_title).setText(item.getVip_name());
                viewHolder.<TextView>getView(R.id.tv_price).setText("实付金额："+item.getVip_price());
                viewHolder.<TextView>getView(R.id.tv_days).setText(StringUtils.getDateToString(item.getSart_time(),"5")+StringUtils.getDateToString(item.getEnd_time(),"5"));
                viewHolder.<TextView>getView(R.id.tv_buy_time).setText(StringUtils.getDateToString(item.getAddtime(),"7"));
                viewHolder.<TextView>getView(R.id.tv_status).setText(item.getStatus_cn());
            }
        };
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        showDialog(smallDialog);
        Map<String, String> params = new HashMap<>();
        MyOkHttp.get().post(ApiHttpClient.VIP_RECORD, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                mRefreshLayout.finishRefresh();
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelVipRecord> list = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelVipRecord.class);
                    if (list != null&&list.size()>0) {
                        mRelNoData.setVisibility(View.GONE);
                        mDatas.clear();
                        mDatas.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }else {
                        mDatas.clear();
                        mRelNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                mRefreshLayout.finishRefresh();
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }

    @Override
    protected void onListViewItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }
}
