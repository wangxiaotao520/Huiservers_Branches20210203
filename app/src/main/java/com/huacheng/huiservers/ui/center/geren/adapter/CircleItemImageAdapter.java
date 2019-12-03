package com.huacheng.huiservers.ui.center.geren.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.MyCookieStore;
import com.huacheng.huiservers.ui.shop.bean.BannerBean;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.glide.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class CircleItemImageAdapter extends BaseAdapter {

    private List<BannerBean> mBeans;
    private Context mContext;

    public CircleItemImageAdapter(Context context, List<BannerBean> beans) {//, ArrayList<String> list
        this.mBeans = beans;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        if (mBeans.size() > 3) {
            return 3;
        } else {
            return mBeans.size();
        }
        /*if (mList.size() > 3) {
            return 3;
        } else {
            return mList.size();
        }*/
    }

    @Override
    public Object getItem(int position) {

        return mBeans.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.circle_image_item,
                    null);
            holder.imageView = (ImageView) view.findViewById(R.id.imageView);
            holder.rel_circleWhiteMarkImg = (RelativeLayout) view.findViewById(R.id.rel_circleWhiteMarkImg);
            holder.tv_picnum = (TextView) view.findViewById(R.id.tv_picnum);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        GlideUtils.getInstance().glideLoad(mContext,MyCookieStore.URL + mBeans.get(position).getImg(),holder.imageView,R.drawable.ic_default_rectange);

        if (mBeans.size() >= 3) {
            if (position == 2) {
                holder.rel_circleWhiteMarkImg.setVisibility(View.VISIBLE);
                holder.tv_picnum.setText("共" + mBeans.size() + "张");
            }
        }
        final ArrayList<String> lists = new ArrayList<>();
        for (int i = 0; i < mBeans.size(); i++) {
            lists.add(MyCookieStore.URL + mBeans.get(i).getImg());
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imageBrower(position, lists);
            }
        });
        return view;
    }


    protected void imageBrower(int position, ArrayList<String> urls2) {
//        Intent intent = new Intent(mContext, ImagePagerActivity.class);
//        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
//        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
//        mContext.startActivity(intent);
        Intent intent = new Intent(mContext, PhotoViewPagerAcitivity.class);
        intent.putExtra("img_list",urls2);
        intent.putExtra("position",position);
        mContext.startActivity(intent);
    }


    public class ViewHolder {
        private ImageView imageView;
        private RelativeLayout rel_circleWhiteMarkImg;
        private TextView tv_picnum;
    }
}
