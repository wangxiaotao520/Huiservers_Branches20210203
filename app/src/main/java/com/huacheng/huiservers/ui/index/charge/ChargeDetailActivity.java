package com.huacheng.huiservers.ui.index.charge;

import android.view.View;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelChargeDetail;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：充电记录详情
 * 时间：2019/8/18 11:56
 * created by DFF
 */
public class ChargeDetailActivity extends BaseActivity {
    private TextView tv_biaozhun, tv_gonglv, tv_time, tv_yuanyin, tv_bianhao, tv_zhandian, tv_chongdianzhuang, tv_chongdianzuo, tv_kefu;
    private TextView tv_shifu, tv_yufu, tv_tuihuan;
    String id = "";
    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("充电详情");

        tv_shifu = findViewById(R.id.tv_shifu);
        tv_yufu = findViewById(R.id.tv_yufu);
        tv_tuihuan = findViewById(R.id.tv_tuihuan);
        tv_biaozhun = findViewById(R.id.tv_biaozhun);
        tv_gonglv = findViewById(R.id.tv_gonglv);
        tv_time = findViewById(R.id.tv_time);
        tv_yuanyin = findViewById(R.id.tv_yuanyin);
        tv_bianhao = findViewById(R.id.tv_bianhao);
        tv_zhandian = findViewById(R.id.tv_zhandian);
        tv_chongdianzhuang = findViewById(R.id.tv_chongdianzhuang);
        tv_chongdianzuo = findViewById(R.id.tv_chongdianzuo);
        tv_kefu = findViewById(R.id.tv_kefu);


    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id + "");

        MyOkHttp.get().post(ApiHttpClient.PAY_CHARGE_RECORD_DETAIL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    //请求进行中的接口
                    ModelChargeDetail  modelChargeDetail = (ModelChargeDetail) JsonUtil.getInstance().parseJsonFromResponse(response, ModelChargeDetail.class);
                    if (modelChargeDetail !=null){
                        tv_shifu.setText("¥"+modelChargeDetail.getReality_price());
                        if ("0".equals(modelChargeDetail.getCancel_price())){
                            tv_tuihuan.setVisibility(View.GONE);
                        }else {
                            tv_tuihuan.setVisibility(View.VISIBLE);
                            tv_tuihuan.setText("退还金额 ¥"+modelChargeDetail.getCancel_price()+"，已实时原路退还，特殊情况受银行系统影响，会延迟至3个工作日内");
                        }
                        tv_yufu.setText("¥"+modelChargeDetail.getOrder_price());
                        tv_biaozhun.setText(""+modelChargeDetail.getPrice());
                        tv_gonglv.setText("--");
                        if (NullUtil.isStringEmpty(modelChargeDetail.getReality_times())) {
                            tv_time.setText(0+"小时");
                        }else {
                            tv_time.setText(modelChargeDetail.getReality_times()+"小时");
                        }
                        tv_yuanyin.setText(modelChargeDetail.getEnd_reason()+"");
                        tv_bianhao.setText(""+modelChargeDetail.getOrder_number());
                        tv_zhandian.setText(modelChargeDetail.getGtel_cn()+"");
                        tv_chongdianzhuang.setText(modelChargeDetail.getGtel()+"");
                        tv_chongdianzuo.setText(modelChargeDetail.getTd()+"");
                        tv_kefu.setText(modelChargeDetail.getSys_tel()+"");

                    }else {
                        SmartToast.showInfo("数据解析失败");
                    }
                } else {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
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

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_detail;
    }

    @Override
    protected void initIntentData() {
        id = this.getIntent().getStringExtra("id");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
