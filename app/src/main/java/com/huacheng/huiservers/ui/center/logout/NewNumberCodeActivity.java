package com.huacheng.huiservers.ui.center.logout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.utils.TextCheckUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.umeng.socialize.sina.helper.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * 类描述：验证新号码
 * 时间：2020/5/8 09:06
 * created by DFF
 */
public class NewNumberCodeActivity extends BaseActivity implements View.OnClickListener {
    private String phone = "";
    private TextView tv_old_number;
    private EditText edit_code;
    private TextView tv_next;
    private TextView tv_send_code;
    private Message msg;
    private String timestamp;
    private String jmStr;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    new MyCount(60000, 1000).start();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initView() {

        findTitleViews();
        tv_old_number = findViewById(R.id.tv_old_number);
        edit_code = findViewById(R.id.edit_code);
        tv_next = findViewById(R.id.tv_next);
        tv_send_code = findViewById(R.id.tv_send_code);
        tv_old_number.setText("新手机号：" + phone + "，绑定新手机号后下次登录可使用新手机号登录");

        //1、传入需要监听的EditText与TextView
        TextCheckUtils textCheckUtils = new TextCheckUtils(edit_code);
        //2、设置是否全部填写完成监听
        textCheckUtils.setOnCompleteListener(new TextCheckUtils.OnCompleteListener() {
            @Override
            public void isComplete(boolean isComplete) {
                if (isComplete) {
                    tv_next.setEnabled(true);
                    tv_next.setBackgroundResource(R.drawable.allshape_orange);

                } else {
                    tv_next.setEnabled(false);
                    tv_next.setBackgroundResource(R.drawable.allshape_gray_solid_bb5);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_next.setOnClickListener(this);
        tv_send_code.setOnClickListener(this);
    }

    //发送验证码接口
    private void getcode(String timestamp, String jmStr) {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("username", phone + "");
        params.put("sms_type", "login");
        params.put("ApiSmstokentime", timestamp);// 时间戳
        params.put("ApiSmstoken", jmStr);// 加密字符串
        MyOkHttp.get().post(info.reg_send_sms, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                try {
                    String str = response.getString("status");
                    String json = response.getString("msg");
                    if (str.equals("1")) {
                        SmartToast.showInfo(json);
                        msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } else {
                        tv_send_code.setEnabled(true);
                        SmartToast.showInfo(json);
                        System.out.println("json--------" + json);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络错误,请检查网络设置");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_new_number_code;
    }

    @Override
    protected void initIntentData() {
        phone = this.getIntent().getStringExtra("phone");
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
            case R.id.tv_send_code://发送验证码
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL url = null;//取得资源对象
                        try {
                            url = new URL("http://www.baidu.com");
                            URLConnection uc = url.openConnection();//生成连接对象
                            uc.connect(); //发出连接
                            long ld = uc.getDate() / 1000; //取得网站日期时间、
                            timestamp = String.valueOf(ld);
                            //获取加密字符串
                            String ttttt = MD5.hexdigest("hui-shenghuo.api_sms").substring(8, 24);
                            jmStr = MD5.hexdigest(timestamp + ttttt + timestamp).substring(0, 16);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getcode(timestamp, jmStr);
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.tv_next:

                getconfirm();

                break;
        }
    }

    private void getconfirm() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("username", phone + "");
        params.put("mobile_vcode", edit_code.getText().toString().trim() + "");

        MyOkHttp.get().post(ApiHttpClient.VERITY_OPERATION, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    Intent intent = new Intent(NewNumberCodeActivity.this, NumberSuccessActivity.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });

    }

    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tv_send_code.setText("重新发送");
            tv_send_code.setEnabled(true);
            tv_send_code.setOnClickListener(NewNumberCodeActivity.this);
            tv_send_code.setTextColor(getResources().getColor(R.color.colorPrimary));
            // tv_send_code.setBackground(getResources().getDrawable(R.drawable.cornes_bgwhite_edge_orange));
          /*  txt_btn.setText("登录");
            txt_btn.setTextColor(getResources().getColor(R.color.white));
            txt_btn.setBackground(getResources().getDrawable(R.drawable.allshape_orange_10));
            txt_btn.setEnabled(true);*/
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished / 1000 >= 1) {
                tv_send_code.setEnabled(false);
            }
            tv_send_code.setTextColor(getResources().getColor(R.color.title_third_color));
            //tv_send_code.setBackground(getResources().getDrawable(R.drawable.cornes_bgcode_login_gray));
            tv_send_code.setText(millisUntilFinished / 1000 + "s");
        }

    }

}
