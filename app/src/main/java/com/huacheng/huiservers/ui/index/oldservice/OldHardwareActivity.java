package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelHandWrist;
import com.huacheng.huiservers.model.ModelOldIndexTop;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Description:养老智能硬件 Activity
 * created by wangxiaotao
 * 2019/8/22 0022 下午 4:02
 */
public class OldHardwareActivity  extends BaseActivity{

    private RelativeLayout rel_no_data;
    private FrameLayout fl_hand_wrist;
    private ImageView img_data;
    private TextView tv_text;
    private ModelHandWrist modelHandWrist;
    private ModelOldIndexTop modelOldIndexTop;
    private int status = 0;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("智能硬件");
        rel_no_data = findViewById(R.id.rel_no_data);
        rel_no_data.setVisibility(View.GONE);
        img_data = findViewById(R.id.img_data);
        fl_hand_wrist = findViewById(R.id.fl_hand_wrist);
        tv_text = findViewById(R.id.tv_text);

        img_data.setBackgroundResource(R.mipmap.bg_oldservice_empty);
        tv_text.setText("暂时没有数据哦");
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("par_uid",modelOldIndexTop.getPar_uid());
        MyOkHttp.get().post( ApiHttpClient.GET_HANRDWARE_INFO, params, new JsonResponseHandler() {



            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                  //  SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response,"领取成功"));
                    status=1;
                    modelHandWrist = (ModelHandWrist) JsonUtil.getInstance().parseJsonFromResponse(response, ModelHandWrist.class);

                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response,"获取数据失败"));
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
        fl_hand_wrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status==1){
                    if (modelHandWrist!=null) {
                        //防走丢设备类型(1->DS-68BWS双键手环 2->E10手表) (有值说明已经绑定)

                        //判断fzdIMEI是否有值
                        if (!NullUtil.isStringEmpty(modelHandWrist.getFzdIMEI())){
                            //有值说明已绑定
                            Intent intent = new Intent(mContext, MyWristbandsActivity.class);
                            intent.putExtra("par_uid",modelOldIndexTop.getPar_uid()+"");
                            startActivity(intent);
                            finish();
                        }else {
                            //没值进入绑定页面
                            Intent intent = new Intent(mContext, OldHandWristBindActivity.class);
                            intent.putExtra("par_uid",modelOldIndexTop.getPar_uid()+"");
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        //没值进入绑定页面
                        Intent intent = new Intent(mContext, OldHandWristBindActivity.class);
                        intent.putExtra("par_uid",modelOldIndexTop.getPar_uid()+"");
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_hardware;
    }

    @Override
    protected void initIntentData() {
        modelOldIndexTop= (ModelOldIndexTop) getIntent().getSerializableExtra("model");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
