package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 工单提交成功
 * created by wangxiaotao
 * 2018/12/13 0013 下午 7:25
 */
public class ModelWorkCommitSuccess implements Serializable {


    private String id;
    private String work_type;
    private String order_number;
    private String prepay_oid;
    private double entry_fee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getPrepay_oid() {
        return prepay_oid;
    }

    public void setPrepay_oid(String prepay_oid) {
        this.prepay_oid = prepay_oid;
    }

    public double getEntry_fee() {
        return entry_fee;
    }

    public void setEntry_fee(double entry_fee) {
        this.entry_fee = entry_fee;
    }
}
