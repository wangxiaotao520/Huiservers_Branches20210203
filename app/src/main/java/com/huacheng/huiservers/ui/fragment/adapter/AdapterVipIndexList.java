package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelVipIndex;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：vip 店铺商品信息
 * 时间：2020/11/30 10:33
 * created by DFF
 */
public class AdapterVipIndexList extends CommonAdapter<ModelVipIndex> {

    public AdapterVipIndexList(Context context, int layoutId, List<ModelVipIndex> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelVipIndex item, int position) {

        viewHolder.<TextView>getView(R.id.txt_shop_original).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

    }
}
