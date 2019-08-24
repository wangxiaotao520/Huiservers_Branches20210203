package com.huacheng.huiservers.ui.index.charge;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelChargeDetail;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.littlejie.circleprogress.DialProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:充电中
 * created by wangxiaotao
 * 2019/8/19 0019 下午 6:06
 */
public class ChargingActivity  extends BaseActivity implements View.OnClickListener {
    View mStatusBar;
    private LinearLayout lin_left;
    private DialProgress mDialProgress;
    private Timer timer;
    private boolean isMax=false;
    long time = 0; //充了总时间
    long preTime = 0;
    TimerTask task = new TimerTask(){
        public void run(){
            // 在此处添加执行的代码
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    if (isMax){
//                        mDialProgress.setValue(1);
//
//                    }else {
//                        mDialProgress.setValue(mDialProgress.getMaxValue());
//                    }
                    isMax=!isMax;

                    mDialProgress.startAnimator(0,1,2000);
                }
            });
        }
    };

    final Handler handler = new Handler(){

        public void handleMessage(Message msg){         // handle message

                        Message message = handler.obtainMessage(1);
                        handler.sendMessageDelayed(message, 1000);      // send message

            time=time+(System.currentTimeMillis()-preTime);
            tv_count_up.setText(StringUtils.changeTime2((int)time/1000));
            preTime=System.currentTimeMillis();
            super.handleMessage(msg);
            //todo 充电结束了  当前时间>结束时间
//            if (modelChargeDetail!=null){
//                if (System.currentTimeMillis()>modelChargeDetail.getEndtime()*1000){
//                    if (handler!=null){
//                        handler.removeMessages(1);
//                    }
//                    if (dialog_notice!=null){
//                        dialog_notice.dismiss();
//                    }
//                }
//
//                Intent intent=new Intent(ChargingActivity.this,ChargeDetailActivity.class);
//                intent.putExtra("id",id+"");
//                startActivity(intent);
//                SmartToast.showInfo("充电结束");
//                finish();
//            }
        }
    };
    private TextView tv_count_up;  //计时显示
    private TextView tv_kefu;
    private TextView tv_price;
    private TextView tv_time_long;
    private TextView tv_finish_btn;
    private TextView tv_equip_id; //充电桩编号
    private String id;
    private String jump_from;
    private ModelChargeDetail modelChargeDetail;
    private CommomDialog dialog_notice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mStatusBar=findViewById(R.id.status_bar);
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));
        mStatusBar.setAlpha((float)1);
        lin_left = findViewById(R.id.lin_left);
        mDialProgress=findViewById(R.id.dial_progress_bar);
        tv_count_up = findViewById(R.id.tv_count_up);

        tv_kefu = findViewById(R.id.tv_kefu);
        tv_kefu.setClickable(false);
        tv_price = findViewById(R.id.tv_price);
        tv_time_long = findViewById(R.id.tv_time_long);
        tv_finish_btn = findViewById(R.id.tv_finish_btn);
        tv_finish_btn.setClickable(false);
        tv_equip_id = findViewById(R.id.tv_equip_id);

        dialog_notice= new CommomDialog(this, R.style.my_dialog_DimEnabled, "请确定电源已接入充电设备？", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                   dialog.dismiss();
                   getInformation();
                }else {
                    finish();
                }
            }
        });
        dialog_notice.setCanceledOnTouchOutside(false);
        dialog_notice.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if ( keyCode == KeyEvent.KEYCODE_BACK
                        && event.getRepeatCount() == 0) {
                    dialog.dismiss();
                    finish();
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        if ("pay".equals(jump_from)){
            openCharge();
        }else {
            getInformation();
        }

    }

    /**
     * 开启充电通道
     */
    private void openCharge() {

        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id + "");

        MyOkHttp.get().post(ApiHttpClient.PAY_CHARGE_START, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                        //请求进行中的接口
                        getInformation();
//                    try {
//                        SmartToast.showInfo(response.getString("msg"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                } else {
                    hideDialog(smallDialog);
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

    /**
     * 获取详情
     */
    private void getInformation() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id + "");

        MyOkHttp.get().post(ApiHttpClient.PAY_CHARGE_ING, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    //请求进行中的接口
                    modelChargeDetail = (ModelChargeDetail) JsonUtil.getInstance().parseJsonFromResponse(response, ModelChargeDetail.class);
                    if (modelChargeDetail !=null){
                        tv_price.setText(modelChargeDetail.getOrder_price()+"");
                        tv_time_long.setText(""+ modelChargeDetail.getTimes());
                        tv_equip_id.setText("充电桩："+ modelChargeDetail.getGtel()+"；"+ modelChargeDetail.getTd()+"号插座");
                        //start时间如果是0 则证明用户没有将插座插进去
                        if (modelChargeDetail.getStart_time()==0) {
                            showDialog(dialog_notice);
                            mDialProgress.setValue(0);
                        }else {
                            //开启倒计时及动画
                            tv_finish_btn.setClickable(true);
                            tv_kefu.setClickable(true);
                            if (timer!=null){
                                timer.cancel();
                            }
                            if (handler!=null){
                                handler.removeMessages(1);
                            }
                            time=System.currentTimeMillis()- modelChargeDetail.getStart_time()*1000;
                            isMax=true;
                            mDialProgress.setValue(mDialProgress.getMaxValue());
                            timer=new Timer();
                            timer.schedule(task,2500,2200);
                            //计时
                            Message message = handler.obtainMessage(1);
                            handler.sendMessageDelayed(message,1000);
                            tv_count_up.setText(StringUtils.changeTime2((int)time/1000));
                            preTime= System.currentTimeMillis();
                        }

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
        lin_left.setOnClickListener(this);
        tv_kefu.setOnClickListener(this);
        tv_finish_btn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charging;
    }

    @Override
    protected void initIntentData() {
        id = this.getIntent().getStringExtra("id");
        jump_from = this.getIntent().getStringExtra("jump_from");
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
            case R.id.lin_left:
                finish();
                break;
            case R.id.tv_kefu:
                //联系客服
                new CommomDialog(this, R.style.my_dialog_DimEnabled, "确认拨打电话？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"
                                    +modelChargeDetail.getSys_tel() ));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }
                }).show();//.setTitle("提示")
                break;
            case R.id.tv_finish_btn:
               //结束充电
                endCharge();
                break;
        }
    }

    /**
     * 结束充电
     */
    private void endCharge() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id + "");

        MyOkHttp.get().post(ApiHttpClient.PAY_CHARGE_END, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    //请求进行中的接口
                    Intent intent=new Intent(ChargingActivity.this,ChargeDetailActivity.class);
                    intent.putExtra("id",id+"");
                    startActivity(intent);
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    finish();
                } else {
                    hideDialog(smallDialog);
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
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
        if (handler!=null){
            handler.removeMessages(1);
        }
        if (dialog_notice!=null){
            dialog_notice.dismiss();
        }
    }

}
