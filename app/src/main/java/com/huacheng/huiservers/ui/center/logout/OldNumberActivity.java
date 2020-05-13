package com.huacheng.huiservers.ui.center.logout;

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
import com.huacheng.huiservers.utils.TextCheckUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：输入原手机号
 * 时间：2020/5/7 15:09
 * created by DFF
 */
public class OldNumberActivity extends BaseActivity {
    private EditText edit_number;
    private TextView tv_next;


    @Override
    protected void initView() {
        findTitleViews();

        edit_number = findViewById(R.id.edit_number);
        tv_next = findViewById(R.id.tv_next);
        //1、传入需要监听的EditText与TextView
        TextCheckUtils textCheckUtils = new TextCheckUtils(edit_number);
        //2、设置是否全部填写完成监听
        textCheckUtils.setOnCompleteListener(new TextCheckUtils.OnCompleteListener() {
            @Override
            public void isComplete(boolean isComplete) {
                if (isComplete) {
                    tv_next.setEnabled(true);
                    tv_next.setBackgroundResource(R.drawable.allshape_orange);

                } else {
                    tv_next.setEnabled(false);
                    tv_next.setBackgroundResource(R.drawable.allshape_gray_solid_bb5);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getNext();

            }
        });
    }

    /**
     * 验证原手机号
     */
    private void getNext() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("old_phone", edit_number.getText().toString().trim() + "");

        MyOkHttp.get().post(ApiHttpClient.VERITY_OLD_PHONE, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    Intent intent = new Intent(OldNumberActivity.this, NewNumberActivity.class);
                    intent.putExtra("phone", edit_number.getText().toString().trim());
                    startActivity(intent);
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交失败");
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
    protected int getLayoutId() {
        return R.layout.layout_old_number;
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
