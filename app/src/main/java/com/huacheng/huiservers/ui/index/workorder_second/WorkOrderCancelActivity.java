package com.huacheng.huiservers.ui.index.workorder_second;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.EventBusWorkOrderModel;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：
 * 时间：2019/4/9 18:49
 * created by DFF
 */
public class WorkOrderCancelActivity extends BaseActivity {
    EditText et_content;
    TextView tv_commit;
    LinearLayout lin_left;
    String work_id = "";

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("取消工单");
        lin_left = findViewById(R.id.lin_left);
        et_content = findViewById(R.id.et_content);
        tv_commit = findViewById(R.id.tv_commit);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        et_content.addTextChangedListener(mTextWatcher);

        lin_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToolUtils(et_content,WorkOrderCancelActivity.this).closeInputMethod();
                finish();
            }
        });
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交取消原因
                commitCancel();
            }
        });
    }

    /**
     * 提交取消工单
     */
    private void commitCancel() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", work_id);
        params.put("cancel_content", et_content.getText().toString() + "");

        MyOkHttp.get().post(ApiHttpClient.GET_WORK_CANCEL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    EventBusWorkOrderModel model = new EventBusWorkOrderModel();
                    model.setEvent_back_type(0);
                    model.setWork_id(work_id);
                    EventBus.getDefault().post(model);
                    setResult(RESULT_OK);
                    finish();
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
    protected int getLayoutId() {
        return R.layout.activity_work_order_cancel;
    }

    @Override
    protected void initIntentData() {
        work_id = this.getIntent().getStringExtra("work_id");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = et_content.getSelectionStart();
            editEnd = et_content.getSelectionEnd();
            if (temp.length() > 100) {
                s.delete(editStart - 1, editEnd);
                et_content.setText(s);
                //mTvNum.setText(s.length() + "/150");
                et_content.setSelection(s.length());
                SmartToast.showInfo("超出字数范围");
            }
        }
    };
}
