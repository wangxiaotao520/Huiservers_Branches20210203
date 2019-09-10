package com.huacheng.huiservers.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;


/**
 * 类：投票弹窗
 * 时间：2019/9/4 15:43
 * 功能描述:Huiservers
 */
public class VoteDialog extends AlertDialog implements View.OnClickListener {
    TextView tv_confirm;
    ImageView iv_cancel;
    private OnCustomDialogListener mOnCustomDialogListener;
    Context mContext;

    public VoteDialog(Context context, OnCustomDialogListener customDialogListener) {
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
        setContentView(R.layout.dialog_vote_poll);
        setCancelable(false);
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        iv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancel:
            case R.id.tv_confirm:
                mOnCustomDialogListener.back("1", this);
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