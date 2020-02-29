package com.huacheng.huiservers.ui.index.coronavirus;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;

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

    private String company_id;
    private String id;
    //private int type;//区分是首页提交，重新提交，审核中

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("通行证申请");

    /*    if (type == 0) {//首次提交

        } else if (type == 1) {//重新提交

        } else {//审核中
            mTvHouse.setFocusable(false);
            mEtName.setFocusable(false);
            mEtShenfenNum.setFocusable(false);
            mEtPhone.setFocusable(false);
            mEtCarNum.setFocusable(false);
            mEtAddress.setFocusable(false);
        }*/

    }

    @Override
    protected void initData() {

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
        company_id= this.getIntent().getStringExtra("company_id");
        id= this.getIntent().getStringExtra("id");
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
