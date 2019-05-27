package com.huacheng.huiservers.ui.index.request;

import android.app.Dialog;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.ui.base.BaseActivity;


/**
 * 类描述：投诉建议详情
 * 时间：2019/5/8 08:55
 * created by DFF
 */
public class RequesDetailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ly_all;
    private LinearLayout mlinear_repair_photo, lin_comment, ly_btn;
    private LinearLayout mlinear_photo;
    private LinearLayout ly_detail;
    private LinearLayout ly_reply;

    private TextView tv_status;
    private TextView tv_danhao;
    private TextView tv_time;
    private TextView tv_user_name;
    private TextView tv_user_address;
    private TextView tv_none;
    private TextView tv_user_photo;
    private TextView tv_detail_content;
    private TextView tv_reply_content;
    private TextView tv_reply;
    private TextView tv_jiejue;
    private TextView tv_send;
    private EditText et_input;

    private String id = "";

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("投诉建议详情");

        ly_all = findViewById(R.id.ly_all);
        tv_danhao = findViewById(R.id.tv_danhao);
        tv_status = findViewById(R.id.tv_status);
        tv_time = findViewById(R.id.tv_time);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_address = findViewById(R.id.tv_user_address);
        tv_user_photo = findViewById(R.id.tv_user_photo);
        mlinear_repair_photo = findViewById(R.id.linear_repair_photo);
        mlinear_photo = findViewById(R.id.linear_photo);
        tv_none = findViewById(R.id.tv_none);
        ly_detail = findViewById(R.id.ly_detail);
        tv_detail_content = findViewById(R.id.tv_detail_content);
        ly_reply = findViewById(R.id.ly_reply);
        tv_reply_content = findViewById(R.id.tv_reply_content);
        tv_reply = findViewById(R.id.tv_reply);
        tv_jiejue = findViewById(R.id.tv_jiejue);
        lin_comment = findViewById(R.id.lin_comment);
        et_input = findViewById(R.id.et_input);
        ly_btn = findViewById(R.id.ly_btn);
        tv_send = findViewById(R.id.tv_send);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_reply.setOnClickListener(this);
        tv_jiejue.setOnClickListener(this);
        tv_send.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_request_detail;
    }

    @Override
    protected void initIntentData() {
        id = this.getIntent().getStringExtra("id");

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
            case R.id.tv_reply://回复
                lin_comment.setVisibility(View.VISIBLE);
                ly_btn.setVisibility(View.GONE);
                et_input.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_input, InputMethodManager.SHOW_IMPLICIT);
                // showInput(tv_reply);
                break;
            case R.id.tv_send:
                lin_comment.setVisibility(View.GONE);
                ly_btn.setVisibility(View.VISIBLE);
                hideInput();
                break;
            case R.id.tv_jiejue://已解决
                new CommomDialog(mContext, R.style.my_dialog_DimEnabled, "是否确认已解决该条信息", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            // TODO: 2019/5/6  请求解决数据
                            dialog.dismiss();
                        }

                    }
                }).show();//.setTitle("提示")
                break;
            default:
                break;
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        lin_comment.setVisibility(View.GONE);
        ly_btn.setVisibility(View.VISIBLE);
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
