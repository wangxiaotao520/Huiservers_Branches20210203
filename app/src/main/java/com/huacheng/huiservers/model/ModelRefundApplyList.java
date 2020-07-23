package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 2.0 服务
 * created by wangxiaotao
 * 2020/7/21 0021 16:36
 */
public class ModelRefundApplyList implements Serializable {

    private String id;
    private String s_name;
    private String i_name;
    private String number;
    private String door_price;
    private String door_display;
    private String refunds_price;
    private String pay_type;
    private String amount;
    private List<RefundMyBean> refund_my;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDoor_price() {
        return door_price;
    }

    public void setDoor_price(String door_price) {
        this.door_price = door_price;
    }

    public String getDoor_display() {
        return door_display;
    }

    public void setDoor_display(String door_display) {
        this.door_display = door_display;
    }

    public String getRefunds_price() {
        return refunds_price;
    }

    public void setRefunds_price(String refunds_price) {
        this.refunds_price = refunds_price;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<RefundMyBean> getRefund_my() {
        return refund_my;
    }

    public void setRefund_my(List<RefundMyBean> refund_my) {
        this.refund_my = refund_my;
    }

    public static class RefundMyBean implements Serializable{
        /**
         * id : 9
         * c_alias : HC_refund_my
         * c_name : 买多/买错/计划有变
         */

        private String id;
        private String c_alias;
        private String c_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getC_alias() {
            return c_alias;
        }

        public void setC_alias(String c_alias) {
            this.c_alias = c_alias;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }
    }
}
