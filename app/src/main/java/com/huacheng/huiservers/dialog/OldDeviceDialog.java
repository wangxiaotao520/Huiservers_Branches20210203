package com.huacheng.huiservers.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
 * 类描述：
 * 时间：2020/10/4 14:12
 * created by DFF
 */
public class OldDeviceDialog extends Dialog {
    private InputMethodManager imm;
    private Context mContext;
    private EditText et_number1;
    private EditText et_number2;
    private int type;
    private int mLastDiff = 0;
    private Button btn_confirm;
    private TextView tv_type;
    String content;
    private OldDeviceDialog.OnClickConfirmListener listener;

    public OldDeviceDialog(@NonNull Context context, OldDeviceDialog.OnClickConfirmListener listener, int type) {
        super(context, R.style.my_dialog_DimEnabled);
        mContext = context;
        this.listener = listener;
        this.type = type;

    }

    public OldDeviceDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public OldDeviceDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_old_device_number);
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
        tv_type = findViewById(R.id.tv_type);
        et_number1 = findViewById(R.id.et_number1);
        et_number2 = findViewById(R.id.et_number2);
        btn_confirm = findViewById(R.id.btn_confirm);

        if (type == 1) {//SOS
            tv_type.setText("SOS手机号码");
            et_number1.setHint("请输入手机号码1");
            et_number2.setVisibility(View.VISIBLE);
        } else {
            //监护号码
            tv_type.setText("监护号码");
            et_number1.setHint("请输入手机号码");

        }
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    if (type == 1) {
                        if ("".equals(et_number2.getText().toString().trim())) {
                            content = et_number1.getText().toString().trim();
                        } else {
                            content = et_number1.getText().toString().trim() + "," + et_number2.getText().toString().trim();
                        }
                    } else {
                        content = et_number1.getText().toString().trim();
                    }
                    listener.onClickConfirm(OldDeviceDialog.this, content, type);
                }

                imm.showSoftInput(et_number1, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(et_number1.getWindowToken(), 0);
                et_number1.setText("");
                // 请求数据

                dismiss();
            }
        });

        et_number1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case KeyEvent.KEYCODE_ENDCALL:
                    case KeyEvent.KEYCODE_ENTER:
                        if (et_number1.getText().length() >= 11) {
//                            mOnTextSendListener.onTextSend("" + messageTextView.getText(), mDanmuOpen);
                            //sendText("" + messageTextView.getText());
                            //imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                            imm.hideSoftInputFromWindow(et_number1.getWindowToken(), 0);
//                            messageTextView.setText("");
                            if (listener != null) {
                                String content = et_number1.getText().toString().trim();
                                listener.onClickConfirm(OldDeviceDialog.this, content, type);
                            }
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

        et_number1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    if (s.length() >= 11) {
                        btn_confirm.setTextColor(mContext.getResources().getColor(R.color.orange));
                        btn_confirm.setClickable(true);
                    } else {
                        btn_confirm.setTextColor(mContext.getResources().getColor(R.color.title_third_color));
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
        et_number1.setFocusableInTouchMode(true);
        et_number1.requestFocus();
        et_number1.postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.showSoftInput(et_number1, InputMethodManager.SHOW_FORCED);
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

    public interface OnClickConfirmListener {
        void onClickConfirm(Dialog dialog, String content, int type);
    }
}
