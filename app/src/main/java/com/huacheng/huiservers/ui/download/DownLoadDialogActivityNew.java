package com.huacheng.huiservers.ui.download;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.DownloadResponseHandler;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Description: 新的下载对话框,使用okhttp
 * created by wangxiaotao
 * 2020/6/24 0024 16:05
 */
public class DownLoadDialogActivityNew extends Activity {


    public static  String DOWNLOAD_FOLDER_NAME = "hui_download";
    public static  String DOWNLOAD_FILE_NAME = "hui.apk";

    public static  String APK_URL = "http://m.hui-shenghuo.cn/data/upload/apk/HuiServers.apk";

    private ProgressBar downloadProgress;
    private TextView downloadTip;
    private TextView downloadSize;
    private TextView downloadSizeTotal;
    private TextView downloadPrecent;


    private long totalBytes = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog_download_new);


        Intent intent = getIntent();
        APK_URL = intent.getStringExtra("download_src");
        DOWNLOAD_FILE_NAME = intent.getStringExtra("file_name");
        DOWNLOAD_FOLDER_NAME=  Environment.getExternalStorageDirectory() + "/hui_download";
        File folder = new File(DOWNLOAD_FOLDER_NAME);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
        initView();
        initData();

    }

    private void initView() {
        downloadProgress = (ProgressBar) findViewById(R.id.download_progress);
        downloadTip = (TextView) findViewById(R.id.download_tip);
        downloadTip.setText("正在下载，请稍后...");
        downloadSize = (TextView) findViewById(R.id.download_size);
        downloadSizeTotal = (TextView) findViewById(R.id.download_size_total);
        downloadPrecent = (TextView) findViewById(R.id.download_precent);
    }



    private void initData() {
//        /**
//         * get download id from preferences.<br/>
//         * if download id bigger than 0, means it has been downloaded, then
//         * query status and show right text;
//         */
//        //     downloadId = PreferencesUtils.getLong(context, KEY_NAME_DOWNLOAD_ID);
//        updateView();

        MyOkHttp.get().download(this, APK_URL, DOWNLOAD_FOLDER_NAME, DOWNLOAD_FILE_NAME, new DownloadResponseHandler() {
            @Override
            public void onFinish(File download_file) {
                downloadProgress.setMax(100);
                downloadProgress.setProgress(100);
                downloadPrecent.setText("100%");
                downloadSize.setText(getAppSize(totalBytes) + "/"
                        );
                downloadSizeTotal.setText( getAppSize(totalBytes));
//                String apkFilePath = new StringBuilder(Environment
//                        .getExternalStorageDirectory().getAbsolutePath())
//                        .append(File.separator)
//                        .append(DOWNLOAD_FOLDER_NAME)
//                        .append(File.separator).append(DOWNLOAD_FILE_NAME)
//                        .toString();
                install(DownLoadDialogActivityNew.this, download_file.getPath());
                DownLoadDialogActivityNew.this.setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onProgress(final long currentBytes, final long totalBytes) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        downloadProgress.setVisibility(View.VISIBLE);

                        downloadSize.setVisibility(View.VISIBLE);
                        downloadPrecent.setVisibility(View.VISIBLE);

                        if (totalBytes == 0) {
                            downloadProgress.setIndeterminate(true);
                            downloadPrecent.setText("0%");
                            downloadSize.setText("0M/0M");
                            downloadProgress.setMax(0);
                            downloadProgress.setProgress(0);
                        } else {
                            int progress = (int) (currentBytes * 1.0f / totalBytes * 100);
                            downloadProgress.setIndeterminate(false);
                            downloadProgress.setMax(100);
                            downloadProgress.setProgress(progress);
                            downloadPrecent.setText(getNotiPercent(currentBytes,
                                    totalBytes));
                            downloadSize.setText(getAppSize(currentBytes) + "/");
                            downloadSizeTotal.setText( getAppSize(totalBytes));
                            DownLoadDialogActivityNew.this.totalBytes=totalBytes;
                        }
                    }
                });

            }

            @Override
            public void onFailure(String error_msg) {
                SmartToast.showInfo("下载失败");
                finish();
            }
        });

    }

    /**
     * install app
     *
     * @param context
     * @param filePath
     * @return whether apk exist
     */
    public static boolean install(Context context, String filePath) {
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        File file = new File(filePath);
//        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
//            i.setDataAndType(Uri.parse("file://" + filePath),
//                    "application/vnd.android.package-archive");
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
//            // 有新版本就把补丁删掉
////            if (BuildConfig.TINKER_ENABLE){
////                TinkerPatch.with().cleanAll();
////            }
//            return true;
//        }
//        return false;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        File file = new File(filePath);
        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //版本大于等于7.0
                //在清单文件中配置的authorities
                data = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
                // 给目标应用一个临时授权
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            } else {
                data = Uri.fromFile(file);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
            intent.setDataAndType(data, "application/vnd.android.package-archive");
            context.startActivity(intent);
            return true;
        }
        return false;



    }


    static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");

    public static final int MB_2_BYTE = 1024 * 1024;
    public static final int KB_2_BYTE = 1024;

    /**
     * @param size
     * @return
     */
    public static CharSequence getAppSize(long size) {
        if (size <= 0) {
            return "0M";
        }

        if (size >= MB_2_BYTE) {
            return new StringBuilder(16).append(
                    DOUBLE_DECIMAL_FORMAT.format((double) size / MB_2_BYTE))
                    .append("M");
        } else if (size >= KB_2_BYTE) {
            return new StringBuilder(16).append(
                    DOUBLE_DECIMAL_FORMAT.format((double) size / KB_2_BYTE))
                    .append("K");
        } else {
            return size + "B";
        }
    }

    public static String getNotiPercent(long progress, long max) {
        int rate = 0;
        if (progress <= 0 || max <= 0) {
            rate = 0;
        } else if (progress > max) {
            rate = 100;
        } else {
            rate = (int) ((double) progress / max * 100);
        }
        return new StringBuilder(16).append(rate).append("%").toString();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            SmartToast.showInfo("正在下载，请耐心等待");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

