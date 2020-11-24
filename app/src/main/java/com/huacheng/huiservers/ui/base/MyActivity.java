package com.huacheng.huiservers.ui.base;

import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;


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
}
