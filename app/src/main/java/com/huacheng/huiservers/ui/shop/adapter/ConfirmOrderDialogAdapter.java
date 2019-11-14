package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：配送方式
 * 时间：2019/11/14 15:19
 * created by DFF
 */
public class ConfirmOrderDialogAdapter extends CommonAdapter<ModelShopIndex> {

    public ConfirmOrderDialogAdapter(Context context, int layoutId, List<ModelShopIndex> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelShopIndex item, int position) {

        viewHolder.<TextView>getView(R.id.tv_style).setText("配送方式");
        viewHolder.<TextView>getView(R.id.txt_address).setText("地址");
        viewHolder.<ImageView>getView(R.id.iv_select);

    }
}
