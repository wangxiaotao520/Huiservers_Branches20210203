package com.huacheng.huiservers.ui.index.workorder_second;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.example.xlhratingbar_lib.XLHRatingBar;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.EventBusWorkOrderModel;
import com.huacheng.huiservers.model.ModelPhoto;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.workorder.adapter.SelectImgAdapter;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.utils.ucrop.ImgCropUtil;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 类描述：
 * 时间：2019/4/9 19:36
 * created by DFF
 */
public class WorkOrderPingjiaActivity extends BaseActivity {
    public static final int ACT_SELECT_PHOTO = 111;//选择图片
    RxPermissions rxPermission;
    GridView gridview_imgs;
    private Map<String, File> images_submit = new HashMap<>();
    SelectImgAdapter gridviewImgsAdapter;
    ArrayList<ModelPhoto> photoList = new ArrayList();//图片集合
    EditText et_content;
    TextView tv_commit, tv_score_content;
    String work_id = "";
    LinearLayout lin_left;
    XLHRatingBar ratingBar;

    @Override
    protected void initView() {
        findTitleViews();
        rxPermission = new RxPermissions(this);
        titleName.setText("评价工单");
        gridview_imgs = findViewById(R.id.gridview_imgs);
        lin_left = findViewById(R.id.lin_left);
        ratingBar = findViewById(R.id.ratingBar);
        et_content = findViewById(R.id.et_content);
        tv_score_content = findViewById(R.id.tv_score_content);
        tv_commit = findViewById(R.id.tv_commit);
        gridviewImgsAdapter = new SelectImgAdapter(this, photoList);
        gridviewImgsAdapter.setShowDelete(true);//不显示删除
        gridview_imgs.setAdapter(gridviewImgsAdapter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initListener() {
        et_content.addTextChangedListener(mTextWatcher);
        ratingBar.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(int countSelected) {
                if (countSelected == 1) {
                    tv_score_content.setText("比较差");
                } else if (countSelected == 2) {
                    tv_score_content.setText("一般");
                } else if (countSelected == 3) {
                    tv_score_content.setText("满意");
                } else if (countSelected == 4) {
                    tv_score_content.setText("满意");
                } else if (countSelected == 5) {
                    tv_score_content.setText("非常满意");
                }

            }
        });
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
                                    jumpToImageSelector(position);
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
                Intent intent = new Intent(WorkOrderPingjiaActivity.this, PhotoViewPagerAcitivity.class);
                intent.putExtra("img_list", imgs);
                intent.putExtra("position", position);
                intent.putExtra("isShowDelete", true);
                startActivity(intent);

            }
        });
        et_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //通知父控件不要干扰
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    //通知父控件不要干扰
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
        lin_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToolUtils(et_content,WorkOrderPingjiaActivity.this).closeInputMethod();
                finish();
            }
        });
    }

    /**
     * 提交评价
     */
    private void commit() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("work_id", work_id);
        params.put("level", ratingBar.getCountSelected() + "");
        params.put("content", et_content.getText().toString());
        if (photoList.size() > 0) {
            // 压缩图片
            showDialog(smallDialog);
            smallDialog.setTipTextView("压缩中...");
            zipPhoto(params);
        } else {
            showDialog(smallDialog);
            smallDialog.setTipTextView("提交中...");
            commitIndeed(params, null);
        }
    }

    private void commitIndeed(HashMap<String, String> params, Map<String, File> params_file) {
        if (params_file != null && params_file.size() > 0) {
            params.put("img_num", params_file.size() + "");
        }
        MyOkHttp.get().upload(this, ApiHttpClient.GET_WORK_SCORE, params, params_file, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交成功");
                    SmartToast.showInfo(msg);
                    EventBusWorkOrderModel model = new EventBusWorkOrderModel();
                    model.setEvent_back_type(1);
                    model.setWork_id(work_id);
                    EventBus.getDefault().post(model);
                    //删除缓存文件夹中的图片
                    ImgCropUtil.deleteCacheFile(new File(ImgCropUtil.getCacheDir()));
                    setResult(RESULT_OK);
                    finish();
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

    //压缩图片
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
        String path = Environment.getExternalStorageDirectory() + "/huiservers/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    /**
     * 跳转到图片选择页
     *
     * @param position
     */
    private void jumpToImageSelector(int position) {
        Intent imageIntent = new Intent(this, MultiImageSelectorActivity.class);
        // 是否显示相机
        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);

        // 单选多选 (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_SINGLE)
        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // 默认选择

        ArrayList<String> list_jump = new ArrayList<String>();
        for (int i = 0; i < photoList.size(); i++) {
            //只要localpath不为空则说明是刚选上的
            if (!NullUtil.isStringEmpty(photoList.get(i).getLocal_path())) {
                list_jump.add(photoList.get(i).getLocal_path());
            }
        }
        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 4);

        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, list_jump);
        startActivityForResult(imageIntent, ACT_SELECT_PHOTO);
    }

    /**
     * 删除图片
     *
     * @param photo
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeletePhoto(ModelPhoto photo) {
        if (photo != null) {
            int position = photo.getPosition();
            photoList.remove(position);
            gridviewImgsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_order_pingjia;
    }

    @Override
    protected void initIntentData() {

        work_id = this.getIntent().getStringExtra("work_id");
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ACT_SELECT_PHOTO:
                    if (data != null) {
                        ArrayList<String> backList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                        // 删除成功后判断哪些是新图，
                        photoList.clear();

                        for (int i = 0; i < backList.size(); i++) {
                            String back_path = backList.get(i);
                            ModelPhoto modelPhoto = new ModelPhoto();
                            modelPhoto.setLocal_path(back_path);
                            photoList.add(modelPhoto);
                        }
                        // 将新图上传
                        if (gridviewImgsAdapter != null) {
                            gridviewImgsAdapter.notifyDataSetChanged();
                        }

                    }
                    break;

                default:
                    break;
            }
        }
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2,
                                  int arg3) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editStart = et_content.getSelectionStart();
            editEnd = et_content.getSelectionEnd();
            if (temp.length() > 100) {
                s.delete(editStart - 1, editEnd);
                et_content.setText(s);
                //mTvNum.setText(s.length() + "/150");
                et_content.setSelection(s.length());
                SmartToast.showInfo("超出字数范围");
            }
        }
    };

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
