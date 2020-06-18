package com.huacheng.huiservers.ui.servicenew1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.servicenew1.adapter.ServiceOrderDetailAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 2.0服务订单详情
 * created by wangxiaotao
 * 2020/6/17 0017 17:21
 */
public class ServiceOrderDetailNew extends BaseActivity {
    private ListView listView;
    private SmartRefreshLayout refreshLayout;
    private View headerView;
    private View footerView;
    private List<String> mDatas = new ArrayList<>();
    private ServiceOrderDetailAdapter mAdapter;
    private FrameLayout fl_bottom;
    private TextView tv_btn;
    private ViewHolder_Header holderHeader;
    private ImageView iv_footer_img;
    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("订单详情");
        listView = findViewById(R.id.listview);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        headerView = LayoutInflater.from(this).inflate(R.layout.layout_service_order_detail_header, null);
        initHeader(headerView);
        footerView = LayoutInflater.from(this).inflate(R.layout.layout_service_order_detail_footer, null);
        initFooter(footerView);
        listView.addHeaderView(headerView);
        listView.addFooterView(footerView);
        mAdapter = new ServiceOrderDetailAdapter(this, R.layout.item_service_order, mDatas);
        listView.setAdapter(mAdapter);

        fl_bottom = findViewById(R.id.fl_bottom);
        tv_btn = findViewById(R.id.tv_btn);

    }

    private void initHeader(View headerView) {
        //头部相关布局
        holderHeader = new ViewHolder_Header(headerView);
        holderHeader.tvBtn2.setVisibility(View.VISIBLE);
    }

    private void initFooter(View footerView) {
        //footer相关布局
        iv_footer_img = footerView.findViewById(R.id.iv_footer_img);
    }

    @Override
    protected void initData() {
        //TODO 测试
        for (int i = 0; i < 6; i++) {
            mDatas.add("1");
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        holderHeader.tvBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                startActivity(new Intent(mContext,ServiceRefundApplyActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_order_detail_new;
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

    static
    class ViewHolder_Header {
        @BindView(R.id.iv_status_img)
        ImageView ivStatusImg;
        @BindView(R.id.iv_status_txt)
        TextView ivStatusTxt;
        @BindView(R.id.iv_status_sub_txt)
        TextView ivStatusSubTxt;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_user_phone)
        TextView tvUserPhone;
        @BindView(R.id.tv_user_address)
        TextView tvUserAddress;
        @BindView(R.id.ly_user_address)
        LinearLayout lyUserAddress;
        @BindView(R.id.sdv_head)
        SimpleDraweeView sdvHead;
        @BindView(R.id.tv_service_name)
        TextView tvServiceName;
        @BindView(R.id.tv_service_detail_jump)
        TextView tvServiceDetailJump;
        @BindView(R.id.ll_order_detail_name_container)
        LinearLayout llOrderDetailNameContainer;
        @BindView(R.id.tv_order_num)
        TextView tvOrderNum;
        @BindView(R.id.tv_order_time)
        TextView tvOrderTime;
        @BindView(R.id.tv_buy_num)
        TextView tvBuyNum;
        @BindView(R.id.tv_price_tag)
        TextView tvPriceTag;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.ly_price)
        LinearLayout lyPrice;
        @BindView(R.id.tv_pay_type)
        TextView tvPayType;
        @BindView(R.id.tv_beizhu)
        TextView tvBeizhu;
        @BindView(R.id.ly_beizhu)
        LinearLayout lyBeizhu;
        @BindView(R.id.tv_btn_1)
        TextView tvBtn1;
        @BindView(R.id.tv_btn_2)
        TextView tvBtn2;

        ViewHolder_Header(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
