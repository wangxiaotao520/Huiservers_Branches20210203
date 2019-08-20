package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;

import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 关联消息
 * created by wangxiaotao
 * 2019/8/20 0020 下午 6:23
 */
public class AdapterOldMessage extends CommonAdapter<String>{
    public AdapterOldMessage(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {

    }
}
