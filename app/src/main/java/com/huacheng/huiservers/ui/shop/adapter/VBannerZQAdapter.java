package com.huacheng.huiservers.ui.shop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelVBaner;
import com.huacheng.libraryservice.widget.verticalbannerview.BaseBannerAdapter;
import com.huacheng.libraryservice.widget.verticalbannerview.VerticalBannerView;

import java.util.List;

/**
 * Description: 垂直轮播广告adapter
 * created by DFF
 * 2020/1/3 下午 16:27
 */
public class VBannerZQAdapter extends BaseBannerAdapter<ModelVBaner> {


    public VBannerZQAdapter(List<ModelVBaner> datas) {
        super(datas);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public View getView(VerticalBannerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zq_v_banner, null);
    }

    @Override
    public void setItem(final View view, final ModelVBaner data, final int position) {
        TextView tv_title1 = (TextView) view.findViewById(R.id.tv_title);
        tv_title1.setText(data.getTitle());
    }
}
