package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Description: 编辑地址
 * created by wangxiaotao
 * 2020/9/29 0029 08:46
 */
public class EditOldAddressActivity extends BaseActivity {

    private TextView txt_right1;
    private EditText edt_address;
    private String par_uid="";
    private String address="";

    @Override
    protected void initView() {
    findTitleViews();
    titleName.setText("地理位置");
    txt_right1 = findViewById(R.id.txt_right1);
    edt_address = findViewById(R.id.edt_address);
    txt_right1.setText("确定");
    txt_right1.setVisibility(View.VISIBLE);
        if (!NullUtil.isStringEmpty(address)){
            edt_address.setText(address);
            edt_address.setSelection(address.length());
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        txt_right1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定
                address = edt_address.getText().toString().trim();
                if (NullUtil.isStringEmpty(address)){
                    SmartToast.showInfo("请输入地址");
                    return;
                }
                requestData();

            }
        });
    }

    /**
     * 请求接口
     */
    private void requestData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("par_uid",par_uid);
        params.put("address",address);
        MyOkHttp.get().post(ApiHttpClient.PENSION_SET_ADDRESS, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "操作成功");
                    SmartToast.showInfo(msg);
                    Intent intent = new Intent();
                    intent.putExtra("address",address);
                    setResult(RESULT_OK,intent);
                    finish();
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "操作失败");
                    //  ToastUtils.showShort(mContext.getApplicationContext(), msg + "");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                //  ToastUtils.showShort(mContext.getApplicationContext(), "网络异常，请检查网络设置");
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_old_address;
    }

    @Override
    protected void initIntentData() {
        par_uid=getIntent().getStringExtra("par_uid");
        address=getIntent().getStringExtra("address");

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
