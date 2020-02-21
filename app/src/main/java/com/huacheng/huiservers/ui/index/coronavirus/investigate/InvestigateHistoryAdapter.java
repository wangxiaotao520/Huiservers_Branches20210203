package com.huacheng.huiservers.ui.index.coronavirus.investigate;

import android.content.Context;

import com.huacheng.huiservers.model.ModelInvestigateList;
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

    }
}
