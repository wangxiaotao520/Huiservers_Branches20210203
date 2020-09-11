package com.huacheng.huiservers.ui.fragment.indexcat;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelAds;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.adapter.IndexMoreSqAdapter;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.widget.GridViewNoScroll;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：查看更多商圈
 * 时间：2020/9/7 09:55
 * created by DFF
 */
public class IndexMoreSQActivity extends BaseActivity {
    private GridViewNoScroll gridview;
    SharePrefrenceUtil prefrenceUtil;
    List<ModelAds> mdata = new ArrayList<>();
    IndexMoreSqAdapter adapter;

    @Override
    protected void initView() {
        prefrenceUtil = new SharePrefrenceUtil(this);
        findTitleViews();
        titleName.setText("共享商圈");

        gridview = findViewById(R.id.gridview_imgs);
        adapter = new IndexMoreSqAdapter(this, mdata);
        gridview.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        // 小区id 要判断
        if (!NullUtil.isStringEmpty(prefrenceUtil.getXiaoQuId())) {
            params.put("c_id", prefrenceUtil.getXiaoQuId());
        }
        if (!NullUtil.isStringEmpty(prefrenceUtil.getProvince_cn())) {
            params.put("province_cn", prefrenceUtil.getProvince_cn());
            params.put("city_cn", prefrenceUtil.getCity_cn());
            params.put("region_cn", prefrenceUtil.getRegion_cn());
        }
        MyOkHttp.get().post(ApiHttpClient.GET_BUSINESS_LIST, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List<ModelAds> info = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelAds.class);
                    mdata.clear();
                    mdata.addAll(info);
                    adapter.notifyDataSetChanged();
                } else {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
    protected void initListener() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(IndexMoreSQActivity.this, IndexShareSQActivity.class);
                intent.putExtra("id",mdata.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_more_sq_list;
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
}
