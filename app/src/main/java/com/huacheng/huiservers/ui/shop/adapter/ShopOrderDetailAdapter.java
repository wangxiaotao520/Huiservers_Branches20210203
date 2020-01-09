package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.center.bean.XorderDetailBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：商品订单详情
 * 时间：2020/1/9 15:36
 * created by DFF
 */
public class ShopOrderDetailAdapter extends CommonAdapter<XorderDetailBean> {
    private int type;//0详情界面 1 申请退款 2 评价
    private OnClickShopDetailListener mListener;

    public ShopOrderDetailAdapter(Context context, int layoutId, List<XorderDetailBean> datas, int type, OnClickShopDetailListener mListener) {
        super(context, layoutId, datas);
        this.type = type;
        this.mListener = mListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final XorderDetailBean item, int position) {
        viewHolder.<TextView>getView(R.id.tv_btn).setVisibility(View.VISIBLE);
        if (type == 0) {
            viewHolder.<TextView>getView(R.id.tv_store_name).setText("商家的名称");
            viewHolder.<TextView>getView(R.id.tv_store_name).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_btn).setText("加购物车");
            viewHolder.<TextView>getView(R.id.tv_btn).setTextColor(mContext.getResources().getColor(R.color.grey));
            viewHolder.<TextView>getView(R.id.tv_btn).setBackgroundResource(R.drawable.allshape_stroke_grey_bb35);
        } else if (type == 1) {
            viewHolder.<TextView>getView(R.id.tv_btn).setText("申请退款");
            viewHolder.<TextView>getView(R.id.tv_btn).setTextColor(mContext.getResources().getColor(R.color.red_guotao));
            viewHolder.<TextView>getView(R.id.tv_btn).setBackgroundResource(R.drawable.allshape_stroke_red_guotao35);
        } else if (type == 2) {
            viewHolder.<TextView>getView(R.id.tv_btn).setText("评价");
            viewHolder.<TextView>getView(R.id.tv_btn).setTextColor(mContext.getResources().getColor(R.color.red_guotao));
            viewHolder.<TextView>getView(R.id.tv_btn).setBackgroundResource(R.drawable.allshape_stroke_red_guotao35);
        }
        viewHolder.<TextView>getView(R.id.tv_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClickButton(type, item);
                }
            }
        });


    }

    public interface OnClickShopDetailListener {
        /**
         * 点击按钮
         *
         * @param type
         */
        void onClickButton(int type, XorderDetailBean item);

    }
}
