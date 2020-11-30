package com.huacheng.huiservers.ui.vip;

import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.GsonResponseHandler;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.VipLogs;
import com.huacheng.huiservers.ui.base.MyListActivity;
import com.huacheng.huiservers.util.DialogUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by changyadong on 2020/11/27
 * @description
 */
public class VipDetailActivity extends MyListActivity {




    TextView growValue,level,need;
    PointDetailAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.actvivity_vip_detail;
    }
    String rule = "";
    @Override
    protected void initView() {
        super.initView();
        back();
        title("我的会员");
        growValue = findViewById(R.id.grow_value);
        level = findViewById(R.id.level);
        need = findViewById(R.id.need);

        findViewById(R.id.rule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.customMsgAlert(mContext,"如何获取成长值",rule);
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
    public void getData(int page) {
        smallDialog.show();
        Map<String,String> map = new HashMap<>();
        MyOkHttp.get().get(ApiHttpClient.USER_RANK, map, new JsonResponseHandler() {

            @Override
            public void onFailure(int statusCode, String error_msg) {
                 smallDialog.dismiss();

            }
            @Override
            public void onSuccess(int statusCode, JSONObject response) {

                 VipLogs logs = new Gson().fromJson(response.toString(),VipLogs.class);
                 smallDialog.dismiss();
                 rule = logs.getData().getSign_rule();

                 growValue.setText(logs.getData().getRank());
                 level.setText(logs.getData().getLevel());
                 need.setText(logs.getData().getLevel_next());


            }


        });

    }


}
