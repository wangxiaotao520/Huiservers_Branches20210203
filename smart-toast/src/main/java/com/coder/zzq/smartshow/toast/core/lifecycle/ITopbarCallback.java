package com.coder.zzq.smartshow.toast.core.lifecycle;

import android.app.Activity;

public interface ITopbarCallback extends IBarCallback {
    void adjustTopbarContainerIfNecessary(final Activity activity);
}
