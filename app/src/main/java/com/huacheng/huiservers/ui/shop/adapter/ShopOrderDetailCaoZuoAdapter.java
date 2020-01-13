package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：商品订单详情
 * 时间：2020/1/9 15:36
 * created by DFF
 */
public class ShopOrderDetailCaoZuoAdapter extends CommonAdapter<BannerBean> {
    private int type;// 1 申请退款 3评价 2收货
    private OnClickShopDetailListener mListener;

    public ShopOrderDetailCaoZuoAdapter(Context context, int layoutId, List<BannerBean> datas, int type, OnClickShopDetailListener mListener) {
        super(context, layoutId, datas);
        this.type = type;
        this.mListener = mListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final BannerBean item, int position) {

        viewHolder.<TextView>getView(R.id.tv_btn).setVisibility(View.VISIBLE);
        viewHolder.<TextView>getView(R.id.tv_status).setVisibility(View.GONE);
        if (type == 1) { //申请退款的所有商品显示界面
            viewHolder.<TextView>getView(R.id.tv_btn).setText("申请退款");
        } else if (type == 3) { //待评价的所有商品显示界面
            viewHolder.<TextView>getView(R.id.tv_btn).setText("评价");

        } else if (type == 2) {//确认收货的所有商品显示界面
            viewHolder.<TextView>getView(R.id.tv_btn).setText("确认收货");
        }
        viewHolder.<TextView>getView(R.id.tv_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClickButton(type, item);
                }
            }
        });
        FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_one), MyCookieStore.URL + item.getP_title_img());
        viewHolder.<TextView>getView(R.id.tv_title_one).setText("" + item.getP_title());
        viewHolder.<TextView>getView(R.id.tv_sub_title_one).setText("" + item.getTagname());
        viewHolder.<TextView>getView(R.id.tv_shop_price_one).setText("¥ " + item.getPrice());
        viewHolder.<TextView>getView(R.id.tv_num_one).setText("× " + item.getNumber());
        viewHolder.<TextView>getView(R.id.tv_status).setVisibility(View.GONE);

    }

    public interface OnClickShopDetailListener {
        /**
         * 点击按钮
         *
         * @param type
         */
        void onClickButton(int type, BannerBean item);

    }
}
