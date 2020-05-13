package com.huacheng.huiservers.ui.center.logout;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.webview.ConstantWebView;
import com.huacheng.huiservers.ui.webview.WebViewDefaultActivity;
import com.huacheng.huiservers.utils.TextCheckUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：输入新号码
 * 时间：2020/5/8 09:06
 * created by DFF
 */
public class NewNumberActivity extends BaseActivity {
    private String phone = "";
    private TextView tv_old_number;
    private EditText edit_number;
    private TextView tv_next;
    private TextView tv_content;
    private LinearLayout ly_select;
    private ImageView iv_select;
    private boolean isChecked = true;


    @Override
    protected void initView() {

        findTitleViews();
        tv_old_number = findViewById(R.id.tv_old_number);
        edit_number = findViewById(R.id.edit_number);
        tv_content = findViewById(R.id.tv_content);
        ly_select = findViewById(R.id.ly_select);
        iv_select = findViewById(R.id.iv_select);
        tv_next = findViewById(R.id.tv_next);
        tv_old_number.setText("当前手机号：" + phone + "，绑定新手机号后下次登录可使用新手机号登录");

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
        String content = "同意《社区慧生活服务协议》和《隐私政策》";
        SpannableString spannableString = new SpannableString(content);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(mContext, WebViewDefaultActivity.class);
                intent.putExtra("web_type", 0);
                intent.putExtra("jump_type", ConstantWebView.CONSTANT_FUWU_XIEYI);
                mContext.startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#2A78FE"));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(mContext, WebViewDefaultActivity.class);
                intent.putExtra("web_type", 0);
                intent.putExtra("jump_type", ConstantWebView.CONSTANT_YINGSI);
                mContext.startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#2A78FE"));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        // String span = "同意《社区慧生活服务协议》和《隐私政策》";
        spannableString.setSpan(clickableSpan, content.indexOf(content) + 2, content.indexOf(content) + 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan1, content.indexOf(content) + 14, content.indexOf(content) + content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_content.setText(spannableString);
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        tv_content.setHighlightColor(mContext.getResources().getColor(R.color.transparent));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if (!isChecked) {
                    getNext();
                } else {
                    SmartToast.showInfo("未选中服务协议");
                }

            }
        });

        ly_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked) {
                    iv_select.setBackground(getResources().getDrawable(R.mipmap.ic_loginout_select));
                    isChecked = false;
                } else {
                    iv_select.setBackground(getResources().getDrawable(R.mipmap.ic_loginout_unselect));
                    isChecked = true;
                }
            }
        });
    }

    /**
     * 验证原手机号
     */
    private void getNext() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("new_phone", edit_number.getText().toString().trim() + "");

        MyOkHttp.get().post(ApiHttpClient.VERITY_NEW_PHONE, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    Intent intent = new Intent(NewNumberActivity.this, NewNumberCodeActivity.class);
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
        return R.layout.layout_new_number;
    }

    @Override
    protected void initIntentData() {
        phone = this.getIntent().getStringExtra("phone");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
