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
    private TextView tv_price, tv_num;
    List<ShopDetailBean> beans = new ArrayList();
    List<ModelChargeDetail> mdata = new ArrayList();
    ChargeGridViewTagAdapter adapter;

    public ChargeSelectPriceDialog(@NonNull Context context) {
        super(context, R.style.Dialog_down);
        this.mContext = context;
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
        gridview = findViewById(R.id.gridview);
        for (int i = 0; i < 4; i++) {
            mdata.add(new ModelChargeDetail());
        }
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
    }
}
