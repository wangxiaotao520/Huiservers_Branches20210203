package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelFenceList;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 围栏列表
 * created by wangxiaotao
 * 2020/10/2 0002 09:56
 */
public class AdapterFenceList <T>extends BaseAdapter {
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_ADD = 1;
    OnClickItemIconListener listener;


    Context mContext;
    private List<T> mDatas = new ArrayList<>();



    public AdapterFenceList(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {

        return mDatas.size() + 1;

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getViewTypeCount() {

        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (mDatas != null) {

           if (position==mDatas.size()){
               return TYPE_ADD;
           }
        }
        return TYPE_NORMAL;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (type == TYPE_NORMAL) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, getLayoutNormalItemId(), null);
                viewHolder = new ViewHolder();
                viewHolder.ll_item = convertView.findViewById(R.id.ll_item);
                viewHolder.iv_img = convertView.findViewById(R.id.iv_img);
                viewHolder.tv_text = convertView.findViewById(R.id.tv_text);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }




            final int final_position = position;
            viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickImage(final_position);
                    }
                }
            });
            ModelFenceList modelFenceList = (ModelFenceList) mDatas.get(position);
            viewHolder.tv_text.setText(modelFenceList.getTitle()+"");
            viewHolder.iv_img.setBackgroundResource(R.mipmap.ic_fence_item);

            return convertView;
        } else {
           ViewHolder viewHolder1 = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, getLayoutAddItemId(), null);
                viewHolder1 = new ViewHolder();
                viewHolder1.ll_item = convertView.findViewById(R.id.ll_item);
                viewHolder1.iv_img = convertView.findViewById(R.id.iv_img);
                viewHolder1.tv_text = convertView.findViewById(R.id.tv_text);
                convertView.setTag(viewHolder1);
            } else {
                viewHolder1 = (ViewHolder) convertView.getTag();
            }
            final int final_position = position;
            viewHolder1.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickAdd(final_position);
                    }
                }
            });
            viewHolder1.tv_text.setText("新增围栏");
            viewHolder1.iv_img.setBackgroundResource(R.mipmap.ic_new_fence_item);
            return convertView;
        }
    }

    static class ViewHolder {
        LinearLayout ll_item;
        ImageView iv_img;
        TextView tv_text;
    }

    public void setListener(OnClickItemIconListener listener) {
        this.listener = listener;
    }

    public interface OnClickItemIconListener {
        /**
         * 点击添加
         *
         * @param position
         */
        void onClickAdd(int position);


        /**
         * 点击图片
         *
         * @param position
         */
        void onClickImage(int position);

    }

    /**
     * 获取item normal布局
     * @return
     */
    public int getLayoutNormalItemId(){

        return R.layout.item_fencelist_normal;
    }
    /**
     * 获取item add布局
     * @return
     */
    public int getLayoutAddItemId(){

        return R.layout.item_fencelist_normal;
    }



}


