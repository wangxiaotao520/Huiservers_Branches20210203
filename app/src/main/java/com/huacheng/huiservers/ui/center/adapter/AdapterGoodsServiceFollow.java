package com.huacheng.huiservers.ui.center.adapter;

import android.content.Context;

import com.huacheng.huiservers.ui.servicenew.model.ModelOrderList;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：商品  服务
 * 时间：2020/12/2 10:04
 * created by DFF
 */
public class AdapterGoodsServiceFollow extends CommonAdapter<ModelOrderList> {

    public AdapterGoodsServiceFollow(Context context, int layoutId, List<ModelOrderList> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelOrderList item, int position) {

    }
}
