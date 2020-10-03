package com.huacheng.huiservers.ui.index.oldservice;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelOldFootmark;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * 类描述：健康计步
 * 时间：2020/10/3 15:07
 * created by DFF
 */
public class OldMyStepActivity extends BaseActivity {
    private LinearLayout ly_select_time;
    private TextView tv_data;
    private TextView tv_step_num;
    private String date;
    private ModelOldFootmark mOldFootmark;

    @Override
    protected void initView() {

        findTitleViews();
        titleName.setText("健康计步");

        tv_data = findViewById(R.id.tv_data);
        ly_select_time = findViewById(R.id.ly_select_time);
        tv_step_num = findViewById(R.id.tv_step_num);


        //默认获取当天的日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        date = sdf.format(System.currentTimeMillis());
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("par_uid", "105");// TODO: 2020/10/3  id记得更改
        params.put("date", date);

        MyOkHttp.get().post(ApiHttpClient.STEP_INFO, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    mOldFootmark = (ModelOldFootmark) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldFootmark.class);
                    if (mOldFootmark != null) {
                        tv_data.setText(date + "步数");
                        tv_step_num.setText(mOldFootmark.getS());

                    }
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
    protected void initListener() {
        ly_select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择日期
                SmartToast.showInfo("选择日期");

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_my_step;
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
