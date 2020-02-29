package com.huacheng.huiservers.ui.index.coronavirus;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelPermit;
import com.huacheng.huiservers.utils.StringUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：通行证详情适配器
 * 时间：2020/2/24 10:35
 * created by DFF
 */
public class PermitDetailAdapter extends CommonAdapter<ModelPermit> {
    public PermitDetailAdapter(Context context, int layoutId, List<ModelPermit> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelPermit item, int position) {
        if (position == 0) {
            viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.VISIBLE);
        } else {
            viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.GONE);
        }
        if ("1".equals(item.getAccess_type())) {
            viewHolder.<TextView>getView(R.id.tv_type).setText("出园");
            viewHolder.<TextView>getView(R.id.tv_time_type).setText("出园时间");
        } else {
            viewHolder.<TextView>getView(R.id.tv_type).setText("入园");
            viewHolder.<TextView>getView(R.id.tv_time_type).setText("入园时间");
        }
        viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAccess_time(),"7"));
        viewHolder.<TextView>getView(R.id.tv_content).setText(item.getNote());
    }
}
