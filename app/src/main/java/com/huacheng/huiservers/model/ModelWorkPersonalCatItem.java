package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 自用工单类型分类列表
 * created by wangxiaotao
 * 2018/12/13 0013 上午 10:32
 */
public class ModelWorkPersonalCatItem implements Serializable{

    private String id;
    private String pid;
    private String type_name;
    private String company_id;
    private String type_price;
    private String type_content;
    private String entry_fee;

    //第二版工单

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getType_price() {
        return type_price;
    }

    public void setType_price(String type_price) {
        this.type_price = type_price;
    }

    public String getType_content() {
        return type_content;
    }

    public void setType_content(String type_content) {
        this.type_content = type_content;
    }

    public String getEntry_fee() {
        return entry_fee;
    }

    public void setEntry_fee(String entry_fee) {
        this.entry_fee = entry_fee;
    }
}
