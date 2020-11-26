package com.huacheng.huiservers.ui.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.libraryservice.utils.TDevice;


/**
 * @author Created by changyadong on 2020/11/9
 * @description
 */
public class MyActivity extends BaseActivity{
    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    public void back(){
        findViewById(R.id.back).setVisibility(View.VISIBLE);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void title(String title){

        TextView textView  = findViewById(R.id.title);
        textView.setText(title);
    }
    public void initStatubar(){

        View mStatusBar = findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(mContext)));
    }
}
