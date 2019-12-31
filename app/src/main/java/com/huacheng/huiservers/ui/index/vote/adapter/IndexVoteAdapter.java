package com.huacheng.huiservers.ui.index.vote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huacheng.huiservers.R;
import com.huacheng.huiservers.http.okhttp.ApiHttpClient;
import com.huacheng.huiservers.model.ModelIndexVoteItem;
import com.huacheng.libraryservice.utils.fresco.FrescoUtils;
import com.stx.xhb.xbanner.OnDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 1是最美家庭 2.是新年vlog投票
 * created by wangxiaotao
 * 2019/9/3 0003 下午 4:40
 */
public class IndexVoteAdapter <T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDatas=new ArrayList<>();
    private OnClickItemListener listener;
    private int type = 1;//1

    public IndexVoteAdapter(Context mContext, List<T> mDatas,OnClickItemListener listener,int type) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.listener=listener;
        this.type=type;
    }

    @Override
    public int getCount() {
        return (int)Math.ceil(mDatas.size()*1f/2);
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_vote_index,null);
            viewHolder=new ViewHolder();
            initViewHolder(convertView,viewHolder);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        // 从数据里取值 第一个View
        if (position*2<mDatas.size()){
            ModelIndexVoteItem item = (ModelIndexVoteItem) mDatas.get(position*2);
            viewHolder. ll_item_view1.setVisibility(View.VISIBLE);
            //String url=    "http://img.hui-shenghuo.cn/huacheng_old/old/artice/19/09/02/5d6c942e27171.jpeg";
            FrescoUtils.getInstance().setImageUri(viewHolder.sdv_title_img1, ApiHttpClient.IMG_URL+item.getImg());
            viewHolder.tv_famliy_name1.setText(item.getTitle());
            viewHolder.tv_vote_count1.setText(item.getPoll()+"票");
           // viewHolder.tv_tag1.setText(""+((position*2)+1));
            viewHolder.tv_tag1.setText(item.getNumber()+"");
            viewHolder.ll_item_view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null) {
                        listener.onClickItem(v,position*2);
                    }
                }
            });
            viewHolder.tv_onClick1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null) {

                            listener.onClickVote(v,position*2);

                    }
                }
            });

            if (type==2){
                viewHolder.tv1_onClick1.setVisibility(View.VISIBLE);
                viewHolder.tv1_onClick1.setOnClickListener(new OnDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {

                    }
                });
            }else {
                viewHolder.tv1_onClick1.setVisibility(View.GONE);
            }

        }
        // 从数据里取值 第二个View
        if (position*2+1<mDatas.size()){
            viewHolder. ll_item_view2.setVisibility(View.VISIBLE);
            ModelIndexVoteItem item = (ModelIndexVoteItem) mDatas.get(position*2+1);
            String url=    "http://img.hui-shenghuo.cn/huacheng_old/old/artice/19/09/02/5d6c942e27171.jpeg";
            FrescoUtils.getInstance().setImageUri(viewHolder.sdv_title_img2,ApiHttpClient.IMG_URL+item.getImg());
            viewHolder.tv_famliy_name2.setText(item.getTitle());
            viewHolder.tv_vote_count2.setText(item.getPoll()+"票");
            viewHolder.tv_tag2.setText(item.getNumber()+"");
           // viewHolder.tv_tag2.setText(""+((position*2+1)+1));
            viewHolder.ll_item_view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null) {
                        listener.onClickItem(v,position*2+1);
                    }
                }
            });
            viewHolder.tv_onClick2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null)
                    listener.onClickVote(v,position*2+1);
                }
            });
            if (type==2){
                viewHolder.tv2_onClick2.setVisibility(View.VISIBLE);
                viewHolder.tv2_onClick2.setOnClickListener(new OnDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {

                    }
                });
            }else {
                viewHolder.tv2_onClick2.setVisibility(View.GONE);
            }

        }else {
            //说明mdatas的总个数是奇数个
            viewHolder. ll_item_view2.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void initViewHolder(View convertView, ViewHolder viewHolder) {
        viewHolder. ll_item_view1=  convertView.findViewById(R.id.ll_item_view1);
        viewHolder. sdv_title_img1=  convertView.findViewById(R.id.sdv_title_img1);
        viewHolder. tv_famliy_name1=  convertView.findViewById(R.id.tv_famliy_name1);
        viewHolder. tv_vote_count1=  convertView.findViewById(R.id.tv_vote_count1);
        viewHolder. tv_tag1=  convertView.findViewById(R.id.tv_tag1);
        viewHolder. ll_item_view2=convertView.findViewById(R.id.ll_item_view2);
        viewHolder. sdv_title_img2=convertView.findViewById(R.id.sdv_title_img2);
        viewHolder. tv_famliy_name2=convertView.findViewById(R.id.tv_famliy_name2);
        viewHolder. tv_vote_count2=convertView.findViewById(R.id.tv_vote_count2);
        viewHolder. tv_tag2=convertView.findViewById(R.id.tv_tag2);
        viewHolder. tv_onClick1=convertView.findViewById(R.id.tv_onClick1);
        viewHolder. tv1_onClick1=convertView.findViewById(R.id.tv1_onClick1);
        viewHolder. tv_onClick2=convertView.findViewById(R.id.tv_onClick2);
        viewHolder. tv2_onClick2=convertView.findViewById(R.id.tv2_onClick2);
    }

    static class  ViewHolder {
        LinearLayout ll_item_view1;
        SimpleDraweeView sdv_title_img1;
        TextView tv_tag1;
        TextView tv_famliy_name1;
        TextView tv_vote_count1;
        LinearLayout  ll_item_view2;
        SimpleDraweeView sdv_title_img2;
        TextView  tv_tag2;
        TextView  tv_famliy_name2;
        TextView  tv_vote_count2;
        TextView  tv_onClick1;
        TextView  tv1_onClick1;
        TextView  tv_onClick2;
        TextView  tv2_onClick2;
    }
    public  interface OnClickItemListener{
        /**
         * 点击item
         * @param v
         * @param position
         */
        void onClickItem(View v,int position);
        /**
         * 点击投票
         * @param v
         * @param position
         */
        void onClickVote(View v,int position);

        /**
         * 点击拉票
         * @param v
         * @param position
         */
        void onClickLapiao(View v,int position);
    }
}
