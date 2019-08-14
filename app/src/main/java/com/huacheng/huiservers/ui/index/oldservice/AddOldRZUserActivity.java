package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

import java.util.ArrayList;

/**
 * 类描述：添加子女、老人认证界面
 * 时间：2019/8/14 10:12
 * created by DFF
 */
public class AddOldRZUserActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_sf;
    private TextView tv_select_sf;
    private TextView tv_btn;
    private ArrayList<String> options1Items = new ArrayList<>();

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("身份认证");
        ll_sf = findViewById(R.id.ll_sf);
        tv_select_sf = findViewById(R.id.tv_select_sf);
        tv_btn = findViewById(R.id.tv_btn);

        options1Items.add("老人");
        options1Items.add("子女");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ll_sf.setOnClickListener(this);
        tv_btn.setOnClickListener(this);
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
            case R.id.ll_sf:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(AddOldRZUserActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1);
                        tv_select_sf.setText(tx);
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
                Intent intent=new Intent(this,OldFileActivity.class);
                startActivity(intent);
                break;
        }

    }
}
