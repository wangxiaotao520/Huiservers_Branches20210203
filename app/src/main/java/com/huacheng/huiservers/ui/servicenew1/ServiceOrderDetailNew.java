package com.huacheng.huiservers.ui.servicenew1;

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
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceOrderDetail;
import com.huacheng.huiservers.ui.servicenew.ui.ServiceDetailActivity;
import com.huacheng.huiservers.ui.servicenew1.adapter.ServiceOrderDetailAdapter;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
    private List<ModelServiceOrderDetail.RecordBean> mDatas = new ArrayList<>();
    private ServiceOrderDetailAdapter mAdapter;
    private FrameLayout fl_bottom;
    private TextView tv_btn;
    private ViewHolder_Header holderHeader;
    private ImageView iv_footer_img;
    private String id ="";
    private ModelServiceOrderDetail model;
    private TextView tvRight;
    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("订单详情");
        tvRight=findViewById(R.id.txt_right1);
        tvRight.setVisibility(View.GONE);
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
        headerView.setVisibility(View.INVISIBLE);
        footerView.setVisibility(View.INVISIBLE);
        showDialog(smallDialog);
        requestData();

    }

    private void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        MyOkHttp.get().post(ApiHttpClient.GET_ORDER_DETAIL, params, new JsonResponseHandler() {

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

    /**
     * 填充数据
     */
    private void inflateContent() {
        if (model!=null){
            headerView.setVisibility(View.VISIBLE);
            footerView.setVisibility(View.VISIBLE);
            holderHeader.tvUserName.setText(model.getContacts()+"");
            holderHeader.tvUserPhone.setText(model.getMobile()+"");
            holderHeader.tvUserAddress.setText(model.getAddress()+"");
            FrescoUtils.getInstance().setImageUri(holderHeader.sdvHead,ApiHttpClient.IMG_URL+model.getTitle_img()+"");
            holderHeader.tvServiceName.setText(model.getS_name()+"");
            holderHeader.llOrderDetailNameContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s_id = model.getS_id();
                    Intent intent = new Intent();
                    intent.setClass(mContext, ServiceDetailActivity.class);
                    intent.putExtra("service_id", s_id);
                    mContext.startActivity(intent);
                }
            });
            holderHeader. tvOrderNum.setText("订单编号："+model.getOrder_number());
            holderHeader. tvOrderTime.setText("下单时间："+ StringUtils.getDateToString(model.getAddtime()+"","1"));
            holderHeader. tvBuyNum.setText("购买数量："+model.getNumber());
            holderHeader. tvPrice.setText("¥ "+model.getAmount());
            holderHeader. tvPayType.setText("支付方式："+model.getPay_type());
            holderHeader. tvBeizhu.setText(model.getDescription()+"");
            String status = model.getStatus();
            int status_int = Integer.parseInt(status);
            if (status_int==2){
                //待派单
                holderHeader.llStatusContainer.setVisibility(View.VISIBLE);
                holderHeader.ivStatusTxt.setText("下单成功");
                holderHeader.ivStatusSubTxt.setText("请提前联系商家预约上门服务时间");
                holderHeader.tvBtn1.setVisibility(View.VISIBLE);
                holderHeader.tvBtn1.setText("申请退款");
                holderHeader.tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //申请退款
                        //todo
                        startActivity(new Intent(mContext,ServiceRefundApplyActivity.class));
                    }
                });
                holderHeader.tvBtn2.setVisibility(View.GONE);
                fl_bottom.setVisibility(View.VISIBLE);
                tv_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //联系商家
                    }
                });
            }else if (status_int==3){
                //已派单
                holderHeader.llStatusContainer.setVisibility(View.GONE);
                holderHeader.tvBtn1.setVisibility(View.VISIBLE);
                holderHeader.tvBtn1.setText("申请退款");
                holderHeader.tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //申请退款
                        //todo
                        startActivity(new Intent(mContext,ServiceRefundApplyActivity.class));
                    }
                });
                holderHeader.tvBtn2.setVisibility(View.GONE);
                fl_bottom.setVisibility(View.VISIBLE);
                tv_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //联系商家
                    }
                });
            }else if (status_int==4){
                //已上门
                holderHeader.llStatusContainer.setVisibility(View.GONE);
                holderHeader.tvBtn1.setVisibility(View.VISIBLE);
                holderHeader.tvBtn1.setText("申请退款");
                holderHeader.tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //申请退款
                        //todo
                        startActivity(new Intent(mContext,ServiceRefundApplyActivity.class));
                    }
                });
                holderHeader.tvBtn2.setVisibility(View.VISIBLE);
                holderHeader.tvBtn2.setText("完成服务");
                holderHeader.tvBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //完成服务
                        //todo
                    }
                });
                fl_bottom.setVisibility(View.VISIBLE);
                tv_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //联系商家
                    }
                });
            }else if (status_int==5){
                //待评价
                holderHeader.llStatusContainer.setVisibility(View.GONE);
                holderHeader.tvBtn1.setVisibility(View.VISIBLE);
                holderHeader.tvBtn1.setText("再次购买");
                holderHeader.tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //再次购买
                        //todo

                    }
                });
                holderHeader.tvBtn2.setVisibility(View.VISIBLE);
                holderHeader.tvBtn2.setText("立即评价");
                holderHeader.tvBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //立即评价
                        //todo
                    }
                });
                fl_bottom.setVisibility(View.VISIBLE);
                tv_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //联系商家
                    }
                });
                tvRight.setText("删除订单");
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除订单
                    }
                });
            }else if (status_int==6){
                //已完成
                holderHeader.llStatusContainer.setVisibility(View.GONE);
                holderHeader.tvBtn1.setVisibility(View.VISIBLE);
                holderHeader.tvBtn1.setText("再次购买");
                holderHeader.tvBtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //再次购买
                        //todo

                    }
                });
                holderHeader.tvBtn2.setVisibility(View.GONE);

                fl_bottom.setVisibility(View.GONE);
                tv_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //联系商家
                    }
                });
                tvRight.setText("删除订单");
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除订单
                    }
                });

            }
            //TODO 以下在退款详情页
            else if (status_int==7){
                //退款审核中


            }else if (status_int==8){
                //退款审核中

            }else if (status_int==9){
                //审核失败

            }else if (status_int==10){
                //退款成功

            }else if (status_int==11){
                //退款失败

            }
            mDatas.clear();
            List<ModelServiceOrderDetail.RecordBean> record_list = model.getRecord();
            if (record_list!=null&&record_list.size()>0){
                mDatas.addAll(record_list);
            }
            mAdapter.notifyDataSetChanged();

        }
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
        @BindView(R.id.iv_status_img)
        ImageView ivStatusImg;
        @BindView(R.id.iv_status_txt)
        TextView ivStatusTxt;
        @BindView(R.id.iv_status_sub_txt)
        TextView ivStatusSubTxt;
        @BindView(R.id.ll_status_container)
        LinearLayout llStatusContainer;
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
