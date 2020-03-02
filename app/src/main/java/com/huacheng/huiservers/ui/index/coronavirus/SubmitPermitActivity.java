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
import com.huacheng.huiservers.model.EventModelPass;
import com.huacheng.huiservers.model.ModelPassCheckInformation;
import com.huacheng.huiservers.model.ModelPermit;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.greenrobot.eventbus.EventBus;
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
    @BindView(R.id.ly_id_card)
    LinearLayout mLyIdCard;
    @BindView(R.id.ly_address)
    LinearLayout mLyAddress;


    private int page = 1;
    ModelPermit permitInfo;
    private String company_id;
    private String id;
    private String status;//1为不需要审核；2为需要审核
    private int jump_type;//1列表；2为详情
    private String type;//长期 临时 访客
    private String owner_name;
    private String id_card;
    private String community_id;
    private String community_name;
    private String room_id;
    private String room_info;
    private String phone;
    private String car_number;
    private String address;
    private String note;


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();

        if ("1".equals(type)) {
            titleName.setText("临时通行证申请");
            //没有来访事由
            mEtContent.setHint("请输入来访事由");
        } else if ("3".equals(type)) {
            titleName.setText("访客通行证申请");
            //没有身份证号 到达地址  外出事由
            mLyIdCard.setVisibility(View.GONE);
            mLyAddress.setVisibility(View.GONE);
            mEtContent.setHint("请输入外出事由");
        }
        mTvHouse.setText(community_name + room_info);
        if (jump_type == 2) { //从详情跳来的

            mEtName.setText(owner_name + "");
            mEtName.setSelection(mEtName.getText().length());
            mEtShenfenNum.setText(id_card + "");
            mEtPhone.setText(phone);
            mEtCarNum.setText(car_number + "");
            mEtAddress.setText(address + "");
            mEtContent.setText(note + "");

        }
    }

    @Override
    protected void initData() {
        if (jump_type == 2) {

        } else {
            String url = ApiHttpClient.PASS_CHECK_INFORMATION;
//        if (jump_type == 1) {
//            url = ApiHttpClient.PASS_CHECK_INFORMATION;
//        } else {
//            url = ApiHttpClient.PASS_CHECK_INFORMATION;
//        }
            showDialog(smallDialog);
            HashMap<String, String> params = new HashMap<>();
            params.put("company_id", company_id);
            params.put("pass_check_set_id", id);

            MyOkHttp.get().post(url, params, new JsonResponseHandler() {
                @Override
                public void onSuccess(int statusCode, JSONObject response) {
                    hideDialog(smallDialog);
                    if (JsonUtil.getInstance().isSuccess(response)) {
//                    List<ModelIvestigateInformation> data = JsonUtil.getInstance().getDataArrayByName(response, "data", ModelIvestigateInformation.class);

                        ModelPassCheckInformation data = (ModelPassCheckInformation) JsonUtil.getInstance().parseJsonFromResponse(response, ModelPassCheckInformation.class);
                        if (data != null) {
                            //普通提交
                            if (data.getPc_info() != null) {
                                ModelPassCheckInformation.PicInfoBean pc_info = data.getPc_info();
                                mEtName.setText(pc_info.getOwner_name() + "");
                                mEtName.setSelection(mEtName.getText().length());
                                owner_name = pc_info.getOwner_name() + "";
                                mEtShenfenNum.setText(pc_info.getId_card() + "");
                                id_card = pc_info.getId_card() + "";
                                mEtPhone.setText(pc_info.getPhone());
                                phone = pc_info.getPhone() + "";
                                mEtCarNum.setText(pc_info.getCar_number() + "");
                                car_number = pc_info.getCar_number() + "";
                                mEtAddress.setText(pc_info.getAddress());
                                address = pc_info.getAddress() + "";
                                mEtContent.setText(pc_info.getNote());
                                note = pc_info.getNote() + "";
                            }
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


    @Override
    protected void initListener() {

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
        type = this.getIntent().getStringExtra("type");
        jump_type = this.getIntent().getIntExtra("jump_type", 1);
        if (jump_type == 2)  {//从详情跳来的
            owner_name = this.getIntent().getStringExtra("owner_name");
            id_card = this.getIntent().getStringExtra("id_card");
            phone = this.getIntent().getStringExtra("phone");
            car_number = this.getIntent().getStringExtra("car_number");
            address = this.getIntent().getStringExtra("address");
            note = this.getIntent().getStringExtra("note");
        }
        community_id = this.getIntent().getStringExtra("community_id");
        community_name = this.getIntent().getStringExtra("community_name");
        room_id = this.getIntent().getStringExtra("room_id");
        room_info = this.getIntent().getStringExtra("room_info");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @OnClick({R.id.txt_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_btn:
                owner_name = mEtName.getText().toString().trim() + "";
                if (NullUtil.isStringEmpty(owner_name)) {
                    SmartToast.showInfo("姓名不可为空");
                    return;
                }
                if ("3".equals(type)) {//访客没有身份证
                    id_card = mEtShenfenNum.getText().toString().trim() + "";
                    if (NullUtil.isStringEmpty(id_card)) {
                        SmartToast.showInfo("身份证号不可为空");
                        return;
                    }
                    address = mEtAddress.getText().toString().trim() + "";
                    if (NullUtil.isStringEmpty(address)) {
                        SmartToast.showInfo("到达地址不可为空");
                        return;
                    }
                }
                phone = mEtPhone.getText().toString().trim() + "";
                if (NullUtil.isStringEmpty(phone)) {
                    SmartToast.showInfo("联系方式不可为空");
                    return;
                }
                note = mEtContent.getText().toString().trim() + "";
                commitIndeed();

                break;
        }
    }

    /**
     * 直接提交(没有图片)
     */
    private void commitIndeed() {

        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("company_id", company_id + "");
        params.put("pass_check_set_id", id + "");
        params.put("type", type + "");
        params.put("community_id", community_id);
        params.put("community_name", community_name);
        params.put("room_id", room_id);
        params.put("room_info", room_info);
        params.put("owner_name", owner_name);
        params.put("phone", phone);
        if (!NullUtil.isStringEmpty(car_number)) {
            params.put("car_number", car_number);
        }
        if (!"3".equals(type)) {//访客没有到达地址
            params.put("id_card", id_card);
            params.put("address", address);
        }
        params.put("note", note);

        MyOkHttp.get().post(ApiHttpClient.PASS_CHECK_SUBMIT, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    setResult(RESULT_OK);
                    finish();
                    EventModelPass modelPass = new EventModelPass();
                    modelPass.setStatus(status);
                    EventBus.getDefault().post(modelPass);

                } else {
                    SmartToast.showInfo(JsonUtil.getInstance().getMsgFromResponse(response, "提交失败"));
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
