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
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.TextCheckUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 商务合作activity
 */

public class HeZuoActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout lin_left;
    private RelativeLayout rel_ti;
    private TextView title_name;
    private EditText et_name, et_phone, et_content;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_left:
                finish();
                break;

            case R.id.rel_ti:
                if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                    return;
                }
                if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
                    return;
                } /*else if (!ToolUtils.isMobileNO(et_phone.getText().toString().trim())) {
                    SmartToast.showInfo("手机号码格式不正确");
                } else*/
                if (TextUtils.isEmpty(et_content.getText().toString().trim())) {
                    return;
                }
                getResult();

                break;
            default:
                break;
        }
    }

    private void getResult() {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("content", et_content.getText().toString().trim());
        params.addBodyParameter("name", et_name.getText().toString().trim());
        params.addBodyParameter("mobile", et_phone.getText().toString().trim());
        params.addBodyParameter("type", "2");
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

    @Override
    protected void initView() {
        lin_left = (LinearLayout) findViewById(R.id.lin_left);
        rel_ti = (RelativeLayout) findViewById(R.id.rel_ti);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_content = (EditText) findViewById(R.id.et_content);
        title_name = (TextView) findViewById(R.id.title_name);

        title_name.setText("商务合作");

        lin_left.setOnClickListener(this);
        rel_ti.setOnClickListener(this);
        //1、传入需要监听的EditText与TextView
        TextCheckUtils textCheckUtils = new TextCheckUtils(et_name, et_phone, et_content);
        //2、设置是否全部填写完成监听
        textCheckUtils.setOnCompleteListener(new TextCheckUtils.OnCompleteListener() {
            @Override
            public void isComplete(boolean isComplete) {
                if (isComplete) {
                    rel_ti.setEnabled(true);
                    rel_ti.setBackgroundResource(R.drawable.allshape_orange);

                } else {
                    rel_ti.setEnabled(false);
                    rel_ti.setBackgroundResource(R.drawable.allshape_gray_solid_bb5);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hezuo;
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
