package com.huacheng.huiservers.ui.index.oldservice;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：添加关联的用户或者是家庭人员
 * 时间：2019/8/13 18:05
 * created by DFF
 */
public class AddOldUserActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_phone;
    private EditText edt_name;
    private TextView tv_btn;
    private LinearLayout ly_SF;
    private int type = 0;//1.老人关联子女 2.子女关联老人

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("添加关联");

        et_phone = findViewById(R.id.et_phone);
        edt_name = findViewById(R.id.edt_name);
        tv_btn = findViewById(R.id.tv_btn);
        ly_SF = findViewById(R.id.ly_SF);
        ly_SF.setVisibility(View.VISIBLE);

        if (type==1){
            edt_name.setHint("输入关联者对你的称谓（如父亲）");
        }else {
            edt_name.setHint("请输入绑定者身份（如父亲）");
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_btn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_old_user;
    }

    @Override
    protected void initIntentData() {
        type = getIntent().getIntExtra("type", 1);

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_btn:
                if (NullUtil.isStringEmpty(et_phone.getText().toString())) {
                    SmartToast.showInfo("请输入社区慧生活绑定账号");
                    return;
                }
                    if (NullUtil.isStringEmpty(edt_name.getText().toString())) {
                        SmartToast.showInfo("请输入绑定者身份");
                        return;
                    }

//                Intent intent = new Intent(AddOldUserActivity.this, AddOldRZUserActivity.class);
//                startActivity(intent);
                showDialog(smallDialog);
                requestData();
                break;
            default:
                break;
        }
    }

    /**
     * 添加关联
     */
    private void requestData() {
        HashMap<String, String> params = new HashMap<>();

        if (type==1){
            params.put("type","1");
        }else if (type==2){
            params.put("type","2");
        }
        String phone = et_phone.getText().toString().trim();
        params.put("username",phone+"");
        String call = edt_name.getText().toString().trim();
        params.put("call",call+"");

        MyOkHttp.get().post(ApiHttpClient.PENSION_RELATION_ADD, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "成功");
                    SmartToast.showInfo(msg);
                    finish();
                } else {
                    try {
                        int status = response.getInt("status");
                        if (status==3){
                            String msg = JsonUtil.getInstance().getMsgFromResponse(response, "");
                            new CommomDialog(AddOldUserActivity.this, R.style.my_dialog_DimEnabled, msg, new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    if (confirm){
                                        startActivity(new Intent(AddOldUserActivity.this,OldMessageActivity.class));
                                    }
                                    dialog.dismiss();
                                }
                            }).setPositiveButton("去看看").show();//.setTitle("提示")

                        }else {
                            String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                            SmartToast.showInfo(msg);
                        }
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
}
