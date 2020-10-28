package com.huacheng.huiservers.ui.index.oldservice;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.OldDeviceDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelOldDevice;
import com.huacheng.huiservers.model.ModelOldFootmark;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.oldservice.adapter.AdapterOldDevice;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：养老 查看更多
 * 时间：2020/9/30 16:06
 * created by DFF
 */
public class OldDeviceMoreActivity extends BaseActivity implements OldDeviceDialog.OnClickConfirmListener {

    private MyGridview mGridview1;
    private MyGridview mGridview2;

    private List<ModelOldDevice> mDatas1 = new ArrayList<>();//常用功能
    private List<ModelOldDevice> mDatas2 = new ArrayList<>();//功能设置
    private ModelOldFootmark mOldFootmark;

    private AdapterOldDevice mAdapterOldDevice1;
    private AdapterOldDevice mAdapterOldDevice2;
    private String par_uid = "";
    private String str_url = "";
    private int jump_type = 0;
    OldDeviceDialog dialog;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("全部功能");
        mGridview1 = findViewById(R.id.grid_cat1);
        mGridview2 = findViewById(R.id.grid_cat2);

        String[] str = {"查看足迹", "健康计步", "远程测心率", "远程测血压", "云测温", "电子围栏"};
        for (int i = 0; i < str.length; i++) {
            ModelOldDevice selectCommon = new ModelOldDevice();
            selectCommon.setName(str[i]);
            mDatas1.add(selectCommon);
        }
        String[] str1 = {"查找设备", "SOS", "监护号码"};
        for (int i = 0; i < str1.length; i++) {
            ModelOldDevice selectCommon = new ModelOldDevice();
            //selectCommon.setId((i + 1) + "");
            selectCommon.setName(str1[i]);
            mDatas2.add(selectCommon);
        }

        mAdapterOldDevice1 = new AdapterOldDevice(this, R.layout.item_old_device, mDatas1, 1);
        mGridview1.setAdapter(mAdapterOldDevice1);

        mAdapterOldDevice2 = new AdapterOldDevice(this, R.layout.item_old_device, mDatas2, 2);
        mGridview2.setAdapter(mAdapterOldDevice2);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mGridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {//查看足迹
                    Intent intent = new Intent(OldDeviceMoreActivity.this, MyTrackActivity.class);
                    if (!NullUtil.isStringEmpty(par_uid)){
                        intent.putExtra("par_uid", par_uid);
                    }
                   intent.putExtra("jump_type",jump_type);
                    startActivity(intent);

                } else if (position == 1) {//健康计步
                    Intent intent = new Intent(OldDeviceMoreActivity.this, OldMyStepActivity.class);
                    if (!NullUtil.isStringEmpty(par_uid)){
                        intent.putExtra("par_uid", par_uid);
                    }
                    intent.putExtra("jump_type",jump_type);
                    startActivity(intent);

                } else if (position == 2) {//远程测心率
                    Intent intent = new Intent(OldDeviceMoreActivity.this, OldGaugeActivity.class);
                    if (!NullUtil.isStringEmpty(par_uid)){
                        intent.putExtra("par_uid", par_uid);
                    }
                    intent.putExtra("jump_type",jump_type);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                } else if (position == 3) {//远程测血压
                    Intent intent = new Intent(OldDeviceMoreActivity.this, OldGaugeActivity.class);
                    if (!NullUtil.isStringEmpty(par_uid)){
                        intent.putExtra("par_uid", par_uid);
                    }
                    intent.putExtra("jump_type",jump_type);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                } else if (position == 4) {//云测温
                    Intent intent = new Intent(OldDeviceMoreActivity.this, OldGaugeActivity.class);
                    if (!NullUtil.isStringEmpty(par_uid)){
                        intent.putExtra("par_uid", par_uid);
                    }
                    intent.putExtra("jump_type",jump_type);
                    intent.putExtra("type", 3);
                    startActivity(intent);
                } else if (position == 5) {//电子围栏
                    Intent intent = new Intent(OldDeviceMoreActivity.this, OldFenceListActivity.class);
                    if (!NullUtil.isStringEmpty(par_uid)){
                        intent.putExtra("par_uid", par_uid);
                    }
                    intent.putExtra("jump_type",jump_type);
                    startActivity(intent);
                }

            }
        });
        mGridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {//查找设备
                    getDevice();
                } else if (position == 1) {//SOS
                    getNumber(1);
                } else if (position == 2) {//监护号码
                    getNumber(2);
                }
            }
        });
    }

    /**
     * 查找设备
     */
    private void getDevice() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        String url = "";
        if (!NullUtil.isStringEmpty(par_uid)){
            params.put("par_uid", par_uid);
        }
        if (jump_type==0){
            url=ApiHttpClient.GET_DEVICE;
        }else {
            url=ApiHttpClient.GET_DEVICE1;
        }

        MyOkHttp.get().post(url, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    //SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "成功"));
                    SmartToast.showInfo("正在响铃");
                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "获取数据失败"));
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
     * 获取设备SOS和监护号码
     */
    private void getNumber(final int type) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        String url = "";
        if (!NullUtil.isStringEmpty(par_uid)){
            params.put("par_uid", par_uid);
        }
        if (jump_type==0){
            url=ApiHttpClient.DEVICE_GET_SOS_GUARDER;
        }else {
            url=ApiHttpClient.DEVICE_GET_SOS_GUARDER1;
        }
        MyOkHttp.get().post(url, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    mOldFootmark = (ModelOldFootmark) JsonUtil.getInstance().parseJsonFromResponse(response, ModelOldFootmark.class);
                    if (mOldFootmark != null) {
                        if (type == 1) {
                            dialog = new OldDeviceDialog(OldDeviceMoreActivity.this, OldDeviceMoreActivity.this, 1, mOldFootmark.getSOS());
                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                            showDialog(dialog);
                        } else {
                            dialog = new OldDeviceDialog(OldDeviceMoreActivity.this, OldDeviceMoreActivity.this, 2, mOldFootmark.getGuarder());
                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                            showDialog(dialog);
                        }
                    } else {
                        if (type == 1) {
                            dialog = new OldDeviceDialog(OldDeviceMoreActivity.this, OldDeviceMoreActivity.this, 1, "");
                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                            showDialog(dialog);
                        } else {
                            dialog = new OldDeviceDialog(OldDeviceMoreActivity.this, OldDeviceMoreActivity.this, 2, "");
                            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                            showDialog(dialog);
                        }
                    }

                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "获取数据失败"));
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_old_device_more;
    }

    @Override
    protected void initIntentData() {
        par_uid = getIntent().getStringExtra("par_uid");
        jump_type=getIntent().getIntExtra("jump_type",0);
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    /**
     * SOS手机号码
     * 设置监护号码
     *
     * @param dialog
     * @param content
     * @param type
     */
    @Override
    public void onClickConfirm(Dialog dialog, String content, int type) {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        if (!NullUtil.isStringEmpty(par_uid)){
            params.put("par_uid", par_uid);
        }
        if (jump_type==0){
            if (type == 1) {
                //SOS
                params.put("SOS", content);
                str_url = ApiHttpClient.DEVICE_SOS;
            } else {
                params.put("Guarder", content);
                str_url = ApiHttpClient.DEVICE_GUARDER;
            }
        }else {
            if (type == 1) {
                //SOS
                params.put("SOS", content);
                str_url = ApiHttpClient.DEVICE_SOS1;
            } else {
                params.put("Guarder", content);
                str_url = ApiHttpClient.DEVICE_GUARDER1;
            }
        }

        MyOkHttp.get().post(str_url, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "成功"));
                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "获取数据失败"));
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }
}
