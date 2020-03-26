package com.huacheng.huiservers.ui.center;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.Url_info;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.RawResponseHandler;
import com.huacheng.huiservers.model.CouponBean;
import com.huacheng.huiservers.model.protocol.CenterProtocol;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.adapter.CouponRecordingAdapter;
import com.huacheng.huiservers.view.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/16.
 */
public class CouponRecordingActivity extends BaseActivity {

    @BindView(R.id.lin_left)
    LinearLayout linLeft;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.listview_couponUse)
    MyListView listviewCouponUse;
    @BindView(R.id.lin_couponUse)
    LinearLayout linCouponUse;
    @BindView(R.id.rel_no_data)
    RelativeLayout relNoData;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    ImageView img_data;
    TextView tv_text;

    CouponRecordingAdapter useAdapter;
    CenterProtocol protocol = new CenterProtocol();
    List<CouponBean> coupons;


    /**
     * 优惠券记录
     */
    private void couponRecording() {
        showDialog(smallDialog);
        Url_info info = new Url_info(this);
        MyOkHttp.get().post(info.coupon_over_40, null, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hideDialog(smallDialog);
                coupons = new CenterProtocol().getCoupon40Recording(response);
                if (coupons != null) {
                    if (coupons.size() > 0) {
                        scrollView.setVisibility(View.VISIBLE);
                        relNoData.setVisibility(View.GONE);
                        useAdapter = new CouponRecordingAdapter(coupons, CouponRecordingActivity.this);
                        listviewCouponUse.setAdapter(useAdapter);

                    } else {
                        scrollView.setVisibility(View.GONE);
                        relNoData.setVisibility(View.VISIBLE);
                        img_data.setBackgroundResource(R.mipmap.bg_no_coupon_data);
                        tv_text.setText("暂无优惠券");
                    }
                } else {
                    scrollView.setVisibility(View.GONE);
                    relNoData.setVisibility(View.VISIBLE);
                    img_data.setBackgroundResource(R.mipmap.bg_no_coupon_data);
                    tv_text.setText("暂无优惠券");

                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        } );
    }

    @OnClick(R.id.lin_left)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.lin_left:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        titleName.setText("记录");
        img_data = findViewById(R.id.img_data);
        tv_text = findViewById(R.id.tv_text);
        couponRecording();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.coupon_recording;
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
