package com.huacheng.huiservers.ui.webview.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huacheng.huiservers.dialog.SmallDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 专门用于使用webview 的basefragment
 *Created by wangxiaotao
 */
public abstract class BaseDelegate extends Fragment {

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;
    protected SmallDialog smallDialog;//等待对话框
    protected Activity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity=activity;
    }

    //子类必须实现，可以返回一个layout的资源id，或一个view
    public abstract Object setLayout();

    //子类初始化时回调
    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        smallDialog=new SmallDialog(mActivity);
        View rootView = null;
        Object mLayout = setLayout();
        if (mLayout instanceof Integer) {
            rootView = inflater.inflate((Integer) mLayout, container, false);
        } else if (mLayout instanceof View) {
            rootView = (View) mLayout;
        }

        if (rootView != null) {
            mUnbinder = ButterKnife.bind(this, rootView);
            onBindView(savedInstanceState, rootView);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
    /**
     * 显示对话框（防止窗体溢出）
     * @param mDialog
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showDialog(Dialog mDialog) {
        if(mDialog != null) {
            Context context = ((ContextWrapper)mDialog.getContext()).getBaseContext();
            if(context instanceof Activity) {
                if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
                    mDialog.show();
            } else {
                mDialog.show();
            }
        }
    }

    /**
     * 隐藏对话框 （防止窗体溢出）
     * @param mDialog
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void hideDialog(Dialog mDialog) {
        if(mDialog != null && mDialog.isShowing()) {
            Context context = ((ContextWrapper)mDialog.getContext()).getBaseContext();
            if(context instanceof Activity) {
                if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
                    mDialog.dismiss();
            } else {
                mDialog.dismiss();
            }
//				mDialog = null;
        }
    }
}