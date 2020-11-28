package com.huacheng.huiservers.ui.vip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.MyAdapter;

/**
 * @author Created by changyadong on 2020/11/27
 * @description
 */
public class PointDetailAdapter extends MyAdapter {

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
           ViewHolder holder = null;
           if (view == null){
               holder = new ViewHolder();
               view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_point_detail,viewGroup,false);
               view.setTag(holder);
               holder.lineUp = view.findViewById(R.id.line_up);
               holder.lineDown = view.findViewById(R.id.line_down);
               holder.titleTx= view.findViewById(R.id.title);
               holder.orderNoTx= view.findViewById(R.id.order_no);
               holder.dateTx= view.findViewById(R.id.date);
               holder.amountTx= view.findViewById(R.id.amount);


           }else holder = (ViewHolder) view.getTag();




        return view;
    }

    @Override
    public int getCount() {
        return 10;
    }

    class ViewHolder{
        View lineUp,lineDown;
        TextView titleTx,orderNoTx,dateTx;
        TextView amountTx;
    }
}
