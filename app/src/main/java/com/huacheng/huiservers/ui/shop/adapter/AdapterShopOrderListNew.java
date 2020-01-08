package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.ui.center.bean.ShopOrderBeanTypeBean;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 新版订单详情adapter
 * created by wangxiaotao
 * 2020/1/8 0008 上午 9:16
 */
public class AdapterShopOrderListNew  extends CommonAdapter<ShopOrderBeanTypeBean>{
    OnClickShopOrderListListener listListener;
    public AdapterShopOrderListNew(Context context, int layoutId, List<ShopOrderBeanTypeBean> datas,OnClickShopOrderListListener listListener) {
        super(context, layoutId, datas);
        this.listListener=listListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ShopOrderBeanTypeBean item, int position) {
      viewHolder.<TextView>getView(R.id.tv_merchant_name).setText("暂无等待宝俊返回");
      viewHolder.<TextView>getView(R.id.tv_pay_status).setText("暂无等待宝俊返回");


      if (item.getImg().size()==1){
          //一张图片
          viewHolder.<LinearLayout>getView(R.id.ll_container_one).setVisibility(View.VISIBLE);
          viewHolder.<HorizontalScrollView>getView(R.id.hsv_container_two).setVisibility(View.GONE);
          FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_one), ApiHttpClient.IMG_URL + item.getImg().get(0).getImg());

          viewHolder.<TextView>getView(R.id.tv_name_one).setText("暂无等待宝俊返回");
          viewHolder.<TextView>getView(R.id.tv_type).setText("暂无等待宝俊返回");

      }else {
          //多张图片
          viewHolder.<LinearLayout>getView(R.id.ll_container_one).setVisibility(View.GONE);
          viewHolder.<HorizontalScrollView>getView(R.id.hsv_container_two).setVisibility(View.VISIBLE);
          viewHolder.<LinearLayout>getView(R.id.ll_container_two).removeAllViews();
          for (int i = 0; i < item.getImg().size(); i++) {
              View view = LayoutInflater.from(mContext).inflate(R.layout.image_item, null);
              SimpleDraweeView sdv_img = view.findViewById(R.id.img);
              LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DeviceUtils.dip2px(mContext, 80), DeviceUtils.dip2px(mContext, 80));
              sdv_img.setLayoutParams(params);
              FrescoUtils.getInstance().setImageUri(sdv_img,ApiHttpClient.IMG_URL +item.getImg().get(i).getImg());
              viewHolder.<LinearLayout>getView(R.id.ll_container_two).addView(view);
          }
      }
        viewHolder.<TextView>getView(R.id.tv_price).setText("¥" + item.getPrice_total());
        viewHolder.<TextView>getView(R.id.tv_num).setText("共" + item.getPro_num() + "件");
        //TODO 两个按钮的状态
        viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.VISIBLE);
        viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.VISIBLE);
    }

    public interface OnClickShopOrderListListener{
        /**
         * 点击支付
         * @param item
         * @param position
         */
        void  onClickPay(ShopOrderBeanTypeBean item, int position);

        /**
         * 再次购买
         * @param item
         * @param position
         */
        void onClickReBuy(ShopOrderBeanTypeBean item, int position);
        /**
         * 确认收货
         * @param item
         * @param position
         */
        void onClickConfirm(ShopOrderBeanTypeBean item, int position);
        /**
         * 评价
         * @param item
         * @param position
         */
        void onClickPinjia(ShopOrderBeanTypeBean item, int position);
    }
}
