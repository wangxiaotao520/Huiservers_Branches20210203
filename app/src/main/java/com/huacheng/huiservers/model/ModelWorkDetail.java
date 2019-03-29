package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 订单详情页
 * created by wangxiaotao
 * 2018/12/11 0011 下午 6:35
 */
public class ModelWorkDetail implements Serializable{
    private int is_pay=1;//是否支付 （1未支付，2已支付）
    private int send_status =0;//是否增派：默认否0, 1为是
    private int work_type = 1 ; //工单类型（1为自用报修，2为公共报修）
    private int evaluate_status =0;//是否评价：默认否0, 1为已评价


    private String id;
    private String order_number;
    private String type_name;
    private String username;
    private String userphone;
    private String address;
    private String content;
    private String release_at;
    private long distribute_at;
    private long send_at;
    private String send_content;
    private String total_fee_at;
    private String labor_cost;
    private String material_cost;
    private String total_fee_content;
    private double total_fee;
    private String complete_at;
    private String degree;
    private String complete_content;
    private int work_status;
    private String order_time;
    private String order_total_time;
    private String level;
    private String evaluate_content;
    private double entry_fee;
    private List<Send_Distribute_UserBean> distributeUser;
    private List<Send_Distribute_UserBean> sendUser;
    private List<Repair_CompleteBean> repairImg;
    private List<Repair_CompleteBean> completeImg;
    private ModelWorkDetail score;
    private String uid;
    private String contact;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEvaluate_content() {
        return evaluate_content;
    }

    public void setEvaluate_content(String evaluate_content) {
        this.evaluate_content = evaluate_content;
    }

    public int getWork_status() {
        return work_status;
    }

    public void setWork_status(int work_status) {
        this.work_status = work_status;
    }

    public ModelWorkDetail getScore() {
        return score;
    }

    public void setScore(ModelWorkDetail score) {
        this.score = score;
    }

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

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRelease_at() {
        return release_at;
    }

    public void setRelease_at(String release_at) {
        this.release_at = release_at;
    }

    public long getDistribute_at() {
        return distribute_at;
    }

    public void setDistribute_at(long distribute_at) {
        this.distribute_at = distribute_at;
    }

    public long getSend_at() {
        return send_at;
    }

    public void setSend_at(long send_at) {
        this.send_at = send_at;
    }

    public String getSend_content() {
        return send_content;
    }

    public void setSend_content(String send_content) {
        this.send_content = send_content;
    }

    public String getTotal_fee_at() {
        return total_fee_at;
    }

    public void setTotal_fee_at(String total_fee_at) {
        this.total_fee_at = total_fee_at;
    }

    public String getLabor_cost() {
        return labor_cost;
    }

    public void setLabor_cost(String labor_cost) {
        this.labor_cost = labor_cost;
    }

    public String getMaterial_cost() {
        return material_cost;
    }

    public void setMaterial_cost(String material_cost) {
        this.material_cost = material_cost;
    }

    public String getTotal_fee_content() {
        return total_fee_content;
    }

    public void setTotal_fee_content(String total_fee_content) {
        this.total_fee_content = total_fee_content;
    }

    public double getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(double total_fee) {
        this.total_fee = total_fee;
    }

    public String getComplete_at() {
        return complete_at;
    }

    public void setComplete_at(String complete_at) {
        this.complete_at = complete_at;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getComplete_content() {
        return complete_content;
    }

    public void setComplete_content(String complete_content) {
        this.complete_content = complete_content;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOrder_total_time() {
        return order_total_time;
    }

    public void setOrder_total_time(String order_total_time) {
        this.order_total_time = order_total_time;
    }

    public List<Send_Distribute_UserBean> getDistributeUser() {
        return distributeUser;
    }

    public void setDistributeUser(List<Send_Distribute_UserBean> distributeUser) {
        this.distributeUser = distributeUser;
    }

    public List<Send_Distribute_UserBean> getSendUser() {
        return sendUser;
    }

    public void setSendUser(List<Send_Distribute_UserBean> sendUser) {
        this.sendUser = sendUser;
    }

    public List<Repair_CompleteBean> getRepairImg() {
        return repairImg;
    }

    public void setRepairImg(List<Repair_CompleteBean> repairImg) {
        this.repairImg = repairImg;
    }

    public List<Repair_CompleteBean> getCompleteImg() {
        return completeImg;
    }

    public void setCompleteImg(List<Repair_CompleteBean> completeImg) {
        this.completeImg = completeImg;
    }

    public static class Send_Distribute_UserBean {
        /**
         * user_id : 110
         * user_status : 2
         * name : 晶晶
         * head_img : huacheng_property/property/user/18/11/24/5bf906827cbe2.jpg
         */

        private String user_id;
        private String user_status;
        private String name;
        private String head_img;

        private String phone;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_status() {
            return user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

    }

    public static class Repair_CompleteBean {
        /**
         * id : 23
         * img_path : huacheng/work/18/12/12/
         * img_name : 5c11113e07324.jpg
         * work_id : 1
         * img_status : 1
         * addtime : 1544622398
         */

        private String id;
        private String img_path;
        private String img_name;
        private String work_id;
        private String img_status;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getImg_name() {
            return img_name;
        }

        public void setImg_name(String img_name) {
            this.img_name = img_name;
        }

        public String getWork_id() {
            return work_id;
        }

        public void setWork_id(String work_id) {
            this.work_id = work_id;
        }

        public String getImg_status() {
            return img_status;
        }

        public void setImg_status(String img_status) {
            this.img_status = img_status;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }

    public int getWork_type() {
        return work_type;
    }

    public void setWork_type(int work_type) {
        this.work_type = work_type;
    }


    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }



    public int getSend_status() {
        return send_status;
    }

    public void setSend_status(int send_status) {
        this.send_status = send_status;
    }

    public int getEvaluate_status() {
        return evaluate_status;
    }

    public void setEvaluate_status(int evaluate_status) {
        this.evaluate_status = evaluate_status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getEntry_fee() {
        return entry_fee;
    }

    public void setEntry_fee(double entry_fee) {
        this.entry_fee = entry_fee;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
