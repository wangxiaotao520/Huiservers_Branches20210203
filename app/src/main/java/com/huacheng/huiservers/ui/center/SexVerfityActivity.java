package com.huacheng.huiservers.ui.center;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.model.PersoninfoBean;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

public class SexVerfityActivity extends BaseActivity implements OnClickListener {

    LinearLayout lin_left;
    TextView title_name, right;
    RadioGroup radiogroup;
    RadioButton radio_nan, radio_nv;
    String currentSelectedVal;
    private String sex="";
    private int type=1;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right:
                if (!currentSelectedVal.equals("")) {
                    getMyinfo(currentSelectedVal);
                    /*Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("sex", currentSelectedVal);
                    intent.putExtras(bundle);
                    setResult(3, intent);
                    finish();*/
                } else {
                    SmartToast.showInfo("性别为空");
                }
                break;
            case R.id.lin_left:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 修改个人信息
     *
     * @param param
     */
    private void getMyinfo(final String param) {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);

        RequestParams params = new RequestParams();
        params.addBodyParameter("sex", param);

        MyOkHttp.get().post(info.edit_center, params.getParams(), new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hideDialog(smallDialog);
                ShopProtocol protocol = new ShopProtocol();
                String str = protocol.setShop(response);
                if (str.equals("1")) {
                    EventBus.getDefault().post(new PersoninfoBean());
                    finish();
                } else {
                    SmartToast.showInfo(str);
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
    protected void initView() {
        title_name = (TextView) findViewById(R.id.title_name);
        right = (TextView) findViewById(R.id.right);
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        radio_nan = (RadioButton) findViewById(R.id.radio_nan);
        radio_nv = (RadioButton) findViewById(R.id.radio_nv);
        // set
        if (type==1){
            title_name.setText("性别");
            radio_nan.setText("男");
            radio_nv.setText("女");
        }else {
            title_name.setText("居住状态");
            radio_nan.setText("自有房屋");
            radio_nv.setText("租房");
        }
        right.setTextColor(getResources().getColor(R.color.orange));
        right.setText("提交");
        right.setVisibility(View.VISIBLE);

        final Drawable drawableCheck = getResources().getDrawable(R.mipmap.ic_selected_pay_type);
        final Drawable drawableunCheck = getResources().getDrawable(R.drawable.shape_oval_grey);
        drawableCheck.setBounds(0, 0, drawableCheck.getMinimumWidth(), drawableCheck.getMinimumHeight());  //
        drawableunCheck.setBounds(0, 0, drawableunCheck.getMinimumWidth(), drawableunCheck.getMinimumHeight());  //
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rb = (RadioButton) findViewById(arg0.getCheckedRadioButtonId());

                    if (rb.getText().toString().equals("男")||rb.getText().toString().equals("自有房屋")) {
                        currentSelectedVal = "1";
                        radio_nan.setCompoundDrawables(null, null, drawableCheck, null);
                        radio_nv.setCompoundDrawables(null, null, drawableunCheck, null);
                    } else  if (rb.getText().toString().equals("女")||rb.getText().toString().equals("租房")){
                        currentSelectedVal = "2";
                        radio_nv.setCompoundDrawables(null, null, drawableCheck, null);
                        radio_nan.setCompoundDrawables(null, null, drawableunCheck, null);
                    }

            }
        });
        //getExtra
        if (!sex.equals("")) {
                if (sex.equals("男")||sex.equals("只有房屋")) {
                    radio_nan.setChecked(true);
                    radio_nan.setCompoundDrawables(null, null, drawableCheck, null);
                    radio_nv.setCompoundDrawables(null, null, drawableunCheck, null);
                } else if (sex.equals("女")||sex.equals("租房")) {
                    radio_nv.setChecked(true);
                    radio_nv.setCompoundDrawables(null, null, drawableCheck, null);
                    radio_nan.setCompoundDrawables(null, null, drawableunCheck, null);
                }
        } else {
            radio_nan.setCompoundDrawables(null, null, drawableunCheck, null);
            radio_nv.setCompoundDrawables(null, null, drawableunCheck, null);
        }
        // listener
        findViewById(R.id.lin_left).setOnClickListener(this);
        right.setOnClickListener(this);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.verify_sex_new;
    }

    @Override
    protected void initIntentData() {
        sex = this.getIntent().getStringExtra("sex");
        type = this.getIntent().getIntExtra("type",1);
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }
}
