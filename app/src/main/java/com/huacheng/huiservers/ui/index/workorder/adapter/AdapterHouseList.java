package com.huacheng.huiservers.ui.index.workorder.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.HouseBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description:工单选择房屋
 * created by wangxiaotao
 * 2019/4/9 0009 下午 5:50
 */
public class AdapterHouseList extends CommonAdapter<HouseBean> {
    private OnClickDeleteback listener;
    int type;
    public AdapterHouseList(Context context, int layoutId, List<HouseBean> datas,int type,OnClickDeleteback listener) {
        super(context, layoutId, datas);
        this.type=type;
        this.listener=listener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final HouseBean item, final int position) {
        viewHolder.<TextView>getView(R.id.tv_community_address).setText(item.getCommunity_name()+"");
        viewHolder.<TextView>getView(R.id.tv_address).setText("地址："+item.getAddress()+"");
        if (type==1){
            viewHolder.<LinearLayout>getView(R.id.ly_delete).setVisibility(View.VISIBLE);
            viewHolder.<LinearLayout>getView(R.id.ly_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onClickDelete(item);
                    }
                }
            });
        }else {
            viewHolder.<LinearLayout>getView(R.id.ly_delete).setVisibility(View.GONE);
        }
    }
    public interface  OnClickDeleteback{
        /**
         * 点击删除
         * @param item
         */
        void onClickDelete(HouseBean item);
    }
}
