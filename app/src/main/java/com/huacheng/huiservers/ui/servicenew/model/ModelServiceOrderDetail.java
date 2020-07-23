package com.huacheng.huiservers.ui.servicenew.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description:服务订单详情
 * created by wangxiaotao
 * 2018/9/6 0006 上午 9:46
 */
public class ModelServiceOrderDetail implements Serializable{

    private String id;
    private String order_number;
    private String status;
    private long addtime;
    private String address;
    private String description;
    private String w_id;
    private String w_fullname;
    private String s_id;
    private String s_name;
    private String title_img;
    private String mobile;
    /**
     * contacts : 王晓涛
     * addtime : 0
     * number : 1
     * amount : 125.00
     * pay_type : alipay
     * i_id : 6
     * telphone : 15392547490
     * record : [{"id":"11","status":"1","order_id":"204","describe":"商家已收到您的订单，正在指派上门服务人员","addtime":"1594608345","worker_id":"0","worker_name":"","worker_number":"","score":"","evaluate":""}]
     */

    private String contacts;
    private String number;
    private String amount;
    private String pay_type;
    private String i_id;
    private String telphone;
    private List<RecordBean> record;
    private List<RecordBean> refund_record;
    /**
     * addtime : 1591775573
     * refunds_number : 1
     * refunds_price : 95.00
     * status_name : 退款审核中
     * record_reason : 商户原因 - 买多/买错/计划有变
     * refund_record : [{"id":"10","status":"1","order_id":"204","describe":"您的退款申请已提交，商家将在1-2个工作日内进行审核","addtime":"1595384215","telphone":"15392547490"}]
     */

    private String refunds_number;
    private String refunds_price;
    private String status_name;
    private String record_reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getW_id() {
        return w_id;
    }

    public void setW_id(String w_id) {
        this.w_id = w_id;
    }

    public String getW_fullname() {
        return w_fullname;
    }

    public void setW_fullname(String w_fullname) {
        this.w_fullname = w_fullname;
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

    public String getTitle_img() {
        return title_img;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getI_id() {
        return i_id;
    }

    public void setI_id(String i_id) {
        this.i_id = i_id;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public String getRefunds_number() {
        return refunds_number;
    }

    public void setRefunds_number(String refunds_number) {
        this.refunds_number = refunds_number;
    }

    public String getRefunds_price() {
        return refunds_price;
    }

    public void setRefunds_price(String refunds_price) {
        this.refunds_price = refunds_price;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getRecord_reason() {
        return record_reason;
    }

    public void setRecord_reason(String record_reason) {
        this.record_reason = record_reason;
    }

    public List<RecordBean> getRefund_record() {
        return refund_record;
    }

    public void setRefund_record(List<RecordBean> refund_record) {
        this.refund_record = refund_record;
    }

    public static class RecordBean implements Serializable{
        /**
         * id : 11
         * status : 1
         * order_id : 204
         * describe : 商家已收到您的订单，正在指派上门服务人员
         * addtime : 1594608345
         * worker_id : 0
         * worker_name :
         * worker_number :
         * score :
         * evaluate :
         */


        private String id;
        private String status;
        private String order_id;
        private String describe;
        private String addtime;
        private String worker_id;
        private String worker_name;
        private String worker_number;
        private int score;
        private String evaluate;

        private String telphone;

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getId() {
            return id;
        }

        public void setId(String idX) {
            this.id = idX;
        }

        public String getStatus() {
            return status;
        }

        public void setStatusX(String status) {
            this.status = status;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getWorker_id() {
            return worker_id;
        }

        public void setWorker_id(String worker_id) {
            this.worker_id = worker_id;
        }

        public String getWorker_name() {
            return worker_name;
        }

        public void setWorker_name(String worker_name) {
            this.worker_name = worker_name;
        }

        public String getWorker_number() {
            return worker_number;
        }

        public void setWorker_number(String worker_number) {
            this.worker_number = worker_number;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(String evaluate) {
            this.evaluate = evaluate;
        }
    }


}
