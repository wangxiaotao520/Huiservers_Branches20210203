package com.coder.zzq.smartshow.toast;


import android.app.Activity;

import com.coder.zzq.smartshow.toast.core.Utils;
import com.coder.zzq.smartshow.toast.core.lifecycle.IToastCallback;


public class ToastCallback implements IToastCallback {
    @Override
    public void dismissOnLeave() {
        if (ToastDelegate.hasCreated() && (ToastDelegate.get().isDismissOnLeave() || !Utils.isNotificationPermitted())) {
            ToastDelegate.get().dismiss();
        }
    }

    @Override
    public void recycleOnDestroy(Activity activity) {
        if (VirtualToastManager.hasCreated()){
            VirtualToastManager.get().destroy(activity);
        }
    }
}
