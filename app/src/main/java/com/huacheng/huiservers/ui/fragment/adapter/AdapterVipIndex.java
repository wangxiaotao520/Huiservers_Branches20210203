package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;

import com.huacheng.huiservers.model.ModelVipIndex;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：vip 店铺商品你信息
 * 时间：2020/11/30 10:33
 * created by DFF
 */
public class AdapterVipIndex extends CommonAdapter<ModelVipIndex> {

    public AdapterVipIndex(Context context, int layoutId, List<ModelVipIndex> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelVipIndex item, int position) {

    }
}
