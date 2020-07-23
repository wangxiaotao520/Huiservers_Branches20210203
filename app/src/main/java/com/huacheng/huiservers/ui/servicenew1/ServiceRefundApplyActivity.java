package com.huacheng.huiservers.ui.servicenew1;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelRefundApplyList;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.servicenew.model.ModelOrderList;
import com.huacheng.huiservers.utils.NoDoubleClickListener;
import com.huacheng.libraryservice.utils.json.JsonUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 申请退款
 * created by wangxiaotao
 * 2020/6/18 0018 16:46
 */
public class ServiceRefundApplyActivity extends BaseActivity {
    @BindView(R.id.tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.ly_service_name)
    LinearLayout lyServiceName;
    @BindView(R.id.tv_service_merchant)
    TextView tvServiceMerchant;
    @BindView(R.id.ly_service_merchant)
    LinearLayout lyServiceMerchant;
    @BindView(R.id.tv_service_num)
    TextView tvServiceNum;
    @BindView(R.id.ly_refund_num)
    LinearLayout lyRefundNum;
    @BindView(R.id.tv_refund_price)
    TextView tvRefundPrice;
    @BindView(R.id.ly_refund_price)
    LinearLayout lyRefundPrice;
    @BindView(R.id.tv_service_account)
    TextView tvServiceAccount;
    @BindView(R.id.ly_refund_account)
    LinearLayout lyRefundAccount;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    List<String> mListFlowLayout = new ArrayList<>();
    @BindView(R.id.tv_btn)
    TextView tvBtn;
    @BindView(R.id.fl_bottom)
    FrameLayout flBottom;

    private int seleted_position = -1;//选中的位置
    private String id = "";
    private List<ModelRefundApplyList.RefundMyBean> list_refund_data;
    private ModelRefundApplyList modelRefundApplyList;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("申请退款");
        ButterKnife.bind(this);
      //  initFlowlayout();

    }


    @Override
    protected void initData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        MyOkHttp.get().post(ApiHttpClient.REFUND_LIST, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    modelRefundApplyList = (ModelRefundApplyList) JsonUtil.getInstance().parseJsonFromResponse(response, ModelRefundApplyList.class);
                    if (modelRefundApplyList !=null){
                        tvServiceName.setText(modelRefundApplyList.getS_name()+"");
                        tvServiceMerchant.setText(modelRefundApplyList.getI_name()+"");
                        tvServiceNum.setText(modelRefundApplyList.getNumber()+"");
                        tvRefundPrice.setText("¥ "+ modelRefundApplyList.getAmount()+"");
                        if ("alipay".equals(modelRefundApplyList.getPay_type())){
                            tvServiceAccount.setText("支付方式："+"支付宝");
                        }else if ("weixinpay".equals(modelRefundApplyList.getPay_type())){
                            tvServiceAccount.setText("支付方式："+"微信支付");
                        }else {
                            tvServiceAccount.setText("支付方式："+"云闪付");
                        }
                        list_refund_data = modelRefundApplyList.getRefund_my();
                        if (list_refund_data !=null&& list_refund_data.size()>0){
                            for (int i = 0; i < list_refund_data.size(); i++) {
                                mListFlowLayout.add(list_refund_data.get(i).getC_name());
                            }
                            if (mListFlowLayout.size() > 0) {
                                final LayoutInflater mInflater = LayoutInflater.from(mContext);
                                TagAdapter adapter = new TagAdapter<String>(mListFlowLayout) {

                                    @Override
                                    public View getView(FlowLayout parent, int position, String s) {
                                        TextView tv = (TextView) mInflater.inflate(R.layout.layout_refund_tag,
                                                idFlowlayout, false);
                                        tv.setText("" + mListFlowLayout.get(position));

                                        return tv;
                                    }
                                };
                                //adapter.setSelectedList(list_selected_position);
                                idFlowlayout.setAdapter(adapter);
                                idFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                                    @Override
                                    public void onSelected(Set<Integer> selectPosSet) {

                                        if (selectPosSet.isEmpty()) {

                                        } else {
                                            for (Integer integer : selectPosSet) {
                                                seleted_position = integer;
                                            }
                                        }

                                    }
                                });
                            }
                        }
                    }
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
        tvBtn.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (list_refund_data!=null){
                    if (seleted_position==-1){
                        SmartToast.showInfo("请选择退款原因");
                    }else {
                        String door_price = modelRefundApplyList.getDoor_price();
                        float door_price_float = Float.parseFloat(door_price);
                        if (door_price_float!=0){
                            new CommomDialog(ServiceRefundApplyActivity.this, R.style.my_dialog_DimEnabled, "师傅已上门后退款会扣除上门服务费 ¥ "+modelRefundApplyList.getDoor_price()+"元,您确定要申请退款吗？", new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(final Dialog dialog, boolean confirm) {
                                    if (confirm) {
                                        dialog.dismiss();
                                        gotoRefund(true);

                                    }

                                }
                            }).show();
                        }else {
                            gotoRefund(false);
                        }

                    }
                }
            }
        });
    }

    /**
     *
     * @param isDoorPrice 是否有上门费
     */
    private void gotoRefund(final boolean isDoorPrice) {
        //请求退款提交接口
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("type",list_refund_data.get(seleted_position).getC_alias());
        params.put("content",list_refund_data.get(seleted_position).getC_name());
        MyOkHttp.get().post(ApiHttpClient.REFUND_COMMIT, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {

                if (JsonUtil.getInstance().isSuccess(response)) {
                    if (isDoorPrice){
                        hideDialog(smallDialog);
                        String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交成功");
                        SmartToast.showInfo(msg);
                        ModelOrderList modelOrderList = new ModelOrderList();
                        modelOrderList.setEvent_type(0);
                        EventBus.getDefault().post(modelOrderList);

                        Intent intent = new Intent(mContext,ServiceRefundApplyResultActivity.class);
                        intent.putExtra("model",modelRefundApplyList);
                        intent.putExtra("reason",list_refund_data.get(seleted_position).getC_name()+"");
                        intent.putExtra("type",2);
                        startActivity(intent);
                        finish();
                    }else {
                       gotoNoDoorRequest();
                    }
                } else {
                    hideDialog(smallDialog);
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
     * 没有上门费请求这个接口
     */
    private void gotoNoDoorRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        MyOkHttp.get().post(ApiHttpClient.ORDER_CANCEL_NO_DOOR_COMMIT, params, new JsonResponseHandler() {

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    String msg = JsonUtil.getInstance().getMsgFromResponse(response, "提交成功");
                    SmartToast.showInfo(msg);
                    ModelOrderList modelOrderList = new ModelOrderList();
                    modelOrderList.setEvent_type(0);
                    EventBus.getDefault().post(modelOrderList);
                    Intent intent = new Intent(mContext,ServiceRefundApplyResultActivity.class);
                    intent.putExtra("model",modelRefundApplyList);
                    intent.putExtra("reason",list_refund_data.get(seleted_position).getC_name()+"");
                    intent.putExtra("type",1);
                    startActivity(intent);
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
    protected int getLayoutId() {
        return R.layout.activity_service_refund_apply;
    }

    @Override
    protected void initIntentData() {
        id=this.getIntent().getStringExtra("id");
    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

}
