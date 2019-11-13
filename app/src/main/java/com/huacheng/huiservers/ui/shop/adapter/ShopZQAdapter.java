package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：专区活动adapter
 * 时间：2019/11/13 17:15
 * created by DFF
 */
public class ShopZQAdapter extends CommonAdapter<BannerBean> {

    public ShopZQAdapter(Context context, int layoutId, List<BannerBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BannerBean item, int position) {
        viewHolder.<LinearLayout>getView(R.id.linear).setVisibility(View.GONE);

    }
}
