package com.huacheng.huiservers.ui.index.request.adapter;

import android.content.Context;

import com.huacheng.huiservers.model.ModelRequest;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 投诉建议adapter
 * created by wangxiaotao
 * 2019/5/8 0008 上午 9:37
 */
public class AdapterRequestList extends CommonAdapter<ModelRequest> {
    public AdapterRequestList(Context context, int layoutId, List<ModelRequest> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelRequest item, int position) {

    }
}

