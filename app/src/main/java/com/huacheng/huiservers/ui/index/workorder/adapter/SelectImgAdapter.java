package com.huacheng.huiservers.ui.index.workorder.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelPhoto;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * created by wangxiaotao
 * 2018/12/12 0012 上午 11:39
 */
public class SelectImgAdapter<T>extends BaseAdapter{
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_ADD = 1;
    OnClickItemIconListener listener;
    protected  int MAX_COUNT=4;

    Context mContext;
    private List<T> mDatas = new ArrayList<>();

    private boolean isShowDelete=true;

    public SelectImgAdapter(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        if (mDatas.size() >= MAX_COUNT) {
            return MAX_COUNT;
        } else {
            return mDatas.size() + 1;
        }
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
            if (mDatas.size() == 0) {
                return TYPE_ADD;
            } else if (mDatas.size() < 9 && mDatas.size() > 0) {
                if (position < mDatas.size()) {
                    return TYPE_NORMAL;
                } else {
                    return TYPE_ADD;
                }

            } else if (mDatas.size() >= 9) {
                return TYPE_NORMAL;
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
                viewHolder.iv_img = convertView.findViewById(R.id.iv_img);
                viewHolder.id_grid_del = convertView.findViewById(R.id.id_grid_del);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //展示图片
            ModelPhoto modelPhoto = (ModelPhoto) mDatas.get(position);

            // 根据不同情况显示不同图片,这里显示网络图片，有的显示本地图片
            if (!NullUtil.isStringEmpty(modelPhoto.getLocal_path())){
                GlideUtils.getInstance().glideLoad(mContext,modelPhoto.getLocal_path(),viewHolder.iv_img,R.drawable.ic_default);
            }else {
                //显示不同比例的图片 这里显示800*1280
                GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL + modelPhoto.getPath()+ApiHttpClient.THUMB_800_1280_+modelPhoto.getHouse_imgs_name(), viewHolder.iv_img, R.drawable.ic_add_img);
            }

            final int final_position = position;
            viewHolder.iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickImage(final_position);
                    }
                }
            });
            if (isShowDelete){
                viewHolder.id_grid_del.setVisibility(View.VISIBLE);
                viewHolder.id_grid_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onClickDelete(final_position);
                        }
                    }
                });
            }else {
                viewHolder.id_grid_del.setVisibility(View.INVISIBLE);
            }
            return convertView;
        } else {
            ViewHolder viewHolder1 = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, getLayoutAddItemId(), null);
                viewHolder1 = new ViewHolder();
                viewHolder1.iv_img_add = convertView.findViewById(R.id.iv_img_add);
                convertView.setTag(viewHolder1);
            } else {
                viewHolder1 = (ViewHolder) convertView.getTag();
            }
            final int final_position = position;
            viewHolder1.iv_img_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickAdd(final_position);
                    }
                }
            });

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView iv_img;
        ImageView id_grid_del;
        ImageView iv_img_add;
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
         * 点击删除
         *
         * @param position
         */
        void onClickDelete(int position);

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

        return R.layout.layout_grid_img;
    }
    /**
     * 获取item add布局
     * @return
     */
    public int getLayoutAddItemId(){

        return R.layout.layout_grid_img_add;
    }


    public boolean isShowDelete() {
        return isShowDelete;
    }

    public void setShowDelete(boolean showDelete) {
        isShowDelete = showDelete;
    }

}

