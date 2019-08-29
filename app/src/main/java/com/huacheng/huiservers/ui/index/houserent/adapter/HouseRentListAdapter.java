package com.huacheng.huiservers.ui.index.houserent.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.HouseRentDetail;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 租售房列表Adapter
 * created by wangxiaotao
 * 2018/11/6 0006 下午 6:55
 */
public class HouseRentListAdapter extends CommonAdapter<HouseRentDetail>{
    private int jump_type;
    public HouseRentListAdapter(Context context, int layoutId, List datas,int jump_type) {
        super(context, layoutId, datas);
        this.jump_type=jump_type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, HouseRentDetail item, int position) {
        viewHolder.getView(R.id.sdv_head).setVisibility(View.VISIBLE);
        FrescoUtils.getInstance().setImageUri((SimpleDraweeView) viewHolder.getView(R.id.sdv_head), StringUtils.getImgUrl(item.getHead_img()+""));

        viewHolder.<TextView>getView(R.id.tv_title).setText(item.getCommunity_name()+"-"
        +item.getRoom()+"室"+item.getOffice()+"厅"+item.getKitchen()+"厨"+item.getGuard()+"卫"+"-面积"
                +item.getArea()+"平米|"+item.getHouse_floor()+"/"+item.getFloor());
        List<HouseRentDetail.LabelBean> label = item.getLabel();
        if (label!=null&&label.size()>0){
            boolean flag=false;
            for (int i = 0; i < label.size(); i++) {
                if (label.get(i).getLabel_name().equals("物业认证")){
                    viewHolder.<TextView>getView(R.id.tv_wuye_tag).setVisibility(View.VISIBLE);
                }else {
                    if (flag==false){
                        flag=true;
                        //显示第一个
                        viewHolder.<TextView>getView(R.id.tv_tag).setText(label.get(i).getLabel_name()+"");
                    }

                }
            }
        }
        if (jump_type==1){
            //租房
            viewHolder.<TextView>getView(R.id.tv_price).setText(item.getUnit_price()+"元/月");
            viewHolder.<TextView>getView(R.id.tv_desc).setText("|"+item.getRoom()+"室"+item.getOffice()+"厅"+item.getKitchen()+"厨"+item.getGuard()+"卫|"+item.getArea()+"平米");
        }else {
           // 售房
            viewHolder.<TextView>getView(R.id.tv_price).setText(item.getTotal_price()+"元");
            viewHolder.<TextView>getView(R.id.tv_desc).setText(item.getUnit_price()+"元/平米");
        }
    }
}
