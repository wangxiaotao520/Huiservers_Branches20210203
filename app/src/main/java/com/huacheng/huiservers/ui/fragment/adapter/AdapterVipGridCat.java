package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.model.ModelVipIndex;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：vip 特权
 * 时间：2020/11/30 10:33
 * created by DFF
 */
public class AdapterVipGridCat extends CommonAdapter<ModelVipIndex> {

    public AdapterVipGridCat(Context context, int layoutId, List<ModelVipIndex> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelVipIndex item, int position) {
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getName());
        GlideUtils.getInstance().glideLoad(mContext, MyCookieStore.URL + item.getImg(),viewHolder.<ImageView>getView(R.id.iv_cat), R.drawable.ic_default);

    }
}
