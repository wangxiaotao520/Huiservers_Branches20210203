package com.huacheng.huiservers.ui.index.oldservice;

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
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;

import java.util.ArrayList;

/**
 * 类描述：添加子女、老人认证界面
 * 时间：2019/8/14 10:12
 * created by DFF
 */
public class AddOldRZUserActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_select_sf;
    private TextView tv_btn, tv_jigou;
    private ArrayList<String> options1Items = new ArrayList<>();
    private LinearLayout ly_sf, ly_chlid_info, ly_old_info, ly_jigou, ly_old_name, ly_SF_ID, ly_old_phobe, ly_child_name, ly_child_phone, ly_old_chengwei;
    private EditText et_old_name, et_sf_ID, et_phone, et_child_name, et_child_phone, et_old_chengwei;


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
        ly_old_phobe = findViewById(R.id.ly_old_phobe);
        et_phone = findViewById(R.id.et_phone);
        //选择子女显示的布局
        ly_child_name = findViewById(R.id.ly_child_name);
        et_child_name = findViewById(R.id.et_child_name);
        ly_child_phone = findViewById(R.id.ly_child_phone);
        et_child_phone = findViewById(R.id.et_child_phone);//子女填写老人手机号
        ly_old_chengwei = findViewById(R.id.ly_old_chengwei);
        et_old_chengwei = findViewById(R.id.et_old_chengwei);

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
        ly_old_phobe.setOnClickListener(this);
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
                    if (NullUtil.isStringEmpty(et_phone.getText().toString())) {
                        SmartToast.showInfo("请输入社区慧生活绑定账号");
                        return;
                    }

                } else {
                    if (NullUtil.isStringEmpty(et_child_name.getText().toString())) {
                        SmartToast.showInfo("请输入姓名");
                        return;
                    }
                    if (NullUtil.isStringEmpty(et_child_phone.getText().toString())) {
                        SmartToast.showInfo("请输入老人手机号");
                        return;
                    }
                    if (NullUtil.isStringEmpty(et_old_chengwei.getText().toString())) {
                        SmartToast.showInfo("请输入对老人的称谓");
                        return;
                    }

                }
                Intent intent = new Intent(this, OldFileActivity.class);
                startActivity(intent);
                break;
            case R.id.ly_jigou:
                if (NullUtil.isStringEmpty(tv_select_sf.getText().toString())) {
                    SmartToast.showInfo("请选择身份");
                    return;
                }
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
            case R.id.ly_old_phobe:
                if (NullUtil.isStringEmpty(tv_select_sf.getText().toString())) {
                    SmartToast.showInfo("请选择身份");
                    return;
                }
                break;
            default:
                break;

        }

    }
}
