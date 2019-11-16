package com.huacheng.huiservers.ui.center.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.center.bean.ModelAddressList;

import java.util.List;

/**
 * 类：
 * 时间：2017/10/14 15:48
 * 功能描述:Huiservers
 */
public class NewAddressAdapter extends BaseAdapter {
    private Context mContext;
    private List<ModelAddressList> beans;
    private String address;
    private OnClickBianjiListener listener;

    public NewAddressAdapter(Context mContext, List<ModelAddressList> beans,OnClickBianjiListener listener) {
        this.mContext = mContext;
        this.beans = beans;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        if (beans.size() > 0) {

            return beans.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int arg0, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.new_address_item, null, false);
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_phone = (TextView) convertView.findViewById(R.id.txt_phone);
            holder.txt_address = (TextView) convertView.findViewById(R.id.txt_address);
            holder.iv_bianji_address = (ImageView) convertView.findViewById(R.id.iv_bianji_address);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ModelAddressList modelAddressList = beans.get(arg0);

        if (modelAddressList.getIs_do()==1) {
            holder.txt_name.setTextColor(mContext.getResources().getColor(R.color.title_color));
            holder.txt_phone.setTextColor(mContext.getResources().getColor(R.color.title_color));
            holder.txt_address.setTextColor(mContext.getResources().getColor(R.color.text_color_hint));
        }else {
            holder.txt_name.setTextColor(mContext.getResources().getColor(R.color.gray_D2));
            holder.txt_phone.setTextColor(mContext.getResources().getColor(R.color.gray_D2));
            holder.txt_address.setTextColor(mContext.getResources().getColor(R.color.gray_D2));
        }
        holder.txt_name.setText(modelAddressList.getConsignee_name()+"");
        holder.txt_phone.setText(modelAddressList.getConsignee_mobile()+"");
        holder.txt_address.setText(modelAddressList.getRegion_cn()+""+modelAddressList.getCommunity_cn()+modelAddressList.getDoorplate());
        holder.iv_bianji_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClickBianji(arg0);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        private TextView txt_name, txt_phone, txt_address;
        private ImageView iv_bianji_address;
    }
    public interface  OnClickBianjiListener{
        void onClickBianji(int position);
    }
}
