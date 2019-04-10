package com.huacheng.huiservers.ui.index.workorder_second.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.WorkOrderCatStatardDialog;
import com.huacheng.huiservers.model.ModelWorkPersonalCatItem;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description:
 * created by wangxiaotao
 * 2019/4/9 0009 下午 5:24
 */
public class AdapterWorkType extends CommonAdapter<ModelWorkPersonalCatItem> {


    public AdapterWorkType(Context context, int layoutId, List<ModelWorkPersonalCatItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelWorkPersonalCatItem item, int position) {
        viewHolder.<TextView>getView(R.id.tv_statard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WorkOrderCatStatardDialog(mContext,"ajdfasjkfdjaks;ljkfdljfls;").show();
            }
        });
    }
}
