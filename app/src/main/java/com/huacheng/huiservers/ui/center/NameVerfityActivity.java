package com.huacheng.huiservers.ui.center;

import android.content.Context;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseActivityOld;
import com.huacheng.huiservers.ui.center.bean.PersoninfoBean;
import com.huacheng.huiservers.utils.UIUtils;
import com.huacheng.huiservers.utils.XToast;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

public class NameVerfityActivity extends BaseActivityOld implements OnClickListener {

    TextView title_name, right, tv_description, tv_flag;
    EditText et_content;


    @Override
    protected void init() {
        super.init();
//        SetTransStatus.GetStatus(this);
        setContentView(R.layout.verify_editxt_new);
        // title
        title_name = (TextView) findViewById(R.id.title_name);
        right = (TextView) findViewById(R.id.right);
        tv_flag = (TextView) findViewById(R.id.tv_flag);
        et_content = (EditText) findViewById(R.id.et_content);
        tv_description = (TextView) findViewById(R.id.tv_description);
        // set
        title_name.setText("姓名");
        right.setTextColor(getResources().getColor(R.color.rednew));
        right.setText("提交");
        // get
        String name = getIntent().getExtras().getString("name");
        String sex = getIntent().getExtras().getString("tv_sex");
        if (sex.equals("男")) {
            tv_description.setText("先生");
        } else if (sex.equals("女")) {
            tv_description.setText("女士");
        } else {
            tv_description.setText("先生/女士");
        }

        if (!name.equals("")) {
            et_content.setText(name);
        } else {
            et_content.setHint("请输入");
        }
        tv_flag.setText("将用于员工联系称呼，填写你的姓氏或全名都可以，不能超过5个汉字");
        // 限定edittext能输入内容
        et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
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
                String name = et_content.getText().toString();
                if (!name.equals("")) {
                    getMyinfo(name);
                } else {
                    XToast.makeText(this, "名称不能为空", XToast.LENGTH_SHORT).show();
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

    /**
     * 修改个人信息
     *
     * @param param
     */
    private void getMyinfo(final String param) {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("fullname", param);

        MyOkHttp.get().post(info.edit_center, params.getParams(), new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hideDialog(smallDialog);
                ShopProtocol protocol = new ShopProtocol();
                String str = protocol.setShop(response);
                if (str.equals("1")) {
                    closeInputMethod();
                    EventBus.getDefault().post(new PersoninfoBean());
//
                    finish();
                    XToast.makeText(NameVerfityActivity.this, "修改成功", XToast.LENGTH_SHORT)
                            .show();
                } else {
                    XToast.makeText(NameVerfityActivity.this, str, XToast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                UIUtils.showToastSafe("网络异常，请检查网络设置");
            }
        });
    }
}
