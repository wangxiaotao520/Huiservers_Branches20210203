package com.huacheng.huiservers.ui.index.coronavirus;

import android.content.Context;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelPermit;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：申请通行证列表适配器
 * 时间：2020/2/25 09:23
 * created by DFF
 */
public class AddPermitListAdapter extends CommonAdapter<ModelPermit> {

    public AddPermitListAdapter(Context context, int layoutId, List<ModelPermit> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelPermit item, int position) {
        viewHolder.<TextView>getView(R.id.tv_title).setText(item.getTitle());
        if ("1".equals(item.getType())) {
            viewHolder.<TextView>getView(R.id.tv_title_type).setText("临时通行证");
        } else if ("2".equals(item.getType())) {
            viewHolder.<TextView>getView(R.id.tv_title_type).setText("长期通行证");
        } else {
            viewHolder.<TextView>getView(R.id.tv_title_type).setText("访客通行证");
        }


    }
}
