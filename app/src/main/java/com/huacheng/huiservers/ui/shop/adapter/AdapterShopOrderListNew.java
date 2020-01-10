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
import com.stx.xhb.xbanner.OnDoubleClickListener;
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
    private int type = 0;
    public AdapterShopOrderListNew(Context context, int layoutId, List<ShopOrderBeanTypeBean> datas,OnClickShopOrderListListener listListener,int type) {
        super(context, layoutId, datas);
        this.listListener=listListener;
        this.type=type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ShopOrderBeanTypeBean item, final int position) {
      viewHolder.<TextView>getView(R.id.tv_merchant_name).setText(item.getP_m_name()+"");
        viewHolder.<TextView>getView(R.id.tv_pay_status).setVisibility(View.GONE);

      if (item.getImg().size()==1){
          //一张图片
          viewHolder.<LinearLayout>getView(R.id.ll_container_one).setVisibility(View.VISIBLE);
          viewHolder.<HorizontalScrollView>getView(R.id.hsv_container_two).setVisibility(View.GONE);
          FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_one), ApiHttpClient.IMG_URL + item.getImg().get(0).getImg());

          viewHolder.<TextView>getView(R.id.tv_name_one).setText( item.getImg().get(0).getP_title()+"");
          viewHolder.<TextView>getView(R.id.tv_type).setText(item.getImg().get(0).getTagname()+"");

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
          viewHolder.<LinearLayout>getView(R.id.ll_container_two).setOnClickListener(new OnDoubleClickListener() {
              @Override
              public void onNoDoubleClick(View v) {
                  if (listListener!=null) {
                      listListener.onClickItemMultiImage(item,position);
                  }
              }
          });
      }
        viewHolder.<TextView>getView(R.id.tv_price).setText("¥" + item.getPrice_total());
        viewHolder.<TextView>getView(R.id.tv_num).setText("共" + item.getPro_num() + "件");
        if (type==0){
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.VISIBLE);

            viewHolder.<TextView>getView(R.id.tv_btn_1).setText("删除订单");
            viewHolder.<TextView>getView(R.id.tv_btn_1).setOnClickListener(new OnDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (listListener!=null) {
                        listListener.onClickDelete(item,position);
                    }
                }
            });
            viewHolder.<TextView>getView(R.id.tv_btn_2).setText("去支付");
            viewHolder.<TextView>getView(R.id.tv_btn_2).setOnClickListener(new OnDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (listListener!=null) {
                        listListener.onClickPay(item,position);
                    }
                }
            });

        }else {
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.GONE);
        }

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
        /**
         * 删除
         * @param item
         * @param position
         */
        void onClickDelete(ShopOrderBeanTypeBean item, int position);
        /**
         * 点击
         * @param item
         * @param position
         */
        void onClickItemMultiImage(ShopOrderBeanTypeBean item, int position);
    }
}
