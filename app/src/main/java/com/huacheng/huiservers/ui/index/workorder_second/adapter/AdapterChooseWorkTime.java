package com.huacheng.huiservers.ui.index.workorder_second.adapter;

import android.content.Context;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelWorkTime;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description:选择时间adapter
 * created by wangxiaotao
 * 2019/4/9 0009 上午 8:53
 */
public class AdapterChooseWorkTime extends CommonAdapter<ModelWorkTime>{


    public AdapterChooseWorkTime(Context context, int layoutId, List<ModelWorkTime> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelWorkTime item, int position) {
        viewHolder.<TextView>getView(R.id.tv_time).setText(item.getTime()+"");
        if (item.isIs_selected()){
            viewHolder.<TextView>getView(R.id.tv_time).setBackgroundResource(R.drawable.allshape_orange);
            viewHolder.<TextView>getView(R.id.tv_time).setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            viewHolder.<TextView>getView(R.id.tv_time).setBackgroundResource(R.drawable.bg_shape_stoke_grey);
            viewHolder.<TextView>getView(R.id.tv_time).setTextColor(mContext.getResources().getColor(R.color.text_color));
        }
    }
}
