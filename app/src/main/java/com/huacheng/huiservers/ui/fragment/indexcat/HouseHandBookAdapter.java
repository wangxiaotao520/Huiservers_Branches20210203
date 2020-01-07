package com.huacheng.huiservers.ui.fragment.indexcat;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelIndex;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：交房手册adapter
 * 时间：2019/12/17 12:01
 * created by DFF
 */
public class HouseHandBookAdapter extends CommonAdapter<ModelIndex> {

    public HouseHandBookAdapter(Context context, int layoutId, List<ModelIndex> datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelIndex item, int position) {
        GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL + item.getArticle_image(), viewHolder.<ImageView>getView(R.id.iv_tag), R.color.white);
        viewHolder.<TextView>getView(R.id.tv_title).setText(item.getTitle());
    }
}
