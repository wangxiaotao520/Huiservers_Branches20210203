package com.huacheng.huiservers.ui.index.oldservice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelOldMessage;
import com.huacheng.libraryservice.utils.NullUtil;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description: 关联消息
 * created by wangxiaotao
 * 2019/8/20 0020 下午 6:23
 */
public class AdapterOldMessage extends CommonAdapter<ModelOldMessage>{
    private OnClickMessageListener listener;
    public AdapterOldMessage(Context context, int layoutId, List<ModelOldMessage> datas,OnClickMessageListener listener) {
        super(context, layoutId, datas);
        this.listener = listener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ModelOldMessage item, final int position) {
        viewHolder.<TextView>getView(R.id.tv_title).setText(item.getContent()+"");

        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getFrom_nickname()+"");
        viewHolder.<TextView>getView(R.id.tv_name).setText(item.getFrom_nickname()+"");
        viewHolder.<TextView>getView(R.id.tv_id).setText("慧生活账号："+item.getFrom_username());
        FrescoUtils.getInstance().setImageUri(viewHolder.<SimpleDraweeView>getView(R.id.sdv_head), ApiHttpClient.IMG_URL+item.getFrom_avatars());
        if (NullUtil.isStringEmpty(item.getState_str())){
            viewHolder.<TextView>getView(R.id.tv_status).setText("");
            viewHolder.<LinearLayout>getView(R.id.ll_agree_reject).setVisibility(View.VISIBLE);
            viewHolder.<TextView>getView(R.id.tv_reject).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onClick(position,item,2);
                    }
                }
            });
            viewHolder.<TextView>getView(R.id.tv_agree).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onClick(position,item,1);
                    }
                }
            });

        }else {
            viewHolder.<TextView>getView(R.id.tv_status).setText(item.getState_str()+"");
            viewHolder.<LinearLayout>getView(R.id.ll_agree_reject).setVisibility(View.GONE);
        }
    }

    public interface  OnClickMessageListener{
        //1->同意 2->拒绝
        void onClick(int position,ModelOldMessage item,int state);
    }
}
