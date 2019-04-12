package com.huacheng.huiservers.ui.index.houserent.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.CommomDialog;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.HouseListBean;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 我的房产租售房列表 适配器
 * created by badge
 * 2018/11/10  下午 18:34
 */
public class MyHouseRentListAdapter extends CommonAdapter<HouseListBean> {

    private String room = "";
    private String office = "";
    private String kitchen = "";
    private String guard = "";

    private String area = "";
    private String floor_hfloor = "";
    private String unitPrice = "";
    private String totalPrice = "";

    private Activity mActivity;
    private int mType = 0;

    public MyHouseRentListAdapter(Activity activity, Context context, int layoutId, int type, List datas) {
        super(context, layoutId, datas);
        this.mActivity = activity;
        this.mContext = context;
        this.mType = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, HouseListBean item, int position) {
        viewHolder.getView(R.id.sdv_head).setVisibility(View.VISIBLE);

        SimpleDraweeView sdv_head = viewHolder.getView(R.id.sdv_head);
        TextView tv_title = viewHolder.getView(R.id.tv_title);

        ImageView iv_broker = viewHolder.getView(R.id.iv_broker);
        TextView tv_broker_name = viewHolder.getView(R.id.tv_broker_name);
        ImageView iv_mobile = viewHolder.getView(R.id.iv_mobile);

        if (!StringUtils.isEmpty(item.getHead_img())) {
            GlideUtils.getInstance()
                    .glideLoad(mContext, ApiHttpClient.IMG_URL + item.getHead_img(), sdv_head, R.drawable.ic_default_rectange);
        } else {
            GlideUtils.getInstance()
                    .glideLoad(mContext, "", sdv_head, R.drawable.ic_default_rectange);
        }

        TextView tv_price = viewHolder.getView(R.id.tv_price);
        ImageView iv_rentss = viewHolder.getView(R.id.iv_rentss);
        TextView tv_rents_status = viewHolder.getView(R.id.tv_rents_status);

        String communityName = item.getCommunity_name();
        room = item.getRoom();
        if (!NullUtil.isStringEmpty(room)) {
            room = room + "室";
        }
        office = item.getOffice();
        if (!NullUtil.isStringEmpty(office)) {
            office = office + "厅";
        }
        kitchen = item.getKitchen();
        if (!NullUtil.isStringEmpty(kitchen)) {
            kitchen = kitchen + "厨";
        }
        guard = item.getGuard();
        if (!NullUtil.isStringEmpty(guard)) {
            guard = guard + "卫";
        }
        area = item.getArea();
        if (!NullUtil.isStringEmpty(area)) {
            area = "面积" + area + "平米";
        }

        String houseFloor = item.getHouse_floor();//当前层数
        String floor = item.getFloor();//总层数

        if (!NullUtil.isStringEmpty(houseFloor) && !NullUtil.isStringEmpty(floor)) {
            floor_hfloor = " | " + houseFloor + "/" + floor + "层";
        }

        String status = item.getStatus();

        tv_title.setText(communityName + "-" + room + office + kitchen + guard + "-" + area + floor_hfloor + "");

        if (mType == 0) {
            totalPrice = item.getTotal_price(); //总价 0售房
            if (!NullUtil.isStringEmpty(totalPrice)) {
                tv_price.setText(totalPrice + "元");
            } else {
                tv_price.setText("");
            }

            //房产状态 1为未审核 2为未售 3为已售
            int mResource = 0;
            if ("1".equals(status)) {
                status = "未审核";
                mResource = R.mipmap.ic_h_no_review;
                tv_rents_status.setTextColor(mContext.getResources().getColor(R.color.red_ff));
            } else if ("2".equals(status)) {
                status = "未售";
                mResource = R.mipmap.ic_h_no_sale;
                tv_rents_status.setTextColor(mContext.getResources().getColor(R.color.red_ff));
            } else if ("3".equals(status)) {
                status = "已售";
                mResource = R.mipmap.ic_h_sold;
                tv_rents_status.setTextColor(Color.parseColor("#18B632"));
            }
            GlideUtils.getInstance().glideLoad(mContext, "", iv_rentss, mResource);
            tv_rents_status.setText(status);


        } else if (mType == 1) {

            unitPrice = item.getUnit_price(); //单价 单元 元/月 1租房
            if (!NullUtil.isStringEmpty(unitPrice)) {
                tv_price.setText(unitPrice + "元/月");
            } else {
                tv_price.setText("");
            }

            //房产状态 1为未审核 2为未出租 3为已出租
            int mResource = 0;
            if ("1".equals(status)) {
                status = "未审核";
                mResource = R.mipmap.ic_h_no_review;
                tv_rents_status.setTextColor(mContext.getResources().getColor(R.color.red_ff));
            } else if ("2".equals(status)) {
                status = "未出租";
                mResource = R.mipmap.ic_h_no_sale;
                tv_rents_status.setTextColor(mContext.getResources().getColor(R.color.red_ff));
            } else if ("3".equals(status)) {
                status = "已出租";
                mResource = R.mipmap.ic_h_sold;
                tv_rents_status.setTextColor(Color.parseColor("#18B632"));
            }
            GlideUtils.getInstance().glideLoad(mContext, "", iv_rentss, mResource);
            tv_rents_status.setText(status);
        }

        String adminImg = item.getAdministrator_img();
        if (!StringUtils.isEmpty(adminImg)) {
            iv_broker.setVisibility(View.VISIBLE);
            GlideUtils.getInstance()
                    .glideLoad(mContext, ApiHttpClient.IMG_URL + adminImg, iv_broker, 0);
        } else {
            iv_broker.setVisibility(View.GONE);
        }

        String broker = item.getName();//经纪人
        if (!StringUtils.isEmpty(broker)) {
            tv_broker_name.setText("经纪人：" + broker);
        } else {
            tv_broker_name.setText("");
        }

        final String mobile = item.getPhone();
        if (!NullUtil.isStringEmpty(mobile)) {
            iv_mobile.setVisibility(View.VISIBLE);
        } else {
            iv_mobile.setVisibility(View.GONE);
        }
        iv_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommomDialog(mActivity, R.style.my_dialog_DimEnabled, "确认拨打电话？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"
                                    + mobile.trim()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mActivity.startActivity(intent);

                            dialog.dismiss();
                        }
                    }
                }).show();
            }
        });

    }
}
