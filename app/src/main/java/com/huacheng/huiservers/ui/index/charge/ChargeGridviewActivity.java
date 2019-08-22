package com.huacheng.huiservers.ui.index.charge;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.ChargeSelectPriceDialog;
import com.huacheng.huiservers.dialog.ChargeShouFeiDetailDialog;
import com.huacheng.huiservers.model.ModelChargeDetail;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.charge.adapter.ChargeGridViewAdapter;
import com.huacheng.huiservers.view.MyGridview;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：小区充电桩显示界面
 * 时间：2019/8/19 17:11
 * created by DFF
 */
public class ChargeGridviewActivity extends BaseActivity implements View.OnClickListener {
    private MyGridview gridView;
    private LinearLayout ly_shoufei_detail;
    List<ModelChargeDetail> mdata = new ArrayList<>();
    ChargeGridViewAdapter chargeGridViewAdapter;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("小区充电桩");

        ly_shoufei_detail = findViewById(R.id.ly_shoufei_detail);


        for (int i = 0; i < 12; i++) {
            mdata.add(new ModelChargeDetail());
        }
        gridView = findViewById(R.id.gridView);
        chargeGridViewAdapter = new ChargeGridViewAdapter(this, R.layout.activity_charge_gridview_item, mdata);
        gridView.setAdapter(chargeGridViewAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        ly_shoufei_detail.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mdata.size(); i++) {
                    if (position == i) {
                        mdata.get(i).setSelect(true);
                    } else {
                        mdata.get(i).setSelect(false);
                    }
                }
                chargeGridViewAdapter.notifyDataSetChanged();
                ChargeSelectPriceDialog dialog = new ChargeSelectPriceDialog(ChargeGridviewActivity.this);
                dialog.setCanceledOnTouchOutside(true);//看需不需要点击外部消失
                dialog.show();

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_gridview;
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
        switch (v.getId()) {
            case R.id.ly_shoufei_detail:
                ChargeShouFeiDetailDialog shouFeiDetailDialog = new ChargeShouFeiDetailDialog(this);
                shouFeiDetailDialog.setCanceledOnTouchOutside(true);//看需不需要点击外部消失
                shouFeiDetailDialog.show();

                break;
            default:
                break;
        }

    }
}