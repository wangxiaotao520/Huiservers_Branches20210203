package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelOldFile;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：添加用户adapter
 * 时间：2019/8/17 10:23
 * created by DFF
 */
public class AddOldAdapter extends CommonAdapter<ModelOldFile> {
    boolean iscancel;

    public AddOldAdapter(Context context, int layoutId, List<ModelOldFile> datas, boolean iscancel) {
        super(context, layoutId, datas);
        this.iscancel = iscancel;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ModelOldFile item, int position) {

       /* for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.activity_old_user_item, null);
            SimpleDraweeView sdv_head = view.findViewById(R.id.sdv_head);
            TextView tv_tag = view.findViewById(R.id.tv_tag);
            TextView tv_name = view.findViewById(R.id.tv_name);
            LinearLayout ly_delete = view.findViewById(R.id.ly_delete);

            //FrescoUtils.getInstance().setImageUri(iv_repair_head, ApiHttpClient.IMG_URL + modelNewWorkOrder.getWork_user().get(i).getHead_img());

        }*/
        viewHolder.<TextView>getView(R.id.tv_tag).setText("父亲");
        viewHolder.<TextView>getView(R.id.tv_name).setText("哈哈哈");
        if (iscancel) {
            viewHolder.<LinearLayout>getView(R.id.ly_delete).setVisibility(View.VISIBLE);
            //viewHolder.<RelativeLayout>getView(R.id.ry_yinying).setElevation(float(3));
        } else {
            viewHolder.<LinearLayout>getView(R.id.ly_delete).setVisibility(View.GONE);
            //viewHolder.<RelativeLayout>getView(R.id.ry_yinying).setVisibility(View.VISIBLE);
        }
        viewHolder.<LinearLayout>getView(R.id.ly_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
