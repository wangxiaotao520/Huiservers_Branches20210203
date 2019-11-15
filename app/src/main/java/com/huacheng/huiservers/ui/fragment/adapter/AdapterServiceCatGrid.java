package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceCat;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description:服务分类grid adapter
 * created by wangxiaotao
 * 2019/11/14 0014 上午 10:46
 */
public class AdapterServiceCatGrid extends CommonAdapter<ModelServiceCat.GridBean> {
    public AdapterServiceCatGrid(Context context, int layoutId, List<ModelServiceCat.GridBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelServiceCat.GridBean item, int position) {
        viewHolder.<TextView>getView(R.id.item_name).setText(item.getName()+"");
        viewHolder.<TextView>getView(R.id.item_name).setTextColor(mContext.getResources().getColor(R.color.title_color));
        GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL+item.getImg(),viewHolder.<ImageView>getView(R.id.item_image),R.color.windowbackground);

    }
}
