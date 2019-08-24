package com.huacheng.huiservers.ui.index.charge.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelChargeMessage;
import com.huacheng.huiservers.utils.StringUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：本次充电adapter
 * 时间：2019/8/19 9:42
 * created by DFF
 */
public class ChargeMessageListAdapter extends CommonAdapter<ModelChargeMessage> {

    public ChargeMessageListAdapter(Context context, int layoutId, List<ModelChargeMessage> datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelChargeMessage item, int position) {

        viewHolder.<TextView>getView(R.id.tv_order_time).setText(StringUtils.getDateToString(item.getStart_time(),"7")+"");
        if ("1".equals(item.getType())){
            //充电开始
            viewHolder.<LinearLayout>getView(R.id.ly_start_info).setVisibility(View.VISIBLE);
            viewHolder.<LinearLayout>getView(R.id.ly_end_info).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_status).setText("充电开始");
            viewHolder.<TextView>getView(R.id.tv_status_content).setText("您本次充电已开始");
            viewHolder.<TextView>getView(R.id.tv_status_content).setTextColor(Color.parseColor("#4997FE"));
            viewHolder.<TextView>getView(R.id.tv_order_bianhao).setText(""+item.getOrder_num());
            viewHolder.<TextView>getView(R.id.tv_start_time).setText(StringUtils.getDateToString(item.getStart_time(),"10")+"");
            viewHolder.<TextView>getView(R.id.tv_yufu_price).setText(item.getOrder_price()+"元");
            viewHolder.<TextView>getView(R.id.tv_yuchong_time).setText(item.getTimes()+"小时");


        }else {
            //充电结束
            viewHolder.<LinearLayout>getView(R.id.ly_start_info).setVisibility(View.GONE);
            viewHolder.<LinearLayout>getView(R.id.ly_end_info).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_status).setText("充电结束");
            viewHolder.<TextView>getView(R.id.tv_status_content).setText("您本次充电已结束");
            viewHolder.<TextView>getView(R.id.tv_status_content).setTextColor(Color.parseColor("#E0473F"));
            viewHolder.<TextView>getView(R.id.tv_order_bianhao).setText(""+item.getOrder_num());


            viewHolder.<TextView>getView(R.id.tv_start_end_time).setText(StringUtils.getDateToString(item.getStart_time(),"10")+" - "+StringUtils.getDateToString(item.getEnd_time(),"10"));
            viewHolder.<TextView>getView(R.id.tv_chongdian_time).setText(item.getReality_times()+"小时");
            viewHolder.<TextView>getView(R.id.tv_xiaofei_price).setText(item.getReality_price()+"元");
            viewHolder.<TextView>getView(R.id.tv_tuikuan_content).setText("退款金额"+item.getCancel_price()+"元");//判断有无显示
            viewHolder.<TextView>getView(R.id.tv_biaozhun).setText(item.getPrice()+"");
            viewHolder.<TextView>getView(R.id.tv_yuanyin).setText(item.getEnd_reason()+"");
        }



        viewHolder.<TextView>getView(R.id.tv_zhandian).setText(""+item.getGtel_cn());
        viewHolder.<TextView>getView(R.id.tv_chongdianzhuang).setText(""+item.getGtel());
        viewHolder.<TextView>getView(R.id.tv_chongdianzuo).setText(""+item.getTd());






    }
}
