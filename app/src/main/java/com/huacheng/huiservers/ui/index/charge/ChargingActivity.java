package com.huacheng.huiservers.ui.index.charge;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.TDevice;
import com.littlejie.circleprogress.DialProgress;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:
 * created by wangxiaotao
 * 2019/8/19 0019 下午 6:06
 */
public class ChargingActivity  extends BaseActivity implements View.OnClickListener {
    View mStatusBar;
    private LinearLayout lin_left;
    private DialProgress mDialProgress;
    private Timer timer;
    private boolean isMax=false;
    long time = 0;
    long preTime = 0;
    TimerTask task = new TimerTask(){
        public void run(){
            // 在此处添加执行的代码
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isMax){
                        mDialProgress.setValue(1);
                    }else {
                        mDialProgress.setValue(mDialProgress.getMaxValue());
                    }
                    isMax=!isMax;
                }
            });
        }
    };

    final Handler handler = new Handler(){

        public void handleMessage(Message msg){         // handle message

                        Message message = handler.obtainMessage(1);
                        handler.sendMessageDelayed(message, 1000);      // send message

            time=time+(System.currentTimeMillis()-preTime);
            tv_count_up.setText(StringUtils.changeTime2((int)time/1000));
            preTime=System.currentTimeMillis();
            super.handleMessage(msg);
        }
    };
    private TextView tv_count_up;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mStatusBar=findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha((float)1);
        lin_left = findViewById(R.id.lin_left);
        mDialProgress=findViewById(R.id.dial_progress_bar);
        tv_count_up = findViewById(R.id.tv_count_up);
    }

    @Override
    protected void initData() {
        mDialProgress.setValue(mDialProgress.getMaxValue());
        isMax=true;
        timer=new Timer();
        timer.schedule(task,2500,2200);
        //计时
        Message message = handler.obtainMessage(1);
        handler.sendMessageDelayed(message,1000);
        preTime= System.currentTimeMillis();
    }

    @Override
    protected void initListener() {
        lin_left.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charging;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lin_left:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
        if (handler!=null){
            handler.removeMessages(1);
        }
    }
}
