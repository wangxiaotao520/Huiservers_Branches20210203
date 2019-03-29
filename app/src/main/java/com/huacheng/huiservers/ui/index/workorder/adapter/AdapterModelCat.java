package com.huacheng.huiservers.ui.index.workorder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelWorkPersonalCatItem;
import com.huacheng.huiservers.ui.index.workorder.WorkCateDescActivtiy;
import com.huacheng.huiservers.ui.index.workorder.commit.PersonalWorkOrderCommitActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 自用分类
 * created by wangxiaotao
 * 2018/12/13 0013 上午 10:33
 */
public class AdapterModelCat extends CommonAdapter<ModelWorkPersonalCatItem> {

    Context mContext;

    public AdapterModelCat(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ModelWorkPersonalCatItem item, int position) {
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getType_name() + "");
        viewHolder.<TextView>getView(R.id.tv_desc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, WorkCateDescActivtiy.class);
                intent.putExtra("typeName", item.getType_name());
                intent.putExtra("typeContent", item.getType_content());
                mContext.startActivity(intent);
            }
        });
        viewHolder.<LinearLayout>getView(R.id.ll_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelWorkPersonalCatItem model = item;
                Intent intent = new Intent(mContext, PersonalWorkOrderCommitActivity.class);
                intent.putExtra("type_id", model.getId());
                intent.putExtra("type_pid", model.getPid());
                intent.putExtra("type_price", model.getEntry_fee());
                intent.putExtra("type_name", model.getType_name());
                mContext.startActivity(intent);
            }
        });

    }
}
