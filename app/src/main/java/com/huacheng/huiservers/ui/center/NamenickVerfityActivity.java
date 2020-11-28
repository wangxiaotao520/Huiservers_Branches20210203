package com.huacheng.huiservers.ui.center;

import android.content.Context;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.db.UserSql;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelUser;
import com.huacheng.huiservers.model.PersoninfoBean;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 修改个人信息页面
 */
public class NamenickVerfityActivity extends BaseActivity implements OnClickListener {

    TextView title_name, tv_description, tv_flag, right;
    EditText et_content;

    @Override
    protected void initView() {
        title_name = (TextView) findViewById(R.id.title_name);
        right = (TextView) findViewById(R.id.right);
        right.setVisibility(View.VISIBLE);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_flag = (TextView) findViewById(R.id.tv_flag);
        et_content = (EditText) findViewById(R.id.et_content);
        // set
        title_name.setText("昵称");
        right.setTextColor(getResources().getColor(R.color.orange));
        right.setText("确定");
        // get
        String nickname = this.getIntent().getStringExtra("nickname");
        if (!nickname.equals("")) {
            et_content.setText(nickname);
        } else {
            et_content.setHint("请输入");
        }
        String sex = this.getIntent().getStringExtra("tv_sex");
        if (sex.equals("男")) {
            tv_description.setText("先生");
        } else if (sex.equals("女")) {
            tv_description.setText("女士");
        } else {
            tv_description.setText("先生/女士");
        }

        tv_flag.setText("将用于社区慧生活社区交流，昵称不能超过8位，包含汉字、字母或数字，且不能与别人重复");
        // 限定edittext能输入内容
        et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        // 强制隐藏Android输入法窗口
        et_content.setFocusableInTouchMode(true);
        et_content.setFocusable(true);
        et_content.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) et_content.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(et_content, 0);
            }
        }, 100);
        // listener
        findViewById(R.id.lin_left).setOnClickListener(this);
        right.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right:
                String nickname = et_content.getText().toString();
                if (!nickname.equals("")) {
                    requestData();
                } else {
                    SmartToast.showInfo("昵称不能为空");
                }

                break;
            case R.id.lin_left:
                closeInputMethod();
                finish();
                break;
            default:
                break;
        }
    }
    /**
     * 修改个人信息
     */
    private void requestData() {
        showDialog(smallDialog);
        Map<String, String> params = new HashMap<>();
        params.put("type","setNickname");
        params.put("value",et_content.getText().toString().trim());
        MyOkHttp.get().post(ApiHttpClient.MY_EDIT_CENTER, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    closeInputMethod();
                    //更改昵称更新数据库
                    ModelUser modelUser = BaseApplication.getUser();
                    modelUser.setNickname(et_content.getText().toString().trim());
                    UserSql.getInstance().updateObject(modelUser);
                    SmartToast.showInfo("修改成功");

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
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.verify_editxt_new;
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

    /**
     * 关闭软键盘
     */
    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // imm.toggleSoftInput(0,
            // InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示

            imm.hideSoftInputFromWindow(et_content.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
