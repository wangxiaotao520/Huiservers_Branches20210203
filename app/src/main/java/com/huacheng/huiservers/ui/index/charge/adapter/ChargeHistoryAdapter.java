package com.huacheng.huiservers.ui.index.charge.adapter;

import android.content.Context;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldFile;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：充电记录adapter
 * 时间：2019/8/18 11:39
 * created by DFF
 */
public class ChargeHistoryAdapter extends CommonAdapter<ModelOldFile> {

    public ChargeHistoryAdapter(Context context, int layoutId, List<ModelOldFile> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelOldFile item, int position) {

        viewHolder.<TextView>getView(R.id.tv_time).setText("时间");
        viewHolder.<TextView>getView(R.id.tv_status).setText("进行中");
        viewHolder.<TextView>getView(R.id.tv_shichang).setText("05:44:22");
        viewHolder.<TextView>getView(R.id.tv_price).setText("5元");

    }
}
