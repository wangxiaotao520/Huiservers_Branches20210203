package com.huacheng.huiservers.ui.center;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.ui.base.BaseActivityOld;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 意见反馈
 */

public class YiJianActivity extends BaseActivityOld implements View.OnClickListener {

    private LinearLayout lin_left;
    private RelativeLayout rel_ti;
    private TextView title_name;
    private EditText editText;

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_yijian);
        //      SetTransStatus.GetStatus(this);

        lin_left = (LinearLayout) findViewById(R.id.lin_left);
        rel_ti = (RelativeLayout) findViewById(R.id.rel_ti);
        editText = (EditText) findViewById(R.id.et_content);
        title_name = (TextView) findViewById(R.id.title_name);

        title_name.setText("意见反馈");

        lin_left.setOnClickListener(this);
        rel_ti.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_left:
                finish();
                break;
            case R.id.rel_ti:
                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    SmartToast.showInfo("请填写内容");
                } else {
                    getResult();
                }
                break;

            default:
                break;
        }
    }

    private void getResult() {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("content", editText.getText().toString().trim());
        params.addBodyParameter("type", "1");
        HttpHelper hh = new HttpHelper(info.yijian, params, this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(json);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        SmartToast.showInfo("提交成功");
                        finish();
                    } else {
                        SmartToast.showInfo(jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }

    //通过dispatchTouchEvent每次ACTION_DOWN事件中动态判断非EditText本身区域的点击事件，然后在事件中进行屏蔽。
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
