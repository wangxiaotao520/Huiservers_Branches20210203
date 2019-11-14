package com.huacheng.huiservers.ui.center;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.CommunityListActivity;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==111){
                if (data!=null){
                    String location_provice = data.getStringExtra("location_provice");
                    String location_city = data.getStringExtra("location_city");
                    String location_district = data.getStringExtra("location_district");
                    String name = data.getStringExtra("name");
                }
            }
        }
    }
}
