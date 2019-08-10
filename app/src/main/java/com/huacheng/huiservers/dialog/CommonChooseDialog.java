package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.utils.StringUtils;

/**
 * Description: 通用选择Dialog
 * created by wangxiaotao
 * 2019/4/9 0009 上午 10:28
 */
public class CommonChooseDialog extends Dialog {
    String[] strs;
    OnClickItemListener listener;
    public CommonChooseDialog(@NonNull Context context, String[] strs, OnClickItemListener listener) {
        super(context, R.style.my_dialog_DimEnabled);
        this.strs=strs;
        this.listener= listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_choose);
        init();

        Window window = getWindow();
        // 设置显示动画
        //     window.setWindowAnimations(R.style.main_menu_animstyle);
        //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = window.getAttributes();
        // 设置对话框显示的位置
        params.gravity = Gravity.BOTTOM;

        params.width = params.MATCH_PARENT;
        //  params.width = params.WRAP_CONTENT;
        params.height = params.WRAP_CONTENT;
        window.setAttributes(params);
    }

    private void init() {
        TextView tv_cancel = findViewById(R.id.tv_cancel);
        tv_cancel.setTextSize(18);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        LinearLayout ll_container = findViewById(R.id.ll_container);
        for (int i = 0; i < strs.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StringUtils.dip2px(getContext(), 44));
            TextView textView = new TextView(getContext());
            textView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            textView.setTextColor(getContext().getResources().getColor(R.color.orange));
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(params);
            textView.setText(strs[i]);
            textView.setTag(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    if (listener!=null){
                        listener.onClickItem(position);
                    }
                    dismiss();
                }
            });
            if (i>0){
                //添加一条线
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StringUtils.dip2px(getContext(), (float) 0.5));
                View view = new View(getContext());
                view.setBackgroundColor(getContext().getResources().getColor(R.color.line));
                ll_container.addView(view,params1);
            }
            ll_container.addView(textView);

        }
    }

    public interface OnClickItemListener{
        void onClickItem(int position);
    }
    @Override
    public void show() {
        super.show();
        this.getWindow().setWindowAnimations(R.style.main_menu_animstyle);
    }
}
