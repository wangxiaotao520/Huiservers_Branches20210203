package com.huacheng.huiservers.ui.index.oldservice;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 手环绑定页面
 * created by wangxiaotao
 * 2020/9/30 0030 10:12
 */
public class OldHandWristBindActivity extends BaseActivity {
    @BindView(R.id.iv_selected_wrist)
    ImageView ivSelectedWrist;
    @BindView(R.id.rl_wrist)
    RelativeLayout rlWrist;
    @BindView(R.id.iv_selected_watch)
    ImageView ivSelectedWatch;
    @BindView(R.id.rl_watch)
    RelativeLayout rlWatch;
    @BindView(R.id.ll_hardware_container)
    LinearLayout llHardwareContainer;
    @BindView(R.id.et_ID)
    EditText etID;
    @BindView(R.id.ly_old_ID)
    LinearLayout lyOldID;
    @BindView(R.id.et_sim)
    EditText etSim;
    @BindView(R.id.ly_old_SIM)
    LinearLayout lyOldSIM;
    @BindView(R.id.tv_comfirm)
    TextView tvComfirm;
    private String fzdType= "1";//1->DS-68BWS双键手环 2->E10手表
    private String fzdIMEI="";
    private String fzdSIM="";
    private String par_uid="";

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("绑定设备");
    }

    @Override
    protected void initData() {
        //默认
        ivSelectedWrist.setImageResource(true?R.mipmap.ic_selected_pay_type:R.drawable.shape_oval_grey);
        ivSelectedWatch.setImageResource(false?R.mipmap.ic_selected_pay_type:R.drawable.shape_oval_grey);
        fzdType="1";
    }

    @Override
    protected void initListener() {
        rlWrist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //手环
                fzdType="1";
                ivSelectedWrist.setImageResource(true?R.mipmap.ic_selected_pay_type:R.drawable.shape_oval_grey);
                ivSelectedWatch.setImageResource(false?R.mipmap.ic_selected_pay_type:R.drawable.shape_oval_grey);
            }
        });
        rlWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //手表
                fzdType="2";
                ivSelectedWrist.setImageResource(false?R.mipmap.ic_selected_pay_type:R.drawable.shape_oval_grey);
                ivSelectedWatch.setImageResource(true?R.mipmap.ic_selected_pay_type:R.drawable.shape_oval_grey);
            }
        });
        tvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkReady()) {
                   commit();
                }
            }
        });
    }

    private void commit() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("par_uid",par_uid+"");
        params.put("fzdType",fzdType+"");
        params.put("fzdIMEI",fzdIMEI+"");
        params.put("fzdSIM",fzdSIM+"");
        MyOkHttp.get().post( ApiHttpClient.HANRDWARE_BINDING, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                   SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response,"操作成功"));
                    //到时候返回par_uid
                    Intent intent = new Intent(mContext, MyWristbandsActivity.class);
                    intent.putExtra("par_uid",par_uid+"");
                    startActivity(intent);
                   finish();

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

    private boolean checkReady() {
        fzdIMEI = etID.getText().toString().trim();
        if (NullUtil.isStringEmpty(fzdIMEI)){
            SmartToast.showInfo("请输入设备ID");
            return false;
        }
        fzdSIM = etSim.getText().toString().trim();
        if (NullUtil.isStringEmpty(fzdSIM)){
            SmartToast.showInfo("请输入插入设备SIM卡手机号");
            return false;
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_old_handwrist_bind;
    }

    @Override
    protected void initIntentData() {
         par_uid = getIntent().getStringExtra("par_uid");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

}
