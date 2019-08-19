package com.huacheng.huiservers.ui.index.charge.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldFile;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：本次充电adapter
 * 时间：2019/8/19 9:42
 * created by DFF
 */
public class ChargeMessageListAdapter extends CommonAdapter<ModelOldFile> {

    public ChargeMessageListAdapter(Context context, int layoutId, List<ModelOldFile> datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelOldFile item, int position) {

        viewHolder.<TextView>getView(R.id.tv_order_time).setText("订单时间");
        viewHolder.<TextView>getView(R.id.tv_status).setText("充电开始or充电结束");
        viewHolder.<TextView>getView(R.id.tv_status_content).setText("您本次充电已结束");
        viewHolder.<TextView>getView(R.id.tv_order_bianhao).setText("订单编号");
        viewHolder.<TextView>getView(R.id.tv_zhandian).setText("站点名称");
        viewHolder.<TextView>getView(R.id.tv_zhandian).setText("站点名称");
        viewHolder.<TextView>getView(R.id.tv_chongdianzhuang).setText("充电桩编号");
        viewHolder.<TextView>getView(R.id.tv_chongdianzuo).setText("充电座编号");

        //充电结束
        viewHolder.<LinearLayout>getView(R.id.ly_end_info).setVisibility(View.VISIBLE);

        viewHolder.<TextView>getView(R.id.tv_start_end_time).setText("");
        viewHolder.<TextView>getView(R.id.tv_chongdian_time).setText("");
        viewHolder.<TextView>getView(R.id.tv_xiaofei_price).setText("");
        viewHolder.<TextView>getView(R.id.tv_tuikuan_content).setText("");//判断有无显示
        viewHolder.<TextView>getView(R.id.tv_biaozhun).setText("");
        viewHolder.<TextView>getView(R.id.tv_yuanyin).setText("");

        //充电开始
        viewHolder.<LinearLayout>getView(R.id.ly_start_info).setVisibility(View.GONE);
        viewHolder.<TextView>getView(R.id.tv_start_time).setText("");
        viewHolder.<TextView>getView(R.id.tv_yufu_price).setText("");
        viewHolder.<TextView>getView(R.id.tv_yuchong_time).setText("");


    }
}
