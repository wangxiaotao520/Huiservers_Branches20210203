package com.huacheng.huiservers.ui.vip;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.SignRulDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelSignRegister;
import com.huacheng.huiservers.model.UserIndex;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.MineFragment;
import com.huacheng.libraryservice.utils.TDevice;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 签到
 * created by wangxiaotao
 * 2020/11/24 0024 17:26
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_bottom_image)
    ImageView ivBottomImage;
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.left)
    ImageView left;
    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.lin_right)
    LinearLayout linRight;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.iv_register_tips)
    ImageView ivRegisterTips;
    @BindView(R.id.tv_register_tips)
    TextView tvRegisterTips;
    @BindView(R.id.iv_tick0)
    ImageView ivTick0;
    @BindView(R.id.iv_tick1)
    ImageView ivTick1;
    @BindView(R.id.iv_tick2)
    ImageView ivTick2;
    @BindView(R.id.iv_tick3)
    ImageView ivTick3;
    @BindView(R.id.iv_tick4)
    ImageView ivTick4;
    @BindView(R.id.iv_tick5)
    ImageView ivTick5;
    @BindView(R.id.ll_days1_6)
    LinearLayout llDays1_6;
    @BindView(R.id.iv_tick6)
    ImageView ivTick6;
    @BindView(R.id.iv_img6)
    ImageView ivImg6;
    @BindView(R.id.rl_days7)
    RelativeLayout rlDays7;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_register_points)
    TextView tv_register_points;
    private ModelSignRegister modelSignRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mStatusBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, TDevice.getStatuBarHeight(this)));

    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();

        MyOkHttp.get().post(ApiHttpClient.SIGN_INFO, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    modelSignRegister = (ModelSignRegister) JsonUtil.getInstance().parseJsonFromResponse(response, ModelSignRegister.class);
                    if (modelSignRegister !=null){
                        String day=""+ modelSignRegister.getSign_num();
                        String s = "已连续签到"+day+"天";
                        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.orange));
                        SpannableString spannableString = new SpannableString(s);
                        spannableString.setSpan(colorSpan, s.indexOf(day) , s.indexOf(day) + day.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        tvDays.setText(spannableString);
                        //循环1-6天
                        for (int i = 0; i < llDays1_6.getChildCount(); i++) {
                            RelativeLayout childAt = (RelativeLayout) llDays1_6.getChildAt(i);
                            LinearLayout childAt_ = (LinearLayout) childAt.getChildAt(0);
                            ImageView imageView = (ImageView) childAt_.getChildAt(0);
                            if (i< modelSignRegister.getSign_num()){
                                imageView.setBackgroundColor(Color.parseColor("#ffffff"));
                            }else {
                                imageView.setBackgroundColor(Color.parseColor("#ff0000"));
                            }
                        }
                        //第七天
                        if (modelSignRegister.getSign_num()==7){
                            ivTick6.setVisibility(View.VISIBLE);
                            ivImg6.setVisibility(View.GONE);
                        }else {
                            ivTick6.setVisibility(View.GONE);
                            ivImg6.setVisibility(View.VISIBLE);
                        }

                        tv_register_points.setText("积分+"+modelSignRegister.getSign_points()+"  成长值+" +modelSignRegister.getSign_rank());

                        if ("1".equals(modelSignRegister.getIs_sign())){
                            tvSign.setBackgroundResource(R.drawable.allshape_orange35_ready);
                            tvSign.setText("已签到");
                            tvSign.setClickable(false);
                        }else {
                            tvSign.setBackgroundResource(R.drawable.allshape_orange35);
                            tvSign.setText("立即签到" );
                            tvSign.setClickable(true);
                        }

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
        linLeft.setOnClickListener(this);
        ivRegisterTips.setOnClickListener(this);
        tvRegisterTips.setOnClickListener(this);
        tvSign.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
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
            case R.id.lin_left:
                finish();
                break;
            case R.id.tv_register_tips:
            case R.id.iv_register_tips:
               //签到规则
                String rule = "1.用户日常签到可获得积分10、成长值10，连续签到7天后，获得额外积分50、成长值100； \n2.用户前一天0：00-24：00前签到成功，第二日0：00-24：00进入页面并签到成功，则视为连续签到； \n3.前一天0：00-24：00前签到成功，第二日0：00-24：00未签到，则视为打断连续签到，再次进入签到页面时，记录消除，并重新开始计算。 \n4.7天为一个连续签到周期，连续签到完成一个周期后，重新开始记录。";
                if (modelSignRegister!=null){
                    rule=modelSignRegister.getSign_rule();
                    new SignRulDialog(this,rule).show();
                }


                break;
            case R.id.tv_sign:
               //点击签到
                doSignRequest();

                break;
        }
    }

    /**
     * 签到操作
     */
    private void doSignRequest() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();

        MyOkHttp.get().post(ApiHttpClient.SIGN_TO, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                if (JsonUtil.getInstance().isSuccess(response)) {
                    initData();
                    EventBus.getDefault().post(new UserIndex());
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
}
