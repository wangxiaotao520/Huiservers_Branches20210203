package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.HouseRentDetail;
import com.huacheng.huiservers.ui.index.houserent.HouserentDetailActivity;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：首页房屋adapter
 * 时间：2018/12/21 14:47
 * created by DFF
 */
public class HouseRentSellAdapter extends CommonAdapter<HouseRentDetail> {
    public HouseRentSellAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, final HouseRentDetail item, int position) {
        // 1为租房，2为售房
        if (item.getHouse_type() == 1) {
            viewHolder.<ImageView>getView(R.id.iv_type).setBackground(mContext.getResources().getDrawable(R.mipmap.ic_rent_ing));
            viewHolder.<TextView>getView(R.id.tv_price).setText(item.getUnit_price() + "元/月");
        } else {
            viewHolder.<TextView>getView(R.id.tv_price).setText(item.getTotal_price() + "元");
            viewHolder.<ImageView>getView(R.id.iv_type).setBackground(mContext.getResources().getDrawable(R.mipmap.ic_sell_img));
        }
        FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.adv_houseImg), ApiHttpClient.IMG_URL + item.getHead_img());
        viewHolder.<TextView>getView(R.id.tv_address).setText(item.getCommunity_name() + "-"
                + item.getRoom() + "室" + item.getOffice() + "厅" + item.getKitchen() + "厨" + item.getGuard() + "卫" + "-面积"
                + item.getArea() + "平米|" + item.getHouse_floor() + "/" + item.getFloor());


        int screenWidth = ToolUtils.getScreenWidth(mContext);
        viewHolder.getView(R.id.adv_houseImg).getLayoutParams().width = (screenWidth - DeviceUtils.dip2px(mContext, 45)) / 2;//屏幕的一半
        Double d = viewHolder.getView(R.id.adv_houseImg).getLayoutParams().width / 1.5;
        viewHolder.getView(R.id.adv_houseImg).getLayoutParams().height = (new Double(d)).intValue();

        viewHolder.getView(R.id.ly_onclick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HouserentDetailActivity.class);
                // 1为租房，2为售房
                intent.putExtra("jump_type", item.getHouse_type());
                intent.putExtra("id", item.getId());
                mContext.startActivity(intent);
            }
        });
    }
}
