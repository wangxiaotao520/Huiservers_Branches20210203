package com.huacheng.huiservers.ui.index.workorder_second;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelPhoto;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.workorder.adapter.SelectImgAdapter;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 类描述：
 * 时间：2019/4/9 19:36
 * created by DFF
 */
public class WorkOrderPingjiaActivity extends BaseActivity {
    public static final int ACT_SELECT_PHOTO = 111;//选择图片
    RxPermissions rxPermission;
    GridView gridview_imgs;
    SelectImgAdapter gridviewImgsAdapter;
    ArrayList<ModelPhoto> photoList = new ArrayList();//图片集合
    EditText et_content;

    @Override
    protected void initView() {
        findTitleViews();
        rxPermission = new RxPermissions(this);
        titleName.setText("评价工单");
        gridview_imgs = findViewById(R.id.gridview_imgs);
        et_content = findViewById(R.id.et_content);
        gridviewImgsAdapter = new SelectImgAdapter(this, photoList);
        gridviewImgsAdapter.setShowDelete(true);//不显示删除
        gridview_imgs.setAdapter(gridviewImgsAdapter);
    }

    @Override
    protected void initData() {

    }

    @SuppressLint("ClickableViewAccessibility")
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
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    //通知父控件不要干扰
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_MOVE){
                    //通知父控件不要干扰
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
