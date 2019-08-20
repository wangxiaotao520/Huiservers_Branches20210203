package com.huacheng.huiservers.ui.index.charge.adapter;

import android.content.Context;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelChargeDetail;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：充电桩adapter
 * 时间：2019/8/19 18:41
 * created by DFF
 */
public class ChargeGridViewTagAdapter extends CommonAdapter<ModelChargeDetail> {

    public ChargeGridViewTagAdapter(Context context, int layoutId, List<ModelChargeDetail> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelChargeDetail item, int position) {

        if(item.isSelect()){
            viewHolder.<TextView>getView(R.id.tv_tag).setBackgroundResource(R.drawable.all_shape_orange_new);
            viewHolder.<TextView>getView(R.id.tv_tag).setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            viewHolder.<TextView>getView(R.id.tv_tag).setBackgroundResource(R.drawable.allshape_stoke_gray_33_5);
            viewHolder.<TextView>getView(R.id.tv_tag).setTextColor(mContext.getResources().getColor(R.color.blackgray));
        }

    }
}
