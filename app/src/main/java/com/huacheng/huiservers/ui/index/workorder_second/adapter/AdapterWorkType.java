package com.huacheng.huiservers.ui.index.workorder_second.adapter;

import android.content.Context;

import com.huacheng.huiservers.model.ModelWorkPersonalCatItem;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description:
 * created by wangxiaotao
 * 2019/4/9 0009 下午 5:24
 */
public class AdapterWorkType extends CommonAdapter<ModelWorkPersonalCatItem> {


    public AdapterWorkType(Context context, int layoutId, List<ModelWorkPersonalCatItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelWorkPersonalCatItem item, int position) {

    }
}