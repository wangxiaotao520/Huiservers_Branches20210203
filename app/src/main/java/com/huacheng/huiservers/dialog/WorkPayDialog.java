package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huacheng.huiservers.R;

/**
 * Description:工单-付款弹窗
 * Author: badge
 * Create: 2018/12/17 15:46
 */
public class WorkPayDialog extends Dialog implements View.OnClickListener {

    OnClickSureListener listener;
    double price;
    double prepaid_fee;
    private TextView tv_price;
    private TextView tv_prepaid_fee;
    private TextView tv_confirm;

    public WorkPayDialog(Context context, double price,double prepaid_fee, OnClickSureListener listener) {
        super(context, R.style.my_dialog_DimEnabled);
        this.price = price;
        this.prepaid_fee=prepaid_fee;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_work_pay);
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
        tv_prepaid_fee = findViewById(R.id.tv_prepaid_fee);
        tv_price.setText("¥ " + (price-prepaid_fee));
        tv_prepaid_fee.setText("已扣除预付费用" + prepaid_fee + "元");

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