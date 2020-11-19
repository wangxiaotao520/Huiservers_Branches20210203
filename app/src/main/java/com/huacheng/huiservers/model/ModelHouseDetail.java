package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：房屋详情
 * 时间：2020/9/18 15:55
 * created by DFF
 */
public class ModelHouseDetail implements Serializable {

    private ModelMyHouseList list;
    private ModelHouseDetail info;
    private ModelHouseDetail audit;
    private List<ModelHouseDetail> img;
    private ModelHouseDetail operation;
    private List<ModelMyHouseList> house_contract_img;
    private ModelHouseDetail log;
    private List<ModelHouseDetail> view;
    private List<ModelHouseDetail> contact;
    private String id;
    private String age;
    private String age_limit;
    private String property_state;
    private String state;
    private int architecture;
    private String decorating;
    private String lift;
    private String lift_cn;
    private int buy_time;
    private String houses_id;
    private String img_url;
    private String title;
    private String property_state_cn;
    private String state_cn;
    private String architecture_cn;
    private String decorating_cn;
    private String buy_time_cn;
    private String view_count;
    private String contact_count;
    private String num;
    private String check_in_cn;
    private String tenancy;

    /**
     * name : 赵耀
     * phone : 13133449558
     * enter_time : 2020
     * expire_time : 2021
     * identity_img1 : /web_img/huacheng/house_organization/operation/5f642bb90f23f.png
     * identity_img2 : /web_img/huacheng/house_organization/operation/5f642bb90ffcb.png
     * uid : 10
     * status : 1
     * house_contract_img : [{"id":"1","img":"/web_img/huacheng/house_organization/operation/5f642fc889bf7.png","houses_id":"49"},{"id":"2","img":"/web_img/huacheng/house_organization/operation/5f642fc88a73a.png","houses_id":"49"}]
     */
    private String name;
    private String phone;
    private String mobile;
    private String enter_time;
    private String expire_time;
    private String identity_img1;
    private String identity_img2;
    private String uid;
    private String status;
    private String avatars;

    public String getTenancy() {
        return tenancy;
    }

    public void setTenancy(String tenancy) {
        this.tenancy = tenancy;
    }

    public String getLift_cn() {
        return lift_cn;
    }

    public void setLift_cn(String lift_cn) {
        this.lift_cn = lift_cn;
    }

    public String getCheck_in_cn() {
        return check_in_cn;
    }

    public void setCheck_in_cn(String check_in_cn) {
        this.check_in_cn = check_in_cn;
    }

    public String getAvatars() {
        return avatars;
    }

    public void setAvatars(String avatars) {
        this.avatars = avatars;
    }

    public ModelHouseDetail getLog() {
        return log;
    }

    public void setLog(ModelHouseDetail log) {
        this.log = log;
    }

    public List<ModelHouseDetail> getView() {
        return view;
    }

    public void setView(List<ModelHouseDetail> view) {
        this.view = view;
    }

    public List<ModelHouseDetail> getContact() {
        return contact;
    }

    public void setContact(List<ModelHouseDetail> contact) {
        this.contact = contact;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getContact_count() {
        return contact_count;
    }

    public void setContact_count(String contact_count) {
        this.contact_count = contact_count;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ModelHouseDetail getAudit() {
        return audit;
    }

    public void setAudit(ModelHouseDetail audit) {
        this.audit = audit;
    }

    public String getProperty_state_cn() {
        return property_state_cn;
    }

    public void setProperty_state_cn(String property_state_cn) {
        this.property_state_cn = property_state_cn;
    }

    public String getState_cn() {
        return state_cn;
    }

    public void setState_cn(String state_cn) {
        this.state_cn = state_cn;
    }

    public String getArchitecture_cn() {
        return architecture_cn;
    }

    public void setArchitecture_cn(String architecture_cn) {
        this.architecture_cn = architecture_cn;
    }

    public String getDecorating_cn() {
        return decorating_cn;
    }

    public void setDecorating_cn(String decorating_cn) {
        this.decorating_cn = decorating_cn;
    }

    public String getBuy_time_cn() {
        return buy_time_cn;
    }

    public void setBuy_time_cn(String buy_time_cn) {
        this.buy_time_cn = buy_time_cn;
    }

    public ModelMyHouseList getList() {
        return list;
    }

    public void setList(ModelMyHouseList list) {
        this.list = list;
    }

    public ModelHouseDetail getInfo() {
        return info;
    }

    public void setInfo(ModelHouseDetail info) {
        this.info = info;
    }

    public List<ModelHouseDetail> getImg() {
        return img;
    }

    public void setImg(List<ModelHouseDetail> img) {
        this.img = img;
    }

    public ModelHouseDetail getOperation() {
        return operation;
    }

    public void setOperation(ModelHouseDetail operation) {
        this.operation = operation;
    }

    public List<ModelMyHouseList> getHouse_contract_img() {
        return house_contract_img;
    }

    public void setHouse_contract_img(List<ModelMyHouseList> house_contract_img) {
        this.house_contract_img = house_contract_img;
    }

    public int getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(int buy_time) {
        this.buy_time = buy_time;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge_limit() {
        return age_limit;
    }

    public void setAge_limit(String age_limit) {
        this.age_limit = age_limit;
    }

    public String getProperty_state() {
        return property_state;
    }

    public void setProperty_state(String property_state) {
        this.property_state = property_state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getArchitecture() {
        return architecture;
    }

    public void setArchitecture(int architecture) {
        this.architecture = architecture;
    }

    public String getDecorating() {
        return decorating;
    }

    public void setDecorating(String decorating) {
        this.decorating = decorating;
    }

    public String getLift() {
        return lift;
    }

    public void setLift(String lift) {
        this.lift = lift;
    }

    public String getHouses_id() {
        return houses_id;
    }

    public void setHouses_id(String houses_id) {
        this.houses_id = houses_id;
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

    public String getEnter_time() {
        return enter_time;
    }

    public void setEnter_time(String enter_time) {
        this.enter_time = enter_time;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public String getIdentity_img1() {
        return identity_img1;
    }

    public void setIdentity_img1(String identity_img1) {
        this.identity_img1 = identity_img1;
    }

    public String getIdentity_img2() {
        return identity_img2;
    }

    public void setIdentity_img2(String identity_img2) {
        this.identity_img2 = identity_img2;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
