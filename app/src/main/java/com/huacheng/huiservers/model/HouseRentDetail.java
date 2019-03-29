package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：租赁详情
 * 时间：2018/11/9 08:46
 * created by DFF
 */
public class HouseRentDetail implements Serializable {

    //租房 //售房
    private String id;
    private String status;
    private String adminid;
    private String name;
    private String phone;
    private String houseid;
    private String community_name;
    private String room;
    private String office;
    private String kitchen;
    private String guard;
    private String floor;
    private String house_floor;
    private String area;
    private String unit_price;
    private String total_price;
    private String label_id;
    private String head_img;
    private String check_in;
    private String lease_term;
    private String elevator;
    private String content;
    private String pay_type;
    private String release_time;
    private String administrator_img;
    private int house_type;
    private List<HouseImgBean> house_img;
    private List<LabelBean> label;
    private List<HouseRentDetail> recommend;

    public int getHouse_type() {
        return house_type;
    }

    public void setHouse_type(int house_type) {
        this.house_type = house_type;
    }

    public List<HouseRentDetail> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<HouseRentDetail> recommend) {
        this.recommend = recommend;
    }

    public String getAdministrator_img() {
        return administrator_img;
    }

    public void setAdministrator_img(String administrator_img) {
        this.administrator_img = administrator_img;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public String getGuard() {
        return guard;
    }

    public void setGuard(String guard) {
        this.guard = guard;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getHouse_floor() {
        return house_floor;
    }

    public void setHouse_floor(String house_floor) {
        this.house_floor = house_floor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getLabel_id() {
        return label_id;
    }

    public void setLabel_id(String label_id) {
        this.label_id = label_id;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getLease_term() {
        return lease_term;
    }

    public void setLease_term(String lease_term) {
        this.lease_term = lease_term;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public List<HouseImgBean> getHouse_img() {
        return house_img;
    }

    public void setHouse_img(List<HouseImgBean> house_img) {
        this.house_img = house_img;
    }

    public List<LabelBean> getLabel() {
        return label;
    }

    public void setLabel(List<LabelBean> label) {
        this.label = label;
    }

    public static class HouseImgBean {
        /**
         * id : 56
         * h_id : 3
         * house_imgs : huacheng_property/property/18/11/08/5be3fe8ef4226.jpg
         */

        private String id;
        private String h_id;
        private String path;
        private String house_imgs_name;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getHouse_imgs_name() {
            return house_imgs_name;
        }

        public void setHouse_imgs_name(String house_imgs_name) {
            this.house_imgs_name = house_imgs_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getH_id() {
            return h_id;
        }

        public void setH_id(String h_id) {
            this.h_id = h_id;
        }

    }

    public static class LabelBean {


        private String label_name;

        public String getLabel_name() {
            return label_name;
        }

        public void setLabel_name(String label_name) {
            this.label_name = label_name;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

}
