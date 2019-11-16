package com.huacheng.huiservers.ui.shop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 购物车列表
 * created by wangxiaotao
 * 2019/11/16 0016 下午 3:57
 */
public class ShopCartBean implements Serializable{
    String id ;
    String merchant_name;

    List <DataBean> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public List <DataBean> getList() {
        return list;
    }

    public void setList(List <DataBean> list) {
        this.list = list;
    }

}
