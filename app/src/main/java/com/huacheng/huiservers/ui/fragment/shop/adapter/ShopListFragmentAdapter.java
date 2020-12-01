package com.huacheng.huiservers.ui.fragment.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelShopIndex;
import com.huacheng.huiservers.ui.login.LoginVerifyCodeActivity;
import com.huacheng.huiservers.ui.shop.ShopCartManager;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.huiservers.utils.NoDoubleClickListener;
import com.huacheng.huiservers.view.MyImageSpan;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/03/20.
 */

public class ShopListFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    List<ModelShopIndex> mDatas;
    String login_type;
    SharedPreferences preferencesLogin;
    private boolean mShowFooter = true;

    int mPage = 0;

    public ShopListFragmentAdapter(List<ModelShopIndex> proLists, Context context) {//, List<String> dataSource
        this.mDatas = proLists;
        this.mContext = context;

    }

    public ShopListFragmentAdapter(List<ModelShopIndex> proLists, int page, Context context) {//, List<String> dataSource
        this.mDatas = proLists;
        this.mContext = context;
        this.mPage = page;

    }


    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView

        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 通过判断显示类型，来创建不同的View
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shop_list_item, parent, false);
            view.setOnClickListener(this);
            return new RecyclerViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_refresh_footer, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerViewHolder) {
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            recyclerViewHolder.itemView.setTag(position);

        //    recyclerViewHolder.iv_shop_list_flag.setVisibility(View.VISIBLE);
            ModelShopIndex index = mDatas.get(position);
            String discount = index.getDiscount();
            if ("1".equals(discount)) {
               // recyclerViewHolder.iv_shop_list_flag.setBackgroundResource(R.drawable.ic_shoplist_spike);
                recyclerViewHolder.tv_tag.setText("秒杀");
                recyclerViewHolder.tv_tag.setVisibility(View.VISIBLE);
            } else {
                if (index.getIs_hot().equals("1")) {
                 //   recyclerViewHolder.iv_shop_list_flag.setBackgroundResource(R.drawable.ic_shoplist_hotsell);
                    recyclerViewHolder.tv_tag.setText("热卖");
                    recyclerViewHolder.tv_tag.setVisibility(View.VISIBLE);
                } else if (index.getIs_new().equals("1")) {
                 //   recyclerViewHolder.iv_shop_list_flag.setBackgroundResource(R.drawable.ic_shoplist_newest);
                    recyclerViewHolder.tv_tag.setText("上新");
                    recyclerViewHolder.tv_tag.setVisibility(View.VISIBLE);
                } else {
                  //  recyclerViewHolder.iv_shop_list_flag.setBackground(null);
                    recyclerViewHolder.tv_tag.setVisibility(View.GONE);
                }
            }
            if ("2".equals(index.getPension_display())){
                recyclerViewHolder.tv_tag_kangyang.setVisibility(View.VISIBLE);
            }else {
                recyclerViewHolder.tv_tag_kangyang.setVisibility(View.GONE);
            }
         //   GlideUtils.getInstance().glideLoad(mContext,MyCookieStore.URL + mDatas.get(position).getTitle_img(),recyclerViewHolder.iv_title_img,R.drawable.ic_default_rectange);

            FrescoUtils.getInstance().setImageUri(recyclerViewHolder.iv_title_img, ApiHttpClient.IMG_URL + mDatas.get(position).getTitle_img());

           // recyclerViewHolder.tv_title.setText(mDatas.get(position).getTitle());
            //TODO vip标签
            String title = mDatas.get(position).getTitle()+"";
            String addSpan = "VIP折扣";
            SpannableString spannableString=new SpannableString(addSpan+" "+title);
            Drawable d = mContext.getResources().getDrawable(R.mipmap.ic_vip_span);
            d.setBounds(0, 0, DeviceUtils.dip2px(mContext,50), DeviceUtils.dip2px(mContext,16));
            ImageSpan span = new MyImageSpan(mContext,d);
            spannableString.setSpan(span, 0, addSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            recyclerViewHolder.tv_title.setText(spannableString);


            if (!NullUtil.isStringEmpty(mDatas.get(position).getDescription())){
                recyclerViewHolder.tv_sub_title.setText(mDatas.get(position).getDescription());
            }else {
                recyclerViewHolder.tv_sub_title.setText("");
            }

            recyclerViewHolder.lin_goodslist_Tag.removeAllViews();
            if (mDatas.get(position).getGoods_tag() != null) {
                for (int i = 0; i < mDatas.get(position).getGoods_tag().size(); i++) {
                    if (i < 2) {
                        View view = LinearLayout.inflate(mContext, R.layout.shop_list_tag_item, null);
                        TextView tag1 = (TextView) view.findViewById(R.id.txt_tag1);
                        tag1.setText(mDatas.get(position).getGoods_tag().get(i).getC_name());
                        recyclerViewHolder.lin_goodslist_Tag.addView(view);
                    }
                }
            }
            if (mDatas.get(position).getInventory().equals("0") || TextUtils.isEmpty(mDatas.get(position).getInventory())
                    || 0 > Integer.valueOf(mDatas.get(position).getInventory())) {
                recyclerViewHolder.tv_shouqing.setVisibility(View.VISIBLE);
            } else {
                recyclerViewHolder.tv_shouqing.setVisibility(View.GONE);
            }
            recyclerViewHolder.ly_onclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).getInventory().equals("0") || TextUtils.isEmpty(mDatas.get(position).getInventory())) {
                        SmartToast.showInfo("商品已售罄");
                    } else {
                        Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("shop_id", mDatas.get(position).getId());
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }

                }
            });
            recyclerViewHolder.tv_shop_price.setText("¥" + mDatas.get(position).getPrice());
            if (mDatas.get(position).getUnit().equals("")) {
                recyclerViewHolder.tv_shop_weight.setText("");
            } else {
                recyclerViewHolder.tv_shop_weight.setText("/" + mDatas.get(position).getUnit());
            }

            String onum = mDatas.get(position).getOrder_num();
            recyclerViewHolder.tv_shop_price_original.setText("¥" + mDatas.get(position).getOriginal());
            recyclerViewHolder.tv_shop_price_original.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            recyclerViewHolder.tv_orders_sold_num.setText("已售" + onum);

            recyclerViewHolder.iv_shopcar.setOnClickListener(new NoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {

                    preferencesLogin = mContext.getSharedPreferences("login", 0);
                    login_type = preferencesLogin.getString("login_type", "");
                    if (login_type.equals("")|| ApiHttpClient.TOKEN==null||ApiHttpClient.TOKEN_SECRET==null) {
                        Intent intent = new Intent(mContext, LoginVerifyCodeActivity.class);
                        mContext.startActivity(intent);
                        preferencesLogin = mContext.getSharedPreferences("login", 0);
                        SharedPreferences.Editor editor = preferencesLogin.edit();
                        editor.putString("login_shop", "shop_login");
                        editor.commit();
                    } else if (login_type.equals("1")) {
                        if (mDatas.get(position).getInventory().equals("0") || TextUtils.isEmpty(mDatas.get(position).getInventory())) {
                            SmartToast.showInfo("商品已售罄");
                        } else {
//                            mOnItemCarListener.onCarClick(position);
                            if (mDatas.get(position).getExist_hours().equals("2")) {
                                SmartToast.showInfo("当前时间不在派送时间范围内");
                            } else {
                                if (mDatas.get(position) != null) {
//                                    if (mtvShopCar != null) {
//                                        new CommonMethod(mDatas.get(position), mtvShopCar, mContext).getShopLimitTag();
//                                    } else {
//                                        new CommonMethod(mDatas.get(position), mContext).getShopLimitTag();
//                                    }
                                    ShopCartManager.getInstance().getShopLimitTag(mContext, mDatas.get(position), new ShopCartManager.OnAddShopCartResultListener() {
                                        @Override
                                        public void onAddShopCart(int status, String msg) {
                                            SmartToast.showInfo(msg);
                                        }
                                    });
                                }
                            }
                        }
                    } else {
                        SmartToast.showInfo("当前账号不是个人账号");
                    }
                }

            });

        } else if (holder instanceof FootViewHolder)

        {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING: // 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_COMPLETE: // 加载完成
                    footViewHolder.pbLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.INVISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;

                case LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }
    }

    // 设置是否显示底部加载提示（将值传递给全局变量）
    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }


    // 判断是否显示底部，数据来自全局变量
    public boolean isShowFooter() {
        return this.mShowFooter;
    }

    //购物车接口
    public interface OnItemCarListener {
        void onCarClick(int i);
    }

    public OnItemCarListener mOnItemCarListener;

    public void setOnItemCarListener(OnItemCarListener onItemCarListener) {
        this.mOnItemCarListener = onItemCarListener;
    }

    //item事件
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mItemClickListener;

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    //

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        // 没有数据的时候，直接返回begin
        if (mDatas == null) {
            return begin;
        }
        // 因为底部布局要占一个位置，所以总数目要+1
        return mDatas.size() + begin;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_shop_list_flag, iv_shopcar;
        SimpleDraweeView iv_title_img;
        TextView tv_title, tv_shop_price, tv_shop_weight, tv_orders_sold_num, tv_shop_price_original, tv_shouqing,tv_sub_title,tv_tag,tv_tag_kangyang;

        LinearLayout lin_goodslist_Tag, ly_onclick, lin_shop_list_car;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            iv_shop_list_flag = (ImageView) itemView.findViewById(R.id.iv_shop_list_flag);
            iv_title_img = (SimpleDraweeView) itemView.findViewById(R.id.iv_title_img);
            iv_shopcar = (ImageView) itemView.findViewById(R.id.iv_shopcar);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_shouqing = (TextView) itemView.findViewById(R.id.tv_shouqing);
            lin_goodslist_Tag = (LinearLayout) itemView.findViewById(R.id.lin_goodslist_Tag);
            ly_onclick = (LinearLayout) itemView.findViewById(R.id.ly_onclick);
            tv_shop_price = (TextView) itemView.findViewById(R.id.tv_shop_price);
            tv_shop_weight = (TextView) itemView.findViewById(R.id.tv_shop_weight);
            tv_shop_price_original = (TextView) itemView.findViewById(R.id.tv_shop_price_original);
            tv_orders_sold_num = (TextView) itemView.findViewById(R.id.tv_orders_sold_num);
            tv_sub_title = (TextView) itemView.findViewById(R.id.tv_sub_title);
            tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
            tv_tag_kangyang = (TextView) itemView.findViewById(R.id.tv_tag_kangyang);


        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }

    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }


}
