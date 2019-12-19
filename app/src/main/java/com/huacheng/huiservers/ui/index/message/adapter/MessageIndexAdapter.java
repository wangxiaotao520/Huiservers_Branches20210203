package com.huacheng.huiservers.ui.index.message.adapter;

import android.content.Context;

import com.huacheng.huiservers.model.ModelIndexMessage;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：消息大厅
 * 时间：2019/12/12 10:26
 * created by DFF
 */
public class MessageIndexAdapter extends CommonAdapter<ModelIndexMessage> {

    public MessageIndexAdapter(Context context, int layoutId, List<ModelIndexMessage> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelIndexMessage item, int position) {

    }
}
