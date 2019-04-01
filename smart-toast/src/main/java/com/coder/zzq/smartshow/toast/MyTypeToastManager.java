package com.coder.zzq.smartshow.toast;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.RestrictTo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.core.SmartShow;


/**
 * Description: 我的自定义toast
 * created by wangxiaotao
 * 2019/3/26 0026 下午 4:38
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class MyTypeToastManager extends BaseToastManager  {


    public MyTypeToastManager() {
        super();
    }

    @Override
    protected Toast createToast() {
        mToast = new Toast(SmartShow.getContext());
        mView = LayoutInflater.from(SmartShow.getContext()).inflate(R.layout.my_layout_type_info, null);
        mMsgView = mView.findViewById(R.id.type_info_message);
        mToast.setView(mView);
        return mToast;
    }


    @Override
    protected void updateToast() {
        super.updateToast();
    }

    @Override
    protected void setupToast() {
        super.setupToast();
        mWindowParams.windowAnimations = R.style.type_info_toast_anim;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        if (ToastDelegate.get().hasToastSetting()
                && ToastDelegate.get().getToastSetting().isTypeInfoThemeColorSetup()) {
            GradientDrawable drawable = (GradientDrawable) mView.getBackground();
            drawable.setColor(ToastDelegate.get().getToastSetting().getTypeInfoThemeColor());
        }
    }

    @Override
    protected int getToastType() {
        //TODO
        return TYPE_TOAST;
      //  return CUSTOM_TOAST;
    }

    private final void showHelper(CharSequence msg, int duration) {
        ToastDelegate.get().dismissPlainShowIfNeed();
        msg = (msg == null) ? "" : msg;
        //文本是否改变
        boolean contentChanged = !mCurMsg.equals(msg.toString().trim());


        boolean needInvokeShow = !isShowing();
        if (isShowing() && contentChanged ) {
            dismiss();
            needInvokeShow = true;
        }

        mCurMsg = msg;
        mDuration = duration;

        updateToast();

        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(mDuration);
        if (needInvokeShow) {
            showToast();
        }
    }


    public void showInfo(CharSequence msg) {
        showHelper(msg, Toast.LENGTH_SHORT);
    }


    public void showInfoLong(CharSequence msg) {
        showHelper(msg, Toast.LENGTH_LONG);
    }



}

