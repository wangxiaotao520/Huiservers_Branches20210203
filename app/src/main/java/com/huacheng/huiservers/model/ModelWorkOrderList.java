package com.huacheng.huiservers.model;

/**
 * Description:工单List
 * Author: badge
 * Create: 2018/12/13 19:34
 */
public class ModelWorkOrderList {

    private String id;
    private String work_type;
    private String type_name;
    private String release_at;
    private String entry_fee;
    private String prepay_is_pay;
    private String work_status;
    private String evaluate_status;
    private String work_type_cn;
    private String work_status_cn;

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

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getRelease_at() {
        return release_at;
    }

    public void setRelease_at(String release_at) {
        this.release_at = release_at;
    }

    public String getEntry_fee() {
        return entry_fee;
    }

    public void setEntry_fee(String entry_fee) {
        this.entry_fee = entry_fee;
    }

    public String getPrepay_is_pay() {
        return prepay_is_pay;
    }

    public void setPrepay_is_pay(String prepay_is_pay) {
        this.prepay_is_pay = prepay_is_pay;
    }

    public String getWork_status() {
        return work_status;
    }

    public void setWork_status(String work_status) {
        this.work_status = work_status;
    }

    public String getEvaluate_status() {
        return evaluate_status;
    }

    public void setEvaluate_status(String evaluate_status) {
        this.evaluate_status = evaluate_status;
    }

    public String getWork_type_cn() {
        return work_type_cn;
    }

    public void setWork_type_cn(String work_type_cn) {
        this.work_type_cn = work_type_cn;
    }

    public String getWork_status_cn() {
        return work_status_cn;
    }

    public void setWork_status_cn(String work_status_cn) {
        this.work_status_cn = work_status_cn;
    }
}
