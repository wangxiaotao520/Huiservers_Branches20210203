package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huacheng.huiservers.Jump;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.utils.ToolUtils;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.glide.GlideUtils;

import java.util.List;

/**
 * 类：
 * 时间：2018/5/24 09:22
 * 功能描述:Huiservers
 */
public class HomeCenterGirdAdapter extends BaseAdapter {
    Context mContext;
    List<BannerBean> list;
    Jump jump;

    public HomeCenterGirdAdapter(Context context, List<BannerBean> list) {

        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoldler holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_center_grid_item_img, null);
            holder = new ViewHoldler(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoldler) convertView.getTag();
        }

        //获取图片的宽高--start
        holder.ll_grid_center.getLayoutParams().width = (ToolUtils.getScreenWidth(mContext)- DeviceUtils.dip2px(mContext,30))/2;
        Double d = Double.valueOf(holder.ll_grid_center.getLayoutParams().width) / 1.5;
        holder.ll_grid_center.getLayoutParams().height = (new Double(d)).intValue();
        GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL + list.get(position).getImg()
                , holder.iv_banner, R.drawable.ic_default_15);

        holder.iv_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(list.get(position).getUrl())) {
                    if (list.get(position).getUrl_type().equals("0") || TextUtils.isEmpty(list.get(position).getUrl_type())) {
                        jump = new Jump(mContext, list.get(position).getType_name(), list.get(position).getAdv_inside_url());
                    } else {
                        jump = new Jump(mContext, list.get(position).getUrl_type(), list.get(position).getType_name(), "", list.get(position).getUrl_type_cn());
                    }
                } else {//URL不为空时外链
                    jump = new Jump(mContext, list.get(position).getUrl());

                }
            }
        });
        return convertView;
    }

    class ViewHoldler {
        LinearLayout ll_grid_center;
        ImageView iv_banner;

        public ViewHoldler(View v) {
            ll_grid_center = (LinearLayout) v.findViewById(R.id.ll_grid_center);
            iv_banner = (ImageView) v.findViewById(R.id.iv_banner);

        }
    }
}
