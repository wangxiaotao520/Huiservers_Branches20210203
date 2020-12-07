package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelVipIndex;
import com.huacheng.huiservers.ui.shop.ShopDetailActivityNew;
import com.huacheng.huiservers.ui.shop.StoreIndexActivity;
import com.huacheng.huiservers.view.MyGridview;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
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
    protected void convert(ViewHolder viewHolder, final ModelVipIndex item, int position) {

        FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.iv_store_head), ApiHttpClient.IMG_URL + item.getLogo());
        viewHolder.<TextView>getView(R.id.tv_store_name).setText(item.getSend_shop());
        viewHolder.<TextView>getView(R.id.tv_store_address).setText(item.getAddress());

        if (item.getS_list() != null && item.getS_list().size() > 0) {
            List<ModelVipIndex> modelVipIndexList=new ArrayList<>();
            modelVipIndexList.clear();
            for (int i = 0; i < item.getS_list().size(); i++) {
                if (i<=2){
                    modelVipIndexList.add(item.getS_list().get(i));
                }
            }
            AdapterVipIndexList adapterVipIndexList = new AdapterVipIndexList(mContext, R.layout.item_vip_index_list_view, modelVipIndexList);
            viewHolder.<MyGridview>getView(R.id.grid_list).setAdapter(adapterVipIndexList);
            adapterVipIndexList.notifyDataSetChanged();
        }

        if (position == 0) {
            viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.VISIBLE);
        } else {
            viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.GONE);
        }
        viewHolder.<LinearLayout>getView(R.id.ly_store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进店逛逛
                Intent intent = new Intent(mContext, StoreIndexActivity.class);
                intent.putExtra("store_id", item.getSend_shop_id());
                mContext.startActivity(intent);
            }
        });
        viewHolder.<MyGridview>getView(R.id.grid_list).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //商品详情
                Intent intent = new Intent(mContext, ShopDetailActivityNew.class);
                Bundle bundle = new Bundle();
                bundle.putString("shop_id", mDatas.get((int) position).getId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

}
