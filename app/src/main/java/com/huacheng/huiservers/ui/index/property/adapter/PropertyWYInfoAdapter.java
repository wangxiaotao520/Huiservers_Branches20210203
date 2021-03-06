package com.huacheng.huiservers.ui.index.property.adapter;

import android.content.Context;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：
 * 时间：2018/8/11
 * created by DFF
 */
public class PropertyWYInfoAdapter extends BaseAdapter {
    private Context mContext;
    List<List<ModelWuye>> wyListData;
    private  boolean is_JF;//是否是缴费页 标示是否可选

    private OnCheckJFListener listener;//点击选择item的回调

    private String selected_invoice_type = "";//选中的账单类型 如果该参数为0，能多选账单，且只能选该参数为0的账单，如果该参数为1，只能单选，不可选其他任何账单)
    private String selected_bill_id = ""; //选中的账单id 且只有在 selected_invoice_type=“1”时有值 只能选择它


    public PropertyWYInfoAdapter(Context mContext, List<List<ModelWuye>> wyListData,boolean is_JF) {
        this.mContext = mContext;
        this.wyListData = wyListData;
        this.is_JF=is_JF;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.property_homelist_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mLinView.removeAllViews();
        for (int i = 0; i < wyListData.get(position).size(); i++) {

            holder.mTvTagName.setText(wyListData.get(position).get(i).getCharge_type());
            View v = LayoutInflater.from(mContext).inflate(R.layout.include_property_linear, null);
            TextView tv_timeInterval = v.findViewById(R.id.tv_timeInterval);
            TextView tv_timePrice = v.findViewById(R.id.tv_timePrice);


            if (!wyListData.get(position).get(i).getStartdate().equals("0") && !TextUtils.isEmpty(wyListData.get(position).get(i).getStartdate())) {
                tv_timeInterval.setText(StringUtils.getDateToString(wyListData.get(position).get(i).getStartdate(), "8") + " - " +
                        StringUtils.getDateToString(wyListData.get(position).get(i).getEnddate(), "8"));
            } else {
                tv_timeInterval.setText(StringUtils.getDateToString(wyListData.get(position).get(i).getBill_time(), "8"));
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
        @BindView(R.id.view_hine)
        View mViewHine;


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
