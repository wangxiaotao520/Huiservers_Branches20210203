package com.huacheng.huiservers.ui.index.workorder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelWorkOrderList;
import com.huacheng.huiservers.utils.StringUtils;

import java.util.List;

/**
 * Description: 工单Adapter
 * Author: badge
 * Create: 2018/12/13 09:52
 */
public class AdapterWorkOrder<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mDatas;
    private int mType;

    public AdapterWorkOrder(Context context, List<T> datas, int type) {
        this.mContext = context;
        this.mDatas = datas;
        this.mType = type;
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.layout_work_order_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ModelWorkOrderList list = (ModelWorkOrderList) mDatas.get(i);

        String workTypeCN = list.getWork_type_cn();
        holder.tv_worder_type.setText(workTypeCN);

        String workStatusCN = list.getWork_status_cn();
        //0、3待付款，1待派单，2待服务，4待完工，
        //5已完工，evaluate_status1=已评价
        //6已取消
        String workStatus = list.getWork_status();

        String workStatusColor = "";
        if (("0".equals(workStatus) || ("3".equals(workStatus)))) {
            workStatusColor = "#ED2D2D";
        } else if (("1".equals(workStatus))) {
            workStatusColor = "#FF9F22";
        } else if (("2".equals(workStatus))) {
            workStatusColor = "#27C432";
        } else if (("4".equals(workStatus))) {
            workStatusColor = "#6983FD";
        } else if (("5".equals(workStatus))) {
            if ("1".equals(list.getEvaluate_status())) {
                workStatusColor = "#28CA43";
            } else {
                workStatusColor = "#FEA439";
            }
        } else if (("6".equals(workStatus))) {
            workStatusColor = "#9C9C9C";
        }
        if (!StringUtils.isEmpty(workStatusColor)) {
            holder.tv_worder_status.setTextColor(Color.parseColor(workStatusColor));
        }

        holder.tv_worder_status.setText(workStatusCN);
        holder.tv_repair_name.setText(list.getType_name());

        String time = list.getRelease_at();
        time = StringUtils.getDateToString(time, "8");
        holder.tv_order_generate_time.setText("下单时间：" + time);

        return convertView;

    }

    static class ViewHolder {

        TextView tv_worder_type, tv_worder_status, tv_repair_name, tv_order_generate_time;

        public ViewHolder(View convertView) {

            tv_worder_type = convertView.findViewById(R.id.tv_worder_type);
            tv_repair_name = convertView.findViewById(R.id.tv_repair_name);
            tv_worder_status = convertView.findViewById(R.id.tv_worder_status);
            tv_order_generate_time = convertView.findViewById(R.id.tv_order_generate_time);

        }
    }
}
