package com.huacheng.huiservers.ui.index.charge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huacheng.huiservers.R;

/**
 * Description:
 * created by wangxiaotao
 * 2019/8/17 0017 下午 3:53
 */
public class ChargeEquipNumberDialog extends Dialog {

    private InputMethodManager imm;
    private Context mContext;
    private EditText et_getcode;

    private int mLastDiff = 0;
    private Button btn_confirm;

    public ChargeEquipNumberDialog(@NonNull Context context) {
        super(context, R.style.my_dialog_DimEnabled);
        mContext=context;
    }

    public ChargeEquipNumberDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public ChargeEquipNumberDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_charge_equip_number);
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
        params.height = params.MATCH_PARENT;
        window.setAttributes(params);
    }

    private void init() {
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        et_getcode = findViewById(R.id.et_getcode);
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(et_getcode.getWindowToken(), 0);
                dismiss();
            }
        });
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.showSoftInput(et_getcode, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(et_getcode.getWindowToken(), 0);
                et_getcode.setText("");
                //TODO 请求数据
                dismiss();
            }
        });

        et_getcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case KeyEvent.KEYCODE_ENDCALL:
                    case KeyEvent.KEYCODE_ENTER:
                        if (et_getcode.getText().length() >= 11) {
//                            mOnTextSendListener.onTextSend("" + messageTextView.getText(), mDanmuOpen);
                            //sendText("" + messageTextView.getText());
                            //imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                            imm.hideSoftInputFromWindow(et_getcode.getWindowToken(), 0);
//                            messageTextView.setText("");
                            //TODO 请求数据
                            dismiss();
                        } else {
                            //SmartToast.showInfo("内容为空");
                        }
                        return true;
                    case KeyEvent.KEYCODE_BACK:
                        dismiss();
                        return false;
                    default:
                        return false;
                }
            }
        });

        et_getcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s!=null){
                    if (s.length()>=11){
                        btn_confirm.setTextColor(Color.parseColor("#ED8D37"));
                        btn_confirm.setClickable(true);
                    }else {
                        btn_confirm.setTextColor(Color.parseColor("#999999"));
                        btn_confirm.setClickable(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //dismiss之前重置mLastDiff值避免下次无法打开
        mLastDiff = 0;
    }

    @Override
    public void show() {
        super.show();
        //imm.showSoftInput(et_getcode, InputMethodManager.SHOW_FORCED);
        //showSoftInput((Activity) mContext);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //弹出对话框后直接弹出键盘
        et_getcode.setFocusableInTouchMode(true);
        et_getcode.requestFocus();
        et_getcode.postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.showSoftInput(et_getcode, InputMethodManager.SHOW_FORCED);
            }
        }, 50);
    }

    /**
     * 显示软键盘，Dialog使用
     *
     * @param activity 当前Activity
     */
    public static void showSoftInput(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
