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
import com.huacheng.huiservers.ui.center.geren.bean.GroupMemberBean;
import com.huacheng.huiservers.ui.index.workorder.adapter.AdapterHouseList;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Description:选择房屋列表
 * created by wangxiaotao
 * 2019/4/9 0009 下午 5:46
 */
public class HouseListActivity  extends BaseListActivity <GroupMemberBean>{



    @Override
    protected void initView() {
        super.initView();
        titleName.setText("选择房屋地址");
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        mAdapter = new AdapterHouseList(this, R.layout.item_house_list,mDatas);
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().post(ApiHttpClient.GET_WORK_HOUSE_ADDRESS, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)){
                    List <GroupMemberBean>data = JsonUtil.getInstance().getDataArrayByName(response, "data", GroupMemberBean.class);
                    mDatas.clear();
                    mDatas.addAll(data);
                    mAdapter.notifyDataSetChanged();
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
        intent.putExtra("community",mDatas.get(position));
        setResult(RESULT_OK,intent);
        finish();
    }
}
