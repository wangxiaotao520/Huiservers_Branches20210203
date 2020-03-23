package com.huacheng.huiservers.ui.index.houserent.adapter;

import android.content.Context;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.HouseRentTagListBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 筛选条件adapter
 * created by wangxiaotao
 * 2018/11/6 0006 下午 7:44
 */
public class HouseRentTagAdapter extends CommonAdapter <HouseRentTagListBean>{


    public HouseRentTagAdapter(Context context, int layoutId, List<HouseRentTagListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, HouseRentTagListBean item, int position) {
        if (item.isSelected()){
            viewHolder.<TextView>getView(R.id.tv_name).setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            if (item.getBean_type()==0){
                viewHolder.<TextView>getView(R.id.tv_name).setText(item.getPrice()+"");
            }else if (item.getBean_type()==1) {
                viewHolder.<TextView>getView(R.id.tv_name).setText(item.getSize()+"");
            }else if (item.getBean_type()==2){
                viewHolder.<TextView>getView(R.id.tv_name).setText(item.getType()+"");
            }else if (item.getBean_type()==3){
                viewHolder.<TextView>getView(R.id.tv_name).setText(item.getStatus()+"");
            }
        }else {
            viewHolder.<TextView>getView(R.id.tv_name).setTextColor(mContext.getResources().getColor(R.color.title_sub_color));
            if (item.getBean_type()==0){
                viewHolder.<TextView>getView(R.id.tv_name).setText(item.getPrice()+"");
            }else if (item.getBean_type()==1) {
                viewHolder.<TextView>getView(R.id.tv_name).setText(item.getSize()+"");
            }else if (item.getBean_type()==2){
                viewHolder.<TextView>getView(R.id.tv_name).setText(item.getType()+"");
            }else if (item.getBean_type()==3){
                viewHolder.<TextView>getView(R.id.tv_name).setText(item.getStatus()+"");
            }
        }

    }
}
