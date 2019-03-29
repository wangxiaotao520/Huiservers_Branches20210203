package com.huacheng.huiservers.ui.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelEventHome;
import com.huacheng.huiservers.model.ModelVBaner;
import com.huacheng.libraryservice.widget.verticalbannerview.BaseBannerAdapter;
import com.huacheng.libraryservice.widget.verticalbannerview.VerticalBannerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Description: 垂直轮播广告adapter
 * created by wangxiaotao
 * 2018/12/19 0019 下午 4:53
 */
public class VBannerAdapter extends BaseBannerAdapter<ModelVBaner> {



    public VBannerAdapter(List<ModelVBaner> datas) {
        super(datas);
    }

    @Override
    public int getCount() {
        return (int)Math.ceil(mDatas.size()*1f/2);
    }

    @Override
    public View getView(VerticalBannerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_v_banner,null);
    }

    @Override
    public void setItem(final View view, final ModelVBaner data,final int position) {
        TextView tv_title1 = (TextView) view.findViewById(R.id.tv_title1);
        TextView tv_time1 = (TextView) view.findViewById(R.id.tv_time1);
        TextView tv_title2 = (TextView) view.findViewById(R.id.tv_title2);
        TextView tv_time2 = (TextView) view.findViewById(R.id.tv_time2);
        //第一个
        if (position*2<mDatas.size()){
            ModelVBaner modelVBaner1 = mDatas.get(position * 2);
            tv_title1.setText(modelVBaner1.getTitle());
            tv_time1.setText(modelVBaner1.getAddtime());
        }
        //第二个
        if ((position*2+1)<mDatas.size()){
            ModelVBaner modelVBaner2 = mDatas.get(position * 2+1);
            tv_title2.setText(modelVBaner2.getTitle());
            tv_time2.setText(modelVBaner2.getAddtime());
        }


        //你可以增加点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //比如打开url
              //  Toast.makeText(view.getContext(),"跳转到邻里",Toast.LENGTH_SHORT).show();
                ModelEventHome modelEventHome = new ModelEventHome();
                modelEventHome.setType(0);
                EventBus.getDefault().post(modelEventHome);
            }
        });
    }
}
