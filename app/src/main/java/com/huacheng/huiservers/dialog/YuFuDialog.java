package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huacheng.huiservers.R;

/**
 * Description: 预付Dialog
 * created by wangxiaotao
 * 2018/12/13 0013 下午 6:58
 */
public class YuFuDialog extends Dialog implements View.OnClickListener {
    OnClickSureListener listener;
    String price;
    private TextView tv_price;
    private TextView tv_confirm;

    public YuFuDialog(@NonNull Context context, String price, OnClickSureListener listener) {
        super(context, R.style.my_dialog_DimEnabled);
        this.listener = listener;
        this.price = price;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_yufu);
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
        tv_price = findViewById(R.id.tv_price);
        price=  "¥ " + price;
        SpannableString spannableString = new SpannableString(price);
        int length = ( price).length();
        int index = price.indexOf(".");
        if (index>0){
            RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(0.6f);
            spannableString.setSpan(sizeSpan01, index+1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv_price.setText(spannableString);
        tv_confirm = findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_confirm) {
            if (listener != null) {
                listener.onEnSure();
            }
        }
    }

    public interface OnClickSureListener {
        void onEnSure();
    }
}
