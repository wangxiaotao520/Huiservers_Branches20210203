package com.huacheng.huiservers.ui.index.property.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
            final ImageView iv_check = v.findViewById(R.id.iv_check);
            if (wyListData.get(position).get(i).isChecked()){
                iv_check.setBackgroundResource(R.drawable.icon_shop_onclick);
            }else {
                iv_check.setBackgroundResource(R.drawable.icon_shop_no);
            }
            if (is_JF){
                //缴费页面 PropertyHomeListActivity  PropertyHomeNewJFActivity
                iv_check.setVisibility(View.VISIBLE);
                final int finalI = i;
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener!=null){
                            listener.onClickChildItem(position, finalI);
                        }
//                        if (wyListData.get(position).get(finalI).isChecked()){//反选
//                            wyListData.get(position).get(finalI).setChecked(false);
//                            iv_check.setBackgroundResource(R.drawable.icon_shop_no);
//                        }else {
//                            wyListData.get(position).get(finalI).setChecked(true);
//                            iv_check.setBackgroundResource(R.drawable.icon_shop_onclick);
//                        }
                    }
                });
            }else {
                iv_check.setVisibility(View.GONE);
            }
            if (!wyListData.get(position).get(i).getStartdate().equals("0") && !TextUtils.isEmpty(wyListData.get(position).get(i).getStartdate())) {
                tv_timeInterval.setText(StringUtils.getDateToString(wyListData.get(position).get(i).getStartdate(), "2") + "/" +
                        StringUtils.getDateToString(wyListData.get(position).get(i).getEnddate(), "2"));
            } else {
                tv_timeInterval.setText(StringUtils.getDateToString(wyListData.get(position).get(i).getBill_time(), "2"));
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

}
