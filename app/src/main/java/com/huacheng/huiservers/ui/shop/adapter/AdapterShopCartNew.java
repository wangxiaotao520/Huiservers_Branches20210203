package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.ui.shop.bean.DataBean;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description:新版购物车
 * created by wangxiaotao
 * 2019/11/16 0016 下午 4:27
 */
public class AdapterShopCartNew extends CommonAdapter<DataBean> {
    private OnClickShopCartListener listener;


    private String selected_m_id = ""; //选中的id


    private int status = 1; //1.结算 2.编辑
    public AdapterShopCartNew(Context context, int layoutId, List<DataBean> datas,OnClickShopCartListener listener,String selected_m_id,int status) {
        super(context, layoutId, datas);
        this.listener=listener;
        this.selected_m_id = selected_m_id;
        this.status=status;
    }

    @Override
    protected void convert(ViewHolder viewHolder, DataBean item, final int position) {
        if (item.isFirstPosition()){
            viewHolder.<LinearLayout>getView(R.id.ll_m_title).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_m_name).setText(item.getM_iname());
        }else {
            viewHolder.<LinearLayout>getView(R.id.ll_m_title).setVisibility(View.GONE);
        }
        viewHolder.<TextView>getView(R.id.tv_intro).setText(item.getTitle());
        viewHolder.<TextView>getView(R.id.tv_price).setText("¥ " + item.getPrice());
        viewHolder.<TextView>getView(R.id.tv_num).setText(item.getNumber() + "");
       // GlideUtils.getInstance().glideLoad(mContext,MyCookieStore.URL + item.getTitle_img(),viewHolder.<ImageView>getView(R.id.iv_adapter_list_pic),R.color.windowbackground);

        FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.iv_adapter_list_pic),MyCookieStore.URL + item.getTitle_img());

        if (item.isChecked()){
            viewHolder.<ImageView>getView(R.id.iv_check).setBackgroundResource(R.drawable.icon_shop_onclick);
        }else {
            viewHolder.<ImageView>getView(R.id.iv_check).setBackgroundResource(R.drawable.shape_oval_grey);
        }

        viewHolder.<TextView>getView( R.id.txt_type).setText(item.getTagname());
        viewHolder.getView(R.id.tv_reduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClickReduce(position);
                }
            }
        });
        viewHolder.getView(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClickAdd(position);
                }
            }
        });
        viewHolder.getView(R.id.ll_goods_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClickItem(position);
                }
            }
        });
        //变化选中状态的颜色
        if (NullUtil.isStringEmpty(selected_m_id)){
            viewHolder.<TextView>getView(R.id.tv_m_name).setTextColor(Color.parseColor("#333333"));
            viewHolder.<TextView>getView(R.id.tv_intro).setTextColor(Color.parseColor("#333333"));
            viewHolder.<TextView>getView(R.id.tv_price).setTextColor(Color.parseColor("#FF625E"));
            viewHolder.<TextView>getView(R.id.tv_num).setTextColor(Color.parseColor("#333333"));
            viewHolder.<TextView>getView(R.id.txt_type).setTextColor(mContext.getResources().getColor(R.color.gray_99));
        }else {
            if (item.getM_id().equals(selected_m_id)){
                viewHolder.<TextView>getView(R.id.tv_m_name).setTextColor(Color.parseColor("#333333"));
                viewHolder.<TextView>getView(R.id.tv_intro).setTextColor(Color.parseColor("#333333"));
                viewHolder.<TextView>getView(R.id.tv_price).setTextColor(Color.parseColor("#FF625E"));
                viewHolder.<TextView>getView(R.id.tv_num).setTextColor(Color.parseColor("#333333"));
                viewHolder.<TextView>getView(R.id.txt_type).setTextColor(mContext.getResources().getColor(R.color.gray_99));
            }else {
                viewHolder.<TextView>getView(R.id.tv_m_name).setTextColor(mContext.getResources().getColor(R.color.gray_D2));
                viewHolder.<TextView>getView(R.id.tv_intro).setTextColor(mContext.getResources().getColor(R.color.gray_D2));
                viewHolder.<TextView>getView(R.id.tv_price).setTextColor(mContext.getResources().getColor(R.color.gray_D2));
                viewHolder.<TextView>getView(R.id.tv_num).setTextColor(mContext.getResources().getColor(R.color.gray_D2));
                viewHolder.<TextView>getView(R.id.txt_type).setTextColor(mContext.getResources().getColor(R.color.gray_D2));
            }
        }
        if (position==mDatas.size()-1){
            viewHolder.<View>getView(R.id.view_bottom).setVisibility(View.VISIBLE);
        }else {
            viewHolder.<View>getView(R.id.view_bottom).setVisibility(View.GONE);
        }

    }

    public String getSelected_m_id() {
        return selected_m_id;
    }

    public void setSelected_m_id(String selected_m_id) {
        this.selected_m_id = selected_m_id;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
