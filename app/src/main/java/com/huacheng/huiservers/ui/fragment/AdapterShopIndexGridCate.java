package com.huacheng.huiservers.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.ui.shop.ShopCateActivity;
import com.huacheng.huiservers.ui.shop.ShopListActivity;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 新版商城首页
 * created by wangxiaotao
 * 2019/12/13 0013 上午 10:06
 */
public class AdapterShopIndexGridCate  extends CommonAdapter<ModelShopIndex>{
    public AdapterShopIndexGridCate(Context context, int layoutId, List<ModelShopIndex> datas) {
        super(context, layoutId, datas);
    }
    @Override
    public int getCount() {
        if (mDatas.size() <= 9) {

            return mDatas.size();

        } else if (mDatas.size() >= 10) {

            return 10;

        } else
            return mDatas.size();

    }
    @Override
    protected void convert(ViewHolder viewHolder, final ModelShopIndex item, int position) {
        if (mDatas.size() <= 9) {
            GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL +item.getIcon(),viewHolder.<ImageView>getView(R.id.iv_img),R.color.default_color);



            viewHolder.<TextView>getView(R.id.tv_name).setText(item.getCate_name());

            viewHolder.<LinearLayout>getView(R.id.ly_onclick).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ShopListActivity.class);
                    intent.putExtra("cateID", item.getId());
                    mContext.startActivity(intent);

                }
            });

        } else if (mDatas.size() >= 10) {
            if (position == 9) {
                viewHolder.<TextView>getView(R.id.tv_name).setText("全部");
                viewHolder.<ImageView>getView(R.id.iv_img).setImageResource(R.drawable.iv_icon_all);
                viewHolder.<LinearLayout>getView(R.id.ly_onclick).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ShopCateActivity.class);
                        mContext.startActivity(intent);

                    }
                });
            } else {
                GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL +item.getIcon(),viewHolder.<ImageView>getView(R.id.iv_img),R.color.default_color);



                viewHolder.<TextView>getView(R.id.tv_name).setText(item.getCate_name());

                viewHolder.<LinearLayout>getView(R.id.ly_onclick).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ShopListActivity.class);
                        intent.putExtra("cateID", item.getId());
                        mContext.startActivity(intent);

                    }
                });
            }
        }
    }
}
