package com.huacheng.huiservers.ui.servicenew1.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.ui.servicenew.model.ModelOrderList;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 2.0服务 订单列表适配器
 * created by wangxiaotao
 * 2020/6/17 0017 16:08
 */
public class FragmentOrderAdapterNew extends CommonAdapter<ModelOrderList> {
    private int  type;
    public FragmentOrderAdapterNew(Context context, int layoutId, List<ModelOrderList> datas,int type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelOrderList item, int position) {

      viewHolder.<TextView>getView(R.id.tv_store_name).setText(item.getI_name());
        FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_one), ApiHttpClient.IMG_URL+item.getTitle_img());
        viewHolder.<TextView>getView(R.id.tv_title_one).setText(item.getS_name()+"");
        viewHolder.<TextView>getView(R.id.tv_sub_title_one).setText(item.getAddress()+"");
        viewHolder.<TextView>getView(R.id.txt_num).setText("共"+item.getNumber()+"件");
        viewHolder.<TextView>getView(R.id.txt_rice).setText("¥"+item.getAmount());

        String status = item.getStatus();
        int status_int = Integer.parseInt(status);
        if (status_int==2){
            //待派单
            viewHolder.<TextView>getView(R.id.tv_status).setText("待派单");
            viewHolder.<TextView>getView(R.id.tv_bottom_text).setVisibility(View.GONE);
            viewHolder.<ImageView>getView(R.id.iv_delete).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setText("申请退款");
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //申请退款

                }
            });
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.GONE);
        }else if (status_int==3){
            //已派单
            viewHolder.<TextView>getView(R.id.tv_status).setText("已派单");
            viewHolder.<ImageView>getView(R.id.iv_delete).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_bottom_text).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setText("申请退款");
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //申请退款
                }
            });
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_2).setText("完成服务");
            viewHolder.<TextView>getView(R.id.tv_btn_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //完成服务
                }
            });
        }else if (status_int==4){
            //已上门
            viewHolder.<TextView>getView(R.id.tv_status).setText("已上门");
            viewHolder.<ImageView>getView(R.id.iv_delete).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_bottom_text).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_bottom_text).setText("师傅已确认完成服务");
            viewHolder.<TextView>getView(R.id.tv_btn_1).setText("申请退款");
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //申请退款
                }
            });
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_btn_2).setText("完成服务");
            viewHolder.<TextView>getView(R.id.tv_btn_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //完成服务
                }
            });
        }else if (status_int==5){
            //待评价
            viewHolder.<TextView>getView(R.id.tv_status).setText("已派单");
            viewHolder.<ImageView>getView(R.id.iv_delete).setVisibility(View.VISIBLE);
            viewHolder.<ImageView>getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除
                }
            });
            viewHolder.<TextView>getView(R.id.tv_bottom_text).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setText("再次购买");
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //再次购买
                }
            });
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_btn_2).setText("立即评价");
            viewHolder.<TextView>getView(R.id.tv_btn_2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //立即评价
                }
            });
        }else if (status_int==6){
            //已完成
            viewHolder.<TextView>getView(R.id.tv_status).setText("已完成");
            viewHolder.<ImageView>getView(R.id.iv_delete).setVisibility(View.VISIBLE);
            viewHolder.<ImageView>getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除
                }
            });
            viewHolder.<TextView>getView(R.id.tv_bottom_text).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setText("再次购买");
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //再次购买
                }
            });
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.GONE);

        }else if (status_int==7){
            //退款审核中
            viewHolder.<TextView>getView(R.id.tv_status).setText("退款审核中");
            viewHolder.<ImageView>getView(R.id.iv_delete).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_bottom_text).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.GONE);
        }else if (status_int==8){
            //退款审核中
            viewHolder.<TextView>getView(R.id.tv_status).setText("退款中");
            viewHolder.<ImageView>getView(R.id.iv_delete).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_bottom_text).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.GONE);
        }else if (status_int==9){
            //审核失败
            viewHolder.<TextView>getView(R.id.tv_status).setText("审核失败");
            viewHolder.<ImageView>getView(R.id.iv_delete).setVisibility(View.VISIBLE);
            viewHolder.<ImageView>getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除
                }
            });
            viewHolder.<TextView>getView(R.id.tv_bottom_text).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.GONE);
        }else if (status_int==10){
            //退款成功
            viewHolder.<TextView>getView(R.id.tv_status).setText("退款成功");
            viewHolder.<ImageView>getView(R.id.iv_delete).setVisibility(View.VISIBLE);
            viewHolder.<ImageView>getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除
                }
            });
            viewHolder.<TextView>getView(R.id.tv_bottom_text).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.GONE);
        }else if (status_int==11){
            //退款失败
            viewHolder.<TextView>getView(R.id.tv_status).setText("退款失败");
            viewHolder.<ImageView>getView(R.id.iv_delete).setVisibility(View.VISIBLE);
            viewHolder.<ImageView>getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除
                }
            });
            viewHolder.<TextView>getView(R.id.tv_bottom_text).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_1).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.tv_btn_2).setVisibility(View.GONE);
        }
    }
}
