package com.huacheng.huiservers.pay.chinaums;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.ui.center.geren.bean.PayTypeBean;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 选择支付方式adapter
 * created by wangxiaotao
 * 2019/7/11 0011 上午 8:53
 */
public class AdapterUnifypay extends CommonAdapter<PayTypeBean>{

    public AdapterUnifypay(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, PayTypeBean item, int position) {

        GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL+item.getIcon(),viewHolder.<ImageView>getView(R.id.iv_pay_type_icon),R.color.transparents);
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getByname()+"");
        if (item.getRecommend()==1){
            viewHolder.<ImageView>getView(R.id.iv_recommand).setVisibility(View.VISIBLE);
        }else {
            viewHolder.<ImageView>getView(R.id.iv_recommand).setVisibility(View.GONE);
        }
        viewHolder.<ImageView>getView(R.id.iv_selected).setImageResource(item.isSelected()?R.mipmap.ic_selected_pay_type:R.drawable.shape_oval_grey);
        String type_name = item.getTypename();

        if ("wxpay".equals(type_name)){
            //微信
            viewHolder.<ImageView>getView(R.id.iv_img_second).setVisibility(View.GONE);
        }else if("unionpay".equals(type_name)){
            //云闪付
            viewHolder.<ImageView>getView(R.id.iv_img_second).setVisibility(View.VISIBLE);
            viewHolder.<ImageView>getView(R.id.iv_img_second).setImageResource(R.mipmap.ic_unify_pay_2);
        }else if ("alipay".equals(type_name)){
            //支付宝
            viewHolder.<ImageView>getView(R.id.iv_img_second).setVisibility(View.GONE);


        }
    }
}
