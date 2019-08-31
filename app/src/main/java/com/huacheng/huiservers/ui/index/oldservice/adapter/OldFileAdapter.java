package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldCheckRecord;
import com.huacheng.huiservers.utils.StringUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：老人档案体检记录
 * 时间：2019/8/17 10:23
 * created by DFF
 */
public class OldFileAdapter extends CommonAdapter<ModelOldCheckRecord> {
    public OldFileAdapter(Context context, int layoutId, List<ModelOldCheckRecord> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelOldCheckRecord item, int position) {
        if (item.getList_type()==0){
            viewHolder.<LinearLayout>getView(R.id.ll_content).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.item_num).setText("体检记录" + (position+1));
            if ("1".equals(item.getType())){
                viewHolder.<TextView>getView(R.id.tv_tijian_type).setText( " 常规体检");
            }else if ("2".equals(item.getType())){
                viewHolder.<TextView>getView(R.id.tv_tijian_type).setText(" 智能硬件体检");
            }else if ("3".equals(item.getType())){
                viewHolder.<TextView>getView(R.id.tv_tijian_type).setText( " 合作医院体检");
            }


            viewHolder.<TextView>getView(R.id.tv_tijian_time).setText(StringUtils.getDateToString(item.getChecktime(),"8"));
            viewHolder.<TextView>getView(R.id.tv_tijian_content).setText(item.getDescribe()+"");

//            viewHolder.<LinearLayout>getView(R.id.ly_detail).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                }
//            });
        }else {
            viewHolder.<LinearLayout>getView(R.id.ll_content).setVisibility(View.INVISIBLE);
        }

    }
}
