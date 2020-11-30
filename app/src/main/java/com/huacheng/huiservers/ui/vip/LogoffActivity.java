package com.huacheng.huiservers.ui.vip;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.ui.base.MyActivity;
import com.huacheng.huiservers.ui.base.MyAdapter;
import com.huacheng.huiservers.ui.webview.WebviewActivity;

import java.util.Arrays;

/**
 * @author Created by changyadong on 2020/11/24
 * @description
 */


public class LogoffActivity extends MyActivity {

    ListView listView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip_logoff;
    }

    @Override
    protected void initView() {
        back();
        listView = findViewById(R.id.listview);
        ItemAdapter adapter = new ItemAdapter();
        listView.setAdapter(adapter);
        adapter.addData(Arrays.asList(array));
        findViewById(R.id.protocal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(mContext, WebviewActivity.class);
                it.putExtra("title", "注销协议");
                it.putExtra("url", ApiHttpClient.POINT_RULE);
                startActivity(it);
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(mContext, LogoffIfActivity.class);

                startActivity(it);
            }
        });

    }


    String[] array = new String[]{
            "账号内无未完成状态的订单",
            "账号内无未完成状态的物业工单",
            "账号无任何纠纷",
            "已注销或关闭账号内所有金融服务",
            "账号内涉及的第三方服务为已完结状态",
    };


    class ItemAdapter extends MyAdapter<String> {


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_logoff_check, viewGroup, false);
            }
            TextView index = view.findViewById(R.id.index);
            TextView textView = view.findViewById(R.id.content);
            index.setText(String.valueOf(i + 1));
            textView.setText(getItem(i));
            return view;
        }


    }

}
