package com.huacheng.huiservers.ui.index.property.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.huacheng.huiservers.BaseApplication;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ChargeRecord;
import com.huacheng.huiservers.ui.webview.WebViewDefaultActivity;
import com.huacheng.huiservers.utils.SharePrefrenceUtil;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.NullUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.huacheng.huiservers.ui.webview.ConstantWebView.CONSTANT_BILL;

/**
 * 类描述：缴费记录Adapter
 * 时间：2018/8/4 10:48
 * created by DFF
 */
public class PropertyPaymentAdapter extends BaseAdapter {

    private Context mContext;
    List<ChargeRecord.DataBean> mdatas;
    String userName,roomName;
    public PropertyPaymentAdapter(Context mContext, List<ChargeRecord.DataBean> mdatas) {
        this.mContext = mContext;
        this.mdatas = mdatas;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public int getCount() {
        return mdatas.size();
    }

    @Override
    public ChargeRecord.DataBean getItem(int position) {
        return mdatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.property_payment_record_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ChargeRecord.DataBean info = getItem(position);
        holder.mTvTime.setText(StringUtils.getDateToString(info.getPay_time(), "1"));

        holder.mTvName.setText(info.getPayer());
        holder.mTvAddress.setText(info.getRoomInfo());

        if (info.getVoidX().equals("0")) {
            if (info.getRefund().equals("0")) {
                holder.tvOrderType.setText("缴费");
            } else holder.tvOrderType.setText("退款");
        } else holder.tvOrderType.setText("已作废");

        holder.mTvOrderNumber.setText("票据号：    " + info.getOrder_number());
        if (info.getRefund().equals("1")) {
            holder.mTvRefundTot.setText(info.getMergeMoney() + "元");
            holder.refundV.setVisibility(View.VISIBLE);
            holder.chargeV.setVisibility(View.GONE);
        } else {
            holder.chargeV.setVisibility(View.VISIBLE);
            holder.mTvChargePrice.setText(info.getMergeMoney() + "元");
            if (Float.valueOf(info.getRefund_money()) ==0) {
                holder.refundV.setVisibility(View.GONE);
            }else {
                holder.refundV.setVisibility(View.VISIBLE);
                holder.mTvRefundTot.setText(info.getRefund_money()+ "元");
            }
        }


        PropertyPaymentAdapterAdapter wyInfoAdapter = new PropertyPaymentAdapterAdapter(mContext, mdatas.get(position).getOrderList(), false);
        holder.mList.setAdapter(wyInfoAdapter);
        holder.tvCheckBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printBill(info.getId());
            }
        });

        return convertView;
    }

    public void printBill(String id) {
        String url = ApiHttpClient.API_URL +ApiHttpClient.API_VERSION +"property/printInfo";
        if (ApiHttpClient.TOKEN != null && ApiHttpClient.TOKEN_SECRET != null) {
            url +=   "?token=" + ApiHttpClient.TOKEN + "&tokenSecret=" + ApiHttpClient.TOKEN_SECRET;
        }
        //  拼接小区id
        SharePrefrenceUtil sharePrefrenceUtil = new SharePrefrenceUtil(BaseApplication.getContext());
        String xiaoQuId = sharePrefrenceUtil.getXiaoQuId();
        if (!NullUtil.isStringEmpty(xiaoQuId)) {
            url  += "&hui_community_id=" + xiaoQuId;
        }
        url += "&id=" + id;
        Intent it = new Intent();
        it.setClass(mContext, WebViewDefaultActivity.class);
        it.putExtra("web_type", 0);
        it.putExtra("jump_type",CONSTANT_BILL);
        it.putExtra("url", url);
        mContext.startActivity(it);


    }

    static class ViewHolder {
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_order_number)
        TextView mTvOrderNumber;
        @BindView(R.id.order_type)
        TextView tvOrderType;
        @BindView(R.id.list)
        ListView mList;
        @BindView(R.id.tv_charge_price)
        TextView mTvChargePrice;
        @BindView(R.id.charev)
        View chargeV;
        @BindView(R.id.tv_refund_tot)
        TextView mTvRefundTot;
        @BindView(R.id.refundv)
        View refundV;
        @BindView(R.id.check_bill)
        TextView tvCheckBill;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
