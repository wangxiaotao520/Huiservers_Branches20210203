package com.huacheng.huiservers.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;


/**
 * 类：隐私政策
 * 时间：2019/9/64 15:43
 * 功能描述:Huiservers
 */
public class PrivacyPolicyDialog extends AlertDialog implements View.OnClickListener {
    TextView tv_cancel, tv_argee, tv_content;
    private OnCustomDialogListener mOnCustomDialogListener;
    Context mContext;

    public PrivacyPolicyDialog(Context context, OnCustomDialogListener customDialogListener) {
        super(context, R.style.my_dialog_DimEnabled);
        this.mContext = context;
        this.mOnCustomDialogListener = customDialogListener;

    }


    // 定义回调事件，用于dialog的点击事件
    public interface OnCustomDialogListener {
        public void back(String tag, Dialog dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_privacy_policy);
        setCancelable(false);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_argee = (TextView) findViewById(R.id.tv_argee);
        tv_content = (TextView) findViewById(R.id.tv_content);
        /*WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;*/
        tv_cancel.setOnClickListener(this);
        tv_argee.setOnClickListener(this);


        String shequ = "欢迎您使用社区慧生活！我们将通过《社区慧生活隐私政策》向您说明我们在您使用我们的产品与/或服务时如何收集、使用、保存、共享和转让这些信息，以及我们为您提供的访问、更新、删除和保护这些信息的方式。\n" +
                "本政策将帮助您了解以下内容:\n\n" +
                "1、我们如何收集和使用您的个人信息\n" +
                "2、我们如何使用Cookie和同类技术\n" +
                "3、我们如何共享、转让、公开披露您的个人信息\n" +
                "4、我们如何保护和保存您的个人信息\n" +
                "5、您如何管理个人信息\n" +
                "6、未成年人信息的保护\n" +
                "7、通知和修订\n" +
                "8、如何联系我们\n" +
                "您可以通过阅读完整版《社区慧生活隐私政策》，了解个人信息类型与用途的对应关系等更加详尽的个人信息处理规则。\n\n" +
                "    如您同意，请点击“同意”开始接受我们的服务。";

        // textview.setText(shequ);
        SpannableString spannableString = new SpannableString(shequ);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //Do something.
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(ApiHttpClient.GET_PRIVARY);
                intent.setData(content_url);
                mContext.startActivity(intent);
                //  ((TextView)widget).setHighlightColor(getResources().getColor(R.color.transparents));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#2A78FE"));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        String span = "完整版《社区慧生活隐私政策》";
        spannableString.setSpan(clickableSpan, shequ.indexOf(span) + 3, shequ.indexOf(span) + span.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_content.setText(spannableString);
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        tv_content.setHighlightColor(mContext.getResources().getColor(R.color.transparents));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                mOnCustomDialogListener.back("1", this);
                break;
            case R.id.tv_argee:
                mOnCustomDialogListener.back("2", this);
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /**
         * event.getRepeatCount() 重复次数,点后退键的时候，为了防止点得过快，触发两次后退事件，故做此设置。
         *
         * 建议保留这个判断，增强程序健壮性。
         */
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}