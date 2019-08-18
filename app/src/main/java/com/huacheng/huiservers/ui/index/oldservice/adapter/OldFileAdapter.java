package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldFile;
import com.huacheng.huiservers.ui.index.oldservice.OldFileDetailActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：老人档案体检记录
 * 时间：2019/8/17 10:23
 * created by DFF
 */
public class OldFileAdapter extends CommonAdapter<ModelOldFile> {
    public OldFileAdapter(Context context, int layoutId, List<ModelOldFile> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelOldFile item, int position) {

        viewHolder.<TextView>getView(R.id.item_num).setText("体检类型" + (position + 1));
        viewHolder.<TextView>getView(R.id.tv_tijian_type).setText("体检类型");
        viewHolder.<TextView>getView(R.id.tv_tijian_time).setText("体检类型");
        viewHolder.<TextView>getView(R.id.tv_tijian_content).setText("心律不齐，患有轻微的新长辈啊啊啊啊啊啊啊啊啊啊");

        viewHolder.<LinearLayout>getView(R.id.ly_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, OldFileDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
