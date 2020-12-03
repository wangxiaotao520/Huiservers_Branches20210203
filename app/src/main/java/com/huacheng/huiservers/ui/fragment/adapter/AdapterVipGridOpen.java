package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelVipIndex;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：开通vip
 * 时间：2020/11/30 10:33
 * created by DFF
 */
public class AdapterVipGridOpen extends CommonAdapter<ModelVipIndex> {

    public AdapterVipGridOpen(Context context, int layoutId, List<ModelVipIndex> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelVipIndex item, int position) {
        viewHolder.<TextView>getView(R.id.tv_original_price).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getName());
        viewHolder.<TextView>getView(R.id.tv_price).setText(item.getPrice());
        viewHolder.<TextView>getView(R.id.tv_original_price).setText(item.getOriginal_price() + "/月");

        if (position == 0) {
            viewHolder.<TextView>getView(R.id.tv_type).setText("单月购买续费");
        } else if (position == 1) {
            viewHolder.<TextView>getView(R.id.tv_type).setText("季度购买续费");
        } else if (position == 2) {
            viewHolder.<TextView>getView(R.id.tv_type).setText("包年购买续费");
        }


        if (item.isSelect() == true) {
            viewHolder.<LinearLayout>getView(R.id.ly_bg).setBackgroundResource(R.drawable.shape_vip_stoke5_solid);
        } else {
            viewHolder.<LinearLayout>getView(R.id.ly_bg).setBackgroundResource(R.drawable.allshape_stroke_grey_bb);
        }
    }
}
