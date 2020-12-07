package com.huacheng.huiservers.ui.center.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelCollect;
import com.huacheng.huiservers.view.MyImageSpan;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：商品  服务
 * 时间：2020/12/2 10:04
 * created by DFF
 */
public class AdapterGoodsServiceFollow extends CommonAdapter<ModelCollect> {
    int type;

    public AdapterGoodsServiceFollow(Context context, int layoutId, List<ModelCollect> datas,int type) {
        super(context, layoutId, datas);
        this.type=type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelCollect item, int position) {

        if (type == 0) {//商品
            viewHolder.<LinearLayout>getView(R.id.ly_onclick).setVisibility(View.VISIBLE);
            viewHolder.<LinearLayout>getView(R.id.ll_service_container).setVisibility(View.GONE);

            FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.iv_title_img),  ApiHttpClient.IMG_URL+mDatas.get(position).getTitle_img());

            if ("1".equals(item.getIs_vip())){
                String title =item.getTitle()+"";
                String addSpan = "VIP折扣";
                SpannableString spannableString=new SpannableString(addSpan+" "+title);
                Drawable d = mContext.getResources().getDrawable(R.mipmap.ic_vip_span);
                d.setBounds(0, 0, DeviceUtils.dip2px(mContext,50), DeviceUtils.dip2px(mContext,16));
                ImageSpan span = new MyImageSpan(mContext,d);
                spannableString.setSpan(span, 0, addSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                viewHolder.<TextView>getView(R.id.tv_title).setText(spannableString);

                viewHolder.<TextView>getView(R.id.tv_shop_price).setText("¥" + item.getVip_price() + "/");

            }else {
                viewHolder.<TextView>getView(R.id.tv_title).setText(item.getTitle());
                viewHolder.<TextView>getView(R.id.tv_shop_price).setText("¥" +item.getPrice() + "/");
            }
            viewHolder.<TextView>getView(R.id.tv_shop_weight).setText("单位");
            viewHolder.<TextView>getView(R.id.tv_shop_price_original).setText("¥" +item.getOriginal() + "元");
            viewHolder.<TextView>getView(R.id.tv_shop_price_original).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            if (!NullUtil.isStringEmpty(mDatas.get(position).getDescription())){
                viewHolder.<TextView>getView(R.id.tv_sub_title).setText(item.getDescription());
            }else {
                viewHolder.<TextView>getView(R.id.tv_sub_title).setText("");
            }

        }else if (type==1){//店铺
            viewHolder.<LinearLayout>getView(R.id.ly_onclick).setVisibility(View.GONE);
            viewHolder.<LinearLayout>getView(R.id.ll_service_container).setVisibility(View.VISIBLE);

            FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_service_bg), ApiHttpClient.IMG_SERVICE_URL + item.getTitle_img());
//            if ("2".equals(service.getPension_display())){
//                holderService.tv_tag_kangyang.setVisibility(View.VISIBLE);
//            }else {
//                holderService.tv_tag_kangyang.setVisibility(View.GONE);
//            }
            //     holderService.tv_serviceName.setText(service.getTitle());
            if ("1".equals(item.getIs_vip())) {
                String title = item.getTitle() + "";
                String addSpan = "VIP折扣";
                SpannableString spannableString = new SpannableString(addSpan + " " + title);
                Drawable d = mContext.getResources().getDrawable(R.mipmap.ic_vip_span);
                d.setBounds(0, 0, DeviceUtils.dip2px(mContext, 50), DeviceUtils.dip2px(mContext, 16));
                ImageSpan span = new MyImageSpan(mContext, d);
                spannableString.setSpan(span, 0, addSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                viewHolder.<TextView>getView(R.id.tv_serviceName).setText(spannableString);

                viewHolder.<TextView>getView(R.id.tv_servicePrice).setText("¥" + item.getVip_price());
            }else {
                viewHolder.<TextView>getView(R.id.tv_serviceName).setText(item.getTitle());
                viewHolder.<TextView>getView(R.id.tv_servicePrice).setText("¥" + item.getPrice());
            }

        }
    }
}
