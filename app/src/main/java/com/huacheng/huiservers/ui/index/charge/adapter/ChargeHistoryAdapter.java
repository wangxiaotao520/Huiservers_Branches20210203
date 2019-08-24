package com.huacheng.huiservers.ui.index.charge.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelChargeHistory;
import com.huacheng.huiservers.utils.StringUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：充电记录adapter
 * 时间：2019/8/18 11:39
 * created by DFF
 */
public class ChargeHistoryAdapter extends CommonAdapter<ModelChargeHistory> {

    public ChargeHistoryAdapter(Context context, int layoutId, List<ModelChargeHistory> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelChargeHistory item, int position) {

        viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getPay_time(),"7"));
        if ("1".equals(item.getStatus())){
            viewHolder.<TextView>getView(R.id.tv_status).setTextColor(Color.parseColor("#ED8D37"));
        }else {
            viewHolder.<TextView>getView(R.id.tv_status).setTextColor(Color.parseColor("#666666"));
        }
        viewHolder.<TextView>getView(R.id.tv_status).setText(item.getStatus_cn()+"");
        viewHolder.<TextView>getView(R.id.tv_shichang).setText(item.getTimes()+"小时");
        viewHolder.<TextView>getView(R.id.tv_price).setText(item.getPrice()+"元");

    }
}
