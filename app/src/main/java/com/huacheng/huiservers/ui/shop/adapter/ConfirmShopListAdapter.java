package com.huacheng.huiservers.ui.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.model.protocol.ShopProtocol;
import com.huacheng.huiservers.ui.center.adapter.ImageAdapter;
import com.huacheng.huiservers.ui.shop.SeeAllOrderActivity;
import com.huacheng.huiservers.ui.shop.bean.ConfirmBean;
import com.huacheng.huiservers.ui.shop.bean.SubmitOrderBean;
import com.huacheng.huiservers.view.HorizontalListView;
import com.huacheng.huiservers.view.SwitchButton;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.List;

public class ConfirmShopListAdapter extends BaseAdapter {
    List<ConfirmBean> mList;
    private Context mContext;
    List<SubmitOrderBean> pro;
    ViewHolder holder = null;
    String str_img, txt_color;
    //List<ConfirmMapBean> mapBeans=new ArrayList<ConfirmMapBean>();
    private ImageLoader imageLoader;
    ShopProtocol protocol = new ShopProtocol();
    private OnClickPeisongListener listener ;

    public ConfirmShopListAdapter(Context mContext, List<ConfirmBean> mList, List<SubmitOrderBean> pro,OnClickPeisongListener listener) {
        super();
        this.pro = pro;
        this.mList = mList;
        this.mContext = mContext;
        if (imageLoader == null) {
            imageLoader = ImageLoader.getInstance();
        }
//        MyCookieStore.Confirmlist.clear();
//        for (int i = 0; i < mList.size(); i++) {
//            ConfirmMapBean bean = new ConfirmMapBean();
//            bean.setId(mList.get(i).getMerchant_id());
//            bean.setStyle("1");
//            MyCookieStore.Confirmlist.add(bean);
//        }
        this.listener=listener;
    }


    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return this.mList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mList == null) {
            return null;
        } else {
            return this.mList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("deprecation")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.confrim_order_item, null, false);
            //  holder.txt_store = (TextView) convertView.findViewById(R.id.txt_store);
            holder.txt_num = (TextView) convertView.findViewById(R.id.txt_num);
            holder.txt_danprice = (TextView) convertView.findViewById(R.id.txt_danprice);
            /*holder.txt_shangmen = (TextView) convertView.findViewById(R.id.txt_shangmen);
            holder.txt_ziti = (TextView) convertView.findViewById(R.id.txt_ziti);*/
            //holder.rg_colleague = (RadioGroup) convertView.findViewById(R.id.rg_colleague);
            holder.txt_baoguo = (TextView) convertView.findViewById(R.id.txt_baoguo);
            holder.btn_style = (SwitchButton) convertView.findViewById(R.id.btn_style);
            holder.rel_see = convertView.findViewById(R.id.rel_see);
            holder.lin_onclick = (LinearLayout) convertView.findViewById(R.id.lin_onclick);
            // holder.lin_market = (LinearLayout) convertView.findViewById(R.id.lin_market);
            holder.hor_scroll = (HorizontalListView) convertView.findViewById(R.id.hor_scroll);
            holder.ly_peisong = convertView.findViewById(R.id.ly_peisong);
            holder.tv_peisong = convertView.findViewById(R.id.tv_peisong);
            holder.tv_peisong_price = convertView.findViewById(R.id.tv_peisong_price);

           holder.fl_one_goods =convertView.findViewById(R.id.fl_one_goods);
           holder.sdv_one =convertView.findViewById(R.id.sdv_one);
           holder.tv_title_one =convertView.findViewById(R.id.tv_title_one);
           holder.tv_sub_title_one =convertView.findViewById(R.id.tv_sub_title_one);
           holder.tv_shop_price_one =convertView.findViewById(R.id.tv_shop_price_one);
           holder.tv_num_one =convertView.findViewById(R.id.tv_num_one);

           convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //holder.txt_store.setText(mList.get(position).getMerchant_name());

        // holder.txt_baoguo.setText("包裹" + (position + 1));
        holder.txt_baoguo.setText(mList.get(position).getMerchant_name());
     //   MyCookieStore.Confirmlist.get(position).setStyle("1");
        holder.btn_style.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    MyCookieStore.Confirmlist.get(position).setStyle("2");//自提

                }else {
                    MyCookieStore.Confirmlist.get(position).setStyle("1");//送货

                }
            }
        });
        /*holder.rg_colleague.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton radio1 = (RadioButton) arg0.findViewById(R.id.radio1);
                RadioButton radio2 = (RadioButton) arg0.findViewById(R.id.radio2);
                if (radio1.isChecked()) {//待付款
                    MyCookieStore.Confirmlist.get(position).setStyle("1");
                    System.out.println("99999---" + MyCookieStore.Confirmlist.get(position).getId());
                    System.out.println("99999---" + MyCookieStore.Confirmlist.get(position).getStyle());
                } else if (radio2.isChecked()) {//待发货
                    MyCookieStore.Confirmlist.get(position).setStyle("2");
                    System.out.println("%%%%---" + MyCookieStore.Confirmlist.get(position).getId());
                    System.out.println("%%%%---" + MyCookieStore.Confirmlist.get(position).getStyle());
                }
            }
        });*/
       /* holder.lin_market.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MarketDialog dialog = new MarketDialog(mContext, mList.get(position).getMerchant_name(),
                        mList.get(position).getMerchant_address(), mList.get(position).getMerchant_telphone());
                dialog.show();
            }
        });*/
        holder.lin_onclick.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SeeAllOrderActivity.class);
                //这里需要传获取到的订单id 来显示所有的商品列表
                Bundle bundle = new Bundle();
                //bundle.putString("order_id", id);
                bundle.putSerializable("pro", (Serializable) pro);
                bundle.putString("m_id", mList.get(position).getMerchant_id());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        if (mList.get(position).getImg().size()==1){
            holder.fl_one_goods.setVisibility(View.VISIBLE);
            holder.rel_see.setVisibility(View.GONE);
            FrescoUtils.getInstance().setImageUri(holder.sdv_one,MyCookieStore.URL+mList.get(position).getImg().get(0).getOne_img());
            //其他数据还没填充服务器没有返回
            holder.tv_title_one.setText(""+mList.get(position).getImg().get(0).getP_title());
            holder.tv_sub_title_one.setText(""+mList.get(position).getImg().get(0).getTagname());
            holder.tv_shop_price_one.setText("¥ "+mList.get(position).getImg().get(0).getPrice());

            holder.tv_num_one.setText("× "+mList.get(position).getImg().get(0).getNumber());
        }else {
            holder.fl_one_goods.setVisibility(View.GONE);
            holder.rel_see.setVisibility(View.VISIBLE);
            //这里图片的显示是个适配器   控制图片数量
            ImageAdapter imageAdapter = new ImageAdapter(mContext, mList.get(position).getImg());
            holder.hor_scroll.setAdapter(imageAdapter);
            holder.txt_num.setText("共" + mList.get(position).getNumber() + "件");
            holder.txt_danprice.setText("¥ " + mList.get(position).getHalf_amount());
        }

        //convertView.setOnTouchListener(this);
        holder.ly_peisong.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClickPeisong(position);
                }
            }
        });
        ConfirmBean confirmBean = mList.get(position);
        if (confirmBean.getDeliversBean_selected()!=null){
            holder.tv_peisong.setText(confirmBean.getDeliversBean_selected().getName()+"( "+ "¥" +confirmBean.getDeliversBean_selected().getDis_fee()+" )");
         //   holder.tv_peisong_price.setText("¥ " +confirmBean.getDeliversBean_selected().getDis_fee());
        }else {
            // 没有地址的情况 或者后台没设置配送方式的情况（bug出现的情况）
            holder.tv_peisong.setText("");
         //   holder.tv_peisong_price.setText("¥ 0");
        }

        return convertView;
    }
/*
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v instanceof EditText) {
            EditText et = (EditText) v;
            et.setFocusable(true);
            et.setFocusableInTouchMode(true);
        } else {
            ViewHolder holder = (ViewHolder) v.getTag();
            holder.edt_view.setFocusable(false);
            holder.edt_view.setFocusableInTouchMode(false);

        }
        return false;
    }*/


    public class ViewHolder {
        private SwitchButton btn_style;
        private RadioGroup rg_colleague;
        private RadioButton radio1, radio2;
        private LinearLayout lin_onclick, lin_market;
        private HorizontalListView hor_scroll;
        public TextView txt_store, txt_num, txt_danprice, txt_shangmen, txt_ziti, txt_baoguo;
        //配送方式
        private LinearLayout ly_peisong ;
        private TextView tv_peisong;
        //配送费
        private TextView tv_peisong_price ;
        //多个商品的布局
        LinearLayout rel_see;

        //单个儿商品的
        FrameLayout fl_one_goods;
        SimpleDraweeView sdv_one;
        TextView tv_title_one;
        TextView tv_sub_title_one;
        TextView tv_shop_price_one;
        TextView tv_num_one;


    }

    private void isCheckd() {

    }

    public interface OnClickPeisongListener{
        /**
         * 点击选择配送方式
         * @param position
         */
        void onClickPeisong(int position);
    }
}
