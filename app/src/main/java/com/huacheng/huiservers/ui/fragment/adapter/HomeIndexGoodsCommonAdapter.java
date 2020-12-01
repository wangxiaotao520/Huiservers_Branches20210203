package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.view.MyImageSpan;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.stx.xhb.xbanner.OnDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 首页下方列表
 * created by wangxiaotao
 * 2019/12/10 0010 下午 5:48
 */
public class HomeIndexGoodsCommonAdapter <T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDatas=new ArrayList<>();
    private OnClickCallback listener;
    public HomeIndexGoodsCommonAdapter(Context mContext, List<T> mDatas,OnClickCallback listener) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_home_index_common,null);
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

          //  GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL+item.getTitle_thumb_img(),viewHolder.item_image1,R.drawable.ic_default_rectange);


            ViewGroup.LayoutParams layoutParams = viewHolder.item_image1.getLayoutParams();
            layoutParams.width= ( DeviceUtils.getWindowWidth(mContext)-DeviceUtils.dip2px(mContext,(float) (16+16+6)))/2;
            layoutParams.height=layoutParams.width;
            viewHolder.item_image1.setLayoutParams(layoutParams);
            FrescoUtils.getInstance().setImageUri(viewHolder.item_image1,ApiHttpClient.IMG_URL+item.getTitle_thumb_img());

            viewHolder. txt_shop_price1.setText("¥ "+item.getPrice());
            //TODO vip标签
            String title = item.getTitle()+"";
            String addSpan = "VIP折扣";
            SpannableString spannableString=new SpannableString(addSpan+" "+title);
            Drawable d = mContext.getResources().getDrawable(R.mipmap.ic_vip_span);
            d.setBounds(0, 0, DeviceUtils.dip2px(mContext,50), DeviceUtils.dip2px(mContext,16));
            ImageSpan span = new MyImageSpan(mContext,d);
            spannableString.setSpan(span, 0, addSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            viewHolder. item_name1.setText(spannableString);


            viewHolder.txt_shop_original1.setText("¥ "+item.getOriginal());
            viewHolder.txt_shop_original1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //是否售罄
            if (NullUtil.isStringEmpty(item.getInventory()) || 0 >= Integer.valueOf(item.getInventory())) {
                viewHolder.iv_shouqing1.setVisibility(View.VISIBLE);
            }else {
                viewHolder.iv_shouqing1.setVisibility(View.GONE);
            }
            //是否秒杀
            if ("1".equals(item.getDiscount())){
                viewHolder.tv_tag1.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tv_tag1.setVisibility(View.GONE);
            }
            if ("2".equals(item.getPension_display())){
                viewHolder.tv_tag_kangyang1.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tv_tag_kangyang1.setVisibility(View.GONE);
            }

            viewHolder.view_goods1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onClickImage(position*2);
                    }
                }
            });
            viewHolder.iv_add_shop_cart1.setOnClickListener(new OnDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (listener!=null){
                        listener.onClickShopCart(position*2);
                    }
                }
            });


        }
        if (position*2+1<mDatas.size()){
            ModelShopIndex item = (ModelShopIndex) mDatas.get(position*2+1);
            viewHolder. view_goods2.setVisibility(View.VISIBLE);
        //    GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL+item.getTitle_thumb_img(),viewHolder.item_image2,R.drawable.ic_default_rectange);
            ViewGroup.LayoutParams layoutParams = viewHolder.item_image2.getLayoutParams();
            layoutParams.width=( DeviceUtils.getWindowWidth(mContext)-DeviceUtils.dip2px(mContext,(float) (16+16+6)))/2;
            layoutParams.height=layoutParams.width;
            viewHolder.item_image2.setLayoutParams(layoutParams);
            FrescoUtils.getInstance().setImageUri(viewHolder.item_image2,ApiHttpClient.IMG_URL+item.getTitle_thumb_img());

            viewHolder. txt_shop_price2.setText("¥ "+item.getPrice());
            //TODO vip标签
            String title = item.getTitle()+"";
            String addSpan = "VIP折扣";
            SpannableString spannableString=new SpannableString(addSpan+" "+title);
            Drawable d = mContext.getResources().getDrawable(R.mipmap.ic_vip_span);
            d.setBounds(0, 0, DeviceUtils.dip2px(mContext,50), DeviceUtils.dip2px(mContext,16));
            ImageSpan span = new MyImageSpan(mContext,d);
            spannableString.setSpan(span, 0, addSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            viewHolder. item_name2.setText(spannableString);

          //  viewHolder. item_name2.setText(item.getTitle()+"");
            viewHolder.txt_shop_original2.setText("¥ "+item.getOriginal());
            viewHolder.txt_shop_original2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //是否售罄
            if (NullUtil.isStringEmpty(item.getInventory()) || 0 >= Integer.valueOf(item.getInventory())) {
                viewHolder.iv_shouqing2.setVisibility(View.VISIBLE);
            }else {
                viewHolder.iv_shouqing2.setVisibility(View.GONE);
            }
            //是否秒杀
            if ("1".equals(item.getDiscount())){
                viewHolder.tv_tag2.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tv_tag2.setVisibility(View.GONE);
            }
            if ("2".equals(item.getPension_display())){
                viewHolder.tv_tag_kangyang2.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tv_tag_kangyang2.setVisibility(View.GONE);
            }

            viewHolder.view_goods2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onClickImage(position*2+1);
                    }
                }
            });
            viewHolder.iv_add_shop_cart2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onClickShopCart(position*2+1);
                    }
                }
            });

        }else {
            viewHolder. view_goods2.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void initViewHolder(View v, ViewHolder viewHolder) {
        //第一个商品布局
        View view_goods1 = v.findViewById(R.id.ll_view_goods1);
        viewHolder.view_goods1=view_goods1;

        viewHolder. ly_onclick1 = (LinearLayout) view_goods1.findViewById(R.id.ly_onclick);
        viewHolder. item_image1 = (SimpleDraweeView) view_goods1.findViewById(R.id.item_image);
        viewHolder. item_name1 = (TextView)view_goods1.findViewById(R.id.item_name);
        viewHolder. txt_shop_price1 = (TextView) view_goods1.findViewById(R.id.txt_shop_price);
        viewHolder. txt_shop_original1 = (TextView) view_goods1.findViewById(R.id.txt_shop_original);
        viewHolder. iv_add_shop_cart1 = (ImageView) view_goods1.findViewById(R.id.iv_add_shop_cart);
        viewHolder. iv_shouqing1 = (ImageView) view_goods1.findViewById(R.id.iv_shouqing);
        viewHolder. tv_tag1 = (TextView) view_goods1.findViewById(R.id.tv_tag);
        viewHolder. tv_tag_kangyang1 = (TextView) view_goods1.findViewById(R.id.tv_tag_kangyang);


        //第二个商品的布局
        View view_goods2 = v.findViewById(R.id.ll_view_goods2);
        viewHolder.view_goods2=view_goods2;
        viewHolder.ly_onclick2 = (LinearLayout) view_goods2.findViewById(R.id.ly_onclick);
        viewHolder.item_image2 = (SimpleDraweeView) view_goods2.findViewById(R.id.item_image);
        viewHolder. item_name2 = (TextView)view_goods2.findViewById(R.id.item_name);
        viewHolder. txt_shop_price2 = (TextView) view_goods2.findViewById(R.id.txt_shop_price);
        viewHolder. txt_shop_original2 = (TextView) view_goods2.findViewById(R.id.txt_shop_original);
        viewHolder. iv_add_shop_cart2 = (ImageView) view_goods2.findViewById(R.id.iv_add_shop_cart);
        viewHolder. iv_shouqing2 = (ImageView) view_goods2.findViewById(R.id.iv_shouqing);
        viewHolder. tv_tag2= (TextView) view_goods2.findViewById(R.id.tv_tag);
        viewHolder. tv_tag_kangyang2 = (TextView) view_goods2.findViewById(R.id.tv_tag_kangyang);

    }

    private class  ViewHolder {
        LinearLayout  ly_onclick1;
        SimpleDraweeView item_image1;
        TextView item_name1, txt_shop_price1,txt_shop_original1;
        ImageView iv_add_shop_cart1;

        LinearLayout  ly_onclick2;
        SimpleDraweeView item_image2;
        TextView item_name2, txt_shop_price2,txt_shop_original2;
        ImageView iv_add_shop_cart2;

        ImageView iv_shouqing1;
        ImageView iv_shouqing2;
        TextView tv_tag1;
        TextView tv_tag2;

        TextView tv_tag_kangyang1;
        TextView tv_tag_kangyang2;

        View view_goods1;
        View view_goods2;
    }
    public interface  OnClickCallback{
        /**
         * 点击Image
         */
        void onClickImage(int position);

        /* * 点击购物车
         * @param position
         */
        void onClickShopCart(int position);
    }
}
