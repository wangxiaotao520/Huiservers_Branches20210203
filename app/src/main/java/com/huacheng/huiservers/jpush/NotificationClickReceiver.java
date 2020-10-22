package com.huacheng.huiservers.jpush;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.ajb.call.service.KeepAliveService;
import com.huacheng.huiservers.HomeActivity;
import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.db.UserSql;
import com.huacheng.huiservers.model.ModelJpushNotifaction;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 点击自定义消息的receiver
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    Intent i = null;
    public static String message;
    @Override
    public void onReceive(Context context, Intent intent) {
        // 跳转之前要处理的逻辑
        Log.e("TAG", "userClick:我被点击啦！！！ ");
        Bundle bundle = intent.getExtras();
        skip(context,bundle);
    }
    private void skip(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (NullUtil.isStringEmpty(extras)){
            return;
        }
        message = bundle.getString(JPushInterface.EXTRA_ALERT);
        try {
            JSONObject jsonObject = new JSONObject(extras);

            if ("Calling".equals(message)) {

                KeepAliveService.handlePushMsg(context, message, extras);
                JPushInterface.clearNotificationById(context,
                        bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));

                return;
            } else {
                //物业工单url_type为27时，判断是否跳工单详情
                String url_type = jsonObject.getString("url_type");
                if ("27".equals(url_type)) {
                    if (UserSql.getInstance().hasLoginUser()!=null) {//判断是否登录
                        //极光推送 工单处理
                        if (!TextUtils.isEmpty(extras)) {
                            ModelJpushNotifaction modelJpushNotifaction = (ModelJpushNotifaction) JsonUtil.getInstance().parseJson(extras, ModelJpushNotifaction.class);
                            if (modelJpushNotifaction != null) {

                                String work_id = modelJpushNotifaction.getData().getId();

//                                Intent intent = new Intent(context, WorkOrderDetailActivity.class);
//                                intent.putExtra("id", work_id);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(intent);

                                if (!isRun(context)) {
                                    Intent intentt = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                                    intentt.putExtra("from", "jpush");
                                    intentt.putExtra("type", "1");
                                    intentt.putExtra("url_type", url_type);   //推给管理和师傅 1是列表 2是详情 推给慧生活用这个 27是详情

                                    intentt.putExtra("j_id", work_id);
                                    intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intentt);
                                } else {
                                    i = new Intent(context, HomeActivity.class);
                                    i.putExtra("from", "jpush");
                                    i.putExtra("type", "1");
                                    i.putExtra("url_type", url_type);

                                    i.putExtra("j_id", work_id);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(i);
                                }

                            }
                        }

                    } else {
                        //没登录就让它跳到首页算求
                        if (!isRun(context)) {
                            Intent intentt = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                            intentt.putExtra("from", "");
                            intentt.putExtra("type", "");
                            intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intentt);
                        } else {
                            i = new Intent(context, HomeActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    }
                } else if ("41".equals(url_type)){
                    if (UserSql.getInstance().hasLoginUser()!=null) {//判断是否登录
                        //极光推送 工单处理
                        if (!TextUtils.isEmpty(extras)) {
                            ModelJpushNotifaction modelJpushNotifaction = (ModelJpushNotifaction) JsonUtil.getInstance().parseJson(extras, ModelJpushNotifaction.class);
                            if (modelJpushNotifaction != null) {

                                String par_uid = modelJpushNotifaction.getData().getPar_uid();

//                                Intent intent = new Intent(context, WorkOrderDetailActivity.class);
//                                intent.putExtra("id", work_id);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(intent);

                                if (!isRun(context)) {
                                    Intent intentt = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                                    intentt.putExtra("from", "jpush");
                                    intentt.putExtra("type", "1");
                                    intentt.putExtra("url_type", url_type);   //推给管理和师傅 1是列表 2是详情 推给慧生活用这个 27是详情

                                    intentt.putExtra("par_uid", par_uid);
                                    intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intentt);
                                } else {
                                    i = new Intent(context, HomeActivity.class);
                                    i.putExtra("from", "jpush");
                                    i.putExtra("type", "1");
                                    i.putExtra("url_type", url_type);

                                    i.putExtra("par_uid", par_uid);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(i);
                                }

                            }
                        }

                    } else {
                        //没登录就让它跳到首页算求
                        if (!isRun(context)) {
                            Intent intentt = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                            intentt.putExtra("from", "");
                            intentt.putExtra("type", "");
                            intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intentt);
                        } else {
                            i = new Intent(context, HomeActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                    }


                }else {
                    String url_link = jsonObject.getString("url_link");
                    if (!TextUtils.isEmpty(url_type) && !TextUtils.isEmpty(url_link)) {
                        new Jump(context, url_type, url_link, "", "");
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断app是否在前台运行
     * @param context
     * @return
     */
    private static boolean isAppForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
        if (runningAppProcessInfoList == null) {
            //  Log.d(TAG, "runningAppProcessInfoList is null!");
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfoList) {
            if (processInfo.processName.equals(context.getPackageName())
                    && (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断app是否运行
     * @param context
     * @return
     */
    public boolean isRun(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        String MY_PKG_NAME = "com.huacheng.huiservers";
        //100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                isAppRunning = true;
                break;
            }
        }
        return isAppRunning;
    }

}

