package com.huacheng.huiservers.ui.base;

/**
 * @author Created by changyadong on 2020/11/7
 * @description
 */

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MyAdapter<T> extends BaseAdapter {


    public List<T> dataList;


    public void addData(List<T> dataList) {

        if (dataList != null) {
            if (this.dataList == null) this.dataList = new ArrayList<>();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (dataList != null) {
            dataList.clear();
            notifyDataSetChanged();
        }
    }

    public void remove(T t) {
        dataList.remove(t);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public  String getEmptyMsg(){

        return "暂无数据";
    };

}
