package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelChargeDetail;
import com.huacheng.huiservers.ui.index.charge.adapter.ChargeGridViewTagAdapter;
import com.huacheng.huiservers.ui.shop.bean.ShopDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：充电桩选择充电金额
 * 时间：2019/8/20 08:42
 * created by DFF
 */
public class ChargeSelectPriceDialog extends Dialog {
    Context mContext;
    private GridView gridview;
    private TextView tv_price, tv_num,tv_content;
    List<ShopDetailBean> beans = new ArrayList();
    List<ModelChargeDetail.PriceListBean> mdata = new ArrayList();
    ChargeGridViewTagAdapter adapter;
    private ModelChargeDetail modelChargeDetail;
    private int position =1;
    private int selected_order_position= 0; //选择金额的position
    private OnClickEnsureListener listener ;


    public ChargeSelectPriceDialog(@NonNull Context context,ModelChargeDetail modelChargeDetail,int position,OnClickEnsureListener listener) {
        super(context, R.style.Dialog_down);
        this.mContext = context;
        this.modelChargeDetail=modelChargeDetail;
        this.position=position;
        for (int i = 0; i < modelChargeDetail.getPrice_list().size(); i++) {
            ModelChargeDetail.PriceListBean priceListBean = modelChargeDetail.getPrice_list().get(i);
            if (i==0){
                priceListBean.setSelect(true);
            }else {
                priceListBean.setSelect(false);
            }
            mdata.add(priceListBean);
        }
        this.listener= listener;
    }

    public ChargeSelectPriceDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ChargeSelectPriceDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_charge_shoufei_price);

        tv_num = findViewById(R.id.tv_num);
        tv_price = findViewById(R.id.tv_price);
        tv_content = findViewById(R.id.tv_content);
        gridview = findViewById(R.id.gridview);
        adapter = new ChargeGridViewTagAdapter(mContext, R.layout.dialog_charge_shoufei_price_item, mdata);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(int i = 0;i<mdata.size();i++){
                    if(position == i){
                        mdata.get(i).setSelect(true);
                    }else{
                        mdata.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();

                tv_content.setText("说明：预付"+modelChargeDetail.getPrice_list().get(position).getOrder_price()+"元（最多可充"+modelChargeDetail.getPrice_list().get(position).getTimes()+"小时）结束后退还未使用金额");
                tv_price.setText("¥ "+modelChargeDetail.getPrice_list().get(position).getOrder_price());
                selected_order_position=position;
            }
        });
        TextView tv_btn = findViewById(R.id.tv_btn);
        tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClick(ChargeSelectPriceDialog.this,position,selected_order_position);
                }
            }
        });
        Window window = getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        //此处可以设置dialog显示的位置
        WindowManager.LayoutParams params = window.getAttributes();
        // 设置对话框显示的位置
        params.gravity = Gravity.BOTTOM;

        params.width = params.MATCH_PARENT;
        //  params.width = params.WRAP_CONTENT;
        params.height = params.WRAP_CONTENT;
        window.setAttributes(params);

        initData();
    }

    private void initData() {
        if (modelChargeDetail!=null){
            tv_num.setText("已选重充电座："+(position+1)+"号");
            tv_content.setText("说明：预付"+modelChargeDetail.getPrice_list().get(0).getOrder_price()+"元（最多可充"+modelChargeDetail.getPrice_list().get(0).getTimes()+"小时）结束后退还未使用金额");
            tv_price.setText("¥ "+modelChargeDetail.getPrice_list().get(0).getOrder_price());
        }
    }
    public interface OnClickEnsureListener{
        /**
         *
         * @param selected_pass_position 充电桩通道position
         * @param selected_order_position 充电订单位置
         */
        void onClick(Dialog dialog1,int selected_pass_position,int selected_order_position);
    }
}
