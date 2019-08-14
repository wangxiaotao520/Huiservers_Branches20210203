package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 新首页ListViewAdapter
 * created by wangxiaotao
 * 2018/12/20 0020 下午 4:46
 */
public class HomeListViewAdapter extends CommonAdapter<ModelShopIndex> {
    private OnAddCartClickListener listener;

    public HomeListViewAdapter(Context context, int layoutId, List datas, OnAddCartClickListener listener) {
        super(context, layoutId, datas);
        this.listener = listener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ModelShopIndex item, final int position) {
        //标记热卖 秒杀 上新
        viewHolder.<ImageView>getView(R.id.iv_shop_list_flag).setVisibility(View.VISIBLE);
        if (item.getDiscount().equals("1")) {
            viewHolder.<ImageView>getView(R.id.iv_shop_list_flag).setBackground(mContext.getResources().getDrawable(R.drawable.ic_shoplist_spike));
        } else {
            if (item.getIs_hot().equals("1")) {
                viewHolder.<ImageView>getView(R.id.iv_shop_list_flag).setBackground(mContext.getResources().getDrawable(R.drawable.ic_shoplist_hotsell));

            } else if (item.getIs_new().equals("1")) {
                viewHolder.<ImageView>getView(R.id.iv_shop_list_flag).setBackground(mContext.getResources().getDrawable(R.drawable.ic_shoplist_newest));
            } else {
                viewHolder.<ImageView>getView(R.id.iv_shop_list_flag).setBackground(null);

            }
        }
        GlideUtils.getInstance().glideLoad(mContext, MyCookieStore.URL + mDatas.get(position).getTitle_img(),
                viewHolder.<ImageView>getView(R.id.iv_title_img), R.drawable.ic_default_rectange);
        viewHolder.<TextView>getView(R.id.tv_title).setText(item.getTitle());
        //商品标签
        viewHolder.<LinearLayout>getView(R.id.lin_goodslist_Tag).removeAllViews();
        if (item.getGoods_tag() != null && item.getGoods_tag().size() > 0) {
            for (int i = 0; i < item.getGoods_tag().size(); i++) {
                if (i < 2) {
                    View view = LinearLayout.inflate(mContext, R.layout.shop_list_tag_item, null);
                    TextView tag1 = (TextView) view.findViewById(R.id.txt_tag1);
                    tag1.setText(mDatas.get(position).getGoods_tag().get(i).getC_name());
                    viewHolder.<LinearLayout>getView(R.id.lin_goodslist_Tag).addView(view);
                }
            }
        }
        //显示是否售罄
        if (NullUtil.isStringEmpty(item.getInventory()) || 0 >= Integer.valueOf(item.getInventory())) {
            viewHolder.<TextView>getView(R.id.tv_shouqing).setVisibility(View.VISIBLE);
        } else {
            viewHolder.<TextView>getView(R.id.tv_shouqing).setVisibility(View.GONE);
        }
        viewHolder.<TextView>getView(R.id.tv_shop_price).setText("¥" + item.getPrice());
        //是否有单位
        if (!NullUtil.isStringEmpty(item.getUnit())) {
            viewHolder.<TextView>getView(R.id.tv_shop_weight).setText("/" + item.getUnit());
        } else {
            viewHolder.<TextView>getView(R.id.tv_shop_weight).setText("");
        }
        viewHolder.<TextView>getView(R.id.tv_shop_price_original).setText("¥" + item.getOriginal());
        viewHolder.<TextView>getView(R.id.tv_shop_price_original).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.<TextView>getView(R.id.tv_orders_sold_num).setText("已售" + item.getOrder_num());

        viewHolder.<ImageView>getView(R.id.iv_shopcar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onAddCartClick(item);
                }
            }
        });
    }

    public interface OnAddCartClickListener {
        void onAddCartClick(ModelShopIndex item);
    }
}
