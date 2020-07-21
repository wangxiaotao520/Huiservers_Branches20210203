package com.huacheng.huiservers.model;

/**
 * Description: 2.0服务购买须知
 * created by wangxiaotao
 * 2020/7/13 0013 10:05
 */
public class ModelServicePurchaseNote {


    /**
     * c_name : 预约须知
     * c_text : 下单后请联系商家提前预约服务
     */

    private String c_name;
    private String c_text;

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_text() {
        return c_text;
    }

    public void setC_text(String c_text) {
        this.c_text = c_text;
    }
}
