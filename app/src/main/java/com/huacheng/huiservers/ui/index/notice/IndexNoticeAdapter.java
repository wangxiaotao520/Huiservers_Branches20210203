package com.huacheng.huiservers.ui.index.notice;

import android.content.Context;

import com.huacheng.huiservers.model.ModelNewWorkOrder;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：首页通知adapter
 * 时间：2019/10/19 15:43
 * created by DFF
 */
public class IndexNoticeAdapter extends CommonAdapter<ModelNewWorkOrder> {

    public IndexNoticeAdapter(Context context, int layoutId, List<ModelNewWorkOrder> datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelNewWorkOrder item, int position) {

      /*  viewHolder.<TextView>getView(R.id.tv_adress).setText(item.getAddress_cn());
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getNickname());
        viewHolder.<TextView>getView(R.id.tv_phone).setText(item.getUsername());
        if (item.getWork_type().equals("1")) {
            viewHolder.<TextView>getView(R.id.tv_type_name).setText("自用报修");
        } else {
            viewHolder.<TextView>getView(R.id.tv_type_name).setText("公共报修");
        }
        viewHolder.<TextView>getView(R.id.tv_comment).setText(item.getCate_pid_cn());
        viewHolder.<TextView>getView(R.id.tv_status).setText(item.getWork_status_cn());*/
    }
}
