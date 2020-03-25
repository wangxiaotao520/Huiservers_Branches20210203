package com.huacheng.huiservers.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.HomeActivity;
import com.huacheng.huiservers.dialog.SmallDialog;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.utils.ThreadManager;
import com.huacheng.huiservers.utils.UIUtils;
import com.huacheng.huiservers.utils.statusbar.StatusBarUtil;
import com.huacheng.libraryservice.widget.SlidingLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 所有activity的父类
 *
 * @author Demon
 */
public class BaseActivityOld extends AppCompatActivity {
    /**
     * 记录处于前台的Activity
     */
    private static BaseActivityOld mForegroundActivity = null;
    static List<Activity> act = new ArrayList<Activity>();
    /**
     * 记录所有活动的Activity
     */
    private static final List<BaseActivityOld> mActivities = new LinkedList<BaseActivityOld>();
    //public static MessageDialog messageDialog;
    protected BaseApplication application;
    private BaseActivityOld oContext;
    protected SmallDialog smallDialog;//等待对话框

    // SpotsDialog dialog;
    /**
     * 是否沉浸状态栏
     **/
    protected boolean isStatusBar = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        if (enableSliding()) {
            setTheme(com.huacheng.libraryservice.R.style.AppTheme_Slide);

            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }else {
            setTheme(com.huacheng.libraryservice.R.style.AppCompatTheme_Base);
        }
        StatusBarUtil.setRootViewFitsSystemWindows(this,false);
        //设置状态栏透明
     //   StatusBarUtil.setTranslucentStatus(this);
        //设置状态栏的颜色

        if (isStatusBar){
            StatusBarUtil.setTranslucentStatus(this);
            if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
                StatusBarUtil.setStatusBarColor(this,0x55000000);
            }
        }else {
            if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
                //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
                //这样半透明+白=灰, 状态栏的文字能看得清
                StatusBarUtil.setStatusBarColor(this,0x55000000);
            }
        }
        if (smallDialog==null){
            smallDialog=new SmallDialog(this);
            smallDialog.setCanceledOnTouchOutside(false);
        }
        // dialog = new SpotsDialog(this);
        // SetTransStatus.GetStatus(this);//系统栏默认为黑色
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight  = display.getHeight();
        MyCookieStore.width = screenWidth;
        MyCookieStore.height = screenHeight;

        init();
        initView();

        // 5.0以上系统状态栏透明
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }*/
        if (application == null) {
            // 得到Application对象
            application = (BaseApplication) getApplication();
        }
        oContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法
    }

    /**
     * 是否允许右滑退出
     * @return
     */
    protected boolean enableSliding() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return false;
        }
       return false;
    }


    @Override
    protected void onResume() {
        mForegroundActivity = this;
        if (mForegroundActivity != HomeActivity.instant) {
            addDestoryActivity(mForegroundActivity);
        }
        //messageDialog = new MessageDialog(this);
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public static void addDestoryActivity(Activity activity) {
        act.add(activity);
    }

    public static void destoryActivity() {
        for (Activity qq : act) {
            qq.finish();
        }

    }

    @Override
    protected void onPause() {
        mForegroundActivity = null;
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 获取当前处于前台的activity
     */
    public static BaseActivityOld getForegroundActivity() {
        return mForegroundActivity;
    }

    /**
     * 获取当前处于栈顶的activity，无论其是否处于前台
     */
    public static BaseActivityOld getCurrentActivity() {
        List<BaseActivityOld> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivityOld>(mActivities);
        }
        if (copy.size() > 0) {
            return copy.get(copy.size() - 1);
        }
        return null;
    }

    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        List<BaseActivityOld> copy = new ArrayList<BaseActivityOld>();
        for (BaseActivityOld activity : copy) {
            activity.finish();
        }
    }

    /**
     * 加载数据，此方法在访问网络之后进行，用于刷新界面
     */
    protected void initData() {

    }

    protected void initView() {

    }

    protected void initList() {

    }

    protected void init() {
        // setBehindContentView(R.layout.add_client);
        // isEnableMenu(false);
    //    StatusBarCompat.setStatusBarColor(this, R.color.transparents, true);
        LoadingTask task = new LoadingTask();
        ThreadManager.getLongPool().execute(task);
    }

     class LoadingTask implements Runnable {
        @Override
        public void run() {
            setData();
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    initData();
                    initList();
                }
            });
        }
    }

    /**
     * 网络访问操作 注：此方法运行在子线程中，不可进行改变UI的操作
     */
    public void setNet() {
        LoadingTask task = new LoadingTask();
        ThreadManager.getLongPool().execute(task);
    }

    /**
     * 网络访问操作 注：此方法运行在子线程中，不可进行改变UI的操作
     */
    public void setData() {

    }

    public Object[] getSections() {
        return null;
    }


    // 添加Activity方法
    public void addActivity() {
        application.addActivity_(oContext);// 调用myApplication的添加Activity方法
    }

    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity_(oContext);// 调用myApplication的销毁单个Activity方法
    }

    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity_();// 调用myApplication的销毁所有Activity方法
    }
    /**
     * 隐藏对话框 （防止窗体溢出）
     *
     * @param mDialog
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void hideDialog(Dialog mDialog) {
        if (mDialog != null && mDialog.isShowing()) {
            Context context = ((ContextWrapper) mDialog.getContext()).getBaseContext();
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed())
                    mDialog.dismiss();
            } else {
                mDialog.dismiss();
            }
//				mDialog = null;
        }
    }

    /**
     * 显示对话框（防止窗体溢出）
     *
     * @param mDialog
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showDialog(Dialog mDialog) {
        if (mDialog != null) {
            Context context = ((ContextWrapper) mDialog.getContext()).getBaseContext();
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed())
                    mDialog.show();
            } else {
                mDialog.show();
            }
        }
    }

    @Override
    protected void onStop() {
        hideDialog(smallDialog);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
