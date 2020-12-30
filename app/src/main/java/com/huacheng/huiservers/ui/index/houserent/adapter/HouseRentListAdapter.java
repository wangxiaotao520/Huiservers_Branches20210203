package com.huacheng.huiservers.ui.index.houserent.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelMyHouseList;
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
public class HouseRentListAdapter extends CommonAdapter<ModelMyHouseList>{
    private int jump_type;
    public HouseRentListAdapter(Context context, int layoutId, List datas,int jump_type) {
        super(context, layoutId, datas);
        this.jump_type=jump_type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelMyHouseList item, int position) {
        viewHolder.getView(R.id.sdv_head).setVisibility(View.VISIBLE);
        FrescoUtils.getInstance().setImageUri((SimpleDraweeView) viewHolder.getView(R.id.sdv_head), StringUtils.getImgUrl(item.getHeadimg()+""));

//        viewHolder.<TextView>getView(R.id.tv_title).setText(item.getCommunity_name()+"   "
//        +item.getRoom()+"室"+item.getOffice()+"厅"+item.getKitchen()+"厨"+item.getGuard()+"卫");
        viewHolder.<TextView>getView(R.id.tv_title).setText(item.getCommunity()+"  "+item.getTitle());

        viewHolder.<TextView>getView(R.id.tv_sub_title).setText(item.getRoom()+"室"+item.getOffice()+"厅"+item.getKitchen()+"厨"+item.getGuard()+"卫"+"/"
                +item.getArea()+"平/"+item.getNumber()+"/"+item.getNumber_count());

        if (jump_type==1){
            //租房
            viewHolder.<TextView>getView(R.id.tv_price).setText(item.getRent()+"元/月");
//            viewHolder.<TextView>getView(R.id.tv_desc).setText("|"+item.getRoom()+"室"+item.getOffice()+"厅"+item.getKitchen()+"厨"+item.getGuard()+"卫|"+item.getArea()+"平米");
//            viewHolder.<TextView>getView(R.id.tv_desc).setVisibility(View.GONE);
        }else {
            // 售房
            viewHolder.<TextView>getView(R.id.tv_price).setText(item.getSelling()+"元");
            viewHolder.<TextView>getView(R.id.tv_desc).setText(item.getHouse_unit()+"元/平米");
        }
        if (item.getLabel_cn() != null && item.getLabel_cn().size() > 0) {

            for (int i = 0; i < item.getLabel_cn().size(); i++) {
                if (i>1){
                    return;
                }
                if (i==0){
                    viewHolder.<TextView>getView(R.id.tv_wuye_tag).setVisibility(View.GONE);
                    viewHolder.<TextView>getView(R.id.tv_tag).setText(item.getLabel_cn().get(0));
                }else {
                    viewHolder.<TextView>getView(R.id.tv_wuye_tag).setVisibility(View.VISIBLE);
                    viewHolder.<TextView>getView(R.id.tv_wuye_tag).setText(item.getLabel_cn().get(0));
                    viewHolder.<TextView>getView(R.id.tv_tag).setText(item.getLabel_cn().get(1));
                }

            }
        }else {
            viewHolder.<TextView>getView(R.id.tv_wuye_tag).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_tag).setVisibility(View.GONE);
        }

//        List<HouseRentDetail.LabelBean> label = item.getLabel();
//        if (label!=null&&label.size()>0){
//            boolean flag=false;
//            for (int i = 0; i < label.size(); i++) {
//                if (label.get(i).getLabel_name().equals("物业认证")){
//                    viewHolder.<TextView>getView(R.id.tv_wuye_tag).setVisibility(View.VISIBLE);
//                }else {
//                    if (flag==false){
//                        flag=true;
//                        //显示第一个
//                        viewHolder.<TextView>getView(R.id.tv_tag).setText(label.get(i).getLabel_name()+"");
//                    }
//
//                }
//            }
//        }

    }
}
