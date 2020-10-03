package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldDevice;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：老人设备查看更多
 * 时间：2019/12/12 15:50
 * created by DFF
 */
public class AdapterOldDevice extends CommonAdapter<ModelOldDevice> {
    int type;

    public AdapterOldDevice(Context context, int layoutId, List<ModelOldDevice> datas, int type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelOldDevice item, int position) {
        if (type == 2) {
            if (position == 0) {
                viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_old_czsb);
            } else if (position == 1) {
                viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_old_sos);
            } else if (position == 2) {
                viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_old_jchm);
            }
        } else {
            if (position == 0) {
                viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_old_zuji);
            } else if (position == 1) {
                viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_old_jibu);
            } else if (position == 2) {
                viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_old_cxl);
            } else if (position == 3) {
                viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_old_cxy);
            } else if (position == 4) {
                viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_old_ycw);
            } else if (position == 5) {
                viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.mipmap.ic_old_weilan);
            }
        }
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getName());

    }
}
