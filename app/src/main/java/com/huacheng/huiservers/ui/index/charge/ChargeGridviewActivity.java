package com.huacheng.huiservers.ui.index.charge;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.ChargeSelectPriceDialog;
import com.huacheng.huiservers.dialog.ChargeShouFeiDetailDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelChargeDetail;
import com.huacheng.huiservers.model.ModelChargeGrid;
import com.huacheng.huiservers.pay.chinaums.UnifyPayActivity;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.index.charge.adapter.ChargeGridViewAdapter;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：小区充电桩显示界面
 * 时间：2019/8/19 17:11
 * created by DFF
 */
public class ChargeGridviewActivity extends BaseActivity implements View.OnClickListener {
    private MyGridview gridView;
    private LinearLayout ly_shoufei_detail;
    List<ModelChargeGrid> mdata = new ArrayList<>();
    ChargeGridViewAdapter chargeGridViewAdapter;
    private String equipment_code;
    private TextView tv_unit_price;
    private ModelChargeDetail model;
    private String  response;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("小区充电桩");
        tv_unit_price = findViewById(R.id.tv_unit_price);
        ly_shoufei_detail = findViewById(R.id.ly_shoufei_detail);
        findViewById(R.id.ll_charge_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gridView = findViewById(R.id.gridView);
        chargeGridViewAdapter = new ChargeGridViewAdapter(this, R.layout.activity_charge_gridview_item, mdata);
        gridView.setAdapter(chargeGridViewAdapter);

    }

    @Override
    protected void initData() {
        if (model!=null){
            tv_unit_price.setText(model.getPrice()+"");

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject_data = jsonObject.getJSONObject("data");

                for (int i = 0; i < model.getGls(); i++) {
                    ModelChargeGrid modelChargeGrid = new ModelChargeGrid();
                    if (jsonObject_data.has("glzt"+(i+1))){
                        modelChargeGrid.setStatus(jsonObject_data.getInt("glzt"+(i+1)));
                    }else {
                        modelChargeGrid.setStatus(0);
                    }
                    mdata.add(modelChargeGrid);
                }
                chargeGridViewAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
                ChargeSelectPriceDialog dialog = new ChargeSelectPriceDialog(ChargeGridviewActivity.this, model, position, new ChargeSelectPriceDialog.OnClickEnsureListener() {
                    @Override
                    public void onClick(Dialog dialog1,int selected_pass_position, int selected_order_position) {
                        //点击确定
                        dialog1.dismiss();
                        ConfirmOrder(selected_pass_position,selected_order_position);

                    }
                });
                dialog.setCanceledOnTouchOutside(true);//看需不需要点击外部消失
                dialog.show();

            }
        });
    }

    /**
     * 充电下单接口
     */
    private void ConfirmOrder(int selected_pass_position, final int selected_order_position) {
        if (model==null){
            return;
        }


        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("gtel", model.getGtel() + "");
        params.put("td", (selected_pass_position + 1)+"");
        params.put("order_price", model.getPrice_list().get(selected_order_position).getOrder_price()+"");
        params.put("times", model.getPrice_list().get(selected_order_position).getTimes()+"");

        MyOkHttp.get().post(ApiHttpClient.GET_CREAT_ORDER, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    Intent intent = new Intent(ChargeGridviewActivity.this, UnifyPayActivity.class);
                    Bundle bundle = new Bundle();
                    try {
                        bundle.putString("o_id", response.getString("data"));
                        bundle.putString("price", model.getPrice_list().get(selected_order_position).getOrder_price()+"" + "");
                        bundle.putString("type", "charge_yx");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
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
    protected int getLayoutId() {
        return R.layout.activity_charge_gridview;
    }

    @Override
    protected void initIntentData() {
        model = (ModelChargeDetail) this.getIntent().getSerializableExtra("model");
        response =  this.getIntent().getStringExtra("response");

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
                ChargeShouFeiDetailDialog shouFeiDetailDialog = new ChargeShouFeiDetailDialog(this,model);
                shouFeiDetailDialog.setCanceledOnTouchOutside(true);//看需不需要点击外部消失
                shouFeiDetailDialog.show();

                break;
            default:
                break;
        }

    }
}
