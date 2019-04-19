package com.huacheng.huiservers.jpush;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.ajb.call.service.KeepAliveService;
import com.ajb.call.utlis.CommonUtils;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelJpushNotifaction;
import com.huacheng.huiservers.ui.index.workorder.WorkOrderDetailActivity;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.utils.statusbar.OSUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";
    private NotificationManager nm;
    SharedPreferences preferencesLogin;

    SharedPreferences userInfo;
    private String login_type, lat, lon;
    String urlh5, phone;
    int num = 0;
    Bundle b = new Bundle();
    Intent intent = new Intent();

    public static NotificationManager manger;
    public static Notification notification;
    public static int id;
    public static String message;
    private SharePrefrenceUtil sharePrefrenceUtil;
    private String token;
    private String tokenSecret;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharePrefrenceUtil = new SharePrefrenceUtil(context);
        preferencesLogin = context.getSharedPreferences("login", 0);
//        login_type = preferencesLogin.getString("login_type", "");

        token = preferencesLogin.getString("token", "");
        tokenSecret = preferencesLogin.getString("tokenSecret", "");

        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            SharedPreferences preferences1 = context.getSharedPreferences(
                    "jpush_id", 0);
            Editor editor = preferences1.edit();
            editor.putString("reg_id", regId);
            editor.commit();
            Log.d("TAG", "[MyReceiver] 接收Registration Id : " + regId);
            System.out.println("接收Registration Id^^^^^^^^^^^^^^^^^^^^" + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推 下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            //打开自定义的Activity
            skip(context, bundle);//点击跳转
            //receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    private void skip(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
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

                    if (hasLoginUser()) {//判断是否登录
                        //极光推送 工单处理
                        ModelJpushNotifaction modelJpushNotifaction = (ModelJpushNotifaction) JsonUtil.getInstance().parseJson(extras, ModelJpushNotifaction.class);
                        if (modelJpushNotifaction != null) {
                            String work_id = modelJpushNotifaction.getData().getId();

                            Intent intent = new Intent(context, WorkOrderDetailActivity.class);
                            intent.putExtra("work_id", work_id);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }

                    } else {
                        Intent intent = new Intent();
                        intent.setClass(context, LoginVerifyCodeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                } else {
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

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }


    private void processCustomMessage(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        if (!TextUtils.isEmpty(extras)) {
            JSONObject jsonObject;
            try {

                ModelJpushNotifaction modelJpushNotifaction = (ModelJpushNotifaction) JsonUtil.getInstance().parseJson(extras, ModelJpushNotifaction.class);
                if (modelJpushNotifaction != null) {
                    showNotifaction(context, bundle);
                }

                jsonObject = new JSONObject(extras);
                if (!jsonObject.optString("badge").equals("")) {
                    String num = jsonObject.getString("badge");
                    if (ToolUtils.isNum(num)) {
                        if (OSUtils.getSystemBrand() == OSUtils.SYSTEM_HUAWEI) {
                            setBadgeOfHuaWei(context, Integer.parseInt(num));
                        }
                    } else {
                        SmartToast.showInfo("类型异常");
                    }
                } else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 显示通知栏
     *
     * @param context
     * @param bundle
     */
    private void showNotifaction(Context context, Bundle bundle) {
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        //Ticker是状态栏显示的提示
        builder.setTicker(bundle.getString(JPushInterface.EXTRA_TITLE));
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle(bundle.getString(JPushInterface.EXTRA_TITLE));
        //第二行内容 通常是通知正文
        builder.setContentText(bundle.getString(JPushInterface.EXTRA_MESSAGE));
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.drawable.app_logo);
        notification = builder.build();

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notification.sound = uri; //Uri.parse("android.resource://" + context.getPackageName() + "/" + Notification.DEFAULT_SOUND);
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE | NotificationCompat.DEFAULT_LIGHTS);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        Intent clickIntent = new Intent(); //点击通知之后要发送的广播
        int id = (int) (System.currentTimeMillis() / 1000);
        clickIntent.addCategory(BaseApplication.getApplication().getPackageName());
        clickIntent.setAction(JPushInterface.ACTION_NOTIFICATION_OPENED);
        clickIntent.putExtra(JPushInterface.EXTRA_EXTRA, bundle.getString(JPushInterface.EXTRA_EXTRA));
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, id, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = contentIntent;

        NotificationManager manger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manger.notify(id, notification);
    }

    // 华为 系列
    public static void setBadgeOfHuaWei(Context context, int num) {

        try {
            Bundle localBundle = new Bundle();
            localBundle.putString("package", context.getPackageName());
            localBundle.putString("class", getLauncherClassName(context));
            localBundle.putInt("badgenumber", num);
            context.getContentResolver()
                    .call(Uri
                                    .parse("content://com.huawei.android.launcher.settings/badge/"),
                            "change_badge", null, localBundle);
            if (num != 0) {
                manger.notify(id, notification);
            } else if (id != 0) {
                // 想要清空通知
                if (null == manger) {
                    manger = (NotificationManager) context
                            .getSystemService(Context.NOTIFICATION_SERVICE);
                }
                // manger.cancel(id);

                JPushInterface.clearNotificationById(context, id);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HUAWEI" + " Badge error", "set Badge failed");
        }

    }

    public static void clearBadgeOfHuaWei() {
        if (id != 0) {
            manger.cancel(id);
        }
    }

    // 获取类名
    public static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ResolveInfo info = packageManager.resolveActivity(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }
        return info.activityInfo.name;
    }

    //处理接收到的通知
    private void receivingNotification(Context context, Bundle bundle) {
        CommonUtils.setDebugMode(context, true, "http://47.104.92.9:8081");
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        message = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, " msg : " + message);
        Log.d(TAG, " extra : " + extra);
        if (message.equals("Calling")) {
            KeepAliveService.handlePushMsg(context, message, extra);
            JPushInterface.clearNotificationById(context,
                    bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
            return;
        }
    }

    private boolean hasLoginUser() {
        if (!NullUtil.isStringEmpty(token) && !NullUtil.isStringEmpty(tokenSecret)) {
            ApiHttpClient.TOKEN = token;
            ApiHttpClient.TOKEN_SECRET = tokenSecret;
            return true;
        }
        return false;
    }
}
