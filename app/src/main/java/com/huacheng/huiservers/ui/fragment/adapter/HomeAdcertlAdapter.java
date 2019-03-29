package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelAds;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：首页精选商品广告adapter
 * 时间：2018/12/21 14:47
 * created by DFF
 */
public class HomeAdcertlAdapter extends CommonAdapter<ModelAds> {
    public HomeAdcertlAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ModelAds item, int position) {

        FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.adv_houseImg), ApiHttpClient.IMG_URL + item.getImg());

        int screenWidth = ToolUtils.getScreenWidth(mContext);
        viewHolder.getView(R.id.adv_houseImg).getLayoutParams().width = (screenWidth - DeviceUtils.dip2px(mContext, 35)) / 2;//屏幕的一半
        Double d = Double.valueOf(viewHolder.<SimpleDraweeView>getView(R.id.adv_houseImg).getLayoutParams().width / 1.5);
        viewHolder.getView(R.id.adv_houseImg).getLayoutParams().height = (new Double(d)).intValue();
        viewHolder.getView(R.id.adv_houseImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(item.getUrl())) {
                    if (item.getUrl_type().equals("0") || TextUtils.isEmpty(item.getUrl_type())) {
                         Jump jump = new Jump(mContext, item.getType_name(), item.getAdv_inside_url());
                    } else {
                        Jump  jump = new Jump(mContext,item.getUrl_type(), item.getType_name(), "", item.getUrl_type_cn());
                    }
                } else {//URL不为空时外链
                    Jump jump = new Jump(mContext, item.getUrl());

                }
            }
        });


    }
}
