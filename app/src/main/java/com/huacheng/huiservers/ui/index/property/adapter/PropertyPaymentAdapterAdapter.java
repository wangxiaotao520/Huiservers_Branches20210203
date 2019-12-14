package com.huacheng.huiservers.ui.index.property.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.index.property.bean.ModelWuye;
import com.huacheng.huiservers.ui.index.property.inter.OnCheckJFListener;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.NullUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 缴费记录列表二级
 * created by wangxiaotao
 * 2019/12/13 0013 下午 5:51
 */
public class PropertyPaymentAdapterAdapter  extends BaseAdapter {
    private Context mContext;
    List<List<ModelWuye>> wyListData;

    private OnCheckJFListener listener;//点击选择item的回调

    private String selected_invoice_type = "";//选中的账单类型 如果该参数为0，能多选账单，且只能选该参数为0的账单，如果该参数为1，只能单选，不可选其他任何账单)
    private String selected_bill_id = ""; //选中的账单id 且只有在 selected_invoice_type=“1”时有值 只能选择它


    public PropertyPaymentAdapterAdapter(Context mContext, List<List<ModelWuye>> wyListData,boolean is_JF) {
        this.mContext = mContext;
        this.wyListData = wyListData;
    }

    @Override
    public int getCount() {
        return wyListData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_item_property_payment_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mLinView.removeAllViews();
        for (int i = 0; i < wyListData.get(position).size(); i++) {

            holder.mTvTagName.setText(wyListData.get(position).get(i).getCharge_type());
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_item_item_property_paymentlist, null);
            TextView tv_timeInterval = v.findViewById(R.id.tv_timeInterval);
            TextView tv_timePrice = v.findViewById(R.id.tv_timePrice);
            TextView tv_refund = v.findViewById(R.id.tv_refund);

            if (!wyListData.get(position).get(i).getStartdate().equals("0") && !TextUtils.isEmpty(wyListData.get(position).get(i).getStartdate())) {
                tv_timeInterval.setText(StringUtils.getDateToString(wyListData.get(position).get(i).getStartdate(), "8") + " — " +
                        StringUtils.getDateToString(wyListData.get(position).get(i).getEnddate(), "8"));
            } else {
                tv_timeInterval.setText(StringUtils.getDateToString(wyListData.get(position).get(i).getBill_time(), "8"));
            }
            //判断是否是退款
            if (!NullUtil.isStringEmpty(wyListData.get(position).get(i).getRefund())&&!"0".equals(wyListData.get(position).get(i).getRefund())&&!"0.00".equals(wyListData.get(position).get(i).getRefund())&&!"0.0".equals(wyListData.get(position).get(i).getRefund())){
                tv_refund.setVisibility(View.VISIBLE);
                tv_refund.setText("已退款");
                tv_refund.setTextColor(Color.parseColor("#E0473F"));
            }else {
                tv_refund.setVisibility(View.VISIBLE);
                tv_refund.setText("—");
                tv_refund.setTextColor(Color.parseColor("#666666"));
            }
            tv_timePrice.setText(wyListData.get(position).get(i).getSumvalue() + "元");
            holder.mLinView.addView(v);

        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_tag_name)
        TextView mTvTagName;
        @BindView(R.id.lin_view)
        LinearLayout mLinView;



        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    public void setListener(OnCheckJFListener listener) {
        this.listener = listener;
    }

    public String getSelected_invoice_type() {
        return selected_invoice_type;
    }

    public void setSelected_invoice_type(String selected_invoice_type) {
        this.selected_invoice_type = selected_invoice_type;
    }

    public String getSelected_bill_id() {
        return selected_bill_id;
    }

    public void setSelected_bill_id(String selected_bill_id) {
        this.selected_bill_id = selected_bill_id;
    }

}

