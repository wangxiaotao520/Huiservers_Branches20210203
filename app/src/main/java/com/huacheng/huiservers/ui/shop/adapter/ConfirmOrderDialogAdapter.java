package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.shop.bean.ConfirmBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：配送方式
 * 时间：2019/11/14 15:19
 * created by DFF
 */
public class ConfirmOrderDialogAdapter extends CommonAdapter<ConfirmBean.DeliversBean> {
    ConfirmBean.DeliversBean selected_bean;
    public ConfirmOrderDialogAdapter(Context context, int layoutId, List<ConfirmBean.DeliversBean> datas, ConfirmBean.DeliversBean selected_bean) {
        super(context, layoutId, datas);
        this.selected_bean=selected_bean;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ConfirmBean.DeliversBean item, int position) {

        viewHolder.<TextView>getView(R.id.tv_style).setText(item.getName()+"( "+"¥ "+item.getDis_fee()+")");
        viewHolder.<TextView>getView(R.id.txt_address).setText("地址");
        if (item.getSign().equals(selected_bean.getSign())){
            viewHolder.<ImageView>getView(R.id.iv_select).setVisibility(View.VISIBLE);
        }else {
            viewHolder.<ImageView>getView(R.id.iv_select).setVisibility(View.GONE);
        }


    }
}
