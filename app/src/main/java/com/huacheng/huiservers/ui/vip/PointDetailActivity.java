package com.huacheng.huiservers.ui.vip;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.StringCallback;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.PointLog;
import com.huacheng.huiservers.ui.base.MyListActivity;
import com.huacheng.huiservers.ui.webview.WebviewActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by changyadong on 2020/11/27
 * @description  我的积分
 */
public class PointDetailActivity extends MyListActivity {

    TextView pointsTx;
    PointDetailAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.actvivity_point_detail;
    }
    @Override
    protected void initView() {
        super.initView();
        back();
        title("我的积分");
        pointsTx = findViewById(R.id.points);
        findViewById(R.id.rule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(mContext, WebviewActivity.class);
                it.putExtra("title", "积分规则");
                it.putExtra("url", ApiHttpClient.POINT_RULE);
                startActivity(it);
            }
        });

    }
    @Override
    protected void initData() {


        adapter = new PointDetailAdapter();
        listView.setAdapter(adapter);

        getData(mPage);


    }

    @Override
    public void getData(final int page) {



        Map<String,String> map = new HashMap<>();
        map.put("p",String.valueOf(mPage));
        MyOkHttp.get().get(ApiHttpClient.USER_POINTS,map, new StringCallback() {
            @Override
            public void onFailure(int code) {
                smallDialog.dismiss();
                loadComplete();
            }
            @Override
            public void onSuccess(String resp) {

                loadComplete();
                smallDialog.dismiss();
                PointLog log = new Gson().fromJson(resp,PointLog.class);

                pointsTx.setText(log.getData().getPoints());
                mPage = page;
                if (page == 1) {
                    adapter.clearData();
                }
                adapter.addData(log.getData().getPoints_log());
                setEmpty(adapter);
            }


        });
//        MyOkHttp.get().get(ApiHttpClient.USER_POINTS, map, new JsonResponseHandler() {
//
//
//
//            @Override
//            public void onFailure(int statusCode, String error_msg) {
//                smallDialog.dismiss();
//                loadComplete();
//
//
//            }
//            @Override
//            public void onSuccess(int statusCode, JSONObject response) {
//                loadComplete();
//                PointLog log = new Gson().fromJson(response.toString(),PointLog.class);
//
//                smallDialog.dismiss();
//                pointsTx.setText(log.getData().getPoints());
//                mPage = page;
//                if (page == 1) {
//                    adapter.clearData();
//                }
//                adapter.addData(log.getData().getPoints_log());
//                setEmpty(adapter);
//            }
//
//
//
//        });
    }


}
