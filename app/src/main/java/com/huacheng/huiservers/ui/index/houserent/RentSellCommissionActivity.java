package com.huacheng.huiservers.ui.index.houserent;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.libraryservice.utils.DeviceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:租售委托
 * Author: badge
 * Create: 2018/11/6 15:42
 */
public class RentSellCommissionActivity extends BaseActivity {

    @BindView(R.id.tv_house_sell)
    TextView tvHouseSell;
    @BindView(R.id.tv_house_rent)
    TextView tvHouseRent;
    @BindView(R.id.fl_house_rent)
    FrameLayout fl_house_rent;
    @BindView(R.id.fl_house_sell)
    FrameLayout fl_house_sell;


    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("租售委托");
//        TextPaint tp = titleName.getPaint();
//        tp.setFakeBoldText(true);

        int height=(int)((DeviceUtils.getWindowWidth(this)-DeviceUtils.dip2px(this,30))*430*1f/1020);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DeviceUtils.getWindowWidth(this)-DeviceUtils.dip2px(this,30), height);
        params.gravity= Gravity.CENTER_HORIZONTAL;
        params.setMargins(0,DeviceUtils.dip2px(this,10),0,0);
        fl_house_rent.setLayoutParams(params);
        fl_house_sell.setLayoutParams(params);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rent_sell_commission;
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

    @OnClick({R.id.tv_house_sell, R.id.tv_house_rent,R.id.fl_house_rent,R.id.fl_house_sell})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_house_sell:
                intent.setClass(this, RentSellReleaseActivity.class);
                intent.putExtra("type", 2);//售房
                startActivityForResult(intent, 22);
                break;
            case R.id.tv_house_rent:
                intent.setClass(this, RentSellReleaseActivity.class);
                intent.putExtra("type", 1);//租房
                startActivityForResult(intent, 22);
                break;
            case R.id.fl_house_sell:
                intent.setClass(this, RentSellReleaseActivity.class);
                intent.putExtra("type", 2);//售房
                startActivityForResult(intent, 22);
                break;
            case R.id.fl_house_rent:
                intent.setClass(this, RentSellReleaseActivity.class);
                intent.putExtra("type", 1);//租房
                startActivityForResult(intent, 22);
                break;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 22) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    int jumpType = data.getIntExtra("jump_type", 0);
                    Intent intent = new Intent(RentSellCommissionActivity.this, MyHousePropertyActivity.class);
                    intent.putExtra("jump_type", jumpType);
                    startActivity(intent);
                    finish();
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
