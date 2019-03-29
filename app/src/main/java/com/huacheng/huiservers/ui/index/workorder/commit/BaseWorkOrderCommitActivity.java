package com.huacheng.huiservers.ui.index.workorder.commit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.YuFuDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelPhoto;
import com.huacheng.huiservers.model.ModelWorkCommitSuccess;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.geren.ZhifuActivity;
import com.huacheng.huiservers.ui.index.workorder.JpushWorkPresenter;
import com.huacheng.huiservers.ui.index.workorder.WorkOrderListActivity;
import com.huacheng.huiservers.ui.index.workorder.adapter.SelectImgAdapter;
import com.huacheng.huiservers.ui.index.wuye.bean.WuYeBean;
import com.huacheng.huiservers.utils.XToast;
import com.huacheng.huiservers.utils.ucrop.ImgCropUtil;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.ToastUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

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
 * Description:基类报修
 * created by wangxiaotao
 * 2018/12/12 0012 上午 10:57
 */
public class BaseWorkOrderCommitActivity extends BaseActivity {
    protected RelativeLayout rel_select_tag;
    protected TextView tv_select_tag;
    protected GridView gridview_imgs;
    SelectImgAdapter gridviewImgsAdapter;
    protected ArrayList<ModelPhoto> photoList = new ArrayList();//图片集合
    private Map<String, File> images_submit = new HashMap<>();
    protected EditText et_beizhu;
    protected TextView tv_confirm;
    public static final int ACT_SELECT_PHOTO = 111;//选择图片
    public static final int ACT_SELECT_HOUSE = 112;//选择房屋
    protected ImageView iv_arrow;
    protected RelativeLayout rel_select_house;
    protected EditText et_name;
    protected EditText et_phone;
    protected TextView tv_address;//显示的地址
    protected TextView tv_yufu;

    //提交
    protected int work_type = 1;
    protected String type_id = "";
    protected String type_name = "";
    protected String contact = "";
    protected String userphone = "";
    protected String address = "";
    protected String type_pid = "";//分类父id 自用部位分类 父id，自用保修必传，公共保修不传
    protected String content = "";//备注
    protected WuYeBean selected_house;//选择的房屋
    private YuFuDialog yuFuDialog;
    private RxPermissions rxPermission;

    @Override
    protected void initView() {
        smallDialog.setCanceledOnTouchOutside(false);
        rxPermission = new RxPermissions(this);
        findTitleViews();
        tv_right = findViewById(R.id.txt_right1);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("订单中心");
        rel_select_tag = findViewById(R.id.rel_select_tag);
        tv_select_tag = findViewById(R.id.tv_select_tag);
        iv_arrow = findViewById(R.id.iv_arrow);
        gridview_imgs = findViewById(R.id.gridview_imgs);
        gridviewImgsAdapter = new SelectImgAdapter(this, photoList);
        gridview_imgs.setAdapter(gridviewImgsAdapter);
        et_beizhu = findViewById(R.id.et_beizhu);
        tv_confirm = findViewById(R.id.tv_confirm);
        rel_select_house = findViewById(R.id.rel_select_house);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        tv_address = findViewById(R.id.tv_address);
        tv_yufu = findViewById(R.id.tv_yufu);
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
                                    jumpToImageSelector(position);
                                } else {
                                    Toast.makeText(BaseWorkOrderCommitActivity.this, "未打开摄像头权限", Toast.LENGTH_LONG).show();
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

            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 提交

                if (checkReady()) {
                    onClickCommit();
                }
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaseWorkOrderCommitActivity.this, WorkOrderListActivity.class);
                startActivity(intent);
            }
        });
        rel_select_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 选择房屋
                Intent intent = new Intent(BaseWorkOrderCommitActivity.this, SelectHouseActivity.class);
                startActivityForResult(intent, ACT_SELECT_HOUSE);
            }
        });
    }

    /**
     * 检查合法
     */
    private boolean checkReady() {
        if (NullUtil.isStringEmpty(type_id)) {
            XToast.makeText(this, "请选择分类", XToast.LENGTH_SHORT).show();
            return false;
        }
        if (NullUtil.isStringEmpty(contact)) {
            XToast.makeText(this, "联系人不能为空", XToast.LENGTH_SHORT).show();
            return false;
        }
        if (NullUtil.isStringEmpty(userphone)) {
            XToast.makeText(this, "联系电话不能为空", XToast.LENGTH_SHORT).show();
            return false;
        }
        if (selected_house == null) {
            XToast.makeText(this, "房屋不能为空", XToast.LENGTH_SHORT).show();
            return false;
        }
        if (NullUtil.isStringEmpty(address)) {
            XToast.makeText(this, "房屋不能为空", XToast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    //点击提交
    protected void onClickCommit() {
        HashMap<String, String> params = new HashMap<>();
        params.put("work_type", work_type + "");
        params.put("type_id", type_id);
        params.put("type_name", type_name);
        params.put("community_id", selected_house.getCommunity_id());
        params.put("room_id", selected_house.getRoom_id());
        params.put("company_id", selected_house.getCompany_id());
        params.put("contact", contact);
        params.put("userphone", userphone);
        params.put("address", address);
        if (!NullUtil.isStringEmpty(type_pid)) {
            params.put("type_pid", type_pid);
        }
        content = et_beizhu.getText().toString().trim();
        if (!NullUtil.isStringEmpty(content)) {
            params.put("content", content);
        }
        if (photoList.size() > 0) {
            // 压缩图片
            showDialog(smallDialog);
            smallDialog.setTipTextView("压缩中...");

            zipPhoto(params);
        } else {
            showDialog(smallDialog);
            smallDialog.setTipTextView("提交中...");
            commit(params, null);
        }
    }

    private void commit(HashMap<String, String> params, Map<String, File> params_file) {
        if (params_file != null && params_file.size() > 0) {
            params.put("img_num", params_file.size() + "");
        }
        MyOkHttp.get().upload(this, ApiHttpClient.WORK_SAVE, params, params_file, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交成功");
                    if (work_type == 1) {
                        //自用报修
                        final ModelWorkCommitSuccess modelWorkCommitSuccess = (ModelWorkCommitSuccess) JsonUtil.getInstance().parseJsonFromResponse(response, ModelWorkCommitSuccess.class);
                        if (modelWorkCommitSuccess != null) {
                            if (modelWorkCommitSuccess.getEntry_fee() == 0) {
                                //给物业端管理角色推送,用户提交维修信息
                                HashMap<String, String> params = new HashMap<>();
                                params.put("id", modelWorkCommitSuccess.getId());
                                params.put("type", "1");
                                new JpushWorkPresenter().userToWorkerSubmitJpush(params);

                                ToastUtils.showShort(BaseWorkOrderCommitActivity.this, msg);
                                startActivity(new Intent(BaseWorkOrderCommitActivity.this, WorkOrderListActivity.class));
                                finish();
                            } else {
                                yuFuDialog = new YuFuDialog(BaseWorkOrderCommitActivity.this, modelWorkCommitSuccess.getEntry_fee() + "", new YuFuDialog.OnClickSureListener() {
                                    @Override
                                    public void onEnSure() {
                                        Intent intent = new Intent(BaseWorkOrderCommitActivity.this, ZhifuActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("o_id", modelWorkCommitSuccess.getId());
                                        bundle.putString("price", modelWorkCommitSuccess.getEntry_fee() + "");//
                                        bundle.putString("type", "workorder_yufu");
                                        bundle.putString("order_type", "yf");

                                        intent.putExtras(bundle);
                                        startActivityForResult(intent, 111);
                                        yuFuDialog.dismiss();
                                    }
                                });
                                yuFuDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        finish();
                                    }
                                });
                                yuFuDialog.setCanceledOnTouchOutside(false);
                                yuFuDialog.show();
                            }
                        }

                    } else {
                        ModelWorkCommitSuccess modelWorkCommitSuccess = (ModelWorkCommitSuccess) JsonUtil.getInstance().parseJsonFromResponse(response, ModelWorkCommitSuccess.class);
                        //给物业端管理角色推送,用户提交维修信息
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id", modelWorkCommitSuccess.getId());
                        params.put("type", "1");
                        new JpushWorkPresenter().userToWorkerSubmitJpush(params);

                        //公共报修
                        ToastUtils.showShort(BaseWorkOrderCommitActivity.this, msg);
                        // 跳转到列表页
                        startActivity(new Intent(BaseWorkOrderCommitActivity.this, WorkOrderListActivity.class));
                        finish();
                    }
                    //删除缓存文件夹中的图片
                    ImgCropUtil.deleteCacheFile(new File(ImgCropUtil.getCacheDir()));
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交失败");
                    XToast.makeText(BaseWorkOrderCommitActivity.this, msg, XToast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                XToast.makeText(BaseWorkOrderCommitActivity.this, "网络异常，请检查网络设置", XToast.LENGTH_SHORT).show();
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
                                    commit(params, images_submit);
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
                                XToast.makeText(BaseWorkOrderCommitActivity.this, "图片压缩失败", Toast.LENGTH_SHORT).show();
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_workorder_commit;
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
                case ACT_SELECT_HOUSE://选择房屋
                    if (data != null) {
                        this.selected_house = (WuYeBean) data.getSerializableExtra("model_house");
                        if (selected_house != null) {
                            address = selected_house.getCommunity_name() + selected_house.getBuilding_name() + selected_house.getUnit() + selected_house.getCode();
                            tv_address.setText(selected_house.getCommunity_name() + selected_house.getBuilding_name() + "号楼-" +
                                    selected_house.getUnit() + "单元-" + selected_house.getCode());
                            this.contact = selected_house.getFullname();
                            et_name.setText(contact);
                            this.userphone = selected_house.getMobile();
                            et_phone.setText(userphone);
                        }

                    }
                    break;

                default:
                    break;
            }
        }
    }

    //点击外部软件盘消失
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (yuFuDialog != null) {
            yuFuDialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
