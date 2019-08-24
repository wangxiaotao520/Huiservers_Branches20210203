package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description: 充电消息
 * created by wangxiaotao
 * 2019/8/24 0024 上午 9:51
 */
public class ModelChargeMessage {
    private int totalPages;



    private List<ModelChargeMessage> list;
    /**
     * id : 2
     * uid : 2399
     * type : 2
     * order_num : yx-2399190822173205
     * start_time : 1566459000
     * end_time : 1566466760
     * times : 5
     * gtel : 68000005100
     * td : 1
     * gtel_cn : 商贸测试
     * price : 1元/5小时
     * order_price : 1.00
     * reality_price : 0.43
     * reality_times : 2.16
     * cancel_price : 0.57
     * addtime : 1566466760
     * end_reason : 用户主动结束充电!
     */

    private String id;
    private String uid;
    private String type;
    private String order_num;
    private String start_time;
    private String end_time;
    private String times;
    private String gtel;
    private String td;
    private String gtel_cn;
    private String price;
    private String order_price;
    private String reality_price;
    private String reality_times;
    private String cancel_price;
    private String addtime;
    private String end_reason;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelChargeMessage> getList() {
        return list;
    }

    public void setList(List<ModelChargeMessage> list) {
        this.list = list;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getGtel() {
        return gtel;
    }

    public void setGtel(String gtel) {
        this.gtel = gtel;
    }

    public String getTd() {
        return td;
    }

    public void setTd(String td) {
        this.td = td;
    }

    public String getGtel_cn() {
        return gtel_cn;
    }

    public void setGtel_cn(String gtel_cn) {
        this.gtel_cn = gtel_cn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getReality_price() {
        return reality_price;
    }

    public void setReality_price(String reality_price) {
        this.reality_price = reality_price;
    }

    public String getReality_times() {
        return reality_times;
    }

    public void setReality_times(String reality_times) {
        this.reality_times = reality_times;
    }

    public String getCancel_price() {
        return cancel_price;
    }

    public void setCancel_price(String cancel_price) {
        this.cancel_price = cancel_price;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getEnd_reason() {
        return end_reason;
    }

    public void setEnd_reason(String end_reason) {
        this.end_reason = end_reason;
    }
}
