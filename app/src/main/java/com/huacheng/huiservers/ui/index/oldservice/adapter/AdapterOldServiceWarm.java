package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelItemServiceWarm;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 亲情关怀Adapter
 * created by wangxiaotao
 * 2019/8/15 0015 下午 5:54
 */
public class AdapterOldServiceWarm extends CommonAdapter<ModelItemServiceWarm>{


    public AdapterOldServiceWarm(Context context, int layoutId, List<ModelItemServiceWarm> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelItemServiceWarm item, int position) {
        if (item.urlList.size()==0){
            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setVisibility(View.GONE);
            viewHolder.<ImageView>getView(R.id.iv_single_img).setVisibility(View.GONE);
        }else if (item.urlList.size()==1){
            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setVisibility(View.GONE);
            GlideUtils.getInstance().glideLoad(mContext,item.urlList.get(0), viewHolder.<ImageView>getView(R.id.iv_single_img),R.drawable.ic_default_rectange);
            viewHolder.<ImageView>getView(R.id.iv_single_img).setVisibility(View.VISIBLE);
            viewHolder.<ImageView>getView(R.id.iv_single_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SmartToast.showInfo("single");
                }
            });
        }else {
            viewHolder.<ImageView>getView(R.id.iv_single_img).setVisibility(View.GONE);
            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setVisibility(View.VISIBLE);

            List<String> urlList= new ArrayList<>();

            for (int i = 0; i < item.urlList.size(); i++) {
                if (i>8){
                    break;
                }
                urlList.add(item.urlList.get(i));
            }
            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setUrlList(urlList);
            viewHolder.<MyNineGridLayout>getView(R.id.nine_grid_layout).setListener(new MyNineGridLayout.OnClickNineGridImageListener() {
                @Override
                public void onClickImage(int position, String url, List<String> urlList) {
                    SmartToast.showInfo(""+position);
                }
            });
        }
    }
}
