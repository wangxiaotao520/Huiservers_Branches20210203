package com.huacheng.huiservers.ui.index.workorder_second.adapter;

import android.content.Context;

import com.huacheng.huiservers.ui.center.geren.bean.GroupMemberBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description:工单选择房屋
 * created by wangxiaotao
 * 2019/4/9 0009 下午 5:50
 */
public class AdapterHouseList extends CommonAdapter<GroupMemberBean> {
    public AdapterHouseList(Context context, int layoutId, List<GroupMemberBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, GroupMemberBean item, int position) {

    }
}
