package com.huacheng.huiservers.ui.circle.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.circle.bean.CircleDetailBean;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;

import java.util.List;

/**
 * 类：
 * 时间：2018/3/16 17:31
 * 功能描述:Huiservers
 */
public class CircleDetailListAdapter extends BaseAdapter {
    private Context mContext;
    private List<CircleDetailBean> mList;

    public CircleDetailListAdapter(Context mContext, List<CircleDetailBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cricle_detail_list_item, null, false);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_time = convertView.findViewById(R.id.tv_time);
            holder.tv_content = convertView.findViewById(R.id.tv_content);
            holder.iv_photo = convertView.findViewById(R.id.iv_photo);
            holder.iv_delete = convertView.findViewById(R.id.iv_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FrescoUtils.getInstance().setImageUri(holder.iv_photo, com.huacheng.huiservers.utils.StringUtils.getImgUrl(mList.get(position).getR_avatars()));
        holder.tv_name.setText(mList.get(position).getR_nickname());
        holder.tv_time.setText(mList.get(position).getAddtime());
        byte[] bytes = Base64.decode(mList.get(position).getContent(), Base64.DEFAULT);
        holder.tv_content.setText(new String(bytes));

        SharedPreferences pref = mContext.getSharedPreferences("login", 0);
        String uid = pref.getString("login_uid", "");
        if (uid.equals(mList.get(position).getUid())) {
            holder.iv_delete.setVisibility(View.VISIBLE);
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnItemDeleteListener.onDeleteClick(position);
                }
            });
           /* RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.tv_time.getLayoutParams();
            lp.setMargins(0, 0, 0, 0);
            holder.tv_time.setLayoutParams(lp);*/
        } else {
            holder.iv_delete.setVisibility(View.GONE);
           /* RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.tv_time.getLayoutParams();
            lp.setMargins(0, 0, 50, 0);
            holder.tv_time.setLayoutParams(lp);*/
        }

        return convertView;
    }


    //删除接口
    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setmOnItemDeleteListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

    private class ViewHolder {
        SimpleDraweeView iv_photo;
        TextView tv_name;
        TextView tv_time;
        TextView tv_content;
        ImageView iv_delete;


    }
}
