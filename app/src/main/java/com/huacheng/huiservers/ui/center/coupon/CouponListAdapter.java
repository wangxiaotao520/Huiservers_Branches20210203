package com.huacheng.huiservers.ui.center.coupon;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelCouponNew;
import com.huacheng.huiservers.utils.StringUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 优惠券列表的adapter
 * created by wangxiaotao
 * 2020/6/5 0005 15:24
 */
public class CouponListAdapter extends CommonAdapter<ModelCouponNew> {
    private int type = 0;//0 个人中心我的优惠券 1更多优化券 2 已使用优化券 3.已过期优化券 4.商品详情弹窗优惠券
    private OnClickRightBtnListener onClickRightBtnListener;

    public CouponListAdapter(Context context, int layoutId, List<ModelCouponNew> datas,int type,OnClickRightBtnListener onClickRightBtnListener) {
        super(context, layoutId, datas);
        this.type=type;
        this.onClickRightBtnListener= onClickRightBtnListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ModelCouponNew item, final int position) {

        if (type==0){
            viewHolder.<TextView>getView(R.id.tv_right_btn).setText("去使用");
            viewHolder.<LinearLayout>getView(R.id.ll_coupon_root).setBackgroundResource(R.mipmap.bg_coupon_red_new);
        }else if (type==1){
            viewHolder.<TextView>getView(R.id.tv_right_btn).setText("立即\n领取");
            viewHolder.<LinearLayout>getView(R.id.ll_coupon_root).setBackgroundResource(R.mipmap.bg_coupon_red_new);
        }else if (type==2){
            viewHolder.<TextView>getView(R.id.tv_right_btn).setText("已使用");
            viewHolder.<TextView>getView(R.id.tv_right_btn).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<TextView>getView(R.id.tv_price_icon).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<TextView>getView(R.id.tv_price).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<TextView>getView(R.id.tv_coupon_rules).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<TextView>getView(R.id.tv_coupon_name).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<TextView>getView(R.id.tv_coupon_time).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<LinearLayout>getView(R.id.ll_coupon_root).setBackgroundResource(R.mipmap.bg_coupon_grey_new);
        }else if (type==3){
            viewHolder.<TextView>getView(R.id.tv_right_btn).setText("已过期");
            viewHolder.<TextView>getView(R.id.tv_right_btn).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<TextView>getView(R.id.tv_price_icon).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<TextView>getView(R.id.tv_price).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<TextView>getView(R.id.tv_coupon_rules).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<TextView>getView(R.id.tv_coupon_name).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<TextView>getView(R.id.tv_coupon_time).setTextColor(mContext.getResources().getColor(R.color.white));
            viewHolder.<LinearLayout>getView(R.id.ll_coupon_root).setBackgroundResource(R.mipmap.bg_coupon_grey_new);
        }else if (type==4){
            //商品详情对话框
            if ("1".equals(item.getStatus())){
                viewHolder.<TextView>getView(R.id.tv_right_btn).setText("立即\n领取");
            }else {
                viewHolder.<TextView>getView(R.id.tv_right_btn).setText("已领取");
            }
        }else if (type==5){
            viewHolder.<TextView>getView(R.id.tv_right_btn).setText("使用");
            viewHolder.<LinearLayout>getView(R.id.ll_coupon_root).setBackgroundResource(R.mipmap.bg_coupon_red_new);
        }
        viewHolder.<TextView>getView(R.id.tv_right_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickRightBtnListener!=null){
                    onClickRightBtnListener.onClickRightBtn(item,position,type);
                }
            }
        });

        viewHolder.<TextView>getView(R.id.tv_price).setText(item.getAmount()+"");
        if ("0".equals(item.getFulfil_amount())){
            viewHolder.<TextView>getView(R.id.tv_coupon_rules).setText("无门槛可用");
        }else {
            viewHolder.<TextView>getView(R.id.tv_coupon_rules).setText("满"+item.getFulfil_amount()+"元可用");
        }
        viewHolder.<TextView>getView(R.id.tv_coupon_name).setText(item.getName());
        viewHolder.<TextView>getView(R.id.tv_coupon_time).setText(StringUtils.getDateToString(item.getStartime(),"8")+"-"+StringUtils.getDateToString(item.getEndtime(),"8"));
    }

    public interface OnClickRightBtnListener{
        /**
         *
         * @param item
         * @param position
         * @param type  0 个人中心我的优惠券 1更多优化券 2 已使用优化券 3.已过期优化券 4.商品详情弹窗
         */
        void onClickRightBtn(ModelCouponNew item, int position,int type);
    }
}
