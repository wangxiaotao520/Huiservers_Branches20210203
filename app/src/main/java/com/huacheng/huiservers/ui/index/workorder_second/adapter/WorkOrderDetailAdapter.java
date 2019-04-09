package com.huacheng.huiservers.ui.index.workorder_second.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelWorkOrderList;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：
 * 时间：2019/4/9 10:54
 * created by DFF
 */
public class WorkOrderDetailAdapter extends CommonAdapter<ModelWorkOrderList> {

    public WorkOrderDetailAdapter(Context context, int layoutId, List<ModelWorkOrderList> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelWorkOrderList item, int position) {

        viewHolder.<TextView>getView(R.id.tv_status_name).setText("");
        viewHolder.<TextView>getView(R.id.tv_time).setText("");
        viewHolder.<TextView>getView(R.id.tv_name).setText("");
        viewHolder.<TextView>getView(R.id.tv_person_type).setText("");
        viewHolder.<TextView>getView(R.id.tv_photo).setText("");
        viewHolder.<TextView>getView(R.id.tv_photo).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
        viewHolder.<TextView>getView(R.id.tv_caozuo_time).setText("");
        viewHolder.<LinearLayout>getView(R.id.linear_repair_photo).removeAllViews();
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,
                    100);//两个50分别为添加图片的大小
            imageView.setImageResource(R.drawable.ic_launcher);
            imageView.setPadding(0, 0, 30, 0);
            imageView.setLayoutParams(params);
            viewHolder.<LinearLayout>getView(R.id.linear_repair_photo).addView(imageView);
        }

        if (position==0){
            viewHolder.<ImageView>getView(R.id.iv_tag).setBackgroundResource(R.mipmap.icon_workorder_yuan_onclick);
            viewHolder.<TextView>getView(R.id.tv_tag).setBackgroundResource(R.color.orange);
        }else {
            viewHolder.<ImageView>getView(R.id.iv_tag).setBackgroundResource(R.mipmap.icon_workorder_yuan);
            viewHolder.<TextView>getView(R.id.tv_tag).setBackgroundResource(R.color.graynew4);
        }
    }

}
