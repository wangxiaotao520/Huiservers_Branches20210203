package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceCat;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 服务分类 1.
 * created by wangxiaotao
 * 2019/11/14 0014 上午 10:03
 */
public class AdapterServiceCatOne extends CommonAdapter<ModelServiceCat> {
    public AdapterServiceCatOne(Context context, int layoutId, List<ModelServiceCat> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelServiceCat item, int position) {

        if (item.isChecked()){
            viewHolder.<TextView>getView(R.id.tv_view).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.txt_one).setText(item.getName()+"");
            viewHolder.<TextView>getView(R.id.txt_one) .getPaint().setFakeBoldText(true);
            viewHolder.<LinearLayout>getView(R.id.ly_bg).setBackgroundResource(R.color.windowbackground);

        }else {
            viewHolder.<TextView>getView(R.id.tv_view).setVisibility(View.GONE);
            viewHolder.<TextView>getView(R.id.txt_one).setText(item.getName()+"");
            viewHolder.<TextView>getView(R.id.txt_one) .getPaint().setFakeBoldText(false);
            viewHolder.<LinearLayout>getView(R.id.ly_bg).setBackgroundResource(R.color.white);
        }

    }
}
