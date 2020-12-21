package com.huacheng.huiservers.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Created by changyadong on 2020/12/19
 * @description
 */
public class ChargeRecord {

    /**
     * status : 1
     * msg : 获取数据成功！
     * data : [{"id":"2","order_number":"20000000001","pay_time":"1608341022","order_id":"18,20,36","mergeMoney":"5.00","room_id":"1","void":"1","refund":"0","company_id":"2","roomInfo":"1-1-101","orderList":[[{"id":"18","chargeFrom":"1607344315","chargeEnd":"1607388348","refund":"0","refund_status":"0","billPrice":"1.00","category_id":"2","category_name":"水费"},{"id":"20","chargeFrom":"1607388348","chargeEnd":"1608083714","refund":"0","refund_status":"0","billPrice":"1.00","category_id":"2","category_name":"水费"}],[{"id":"36","chargeFrom":"1580486400","chargeEnd":"1596211199","refund":"0","refund_status":"0","billPrice":"3.00","category_id":"1","category_name":"物业费"}]],"refund_money":0}]
     * dialog :
     */

    private int status;
    private String msg;
    private String dialog;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * order_number : 20000000001
         * pay_time : 1608341022
         * order_id : 18,20,36
         * mergeMoney : 5.00
         * room_id : 1
         * void : 1
         * refund : 0
         * company_id : 2
         * roomInfo : 1-1-101
         * orderList : [[{"id":"18","chargeFrom":"1607344315","chargeEnd":"1607388348","refund":"0","refund_status":"0","billPrice":"1.00","category_id":"2","category_name":"水费"},{"id":"20","chargeFrom":"1607388348","chargeEnd":"1608083714","refund":"0","refund_status":"0","billPrice":"1.00","category_id":"2","category_name":"水费"}],[{"id":"36","chargeFrom":"1580486400","chargeEnd":"1596211199","refund":"0","refund_status":"0","billPrice":"3.00","category_id":"1","category_name":"物业费"}]]
         * refund_money : 0
         */

        private String id;
        private String order_number;
        private String pay_time;
        private String order_id;
        private String mergeMoney;
        private String room_id;
        @SerializedName("void")
        private String voidX;
        private String refund;
        private String company_id;
        private String roomInfo;
        private String refund_money;
        private List<List<OrderListBean>> orderList;

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

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getMergeMoney() {
            return mergeMoney;
        }

        public void setMergeMoney(String mergeMoney) {
            this.mergeMoney = mergeMoney;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getVoidX() {
            return voidX;
        }

        public void setVoidX(String voidX) {
            this.voidX = voidX;
        }

        public String getRefund() {
            return refund;
        }

        public void setRefund(String refund) {
            this.refund = refund;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getRoomInfo() {
            return roomInfo;
        }

        public void setRoomInfo(String roomInfo) {
            this.roomInfo = roomInfo;
        }

        public String getRefund_money() {
            return refund_money;
        }

        public void setRefund_money(String  refund_money) {
            this.refund_money = refund_money;
        }

        public List<List<OrderListBean>> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<List<OrderListBean>> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            /**
             * id : 18
             * chargeFrom : 1607344315
             * chargeEnd : 1607388348
             * refund : 0
             * refund_status : 0
             * billPrice : 1.00
             * category_id : 2
             * category_name : 水费
             */

            private String id;
            private String chargeFrom;
            private String chargeEnd;
            private String refund;
            private String refund_status;
            private String billPrice;
            private String category_id;
            private String category_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getChargeFrom() {
                return chargeFrom;
            }

            public void setChargeFrom(String chargeFrom) {
                this.chargeFrom = chargeFrom;
            }

            public String getChargeEnd() {
                return chargeEnd;
            }

            public void setChargeEnd(String chargeEnd) {
                this.chargeEnd = chargeEnd;
            }

            public String getRefund() {
                return refund;
            }

            public void setRefund(String refund) {
                this.refund = refund;
            }

            public String getRefund_status() {
                return refund_status;
            }

            public void setRefund_status(String refund_status) {
                this.refund_status = refund_status;
            }

            public String getBillPrice() {
                return billPrice;
            }

            public void setBillPrice(String billPrice) {
                this.billPrice = billPrice;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }
        }
    }
}
