package com.huacheng.huiservers.ui.center.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.center.bean.CouponBean;
import com.huacheng.huiservers.utils.StringUtils;
import com.lidroid.xutils.BitmapUtils;

public class CouponToShopUseAdapter extends BaseAdapter {
    CouponBean mBean;
    private Context context;
    BitmapUtils bitmapUtils;

    public CouponToShopUseAdapter(CouponBean bean, Context context) {
        this.mBean = bean;
        this.context = context;
        bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return 1;//beans.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.coupon_toshopuse_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String condition = mBean.getCondition();
        String userule = mBean.getUserule();
        if (!StringUtils.isEmpty(condition)) {
            holder.tv_1.setVisibility(View.VISIBLE);
            holder.tv_1.setText(condition);
        } else {
            holder.tv_1.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(userule)) {
            holder.tv_2.setVisibility(View.VISIBLE);
            holder.tv_2.setText(userule);
        } else {
            holder.tv_2.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_toshopuse_title, tv_1, tv_2, tv_divider_line;

        public ViewHolder(View v) {
            tv_toshopuse_title = (TextView) v.findViewById(R.id.tv_toshopuse_title);
            tv_1 = (TextView) v.findViewById(R.id.tv_1);
            tv_2 = (TextView) v.findViewById(R.id.tv_2);
            tv_divider_line = (TextView) v.findViewById(R.id.tv_divider_line);
        }

    }

}
