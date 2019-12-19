package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.model.ModelCircle;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：邻里首页adapter
 * 时间：2019/12/18 10:29
 * created by DFF
 */
public class CircleListAdapter extends CommonAdapter<ModelCircle> {

    public CircleListAdapter(Context context, int layoutId, List<ModelCircle> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelCircle item, int position) {
        if ("0".equals(item.getAdmin_id())) {//0为用户发布的
            viewHolder.<LinearLayout>getView(R.id.ly_admin).setVisibility(View.GONE);
            viewHolder.<LinearLayout>getView(R.id.ly_user).setVisibility(View.VISIBLE);
        } else {
            viewHolder.<LinearLayout>getView(R.id.ly_admin).setVisibility(View.VISIBLE);
            viewHolder.<LinearLayout>getView(R.id.ly_user).setVisibility(View.GONE);

            byte[] bytes1 = Base64.decode(item.getTitle(), Base64.DEFAULT);
            viewHolder.<TextView>getView(R.id.tv_title).setText(new String(bytes1));
            viewHolder.<TextView>getView(R.id.tv_catname).setText(item.getC_name());
            viewHolder.<TextView>getView(R.id.tv_readnum).setText(item.getClick() + "阅读");
            viewHolder.<TextView>getView(R.id.tv_addtime).setText(item.getAddtime());

            if (item.getImg_list() != null && item.getImg_list().size() > 0) {
                if (!TextUtils.isEmpty(item.getImg_list().get(0).getImg())) {
                    viewHolder.<SimpleDraweeView>getView(R.id.sdv_head).setVisibility(View.VISIBLE);
                    FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_head), MyCookieStore.URL + item.getImg_list().get(0).getImg());
                } else {
                    viewHolder.<SimpleDraweeView>getView(R.id.sdv_head).setVisibility(View.GONE);
                }

            } else {
                viewHolder.<SimpleDraweeView>getView(R.id.sdv_head).setVisibility(View.GONE);
            }

        }

    }
}
