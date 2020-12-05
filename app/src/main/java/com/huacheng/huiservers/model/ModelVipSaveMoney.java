package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description: 累计省钱model
 * created by wangxiaotao
 * 2020/11/30 0030 11:07
 */
public class ModelVipSaveMoney {
    private String save_pice;

    private List <ModelVipSaveMoney> save_pice_log;
    private int total_Pages ;

    /**
     * id : 1
     * uid : 2399
     * title : 购物抵扣
     * type : 1
     * oder_num : gw-15341564654
     * order_price : 100.00
     * vip_price : 90.00
     * save_pice : 10.00
     * content : 订单编号：gw-15341564654
     * addtime : 1607050065
     */

    private String id;
    private String uid;
    private String title;
    private String type;
    private String oder_num;
    private String order_price;
    private String vip_price;
    private String content;
    private String addtime;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOder_num() {
        return oder_num;
    }

    public void setOder_num(String oder_num) {
        this.oder_num = oder_num;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getVip_price() {
        return vip_price;
    }

    public void setVip_price(String vip_price) {
        this.vip_price = vip_price;
    }

    public String getSave_pice() {
        return save_pice;
    }

    public void setSave_pice(String save_pice) {
        this.save_pice = save_pice;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public List<ModelVipSaveMoney> getSave_pice_log() {
        return save_pice_log;
    }

    public void setSave_pice_log(List<ModelVipSaveMoney> save_pice_log) {
        this.save_pice_log = save_pice_log;
    }

    public int getTotal_Pages() {
        return total_Pages;
    }

    public void setTotal_Pages(int total_Pages) {
        this.total_Pages = total_Pages;
    }

}
