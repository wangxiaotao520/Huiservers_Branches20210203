package com.huacheng.huiservers.ui.index.workorder.commit;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelWorkPersonalCatItem;
import com.huacheng.huiservers.ui.base.BaseListActivity;
import com.huacheng.huiservers.ui.index.workorder.adapter.AdapterWorkType;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description:工单选择分类页面
 * created by wangxiaotao
 * 2019/4/9 0009 下午 5:22
 */
public class WorkTypeListActivity extends BaseListActivity {
    List <ModelWorkPersonalCatItem> mDatas = new ArrayList<>();
    private AdapterWorkType adapterWorkType;
    private  String type="1";

    @Override
    protected void initView() {
         type = getIntent().getStringExtra("type");
        super.initView();
        titleName.setText("选择分类");
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        adapterWorkType = new AdapterWorkType(this, R.layout.item_work_type, mDatas);
        mListview.setAdapter(adapterWorkType);

    }

    @Override
    protected void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("type",type);
        MyOkHttp.get().post(ApiHttpClient.GET_WORK_TYPE_LIST, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)){
                    List <ModelWorkPersonalCatItem>data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelWorkPersonalCatItem.class);
                    mDatas.clear();
                    mDatas.addAll(data);
                    adapterWorkType.notifyDataSetChanged();
                    if (mDatas.size()==0){
                        mRelNoData.setVisibility(View.VISIBLE);
                    }
                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"获取数据失败");
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
    protected void onListViewItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("type_data",mDatas.get(position));
        setResult(RESULT_OK,intent);
        finish();
    }
}
