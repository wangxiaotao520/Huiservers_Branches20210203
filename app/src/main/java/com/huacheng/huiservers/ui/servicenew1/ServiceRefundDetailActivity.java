package com.huacheng.huiservers.ui.servicenew1;

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
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceOrderDetail;
import com.huacheng.huiservers.ui.servicenew1.adapter.ServiceOrderDetailAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 2.0服务退款详情页
 * created by wangxiaotao
 * 2020/6/18 0018 11:25
 */
public class ServiceRefundDetailActivity extends BaseActivity {
    private ListView listView;
    private SmartRefreshLayout refreshLayout;
    private View headerView;
    private View footerView;
    private List<ModelServiceOrderDetail.RecordBean> mDatas = new ArrayList<>();
    private ServiceOrderDetailAdapter mAdapter;
    private FrameLayout fl_bottom;
    private TextView tv_btn;
    private ViewHolder_Header holderHeader;
    private ImageView iv_footer_img;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("退款详情");
        listView = findViewById(R.id.listview);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadMore(false);
        headerView = LayoutInflater.from(this).inflate(R.layout.layout_service_refund_detail_header, null);
        initHeader(headerView);
        footerView = LayoutInflater.from(this).inflate(R.layout.layout_service_order_detail_footer, null);
        initFooter(footerView);
        listView.addHeaderView(headerView);
        listView.addFooterView(footerView);
        mAdapter = new ServiceOrderDetailAdapter(this, R.layout.item_service_order, mDatas);
        listView.setAdapter(mAdapter);

        fl_bottom = findViewById(R.id.fl_bottom);
        tv_btn = findViewById(R.id.tv_btn);
        fl_bottom.setVisibility(View.GONE);
    }

    private void initHeader(View headerView) {
        //头部相关布局
        holderHeader = new ViewHolder_Header(headerView);
        holderHeader.llServiceContentContainer.setVisibility(View.GONE);
    }

    private void initFooter(View footerView) {
        //footer相关布局
        iv_footer_img = footerView.findViewById(R.id.iv_footer_img);
    }

    @Override
    protected void initData() {
        //TODO 测试
//        for (int i = 0; i < 6; i++) {
//            mDatas.add("1");
//        }
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {

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
        //头部相关布局
        @BindView(R.id.iv_status_img)
        ImageView ivStatusImg;
        @BindView(R.id.tv_status_txt)
        TextView tvStatusTxt;
        @BindView(R.id.tv_status_sub_txt)
        TextView tvStatusSubTxt;
        @BindView(R.id.tv_service_content)
        TextView tvServiceContent;  //服务内容
        @BindView(R.id.ly_service_content)
        LinearLayout lyServiceContent;
        @BindView(R.id.tv_service_merchant)
        TextView tvServiceMerchant;  //服务商家
        @BindView(R.id.ly_service_merchant)
        LinearLayout lyServiceMerchant;
        @BindView(R.id.tv_service_refund_number)
        TextView tvServiceRefundNumber;  //服务数量
        @BindView(R.id.ly_service_refund_number)
        LinearLayout lyServiceRefundNumber;
        @BindView(R.id.ll_service_content_container)
        LinearLayout llServiceContentContainer; //服务内容的那三个布局
        @BindView(R.id.tv_price_tag)
        TextView tvPriceTag;
        @BindView(R.id.tv_price)
        TextView tvPrice; //退款价格
        @BindView(R.id.ly_price)
        LinearLayout lyPrice;
        @BindView(R.id.tv_refund_account_type)
        TextView tvRefundAccountType;
        @BindView(R.id.ly_refund_account)
        LinearLayout lyRefundAccount;
        @BindView(R.id.tv_refund_reason_content)
        TextView tvRefundReasonContent;
        @BindView(R.id.ly_refund_reason)
        LinearLayout lyRefundReason;
        @BindView(R.id.tv_service_name)
        TextView tvServiceName;
        @BindView(R.id.tv_service_detail)
        TextView tvServiceDetail;
        @BindView(R.id.sdv_head)
        SimpleDraweeView sdvHead;
        @BindView(R.id.tv_order_number)
        TextView tvOrderNumber;
        @BindView(R.id.tv_order_time)
        TextView tvOrderTime;
        @BindView(R.id.tv_order_price)
        TextView tvOrderPrice;  //订单价格
        @BindView(R.id.ll_order_detail_name_container)
        LinearLayout llOrderDetailNameContainer; //订单详情布局
        @BindView(R.id.tv_btn_1)
        TextView tvBtn1;

        ViewHolder_Header(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

