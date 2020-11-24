package com.huacheng.huiservers.ui.vip;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.MyActivity;
import com.huacheng.huiservers.util.DialogUtil;

/**
 * @author Created by changyadong on 2020/11/23
 * @description
 */
public class QRCodeActivity extends MyActivity {


    ImageView qrcodeImg;
    @Override
    protected int getLayoutId() {
        return  R.layout.activity_qrcode;
    }

    @Override
    protected void initView() {
        back();
        title("我的二维码");
        qrcodeImg = findViewById(R.id.qrcode);

        findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.bottomList(mContext, new String[]{"保存到手机","重置二维码"}, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }).show();
            }
        });
    }
}
