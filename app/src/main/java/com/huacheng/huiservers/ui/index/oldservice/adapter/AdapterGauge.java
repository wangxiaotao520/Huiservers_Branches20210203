package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldFootmark;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：心率  血压 云测温
 * 时间：2020/10/3 11:59
 * created by DFF
 */
public class AdapterGauge extends CommonAdapter<ModelOldFootmark> {
    int type;

    public AdapterGauge(Context context, int layoutId, List<ModelOldFootmark> datas, int type) {
        super(context, layoutId, datas);
        this.type = type;

    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelOldFootmark item, int position) {

        if (position == 0) {
            viewHolder.<TextView>getView(R.id.ly_title).setVisibility(View.VISIBLE);
        } else {
            viewHolder.<TextView>getView(R.id.ly_title).setVisibility(View.GONE);
        }
        if (type == 1) {//心率
            viewHolder.<TextView>getView(R.id.tv_type_unit).setText("次/分");
            viewHolder.<TextView>getView(R.id.tv_data).setText(item.getT());
            viewHolder.<TextView>getView(R.id.tv_name).setText(item.getH());
        } else if (type == 2) {//血压
            viewHolder.<TextView>getView(R.id.tv_type_unit).setText("收缩压/舒张压");
            viewHolder.<TextView>getView(R.id.tv_data).setText(item.getT());
            viewHolder.<TextView>getView(R.id.tv_name).setText(item.getH() + "/" + item.getL());
        } else {//云测温
            viewHolder.<TextView>getView(R.id.tv_type_unit).setText("温度");
            viewHolder.<TextView>getView(R.id.tv_data).setText(item.getT());
            viewHolder.<TextView>getView(R.id.tv_name).setText(item.getW());
        }

    }

}
