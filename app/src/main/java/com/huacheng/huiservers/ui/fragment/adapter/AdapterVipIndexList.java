package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelVipIndex;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：vip 店铺商品信息
 * 时间：2020/11/30 10:33
 * created by DFF
 */
public class AdapterVipIndexList extends CommonAdapter<ModelVipIndex> {

    public AdapterVipIndexList(Context context, int layoutId, List<ModelVipIndex> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ModelVipIndex item, int position) {
        if ("1".equals(item.getIs_vip())){//会员
            viewHolder.<TextView>getView(R.id.txt_shop_price).setText("¥" +item.getVip_price());
        }else {
            viewHolder.<TextView>getView(R.id.txt_shop_price).setText("¥" +item.getPrice());
        }
        viewHolder.<TextView>getView(R.id.txt_shop_original).setText("¥" +item.getOriginal());
        viewHolder.<TextView>getView(R.id.txt_shop_original).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.item_image), ApiHttpClient.IMG_URL + item.getTitle_img());
        //TODO vip标签
        String title = item.getTitle()+"";
//        String addSpan = "VIP折扣";
//        SpannableString spannableString=new SpannableString(addSpan+" "+title);
//        Drawable d = mContext.getResources().getDrawable(R.mipmap.ic_vip_span);
//        d.setBounds(0, 0, DeviceUtils.dip2px(mContext,50), DeviceUtils.dip2px(mContext,16));
//        ImageSpan span = new MyImageSpan(mContext,d);
//        spannableString.setSpan(span, 0, addSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.<TextView>getView(R.id.item_name).setText(title);
        if ("1".equals(item.getIs_vip())){//会员
            viewHolder.<TextView>getView(R.id.tv_tag_vip).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_tag).setVisibility(View.GONE);
        }else {
            viewHolder.<TextView>getView(R.id.tv_tag_vip).setVisibility(View.GONE);
            int discount = item.getDiscount();
            if (discount==1) {
                // recyclerViewHolder.iv_shop_list_flag.setBackgroundResource(R.drawable.ic_shoplist_spike);
                viewHolder.<TextView>getView(R.id.tv_tag).setText("秒杀");
                viewHolder.<TextView>getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            } else {
                if (item.getIs_hot()==1) {
                    //   recyclerViewHolder.iv_shop_list_flag.setBackgroundResource(R.drawable.ic_shoplist_hotsell);
                    viewHolder.<TextView>getView(R.id.tv_tag).setText("热卖");
                } else if (item.getIs_new()==1) {
                    //   recyclerViewHolder.iv_shop_list_flag.setBackgroundResource(R.drawable.ic_shoplist_newest);
                    viewHolder.<TextView>getView(R.id.tv_tag).setText("上新");
                    viewHolder.<TextView>getView(R.id.tv_tag).setVisibility(View.VISIBLE);
                } else {
                    //  recyclerViewHolder.iv_shop_list_flag.setBackground(null);
                    viewHolder.<TextView>getView(R.id.tv_tag).setVisibility(View.GONE);
                }
            }
        }
//        if ("2".equals(index.getPension_display())){
//            recyclerViewHolder.tv_tag_kangyang.setVisibility(View.VISIBLE);
//        }else {
//            recyclerViewHolder.tv_tag_kangyang.setVisibility(View.GONE);
//        }
        if (item.getInventory()==0|| 0 > item.getInventory()) {
            viewHolder.<TextView>getView(R.id.tv_shouqing).setVisibility(View.VISIBLE);
        } else {
            viewHolder.<TextView>getView(R.id.tv_shouqing).setVisibility(View.GONE);
        }
        viewHolder.<LinearLayout>getView(R.id.ly_onclick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getInventory()==0) {
                    SmartToast.showInfo("商品已售罄");
                } else {
                    Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("shop_id", item.getId());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }

            }
        });


    }
}
