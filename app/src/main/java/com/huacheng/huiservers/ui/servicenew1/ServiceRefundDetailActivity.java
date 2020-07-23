package com.huacheng.huiservers.ui.servicenew1;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.servicenew.model.ModelOrderList;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceOrderDetail;
import com.huacheng.huiservers.ui.servicenew1.adapter.ServiceOrderDetailAdapter;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
    private String id ="";
    private ModelServiceOrderDetail model;

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        findTitleViews();
        titleName.setText("退款详情");
        listView = findViewById(R.id.listview);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        headerView = LayoutInflater.from(this).inflate(R.layout.layout_service_refund_detail_header, null);
        initHeader(headerView);
        footerView = LayoutInflater.from(this).inflate(R.layout.layout_service_order_detail_footer, null);
        initFooter(footerView);
        listView.addHeaderView(headerView);
        listView.addFooterView(footerView);
        mAdapter = new ServiceOrderDetailAdapter(this, R.layout.item_service_order, mDatas,2);
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
        headerView.setVisibility(View.INVISIBLE);
        footerView.setVisibility(View.INVISIBLE);
        showDialog(smallDialog);
        requestData();
    }
    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        MyOkHttp.get().post(ApiHttpClient.ORDER_REFUND_DETAIL, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    model = (ModelServiceOrderDetail) JsonUtil.getInstance().parseJsonFromResponse(response, ModelServiceOrderDetail.class);
                   inflateContent();
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "获取数据失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
    }

    private void inflateContent() {
        if (model!=null){
            headerView.setVisibility(View.VISIBLE);
            footerView.setVisibility(View.VISIBLE);
            holderHeader.lyServiceContent.setVisibility(View.GONE);
            holderHeader.lyServiceMerchant.setVisibility(View.GONE);
            holderHeader.lyServiceRefundNumber.setVisibility(View.GONE);

            holderHeader.tvPrice.setText("¥ "+model.getRefunds_price()+"");
            if ("alipay".equals(model.getPay_type())){
                holderHeader.tvRefundReasonContent.setText("支付宝");
            }else if ("weixinpay".equals(model.getPay_type())){
                holderHeader.tvRefundReasonContent.setText("微信支付");
            }else {
                holderHeader.tvRefundReasonContent.setText("云闪付");
            }
            holderHeader.tvRefundReasonContent.setText(model.getRecord_reason()+"");
            FrescoUtils.getInstance().setImageUri(holderHeader.sdvHead,ApiHttpClient.IMG_URL+model.getTitle_img()+"");
            holderHeader.tvServiceName.setText(model.getS_name()+"");
            holderHeader.tvOrderNumber.setText("订单编号："+model.getOrder_number());
            holderHeader.tvOrderTime.setText("下单时间："+ StringUtils.getDateToString(model.getAddtime()+"","7"));
            holderHeader.tvOrderPrice.setText("订单金额："+ "¥ "+model.getAmount());

            holderHeader.llOrderDetailNameContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ServiceRefundDetailActivity.this, ServiceOrderDetailNew.class);
                    intent.putExtra("order_id", id);
                    intent.putExtra("jump_type", 2);
                    startActivity(intent);
                }
            });
            String status = model.getStatus();
            int status_int = Integer.parseInt(status);
            // 以下在退款详情页
             if (status_int==7){
                //退款审核中
                 holderHeader.ivStatusImg.setBackgroundResource(R.mipmap.ic_verfing);
                 holderHeader.tvStatusTxt.setText("退款审核中,请耐心等待");
                 holderHeader.tvStatusSubTxt.setText("商家将在1-2个工作日内审核您的退款申请");
                 holderHeader.tvBtn1.setVisibility(View.VISIBLE);
                 holderHeader.tvBtn1.setText("取消退款");
                 holderHeader.tvBtn1.setTextColor(getResources().getColor(R.color.orange));
                 holderHeader.tvBtn1.setBackgroundResource(R.drawable.allshape_stroke_orange_35);
                 holderHeader.tvBtn1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         //取消退款
                         new CommomDialog(mContext, R.style.my_dialog_DimEnabled, "确定取消退款？", new CommomDialog.OnCloseListener() {
                             @Override
                             public void onClick(Dialog dialog, boolean confirm) {
                                 if (confirm) {
                                     cancelRefund();
                                     dialog.dismiss();
                                 }
                             }
                         }).show();
                     }
                 });
            }else if (status_int==8){
                //退款中
                 holderHeader.ivStatusImg.setBackgroundResource(R.mipmap.ic_number_bind);
                 holderHeader.tvStatusTxt.setText("退款中...");
                 holderHeader.tvStatusSubTxt.setText("订单金额将在1~3个工作日退回您的付款账户");
                 holderHeader.tvBtn1.setVisibility(View.GONE);
            }else if (status_int==9){
                //审核失败
                 holderHeader.ivStatusImg.setBackgroundResource(R.mipmap.ic_fail);
                 holderHeader.tvStatusTxt.setText("退款审核失败");
                 holderHeader.tvStatusSubTxt.setText("您的订单退款失败");
                 holderHeader.tvBtn1.setText("再次提交");
                 holderHeader.tvBtn1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent intent = new Intent(mContext, ServiceRefundApplyActivity.class);
                         intent.putExtra("id",id+"");
                         startActivity(intent);
                     }
                 });
            }else if (status_int==10){
                //退款成功
                 holderHeader.ivStatusImg.setBackgroundResource(R.mipmap.ic_number_bind);
                 holderHeader.tvStatusTxt.setText("退款成功");
                 holderHeader.tvStatusSubTxt.setText("您的订单已退款成功");
                 holderHeader.tvBtn1.setVisibility(View.GONE);
            }else if (status_int==11){
                //退款失败
                 holderHeader.ivStatusImg.setBackgroundResource(R.mipmap.ic_fail);
                 holderHeader.tvStatusTxt.setText("退款失败");
                 holderHeader.tvStatusSubTxt.setText("您的订单退款失败");
                 holderHeader.ivStatusImg.setBackgroundResource(R.mipmap.ic_fail);
                 holderHeader.tvStatusTxt.setText("退款审核失败");
                 holderHeader.tvStatusSubTxt.setText("您的订单退款失败");
                 holderHeader.tvBtn1.setText("再次提交");
                 holderHeader.tvBtn1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent intent = new Intent(mContext, ServiceRefundApplyActivity.class);
                         intent.putExtra("id",id+"");
                         startActivity(intent);
                     }
                 });
            }
            mDatas.clear();
            List<ModelServiceOrderDetail.RecordBean> record_list = model.getRefund_record();
            if (record_list!=null&&record_list.size()>0){
                mDatas.addAll(record_list);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 取消退款
     */
    private void cancelRefund() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        MyOkHttp.get().post(ApiHttpClient.CANCEL_REFUND, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交成功");
                    SmartToast.showInfo(msg);
                    ModelOrderList modelOrderList = new ModelOrderList();
                    modelOrderList.setEvent_type(1);
                    EventBus.getDefault().post(modelOrderList);
                    finish();

                } else {

                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "获取数据失败");
                    SmartToast.showInfo(msg);
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                hideDialog(smallDialog);
                SmartToast.showInfo("网络异常，请检查网络设置");
            }
        });
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
        id =this.getIntent().getStringExtra("order_id");
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

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    /**
     * 更新数据（取消订单，评价完成）
     *
     * @param modelOrderList
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateOrderList(ModelOrderList modelOrderList) {
        if (modelOrderList!=null){
            if (modelOrderList.getEvent_type()==0){//申请退款
                finish();
            }
        }
    }
}

