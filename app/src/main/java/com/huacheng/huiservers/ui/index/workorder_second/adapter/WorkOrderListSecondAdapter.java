package com.huacheng.huiservers.ui.index.workorder_second.adapter;

import android.content.Context;

import com.huacheng.huiservers.model.ModelWorkOrderList;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：新工单列表adapter
 * 时间：2019/4/8 16:55
 * created by DFF
 */
public class WorkOrderListSecondAdapter extends CommonAdapter<ModelWorkOrderList> {

    public WorkOrderListSecondAdapter(Context context, int layoutId, List<ModelWorkOrderList> datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelWorkOrderList item, int position) {

//        viewHolder.<TextView>getView(R.id.tv_adress).setText("");
//        viewHolder.<TextView>getView(R.id.tv_name).setText("");
//        viewHolder.<TextView>getView(R.id.tv_phone).setText("");
//        viewHolder.<TextView>getView(R.id.tv_type_name).setText("");
//        viewHolder.<TextView>getView(R.id.tv_comment).setText("");
//        if (type==0){//待派单  待增派 应该用status 判断
//            viewHolder.<TextView>getView(R.id.tv_status).setText("");
//        }else if(type==1){
//
//        }
    }
}
