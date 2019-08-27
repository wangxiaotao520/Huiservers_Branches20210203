package com.huacheng.huiservers.ui.index.oldservice;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelEventOld;
import com.huacheng.huiservers.model.ModelOldInst;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：添加子女、老人认证界面
 * 时间：2019/8/14 10:12
 * created by DFF
 */
public class AddOldRZUserActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_select_sf;
    private TextView tv_btn, tv_jigou;
    private ArrayList<String> options1Items = new ArrayList<>(); //老人 子女
    private ArrayList<String> options1Item2 = new ArrayList<>();//机构
    private int selected_options1 = 0;
    private LinearLayout ly_sf, ly_chlid_info, ly_old_info, ly_jigou, ly_old_name, ly_SF_ID, ly_child_phone, ly_old_chengwei;
    private EditText et_old_name, et_sf_ID , et_child_phone, et_old_chengwei;

    private int type = 1;  //1是老人 2是子女
    private List<ModelOldInst> mDatas_inst = new ArrayList<>();//机构数据
    private String i_id = "";
    private String o_company_id = "";

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("身份认证");
        ly_sf = findViewById(R.id.ly_sf);

        tv_select_sf = findViewById(R.id.tv_select_sf);
        tv_btn = findViewById(R.id.tv_btn);
        ly_chlid_info = findViewById(R.id.ly_chlid_info);
        //选择老人显示的布局
        ly_old_info = findViewById(R.id.ly_old_info);
        ly_jigou = findViewById(R.id.ly_jigou);
        tv_jigou = findViewById(R.id.tv_jigou);
        ly_old_name = findViewById(R.id.ly_old_name);
        et_old_name = findViewById(R.id.et_old_name);
        ly_SF_ID = findViewById(R.id.ly_SF_ID);
        et_sf_ID = findViewById(R.id.et_sf_ID);
        //选择子女显示的布局

        ly_child_phone = findViewById(R.id.ly_child_phone);
        et_child_phone = findViewById(R.id.et_child_phone);//子女填写老人手机号
        ly_old_chengwei = findViewById(R.id.ly_old_chengwei);
        et_old_chengwei = findViewById(R.id.et_old_chengwei);
        //默认选中老人
        tv_select_sf.setText("老人");

        options1Items.add("老人");
        options1Items.add("子女");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ly_sf.setOnClickListener(this);
        tv_btn.setOnClickListener(this);
        ly_jigou.setOnClickListener(this);
        ly_old_name.setOnClickListener(this);
        ly_SF_ID.setOnClickListener(this);
        // ly_old_phobe.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_old_rz_user;
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
        switch (v.getId()) {
            case R.id.ly_sf:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(AddOldRZUserActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1);
                        tv_select_sf.setText(tx);
                        if (tv_select_sf.getText().toString().equals("")) {
                            ly_old_info.setVisibility(View.GONE);
                            ly_chlid_info.setVisibility(View.VISIBLE);
                        } else if (tv_select_sf.getText().toString().equals("老人")) {
                            ly_old_info.setVisibility(View.VISIBLE);
                            ly_chlid_info.setVisibility(View.GONE);
                        } else {
                            ly_old_info.setVisibility(View.GONE);
                            ly_chlid_info.setVisibility(View.VISIBLE);
                        }
                        if (options1==0){
                            type = 1;//老人
                        }else {
                            type = 2; //子女
                        }

                    }
                }).setTitleText("请选择")//标题文字
                        .setTitleColor(this.getResources().getColor(R.color.blackgray))
                        .setSubmitColor(this.getResources().getColor(R.color.orange_bg))//确定按钮文字颜色
                        .setCancelColor(this.getResources().getColor(R.color.text_color))
                        .setContentTextSize(18).build();//取消按钮文字颜色;
                pvOptions.setPicker(options1Items);
                pvOptions.show();

                break;
            case R.id.tv_btn:
                if (NullUtil.isStringEmpty(tv_select_sf.getText().toString())) {
                    SmartToast.showInfo("请选择身份");
                    return;
                }
                if (tv_select_sf.getText().toString().equals("老人")) {
                    //老人
                    if (NullUtil.isStringEmpty(tv_jigou.getText().toString())) {
                        SmartToast.showInfo("请选择机构");
                        return;
                    }
                    if (NullUtil.isStringEmpty(et_old_name.getText().toString())) {
                        SmartToast.showInfo("请输入姓名");
                        return;
                    }
                    if (NullUtil.isStringEmpty(et_sf_ID.getText().toString())) {
                        SmartToast.showInfo("请输入身份证号");
                        return;
                    }
                /*    if (NullUtil.isStringEmpty(et_phone.getText().toString())) {
                        SmartToast.showInfo("请输入社区慧生活绑定账号");
                        return;
                    }*/
                requestDataAuth();

                } else {

                    if (NullUtil.isStringEmpty(et_child_phone.getText().toString())) {
                        SmartToast.showInfo("请输入老人手机号");
                        return;
                    }
                    if (NullUtil.isStringEmpty(et_old_chengwei.getText().toString())) {
                        SmartToast.showInfo("请输入对老人的称谓");
                        return;
                    }
                    requestDataAuth();

                }
                break;
            case R.id.ly_jigou:
                if (NullUtil.isStringEmpty(tv_select_sf.getText().toString())) {
                    SmartToast.showInfo("请选择身份");
                    return;
                }
                requestInst();
                break;
            case R.id.ly_old_name:
                if (NullUtil.isStringEmpty(tv_select_sf.getText().toString())) {
                    SmartToast.showInfo("请选择身份");
                    return;
                }
                break;
            case R.id.ly_SF_ID:
                if (NullUtil.isStringEmpty(tv_select_sf.getText().toString())) {
                    SmartToast.showInfo("请选择身份");
                    return;
                }
                break;
           /* case R.id.ly_old_phobe:
                if (NullUtil.isStringEmpty(tv_select_sf.getText().toString())) {
                    SmartToast.showInfo("请选择身份");
                    return;
                }
                break;*/
            default:
                break;

        }

    }

    /**
     * 机构
     */
    private void requestInst() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        MyOkHttp.get().post(ApiHttpClient.PENSION_INST_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    List <ModelOldInst>data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelOldInst.class);
                    mDatas_inst.clear();
                    options1Item2.clear();
                    mDatas_inst.addAll(data);
                    for (int i = 0; i < mDatas_inst.size(); i++) {
                        options1Item2.add(mDatas_inst.get(i).getI_name()+"");
                    }
                    showInstDialog();
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
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

    /**
     * 机构列表
     */
    private void showInstDialog() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(AddOldRZUserActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Item2.get(options1);
                tv_jigou.setText(tx);
                i_id=mDatas_inst.get(options1).getI_id()+"";
                o_company_id=mDatas_inst.get(options1).getO_company_id()+"";
                selected_options1=options1;

            }
        }).setTitleText("请选择")//标题文字
                .setTitleColor(this.getResources().getColor(R.color.blackgray))
                .setSubmitColor(this.getResources().getColor(R.color.orange_bg))//确定按钮文字颜色
                .setCancelColor(this.getResources().getColor(R.color.text_color))
                .setContentTextSize(18).setSelectOptions(selected_options1).build();//取消按钮文字颜色;
        pvOptions.setPicker(options1Item2);
        pvOptions.show();
    }

    /**
     * 请求
     */
    private void requestDataAuth() {
        if (type ==1){
            requestOldAuth();
        }else {
            requestChildAuth();
        }
    }

    /**
     * 子女认证老人
     */
    private void requestChildAuth() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("type","2");
        String phone = et_child_phone.getText().toString().trim();
        params.put("username",phone+"");
        String call = et_old_chengwei.getText().toString().trim();
        params.put("call",call+"");

        MyOkHttp.get().post(ApiHttpClient.PENSION_RELATION_ADD, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "成功");
                    SmartToast.showInfo(msg);
                    finish();
                } else {
                    try {
                        int status = response.getInt("status");
                        if (status==3){
                            String msg = JsonUtil.getInstance().getMsgFromResponse(response, "");
                            new CommomDialog(AddOldRZUserActivity.this, R.style.my_dialog_DimEnabled, msg, new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    if (confirm){
                                        startActivity(new Intent(AddOldRZUserActivity.this,OldMessageActivity.class));
                                    }
                                    dialog.dismiss();
                                }
                            }).setPositiveButton("去看看").show();//.setTitle("提示")

                        }else {
                            String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                            SmartToast.showInfo(msg);
                        }
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

    /**
     * 老人认证
     */
    private void requestOldAuth() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("i_id",i_id+"");
        params.put("o_company_id",o_company_id+"");
        String name = et_old_name.getText().toString().trim();
        params.put("name",name+"");
        String idcard = et_sf_ID.getText().toString().trim();
        params.put("idcard",idcard+"");

        MyOkHttp.get().post(ApiHttpClient.PENSION_OLD_AUDIT, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "成功");
                    SmartToast.showInfo(msg);
                    EventBus.getDefault().post(new ModelEventOld());
                    finish();
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
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
}
