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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommonChooseDialog;
import com.huacheng.huiservers.model.ModelPhoto;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.geren.bean.GroupMemberBean;
import com.huacheng.huiservers.ui.index.property.SelectCommunityActivity;
import com.huacheng.huiservers.ui.index.workorder.adapter.SelectImgAdapter;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Description: 公共提交页面
 * created by wangxiaotao
 * 2019/4/8 0008 下午 3:44
 */
public class PublicWorkOrderCommitActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener {
    private RxPermissions rxPermission;
    protected RelativeLayout rel_select_tag;
    protected TextView tv_select_tag;
    protected RelativeLayout rel_emergency;
    protected TextView tv_select_emergency;
    protected RelativeLayout rel_community;
    protected TextView tv_select_community;
    protected EditText et_beizhu;
    protected GridView gridview_imgs;
    SelectImgAdapter gridviewImgsAdapter;
    protected ArrayList<ModelPhoto> photoList = new ArrayList();//图片集合
    private Map<String, File> images_submit = new HashMap<>();
    String[] emergencyLists = {"紧急", "普通"};
    private TextView tv_confirm;
    public static final int ACT_SELECT_PHOTO = 111;//选择图片
    public static final int ACT_REQUEST_COMMUNITY = 112;//选择小区
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        smallDialog.setCanceledOnTouchOutside(false);
        rxPermission = new RxPermissions(this);
        findTitleViews();
        titleName.setText("公共报修");
        tv_right = findViewById(R.id.txt_right1);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("工单中心");
        rel_select_tag = findViewById(R.id.rel_select_tag);
        tv_select_tag = findViewById(R.id.tv_select_tag);
        rel_emergency = findViewById(R.id.rel_emergency);
        tv_select_emergency = findViewById(R.id.tv_select_emergency);
        rel_community = findViewById(R.id.rel_community);
        tv_select_community = findViewById(R.id.tv_select_community);
        et_beizhu = findViewById(R.id.et_beizhu);
        et_beizhu.setOnTouchListener(this);
        gridview_imgs = findViewById(R.id.gridview_imgs);
        gridviewImgsAdapter = new SelectImgAdapter(this, photoList);
        gridview_imgs.setAdapter(gridviewImgsAdapter);
        tv_confirm = findViewById(R.id.tv_confirm);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_right.setOnClickListener(this);
        rel_select_tag.setOnClickListener(this);
        rel_emergency.setOnClickListener(this);
        rel_community.setOnClickListener(this);
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
                Intent intent = new Intent(PublicWorkOrderCommitActivity.this, PhotoViewPagerAcitivity.class);
                intent.putExtra("img_list",imgs);
                intent.putExtra("position",position);
                intent.putExtra("isShowDelete",true);
                startActivity(intent);

            }
        });
        tv_confirm.setOnClickListener(this);
    }
    /**
     * 跳转到图片选择页
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
     * @param photo
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeletePhoto(ModelPhoto photo){
        if (photo!=null){
            int position = photo.getPosition();
            photoList.remove(position);
            gridviewImgsAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_public_workorder_commit;
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_right:
                //TODO 工单中心
                break;
            case R.id.rel_select_tag:
                //TODO 选择报修类型
                break;
            case R.id.rel_community:
                //TODO 选择小区
                Intent intent = new Intent(this, SelectCommunityActivity.class);
                startActivityForResult(intent, ACT_REQUEST_COMMUNITY);
                break;

            case R.id.rel_emergency:
                new CommonChooseDialog(this, emergencyLists, new CommonChooseDialog.OnClickItemListener() {
                    @Override
                    public void onClickItem(int position) {
                        tv_select_emergency.setText(emergencyLists[position]);
                        //TODO 获取紧急提交字段
                    }
                }).show();
                break;
            case R.id.tv_confirm:
                commit();
                break;
            default:
                break;
        }
    }

    /**
     * 提交
     */
    private void commit() {
        //todo 提交
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.et_beizhu && canVerticalScroll(et_beizhu))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);//告诉父view，我的事件自己处理
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);//告诉父view，你可以处理了
            }
        }
        return false;
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动   false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
                case  ACT_REQUEST_COMMUNITY:
                    GroupMemberBean groupMemberBean = (GroupMemberBean) data.getSerializableExtra("community");
                    //TODO 获取小区id
                    groupMemberBean.getId();
                    tv_select_community.setText(groupMemberBean.getName());
                    break;
                default:
                    break;
            }
        }
    }
}
