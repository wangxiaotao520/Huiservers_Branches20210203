package com.huacheng.huiservers.ui.servicenew.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceItem;
import com.huacheng.huiservers.view.MyImageSpan;
import com.huacheng.libraryservice.utils.DeviceUtils;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;

import java.util.List;

/**
 * Description: 服务列表
 * created by wangxiaotao
 * 2018/9/4 0004 上午 11:40
 */
public class ServiceFragmentAdapter <T>extends BaseAdapter {
    private List<T> mDatas;

    public ServiceFragmentAdapter(List<T> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    private Context mContext;


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_service_item, null);
            viewHolder=new ViewHolder();
            viewHolder.sdv_pic=convertView.findViewById(R.id.sdv_pic);
            viewHolder.tv_service_name=convertView.findViewById(R.id.tv_service_name);
            viewHolder.tv_price=convertView.findViewById(R.id.tv_price);
            viewHolder.tv_tag_kangyang=convertView.findViewById(R.id.tv_tag_kangyang);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ModelServiceItem item = (ModelServiceItem) getItem(position);
        FrescoUtils.getInstance().setImageUri(viewHolder.sdv_pic, ApiHttpClient.IMG_SERVICE_URL+item.getTitle_img());
       // viewHolder.tv_service_name.setText(item.getTitle()+"");
        //TODO vip标签
        String title =item.getTitle()+"";
        String addSpan = "VIP折扣";
        SpannableString spannableString=new SpannableString(addSpan+" "+title);
        Drawable d = mContext.getResources().getDrawable(R.mipmap.ic_vip_span);
        d.setBounds(0, 0, DeviceUtils.dip2px(mContext,50), DeviceUtils.dip2px(mContext,16));
        ImageSpan span = new MyImageSpan(mContext,d);
        spannableString.setSpan(span, 0, addSpan.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.tv_service_name.setText(spannableString);


        viewHolder.tv_price.setText("¥ "+item.getPrice());
        if ("2".equals(item.getPension_display())){
            viewHolder.tv_tag_kangyang.setVisibility(View.VISIBLE);
        }else {
            viewHolder.tv_tag_kangyang.setVisibility(View.GONE);
        }
        return convertView;
    }
    static class ViewHolder{
        SimpleDraweeView sdv_pic;
        TextView tv_service_name;
        TextView tv_price;
        TextView tv_tag_kangyang;
    }

}

