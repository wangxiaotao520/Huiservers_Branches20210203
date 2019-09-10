package com.huacheng.huiservers.ui.index.vote.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelVote;
import com.huacheng.huiservers.utils.StringUtils;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 类描述：活动留言adapter
 * 时间：2019/9/5 18:40
 * created by DFF
 */
public class VoteMessageAdapter extends CommonAdapter<ModelVote> {

    private OnClickItemListener mListener;

    public VoteMessageAdapter(Context context, int layoutId, List<ModelVote> datas, OnClickItemListener mListener) {
        super(context, layoutId, datas);
        this.mListener = mListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ModelVote item, final int position) {

        FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_head), ApiHttpClient.IMG_URL + item.getAvatars());
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getNickname());
        viewHolder.<TextView>getView(R.id.tv_zan_num).setText(item.getPraise()+"");
        viewHolder.<TextView>getView(R.id.tv_time).setText(StringUtils.getDateToString(item.getAddtime(), "7"));
        viewHolder.<TextView>getView(R.id.tv_content).setText(item.getMessage());
        if (item.getType() == 0) {
            viewHolder.<ImageView>getView(R.id.iv_zan).setBackground(mContext.getResources().getDrawable(R.mipmap.ic_zan_no));
        } else {
            viewHolder.<ImageView>getView(R.id.iv_zan).setBackground(mContext.getResources().getDrawable(R.mipmap.ic_zan_yes));
        }
        viewHolder.<ImageView>getView(R.id.iv_zan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickZan(item, position);
            }
        });


    }

    public interface OnClickItemListener {
        /**
         * 点击赞
         *
         * @param v
         * @param position
         */
        void onClickZan(ModelVote v, int position);

    }
}
