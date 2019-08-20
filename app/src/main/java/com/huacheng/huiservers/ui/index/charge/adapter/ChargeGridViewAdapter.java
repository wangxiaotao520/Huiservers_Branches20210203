package com.huacheng.huiservers.ui.index.charge.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.FrameLayout;
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
public class ChargeGridViewAdapter extends CommonAdapter<ModelChargeDetail> {

    public ChargeGridViewAdapter(Context context, int layoutId, List<ModelChargeDetail> datas) {
        super(context, layoutId, datas);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void convert(ViewHolder viewHolder, ModelChargeDetail item, int position) {
        viewHolder.<TextView>getView(R.id.tv_number).setText((position+1)+"");
        if (position == 0) {
            viewHolder.<FrameLayout>getView(R.id.fy_bg).setBackground(mContext.getDrawable(R.drawable.allshape_charge_blue));
            viewHolder.<TextView>getView(R.id.tv_number).setTextColor(mContext.getResources().getColor(R.color.gray_66));
            viewHolder.<TextView>getView(R.id.tv_content).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_content).setText("占用");
        } else if (position == 2) {
            viewHolder.<FrameLayout>getView(R.id.fy_bg).setBackground(mContext.getDrawable(R.drawable.allshape_charge_fen));
            viewHolder.<TextView>getView(R.id.tv_number).setTextColor(mContext.getResources().getColor(R.color.gray_66));
            viewHolder.<TextView>getView(R.id.tv_content).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_content).setText("故障");
        } else {
            if (item.isSelect()){
                viewHolder.<FrameLayout>getView(R.id.fy_bg).setBackground(mContext.getDrawable(R.drawable.all_shape_orange_new));
                viewHolder.<TextView>getView(R.id.tv_number).setTextColor(mContext.getResources().getColor(R.color.white));
                viewHolder.<TextView>getView(R.id.tv_content).setVisibility(View.GONE);
            }else {
                viewHolder.<FrameLayout>getView(R.id.fy_bg).setBackground(mContext.getDrawable(R.drawable.allshape_white));
                viewHolder.<TextView>getView(R.id.tv_number).setTextColor(mContext.getResources().getColor(R.color.blackgray));
                viewHolder.<TextView>getView(R.id.tv_content).setVisibility(View.GONE);
            }

        }


    }
}
