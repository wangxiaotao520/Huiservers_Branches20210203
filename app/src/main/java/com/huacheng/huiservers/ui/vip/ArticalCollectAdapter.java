package com.huacheng.huiservers.ui.vip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.base.MyAdapter;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;

import me.nereo.multi_image_selector.bean.Image;

/**
 * @author Created by changyadong on 2020/12/3
 * @description
 */
public class ArticalCollectAdapter extends MyAdapter {

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_artical_collect, viewGroup, false);
            view.setTag(holder);
            holder.titleTx = view.findViewById(R.id.title);
            holder.typeTx = view.findViewById(R.id.type);
            holder.readNumTx = view.findViewById(R.id.read_num);
            holder.img = view.findViewById(R.id.img);
        } else holder = (ViewHolder) view.getTag();

        FrescoUtils.getInstance().setImageUri(holder.img,"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJXCgJrj89jpFHtuN2lwapQXMf1WAx0xMgmbmof4iatV4mhIgiaLEXvFCnkQq3lF4IjhIjxoEwOXB6Q/132");

        return view;
    }

    @Override
    public int getCount() {
        return 5;
    }

    class ViewHolder {
        TextView titleTx, readNumTx, dateTx, typeTx;
        SimpleDraweeView img;
    }
}
