package com.huacheng.huiservers.ui.fragment.shop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 商城首页common Adapter
 * created by wangxiaotao
 * 2018/12/21 0021 下午 1:20
 */
public class ShopCommonAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDatas=new ArrayList<>();
    private OnClickCallback listener;
    public ShopCommonAdapter(Context mContext, List<T> mDatas,OnClickCallback listener) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.listener=listener;
    }


    @Override
    public int getCount() {

        return (int)Math.ceil(mDatas.size()*1f/2);
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.layout_shop_common_item,null);
            viewHolder=new ViewHolder();
            initViewHolder(convertView,viewHolder);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        // 从数据里取值 第一个View
        if (position*2<mDatas.size()){
            ModelShopIndex item = (ModelShopIndex) mDatas.get(position*2);
            viewHolder. view_goods1.setVisibility(View.VISIBLE);

            GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL+item.getTitle_thumb_img(),viewHolder.item_image1,R.drawable.ic_default_rectange);
            ViewGroup.LayoutParams layoutParams = viewHolder.item_image1.getLayoutParams();
            layoutParams.width= ( DeviceUtils.getWindowWidth(mContext)-DeviceUtils.dip2px(mContext,(float) (16+16+6+20)))/2;
            layoutParams.height=layoutParams.width;
            viewHolder.item_image1.setLayoutParams(layoutParams);
            viewHolder. tv_tag1_first.setVisibility(View.INVISIBLE);
            viewHolder. tv_tag2_first.setVisibility(View.INVISIBLE);
            if (item.getGoods_tag()!=null&&item.getGoods_tag().size()>0){
                List<ModelShopIndex> goods_tag = item.getGoods_tag();
                for (int i = 0; i < goods_tag.size(); i++) {
                    if (i==0){
                        viewHolder. tv_tag1_first.setVisibility(View.VISIBLE);
                        viewHolder. tv_tag1_first .setText(goods_tag.get(i).getC_name()+"");
                    }else if (i==1){
                        viewHolder. tv_tag2_first.setVisibility(View.VISIBLE);
                        viewHolder. tv_tag2_first .setText(goods_tag.get(i).getC_name()+"");
                    }
                }
            }
            viewHolder. txt_shop_price1.setText("¥"+item.getPrice());
            viewHolder. item_name1.setText(item.getTitle()+"");
            viewHolder.txt_shop_unit1.setText("/"+item.getUnit());
            viewHolder.txt_orangil1.setText(item.getOriginal());
            viewHolder.txt_orangil1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.iv_gouwu1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onClickShopCart(position*2);
                    }
                }
            });
            viewHolder.view_goods1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onClickImage(position*2);
                    }
                }
            });

            if ("1".equals(item.getDiscount())) {
                viewHolder.iv_shop_list_flag1.setBackgroundResource(R.drawable.ic_shoplist_spike);
            } else {
                if (item.getIs_hot().equals("1")) {
                    viewHolder.iv_shop_list_flag1.setBackgroundResource(R.drawable.ic_shoplist_hotsell);
                } else if (item.getIs_new().equals("1")) {
                    viewHolder.iv_shop_list_flag1.setBackgroundResource(R.drawable.ic_shoplist_newest);
                } else {
                    viewHolder.iv_shop_list_flag1.setBackgroundColor(mContext.getResources().getColor(R.color.transparents));
                }
            }
        }
        if (position*2+1<mDatas.size()){
            ModelShopIndex item = (ModelShopIndex) mDatas.get(position*2+1);
            viewHolder. view_goods2.setVisibility(View.VISIBLE);
            GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL+item.getTitle_thumb_img(),viewHolder.item_image2,R.drawable.ic_default_rectange);
            ViewGroup.LayoutParams layoutParams = viewHolder.item_image2.getLayoutParams();
            layoutParams.width=( DeviceUtils.getWindowWidth(mContext)-DeviceUtils.dip2px(mContext,(float) (16+16+6+20)))/2;
            layoutParams.height=layoutParams.width;
            viewHolder.item_image2.setLayoutParams(layoutParams);
            viewHolder. tv_tag1_second.setVisibility(View.INVISIBLE);
            viewHolder. tv_tag2_second.setVisibility(View.INVISIBLE);
            if (item.getGoods_tag()!=null&&item.getGoods_tag().size()>0){
                List<ModelShopIndex> goods_tag = item.getGoods_tag();
                for (int i = 0; i < goods_tag.size(); i++) {
                    if (i==0){
                        viewHolder. tv_tag1_second.setVisibility(View.VISIBLE);
                        viewHolder. tv_tag1_second .setText(goods_tag.get(i).getC_name()+"");
                    }else if (i==1){
                        viewHolder. tv_tag2_second.setVisibility(View.VISIBLE);
                        viewHolder. tv_tag2_second .setText(goods_tag.get(i).getC_name()+"");
                    }
                }
            }
            viewHolder. txt_shop_price2.setText("¥"+item.getPrice());
            viewHolder. item_name2.setText(item.getTitle()+"");
            viewHolder.txt_shop_unit2.setText("/"+item.getUnit());
            viewHolder.txt_orangil2.setText(item.getOriginal());
            viewHolder.txt_orangil2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.iv_gouwu2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onClickShopCart(position*2+1);
                    }
                }
            });
            viewHolder.view_goods2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onClickImage(position*2+1);
                    }
                }
            });
            if ("1".equals(item.getDiscount())) {
                viewHolder.iv_shop_list_flag2.setBackgroundResource(R.drawable.ic_shoplist_spike);
            } else {
                if (item.getIs_hot().equals("1")) {
                    viewHolder.iv_shop_list_flag2.setBackgroundResource(R.drawable.ic_shoplist_hotsell);
                } else if (item.getIs_new().equals("1")) {
                    viewHolder.iv_shop_list_flag2.setBackgroundResource(R.drawable.ic_shoplist_newest);
                } else {
                    viewHolder.iv_shop_list_flag2.setBackgroundColor(mContext.getResources().getColor(R.color.transparents));
                }
            }
        }else {
            viewHolder. view_goods2.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void initViewHolder(View v, ViewHolder viewHolder) {
        //第一个商品布局
        View view_goods1 = v.findViewById(R.id.ll_view_goods1);
        viewHolder.view_goods1=view_goods1;
        viewHolder. iv_shop_list_flag1 = (ImageView) view_goods1.findViewById(R.id.iv_shop_list_flag);
        viewHolder. ly_onclick1 = (LinearLayout) view_goods1.findViewById(R.id.ly_onclick);
        viewHolder. item_image1 = (ImageView) view_goods1.findViewById(R.id.item_image);
        viewHolder.  iv_gouwu1 = (ImageView) view_goods1.findViewById(R.id.iv_gouwu);
        viewHolder. item_name1 = (TextView)view_goods1.findViewById(R.id.item_name);
        viewHolder. txt_shop_price1 = (TextView) view_goods1.findViewById(R.id.txt_shop_price);
        viewHolder.  txt_shop_unit1 = (TextView) view_goods1.findViewById(R.id.txt_shop_unit);
        viewHolder.  txt_orangil1 = (TextView) view_goods1.findViewById(R.id.txt_orangil);
        viewHolder.  tv_tag1_first = (TextView) view_goods1.findViewById(R.id.tv_tag1);
        viewHolder.  tv_tag2_first = (TextView) view_goods1.findViewById(R.id.tv_tag2);
        //第二个商品的布局
        View view_goods2 = v.findViewById(R.id.ll_view_goods2);
        viewHolder.view_goods2=view_goods2;
        viewHolder. iv_shop_list_flag2 = (ImageView) view_goods2.findViewById(R.id.iv_shop_list_flag);
        viewHolder.ly_onclick2 = (LinearLayout) view_goods2.findViewById(R.id.ly_onclick);
        viewHolder.item_image2 = (ImageView) view_goods2.findViewById(R.id.item_image);
        viewHolder. iv_gouwu2 = (ImageView) view_goods2.findViewById(R.id.iv_gouwu);
        viewHolder. item_name2 = (TextView)view_goods2.findViewById(R.id.item_name);
        viewHolder. txt_shop_price2 = (TextView) view_goods2.findViewById(R.id.txt_shop_price);
        viewHolder. txt_shop_unit2 = (TextView) view_goods2.findViewById(R.id.txt_shop_unit);
        viewHolder. txt_orangil2 = (TextView) view_goods2.findViewById(R.id.txt_orangil);
        viewHolder. tv_tag1_second = (TextView) view_goods2.findViewById(R.id.tv_tag1);
        viewHolder. tv_tag2_second = (TextView) view_goods2.findViewById(R.id.tv_tag2);
    }

    private class  ViewHolder {
        LinearLayout  ly_onclick1;
        ImageView item_image1, iv_gouwu1, iv_shop_list_flag1;
        TextView item_name1, txt_shop_price1, txt_orangil1, txt_shop_unit1, tv_tag1_first, tv_tag2_first;

        LinearLayout  ly_onclick2;
        ImageView item_image2, iv_gouwu2, iv_shop_list_flag2;
        TextView item_name2, txt_shop_price2, txt_orangil2, txt_shop_unit2, tv_tag1_second, tv_tag2_second;

        View view_goods1;
        View view_goods2;
    }
    public interface  OnClickCallback{
        /**
         * 点击购物车
         * @param position
         */
        void onClickShopCart(int position);
        /**
         * 点击Image
         */
        void onClickImage(int position);
    }
}
