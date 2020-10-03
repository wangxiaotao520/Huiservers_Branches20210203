package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldFootmark;
import com.huacheng.huiservers.utils.StringUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：查看足迹
 * 时间：2020/10/3 11:59
 * created by DFF
 */
public class AdapterMyTrak extends CommonAdapter<ModelOldFootmark.PosBean> {
    public AdapterMyTrak(Context context, int layoutId, List<ModelOldFootmark.PosBean> datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelOldFootmark.PosBean item, int position) {

        viewHolder.<TextView>getView(R.id.tv_city).setText(item.getCity());

        if (item.getDetail() != null && item.getDetail().size() > 0) {

            viewHolder.<LinearLayout>getView(R.id.ly_zuji_view).removeAllViews();
            for (int i = 0; i < item.getDetail().size(); i++) {
                View trak_view = LayoutInflater.from(mContext).inflate(R.layout.item_my_trak_view, null);
                View view1 = trak_view.findViewById(R.id.view1);
                View view2 = trak_view.findViewById(R.id.view2);
                View view3 = trak_view.findViewById(R.id.view3);
                View view4 = trak_view.findViewById(R.id.view4);

                LinearLayout ly_tag1 = trak_view.findViewById(R.id.ly_tag1);
                LinearLayout ly_tag2 = trak_view.findViewById(R.id.ly_tag2);

                TextView tv_data = trak_view.findViewById(R.id.tv_data);
                TextView tv_address = trak_view.findViewById(R.id.tv_address);

                TextView tv_end_time = trak_view.findViewById(R.id.tv_end_time);
                TextView tv_start_time = trak_view.findViewById(R.id.tv_start_time);
                TextView tv_start_address = trak_view.findViewById(R.id.tv_start_address);
                TextView tv_end_address = trak_view.findViewById(R.id.tv_end_address);

                if (item.getDetail().get(i).getTag() == 1) {//如果是停留数据
                    ly_tag1.setVisibility(View.GONE);
                    ly_tag2.setVisibility(View.VISIBLE);
                    tv_start_time.setText(StringUtils.getDateToString(item.getDetail().get(i).getCT(), "4"));
                    tv_start_address.setText(item.getDetail().get(i).getDist() + item.getDetail().get(i).getStr());

                    tv_end_time.setText(StringUtils.getDateToString(item.getDetail().get(i).getUT(), "4"));
                    tv_end_address.setText(item.getDetail().get(i).getStopT());

                } else {
                    ly_tag1.setVisibility(View.VISIBLE);
                    ly_tag2.setVisibility(View.GONE);
                    tv_data.setText(StringUtils.getDateToString(item.getDetail().get(i).getCT(), "4"));
                    tv_address.setText("到   "+item.getDetail().get(i).getDist() + item.getDetail().get(i).getStr());
                }
                if (i == 0) {//
                    if (item.getDetail().get(i).getTag() == 1) {//判断第一条是否是停留数据
                        view3.setVisibility(View.GONE);
                    } else {
                        view1.setVisibility(View.GONE);
                    }
                }
                if (i == item.getDetail().size() - 1) {
                    if (item.getDetail().get(i).getTag() == 1) {//判断最后一条是否是停留数据
                        view4.setVisibility(View.GONE);
                    } else {
                        view2.setVisibility(View.GONE);
                    }
                }

                viewHolder.<LinearLayout>getView(R.id.ly_zuji_view).addView(trak_view);
            }
        } else {
            viewHolder.<LinearLayout>getView(R.id.ly_zuji_view).setVisibility(View.GONE);
        }
    }

}
