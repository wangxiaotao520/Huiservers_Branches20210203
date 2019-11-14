package com.huacheng.huiservers.ui.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.ui.servicenew.model.ModelServiceCat;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description:服务分类 2
 * created by wangxiaotao
 * 2019/11/14 0014 上午 10:04
 */
public class AdapterServiceCatTwo extends CommonAdapter<ModelServiceCat> {
  private   OnClickGridCatListener listener;
    public AdapterServiceCatTwo(Context context, int layoutId, List<ModelServiceCat> datas,OnClickGridCatListener listener) {
        super(context, layoutId, datas);
        this.listener = listener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelServiceCat item, final int position_parent) {

        viewHolder.<TextView>getView(R.id.txt_one).setText(item.getName()+"");
        viewHolder.<TextView>getView(R.id.txt_one).setTextColor(mContext.getResources().getColor(R.color.title_color));

        AdapterServiceCatGrid adapterServiceCatGrid = new AdapterServiceCatGrid(mContext, R.layout.item_view, item.getList());

        viewHolder.<GridView>getView(R.id.myGridView).setAdapter(adapterServiceCatGrid);
        viewHolder.<GridView>getView(R.id.myGridView).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener!=null){
                    listener.onClickGrid(position_parent,position);
                }
            }
        });
    }
    public interface OnClickGridCatListener{
        void  onClickGrid(int position_parent,int position_child);
    }
}