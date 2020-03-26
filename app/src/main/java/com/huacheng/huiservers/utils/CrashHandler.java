package com.huacheng.huiservers.utils;

import android.content.Context;

import com.huacheng.huiservers.ui.base.ActivityStackManager;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static volatile CrashHandler crashHandler;

    private Context context;

    private CrashHandler(){}

    public void init(Context context){
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CrashHandler getCrashHander(){
        if (crashHandler == null){
            synchronized (CrashHandler.class){
                if (crashHandler == null){
                    crashHandler = new CrashHandler();
                }
            }
        }
        return crashHandler;
    }

    @Override
    public void uncaughtException(Thread t, final Throwable e) {
        // 提示信息
//        new Thread() {
//            @Override
//            public void run() {
////                Looper.prepare();
////                Intent intent = new Intent(context , CrashActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                intent.putExtra("exceptionOfCrash" , e.getMessage());
////                context.startActivity(intent);
////                Looper.loop();
//            }
//        }.start();
        //FIXME 这块儿感觉有点问题 到时候有时间统一优化一下

        ActivityStackManager.getActivityStackManager().finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());

        System.exit(1);
    }
}
