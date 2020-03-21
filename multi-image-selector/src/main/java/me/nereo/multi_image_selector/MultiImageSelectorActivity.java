package me.nereo.multi_image_selector;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.statusbar.ScreenManager;
import me.nereo.multi_image_selector.statusbar.StatusBarUtil;

/**
 * 多图选择
 * Created by Nereo on 2015/4/7.
 * Updated by nereo on 2016/1/19.
 */
public class MultiImageSelectorActivity extends AppCompatActivity implements MultiImageSelectorFragment.Callback{

    /** 最大图片选择次数，int类型，默认9 */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /** 图片选择模式，默认多选 */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /** 是否显示相机，默认显示 */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /** 是否显示视频，默认不显示 */
    public static final String EXTRA_SHOW_VIDEO = "show_video";
    public static final String EXTRA_SELECT_ORIGIANL = "select_original";
    /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合  */
    public static final String EXTRA_RESULT = "select_result";
    /** 默认选择集 */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    /** 单选 */
    public static final int MODE_SINGLE = 0;
    /** 多选 */
    public static final int MODE_MULTI = 1;

    private ImagePagerFragment selectedPagerFragment;
    private ImagePagerFragment imagePagerFragment;

    private ArrayList<String> resultList = new ArrayList<>();
    private Button mSubmitButton;
    private int mDefaultCount;
    private boolean mIsOriginal;
    /**选择本地视频*/
    public static final String EXTRA_NATIVE_VIDEO_PATH="native_video_path";
    /**选择本地视频的长度*/
    public static final String EXTRA_NATIVE_VIDEO_DURITION="native_video_durition";
    /**选择本地视频的result_code*/
    public static final int NATIVE_VIDEO_RESULTCODE= 1028;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        this.getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
 //       this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ScreenManager  screenManager = ScreenManager.getInstance();
        screenManager.setFullScreen(false, this);
        screenManager.setStatusBar(false, this);
        screenManager.setScreenRoate(false, this);

        //修改状态栏的颜色为橙色
        StatusBarUtil.setStatusBarColor(this,getResources().getColor(R.color.orange));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
        int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        boolean isShow2 = intent.getBooleanExtra(EXTRA_SHOW_VIDEO, false);
        mIsOriginal = intent.getBooleanExtra(EXTRA_SELECT_ORIGIANL, false);
        if(mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_VIDEO, isShow2);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SELECT_ORIGINAL, mIsOriginal);
        bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();

        // 返回按钮
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // 完成按钮
        mSubmitButton = (Button) findViewById(R.id.commit);
        if(resultList == null || resultList.size()<=0){
            mSubmitButton.setText(R.string.action_done);
            mSubmitButton.setEnabled(false);
        }else{
            updateDoneText();
            mSubmitButton.setEnabled(true);
        }
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resultList != null && resultList.size() >0){
                    // 返回已选择的图片数据
                    Intent data = new Intent();
                    data.putStringArrayListExtra(EXTRA_RESULT, resultList);
                    data.putExtra(EXTRA_SELECT_ORIGIANL, mIsOriginal);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    private void updateDoneText(){
        mSubmitButton.setText(String.format("%s(%d/%d)",
                getString(R.string.action_done), resultList.size(), mDefaultCount));
    }

    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        data.putExtra(EXTRA_SELECT_ORIGIANL, mIsOriginal);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(String path) {
        if(!resultList.contains(path)) {
            resultList.add(path);
        }
        // 有图片之后，改变按钮状态
        if(resultList.size() > 0){
            updateDoneText();
            if(!mSubmitButton.isEnabled()){
                mSubmitButton.setEnabled(true);
            }
        }
    }

    @Override
    public void onImageUnselected(String path) {
        if(resultList.contains(path)){
            resultList.remove(path);
        }
        updateDoneText();
        // 当为选择图片时候的状态
        if(resultList.size() == 0){
            mSubmitButton.setText(R.string.action_done);
            mSubmitButton.setEnabled(false);
        }
    }

    @Override
        public void onCameraShot(File imageFile) {
        if(imageFile != null) {
            // notify system
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void onOriginalSelected(boolean selected) {
        this.mIsOriginal = selected;
    }

    @Override
    public void onPreviewImage() {
        //预览图像
//        DisplayMetrics metric = new DisplayMetrics();
//        metric = getResources().getDisplayMetrics();
//        int width = metric.widthPixels;
//        int height = metric.heightPixels;
//        int[] screenLocation = new int[2];
//        //选取屏幕中心点
//        screenLocation[0] = width / 2;
//        screenLocation[1] = height / 2;
//
//        ImagePagerFragment imagePagerFragment =
//                ImagePagerFragment.newInstance(resultList, 0, screenLocation, 100
//                        , 100);
//        addImagePagerFragment(imagePagerFragment, true);
    }

    @Override
    public void onPreviewSelected(List<String> paths) {
        resultList.clear();
        resultList.addAll(paths);
        updateDoneText();
        // 当为选择图片时候的状态
        if(resultList.size() == 0){
            mSubmitButton.setText(R.string.action_done);
            mSubmitButton.setEnabled(false);
        }
    }

    public void addImagePagerFragment(ImagePagerFragment imagePagerFragment, boolean isSelected) {
        if(isSelected){
            this.selectedPagerFragment = imagePagerFragment;
            if (this.imagePagerFragment != null && this.imagePagerFragment.isVisible()) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                }
            }
        }else{
            this.imagePagerFragment = imagePagerFragment;
            if (this.selectedPagerFragment != null && this.selectedPagerFragment.isVisible()) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                }
            }

        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.image_grid, imagePagerFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (imagePagerFragment != null && imagePagerFragment.isVisible()) {
            imagePagerFragment.runExitAnimation(new Runnable() {
                public void run() {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();
                    }
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case MultiImageSelectorFragment.REQUEST_CODE_IMPORT_VIDEO:
                    // 视频导入
                    Log.d("path", "onActivityResult: ");
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            String columnName = MediaStore.Video.Media.DATA;
                            if (!TextUtils.isEmpty(columnName)) {
//                                Cursor cursor = getContentResolver().query(uri, new String[]{columnName}, null, null, null);
                                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                                if (cursor != null) {
                                    String path = ""; //文件路径
                                    int durition=0;  //文件时长
                                    //added by wangxiaotao  2016/11/8
                                    long size = 0 ; //文件大小
                                    while (cursor.moveToNext()) {
                                        //   path = cursor.getString(0);
                                        try {
                                            path=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                                            durition = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                                        }catch (IllegalArgumentException e){
                                            e.printStackTrace();
                                        }

                                        size=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                                        Log.d("size","[size]:"+size);
                                        String[] columnNames = cursor.getColumnNames();
                                        for (String name : columnNames) {
                                            Log.d("name", "[columnName]:"+name);
                                            Log.d("name","[value]:"+cursor.getString(cursor.getColumnIndex(name)));
                                        }
                                    }
                                    cursor.close();
                                    if (!TextUtils.isEmpty(path)){
                                        //这里先不要进行改动
                                        if(path.endsWith(".mp4")&&durition<=50*1000&&size<=1024*1024*40){//小于40M
                                            // 将数据返回回去
                                            sendNativeVideoback(path,durition);
                                        }else{
                                            if(path.endsWith(".mp4")&&durition>50*1000){
                                                Toast.makeText(this, "视频过长，请选择小于50s的视频", Toast.LENGTH_SHORT).show();
                                            }else if(path.endsWith(".mp4")&&size>1024*1024*40){
                                                Toast.makeText(this, "视频过大，请选择小于40M的视频", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(this, "视频格式错误，请选择MP4格式视频", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 返回本地视频数据
     * @param path
     * @param durition
     */
    private void sendNativeVideoback(String path, int durition) {
        Intent data = new Intent();
        data.putExtra(EXTRA_NATIVE_VIDEO_PATH,path);
        data.putExtra(EXTRA_NATIVE_VIDEO_DURITION,durition);
        setResult(NATIVE_VIDEO_RESULTCODE,data);
        finish();
    }
}
