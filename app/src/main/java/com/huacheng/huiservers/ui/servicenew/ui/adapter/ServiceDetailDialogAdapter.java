package com.huacheng.huiservers.ui.servicenew.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceDetail;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：服务详情分类
 * 时间：2019/12/27 19:25
 * created by DFF
 */
public class ServiceDetailDialogAdapter extends CommonAdapter<ModelServiceDetail.TagListBean> {
    ModelServiceDetail.TagListBean selected_bean;
    public ServiceDetailDialogAdapter(Context context, int layoutId, List<ModelServiceDetail.TagListBean> datas, ModelServiceDetail.TagListBean selected_bean) {
        super(context, layoutId, datas);
        this.selected_bean=selected_bean;
    }

    @Override
    protected void convert(ViewHolder viewHolder,ModelServiceDetail.TagListBean item, int position) {

        viewHolder.<TextView>getView(R.id.tv_style).setText(item.getTagname());
        viewHolder.<TextView>getView(R.id.txt_address).setText("地址");
        if (item.getId().equals(selected_bean.getId())){
            viewHolder.<ImageView>getView(R.id.iv_select).setVisibility(View.VISIBLE);
        }else {
            viewHolder.<ImageView>getView(R.id.iv_select).setVisibility(View.GONE);
        }


    }
}
