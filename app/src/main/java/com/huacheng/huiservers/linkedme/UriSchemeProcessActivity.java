package com.huacheng.huiservers.linkedme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.huacheng.huiservers.R;

/**
 * UriSchemeProcessActivity继承AppCompatActivity或者Activity，不继承基类
 */
public class UriSchemeProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 解决冷启动时显示纯白或纯黑背景
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        // 唤起自身
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        assert intent != null;
        intent.setFlags(getIntent().getFlags());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // App打开后无广告展示及登录等条件限制，直接在此处调用以下方法跳转到具体页面，若有条件限制请参考Demo
//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            LinkedME.getInstance().setImmediate(true);
//        }

        // 防止唤起后台App后一直停留在该页面
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 请重写改方法并且设置该Activity的launchmode为singleTask
        setIntent(intent);
    }

}
