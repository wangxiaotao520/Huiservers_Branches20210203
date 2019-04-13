package com.huacheng.huiservers.ui.index.workorder_second;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.http.okhttp.MyOkHttp;
import com.huacheng.huiservers.http.okhttp.response.JsonResponseHandler;
import com.huacheng.huiservers.model.ModelNewWorkOrder;
import com.huacheng.huiservers.ui.base.BaseActivity;
import com.huacheng.huiservers.ui.center.geren.ZhifuActivity;
import com.huacheng.huiservers.ui.index.workorder_second.adapter.WorkOrderDetailAdapter;
import com.huacheng.huiservers.utils.HiddenAnimUtils;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.huiservers.view.MyListView;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.huacheng.libraryservice.utils.json.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cn.jiguang.d.a.i;

/**
 * 类描述：
 * 时间：2019/4/9 09:03
 * created by DFF
 */
public class WorkOrderDetailActivity extends BaseActivity implements View.OnClickListener, WorkOrderDetailAdapter.OnclickImg {
    LinearLayout mlinear_repair_person, mlinear_repair_photo, mlinear_photo, linear_visibility, ly_btn, linear_other_info, ly_all;
    WorkOrderDetailAdapter mWorkOrderDetailAdapter;
    List<ModelNewWorkOrder.WorkLogBean> mlistLog = new ArrayList<>();
    MyListView mListView;
    ImageView iv_up;
    TextView tv_bianhao, tv_repair_date, tv_baoxiu_type, tv_jinji, tv_user_name, tv_baoxiu_content, tv_user_address, tv_user_photo, tv_up_name,
            tv_none, tv_btn;
    String work_id = "";//工单id
    String work_status = "";//工单状态
    private int height = 0;
    ModelNewWorkOrder mNewWorkOrder;

    @Override
    protected void initView() {
        findTitleViews();
        titleName.setText("工单详情");

        ly_all = findViewById(R.id.ly_all);
        mlinear_repair_person = findViewById(R.id.linear_repair_person);
        mlinear_repair_photo = findViewById(R.id.linear_repair_photo);
        linear_other_info = findViewById(R.id.linear_other_info);
        mlinear_photo = findViewById(R.id.linear_photo);
        tv_bianhao = findViewById(R.id.tv_bianhao);
        tv_repair_date = findViewById(R.id.tv_repair_date);
        tv_baoxiu_type = findViewById(R.id.tv_baoxiu_type);
        tv_jinji = findViewById(R.id.tv_jinji);
        tv_baoxiu_content = findViewById(R.id.tv_baoxiu_content);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_address = findViewById(R.id.tv_user_address);
        tv_user_photo = findViewById(R.id.tv_user_photo);
        tv_none = findViewById(R.id.tv_none);
        linear_visibility = findViewById(R.id.linear_visibility);
        tv_up_name = findViewById(R.id.tv_up_name);
        iv_up = findViewById(R.id.iv_up);
        ly_btn = findViewById(R.id.ly_btn);
        tv_btn = findViewById(R.id.tv_btn);
        mListView = findViewById(R.id.mListView);
        tv_user_photo.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        mWorkOrderDetailAdapter = new WorkOrderDetailAdapter(this, R.layout.activity_workorder_detail_item_list, mlistLog, this);
        mListView.setAdapter(mWorkOrderDetailAdapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //获取平铺布局的高度
        height = linear_other_info.getHeight();
        linear_other_info.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        getDetail();
    }

    @Override
    protected void initListener() {
        //缩放按钮
        linear_visibility.setOnClickListener(this);

    }

    /**
     * 请求数据
     */
    private void getDetail() {
        showDialog(smallDialog);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", work_id);
        MyOkHttp.get().post(ApiHttpClient.GET_WORK_DETAIL, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                hideDialog(smallDialog);
                if (JsonUtil.getInstance().isSuccess(response)) {
                    ly_all.setVisibility(View.VISIBLE);
                    ly_btn.setOnClickListener(WorkOrderDetailActivity.this);
                    ModelNewWorkOrder modelNewWorkOrder = (ModelNewWorkOrder) JsonUtil.getInstance().parseJsonFromResponse(response, ModelNewWorkOrder.class);
                    inflateContent(modelNewWorkOrder);

                } else {
                    try {
                        SmartToast.showInfo(response.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
    private void inflateContent(final ModelNewWorkOrder modelNewWorkOrder) {
        if (modelNewWorkOrder != null) {
            this.mNewWorkOrder = modelNewWorkOrder;
            //维修人员信息
            if (modelNewWorkOrder.getWork_user() != null && modelNewWorkOrder.getWork_user().size() > 0) {
                mlinear_repair_person.removeAllViews();
                for (int i = 0; i < modelNewWorkOrder.getWork_user().size(); i++) {
                    View view = LayoutInflater.from(this).inflate(R.layout.activity_workorder_detail_item_view, null);
                    SimpleDraweeView iv_repair_head = view.findViewById(R.id.iv_repair_head);
                    TextView tv_repair_person = view.findViewById(R.id.tv_repair_person);
                    TextView tv_repair = view.findViewById(R.id.tv_repair);
                    TextView tv_repair_time = view.findViewById(R.id.tv_repair_time);
                    ImageView iv_call = view.findViewById(R.id.iv_call);

                    FrescoUtils.getInstance().setImageUri(iv_repair_head, ApiHttpClient.IMG_URL + modelNewWorkOrder.getWork_user().get(i).getHead_img());
                    tv_repair_person.setText(modelNewWorkOrder.getWork_user().get(i).getName());
                    tv_repair.setText(modelNewWorkOrder.getWork_user().get(i).getAttribute());
                    tv_repair_time.setText(StringUtils.getDateToString(modelNewWorkOrder.getWork_user().get(i).getAcceptime(), "1"));

                    final int finalI = i;
                    iv_call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new CommomDialog(mContext, R.style.my_dialog_DimEnabled, "确认拨打电话给师傅？", new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    if (confirm) {
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_DIAL);
                                        intent.setData(Uri.parse("tel:"
                                                + modelNewWorkOrder.getWork_user().get(finalI).getPhone()));
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                }
                            }).show();
                        }
                    });
                    mlinear_repair_person.addView(view);
                }
            }
            //工单信息及下单人信息
            tv_bianhao.setText(modelNewWorkOrder.getOrder_number());
            if (!NullUtil.isStringEmpty(modelNewWorkOrder.getAppointime())) {
                tv_repair_date.setText(modelNewWorkOrder.getAppointime());
            } else {
                tv_repair_date.setText("--");
            }
            tv_baoxiu_type.setText(modelNewWorkOrder.getCate_pid_cn());
            if (modelNewWorkOrder.getDegree().equals("1")) {
                tv_jinji.setText("紧急");
            } else {
                tv_jinji.setText("普通");
            }
            if (!NullUtil.isStringEmpty(modelNewWorkOrder.getContent())) {
                tv_baoxiu_content.setText(modelNewWorkOrder.getContent());
            } else {
                tv_baoxiu_content.setText("--");
            }
            tv_user_name.setText(modelNewWorkOrder.getNickname());
            tv_user_address.setText(modelNewWorkOrder.getAddress());
            tv_user_photo.setText(modelNewWorkOrder.getUsername());
            //故障照片
            if (modelNewWorkOrder.getImg_list() != null && modelNewWorkOrder.getImg_list().size() > 0) {
                tv_none.setVisibility(View.GONE);
                mlinear_repair_photo.setVisibility(View.VISIBLE);

                mlinear_repair_photo.removeAllViews();
                for (int i = 0; i < modelNewWorkOrder.getImg_list().size(); i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,
                            100);//两个50分别为添加图片的大小
                    // imageView.setImageResource(R.drawable.ic_launcher);
                    GlideUtils.getInstance().glideLoad(WorkOrderDetailActivity.this, ApiHttpClient.IMG_URL + modelNewWorkOrder.getImg_list().get(i).getImg_path()
                            + modelNewWorkOrder.getImg_list().get(i).getImg_name(), imageView, R.drawable.ic_default_head);
                    params.setMargins(0, 0, 20, 0);
                    imageView.setLayoutParams(params);
                    //点击图片放大
                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //点击图片
                            ArrayList<String> imgs = new ArrayList<>();
                            for (int i = 0; i < modelNewWorkOrder.getImg_list().size(); i++) {
                                //只要localpath不为空则说明是刚选上的
                                imgs.add(ApiHttpClient.IMG_URL + modelNewWorkOrder.getImg_list().get(i).getImg_path()
                                        + modelNewWorkOrder.getImg_list().get(i).getImg_name());

                            }
                            Intent intent = new Intent(WorkOrderDetailActivity.this, PhotoViewPagerAcitivity.class);
                            intent.putExtra("img_list", imgs);
                            intent.putExtra("position", finalI);
                            intent.putExtra("isShowDelete", false);
                            startActivity(intent);
                        }
                    });
                    mlinear_repair_photo.addView(imageView);
                }
            } else {
                tv_none.setVisibility(View.VISIBLE);
                mlinear_repair_photo.setVisibility(View.GONE);
            }

            //报修流程
            if (modelNewWorkOrder.getWork_log() != null && modelNewWorkOrder.getWork_log().size() > 0) {
                mlistLog.clear();
                mlistLog.addAll(modelNewWorkOrder.getWork_log());
            }
            //工单状态（1待派单，2已派单,待接单，3未增派,待服务，4待增派，5已增派,待接单，6已增派,待服务，7服务中，8待付款，9已完成，10已取消）
            if (work_status.equals("7")) {//服务中
                ly_btn.setVisibility(View.GONE);
            } else if (work_status.equals("8")) {//待支付
                ly_btn.setVisibility(View.VISIBLE);
                tv_btn.setText("支付");
            } else if (work_status.equals("9")) {//已完成
                if (modelNewWorkOrder.getEvaluate_status().equals("0")) {//未评价
                    ly_btn.setVisibility(View.VISIBLE);
                    tv_btn.setText("评价");
                } else {
                    ly_btn.setVisibility(View.GONE);
                }
            } else if (work_status.equals("10")) {//已取消

                ly_btn.setVisibility(View.GONE);

            } else {//待服务
                ly_btn.setVisibility(View.VISIBLE);
                tv_btn.setText("取消工单");
            }

        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_workorder_detail;
    }

    @Override
    protected void initIntentData() {
        work_id = getIntent().getStringExtra("id");
        work_status = getIntent().getStringExtra("work_status");

    }

    @Override
    protected int getFragmentCotainerId() {
        return 0;
    }

    @Override
    protected void initFragment() {

    }

    /**
     * 回调师傅上传的图片
     *
     * @param mListimg
     */
    @Override
    public void lickImg(List<ModelNewWorkOrder.ImgListBean> mListimg) {
        if (mListimg != null && mListimg.size() > 0) {
            //点击图片
            ArrayList<String> imgs = new ArrayList<>();
            for (int i = 0; i < mListimg.size(); i++) {
                imgs.add(ApiHttpClient.IMG_URL + mListimg.get(i).getImg_path()
                        + mListimg.get(i).getImg_name());

            }
            Intent intent = new Intent(WorkOrderDetailActivity.this, PhotoViewPagerAcitivity.class);
            intent.putExtra("img_list", imgs);
            intent.putExtra("position", i);
            intent.putExtra("isShowDelete", false);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_btn://取消 付款 评价
                if (work_status.equals("8")) {//待支付
                    Intent intent = new Intent(WorkOrderDetailActivity.this, ZhifuActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("o_id", work_id);
                    bundle.putString("price", "");
                    bundle.putString("type", "workorder_pay");
                    bundle.putString("order_type", "wo");
                    startActivity(intent);
                } else if (work_status.equals("9")) {//已完成
                    if (mNewWorkOrder.getEvaluate_status().equals("0")) {//未评价
                        Intent intent = new Intent(WorkOrderDetailActivity.this, WorkOrderPingjiaActivity.class);
                        intent.putExtra("work_id", work_id);
                        startActivityForResult(intent, 22);
                    }
                } else {//待服务
                    Intent intent = new Intent(WorkOrderDetailActivity.this, WorkOrderCancelActivity.class);
                    intent.putExtra("work_id", work_id);
                    startActivityForResult(intent, 11);
                }

                break;
            case R.id.linear_visibility://缩放按钮
                if (linear_other_info.getVisibility() == View.VISIBLE) {
                    iv_up.setBackgroundResource(R.mipmap.icon_workorder_detail_down);
                    tv_up_name.setText("展开");

                } else {
                    iv_up.setBackgroundResource(R.mipmap.icon_workorder_detail_up);
                    tv_up_name.setText("收起");
                }
                HiddenAnimUtils.newInstance(WorkOrderDetailActivity.this, linear_other_info, linear_visibility, height ).toggle();

                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            finish();
        }
    }

}
