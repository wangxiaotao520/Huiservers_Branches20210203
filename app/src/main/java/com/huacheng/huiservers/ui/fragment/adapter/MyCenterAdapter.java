package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.PersoninfoBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：个人中心我的服务
 * 时间：2019/12/12 15:50
 * created by DFF
 */
public class MyCenterAdapter extends CommonAdapter<PersoninfoBean> {
    public MyCenterAdapter(Context context, int layoutId, List<PersoninfoBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, PersoninfoBean item, int position) {
        if (position==0){
            viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_center_shopcar);
        }else if (position==1){
            viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_center_shoporder);
        }else if (position==2){
            viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_center_serviceorder);
        }else if (position==3){
            viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_center_lifeorder);
        }else if (position==4){
            viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_center_houserent);
        }else if (position==5){
            viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_center_coupon);
        }else if (position==6){
            viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_center_invite);
        }
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getFullname());

    }
}
