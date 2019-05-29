package com.huacheng.huiservers.ui.index.request;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelPhoto;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.geren.bean.GroupMemberBean;
import com.huacheng.huiservers.ui.index.workorder.adapter.SelectImgAdapter;
import com.huacheng.huiservers.ui.index.workorder.commit.HouseListActivity;
import com.huacheng.huiservers.utils.ucrop.ImgCropUtil;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.utils.FileUtils;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Description: 投诉建议请求
 * created by wangxiaotao
 * 2019/5/27 0027 上午 11:41
 */
public class CommitRequestActivity extends BaseActivity {

    private EditText et_content;
    protected GridView gridview_imgs;
    protected ArrayList<ModelPhoto> photoList = new ArrayList();//图片集合
    private Map<String, File> images_submit = new HashMap<>();
    SelectImgAdapter gridviewImgsAdapter;
    private RxPermissions rxPermission;
    public static final int ACT_SELECT_PHOTO = 111;//选择图片
    // 请求加载系统照相机
    private static final int REQUEST_CAMERA = 100;
    private File mTmpFile;
    private TextView tv_commit;
    private LinearLayout ll_address;
    private TextView tv_address;
    public static final int ACT_SELECT_HOUSE = 333;//选择房屋

    private String address=""; //地址
    private String community_id=""; //小区id
    private String community_cn=""; //小区名称
    private String company_id=""; //公司id
    private String room_id=""; //房间id
    private TextView tv_nickname;

    @Override
    protected void initView() {
        rxPermission = new RxPermissions(this);
        findTitleViews();
        titleName.setText("投诉建议");
        tv_right=findViewById(R.id.right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("历史记录");
        et_content = findViewById(R.id.et_content);
        gridview_imgs = findViewById(R.id.gridview_imgs);
        gridviewImgsAdapter = new SelectImgAdapter(this, photoList);
        gridview_imgs.setAdapter(gridviewImgsAdapter);
        tv_commit = findViewById(R.id.tv_commit);

        TextView tv_nickname = findViewById(R.id.tv_nickname);
        TextView tv_phone = findViewById(R.id.tv_phone);
        ll_address = findViewById(R.id.ll_address);
        tv_address = findViewById(R.id.tv_address);
        if (BaseApplication.getUser()!=null){
            tv_nickname.setText(""+BaseApplication.getUser().getNickname());
            tv_phone.setText(""+BaseApplication.getUser().getUsername());
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        gridviewImgsAdapter.setListener(new SelectImgAdapter.OnClickItemIconListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClickAdd(final int position) {
                //跳转到图片选择页
                rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean isGranted) throws Exception {
                                if (isGranted) {
                                    jumpToCamera(position);
                                } else {
                                    SmartToast.showInfo("未打开摄像头权限");
                                }
                            }
                        });

            }

            @Override
            public void onClickDelete(int position) {
                // 访问删除接口
                photoList.remove(position);
                gridviewImgsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onClickImage(int position) {
                //点击图片
                ArrayList<String> imgs = new ArrayList<>();
                for (int i = 0; i < photoList.size(); i++) {
                    //只要localpath不为空则说明是刚选上的
                    if (!NullUtil.isStringEmpty(photoList.get(i).getLocal_path())) {
                        imgs.add(photoList.get(i).getLocal_path());
                    }
                }
                Intent intent = new Intent(CommitRequestActivity.this, PhotoViewPagerAcitivity.class);
                intent.putExtra("img_list", imgs);
                intent.putExtra("position", position);
                intent.putExtra("isShowDelete", true);
                startActivity(intent);

            }
        });
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommitRequestActivity.this, RequestListActivity.class);
                startActivity(intent);

            }
        });
        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转地址
                Intent intent_house = new Intent(CommitRequestActivity.this, HouseListActivity.class);
                startActivityForResult(intent_house,ACT_SELECT_HOUSE);
            }
        });
    }

    /**
     * 提交
     */
    private void commit() {


        if (NullUtil.isStringEmpty(address)){
            SmartToast.showInfo("请选择地址");
            return;
        }
        String content = et_content.getText().toString().trim();
        if (NullUtil.isStringEmpty(content)){
            SmartToast.showInfo("请输入内容");
            return;
        }
        if (photoList.size()==0){
            SmartToast.showInfo("请上传图片");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        if (BaseApplication.getUser()!=null){
            params.put("nickname",BaseApplication.getUser().getNickname());
            params.put("phone",BaseApplication.getUser().getUsername());
        }
        params.put("address",address);
        params.put("content",content);

        // 提交
        if (photoList.size() > 0) {
            // 压缩图片
            showDialog(smallDialog);
            smallDialog.setTipTextView("压缩中...");
            zipPhoto(params);
        }

    }
    private void zipPhoto(final HashMap<String, String> params) {

        ArrayList<File> files = new ArrayList<>();
        for (int i1 = 0; i1 < photoList.size(); i1++) {
            File file = new File(photoList.get(i1).getLocal_path());
            files.add(file);
        }
        Luban.with(this)
                .load(files)
                .ignoreBy(100)
                .setTargetDir(getPath())
                .setFocusAlpha(false)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    int count = 0;

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        //这个方法重复调用
                        images_submit.put("img" + count, file);
                        if (images_submit.size() == photoList.size()) {
                            //完成了
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    smallDialog.setTipTextView("提交中...");
                                    commitIndeed(params, images_submit);
                                }
                            });

                        } else {
                            count++;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SmartToast.showInfo("图片压缩失败");
                                hideDialog(smallDialog);
                            }
                        });

                    }
                }).launch();
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/huiworkers/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }
    private void commitIndeed(HashMap<String, String> params, Map<String, File> params_file) {
        if (params_file != null && params_file.size() > 0) {
            params.put("img_num", params_file.size() + "");
        }
        String url = ApiHttpClient.FEED_BACK_ADD;

        MyOkHttp.get().upload(this, url, params, params_file, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交成功");
                    SmartToast.showInfo(msg);
                    startActivity(new Intent(CommitRequestActivity.this, RequestListActivity.class));
                    finish();

                    //删除缓存文件夹中的图片
                    ImgCropUtil.deleteCacheFile(new File(ImgCropUtil.getCacheDir()));
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }

            @Override
            public void onProgress(long currentBytes, long totalBytes) {
                super.onProgress(currentBytes, totalBytes);
            }
        });
    }
    /**
     * 跳转到照相机
     * @param position
     */
    private void jumpToCamera(int position) {
        Intent fullIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(fullIntent.resolveActivity(getPackageManager()) != null){
            try {
                mTmpFile = FileUtils.createTmpFile(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 获取uri
            if(mTmpFile != null && mTmpFile.exists()) {
                Uri uri = null;
                if (Build.VERSION.SDK_INT >= 24) {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, mTmpFile.getAbsolutePath());
                    uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
                } else {
                    uri = Uri.fromFile(mTmpFile);
                }
                // 跳转
                fullIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                fullIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(fullIntent, REQUEST_CAMERA);
            }else{
                SmartToast.showInfo("图片错误");
            }
        }else {
            SmartToast.showInfo("没有系统相机");
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commit_request;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case REQUEST_CAMERA:
                String back_path = mTmpFile.getAbsolutePath();
                ModelPhoto modelPhoto = new ModelPhoto();
                modelPhoto.setLocal_path(back_path);
                photoList.add(modelPhoto);
                // 将新图上传
                if (gridviewImgsAdapter != null) {
                    gridviewImgsAdapter.notifyDataSetChanged();
                }
                break;
            case ACT_SELECT_HOUSE:
                if (data != null) {
                    GroupMemberBean item = (GroupMemberBean) data.getSerializableExtra("community");
                    community_id=item.getCommunity_id();
                    community_cn=item.getCommunity_name();
                    address=item.getCommunity_address();
                    room_id=item.getRoom_id();
                    tv_address.setText(item.getCommunity_address());
                }
                break;
        }
    }
}
