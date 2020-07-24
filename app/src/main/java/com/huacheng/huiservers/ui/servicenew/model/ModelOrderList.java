package com.huacheng.huiservers.ui.servicenew.model;

/**
 * 类描述：
 * 时间：2018/9/5 20:27
 * created by DFF
 */
public class ModelOrderList {

    private String dfw;
    private String dpj;
    private String wc;
    private String id;
    private String uid;
    private String address;
    private String s_id;
    private String s_name;
    private String i_name;
    private String status;
    private String score;
    private String evaluate;
    private String evaluatime;
    private String description;
    private String title_img;


    private String amount;
    private int total_Pages;

    private int event_type;//0 申请退款 1取消退款 2评价 3完成服务 4删除订单
    /**
     * number : 2
     * worker_confirm : 0
     * user_confirm : 0
     */

    private String number;
    private String worker_confirm;
    private String user_confirm;


    public String getDfw() {
        return dfw;
    }

    public void setDfw(String dfw) {
        this.dfw = dfw;
    }

    public String getDpj() {
        return dpj;
    }

    public void setDpj(String dpj) {
        this.dpj = dpj;
    }

    public String getWc() {
        return wc;
    }

    public void setWc(String wc) {
        this.wc = wc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getEvaluatime() {
        return evaluatime;
    }

    public void setEvaluatime(String evaluatime) {
        this.evaluatime = evaluatime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle_img() {
        return title_img;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }

    public int getTotal_Pages() {
        return total_Pages;
    }

    public void setTotal_Pages(int total_Pages) {
        this.total_Pages = total_Pages;
    }

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWorker_confirm() {
        return worker_confirm;
    }

    public void setWorker_confirm(String worker_confirm) {
        this.worker_confirm = worker_confirm;
    }

    public String getUser_confirm() {
        return user_confirm;
    }

    public void setUser_confirm(String user_confirm) {
        this.user_confirm = user_confirm;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
