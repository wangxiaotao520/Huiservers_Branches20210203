package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelVipIndex;
import com.huacheng.huiservers.view.MyGridview;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：vip 店铺信息
 * 时间：2020/11/30 10:33
 * created by DFF
 */
public class AdapterVipIndex extends CommonAdapter<ModelVipIndex> {

    public AdapterVipIndex(Context context, int layoutId, List<ModelVipIndex> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelVipIndex item, int position) {

        if (position==0){
            viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.VISIBLE);
        }else {
            viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.GONE);
        }
        viewHolder.<SimpleDraweeView>getView(R.id.iv_store_head);
        viewHolder.<TextView>getView(R.id.tv_store_name);
        viewHolder.<TextView>getView(R.id.tv_store_address);
        viewHolder.<LinearLayout>getView(R.id.ly_store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进店逛逛
            }
        });
        List<ModelVipIndex> mDatas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mDatas.add(new ModelVipIndex());
        }
        AdapterVipIndexList adapterVipIndexList = new AdapterVipIndexList(mContext, R.layout.item_vip_index_list_view, mDatas);
        viewHolder.<MyGridview>getView(R.id.grid_list).setAdapter(adapterVipIndexList);


        viewHolder.<MyGridview>getView(R.id.grid_list).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //商品详情
            }
        });
    }
}
