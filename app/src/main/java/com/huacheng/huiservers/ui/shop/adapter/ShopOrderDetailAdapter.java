package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.model.XorderDetailBean;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.view.HorizontalListView;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：商品订单详情
 * 时间：2020/1/9 15:36
 * created by DFF
 */
public class ShopOrderDetailAdapter extends CommonAdapter<XorderDetailBean> {
    private OnClickShopDetailListener mListener;
    private String status;

    public ShopOrderDetailAdapter(Context context, int layoutId, List<XorderDetailBean> datas, OnClickShopDetailListener mListener, String status) {
        super(context, layoutId, datas);
        this.status = status;
        this.mListener = mListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final XorderDetailBean item, int position) {

        viewHolder.<RelativeLayout>getView(R.id.rel_see);//单个商品布局
        viewHolder.<FrameLayout>getView(R.id.fl_one_goods);//待付款的多个商品的布局
        viewHolder.<LinearLayout>getView(R.id.ly_peisong);//配送方式布局
        //详情界面布局显示
        if ("1".equals(status)) { //待付款
            viewHolder.<MyListView>getView(R.id.listview).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.txt_baoguo).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.txt_baoguo).setText(item.getP_m_name());
            viewHolder.<ImageView>getView(R.id.iv_jiantou1).setVisibility(View.GONE);//箭头不显示
            viewHolder.<ImageView>getView(R.id.iv_jiantou2).setVisibility(View.GONE);//箭头不显示
            //待付款状态下数据商户1个商品显示
            if (item.getImg().size() == 1) {
                viewHolder.<FrameLayout>getView(R.id.fl_one_goods).setVisibility(View.VISIBLE);
                viewHolder.<LinearLayout>getView(R.id.rel_see).setVisibility(View.GONE);
                viewHolder.<LinearLayout>getView(R.id.ly_peisong).setVisibility(View.VISIBLE);//配送方式布局

                FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_one), MyCookieStore.URL + item.getImg().get(0).getImg());
                viewHolder.<TextView>getView(R.id.tv_title_one).setText("" + item.getImg().get(0).getP_title());
                viewHolder.<TextView>getView(R.id.tv_sub_title_one).setText("" + item.getImg().get(0).getTagname());
                viewHolder.<TextView>getView(R.id.tv_shop_price_one).setText("¥ " + item.getImg().get(0).getPrice());

                viewHolder.<TextView>getView(R.id.tv_num_one).setText("× " + item.getImg().get(0).getNumber());
            } else {
                //待付款状态下数据商户多个商品显示
                viewHolder.<FrameLayout>getView(R.id.fl_one_goods).setVisibility(View.GONE);
                viewHolder.<LinearLayout>getView(R.id.rel_see).setVisibility(View.VISIBLE);
                //这里图片的显示是个适配器
                //ImageAdapter imageAdapter = new ImageAdapter(mContext, item.getImg());
                CommonAdapter imageAdapter = new CommonAdapter<BannerBean>(mContext, R.layout.image_item, item.getImg()) {
                    @Override
                    protected void convert(ViewHolder viewHolder, BannerBean item, int position) {
                        FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.img), MyCookieStore.URL + item.getImg());
                    }
                };
                viewHolder.<HorizontalListView>getView(R.id.hor_scroll).setAdapter(imageAdapter);
                viewHolder.<TextView>getView(R.id.txt_num).setText("共" + item.getPro_num() + "件");
                viewHolder.<TextView>getView(R.id.txt_danprice).setText("¥ " + item.getPrice_num());
            }
            //显示配送方式
            if ("1".equals(item.getSend_type())) {
                viewHolder.<TextView>getView(R.id.tv_peisong).setText("送货上门" + "( " + "¥" + item.getDis_fee() + " )");
            } else if ("2".equals(item.getSend_type())) {
                viewHolder.<TextView>getView(R.id.tv_peisong).setText("自提");
            } else if ("3".equals(item.getSend_type())) {
                viewHolder.<TextView>getView(R.id.tv_peisong).setText("快递物流" + "( " + "¥" + item.getDis_fee() + " )");
            }

        } else {
            //详情页其他状态下的布局显示
            viewHolder.<MyListView>getView(R.id.listview).setVisibility(View.VISIBLE);
            viewHolder.<FrameLayout>getView(R.id.fl_one_goods).setVisibility(View.GONE);
            viewHolder.<LinearLayout>getView(R.id.rel_see).setVisibility(View.GONE);
            viewHolder.<LinearLayout>getView(R.id.ly_peisong).setVisibility(View.GONE);//配送方式布局

            viewHolder.<TextView>getView(R.id.txt_baoguo).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.txt_baoguo).setText(item.getP_m_name());
               /* if (position==0){
                    viewHolder.<TextView>getView(R.id.txt_baoguo).setVisibility(View.VISIBLE);
                    viewHolder.<TextView>getView(R.id.txt_baoguo).setText(item.getP_m_name());
                }else {
                    viewHolder.<TextView>getView(R.id.txt_baoguo).setVisibility(View.GONE);
                }*/
            CommonAdapter commonAdapter = new CommonAdapter<BannerBean>(mContext, R.layout.item_shop_common, item.getImg()) {
                @Override
                protected void convert(ViewHolder viewHolder, BannerBean item, int position) {
                    FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_one), MyCookieStore.URL + item.getImg());
                    viewHolder.<TextView>getView(R.id.tv_title_one).setText("" + item.getP_title());
                    viewHolder.<TextView>getView(R.id.tv_sub_title_one).setText("" + item.getTagname());
                    viewHolder.<TextView>getView(R.id.tv_shop_price_one).setText("¥ " + item.getPrice());
                    viewHolder.<TextView>getView(R.id.tv_num_one).setText("× " + item.getNumber());
                    // 订单状态(1->刚下单(待付款) 2->待发货 3->待收货 4->待评价 5->删除 6->申请退款 7->完成 8->退款中 9->已退款)
                    if ("1".equals(item.getStatus())) {
                        viewHolder.<TextView>getView(R.id.tv_status).setText("待付款");
                    } else if ("2".equals(item.getStatus())) {
                        viewHolder.<TextView>getView(R.id.tv_status).setText("待发货");
                    } else if ("3".equals(item.getStatus())) {
                        viewHolder.<TextView>getView(R.id.tv_status).setText("待收货");
                    } else if ("4".equals(item.getStatus())) {
                        viewHolder.<TextView>getView(R.id.tv_status).setText("待评价");
                    } else /* if ("5".equals(item.getStatus())){
                            viewHolder.<TextView>getView(R.id.tv_status).setText("删除");
                        }else */ if ("6".equals(item.getStatus())) {
                        viewHolder.<TextView>getView(R.id.tv_status).setText("申请退款");
                    } else if ("7".equals(item.getStatus())) {
                        viewHolder.<TextView>getView(R.id.tv_status).setText("完成");
                    } else if ("8".equals(item.getStatus())) {
                        viewHolder.<TextView>getView(R.id.tv_status).setText("退款中");
                    } else if ("9".equals(item.getStatus())) {
                        viewHolder.<TextView>getView(R.id.tv_status).setText("已退款");
                    }
                }
            };
            viewHolder.<MyListView>getView(R.id.listview).setAdapter(commonAdapter);
        }

    }

    public interface OnClickShopDetailListener {
        /**
         * 点击按钮
         *
         * @param type
         */
        void onClickButton(int type, BannerBean item);

    }
}
