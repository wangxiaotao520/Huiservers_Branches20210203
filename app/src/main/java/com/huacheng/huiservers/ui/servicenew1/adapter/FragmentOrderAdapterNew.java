package com.huacheng.huiservers.ui.servicenew1.adapter;

import android.content.Context;

import com.huacheng.huiservers.ui.servicenew.model.ModelOrderList;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 2.0服务 订单列表适配器
 * created by wangxiaotao
 * 2020/6/17 0017 16:08
 */
public class FragmentOrderAdapterNew extends CommonAdapter<ModelOrderList> {
    private int  type;
    public FragmentOrderAdapterNew(Context context, int layoutId, List<ModelOrderList> datas,int type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelOrderList item, int position) {

    }
}
