package com.huacheng.huiservers.ui.index.workorder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelWorkDetail;
import com.huacheng.huiservers.view.PhotoViewPagerAcitivity;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 时间：2018/12/10 15:50
 * created by DFF
 */
public class ImgAdapter extends CommonAdapter<ModelWorkDetail.Repair_CompleteBean> {
    public ImgAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelWorkDetail.Repair_CompleteBean item, final int position) {
        GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL + item.getImg_path() + ApiHttpClient.THUMB__500_500_ + item.getImg_name(),
                viewHolder.<ImageView>getView(R.id.iv_img), R.drawable.ic_default_rectange);
        viewHolder.<ImageView>getView(R.id.iv_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imgs = new ArrayList<>();

                    for (int i = 0; i <mDatas.size(); i++) {
                        imgs.add(ApiHttpClient.IMG_URL +mDatas.get(i).getImg_path()+mDatas.get(i).getImg_name());
                    }
                    Intent intent = new Intent(mContext, PhotoViewPagerAcitivity.class);
                    intent.putExtra("img_list",imgs);
                    intent.putExtra("position",position);
                    mContext.startActivity(intent);

            }
        });
    }
}
