package com.huacheng.huiservers.ui.center;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.CommunityListActivity;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

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

    @Override
    protected void initView() {
        findTitleViews();
        tv_right=findViewById(R.id.txt_right1);
        tv_right.setText("保存");
        tv_right.setVisibility(View.VISIBLE);
        titleName.setText("添加地址");
        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_address = findViewById(R.id.edt_address);
        ll_address = findViewById(R.id.ll_address);
        edt_address_detail = findViewById(R.id.edt_address_detail);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(mContext, CommunityListActivity.class);
                startActivityForResult(intent1, 111);
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
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
}
