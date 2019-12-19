package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;

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

    }
}
