package com.huacheng.huiservers.ui.index.houserent;

import android.content.Intent;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.ui.base.BaseActivity;

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
    @BindView(R.id.wv_commission)
    WebView wvCommission;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findTitleViews();
        titleName.setText("租售委托");
        TextPaint tp = titleName.getPaint();
        tp.setFakeBoldText(true);
    }

    @Override
    protected void initData() {
        //支持javascript
        wvCommission.getSettings().setJavaScriptEnabled(true);
        // 设置允许JS弹窗
//        wvCommission.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置可以支持缩放
        wvCommission.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        wvCommission.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        wvCommission.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        wvCommission.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wvCommission.getSettings().setLoadWithOverviewMode(true);

        wvCommission.loadUrl(ApiHttpClient.GET_ENTRUST_BACKGROUND);
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
        if (wvCommission != null) {
            wvCommission.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wvCommission.clearHistory();

            ((ViewGroup) wvCommission.getParent()).removeView(wvCommission);
            wvCommission.destroy();
            wvCommission = null;
        }
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
