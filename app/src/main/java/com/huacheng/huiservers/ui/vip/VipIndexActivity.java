package com.huacheng.huiservers.ui.vip;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelVipIndex;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.fragment.adapter.AdapterVipGridCat;
import com.huacheng.huiservers.ui.fragment.adapter.AdapterVipGridOpen;
import com.huacheng.huiservers.ui.fragment.adapter.AdapterVipIndex;
import com.huacheng.huiservers.view.MyGridview;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：vip特权首页
 * 时间：2020/11/30 08:35
 * created by DFF
 */
public class VipIndexActivity extends BaseActivity {
    @BindView(R.id.lin_left)
    LinearLayout mLinLeft;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.ly_message)
    LinearLayout mLyMessage;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.ly_share)
    LinearLayout mLyShare;
    @BindView(R.id.listview)
    ListView mListview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rel_no_data)
    RelativeLayout mRelNoData;

    View headerView;
    HeaderViewHolder headerViewHolder;


    private AdapterVipIndex mVipIndex;
    private AdapterVipGridCat mVipGridCat;
    private AdapterVipGridOpen mVipGridOpen;
    private List<ModelVipIndex> mDatas = new ArrayList<>();
    private List<ModelVipIndex> mDatasCat = new ArrayList<>();
    private List<ModelVipIndex> mDatasOpen = new ArrayList<>();

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        addHeaderView();
        //底部商品
        mVipIndex = new AdapterVipIndex(this, R.layout.item_old_gauge, mDatas);
        mListview.setAdapter(mVipIndex);

    }

    private void addHeaderView() {
        //添加list头布局
        headerView = LayoutInflater.from(this).inflate(R.layout.header_vip_index, null);
        mListview.addHeaderView(headerView);
        headerViewHolder = new HeaderViewHolder(headerView);

        headerViewHolder.mTvJilu.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        for (int i = 0; i < 8; i++) {
            mDatasCat.add(new ModelVipIndex());
        }
        //vip分类
        mVipGridCat = new AdapterVipGridCat(this, R.layout.item_vip_index_grid, mDatasCat);
        headerViewHolder.mGridVip.setAdapter(mVipGridCat);

        for (int i = 0; i < 3; i++) {
            mDatasOpen.add(new ModelVipIndex());
        }
        //vip开通方式
        mVipGridOpen = new AdapterVipGridOpen(this, R.layout.item_vip_index_open_style, mDatasOpen);
        headerViewHolder.mGridOpen.setAdapter(mVipGridOpen);
        mDatasOpen.get(0).setSelect(true);//默认第一条选中

        //优惠券
        headerViewHolder.mLlCoupon.removeAllViews();
        for (int i = 0; i < 3; i++) {
            View view_coupon = LayoutInflater.from(this).inflate(R.layout.item_vip_index_coupon, null);
            TextView tv_price = view_coupon.findViewById(R.id.tv_tag);
            TextView tv_coupon_price = view_coupon.findViewById(R.id.tv_coupon_price);
            TextView tv_total_price = view_coupon.findViewById(R.id.tv_total_price);
            TextView tv_coupon_type = view_coupon.findViewById(R.id.tv_coupon_type);

            headerViewHolder.mLlCoupon.addView(view_coupon);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mLinLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //选中开通方式
        headerViewHolder.mGridOpen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mDatasOpen.size(); i++) {
                    if (position == i) {
                        mDatasOpen.get(i).setSelect(true);
                    } else {
                        mDatasOpen.get(i).setSelect(false);
                    }
                }
                mVipGridOpen.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_vip_index;
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    class HeaderViewHolder {

        @BindView(R.id.sdv_bg)
        SimpleDraweeView mSdvBg;
        @BindView(R.id.sdv_head)
        SimpleDraweeView mSdvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_open_type)
        TextView mTvOpenType;
        @BindView(R.id.tv_open_num)
        TextView mTvOpenNum;
        @BindView(R.id.tv_buy)
        TextView mTvBuy;
        @BindView(R.id.tv_kesheng)
        TextView mTvKesheng;
        @BindView(R.id.tv_money)
        TextView mTvMoney;
        @BindView(R.id.tv_jilu)
        TextView mTvJilu;
        @BindView(R.id.tv_more_vip)
        TextView mTvMoreVip;
        @BindView(R.id.iv_more_vip)
        ImageView mIvMoreVip;
        @BindView(R.id.grid_vip)
        MyGridview mGridVip;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.grid_open)
        MyGridview mGridOpen;
        @BindView(R.id.tv_btn_buy)
        TextView mTvBtnBuy;
        @BindView(R.id.tv_xieyi)
        TextView mTvXieyi;
        @BindView(R.id.ll_coupon)
        LinearLayout mLlCoupon;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
