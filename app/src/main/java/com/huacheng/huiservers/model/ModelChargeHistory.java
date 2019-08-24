package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 充电记录
 * created by wangxiaotao
 * 2019/8/24 0024 上午 9:30
 */
public class ModelChargeHistory  implements Serializable {
    private int totalPages;

    private List<ModelChargeHistory> list;

    /**
     * id : 24
     * pay_time : 1566546404
     * status : 1
     * status_cn : 进行中
     * times : 10
     * price : 2.00
     */

    private String id;
    private String pay_time;
    private String status;
    private String status_cn;
    private String times;
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_cn() {
        return status_cn;
    }

    public void setStatus_cn(String status_cn) {
        this.status_cn = status_cn;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelChargeHistory> getList() {
        return list;
    }

    public void setList(List<ModelChargeHistory> list) {
        this.list = list;
    }
}
