package com.huacheng.huiservers.ui.fragment;

import android.content.Context;
import android.widget.ImageView;

import com.huacheng.huiservers.R;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 新版商城首页
 * created by wangxiaotao
 * 2019/12/13 0013 上午 10:06
 */
public class AdapterShopIndexGridCate  extends CommonAdapter<String>{
    public AdapterShopIndexGridCate(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        GlideUtils.getInstance().glideLoad(mContext,"",viewHolder.<ImageView>getView(R.id.iv_img),R.color.default_color);

    }
}
