package com.huacheng.huiservers.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.model.ModelCoummnityList;
import com.huacheng.libraryservice.utils.NullUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description:小区列表
 * created by wangxiaotao
 * 2019/11/8 0008 上午 11:22
 */
public class AdapterCoummunityList extends CommonAdapter<ModelCoummnityList>{
    private OnClickCommunityListListener listListener ;
    private int type = 0;//0是小区列表 1是搜索小区列表
    private int jump_type = 1;//1是首页2是商城搜索


    public AdapterCoummunityList(Context context, int layoutId, List<ModelCoummnityList> datas,OnClickCommunityListListener listener,int type,int jump_type) {
        super(context, layoutId, datas);
        this.listListener = listener;
        this.type = type;
        this.jump_type=jump_type;
    }

    @Override
    protected void convert(ViewHolder viewHolder, final ModelCoummnityList item, final int position) {
       if (item.getType()==1){
           viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.VISIBLE);
           viewHolder.<TextView>getView(R.id.tv_title).setText("当前小区");
           viewHolder.<TextView>getView(R.id.tv_community_name).setText(item.getName());
           if (!NullUtil.isStringEmpty(item.getAddress())){
               viewHolder.<TextView>getView(R.id.tv_community_address).setVisibility(View.VISIBLE);
               viewHolder.<TextView>getView(R.id.tv_community_address).setText(item.getAddress()+"");
           }else {
               viewHolder.<TextView>getView(R.id.tv_community_address).setVisibility(View.GONE);
           }
           viewHolder.<ImageView>getView(R.id.iv_relocation).setVisibility(View.VISIBLE);
           viewHolder.<TextView>getView(R.id.tv_relocation).setVisibility(View.VISIBLE);
           viewHolder.<ImageView>getView(R.id.iv_relocation).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    if (listListener!=null){
                        listListener.onListClickRelocation();
                    }
               }
           });
           viewHolder.<TextView>getView(R.id.tv_relocation).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (listListener!=null){
                       listListener.onListClickRelocation();
                   }
               }
           });
       }else if (item.getType()==2){
           if (item.getPosition()==0){
               viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.VISIBLE);
               viewHolder.<TextView>getView(R.id.tv_title).setText("我的小区");
           }else {
               viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.GONE);
           }
           viewHolder.<TextView>getView(R.id.tv_community_name).setText(item.getName());
           if (!NullUtil.isStringEmpty(item.getAddress())){
               viewHolder.<TextView>getView(R.id.tv_community_address).setVisibility(View.VISIBLE);
               viewHolder.<TextView>getView(R.id.tv_community_address).setText(item.getAddress()+"");
           }else {
               viewHolder.<TextView>getView(R.id.tv_community_address).setVisibility(View.GONE);
           }
           viewHolder.<ImageView>getView(R.id.iv_relocation).setVisibility(View.GONE);
           viewHolder.<TextView>getView(R.id.tv_relocation).setVisibility(View.GONE);
       }else if (item.getType()==3){
           if (item.getPosition()==0){
               if (type==1){
                   viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.GONE);
               }else {
                   viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.VISIBLE);
                   if (jump_type==1){
                       viewHolder.<TextView>getView(R.id.tv_title).setText("附近小区");
                   }else {
                       viewHolder.<TextView>getView(R.id.tv_title).setText("附近");
                   }

               }
           }else {
               viewHolder.<TextView>getView(R.id.tv_title).setVisibility(View.GONE);
           }
           viewHolder.<TextView>getView(R.id.tv_community_name).setText(item.getName());
           if (!NullUtil.isStringEmpty(item.getAddress())){
               viewHolder.<TextView>getView(R.id.tv_community_address).setVisibility(View.VISIBLE);
               viewHolder.<TextView>getView(R.id.tv_community_address).setText(item.getAddress()+"");
           }else {
               viewHolder.<TextView>getView(R.id.tv_community_address).setVisibility(View.GONE);
           }
           viewHolder.<ImageView>getView(R.id.iv_relocation).setVisibility(View.GONE);
           viewHolder.<TextView>getView(R.id.tv_relocation).setVisibility(View.GONE);
       }

       viewHolder.<LinearLayout>getView(R.id.ll_name_address).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (listListener!=null){
                   listListener.onClickListItem( item,  position);
               }
           }
       });

    }
    public interface OnClickCommunityListListener{
        /**
         * 定位成功后点击重新定位
         */
        void onListClickRelocation();

        /**
         * 点击列表
         * @param item
         * @param position
         */
        void onClickListItem(ModelCoummnityList item, int position);
    }
}
