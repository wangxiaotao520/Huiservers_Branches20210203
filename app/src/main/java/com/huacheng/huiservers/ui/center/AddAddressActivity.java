package com.huacheng.huiservers.ui.center;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.CommunityListActivity;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.bean.ModelAddressList;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.stx.xhb.xbanner.OnDoubleClickListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.functions.Consumer;

/**
 * Description: 新建地址
 * created by wangxiaotao
 * 2019/11/13 0013 上午 11:21
 */
public class AddAddressActivity extends BaseActivity{

    private EditText edt_name;
    private EditText edt_phone;
    private TextView edt_address;
    private EditText edt_address_detail;
    private LinearLayout ll_address;
    ModelAddressList modelAddressList;
    private int jump_type = 1;//1.从商城跳过来的 2.从服务调过来的 3.从我的跳过来的
    private String region_cn = ""; //地址区域
    private String community_cn = "";//
    private String service_id = "";//
    private TextView tv_delete_address;

    @Override
    protected void initView() {
        findTitleViews();
        tv_right=findViewById(R.id.txt_right1);
        tv_right.setText("保存");
        tv_right.setVisibility(View.VISIBLE);

        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_address = findViewById(R.id.edt_address);
        ll_address = findViewById(R.id.ll_address);
        edt_address_detail = findViewById(R.id.edt_address_detail);
        if (modelAddressList!=null){
            //编辑
            titleName.setText("编辑地址");
            edt_name.setText(modelAddressList.getConsignee_name());
            edt_name.setSelection(modelAddressList.getConsignee_name().length());
            edt_phone.setText(modelAddressList.getConsignee_mobile());
            edt_address.setText(modelAddressList.getRegion_cn()+modelAddressList.getCommunity_cn());
            edt_address_detail.setText(modelAddressList.getDoorplate());

            region_cn=modelAddressList.getRegion_cn();
            community_cn=modelAddressList.getCommunity_cn();
        }else {
            //新增
            titleName.setText("添加地址");
        }
        tv_delete_address = findViewById(R.id.tv_delete_address);
    }

    @Override
    protected void initData() {

    }

    /**
     * 确认
     */
    private void confirm() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        String consignee_name = edt_name.getText().toString().trim();
        params.put("consignee_name",consignee_name);
        String consignee_mobile = edt_phone.getText().toString().trim();
        params.put("consignee_mobile",consignee_mobile);
        String doorplate = edt_address_detail.getText().toString().trim();
        params.put("doorplate",doorplate);
        params.put("region_cn",region_cn);
        params.put("community_cn",community_cn);
        if (modelAddressList!=null){
            params.put("id",modelAddressList.getId()+"");//编辑
        }
        if (jump_type==2){
            params.put("service_id",service_id+"");
        }
        MyOkHttp.get().post( Url_info.add_user_address, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    Intent intent = new Intent();
                    // 生成一个model
                    ModelAddressList modelAddressList = (ModelAddressList) JsonUtil.getInstance().parseJsonFromResponse(response, ModelAddressList.class);
                    if (modelAddressList!=null){
                        intent.putExtra("model",modelAddressList);
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                } else {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
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

    @Override
    protected void initListener() {
        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new RxPermissions(AddAddressActivity.this).request( Manifest.permission.ACCESS_COARSE_LOCATION)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean isGranted) throws Exception {
                                if (isGranted) {
                                    //权限同意 ,开始定位
                                    Intent intent1 = new Intent(mContext, CommunityListActivity.class);
                                    intent1.putExtra("jump_type",2);
                                    startActivityForResult(intent1, 111);

                                } else {
                                    //权限拒绝
                                }
                            }
                        });

            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (checkReady()){
                   confirm();
               }
            }
        });

        if (jump_type==3&&modelAddressList!=null){
            //从我的跳进来的
            tv_delete_address.setVisibility(View.VISIBLE);
            tv_delete_address.setOnClickListener(new OnDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    new CommomDialog(AddAddressActivity.this, R.style.my_dialog_DimEnabled, "确认删除地址？", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                dialog.dismiss();
                                getdelete();
                            }
                        }
                    }).show();//.setTitle("提示")
                }
            });
        }
    }
    private void getdelete() {//删除地址
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", modelAddressList.getId());
        showDialog(smallDialog);
        MyOkHttp.get().post(info.del_user_address, params.getParams(), new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)){
                    Intent intent = new Intent();
                    ModelAddressList modelAddressList = new ModelAddressList();
                    intent.putExtra("model",modelAddressList);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response,"删除失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }
    private boolean checkReady() {
        String consignee_name = edt_name.getText().toString().trim();
        if (NullUtil.isStringEmpty(consignee_name)){
            SmartToast.showInfo("请输入名字");
            return false;
        }
        String consignee_mobile = edt_phone.getText().toString().trim();
        if (NullUtil.isStringEmpty(consignee_mobile)){
            SmartToast.showInfo("请输入电话号码");
            return false;
        }
        String address = edt_address.getText().toString().trim();
        if (NullUtil.isStringEmpty(address)){
            SmartToast.showInfo("请选择地址");
            return false;
        }
        String doorplate = edt_address_detail.getText().toString().trim();
        if (NullUtil.isStringEmpty(doorplate)){
            SmartToast.showInfo("请输入详细地址");
            return false;
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initIntentData() {
       this.jump_type= this.getIntent().getIntExtra("jump_type",1);
           this.modelAddressList = (ModelAddressList) this.getIntent().getSerializableExtra("model");
           if (modelAddressList!=null){
               //编辑
           }else {
               //新增
           }
           if (jump_type==2){
               service_id=this.getIntent().getStringExtra("service_id")+"";
           }

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==111){
                if (data!=null){
                    String location_provice = data.getStringExtra("location_provice");
                    String location_city = data.getStringExtra("location_city");
                    String location_district = data.getStringExtra("location_district");

                    region_cn=location_provice+location_city+location_district;
                    String name = data.getStringExtra("name");
                    community_cn=name;
                    edt_address.setText(region_cn+community_cn+"");
                }
            }
        }
    }
}
