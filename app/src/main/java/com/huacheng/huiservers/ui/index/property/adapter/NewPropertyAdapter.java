package com.huacheng.huiservers.ui.index.property.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.center.bean.HouseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：
 * 时间：2018/8/4 10:48
 * created by DFF
 */
public class NewPropertyAdapter extends BaseAdapter {
    private OnDeleteClickListener listener;
    private Context mContext;
    List<HouseBean> mdatas;

    public NewPropertyAdapter(Context mContext, List<HouseBean> mdatas, OnDeleteClickListener listener) {
        this.mContext = mContext;
        this.mdatas = mdatas;
        this.listener = listener;

    }

    public interface OnDeleteClickListener {
        void onDeleteClick(HouseBean item);
    }

    @Override
    public int getCount() {
        return mdatas.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.new_property_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvName.setText(mdatas.get(position).getCommunity_name());
        holder.mTvAddress.setText("地址：" + mdatas.get(position).getAddress());
        holder.mTvDetete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteClick(mdatas.get(position));
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_detete)
        TextView mTvDetete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
