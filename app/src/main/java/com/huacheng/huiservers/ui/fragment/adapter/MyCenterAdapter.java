package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.center.bean.PersoninfoBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：个人中心我的服务
 * 时间：2019/12/12 15:50
 * created by DFF
 */
public class MyCenterAdapter extends CommonAdapter<PersoninfoBean> {
    public MyCenterAdapter(Context context, int layoutId, List<PersoninfoBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, PersoninfoBean item, int position) {
        // TODO: 2019/12/28 图标 
        viewHolder.<ImageView>getView(R.id.iv_cat).setBackgroundResource(R.color.orange_bg);
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getFullname());

    }
}
