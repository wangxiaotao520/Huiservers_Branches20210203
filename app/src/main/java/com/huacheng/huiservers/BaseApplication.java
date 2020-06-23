package com.huacheng.huiservers;


import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.ajb.call.utlis.CommonUtils;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.smartshow.toast.core.SmartShow;
import com.huacheng.huiservers.linkedme.MiddleActivity;
import com.huacheng.huiservers.model.ModelUser;
import com.huacheng.huiservers.utils.CrashHandler;
import com.huacheng.huiservers.utils.LogUtils;
import com.huacheng.huiservers.utils.NightModeUtils;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.microquation.linkedme.android.LinkedME;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.tinker.entry.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.umeng.socialize.PlatformConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

import cn.com.chinatelecom.account.lib.auth.CtAuth;
import cn.jpush.android.api.JPushInterface;



public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    /**
     * 全局Context，原理是因为Application类是应用�?��运行的，�?��在我们的代码调用时，该�?已经被赋值过�?
     */
    private static BaseApplication mInstance;
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;

    private CtAuth ctAuth;

    //字体地址，一般放置在assets/fonts目录
    String fontPath = "fonts/scyahei.ttf";

    public static ModelUser user;
    public static Context mContext;

    private ApplicationLike tinkerApplicationLike;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //    layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                //   return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
                return new MaterialHeader(context).setColorSchemeColors(getApplication().getResources().getColor(R.color.orange));
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
    @Override
    public void onCreate() {
        super.onCreate();

//        replaceSystemDefaultFont(this,fontPath);
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        mInstance = this;
        mContext = this.getApplicationContext();

     /*   UMShareAPI.get(this);//初始化sdk
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;*/

        //设置夜间模式
//        if (new SharePrefrenceUtil(mContext).getNightMode()) {
//            NightModeUtils.setThemeMode(NightModeUtils.ThemeMode.NIGHT);
//        }else {
//            NightModeUtils.setThemeMode(NightModeUtils.ThemeMode.DAY);
//        }
        // Android Q下的适配，根据系统的情况进行操作
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
            //>28说明是AndroidQ
            int mode = this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (mode == Configuration.UI_MODE_NIGHT_YES ) {
                NightModeUtils.setThemeMode(NightModeUtils.ThemeMode.NIGHT);
            } else if (mode == Configuration.UI_MODE_NIGHT_NO ) {
                NightModeUtils.setThemeMode(NightModeUtils.ThemeMode.DAY);
            }
        }else {
            //设置夜间模式
            if (new SharePrefrenceUtil(mContext).getNightMode()) {
                NightModeUtils.setThemeMode(NightModeUtils.ThemeMode.NIGHT);
            }else {
                NightModeUtils.setThemeMode(NightModeUtils.ThemeMode.DAY);
            }

        }
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initImageLoaderConfig();
        ctAuth = CtAuth.getInstance();
        ctAuth.init(this, "tW0N2VEpc7BrAUaF2Y9XoWiY2bzjXtZM");
      /*  CrashHandler crashHandler = CrashHandler.getInstance();////错误日志打开的设置
        crashHandler.init(getApplicationContext());*/
        CommonUtils.setDebugMode(getApplicationContext(), true, "http://47.104.92.9:8081");

        //腾讯bug检索
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "bb9f25fed0", false, strategy);
        //CrashReport.initCrashReport(getApplicationContext(), "bb9f25fed0", true);
        //CrashReport.testJavaCrash();
        //初始化fresco
        FrescoUtils.getInstance().initializeFresco(this);
        //初始化linkedme
        initLinkedME();
        // 初始化tinker
        initTinkerPatch();
        //初始化smartshow
        SmartShow.init(this);
        SmartToast.setting()
//                .backgroundColorRes(R.color.colorPrimary)
                .dismissOnLeave(false);

        // 崩溃处理
        if (!LogUtils.isApkDebugable(this)){
           CrashHandler.getCrashHander().init(this);
        }
        //初始化x5webview
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);

    }
    /**
     * 我们需要确保至少对主进程跟patch进程初始化 TinkerPatch
     */
    private void initTinkerPatch() {
        // 我们可以从这里获得Tinker加载过程的信息
        if (BuildConfig.TINKER_ENABLE) {
            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
            // 初始化TinkerPatch SDK
            TinkerPatch.init(
                    tinkerApplicationLike
//                new TinkerPatch.Builder(tinkerApplicationLike)
//                    .requestLoader(new OkHttp3Loader())
//                    .build()
            )
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true)
                    .setFetchPatchIntervalByHours(3)
            ;
            // 获取当前的补丁版本
            Log.d(TAG, "Current patch version is " + TinkerPatch.with().getPatchVersion());

            // fetchPatchUpdateAndPollWithInterval 与 fetchPatchUpdate(false)
            // 不同的是，会通过handler的方式去轮询
            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
        }
    }
    private void initLinkedME() {
        // 初始化SDK
        LinkedME.getInstance(this);

        if (BuildConfig.DEBUG) {
            //设置debug模式下打印LinkedME日志
            LinkedME.getInstance().setDebug();
        }
        //初始时请设置为false
        LinkedME.getInstance().setImmediate(false);
        //设置处理跳转逻辑的中转页，MiddleActivity详见后续配置
        LinkedME.getInstance().setHandleActivity(MiddleActivity.class.getName());
    }

    public void replaceSystemDefaultFont(Context context, String fontPath) {
        //這里我们修改的是MoNOSPACE,是因为我们在主题里给app设置的默认字体就是monospace，设置其他的也可以
        replaceTypefaceField("MONOSPACE", createTypeface(context, fontPath));
    }

    //关键--》通过修改MONOSPACE字体为自定义的字体达到修改app默认字体的目的
    private void replaceTypefaceField(String fieldName, Object value) {
        try {
            Field defaultField = Typeface.class.getDeclaredField(fieldName);
            defaultField.setAccessible(true);
            defaultField.set(null, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //通过字体地址创建自定义字体
    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }

    ///===========设置字体不跟随系统字体大小改变
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
    ///===========设置字体不跟随系统字体大小改变


    public static BaseApplication getApplication() {
        return mInstance;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线�?
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    // 初始化ImageLoader
    private void initImageLoaderConfig() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
                getApplicationContext());
        config.threadPriority(Thread.MIN_PRIORITY + 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.discCacheFileNameGenerator(new Md5FileNameGenerator());
        config.discCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.threadPoolSize(5);
        config.writeDebugLogs();
        config.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024);
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    public static DisplayImageOptions options(int drawable) {

        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        if (drawable > 0) {
            builder.showImageOnFail(drawable);
            builder.showImageOnLoading(drawable);
            builder.showImageForEmptyUri(drawable);
        }
        builder.cacheInMemory(true);
        builder.cacheOnDisc(true);
        builder.imageScaleType(ImageScaleType.EXACTLY);
        builder.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true);

        return builder.build();
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wx8765e31488491eb2", "d84daba151d38166a67e0c218d8224bf");
        PlatformConfig.setQQZone("1105961986", "28YeU7L5l0yhNuzH");
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    public static Context getContext() {
        return mContext;
    }

    public static ModelUser getUser() {
        return user;
    }

    public static void setUser(ModelUser user) {
        BaseApplication.user = user;
    }
}
