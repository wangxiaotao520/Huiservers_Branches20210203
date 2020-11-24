package com.huacheng.huiservers.ui.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.SmallDialog;
import com.huacheng.libraryservice.utils.TDevice;

public class MyFragment extends BaseFragment {
    View mStatusBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smallDialog = new SmallDialog(mActivity);
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initIntentData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    public void initStatubar(){

        mStatusBar = getView().findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
    }
}
