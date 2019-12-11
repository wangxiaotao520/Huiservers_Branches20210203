package com.huacheng.huiservers.ui.index.request.adapter;

import android.content.Context;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelRequest;
import com.huacheng.huiservers.utils.StringUtils;
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
     //   byte[] bytes = Base64.decode(item.getContent(), Base64.DEFAULT);
     viewHolder.<TextView>getView(R.id.tv_title).setText(item.getContent());
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getNickname()+"     "+item.getPhone());
        if (item.getStatus()==1){
            viewHolder.<TextView>getView(R.id.tv_status).setText("未处理");
            viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAddtime(),"8")+"");
            viewHolder.<TextView>getView(R.id.tv_status).setTextColor(mContext.getResources().getColor(R.color.red_bg));
        }else {
            viewHolder.<TextView>getView(R.id.tv_status).setText("已处理");
            viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAddtime(),"8")+" - "+StringUtils.getDateToString(item.getComplete_time(),"8"));
            viewHolder.<TextView>getView(R.id.tv_status).setTextColor(mContext.getResources().getColor(R.color.gray_99));
        }

    }
}

