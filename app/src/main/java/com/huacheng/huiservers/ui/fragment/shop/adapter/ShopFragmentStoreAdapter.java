package com.huacheng.huiservers.ui.fragment.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 商城首页商城Adapter
 * created by wangxiaotao
 * 2019/12/12 0012 上午 10:42
 */
public class ShopFragmentStoreAdapter extends CommonAdapter<String>{
    public ShopFragmentStoreAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {

        viewHolder.<LinearLayout>getView(R.id.ll_shop_img_container).setVisibility(View.VISIBLE);
        viewHolder.<LinearLayout>getView(R.id.ll_shop_img_container).removeAllViews();
        for (int i = 0; i <2 ; i++) {
            View item_img_view = LayoutInflater.from(mContext).inflate(R.layout.item_item_shop_goods, null);
            SimpleDraweeView sdv_nearby_food = item_img_view.findViewById(R.id.sdv_nearby_food);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight=1;
            item_img_view.setLayoutParams(params);
            viewHolder.<LinearLayout>getView(R.id.ll_shop_img_container).addView(item_img_view);
        }

    }
}
