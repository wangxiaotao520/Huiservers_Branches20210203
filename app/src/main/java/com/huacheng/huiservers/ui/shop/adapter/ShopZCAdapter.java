package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;

import com.huacheng.huiservers.model.ModelShopIndex;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：专场
 * 时间：2019/12/13 10:56
 * created by DFF
 */
public class ShopZCAdapter extends CommonAdapter<ModelShopIndex> {

    public ShopZCAdapter(Context context, int layoutId, List<ModelShopIndex> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelShopIndex item, int position) {

    }
}
