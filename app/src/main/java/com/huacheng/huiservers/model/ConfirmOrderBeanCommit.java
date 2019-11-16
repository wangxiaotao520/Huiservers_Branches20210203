package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 下单计算金额的bean(用于提交)
 * created by wangxiaotao
 * 2019/11/15 0015 下午 6:52
 */
public class ConfirmOrderBeanCommit implements Serializable{

    private String sign;
    private String dis_fee;
    private String half_amount;
    private String merchant_id;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDis_fee() {
        return dis_fee;
    }

    public void setDis_fee(String dis_fee) {
        this.dis_fee = dis_fee;
    }

    public String getHalf_amount() {
        return half_amount;
    }

    public void setHalf_amount(String half_amount) {
        this.half_amount = half_amount;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }



}
