package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description:租售房列表/我的房屋列表
 * created by wangxiaotao
 * 2018/11/6 0006 下午 6:58
 */
public class HouseListBean implements Serializable {

    List<HouseRentDetail> list;
    public List<HouseRentDetail> getList() {
        return list;
    }

    public void setList(List<HouseRentDetail> list) {
        this.list = list;
    }
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
    private String unit_price;//租房-单价
    private String total_price;//售房价格

    private String administrator_img;
    private String head_img;
    private String status;

    private List<HouseListBean> houseList;
    private int CountPage;

    public String getAdministrator_img() {
        return administrator_img;
    }

    public void setAdministrator_img(String administrator_img) {
        this.administrator_img = administrator_img;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public List<HouseListBean> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<HouseListBean> houseList) {
        this.houseList = houseList;
    }

    public int getCountPage() {
        return CountPage;
    }

    public void setCountPage(int countPage) {
        CountPage = countPage;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
