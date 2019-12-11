package com.huacheng.huiservers.ui.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelVBaner;
import com.huacheng.libraryservice.widget.verticalbannerview.BaseBannerAdapter;
import com.huacheng.libraryservice.widget.verticalbannerview.VerticalBannerView;

import java.util.List;

/**
 * Description: 资讯的banner
 * created by wangxiaotao
 * 2019/10/17 0017 上午 11:23
 */
public class VBannerZixunAdapter extends BaseBannerAdapter<ModelVBaner> {



    public VBannerZixunAdapter(List<ModelVBaner> datas) {
        super(datas);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public View getView(VerticalBannerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article,null);
    }

    @Override
    public void setItem(final View view, final ModelVBaner data, final int position) {
        TextView tv_title = view.findViewById(R.id.tv_title);

        tv_title.setText(""+position);
        //你可以增加点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //比如打开url
//              //  Toast.makeText(view.getContext(),"跳转到邻里",Toast.LENGTH_SHORT).show();
//                ModelEventHome modelEventHome = new ModelEventHome();
//                modelEventHome.setType(0);
//                EventBus.getDefault().post(modelEventHome);
            }
        });
    }
}

