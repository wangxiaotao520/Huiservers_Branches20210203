package com.huacheng.huiservers.model;

/**
 * Description: Vip开通记录
 * created by wangxiaotao
 * 2020/11/30 0030 08:28
 */
public class ModelVipRecord {

    /**
     * id : 1
     * uid : 2399
     * vip_id : 1
     * vip_name : 月卡
     * vip_price : 9.80
     * sart_time : 1606838400
     * end_time : 1609516800
     * addtime : 1606903189
     * status_cn : 生效中
     */

    private String id;
    private String uid;
    private String vip_id;
    private String vip_name;
    private String vip_price;
    private String sart_time;
    private String end_time;
    private String addtime;
    private String status_cn;

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

    public String getVip_id() {
        return vip_id;
    }

    public void setVip_id(String vip_id) {
        this.vip_id = vip_id;
    }

    public String getVip_name() {
        return vip_name;
    }

    public void setVip_name(String vip_name) {
        this.vip_name = vip_name;
    }

    public String getVip_price() {
        return vip_price;
    }

    public void setVip_price(String vip_price) {
        this.vip_price = vip_price;
    }

    public String getSart_time() {
        return sart_time;
    }

    public void setSart_time(String sart_time) {
        this.sart_time = sart_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getStatus_cn() {
        return status_cn;
    }

    public void setStatus_cn(String status_cn) {
        this.status_cn = status_cn;
    }
}
