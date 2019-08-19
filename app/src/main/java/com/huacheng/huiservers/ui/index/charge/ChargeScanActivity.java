package com.huacheng.huiservers.ui.index.charge;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.servicenew.ui.scan.CustomCaptureActivity;
import com.huacheng.huiservers.view.ShadowLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Description: 慧充电
 * created by wangxiaotao
 * 2019/8/19 0019 上午 8:51
 */
public class ChargeScanActivity extends BaseActivity implements View.OnClickListener{
    private ChargeEquipNumberDialog dialog;
    private SimpleDraweeView sdv_title;
    private ShadowLayout sl_message;
    private ShadowLayout sl_charge_record;
    private LinearLayout ll_scan;
    private TextView tv_equip_num;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("慧充电");
        sdv_title = findViewById(R.id.sdv_title);
        sl_message = findViewById(R.id.sl_message);
        sl_charge_record = findViewById(R.id.sl_charge_record);
        ll_scan = findViewById(R.id.ll_scan);
        tv_equip_num = findViewById(R.id.tv_equip_num);
        dialog = new ChargeEquipNumberDialog(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        sl_message.setOnClickListener(this);
        sl_charge_record.setOnClickListener(this);
        ll_scan.setOnClickListener(this);
        tv_equip_num.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_scan;
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
        switch (v.getId()){
            case R.id.sl_message:
                //消息中心
                break;
            case R.id.sl_charge_record:
                //充电记录
                break;
            case R.id.ll_scan:
                //扫一扫
                scan();
                break;
            case R.id.tv_equip_num:
                //设备编号
                showNumberDialog();
                break;

        }
    }

    /**
     * 扫一扫
     */
    private void scan() {
        new RxPermissions(this).request( Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isGranted) throws Exception {
                        if (isGranted) {
                            IntentIntegrator intentIntegrator = new IntentIntegrator(ChargeScanActivity.this)
                                    .setOrientationLocked(false);
                            intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
                        /*intentIntegrator.setPrompt("将服务师傅的二维码放入框内\n" +
                            "即可扫描付款");*/
                            // 设置自定义的activity是ScanActivity
                            intentIntegrator.initiateScan(); // 初始化扫描
                        } else {

                        }
                    }
                });;

    }

    /**
     * 输入设备编号
     */
    private void showNumberDialog() {
        if (dialog!=null){
            dialog.setCancelable(true);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            showDialog(dialog);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 49374) {//二维码扫描返回（自己打断点试出来的）
                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (intentResult != null) {
                    if (intentResult.getContents() == null) {
                        SmartToast.showInfo("内容为空");
                    } else {
                        String ScanResult = intentResult.getContents();


                    }
                }
            } else {

            }
        }

    }
}
