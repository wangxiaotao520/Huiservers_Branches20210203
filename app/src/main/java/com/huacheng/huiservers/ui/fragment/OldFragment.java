package com.huacheng.huiservers.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseFragment;
import com.huacheng.libraryservice.utils.TDevice;

/**
 * Description: 居家养老
 * created by wangxiaotao
 * 2019/8/13 0013 上午 10:58
 */
public class OldFragment extends BaseFragment{

    private TextView title_name;
    View mStatusBar;

    @Override
    public void initView(View view) {
        //状态栏
        mStatusBar=view.findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mActivity)));
        mStatusBar.setAlpha((float)1);
        view.findViewById(R.id.lin_left).setVisibility(View.GONE);
        title_name = view.findViewById(R.id.title_name);
        title_name.setText("居家养老");

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
        return R.layout.fragment_old;
    }
}
