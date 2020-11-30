package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huacheng.huiservers.R;

/**
 * Description: 签到规则
 * created by wangxiaotao
 * 2020/11/25 0025 11:24
 */
public class SignRulDialog extends Dialog {
    String str;
    public SignRulDialog(@NonNull Context context, String str) {
        super(context, R.style.my_dialog_DimEnabled);
        this.str=str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_rule);
        init();
        Window window = getWindow();
        // 设置显示动画
        //     window.setWindowAnimations(R.style.main_menu_animstyle);
        //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = window.getAttributes();
        // 设置对话框显示的位置
        params.gravity = Gravity.CENTER;

        params.width = params.WRAP_CONTENT;
        //  params.width = params.WRAP_CONTENT;
        params.height = params.WRAP_CONTENT;
        window.setAttributes(params);
    }

    private void init() {
        TextView tv_intro = findViewById(R.id.tv_intro);
        tv_intro.setText(str);
        TextView tv_confirm = findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}