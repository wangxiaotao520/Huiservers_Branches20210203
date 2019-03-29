package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description:通用Bean(工单)
 * Author: badge
 * Create: 2018/11/17 16:29
 */
public class ModelCommonCat implements Serializable{

    //公用
    private int id;
    private String c_name;
    private boolean is_selected;
    //自用


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel_name() {
        return c_name;
    }

    public void setLabel_name(String label_name) {
        this.c_name = label_name;
    }


    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }


}
