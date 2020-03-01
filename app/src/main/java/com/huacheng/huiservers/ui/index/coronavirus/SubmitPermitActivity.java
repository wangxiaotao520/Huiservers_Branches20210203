package com.huacheng.huiservers.ui.index.coronavirus;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelPermit;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述：提交申请通行证
 * 时间：2020/2/25 11:14
 * created by DFF
 */
public class SubmitPermitActivity extends BaseActivity {
    @BindView(R.id.tv_house)
    TextView mTvHouse;
    @BindView(R.id.ly_select_house)
    LinearLayout mLySelectHouse;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_shenfen_num)
    EditText mEtShenfenNum;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_car_num)
    EditText mEtCarNum;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_shenhe_status)
    TextView mTvShenheStatus;
    @BindView(R.id.txt_btn)
    TextView mTxtBtn;

    private int page = 1;
    ModelPermit permitInfo;
    private String company_id;
    private String id;
    private String status;//列表跳转过来的 1为不需要审核；2为需要审核 3是详情跳过来的重新审核
    //private int type;//区分是首页提交，重新提交，审核中

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("通行证申请");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    /**
     * 请求数据
     */
    private void requestData() {
        // 根据接口请求数据
        HashMap<String, String> params = new HashMap<>();
        params.put("pc_id", id + "");
        params.put("company_id", company_id + "");
        params.put("p", page + "");

        MyOkHttp.get().get(ApiHttpClient.GET_PERMIT_DETAIL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                // ly_share.setClickable(true);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelPermit modelPermit = (ModelPermit) JsonUtil.getInstance().parseJsonFromResponse(response, ModelPermit.class);
                    // List<ModelShopIndex> shopIndexList = (List<ModelShopIndex>) JsonUtil.getInstance().getDataArrayByName(response, "data", ModelShopIndex.class);
                    if (modelPermit != null) {
                        permitInfo = modelPermit;
                        inflateContent(permitInfo);
                    }
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                // ly_share.setClickable(false);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    private void inflateContent(ModelPermit modelPermit) {

// TODO: 2020/2/29 根据status 判断通行证是待审核还是已拒绝

        if ("1".equals(modelPermit.getStatus())) {//待审核
            mTxtBtn.setText("审核中");

        } else if ("3".equals(modelPermit.getStatus())) {//已拒绝
            mTxtBtn.setText("重新提交");
        } else {//首次提交

        }
/*
        if (type == 0) {//首次提交

        } else if (type == 1) {//重新提交

        } else {//审核中
            mTvHouse.setFocusable(false);
            mEtName.setFocusable(false);
            mEtShenfenNum.setFocusable(false);
            mEtPhone.setFocusable(false);
            mEtCarNum.setFocusable(false);
            mEtAddress.setFocusable(false);
        }*/

       /* if ("1".equals(modelPermit.getType())) {
            mTvStatus.setText("临时通行证");
            //没有来访事由
            mLyFangkeContent.setVisibility(View.GONE);

        } else if ("2".equals(modelPermit.getType())) {
            mTvStatus.setText("长期通行证");
            //没有外出事由 到达地址  来访事由
            mLyYezhuContent.setVisibility(View.GONE);
            mLyYezhuAddress.setVisibility(View.GONE);
            mLyFangkeContent.setVisibility(View.GONE);

        } else {
            mTvStatus.setText("访客通行证");
            //没有身份证号 到达地址  外出事由
            mLyYezhuID.setVisibility(View.GONE);
            mLyYezhuAddress.setVisibility(View.GONE);
            mLyFangkeContent.setVisibility(View.GONE);
        }
        mTvFangwu.setText(modelPermit.getCommunity_name());
        mTvPerson.setText(modelPermit.getOwner_name());
        mTvShenfenzheng.setText(modelPermit.getId_card());
        mTvPhone.setText(modelPermit.getPhone());
        if (!NullUtil.isStringEmpty(modelPermit.getCar_number())) {
            mTvCarNum.setText(modelPermit.getCar_number());
        } else {
            mTvCarNum.setText("--");
        }
        mTvYezhuAddress.setText(modelPermit.getAddress());
        mTvYezhuContent.setText(modelPermit.getNote());
        mTvFangkeContent.setText(modelPermit.getNote());*/

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_submit_permit;
    }

    @Override
    protected void initIntentData() {
        company_id = this.getIntent().getStringExtra("company_id");
        id = this.getIntent().getStringExtra("id");
        status = this.getIntent().getStringExtra("status");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ly_select_house, R.id.txt_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_select_house:
                break;
            case R.id.txt_btn:
                break;
        }
    }
}
