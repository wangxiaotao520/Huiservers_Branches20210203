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
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.GsonResponseHandler;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.BaseResp;
import com.huacheng.huiservers.model.ModelLoginOverTime;
import com.huacheng.huiservers.model.UnsetReason;
import com.huacheng.huiservers.ui.base.MyActivity;
import com.huacheng.huiservers.ui.base.MyAdapter;
import com.huacheng.libraryservice.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Created by changyadong on 2020/11/24
 * @description
 */
public class LogoffIfActivity extends MyActivity {

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
        listView = findViewById(R.id.listview);

        findViewById(R.id.unset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unset();
            }
        });


    }

    @Override
    protected void initData() {
        getData();
    }

    public void unset() {
        smallDialog.show();
        MyOkHttp.get().post(ApiHttpClient.UNSET_ACCOUNT, new HashMap<String, String>(), new GsonResponseHandler<BaseResp>() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                smallDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, BaseResp response) {
                smallDialog.dismiss();
                if (response.getStatus() == 1){
                    ModelLoginOverTime modelLoginOverTime = new ModelLoginOverTime();
                    modelLoginOverTime.setType(1);
                    EventBus.getDefault().post(modelLoginOverTime);
                    finish();
                }
                ToastUtils.showShort(mContext,response.getMsg());
            }
        });


    }

    public void getData() {
        smallDialog.show();
        MyOkHttp.get().post(ApiHttpClient.UNSET_ACCOUNT_REASON, new HashMap<String, String>(), new GsonResponseHandler<UnsetReason>() {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                smallDialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, UnsetReason response) {
                smallDialog.dismiss();
                if (response.getData() == null || response.getData().isEmpty()) {
                    zhuxiaoTx.setText("当前账号可以注销");
                    findViewById(R.id.unset_yes).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.listview).setVisibility(View.VISIBLE);

                    ItemAdapter adapter = new ItemAdapter();
                    listView.setAdapter(adapter);
                    adapter.addData(response.getData());

                }
            }
        });


    }

    public void callPhone() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:400-6535-355"));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

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


    class ItemAdapter extends MyAdapter<UnsetReason.DataBean> {


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_logoff_cause, viewGroup, false);
            }
            TextView content = view.findViewById(R.id.content);
            TextView no = view.findViewById(R.id.no);
            content.setText(getItem(i).getTitle());
            no.setText(getItem(i).getContent());
            return view;
        }


    }
}
