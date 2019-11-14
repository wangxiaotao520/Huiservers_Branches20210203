package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelZQInfo;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.CircularImage;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：专区活动adapter
 * 时间：2019/11/13 17:15
 * created by DFF
 */
public class ShopZQAdapter extends CommonAdapter<ModelZQInfo> {

    public ShopZQAdapter(Context context, int layoutId, List<ModelZQInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelZQInfo item, int position) {
        viewHolder.<LinearLayout>getView(R.id.linear).setVisibility(View.GONE);
        viewHolder.<TextView>getView(R.id.tv_title).setText(item.getTitle());
        viewHolder.<TextView>getView(R.id.tv_content).setText(item.getContent());
        viewHolder.<TextView>getView(R.id.tv_time).setText("发布于"+ StringUtils.getDateToString(item.getAddtime(),"1"));
        GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL + item.getImg(), viewHolder.<ImageView>getView(R.id.iv_img), R.drawable.ic_default_rectange);
        viewHolder.<CircularImage>getView(R.id.iv_head).setVisibility(View.GONE);
        viewHolder.<ImageView>getView(R.id.iv_2).setVisibility(View.GONE);
        viewHolder.<ImageView>getView(R.id.iv_1).setVisibility(View.GONE);
        viewHolder.<View>getView(R.id.view).setVisibility(View.VISIBLE);


    }
}
