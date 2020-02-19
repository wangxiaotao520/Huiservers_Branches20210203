package com.huacheng.huiservers.ui.index.coronavirus;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.ZXingUtils;

/**
 * Description: 生成门禁二维码Activity
 * created by wangxiaotao
 * 2020/2/17 0017 下午 6:42
 */
public class QrImageActivity extends BaseActivity {


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //todo 测试
        String str = "dsafsafsafsafsafsafewqtqyhehrh";
        Bitmap qrImage = ZXingUtils.createQRImage(str, 800, 800);
        ImageView iv_qr_code = findViewById(R.id.iv_qr_code);
        iv_qr_code.setImageBitmap(qrImage);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_image;
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
