package com.huacheng.huiservers.ui.index.workorder;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.example.xlhratingbar_lib.XLHRatingBar;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.dialog.WorkPayDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.EventBusWorkOrderModel;
import com.huacheng.huiservers.model.ModelWorkDetail;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.geren.ZhifuActivity;
import com.huacheng.huiservers.ui.index.workorder.adapter.ImgAdapter;
import com.huacheng.huiservers.ui.index.workorder.adapter.WorkDetailImgAdapter;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.huacheng.libraryservice.widget.GridViewNoScroll;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：工单详情
 * 时间：2018/12/13 11:22
 * created by DFF
 */
public class WorkOrderDetailActivity extends BaseActivity {
    protected int work_type = 1;//1是公共服务 2.是用户
    protected int work_status = 1;//状态 不同的状态展示不同的界面
    protected ModelWorkDetail modelWorkDetail;//服务器返回的详情数据
    protected String work_id = "";//工单id
    protected LinearLayout mLyAll;
    protected LinearLayout mLinLeft;
    protected TextView mTitleName;
    protected TextView mTvRight;
    protected TextView mTvOrdertNum;
    protected TextView mTvAddress;
    protected  TextView mTvMasterName;
    protected TextView mTvMasterPhone;
    protected TextView mTvRepairType;
    protected GridViewNoScroll mGridviewImgs;
    protected TextView mTvRepairContent;
    protected LinearLayout mLyXiaDanInfo;
    protected TextView mTvOrderTime;
    protected TextView mTvPaidanTime;
    protected GridViewNoScroll mGridviewRepairPerson;
    protected TextView mTvAllPrice;
    protected TextView mTvJinDuType;
    protected LinearLayout mLyFinish;
    protected TextView mTvAllTime;
    protected TextView mTvOrderFinish;
    protected GridViewNoScroll mGridviewFinishImgs;
    protected TextView mTvMasterContent;
    protected XLHRatingBar mRatingBar;
    protected TextView mTvPingjiaContent;
    protected LinearLayout mLyPingjia;
    protected LinearLayout mLyCancel;
    protected TextView mTvCancelContent;
    protected TextView mTvCancel;
    protected TextView mTvPriceType;

    protected List<ModelWorkDetail.Repair_CompleteBean> mImg = new ArrayList<>();
    protected ImgAdapter imgAdapter;//用户提交的
    protected List<ModelWorkDetail.Repair_CompleteBean> mImg_master = new ArrayList<>();
    protected ImgAdapter imgAdapter_master;//师傅提交的

    protected List<ModelWorkDetail.Send_Distribute_UserBean> mPersonImg = new ArrayList<>();
    protected WorkDetailImgAdapter detailImgAdapter;
    private LinearLayout mLlInfoContainer;
    private RelativeLayout mRlIdNum;
    private RelativeLayout rl_yufu;
    private TextView tv_yufu_price;
    private TextView tv_pay_button;
    private WorkPayDialog workPayDialog;
    private View view_line;
    public static final int ACT_EVALUATE = 100;//评价

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        //标题栏
        mLyAll = findViewById(R.id.ly_all);//全部布局
        mLinLeft = findViewById(R.id.lin_left);
        mTitleName = findViewById(R.id.title_name);
        mTvRight = findViewById(R.id.tv_pay_pingjia);//评价或者支付按钮
        mTvCancel = findViewById(R.id.tv_cancel);//取消订单按钮
        //
        mRlIdNum = findViewById(R.id.ry_ID_num);
        mTvOrdertNum = findViewById(R.id.tv_ordert_num);//订单编号
        mTvPriceType = findViewById(R.id.tv_price_type);//付款状态
        //
        mLlInfoContainer = findViewById(R.id.ll_info_container);
        mTvAddress = findViewById(R.id.tv_address);//地址
        mTvMasterName = findViewById(R.id.tv_master_name);//下单者的名字
        mTvMasterPhone = findViewById(R.id.tv_master_phone);//下单者的电话
        mTvRepairType = findViewById(R.id.tv_repair_type);//需要修理的便签
        mGridviewImgs = findViewById(R.id.gridview_imgs);//需要修理的图片
        mTvRepairContent = findViewById(R.id.tv_repair_content);//下单维修备注
        view_line = findViewById(R.id.view_line);//横线
        //
        mLyXiaDanInfo = findViewById(R.id.ly_xiadan_info);//下单时间信息布局
        mTvOrderTime = findViewById(R.id.tv_order_time);//下单时间
        mTvPaidanTime = findViewById(R.id.tv_paidan_time);//派单时间
        mGridviewRepairPerson = findViewById(R.id.gridview_repair_person);//维修人员
        mTvAllPrice = findViewById(R.id.tv_all_price);//用户付款
        mTvJinDuType = findViewById(R.id.tv_jindu_type);//等待用户付款或者等待完工
        //
        mLyFinish = findViewById(R.id.ly_finish);//服务布局
        mTvAllTime = findViewById(R.id.tv_all_time);//服务用时
        mTvOrderFinish = findViewById(R.id.tv_order_finish);//订单完成时间
        mGridviewFinishImgs = findViewById(R.id.gridview_finish_imgs);//维修完成图片
        mTvMasterContent = findViewById(R.id.tv_master_content);//维修完成师傅备注
        mLyPingjia = findViewById(R.id.ly_pingjia);//评分布局
        mRatingBar = findViewById(R.id.ratingBar);//评分
        mTvPingjiaContent = findViewById(R.id.tv_pingjia_content);//评价
        //
        mLyCancel = findViewById(R.id.ly_cancel);//订单取消
        mTvCancelContent = findViewById(R.id.tv_cancel_content);//是否付款显示退款说明

        //用户提交的图片信息
        imgAdapter = new ImgAdapter(this, R.layout.activity_base_work_order_img, mImg);
        mGridviewImgs.setAdapter(imgAdapter);
        //师傅提交的图片信息
        imgAdapter_master = new ImgAdapter(this, R.layout.activity_base_work_order_img, mImg_master);
        mGridviewFinishImgs.setAdapter(imgAdapter_master);
        //派单人员
        detailImgAdapter = new WorkDetailImgAdapter(this, R.layout.layout_workorder_detail_img_item, mPersonImg);
        mGridviewRepairPerson.setAdapter(detailImgAdapter);

        rl_yufu = findViewById(R.id.rl_yufu);
        tv_yufu_price = findViewById(R.id.tv_yufu_price);
        tv_pay_button = findViewById(R.id.tv_pay_button);
    }

    @Override
    protected void initData() {
        showDialog(smallDialog);
        HashMap<String, String> mParams = new HashMap<>();
        mParams.put("id", work_id);
        MyOkHttp.get().post(ApiHttpClient.WORK_DETAIL, mParams, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ModelWorkDetail info = (ModelWorkDetail) JsonUtil.getInstance().parseJsonFromResponse(response, ModelWorkDetail.class);
                   inflateContent(info);
                } else {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "");
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
        mLinLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_order_detail;
    }

    @Override
    protected void initIntentData() {
        work_id = getIntent().getStringExtra("work_id");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    /**
     * 填充内容
     *
     * @param detail
     */
    protected void inflateContent(ModelWorkDetail detail) {
        if (detail != null) {
            //显示布局
            mLyAll.setVisibility(View.VISIBLE);
            mLlInfoContainer.setVisibility(View.VISIBLE);
            mRlIdNum.setVisibility(View.VISIBLE);
            modelWorkDetail = detail;
            work_type = detail.getWork_type();
            work_status = detail.getWork_status();
            //下单信息
            mTvAddress.setText(detail.getAddress());
          //  mTvMasterName.setText(detail.getUsername());
            mTvMasterName.setText(detail.getContact());
            mTvMasterPhone.setText(detail.getUserphone());
            mTvRepairType.setText(detail.getType_name());

            //用户提交图片信息
            if (detail.getRepairImg() != null && detail.getRepairImg().size() > 0) {
                mImg.clear();
                mImg.addAll(detail.getRepairImg());
                imgAdapter.notifyDataSetChanged();
            }
            if (NullUtil.isStringEmpty(detail.getContent())) {
                mTvRepairContent.setText("维修备注：无");
            } else {
                mTvRepairContent.setText("维修备注：" + detail.getContent());
            }
            mTvOrdertNum.setText("订单编号：" + detail.getOrder_number());
            //付款金额按钮暂时先显示为空，后面会做判断
            mTvPriceType.setText("");
            mTvPriceType.setVisibility(View.GONE);

            //订单时间
            mTvOrderTime.setText("下单时间："+ StringUtils.getDateToString(detail.getRelease_at(), "7"));
            if (detail.getDistribute_at()!=0){
                mTvPaidanTime.setVisibility(View.VISIBLE);
                mTvPaidanTime.setText("派单时间："+StringUtils.getDateToString(detail.getDistribute_at()+"", "7"));
            }else {
                mTvPaidanTime.setText("");
                mTvPaidanTime.setVisibility(View.GONE);
            }
            if (detail.getDistributeUser()!=null&&detail.getDistributeUser().size()>0){
                mPersonImg.clear();
                mPersonImg.addAll(detail.getDistributeUser());
                detailImgAdapter.notifyDataSetChanged();
            }

            //增派人员头像
            if (detail.getSendUser()!=null&&detail.getSendUser().size()>0){
                mPersonImg.addAll(detail.getSendUser());
                detailImgAdapter.notifyDataSetChanged();
            }
            //需判断是否是已付款  付款中
            if (detail.getTotal_fee()==0){
                mTvAllPrice.setText("");
                mTvAllPrice.setVisibility(View.GONE);
            }else {
                mTvAllPrice.setVisibility(View.VISIBLE);
                mTvAllPrice.setText("用户付款：¥" + detail.getTotal_fee());
            }
            //需判断 订单是完成还是进行中的 取其一
            if (work_status==5){//完成
                mTvAllTime.setText("服务用时：" + detail.getOrder_total_time());
            }else {
                mTvAllTime.setText("服务用时：" + detail.getOrder_time());
            }
            mTvOrderFinish.setText("订单完成：" + StringUtils.getDateToString(detail.getComplete_at(), "7"));
            //师傅提交的图片信息
            if (detail.getCompleteImg() != null && detail.getCompleteImg().size() > 0) {
                mImg_master.clear();
                mImg_master.addAll(detail.getCompleteImg());
                imgAdapter_master.notifyDataSetChanged();
            }
            if (NullUtil.isStringEmpty(detail.getComplete_content())) {
                mTvMasterContent.setText("完工备注：无");
            } else {
                mTvMasterContent.setText("完工备注：" + detail.getComplete_content());
            }
            if (modelWorkDetail.getScore() != null) {//评价
                mRatingBar.setCountSelected(Integer.valueOf(detail.getScore().getLevel()));
                if (!NullUtil.isStringEmpty(detail.getEvaluate_content())) {
                    mTvPingjiaContent.setText(detail.getEvaluate_content());
                }
            }
            //处理逻辑
            if (work_status==0){//未支付预付款
                rl_yufu.setVisibility(View.VISIBLE);
                mTvCancel.setVisibility(View.GONE);
                tv_yufu_price.setText(modelWorkDetail.getEntry_fee()+"元");
                mTvJinDuType.setText("");
                tv_pay_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(WorkOrderDetailActivity.this, ZhifuActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("o_id", work_id);
                        bundle.putString("price", modelWorkDetail.getEntry_fee()+"");//
                        bundle.putString("type", "workorder_yufu");
                        bundle.putString("order_type", "yf");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                });
            }else if (work_status==1){//待派单
                mTvCancel.setVisibility(View.VISIBLE);
                mTvJinDuType.setText("");
                mTvJinDuType.setText("等待服务...");
                mTvJinDuType.setVisibility(View.GONE);

                mTvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cancelOrder();
                    }
                });
            }else if (work_status==2){//派单中
                mTvCancel.setVisibility(View.VISIBLE);
                mTvJinDuType.setText("等待服务...");
                mGridviewRepairPerson.setVisibility(View.GONE);
                mTvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cancelOrder();
                    }
                });
            }else if (work_status==3){//待付款
                mTvJinDuType.setText("服务中...");
                mGridviewRepairPerson.setVisibility(View.GONE);
                mTvPriceType.setText("未付：¥ "+modelWorkDetail.getTotal_fee());
                mTvPriceType.setTextColor(getResources().getColor(R.color.orange));
                mTvPriceType.setVisibility(View.VISIBLE);
                mTvRight.setVisibility(View.VISIBLE);

                mTvRight.setText("付款");
                mTvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 付款
                        payOrder();
                    }
                });
            }else if (work_status==4){//待完工
                mTvJinDuType.setText("等待完工...");
                if (modelWorkDetail.getWork_type()==1){
                    mTvPriceType.setVisibility(View.VISIBLE);
                    mTvPriceType.setText("已付：¥ "+modelWorkDetail.getTotal_fee());
                    mTvPriceType.setTextColor(getResources().getColor(R.color.green));
                    mTvRight.setVisibility(View.GONE);
                }else {
                    //公共报修
                    mTvPriceType.setVisibility(View.GONE);
                    mTvRight.setVisibility(View.GONE);
                }

            }else if (work_status==5){//已完工
                if (modelWorkDetail.getWork_type()==1){
                    mTvRight.setVisibility(View.VISIBLE);
                    mTvRight.setText("评价");
                    mTvRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //评价
                            evaluate();
                        }
                    });
                    mLyFinish.setVisibility(View.VISIBLE);
                    mTvPriceType.setVisibility(View.VISIBLE);
                    mTvPriceType.setText("已付：¥ "+modelWorkDetail.getTotal_fee());
                    mTvPriceType.setTextColor(getResources().getColor(R.color.green));
                    mTvJinDuType.setVisibility(View.GONE);
                    if (modelWorkDetail.getScore()!=null){
                        mTvRight.setVisibility(View.GONE);
                        mRatingBar.setCountSelected(Integer.valueOf(detail.getScore().getLevel()));
                        if (!NullUtil.isStringEmpty(detail.getScore().getEvaluate_content())) {
                            mTvPingjiaContent.setText(detail.getScore().getEvaluate_content());
                        }
                        mLyPingjia.setVisibility(View.VISIBLE);
                    }
                }else {
                    mTvRight.setVisibility(View.GONE);
                    mLyFinish.setVisibility(View.VISIBLE);
                    mTvPriceType.setVisibility(View.GONE);
                    mTvJinDuType.setVisibility(View.GONE);
                }

            }else if (work_status==6){//取消
                view_line.setVisibility(View.GONE);
                mTvRight.setVisibility(View.GONE);
                mLyFinish.setVisibility(View.GONE);
                mTvOrderTime.setVisibility(View.GONE);
                mTvJinDuType.setText("");
                mTvJinDuType.setVisibility(View.GONE);
                mTvRight.setVisibility(View.GONE);
                mLyCancel.setVisibility(View.VISIBLE);
                if (modelWorkDetail.getWork_type()==1){
                    mTvCancelContent.setVisibility(View.VISIBLE);
                }else {
                    mTvCancelContent.setVisibility(View.GONE);
                }

            }
        }

    }

    /**
     * 评价
     */
    private void evaluate() {
        Intent intent = new Intent(WorkOrderDetailActivity.this, WorkEvaluateActivity.class);
        intent.putExtra("w_id",work_id);
        startActivityForResult(intent,ACT_EVALUATE);
    }

    /**
     * 支付订单
     */
    private void payOrder() {
        //支付订单
        //一卡通
        //
        workPayDialog = new WorkPayDialog(this, modelWorkDetail.getTotal_fee() , modelWorkDetail.getEntry_fee() , new WorkPayDialog.OnClickSureListener() {
            @Override
            public void onEnSure() {
                Intent intent = new Intent(WorkOrderDetailActivity.this, ZhifuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("o_id", work_id);
                bundle.putString("price", (modelWorkDetail.getTotal_fee()-modelWorkDetail.getEntry_fee()) + "");//
                bundle.putString("type", "workorder_pay");
                bundle.putString("order_type", "wo");

                intent.putExtras(bundle);
                startActivity(intent);
                workPayDialog.dismiss();
            }
        });

        workPayDialog.show();
    }

    /**
     * 取消订单
     */
    private void cancelOrder() {
        new CommomDialog(this, R.style.my_dialog_DimEnabled, "确认取消订单吗？", new CommomDialog.OnCloseListener() {

            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    cancelOrderCommit();
                }
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 取消订单
     */
    private void cancelOrderCommit() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", work_id);
        MyOkHttp.get().post(ApiHttpClient.WorkCancel, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                String msg = "";
                if (JsonUtil.getInstance().isSuccess(response)) {
                    msg = JsonUtil.getInstance().getMsgFromResponse(response, "请求成功");
                    SmartToast.showInfo(msg);
                    EventBusWorkOrderModel model = new EventBusWorkOrderModel();
                    model.setEvent_type(0);
                    model.setWo_id(work_id);
                    EventBus.getDefault().post(model);
                    finish();

                    //给物业端管理角色推送,用户取消已派单订单
                    HashMap<String, String> params = new HashMap<>();
                    params.put("id", work_id);
                    params.put("type", "2");
                    new JpushWorkPresenter().userToWorkerSubmitJpush(params);
                } else {
                    msg = JsonUtil.getInstance().getMsgFromResponse(response, "数据获取失败");
                }
                SmartToast.showInfo(msg);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                SmartToast.showInfo("网络异常，请检查网络设置");
            }

        });
    }

    /**
     *
     *Eventbus
     * @param model
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(EventBusWorkOrderModel model) {
        if (model!=null){
            String cancel_wid = model.getWo_id();
            if (model.getEvent_type()==0){//取消订单

            }else if (model.getEvent_type()==1){

            }else if (model.getEvent_type()==2){
                // 付款
                initData();
            }

        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ACT_EVALUATE:
                    initData();
                    break;
                default:
                    break;
            }
        }
    }

}
