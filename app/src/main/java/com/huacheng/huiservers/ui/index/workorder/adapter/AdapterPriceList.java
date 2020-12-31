package com.huacheng.huiservers.ui.index.workorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.huacheng.huiservers.R;
import com.huacheng.huiservers.dialog.WorkOrderCatStatardDialog;
import com.huacheng.huiservers.model.ModelWorkPersonalCatItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * created by wangxiaotao
 * 2019/4/10 0010 下午 3:14
 */
public class AdapterPriceList extends BaseExpandableListAdapter {
    private Context mcontext;
    List<ModelWorkPersonalCatItem> mDatas = new ArrayList<>();
//    public String[] groupString = {"射手", "辅助", "坦克", "法师"};
//    public String[][] childString = {
//            {"孙尚香", "后羿", "马可波罗", "狄仁杰"},
//            {"孙膑", "蔡文姬", "鬼谷子", "杨玉环"},
//            {"张飞", "廉颇", "牛魔", "项羽"},
//            {"诸葛亮", "王昭君", "安琪拉", "干将"}
//
//    };
//    public float[][] childPrice={
//        {0, 0, 0, 0},
//        {0, 0, 0, 0},
//        {0, 0, 0, 0},
//        {0, 0, 0, 0}};


    public AdapterPriceList(Context mcontext,List<ModelWorkPersonalCatItem> mDatas) {
        this.mcontext = mcontext;
        // 重写构造，把数据传进来
        this.mDatas=mDatas;
    }

    @Override
    // 获取分组的个数
    public int getGroupCount() {
        return mDatas.size();
    }

    //获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        if (mDatas.get(groupPosition).getLists()!=null){
            return mDatas.get(groupPosition).getLists().size();
        }else {
            return  0;
        }

    }

    //        获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return  mDatas.get(groupPosition);
    }

    //获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mDatas.get(groupPosition).getLists()!=null){
            return mDatas.get(groupPosition).getLists().get(childPosition);
        }else {
            return null;
        }

    }

    //获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    @Override
    public boolean hasStableIds() {
        return true;
    }
    /**
     *
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded 该组是展开状态还是伸缩状态
     * @param convertView 重用已有的视图对象
     * @param parent 返回的视图对象始终依附于的视图组
     */
// 获取显示指定分组的视图
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_type,parent,false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
            groupViewHolder.tv_statard = (TextView)convertView.findViewById(R.id.tv_statard);
            groupViewHolder.view_line = convertView.findViewById(R.id.view_line);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder)convertView.getTag();
        }
        groupViewHolder.tv_title.setText(mDatas.get(groupPosition).getName());
        groupViewHolder.tv_statard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WorkOrderCatStatardDialog(mcontext,mDatas.get(groupPosition).getContent()+"").show();
            }
        });
        groupViewHolder.tv_statard.setVisibility(View.GONE);
        groupViewHolder.tv_statard.setTextColor(mcontext.getResources().getColor(R.color.title_third_color));
        groupViewHolder.view_line.setVisibility(View.VISIBLE);
        return convertView;
    }
    /**
     *
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild 子元素是否处于组中的最后一个
     * @param convertView 重用已有的视图(View)对象
     * @param parent 返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
     *      android.view.ViewGroup)
     */

    //取得显示给定分组给定子位置的数据用的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item_work_price,parent,false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            childViewHolder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            convertView.setTag(childViewHolder);

        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        if (mDatas.get(groupPosition).getLists()!=null) {
            childViewHolder.tv_name.setText(mDatas.get(groupPosition).getLists().get(childPosition).getName());
            childViewHolder.tv_price.setText("¥ "+mDatas.get(groupPosition).getLists().get(childPosition).getPrice());
        }else {
            childViewHolder.tv_name.setText("");
            childViewHolder.tv_price.setText("");
        }

        return convertView;
    }

    //指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView tv_title;
        TextView tv_statard;
        View view_line;
    }

    static class ChildViewHolder {
        TextView tv_name;
        TextView tv_price;

    }
}
