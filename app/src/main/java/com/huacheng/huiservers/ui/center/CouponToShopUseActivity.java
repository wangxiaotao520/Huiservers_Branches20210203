package com.huacheng.huiservers.ui.center;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.HttpHelper;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.RequestParams;
import com.huacheng.huiservers.model.CouponBean;
import com.huacheng.huiservers.model.protocol.CenterProtocol;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.adapter.CouponToShopUseAdapter;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.huiservers.view.MyListView;
import com.lidroid.xutils.BitmapUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/4.
 */

public class CouponToShopUseActivity extends BaseActivity {

    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.tv_toshopuse_title)
    TextView tvToshopuseTitle;

    @BindView(R.id.tv_howManywhatCoupon)
    TextView tvHowManywhatCoupon;
    @BindView(R.id.tv_died_date)
    TextView tvDiedDate;
    @BindView(R.id.tv_coupon_toshopUse_status)
    TextView tvCouponToshopUseStatus;
    @BindView(R.id.listview_use)
    MyListView listviewUse;
    @BindView(R.id.iv_toshopuse_bg)
    ImageView ivToshopuseBg;
    String couponID;
    CouponBean coupon;
    BitmapUtils bitmapUtils;
    @BindView(R.id.scrollViewDialog)
    ScrollView scrollViewDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isStatusBar=true;
        isFullScreen=true;
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.lin_left, R.id.tv_coupon_toshopUse_status})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.lin_left:
                finish();
                break;
        }
    }

    String couponStatus;

    private void getDetail(String couponID) {//详情
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("id", couponID);
        new HttpHelper(info.coupon_details_40, params, this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                coupon = new CenterProtocol().getCoupon40Detail(json);
                if (coupon != null) {
                    String boxImg = coupon.getBox_img();
                    if (!StringUtils.isEmpty(boxImg)) {
                        bitmapUtils.display(ivToshopuseBg, coupon.getBox_img());
                        ivToshopuseBg.getLayoutParams().width = ToolUtils.getScreenWidth(CouponToShopUseActivity.this);
                        Double d = Double.valueOf(ToolUtils.getScreenWidth(CouponToShopUseActivity.this)) / (Double.valueOf(coupon.getImg_size()));
                        ivToshopuseBg.getLayoutParams().height = (new Double(d)).intValue();
                    }
                    tvToshopuseTitle.setText(coupon.getAddress());


                    String amount = coupon.getAmount();
                    if (!StringUtils.isEmpty(amount)) {
                        double doubleAmount = Double.valueOf(amount);
                        if (doubleAmount > 1) {
                            tvHowManywhatCoupon.setText(doubleAmount + "元" + coupon.getCategory_name());
                        } else {
                            if (doubleAmount > 0 && doubleAmount < 1) {
                                String decimal = (doubleAmount + "").substring(2);
                                tvHowManywhatCoupon.setText(decimal + "折" + coupon.getCategory_name());
                            }
                        }
                    }
                    if (!StringUtils.isEmpty(coupon.getEndtime())) {
                        tvDiedDate.setText("有效期至 " + StringUtils.getDateToString(coupon.getEndtime(), "2"));
                    }
                    couponStatus = (coupon.getCoupon_status());
                    String couponStatustxt = "";
                    //
                    if (!StringUtils.isEmpty(couponStatus)) {
                        if (couponStatus.equals("0")) {
                            couponStatustxt = "立即领取";
                            tvCouponToshopUseStatus.setEnabled(true);
                            tvCouponToshopUseStatus.setBackground(getResources().getDrawable(R.drawable.corners5_green_use));
                        } else if (couponStatus.equals("1")) {
                            couponStatustxt = "立即使用";
                            tvCouponToshopUseStatus.setEnabled(true);
                            tvCouponToshopUseStatus.setBackground(getResources().getDrawable(R.drawable.corners5_green_use));
                        } else if (couponStatus.equals("2")) {
                            couponStatustxt = "已使用";
                            tvCouponToshopUseStatus.setEnabled(false);
                            tvCouponToshopUseStatus.setBackground(getResources().getDrawable(R.drawable.corners5_green_use_trans));

                        }
                        tvCouponToshopUseStatus.setText(couponStatustxt);
                    }

                    tvCouponToshopUseStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (couponStatus.equals("0")) {
                                couponAdd(coupon.getId());
                            } else if (couponStatus.equals("1")) {
                                new CommomDialog(CouponToShopUseActivity.this, R.style.my_dialog_DimEnabled, "每张优惠券只能使用一次，您确定立即使用？", new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        if (confirm) {
                                            getOpen(coupon.getM_c_id());
                                            dialog.dismiss();
                                        }
                                    }
                                }).show();//.setTitle("提示")
                            }
                        }
                    });
                    CouponToShopUseAdapter toShopUseAdapter = new CouponToShopUseAdapter(coupon, CouponToShopUseActivity.this);
                    listviewUse.setAdapter(toShopUseAdapter);
                    scrollViewDialog.setVisibility(View.VISIBLE);

                } else {
                    scrollViewDialog.setVisibility(View.GONE);

                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };

    }

    private void couponAdd(String id) {//领取优惠券
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("coupon_id", id);
        new HttpHelper(info.coupon_add, params, this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                String str = new CenterProtocol().setStatus(json);
                if ("1".equals(str)) {
                    coupon = null;
                    getDetail(couponID);
                    SmartToast.showInfo("领取到店优惠券成功");
                } else {
                    SmartToast.showInfo(str);
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }


    private void getOpen(String m_c_id) {//立即使用，开启使用
        Url_info info = new Url_info(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("m_c_id", m_c_id);
        showDialog(smallDialog);
        new HttpHelper(info.use_coupon, params, this) {

            @Override
            protected void setData(String json) {
                hideDialog(smallDialog);
                String str = new CenterProtocol().setStatus(json);
                if (str.equals("1")) {

                    SmartToast.showInfo("使用成功");
                    Intent intent = new Intent(CouponToShopUseActivity.this, CouponRecordingActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    SmartToast.showInfo(str);
                }
            }

            @Override
            protected void requestFailure(Exception error, String msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        };
    }


    @Override
    protected void initView() {
        ButterKnife.bind(this);

        bitmapUtils = new BitmapUtils(this);
        couponID = getIntent().getStringExtra("coupon_id");

        if (!StringUtils.isEmpty(couponID)) {
            getDetail(couponID);
        } else {
            SmartToast.showInfo("couponID不允许为空");
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.coupon_toshopuse;
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
}
