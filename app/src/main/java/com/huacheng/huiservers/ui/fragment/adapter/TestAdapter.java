package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huacheng.huiservers.R;

import java.util.List;

/**
 * Description:测试页
 * Author: badge
 * Create: 2018/12/20 12:04
 */
public class TestAdapter extends BaseAdapter {

    public Context mContext;
    public List<String> mDatas;

    public TestAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return (
                mDatas != null && mDatas.size() > 0) ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.test_item_txt, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item.setText(mDatas.get(i));

        return convertView;
    }

    class ViewHolder {
        TextView tv_item;

        public ViewHolder(View v) {
            this.tv_item = v.findViewById(R.id.tv_item);
        }
    }
}
