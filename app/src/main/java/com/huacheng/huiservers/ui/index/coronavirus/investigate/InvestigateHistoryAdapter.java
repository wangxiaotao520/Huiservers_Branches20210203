package com.huacheng.huiservers.ui.index.coronavirus.investigate;

import android.content.Context;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelInvestigateList;
import com.huacheng.huiservers.utils.StringUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 调查问卷历史记录adapter
 * created by wangxiaotao
 * 2020/2/21 0021 下午 4:38
 */
public class InvestigateHistoryAdapter extends CommonAdapter<ModelInvestigateList> {
    public InvestigateHistoryAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelInvestigateList item, int position) {
        viewHolder.<TextView>getView(R.id.tv_title).setText(""+item.getTitle());
        viewHolder.<TextView>getView(R.id.tv_sub_title).setText(""+item.getIntroduce());
        viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAddtime(),"7"));
    }
}
