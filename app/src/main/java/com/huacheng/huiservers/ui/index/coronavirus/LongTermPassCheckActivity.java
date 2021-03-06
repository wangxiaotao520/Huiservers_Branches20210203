package com.huacheng.huiservers.ui.index.coronavirus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.EventModelPass;
import com.huacheng.huiservers.model.ModelIvestigateCommit;
import com.huacheng.huiservers.model.ModelPassCheckInformation;
import com.huacheng.huiservers.model.ModelPhoto;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.workorder.adapter.SelectImgAdapter;
import com.huacheng.huiservers.utils.ucrop.ImgCropUtil;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.widget.GridViewNoScroll;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.utils.FileUtils;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Description: 长期通行证申请界面
 * created by wangxiaotao
 * 2020/2/25 0025 上午 11:10
 */
public class LongTermPassCheckActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_container;
    private List<ModelPassCheckInformation.QuestionBean> mDatas = new ArrayList<>();     //总数据
    private List<ModelIvestigateCommit> mDatas_commit = new ArrayList<>();   //总提交
    private List<ModelIvestigateCommit> mDatas_commit_radio = new ArrayList<>();//提交radio
    private List<ModelIvestigateCommit> mDatas_commit_check = new ArrayList<>();//提交checkbox
    private List<ModelIvestigateCommit> mDatas_commit_text = new ArrayList<>();//提交文本框
    private List<ModelIvestigateCommit> mDatas_commit_img = new ArrayList<>();//提交图片
    private List<EditText> mDatas_edittext = new ArrayList<>();//文本框
    private GridViewNoScroll gridview_imgs;
    SelectImgAdapter gridviewImgsAdapter;
    protected ArrayList<ModelPhoto> photoList = new ArrayList();//图片集合
    private Map<String, File> images_submit = new HashMap<>();
    protected ArrayList<File> waterMarkList = new ArrayList();//水印图片集合
    private RxPermissions rxPermission;
    public static final int ACT_SELECT_PHOTO = 111;//选择图片
    private TextView tv_commit;
    // 请求加载系统照相机
    private static final int REQUEST_CAMERA = 100;
    private File mTmpFile;
    private int jump_type = 1; //1是从右上角问题提交  2重新提交

    private int inspect_status = 1; //设备巡检情况  //1是正常2是异常
    private String status="";//1不需要审核 2 需要审核
    private ImageView iv_right;
    private TextView tv_house;
    private EditText et_name;
    private EditText et_shenfen_num;
    private EditText et_phone;
    private EditText et_car_num;

    private String company_id="";
    private String pass_check_set_id="";
    private String community_id;
    private String community_name;
    private String room_id;
    private String room_info;
    private String owner_name;
    private String id_card;
    private String phone;
    private String car_number;

    @Override
    protected void initView() {
        rxPermission = new RxPermissions(this);
        findTitleViews();
        titleName.setText("长期通行证申请");
        iv_right = findViewById(R.id.iv_right);
        iv_right.setBackgroundResource(R.mipmap.ic_share_black);
        iv_right.setVisibility(View.VISIBLE);

        tv_house = findViewById(R.id.tv_house);
        tv_house.setText(community_name+room_info);
        et_name = findViewById(R.id.et_name);
        et_shenfen_num = findViewById(R.id.et_shenfen_num);
        et_phone = findViewById(R.id.et_phone);
        et_car_num = findViewById(R.id.et_car_num);


        ll_container = findViewById(R.id.ll_container);

    }

    @Override
    protected void initData() {
        String url = ApiHttpClient.PASS_CHECK_INFORMATION;
//        if (jump_type == 1) {
//            url = ApiHttpClient.PASS_CHECK_INFORMATION;
//        } else {
//            url = ApiHttpClient.PASS_CHECK_INFORMATION;
//        }
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("company_id",company_id);
        params.put("pass_check_set_id",pass_check_set_id);

        MyOkHttp.get().post(url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
//                    List<ModelIvestigateInformation> data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelIvestigateInformation.class);

                    ModelPassCheckInformation data = (ModelPassCheckInformation) JsonUtil.getInstance().parseJsonFromResponse(response, ModelPassCheckInformation.class);
                    if (data!=null){
                        if (jump_type==1){
                            //普通提交
                            if (data.getPc_info()!=null){
                                ModelPassCheckInformation.PicInfoBean pc_info = data.getPc_info();
                                et_name.setText(pc_info.getOwner_name()+"");
                                et_name.setSelection(et_name.getText().length());
                                owner_name=pc_info.getOwner_name()+"";
                                et_shenfen_num.setText(pc_info.getId_card()+"");
                                id_card=pc_info.getId_card()+"";
                                et_phone.setText(pc_info.getPhone());
                                phone=pc_info.getPhone()+"";
                                et_car_num.setText(pc_info.getCar_number()+"");
                                car_number=pc_info.getCar_number()+"";
                            }
                        }else {
                            //重新提交
                            //如果是重新提交的话 一定要显示详情带过来的数据
                            owner_name = getIntent().getStringExtra("owner_name") + "";
                            et_name.setText(owner_name);
                            et_name.setSelection(et_name.getText().length());
                            id_card = getIntent().getStringExtra("id_card") + "";
                            et_shenfen_num.setText(id_card);
                            phone=getIntent().getStringExtra("phone");
                            et_phone.setText(phone);
                            String car_number=getIntent().getStringExtra("car_number");
                            if (!NullUtil.isStringEmpty(car_number)){
                              LongTermPassCheckActivity.this.car_number=car_number;
                              et_car_num.setText(car_number);
                            }

                        }
                          //下方问题
                        if (data.getQuestion()!=null&&data.getQuestion().size()>0){
                            mDatas.clear();
                            mDatas.addAll(data.getQuestion());
                            inflateContent();
                        }else {
                            // 说明没有问题 显示底部按钮
                            View view_commit = LayoutInflater.from(LongTermPassCheckActivity.this).inflate(R.layout.item_inspect_5, null);
                            tv_commit = view_commit.findViewById(R.id.tv_confirm);
                            tv_commit.setOnClickListener(LongTermPassCheckActivity.this);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            ll_container.addView(view_commit, params);
                        }
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    /**
     * 填充界面
     */
    private void inflateContent() {
        if (mDatas.size() > 0) {
            for (int i = 0; i < mDatas.size(); i++) {
                final ModelPassCheckInformation.QuestionBean model = mDatas.get(i);
                if ("1".equals(model.getType())) {
                    //单选框
                    View view_radio = LayoutInflater.from(this).inflate(R.layout.item_inspect_1, null);
                    TextView tv_radio_title = view_radio.findViewById(R.id.tv_radio_title);
                    final MyListView mListView = view_radio.findViewById(R.id.mListView);
                    tv_radio_title.setText(model.getName() + "");
                    final ArrayList<ModelPassCheckInformation.QuestionBean.AnswerBean> checkBeans = new ArrayList<>();
                    checkBeans.addAll(model.getAnswer());
                    final CommonAdapter<ModelPassCheckInformation.QuestionBean.AnswerBean> adapter = new CommonAdapter<ModelPassCheckInformation.QuestionBean.AnswerBean>(this, R.layout.item_item_inspect1, checkBeans) {
                        @Override
                        protected void convert(ViewHolder viewHolder, ModelPassCheckInformation.QuestionBean.AnswerBean item, int position) {
                            viewHolder.<ImageView>getView(R.id.iv_select).setBackgroundResource(R.drawable.selector_radio);
                            if (item.isSelected()) {
                                viewHolder.<ImageView>getView(R.id.iv_select).setSelected(true);
                            } else {
                                viewHolder.<ImageView>getView(R.id.iv_select).setSelected(false);
                            }
                            viewHolder.<TextView>getView(R.id.tv_name).setText(item.getVal() + "");
                        }
                    };
                    mListView.setAdapter(adapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            if (jump_type == 2) {//详情就返回
//                                return;
//                            }
                            for (int i1 = 0; i1 < checkBeans.size(); i1++) {
                                ModelPassCheckInformation.QuestionBean.AnswerBean checkBean = checkBeans.get(i1);
                                if (i1 == position) {
                                    checkBean.setSelected(true);
                                    // 参数如果改的话
                                    ModelIvestigateCommit modelInspectCommit_radio = new ModelIvestigateCommit();
                                    modelInspectCommit_radio.setId(model.getId());
                                    modelInspectCommit_radio.setForm_type(model.getType());
                                    modelInspectCommit_radio.setForm_val(checkBean.getId());
                                    //根据最外层的id判断原有的是否被选择过
                                    if (mDatas_commit_radio.size() > 0 && mDatas_commit_radio.contains(modelInspectCommit_radio)) {
                                        //之前已经选过这个单选框
                                        ModelIvestigateCommit modelInspectCommit_radio1 = null;
                                        for (int i2 = 0; i2 < mDatas_commit_radio.size(); i2++) {
                                            if (modelInspectCommit_radio.getId().equals(mDatas_commit_radio.get(i2).getId())) {
                                                modelInspectCommit_radio1 = mDatas_commit_radio.get(i2);
                                            }
                                        }
                                        if (modelInspectCommit_radio1 != null) {
                                            modelInspectCommit_radio1.setForm_val(checkBean.getId());
                                        }
                                    } else {
                                        //之前没有选过这个单选框选项
                                        mDatas_commit_radio.add(modelInspectCommit_radio);
                                    }
                                } else {
                                    checkBean.setSelected(false);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll_container.addView(view_radio, params);
                } else if ("2".equals(model.getType())) {
                    //复选框
                    View view_check = LayoutInflater.from(this).inflate(R.layout.item_inspect_2, null);
                    TextView tv_checkbox_title = view_check.findViewById(R.id.tv_checkbox_title);
                    MyListView mListView = view_check.findViewById(R.id.mListView);
                    tv_checkbox_title.setText(model.getName() + "");
                    final ArrayList<ModelPassCheckInformation.QuestionBean.AnswerBean> checkBeans = new ArrayList<>();
                    checkBeans.addAll(model.getAnswer());
                    final CommonAdapter<ModelPassCheckInformation.QuestionBean.AnswerBean> adapter = new CommonAdapter<ModelPassCheckInformation.QuestionBean.AnswerBean>(this, R.layout.item_item_inspect1, checkBeans) {
                        @Override
                        protected void convert(ViewHolder viewHolder, ModelPassCheckInformation.QuestionBean.AnswerBean item, int position) {
                            viewHolder.<ImageView>getView(R.id.iv_select).setBackgroundResource(R.drawable.selector_radio2);
                            if (item.isSelected()) {
                                viewHolder.<ImageView>getView(R.id.iv_select).setSelected(true);
                            } else {
                                viewHolder.<ImageView>getView(R.id.iv_select).setSelected(false);
                            }
                            viewHolder.<TextView>getView(R.id.tv_name).setText(item.getVal() + "");
                        }
                    };
                    mListView.setAdapter(adapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                            if (jump_type == 2) {//详情就返回
//                                return;
//                            }
                            ModelPassCheckInformation.QuestionBean.AnswerBean checkBean = checkBeans.get(position);
                            if (checkBean.isSelected()) {
                                checkBean.setSelected(false);
                            } else {
                                checkBean.setSelected(true);
                            }
                            adapter.notifyDataSetChanged();
                            // 参数如果改的话
                            ModelIvestigateCommit modelInspectCommit_check = new ModelIvestigateCommit();
                            modelInspectCommit_check.setId(model.getId());
                            modelInspectCommit_check.setForm_type(model.getType());
                            String str_commit = "";
                            for (int i1 = 0; i1 < checkBeans.size(); i1++) {
                                if (checkBeans.get(i1).isSelected()) {
                                    str_commit += checkBeans.get(i1).getId() + ",";
                                }
                            }
                            if (str_commit.contains(",")) {
                                str_commit = str_commit.substring(0, str_commit.lastIndexOf(","));
                            }
                            if (mDatas_commit_check.size() > 0 && mDatas_commit_check.contains(modelInspectCommit_check)) {
                                //之前已经选过这个复选框所有选项
                                ModelIvestigateCommit modelInspectCommit_check1 = null;
                                for (int i2 = 0; i2 < mDatas_commit_check.size(); i2++) {
                                    if (modelInspectCommit_check.getId().equals(mDatas_commit_check.get(i2).getId())) {
                                        modelInspectCommit_check1 = mDatas_commit_check.get(i2);
                                    }
                                }
                                if (modelInspectCommit_check1 != null) {
                                    modelInspectCommit_check1.setForm_val(str_commit);
                                }
                            } else {
                                //之前没有选过这个复选框选项
                                if (modelInspectCommit_check != null) {
                                    modelInspectCommit_check.setForm_val(str_commit);
                                }
                                mDatas_commit_check.add(modelInspectCommit_check);
                            }
                        }
                    });
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll_container.addView(view_check, params);
                } else if ("3".equals(model.getType())) {
                    //文本框
                    View view_check = LayoutInflater.from(this).inflate(R.layout.item_inspect_3, null);
                    TextView tv_title = view_check.findViewById(R.id.tv_title);
                    EditText et_content = view_check.findViewById(R.id.et_content);
                    tv_title.setText(model.getName() + "");
//                    if (jump_type == 2) { //详情
//                        //取第一条就行
//                        et_content.setText(model.getForm_val().get(0).getVal());
//                        et_content.setEnabled(false);
//                    }
                    // 参数如果改的话
                    ModelIvestigateCommit modelInspectCommit_text = new ModelIvestigateCommit();
                    modelInspectCommit_text.setId(model.getId());
                    modelInspectCommit_text.setForm_type(model.getType());
                    mDatas_commit_text.add(modelInspectCommit_text);
                    mDatas_edittext.add(et_content);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll_container.addView(view_check, params);
                } else if ("4".equals(model.getType())) {
                    //图片
                    View view_img = LayoutInflater.from(this).inflate(R.layout.item_inspect_4, null);
                    TextView tv_title = view_img.findViewById(R.id.tv_title);
                    gridview_imgs = view_img.findViewById(R.id.gridview_imgs);
                    tv_title.setText((model.getName() + ""));

                    gridviewImgsAdapter = new SelectImgAdapter(this, photoList);
                    gridviewImgsAdapter.setMAX_COUNT(1);
                    gridview_imgs.setAdapter(gridviewImgsAdapter);
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
                                                jumpToImageSelector( position);
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
//                                if (jump_type == 2) {//详情
//                                    if (!NullUtil.isStringEmpty(photoList.get(i).getPath())) {
//                                        imgs.add(ApiHttpClient.IMG_URL + photoList.get(i).getPath());
//                                    }
//                                } else {
                                    //只要localpath不为空则说明是刚选上的
                                    if (!NullUtil.isStringEmpty(photoList.get(i).getLocal_path())) {
                                        imgs.add(photoList.get(i).getLocal_path());
                                    }
   //                             }
                            }
                            Intent intent = new Intent(LongTermPassCheckActivity.this, PhotoViewPagerAcitivity.class);
                            intent.putExtra("img_list", imgs);
                            intent.putExtra("position", position);
                            intent.putExtra("isShowDelete", false);
                            startActivity(intent);

                        }
                    });

//                    if (jump_type == 2) {//详情展示
//                        gridviewImgsAdapter.setShowAdd(false);
//                        gridviewImgsAdapter.setShowDelete(false);
//                        for (int i1 = 0; i1 < model.getForm_val().size(); i1++) {
//                            ModelPhoto modelPhoto = new ModelPhoto();
//                            modelPhoto.setPath(model.getForm_val().get(i1).getVal() + "");
//                            photoList.add(modelPhoto);
//                        }
//                        gridviewImgsAdapter.notifyDataSetChanged();
//                    }

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll_container.addView(view_img, params);
                    ModelIvestigateCommit modelInspectCommit = new ModelIvestigateCommit();
                    modelInspectCommit.setId(model.getId());
                    modelInspectCommit.setForm_type(model.getType());
                    modelInspectCommit.setForm_val("");
                    mDatas_commit_img.add(modelInspectCommit);
                }

            }
            View view_commit = LayoutInflater.from(this).inflate(R.layout.item_inspect_5, null);
            tv_commit = view_commit.findViewById(R.id.tv_confirm);
            tv_commit.setOnClickListener(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll_container.addView(view_commit, params);
        }
//        if (jump_type == 2) {
//            tv_commit.setVisibility(View.GONE);
//        }
    }

    /**
     * 跳转到照相机
     *
     * @param position
     */
    private void jumpToCamera(int position) {
        Intent fullIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (fullIntent.resolveActivity(getPackageManager()) != null) {
            try {
                mTmpFile = FileUtils.createTmpFile(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 获取uri
            if (mTmpFile != null && mTmpFile.exists()) {
                Uri uri = null;
                if (Build.VERSION.SDK_INT >= 24) {
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, mTmpFile.getAbsolutePath());
                    uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                } else {
                    uri = Uri.fromFile(mTmpFile);
                }
                // 跳转
                fullIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                fullIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(fullIntent, REQUEST_CAMERA);
            } else {
                SmartToast.showInfo("图片错误");
            }
        } else {
            SmartToast.showInfo("没有系统相机");
        }

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
        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);

        imageIntent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, list_jump);
        startActivityForResult(imageIntent, ACT_SELECT_PHOTO);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_longterm_passcheck;
    }

    @Override
    protected void initIntentData() {
        this.jump_type = this.getIntent().getIntExtra("jump_type", 1);
        this.status = this.getIntent().getStringExtra("status");
        this.company_id = this.getIntent().getStringExtra("company_id");
        this.pass_check_set_id = this.getIntent().getStringExtra("id");
        community_id = this.getIntent().getStringExtra("community_id");
        community_name = this.getIntent().getStringExtra("community_name");
        room_id = this.getIntent().getStringExtra("room_id");
        room_info = this.getIntent().getStringExtra("room_info");
//        this.task_info_id = this.getIntent().getStringExtra("task_info_id");
//        this.equipment_id = this.getIntent().getStringExtra("equipment_id");
//        this.name = this.getIntent().getStringExtra("name");

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
//                case REQUEST_CAMERA:
//                    String back_path = mTmpFile.getAbsolutePath();
//                    ModelPhoto modelPhoto = new ModelPhoto();
//                    modelPhoto.setLocal_path(back_path);
//                    photoList.add(modelPhoto);
//                    // 将新图上传
//                    if (gridviewImgsAdapter != null) {
//                        gridviewImgsAdapter.notifyDataSetChanged();
//                    }
//                    break;
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
                        //    checkButton();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                //先将文本框的内容添加进去
                if (mDatas_edittext.size() > 0) {
                    for (int i = 0; i < mDatas_edittext.size(); i++) {
                        String text = mDatas_edittext.get(i).getText().toString().trim();
                        if (!NullUtil.isStringEmpty(text)) {
                            ModelIvestigateCommit modelInspectCommit = mDatas_commit_text.get(i);
                            modelInspectCommit.setForm_val("" + text);
                        }
                    }
                }
                if (!checkReady()) {
                    return;
                }

                checkInspectStatus();
                if (inspect_status == 2) {
                    new CommomDialog(LongTermPassCheckActivity.this, R.style.my_dialog_DimEnabled,
                            "设备出现异常情况，是否提交工单", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm){
                                if (mDatas_commit_img.size() > 0) {//说明有图片
                                    showDialog(smallDialog);
                                    smallDialog.setTipTextView("压缩中");
                                    waterMarkList.clear();
                                    images_submit.clear();
                                    zipPhoto(new HashMap<String, String>());

                                } else {
                                    commitIndeed();
                                }
                            }else {
                                dialog.dismiss();
                            }
                        }
                    }).show();

                } else {
                    if (mDatas_commit_img.size() > 0) {//说明有图片
                        showDialog(smallDialog);
                        smallDialog.setTipTextView("压缩中");
                        waterMarkList.clear();
                        images_submit.clear();
                        zipPhoto(new HashMap<String, String>());

                    } else {
                        commitIndeed();
                    }
                }
                break;
            default:
                break;
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
                                    commitWithImage(params, images_submit);
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
        String path = Environment.getExternalStorageDirectory() + "/huiservers /image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    /**
     * 直接提交(没有图片)
     */
    private void commitIndeed() {

        mDatas_commit.clear();
        //添加
        mDatas_commit.addAll(mDatas_commit_radio);
        mDatas_commit.addAll(mDatas_commit_check);

        mDatas_commit.addAll(mDatas_commit_text);
        mDatas_commit.addAll(mDatas_commit_img);

        Gson gson = new Gson();
        String json_commit = gson.toJson(mDatas_commit);
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("answer", json_commit + "");
        params.put("company_id",company_id+"");
        params.put("pass_check_set_id",pass_check_set_id+"");
        params.put("type",2+"");
        params.put("community_id",community_id);
        params.put("community_name",community_name);
        params.put("room_id",room_id);
        params.put("room_info",room_info);
        params.put("owner_name",owner_name);
        params.put("id_card",id_card);
        params.put("phone",phone);
        if (!NullUtil.isStringEmpty(car_number)){
            params.put("car_number",car_number);
        }

//        if (inspect_status==2){//有异常的时候传1 //正常传0
//            params.put("yichang", 1 + "");
//        }
        MyOkHttp.get().post(ApiHttpClient.PASS_CHECK_SUBMIT, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    setResult(RESULT_OK);
                    if (inspect_status==2){
                        //有异常 提交工单
//                        Intent intent = new Intent(LongTermPassCheckActivity.this, PublicWorkOrderCommitActivity.class);
//                        intent.putExtra("task_info_id", task_info_id);
//                        intent.putExtra("equipment_id", equipment_id);
//                        startActivity(intent);
                    }else {
                        SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "提交成功"));
                    }
                    EventModelPass modelPass=new EventModelPass();
                    modelPass.setStatus(status);
                    EventBus.getDefault().post(modelPass);
                    finish();
                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "提交失败"));
                }
                ImgCropUtil.deleteCacheFile(new File(ImgCropUtil.getCacheDir()));
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    /**
     * 上传带图片的情况
     *
     * @param params
     * @param
     */
    private void commitWithImage(HashMap<String, String> params, Map<String, File> params_file) {
//        if (params_file != null && params_file.size() > 0) {
//            params.put("img_num", params_file.size() + "");
//        }
        mDatas_commit.clear();
        //添加
        mDatas_commit.addAll(mDatas_commit_radio);
        mDatas_commit.addAll(mDatas_commit_check);

        mDatas_commit.addAll(mDatas_commit_text);
        mDatas_commit.addAll(mDatas_commit_img);

        Gson gson = new Gson();
        String json_commit = gson.toJson(mDatas_commit);
        showDialog(smallDialog);
        params.put("answer", json_commit + "");
        params.put("company_id",company_id+"");
        params.put("pass_check_set_id",pass_check_set_id+"");
        params.put("type",2+"");
        params.put("community_id",community_id);
        params.put("community_name",community_name);
        params.put("room_id",room_id);
        params.put("room_info",room_info);
        params.put("owner_name",owner_name);
        params.put("id_card",id_card);
        params.put("phone",phone);
        if (!NullUtil.isStringEmpty(car_number)){
            params.put("car_number",car_number);
        }
//        if (inspect_status==2){//有异常的时候传1 //正常传0
//            params.put("yichang", 1 + "");
//        }
        MyOkHttp.get().upload(ApiHttpClient.PASS_CHECK_SUBMIT, params, params_file, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    setResult(RESULT_OK);
                    if (inspect_status==2){
                        //有异常 提交工单
//                        Intent intent = new Intent(LongTermPassCheckActivity.this, PublicWorkOrderCommitActivity.class);
//                        intent.putExtra("task_info_id", task_info_id);
//                        intent.putExtra("equipment_id", equipment_id);
//                        startActivity(intent);
                    }else {
                        SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "提交成功"));
                    }
                    EventModelPass modelPass=new EventModelPass();
                    modelPass.setStatus(status);
                    EventBus.getDefault().post(modelPass);
                    finish();
                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "提交失败"));
                }
                ImgCropUtil.deleteCacheFile(new File(ImgCropUtil.getCacheDir()));
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    /**
     * 判断设备有无异常情况
     */
    private void checkInspectStatus() {
        for (int i = 0; i < mDatas.size(); i++) {
            final ModelPassCheckInformation.QuestionBean model = mDatas.get(i);
            if ("1".equals(model.getType())) {
                List<ModelPassCheckInformation.QuestionBean.AnswerBean> form_val = model.getAnswer();
                for (int i1 = 0; i1 < form_val.size(); i1++) {
                    if (form_val.get(i1).getStatus() == 2 && form_val.get(i1).isSelected()) {
                        inspect_status = 2;
                        return;
                    }
                }
            } else if ("2".equals(model.getType())) {
                List<ModelPassCheckInformation.QuestionBean.AnswerBean> form_val = model.getAnswer();
                for (int i1 = 0; i1 < form_val.size(); i1++) {
                    if (form_val.get(i1).getStatus() == 2 && form_val.get(i1).isSelected()) {
                        inspect_status = 2;
                        return;
                    }
                }
            }
        }
        inspect_status = 1;
    }

    private boolean checkReady() {
        boolean isReady = true;
        owner_name = et_name.getText().toString().trim()+"";
        if (NullUtil.isStringEmpty(owner_name)){
            SmartToast.showInfo("姓名不可为空");
            return false;
        }
        id_card = et_shenfen_num.getText().toString().trim()+"";
        if (NullUtil.isStringEmpty(id_card)){
            SmartToast.showInfo("身份证号不可为空");
            return false;
        }
        phone = et_phone.getText().toString().trim()+"";
        if (NullUtil.isStringEmpty(phone)){
            SmartToast.showInfo("联系方式不可为空");
            return false;
        }
        car_number = et_car_num.getText().toString().trim()+"";
        if (mDatas.size() > 0) {
            Loop:
            for (int i = 0; i < mDatas.size(); i++) {
                ModelPassCheckInformation.QuestionBean  model = mDatas.get(i);
                if ("1".equals(model.getType())) {
                    //保证每个id都包含
                    isReady = false;
                    if (mDatas_commit_radio.size() == 0) {
                        SmartToast.showInfo("有单选框为空");
                        break Loop;
                    }
                    for (int i1 = 0; i1 < mDatas_commit_radio.size(); i1++) {
                        ModelIvestigateCommit modelInspectCommit = mDatas_commit_radio.get(i1);
                        //所有提交的里面必须包含这些id且不能为空
                        if (modelInspectCommit.getId().equals(model.getId())) {
                            if (!NullUtil.isStringEmpty(modelInspectCommit.getForm_val())) {
                                isReady = true;
                                break;
                            }
                        }
                    }
                    if (isReady == false) {
                        SmartToast.showInfo("有单选框为空");
                        break Loop;
                    }
                } else if ("2".equals(model.getType())) {
                    isReady = false;
                    if (mDatas_commit_check.size() == 0) {
                        SmartToast.showInfo("有复选框为空");
                        break Loop;
                    }
                    for (int i1 = 0; i1 < mDatas_commit_check.size(); i1++) {
                        ModelIvestigateCommit modelInspectCommit = mDatas_commit_check.get(i1);
                        if (modelInspectCommit.getId().equals(model.getId())) {
                            //所有提交的里面必须包含这些id且不能为空
                            if (!NullUtil.isStringEmpty(modelInspectCommit.getForm_val())) {
                                isReady = true;
                                break;
                            }
                        }
                    }
                    if (isReady == false) {
                        SmartToast.showInfo("有复选框为空");
                        break Loop;
                    }
                } else if ("3".equals(model.getType())) {
                    isReady = true;
                    for (int i1 = 0; i1 < mDatas_commit_text.size(); i1++) {
                        ModelIvestigateCommit modelInspectCommit = mDatas_commit_text.get(i1);
                        if (NullUtil.isStringEmpty(modelInspectCommit.getForm_val())) {
                            SmartToast.showInfo("有文本框为空");
                            isReady = false;
                            break Loop;
                        }
                    }

                } else if ("4".equals(model.getType())) {
                    if (photoList.size() == 0) {
                        isReady = false;
                        SmartToast.showInfo("图片不能为空");
                        break Loop;
                    }
                }
            }
        }
        return isReady;
    }

}

