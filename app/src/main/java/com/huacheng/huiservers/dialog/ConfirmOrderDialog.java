package com.huacheng.huiservers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.ui.shop.adapter.ConfirmOrderDialogAdapter;
import com.huacheng.huiservers.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：确认订单配送方式
 * 时间：2019/11/14 11:02
 * created by DFF
 */
public class ConfirmOrderDialog extends Dialog {
    Context mContext;
    private MyListView listView;
    private LinearLayout lin_btn;
    private  ConfirmOrderDialogAdapter adapter;
    List<ModelShopIndex> mdata = new ArrayList();

    public ConfirmOrderDialog(@NonNull Context context, List<ModelShopIndex> mdata) {
        super(context, R.style.Dialog_down);
        this.mContext = context;
        this.mdata = mdata;
    }

    public ConfirmOrderDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ConfirmOrderDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm_order);
        listView = findViewById(R.id.listview);
        lin_btn = findViewById(R.id.lin_btn);

        for (int i = 0; i < 5; i++) {
            mdata.add(new ModelShopIndex());
        }
        adapter = new ConfirmOrderDialogAdapter(mContext, R.layout.item_confirm_order, mdata);
        listView.setAdapter(adapter);

    }
}
