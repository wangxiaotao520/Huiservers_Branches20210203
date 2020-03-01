package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：电子通行证
 * 时间：2020/2/22 16:17
 * created by DFF
 */
public class ModelPermit implements Serializable {
    private String id;
    private String title;
    private String type;
    private String idX;
    private String pass_check_set_id;
    private String typeX;
    private String community_id;
    private String community_name;
    private String room_id;
    private String room_info;
    private String owner_name;
    private String id_card;
    private String phone;
    private String car_number;
    private String address;
    private String note;
    private String status;
    private String uid;
    private String qr_code;
    private String created;
    private String updated;
    private String titleX;
    private String access_type;
    private String access_time;
    private List<ModelPermit> pca_list;
    private List<ModelPermit> pc_list;
    private int totalPages;

    public List<ModelPermit> getPc_list() {
        return pc_list;
    }

    public void setPc_list(List<ModelPermit> pc_list) {
        this.pc_list = pc_list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getIdX() {
        return idX;
    }

    public void setIdX(String idX) {
        this.idX = idX;
    }

    public String getPass_check_set_id() {
        return pass_check_set_id;
    }

    public void setPass_check_set_id(String pass_check_set_id) {
        this.pass_check_set_id = pass_check_set_id;
    }

    public String getTypeX() {
        return typeX;
    }

    public void setTypeX(String typeX) {
        this.typeX = typeX;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_info() {
        return room_info;
    }

    public void setRoom_info(String room_info) {
        this.room_info = room_info;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getTitleX() {
        return titleX;
    }

    public void setTitleX(String titleX) {
        this.titleX = titleX;
    }

    public String getAccess_type() {
        return access_type;
    }

    public void setAccess_type(String access_type) {
        this.access_type = access_type;
    }

    public String getAccess_time() {
        return access_time;
    }

    public void setAccess_time(String access_time) {
        this.access_time = access_time;
    }

    public List<ModelPermit> getPca_list() {
        return pca_list;
    }

    public void setPca_list(List<ModelPermit> pca_list) {
        this.pca_list = pca_list;
    }
}
