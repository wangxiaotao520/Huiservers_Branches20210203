package com.huacheng.huiservers.ui.index.workorder.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelNewWorkOrder;
import com.huacheng.libraryservice.utils.glide.GlideUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：
 * 时间：2019/4/9 10:54
 * created by DFF
 */
public class WorkOrderDetailAdapter extends CommonAdapter<ModelNewWorkOrder.WorkLogBean> {
    private OnclickImg mOnclickImg;

    public WorkOrderDetailAdapter(Context context, int layoutId, List<ModelNewWorkOrder.WorkLogBean> datas,OnclickImg mOnclickImg) {
        super(context, layoutId, datas);
        this.mOnclickImg=mOnclickImg;

    }

    public interface OnclickImg {
        void lickImg(List<ModelNewWorkOrder.ImgListBean> mListimg);
        void lickPhoto(String photo);
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ModelNewWorkOrder.WorkLogBean item, int position) {

        viewHolder.<TextView>getView(R.id.tv_status_name).setText(item.getType_cn());
        viewHolder.<TextView>getView(R.id.tv_time).setText(item.getAddtime());
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getOperator());
        viewHolder.<TextView>getView(R.id.tv_person_type).setText("[" + item.getRole() + "]");
        viewHolder.<TextView>getView(R.id.tv_photo).setText(item.getPhone());
        viewHolder.<TextView>getView(R.id.tv_photo).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        viewHolder.<TextView>getView(R.id.tv_caozuo_content).setText(item.getExplain());
        if (item.getLog_img() != null && item.getLog_img().size() > 0) {
            viewHolder.<LinearLayout>getView(R.id.ly_none).setVisibility(View.VISIBLE);
            viewHolder.<LinearLayout>getView(R.id.linear_repair_photo).removeAllViews();
            for (int i = 0; i < item.getLog_img().size(); i++) {
                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,
                        100);//两个50分别为添加图片的大小
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                params.setMargins(0,0,20,0);
                GlideUtils.getInstance().glideLoad(mContext, ApiHttpClient.IMG_URL + item.getLog_img().get(i).getImg_path()
                        + item.getLog_img().get(i).getImg_name(), imageView, R.drawable.ic_default_rectange);
                imageView.setLayoutParams(params);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnclickImg.lickImg(item.getLog_img());
                    }
                });
                viewHolder.<LinearLayout>getView(R.id.linear_repair_photo).addView(imageView);
            }
        } else {
            viewHolder.<LinearLayout>getView(R.id.ly_none).setVisibility(View.GONE);
        }
        viewHolder.<TextView>getView(R.id.tv_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnclickImg.lickPhoto(item.getPhone());

            }
        });
        if (position == 0) {
            viewHolder.<ImageView>getView(R.id.iv_tag).setBackgroundResource(R.mipmap.icon_workorder_yuan_onclick);
            viewHolder.<TextView>getView(R.id.tv_tag).setBackgroundResource(R.color.orange);
        } else {
            viewHolder.<ImageView>getView(R.id.iv_tag).setBackgroundResource(R.mipmap.icon_workorder_yuan);
            viewHolder.<TextView>getView(R.id.tv_tag).setBackgroundResource(R.color.graynew4);
        }
    }
}
