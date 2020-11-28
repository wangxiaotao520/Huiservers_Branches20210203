package com.huacheng.huiservers.ui.center;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.PersoninfoBean;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SexVerfityActivity extends BaseActivity implements OnClickListener {

    LinearLayout lin_left;
    TextView title_name, right;
    RadioGroup radiogroup;
    RadioButton radio_nan, radio_nv;
    String currentSelectedVal = "";
    private String type_value = "";
    private int type = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right:
                if (!currentSelectedVal.equals("")) {
                    requestData();
                } else {
                    SmartToast.showInfo("性别为空");
                }
                break;
            case R.id.lin_left:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 修改个人信息性别 / 居住状态
     */
    private void requestData() {
        showDialog(smallDialog);
        Map<String, String> params = new HashMap<>();
        if (type == 1) {//性别
            params.put("type", "setSex");
        } else {//居住状态
            params.put("type", "setHouseStatus");
        }
        params.put("value", currentSelectedVal);
        MyOkHttp.get().post(ApiHttpClient.MY_EDIT_CENTER, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    EventBus.getDefault().post(new PersoninfoBean());
                    finish();
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
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
    protected void initView() {
        title_name = (TextView) findViewById(R.id.title_name);
        right = (TextView) findViewById(R.id.right);
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        radio_nan = (RadioButton) findViewById(R.id.radio_nan);
        radio_nv = (RadioButton) findViewById(R.id.radio_nv);
        // set
        if (type == 1) {
            title_name.setText("性别");
            radio_nan.setText("男");
            radio_nv.setText("女");
        } else {
            title_name.setText("居住状态");
            radio_nan.setText("自有房屋");
            radio_nv.setText("租房");
        }
        right.setTextColor(getResources().getColor(R.color.orange));
        right.setText("提交");
        right.setVisibility(View.VISIBLE);

        final Drawable drawableCheck = getResources().getDrawable(R.mipmap.ic_selected_pay_type);
        final Drawable drawableunCheck = getResources().getDrawable(R.drawable.shape_oval_grey);
        drawableCheck.setBounds(0, 0, drawableCheck.getMinimumWidth(), drawableCheck.getMinimumHeight());  //
        drawableunCheck.setBounds(0, 0, drawableunCheck.getMinimumWidth(), drawableunCheck.getMinimumHeight());  //
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rb = (RadioButton) findViewById(arg0.getCheckedRadioButtonId());

                if (rb.getText().toString().equals("男") || rb.getText().toString().equals("自有房屋")) {
                    currentSelectedVal = "1";
                    radio_nan.setCompoundDrawables(null, null, drawableCheck, null);
                    radio_nv.setCompoundDrawables(null, null, drawableunCheck, null);
                } else if (rb.getText().toString().equals("女") || rb.getText().toString().equals("租房")) {
                    currentSelectedVal = "2";
                    radio_nv.setCompoundDrawables(null, null, drawableCheck, null);
                    radio_nan.setCompoundDrawables(null, null, drawableunCheck, null);
                }

            }
        });
        //getExtra
        if (!type_value.equals("")) {
            if (type_value.equals("1")) {
                currentSelectedVal = "1";
                radio_nan.setChecked(true);
                radio_nan.setCompoundDrawables(null, null, drawableCheck, null);
                radio_nv.setCompoundDrawables(null, null, drawableunCheck, null);
            } else if (type_value.equals("2")) {
                currentSelectedVal = "2";
                radio_nv.setChecked(true);
                radio_nv.setCompoundDrawables(null, null, drawableCheck, null);
                radio_nan.setCompoundDrawables(null, null, drawableunCheck, null);
            }
        } else {
            radio_nan.setCompoundDrawables(null, null, drawableunCheck, null);
            radio_nv.setCompoundDrawables(null, null, drawableunCheck, null);
        }
        // listener
        findViewById(R.id.lin_left).setOnClickListener(this);
        right.setOnClickListener(this);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.verify_sex_new;
    }

    @Override
    protected void initIntentData() {
        type_value = this.getIntent().getStringExtra("type_value");
        type = this.getIntent().getIntExtra("type", 1);
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
