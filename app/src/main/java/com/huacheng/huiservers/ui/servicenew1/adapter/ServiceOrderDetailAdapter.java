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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xlhratingbar_lib.XLHRatingBar;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceOrderDetail;
import com.huacheng.huiservers.utils.StringUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 订单详情adapter
 * created by wangxiaotao
 * 2020/6/17 0017 17:27
 */
public class ServiceOrderDetailAdapter extends CommonAdapter <ModelServiceOrderDetail.RecordBean>{
    private int type = 1;

    public ServiceOrderDetailAdapter(Context context, int layoutId, List <ModelServiceOrderDetail.RecordBean>datas,int type) {
        super(context, layoutId, datas);
        this.type=type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelServiceOrderDetail.RecordBean item, int position) {
        if (position==0){
            viewHolder.getView(R.id.rl_first) .setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.rl_other) .setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_title).setTextColor(mContext.getResources().getColor(R.color.title_color));
            viewHolder.<TextView>getView(R.id.tv_sub_title).setTextColor(mContext.getResources().getColor(R.color.title_color));
        }else {
            viewHolder.getView(R.id.rl_first) .setVisibility(View.GONE);
            viewHolder.getView(R.id.rl_other) .setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_title).setTextColor(mContext.getResources().getColor(R.color.title_third_color));
            viewHolder.<TextView>getView(R.id.tv_sub_title).setTextColor(mContext.getResources().getColor(R.color.title_third_color));
        }
        String status = item.getStatus();
        int status_int = Integer.parseInt(status);
        if (type==1){//订单详情
            if (status_int==1){
                viewHolder.<TextView>getView(R.id.tv_title).setText("待派单");
                viewHolder.<TextView>getView(R.id.tv_sub_title).setText(item.getDescribe());
                viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAddtime(),"7"));
                viewHolder.<LinearLayout>getView(R.id.ll_evaluate_cotainer).setVisibility(View.GONE);
            }else if (status_int==2){
                viewHolder.<TextView>getView(R.id.tv_title).setText("已派单");

                String sub_content = item.getDescribe()+""+item.getWorker_name()+" "+item.getWorker_number();
                String phone = item.getWorker_number()+"";
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
                SpannableString spannableString = new SpannableString(sub_content);
                spannableString.setSpan(clickableSpan, sub_content.indexOf(phone) , sub_content.indexOf(phone) + phone.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                viewHolder.<TextView>getView(R.id.tv_sub_title).setText(spannableString);
                viewHolder.<TextView>getView(R.id.tv_sub_title).setMovementMethod(LinkMovementMethod.getInstance());

                viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAddtime(),"7"));
                viewHolder.<LinearLayout>getView(R.id.ll_evaluate_cotainer).setVisibility(View.GONE);
            }else if (status_int==3){
                viewHolder.<TextView>getView(R.id.tv_title).setText("已上门");
                String sub_content = item.getDescribe()+""+item.getWorker_name()+" "+item.getWorker_number();
                String phone = item.getWorker_number()+"";
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
                SpannableString spannableString = new SpannableString(sub_content);
                spannableString.setSpan(clickableSpan, sub_content.indexOf(phone) , sub_content.indexOf(phone) + phone.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                viewHolder.<TextView>getView(R.id.tv_sub_title).setText(spannableString);
                viewHolder.<TextView>getView(R.id.tv_sub_title).setMovementMethod(LinkMovementMethod.getInstance());

                viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAddtime(),"7"));
                viewHolder.<LinearLayout>getView(R.id.ll_evaluate_cotainer).setVisibility(View.GONE);

            }else if (status_int==4){
                viewHolder.<TextView>getView(R.id.tv_title).setText("待评价");
                String sub_content = item.getDescribe()+""+item.getWorker_name()+" "+item.getWorker_number();
                String phone = item.getWorker_number()+"";
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
                SpannableString spannableString = new SpannableString(sub_content);
                spannableString.setSpan(clickableSpan, sub_content.indexOf(phone) , sub_content.indexOf(phone) + phone.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                viewHolder.<TextView>getView(R.id.tv_sub_title).setText(spannableString);
                viewHolder.<TextView>getView(R.id.tv_sub_title).setMovementMethod(LinkMovementMethod.getInstance());

                viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAddtime(),"7"));
                viewHolder.<LinearLayout>getView(R.id.ll_evaluate_cotainer).setVisibility(View.GONE);
            }else if (status_int==5){
                viewHolder.<TextView>getView(R.id.tv_title).setText("已完成");
                viewHolder.<TextView>getView(R.id.tv_sub_title).setText(item.getDescribe());
                viewHolder.<LinearLayout>getView(R.id.ll_evaluate_cotainer).setVisibility(View.VISIBLE);
                viewHolder.<XLHRatingBar>getView(R.id.ratingBar).setCountSelected(item.getScore());
                viewHolder.<TextView>getView(R.id.tv_evaluate_content).setText(item.getEvaluate()+"");
                viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAddtime(),"7"));
                viewHolder.<LinearLayout>getView(R.id.ll_evaluate_img_container).setVisibility(View.GONE);
//            viewHolder.<LinearLayout>getView(R.id.ll_evaluate_img_container).removeAllViews();
//            for (int i = 0; i < 4; i++) {
//                ImageView imageView = new ImageView(mContext);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DeviceUtils.dip2px(mContext, 60), DeviceUtils.dip2px(mContext, 60));
//                layoutParams.rightMargin=DeviceUtils.dip2px(mContext, 5);
//                GlideUtils.getInstance().glideLoad(mContext,"",imageView,R.color.default_img_color);
//                viewHolder.<LinearLayout>getView(R.id.ll_evaluate_img_container).addView(imageView,layoutParams);
//            }

            }
        }else {
            //退款详情
            viewHolder.<LinearLayout>getView(R.id.ll_evaluate_cotainer).setVisibility(View.GONE);
            if (status_int==1){
                //审核中
                viewHolder.<TextView>getView(R.id.tv_title).setText("退款审核中");
            }else if (status_int==2){
                //退款中
                viewHolder.<TextView>getView(R.id.tv_title).setText("退款中");
            }else if (status_int==3){
                //审核失败
                viewHolder.<TextView>getView(R.id.tv_title).setText("退款审核失败");
            }else if (status_int==4){
                //退款成功
                viewHolder.<TextView>getView(R.id.tv_title).setText("退款成功");
            }else if (status_int==5){
                //退款失败
                viewHolder.<TextView>getView(R.id.tv_title).setText("退款失败");
            }

            String sub_content = item.getDescribe()+"  "+item.getTelphone();
            String phone = item.getTelphone()+"";
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
            SpannableString spannableString = new SpannableString(sub_content);
            spannableString.setSpan(clickableSpan, sub_content.indexOf(phone) , sub_content.indexOf(phone) + phone.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            viewHolder.<TextView>getView(R.id.tv_sub_title).setText(spannableString);
            viewHolder.<TextView>getView(R.id.tv_sub_title).setMovementMethod(LinkMovementMethod.getInstance());

            viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAddtime(),"7"));
        }
    }
}
