package com.huacheng.huiservers.ui.servicenew1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 订单详情adapter
 * created by wangxiaotao
 * 2020/6/17 0017 17:27
 */
public class ServiceOrderDetailAdapter extends CommonAdapter <String>{
    public ServiceOrderDetailAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String  item, int position) {
        if (position==0){
           viewHolder.getView(R.id.rl_first) .setVisibility(View.VISIBLE);
           viewHolder.getView(R.id.rl_other) .setVisibility(View.GONE);
           viewHolder.<TextView>getView(R.id.tv_title).setTextColor(mContext.getResources().getColor(R.color.title_color));
           viewHolder.<TextView>getView(R.id.tv_sub_title).setTextColor(mContext.getResources().getColor(R.color.title_color));
           viewHolder.<LinearLayout>getView(R.id.ll_evaluate_cotainer).setVisibility(View.VISIBLE);
            viewHolder.<LinearLayout>getView(R.id.ll_evaluate_img_container).removeAllViews();
            for (int i = 0; i < 4; i++) {
                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DeviceUtils.dip2px(mContext, 60), DeviceUtils.dip2px(mContext, 60));
                layoutParams.rightMargin=DeviceUtils.dip2px(mContext, 5);
                GlideUtils.getInstance().glideLoad(mContext,"",imageView,R.color.default_img_color);
                viewHolder.<LinearLayout>getView(R.id.ll_evaluate_img_container).addView(imageView,layoutParams);
            }

        }else {
            viewHolder.getView(R.id.rl_first) .setVisibility(View.GONE);
            viewHolder.getView(R.id.rl_other) .setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_title).setTextColor(mContext.getResources().getColor(R.color.title_third_color));
            viewHolder.<TextView>getView(R.id.tv_sub_title).setTextColor(mContext.getResources().getColor(R.color.title_third_color));
            viewHolder.<LinearLayout>getView(R.id.ll_evaluate_cotainer).setVisibility(View.GONE);
        }
        String s = "商家已指派服务人员为您进行上门服务，请保持手机接听通畅，注意接听电话 上门师傅：何伟伟"+"15745214578";
        final String s_phone = "15745214578";
        //参照弹窗隐私政策写颜色
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //Do something.
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:"
//                        + s_phone));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
                //弹窗拨打电话

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#459fe5"));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(clickableSpan, s.indexOf(s_phone) , s.indexOf(s_phone) + s_phone.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.<TextView>getView(R.id.tv_sub_title).setText(spannableString);
        viewHolder.<TextView>getView(R.id.tv_sub_title).setMovementMethod(LinkMovementMethod.getInstance());
    }
}
