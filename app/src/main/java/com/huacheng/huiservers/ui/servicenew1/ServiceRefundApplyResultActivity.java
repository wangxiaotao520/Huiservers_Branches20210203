package com.huacheng.huiservers.ui.servicenew1;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelRefundApplyList;
import com.huacheng.huiservers.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 申请退款结果页
 * created by wangxiaotao
 * 2020/7/23 0023 16:38
 */
public class ServiceRefundApplyResultActivity extends BaseActivity {
    @BindView(R.id.iv_status_img)
    ImageView ivStatusImg;
    @BindView(R.id.tv_status_txt)
    TextView tvStatusTxt;
    @BindView(R.id.tv_status_sub_txt)
    TextView tvStatusSubTxt;
    @BindView(R.id.tv_service_content)
    TextView tvServiceContent;
    @BindView(R.id.ly_service_content)
    LinearLayout lyServiceContent;
    @BindView(R.id.tv_service_merchant)
    TextView tvServiceMerchant;
    @BindView(R.id.ly_service_merchant)
    LinearLayout lyServiceMerchant;
    @BindView(R.id.tv_service_refund_number)
    TextView tvServiceRefundNumber;
    @BindView(R.id.ly_service_refund_number)
    LinearLayout lyServiceRefundNumber;
    @BindView(R.id.ll_service_content_container)
    LinearLayout llServiceContentContainer;
    @BindView(R.id.tv_price_tag)
    TextView tvPriceTag;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.ly_price)
    LinearLayout lyPrice;
    @BindView(R.id.tv_refund_account_type)
    TextView tvRefundAccountType;
    @BindView(R.id.ly_refund_account)
    LinearLayout lyRefundAccount;
    @BindView(R.id.tv_refund_reason_content)
    TextView tvRefundReasonContent;
    @BindView(R.id.ly_refund_reason)
    LinearLayout lyRefundReason;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    private int type = 1; //1是直接退款2是退款审核
    private ModelRefundApplyList modelRefundApplyList;
    private String reason= "";

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("申请退款");
        if (type ==1){
            ivStatusImg.setBackgroundResource(R.mipmap.ic_number_bind);
            tvStatusTxt.setText("申请成功，退款中");
            tvStatusSubTxt.setText("订单金额将在1~3个工作日退回您的付款账户");
        }else {
            ivStatusImg.setBackgroundResource(R.mipmap.ic_verfing);
            tvStatusTxt.setText("退款审核中,请耐心等待");
            tvStatusSubTxt.setText("商家将在1-2个工作日内审核您的退款申请");
        }
        tvServiceContent.setText(modelRefundApplyList.getS_name()+"");
        tvServiceMerchant.setText(modelRefundApplyList.getI_name()+"");
        tvServiceRefundNumber.setText(modelRefundApplyList.getNumber()+"");
        if (type ==1){
            tvPrice.setText(modelRefundApplyList.getAmount()+"");
        }else {
            tvPrice.setText(modelRefundApplyList.getRefunds_price()+"");
        }
        if ("alipay".equals(modelRefundApplyList.getPay_type())){
            tvRefundAccountType.setText("支付宝");
        }else if ("weixinpay".equals(modelRefundApplyList.getPay_type())){
            tvRefundAccountType.setText("微信支付");
        }else {
            tvRefundAccountType.setText("云闪付");
        }
        tvRefundReasonContent.setText(reason+"");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_refund_apply_reslut;
    }

    @Override
    protected void initIntentData() {
        modelRefundApplyList = (ModelRefundApplyList) getIntent().getSerializableExtra("model");
        reason = getIntent().getStringExtra("reason");
        type=this.getIntent().getIntExtra("type",1);
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

}
