package com.huacheng.huiservers.ui.index.workorder;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.example.xlhratingbar_lib.XLHRatingBar;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:工单评价 Activity
 * Author: badge
 * Create: 2018/12/14 9:24
 */
public class WorkEvaluateActivity extends BaseActivity {

    XLHRatingBar ratingBar;

    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rel_ti)
    RelativeLayout relTi;

    String content;
    int ratingCount = 0;
    String w_id = "";

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        LinearLayout lin_left = findViewById(R.id.lin_left);
        ratingBar = findViewById(R.id.ratingBar);

        lin_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToolUtils(etContent,WorkEvaluateActivity.this ).closeInputMethod();
                finish();
            }
        });
        titleName = findViewById(com.huacheng.libraryservice.R.id.title_name);
        titleName.setText("评价");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ratingBar.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
                ratingCount = ratingBar.getCountSelected();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_evaluate;
    }

    @Override
    protected void initIntentData() {
        w_id = getIntent().getStringExtra("w_id");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }


    @OnClick(R.id.rel_ti)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.rel_ti:
                if (valid()) {
                    if (!StringUtils.isEmpty(w_id)) {
                        commit();

                    }

                }

                break;
        }
    }

    /**
     * 提交
     */
    private void commit() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", w_id);
        params.put("level", ratingCount + "");
        params.put("evaluate_content", content + "");
        MyOkHttp.get().post(ApiHttpClient.WorkScore, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {

                if (JsonUtil.getInstance().isSuccess(response)) {
                    setResult(RESULT_OK);
                    startActivity(new Intent(WorkEvaluateActivity.this, WorkEvaluateSuccessActivity.class));
                    finish();
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }

    /**
     * 非空验证
     *
     * @return
     */
    private boolean valid() {
        content = etContent.getText().toString();
        if (StringUtils.isEmpty(content)) {
            SmartToast.showInfo("请输入评价");
            return false;
        }
        return true;
    }

}
