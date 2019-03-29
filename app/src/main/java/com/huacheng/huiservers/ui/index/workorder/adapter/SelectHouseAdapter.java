package com.huacheng.huiservers.ui.index.workorder.adapter;

import android.content.Context;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.index.wuye.bean.WuYeBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：选择房屋Adapter
 * 时间：2018/12/12 16:44
 * created by DFF
 */
public class SelectHouseAdapter extends CommonAdapter<WuYeBean> {
    public SelectHouseAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);


    }

    @Override
    protected void convert(ViewHolder viewHolder, WuYeBean item, int position) {
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getFullname());
         viewHolder.<TextView>getView(R.id.tv_phone).setText(item.getMobile());
         viewHolder.<TextView>getView(R.id.tv_address).setText(item.getCommunity_name()+ item.getBuilding_name()+ "号楼-" +
                 item.getUnit()+"单元-"+item.getCode());
    }
}
