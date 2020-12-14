package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 我的房源 列表
 * created by wangxiaotao
 * 2018/11/15 0015 上午 11:01
 */
public class ModelMyHouseList implements Serializable {

    private int totalPages;
    private List<ModelMyHouseList> list;
    private String id;
    private String community_name;
    private String room;
    private String office;
    private String kitchen;
    private String guard;
    private String area;
    private String floor;
    private String house_floor;
    private String status;
    private String user_name;
    private String username;
    private String nickname;
    private String user_phone;
    private String release_time;
    private String unit_price;
    private String total_price;
    private String read_num;
    private String call_num;
    private String timeat;
    private String avatars;
    private String name;
    private String phone;
    private String head_img;
    private String img;
    private String num;
    private String created_at;
    /**
     * community_id :
     * community : 2
     * site : 2
     * mobile : 2
     * pattern : 2室2厅2厨2卫
     * number : 2
     * number_count : 2
     * introduced : null
     * title : null
     * label : null
     * selling :
     * unit :
     * rent : 2
     * rents_state : 4
     * pay :
     * add_state : 4
     * uid : null
     * add_id : 2
     * state : 1
     * add_time : 1600226457
     * set_time : null
     * check : 0
     * organization_id : 0
     * cause : 0
     */
    private String community_id;
    private String community;
    private String site;
    private String mobile;
    private String pattern;
    private String number;
    private String number_count;
    private String introduced;
    private String title;
    private String label;
    private String selling;
    private String unit;
    private String code;
    private String rent;
    private String rents_state;
    private String pay;
    private String add_state;
    private String uid;
    private String add_id;
    private String state;
    private String add_time;
    private String set_time;
    private int check;
    private String organization_id;
    private String cause;
    private String add_name;
    private String add_mobile;
    private String headimg;
    private String house_unit;
    private ModelMyHouseList audit;
    private List<String> label_cn;

    public String getHouse_unit() {
        return house_unit;
    }

    public void setHouse_unit(String house_unit) {
        this.house_unit = house_unit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<String> getLabel_cn() {
        return label_cn;
    }

    public void setLabel_cn(List<String> label_cn) {
        this.label_cn = label_cn;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public String getAdd_mobile() {
        return add_mobile;
    }

    public void setAdd_mobile(String add_mobile) {
        this.add_mobile = add_mobile;
    }

    public ModelMyHouseList getAudit() {
        return audit;
    }

    public void setAudit(ModelMyHouseList audit) {
        this.audit = audit;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatars() {
        return avatars;
    }

    public void setAvatars(String avatars) {
        this.avatars = avatars;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ModelMyHouseList> getList() {
        return list;
    }

    public void setList(List<ModelMyHouseList> list) {
        this.list = list;
    }

    public int getCountPage() {
        return totalPages;
    }

    public void setCountPage(int CountPage) {
        this.totalPages = CountPage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }

    public String getCall_num() {
        return call_num;
    }

    public void setCall_num(String call_num) {
        this.call_num = call_num;
    }

    public String getTimeat() {
        return timeat;
    }

    public void setTimeat(String timeat) {
        this.timeat = timeat;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber_count() {
        return number_count;
    }

    public void setNumber_count(String number_count) {
        this.number_count = number_count;
    }


    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSet_time(String set_time) {
        this.set_time = set_time;
    }

    public String getSelling() {
        return selling;
    }

    public void setSelling(String selling) {
        this.selling = selling;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getRents_state() {
        return rents_state;
    }

    public void setRents_state(String rents_state) {
        this.rents_state = rents_state;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getAdd_state() {
        return add_state;
    }

    public void setAdd_state(String add_state) {
        this.add_state = add_state;
    }


    public String getAdd_id() {
        return add_id;
    }

    public void setAdd_id(String add_id) {
        this.add_id = add_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUid() {
        return uid;
    }

    public String getSet_time() {
        return set_time;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
