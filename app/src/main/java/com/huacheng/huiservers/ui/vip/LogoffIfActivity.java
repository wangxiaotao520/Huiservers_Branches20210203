package com.huacheng.huiservers.ui.vip;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.MyActivity;
import com.huacheng.huiservers.ui.base.MyAdapter;

import java.util.Arrays;

/**
 * @author Created by changyadong on 2020/11/24
 * @description
 */
public class LogoffIfActivity extends MyActivity {

    ViewSwitcher switcher;
    ListView listView;
    TextView tellTx;
    TextView zhuxiaoTx;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip_logoff_if;
    }

    @Override
    protected void initView() {
        back();
        zhuxiaoTx = findViewById(R.id.zhuxiao);
        tellTx = findViewById(R.id.tell);
        tellTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT > 22) {

                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(LogoffIfActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                        return;
                    }
                    callPhone();
                }

            }
        });
        switcher = findViewById(R.id.switcher);
        listView = findViewById(R.id.listview);

        ItemAdapter adapter = new ItemAdapter();
        listView.setAdapter(adapter);
        adapter.addData(Arrays.asList(array));


    }
    public void callPhone(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:400-6535-355"));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode ==101 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            callPhone();

        }
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
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_logoff_cause, viewGroup, false);
            }
            TextView content = view.findViewById(R.id.content);
            TextView no = view.findViewById(R.id.no);
            content.setText(getItem(i));
            return view;
        }


    }
}
