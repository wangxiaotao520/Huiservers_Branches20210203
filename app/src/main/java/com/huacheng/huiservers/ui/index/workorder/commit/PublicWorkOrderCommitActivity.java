package com.huacheng.huiservers.ui.index.workorder.commit;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommonChooseDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelNewWorkOrder;
import com.huacheng.huiservers.model.ModelPhoto;
import com.huacheng.huiservers.model.ModelWorkPersonalCatItem;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.geren.bean.GroupMemberBean;
import com.huacheng.huiservers.ui.index.property.SelectCommunityActivity;
import com.huacheng.huiservers.ui.index.workorder.JpushWorkPresenter;
import com.huacheng.huiservers.ui.index.workorder.WorkOrderListActivity;
import com.huacheng.huiservers.ui.index.workorder.adapter.SelectImgAdapter;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
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
 * Description: 公共提交页面
 * created by wangxiaotao
 * 2019/4/8 0008 下午 3:44
 */
public class PublicWorkOrderCommitActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener {
    private com.huacheng.huiservers.utils.SharePrefrenceUtil sharePrefrenceUtil;
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
    public static final int ACT_SELECT_TYPE = 222;//选择分类
    private TextView tv_text_limit;
    //提交
    private String cate_pid="";//报修类型一级id
    private String cate_pid_cn=""; //报修类型一级名称
    private String degree=""; //紧急
    private String nickname=""; //昵称
    private String username=""; //联系方式
    private String community_id=""; //小区id
    private String community_cn=""; //小区名称
    private String company_id=""; //公司id



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        sharePrefrenceUtil = new SharePrefrenceUtil(this);
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
        tv_text_limit = findViewById(R.id.tv_text_limit);
        gridview_imgs = findViewById(R.id.gridview_imgs);
        gridviewImgsAdapter = new SelectImgAdapter(this, photoList);
        gridview_imgs.setAdapter(gridviewImgsAdapter);
        TextView tv_nickname = findViewById(R.id.tv_nickname);
        TextView tv_phone = findViewById(R.id.tv_phone);
        if (BaseApplication.getUser()!=null){
            tv_nickname.setText(""+BaseApplication.getUser().getNickname());
            tv_phone.setText(""+BaseApplication.getUser().getUsername());
            nickname=BaseApplication.getUser().getNickname();
            username=BaseApplication.getUser().getUsername();
        }
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
        et_beizhu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s!=null){
                    if (s.length()>=100){
                        tv_text_limit.setVisibility(View.VISIBLE);
                    }else {
                        tv_text_limit.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
            case R.id.txt_right1:
                // 工单中心
                startActivity(new Intent(PublicWorkOrderCommitActivity.this, WorkOrderListActivity.class));
                break;
            case R.id.rel_select_tag:
                // 选择报修类型
                Intent intent = new Intent(this, WorkTypeListActivity.class);
                intent.putExtra("type","2");
                startActivityForResult(intent,ACT_SELECT_TYPE);
                break;
            case R.id.rel_community:
                // 选择小区
                Intent intent1 = new Intent(this, SelectCommunityActivity.class);
                startActivityForResult(intent1, ACT_REQUEST_COMMUNITY);
                break;

            case R.id.rel_emergency:
                new CommonChooseDialog(this, emergencyLists, new CommonChooseDialog.OnClickItemListener() {
                    @Override
                    public void onClickItem(int position) {
                        tv_select_emergency.setText(emergencyLists[position]);
                        // 获取紧急提交字段
                        if (position==1){
                            degree="0" ;
                        }else {
                            degree="1" ;
                        }
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
        // 提交
        if (checkReady()){
            HashMap<String, String> params = new HashMap<>();
            params.put("work_type",  "2");
            params.put("community_id", community_id);
            params.put("community_cn", community_cn);
            String content = et_beizhu.getText().toString().trim();
            if (!NullUtil.isStringEmpty(content)){
                params.put("content", content);
            }
            params.put("cate_pid", cate_pid);
            params.put("cate_pid_cn", cate_pid_cn);
            params.put("degree", degree);
            this.company_id=sharePrefrenceUtil.getCommanyId();
            params.put("company_id", company_id);
            params.put("username", username);
            params.put("nickname", nickname);

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
    }
    private void commitIndeed(HashMap<String, String> params, Map<String, File> params_file) {
        if (params_file != null && params_file.size() > 0) {
            params.put("img_num", params_file.size() + "");
        }
        MyOkHttp.get().upload(this, ApiHttpClient.SBMMIT_WORK, params, params_file, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelNewWorkOrder modelNewWorkOrder = (ModelNewWorkOrder) JsonUtil.getInstance().parseJsonFromResponse(response, ModelNewWorkOrder.class);
                    if (modelNewWorkOrder!=null){
                        //用户下单给管理员推送
                        HashMap<String, String> params = new HashMap<>();
                        params.put("work_id",modelNewWorkOrder.getId());
                        new JpushWorkPresenter().setToManage(params);

                        String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交成功");
                        SmartToast.showInfo(msg);
                        startActivity(new Intent(PublicWorkOrderCommitActivity.this, WorkOrderListActivity.class));
                        finish();

                    }
                    //自用报修
//                        final ModelWorkCommitSuccess modelWorkCommitSuccess = (ModelWorkCommitSuccess) JsonUtil.getInstance().parseJsonFromResponse(response, ModelWorkCommitSuccess.class);
//                        if (modelWorkCommitSuccess != null) {
//                            if (modelWorkCommitSuccess.getEntry_fee() == 0) {
//                                //给物业端管理角色推送,用户提交维修信息
//                                HashMap<String, String> params = new HashMap<>();
//                                params.put("id", modelWorkCommitSuccess.getId());
//                                params.put("type", "1");
//                                new JpushWorkPresenter().userToWorkerSubmitJpush(params);
//
//
//                            }
//
//
//                    }
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
     * 检查合法
     */
    private boolean checkReady() {
        if (NullUtil.isStringEmpty(cate_pid)) {
            SmartToast.showInfo("请选择报修类型");
            return false;
        }
        if (NullUtil.isStringEmpty(degree)) {
            SmartToast.showInfo("请选择紧急程度");
            return false;
        }
        if (NullUtil.isStringEmpty(community_id)) {
            SmartToast.showInfo("请选择小区");
            return false;
        }

        return true;
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
                    // 获取小区id 这里必须用之前得
                    community_id=groupMemberBean.getId();
                    community_cn=groupMemberBean.getName();
                    tv_select_community.setText(groupMemberBean.getName());
                    break;
                case ACT_SELECT_TYPE:
                    if (data != null) {
                        ModelWorkPersonalCatItem item = (ModelWorkPersonalCatItem) data.getSerializableExtra("type_data");
                        cate_pid=item.getId();
                        cate_pid_cn=item.getName();
                        tv_select_tag.setText(cate_pid_cn);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
