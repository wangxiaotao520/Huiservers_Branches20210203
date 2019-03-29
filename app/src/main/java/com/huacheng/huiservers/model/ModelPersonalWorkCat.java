package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Description: 分类
 * created by wangxiaotao
 * 2018/12/13 0013 下午 6:13
 */
public class ModelPersonalWorkCat implements Serializable{

    private String id;
    private String type_name;
    private ArrayList<ModelWorkPersonalCatItem> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public ArrayList<ModelWorkPersonalCatItem> getList() {
        return list;
    }

    public void setList(ArrayList<ModelWorkPersonalCatItem> list) {
        this.list = list;
    }
}
