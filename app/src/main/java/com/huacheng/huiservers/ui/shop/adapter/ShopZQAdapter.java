package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelZQInfo;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
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
        viewHolder.<LinearLayout>getView(R.id.ly_user).setVisibility(View.GONE);
        viewHolder.<TextView>getView(R.id.tv_title).setText(item.getTitle());
        viewHolder.<TextView>getView(R.id.tv_catname).setText(StringUtils.getDateToString(item.getAddtime(), "7"));
        if (!NullUtil.isStringEmpty(item.getImg())) {
            viewHolder.<SimpleDraweeView>getView(R.id.sdv_head).setVisibility(View.VISIBLE);
            FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_head), ApiHttpClient.IMG_URL + item.getImg());
        } else {
            viewHolder.<SimpleDraweeView>getView(R.id.sdv_head).setVisibility(View.GONE);
        }
        viewHolder.<TextView>getView(R.id.tv_readnum).setVisibility(View.GONE);
        viewHolder.<TextView>getView(R.id.tv_addtime).setVisibility(View.GONE);

    }
}
