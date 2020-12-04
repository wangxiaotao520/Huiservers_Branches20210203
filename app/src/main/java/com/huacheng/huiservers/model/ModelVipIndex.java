package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：Vip首页
 * 时间：2020/11/30 16:07
 * created by DFF
 */
public class ModelVipIndex implements Serializable {
    //顶部信息
    private String uid;
    private String nickname;
    private String avatars;
    private String is_vip;
    private String vip_endtime;
    private String save_plan;
    private String save_already;

    private String id;
    private String name;
    private String img;
    private String content;
    private String price;
    private String original_price;
    private String days;
    private String category_name;
    private String amount;
    private String fulfil_amount;
    private String send_shop;
    private String send_shop_id;
    private String vip_count;
    private String logo;
    private String address;

    private List<ModelVipIndex> vip_right;
    private List<ModelVipIndex> vip_list;
    private List<ModelVipIndex> vip_coupon;
    private List<ModelVipIndex> vip_shop;
    private List<ModelVipIndex> s_list;
    private boolean isSelect;
    /**
     * title : 阿尔卑斯乐嚼Q
     * title_img : huacheng/product/19/06/07/thumb_5cfa13a23d35f.jpg
     * title_thumb_img : huacheng/product/19/06/07/thumb_5cfa13a23d35f.jpg
     * goods_tag :
     * discount : 0
     * distance_start : -1606957355
     * distance_end : -1606957355
     * tagid :
     * tagname :
     * original :
     * vip_price :
     * inventory : 0
     * unit :
     * exist_hours : 2
     * is_hot : 0
     * is_new : 0
     * is_time : 0
     * total_Pages : 272
     */

    private String title;
    private String title_img;
    private String title_thumb_img;
    private String goods_tag;
    private int discount;
    private int distance_start;
    private int distance_end;
    private String tagid;
    private String tagname;
    private String original;
    private String vip_price;
    private int inventory;
    private String unit;
    private int exist_hours;
    private int is_hot;
    private int is_new;
    private int is_time;
    private int total_Pages;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatars() {
        return avatars;
    }

    public void setAvatars(String avatars) {
        this.avatars = avatars;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getVip_endtime() {
        return vip_endtime;
    }

    public void setVip_endtime(String vip_endtime) {
        this.vip_endtime = vip_endtime;
    }

    public String getSave_plan() {
        return save_plan;
    }

    public void setSave_plan(String save_plan) {
        this.save_plan = save_plan;
    }

    public String getSave_already() {
        return save_already;
    }

    public void setSave_already(String save_already) {
        this.save_already = save_already;
    }

    public List<ModelVipIndex> getVip_right() {
        return vip_right;
    }

    public void setVip_right(List<ModelVipIndex> vip_right) {
        this.vip_right = vip_right;
    }

    public List<ModelVipIndex> getVip_list() {
        return vip_list;
    }

    public void setVip_list(List<ModelVipIndex> vip_list) {
        this.vip_list = vip_list;
    }

    public List<ModelVipIndex> getVip_coupon() {
        return vip_coupon;
    }

    public void setVip_coupon(List<ModelVipIndex> vip_coupon) {
        this.vip_coupon = vip_coupon;
    }

    public List<ModelVipIndex> getVip_shop() {
        return vip_shop;
    }

    public void setVip_shop(List<ModelVipIndex> vip_shop) {
        this.vip_shop = vip_shop;
    }

    public List<ModelVipIndex> getS_list() {
        return s_list;
    }

    public void setS_list(List<ModelVipIndex> s_list) {
        this.s_list = s_list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFulfil_amount() {
        return fulfil_amount;
    }

    public void setFulfil_amount(String fulfil_amount) {
        this.fulfil_amount = fulfil_amount;
    }

    public String getSend_shop() {
        return send_shop;
    }

    public void setSend_shop(String send_shop) {
        this.send_shop = send_shop;
    }

    public String getSend_shop_id() {
        return send_shop_id;
    }

    public void setSend_shop_id(String send_shop_id) {
        this.send_shop_id = send_shop_id;
    }

    public String getVip_count() {
        return vip_count;
    }

    public void setVip_count(String vip_count) {
        this.vip_count = vip_count;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_img() {
        return title_img;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }

    public String getTitle_thumb_img() {
        return title_thumb_img;
    }

    public void setTitle_thumb_img(String title_thumb_img) {
        this.title_thumb_img = title_thumb_img;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDistance_start() {
        return distance_start;
    }

    public void setDistance_start(int distance_start) {
        this.distance_start = distance_start;
    }

    public int getDistance_end() {
        return distance_end;
    }

    public void setDistance_end(int distance_end) {
        this.distance_end = distance_end;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getVip_price() {
        return vip_price;
    }

    public void setVip_price(String vip_price) {
        this.vip_price = vip_price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getExist_hours() {
        return exist_hours;
    }

    public void setExist_hours(int exist_hours) {
        this.exist_hours = exist_hours;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getIs_new() {
        return is_new;
    }

    public void setIs_new(int is_new) {
        this.is_new = is_new;
    }

    public int getIs_time() {
        return is_time;
    }

    public void setIs_time(int is_time) {
        this.is_time = is_time;
    }

    public int getTotal_Pages() {
        return total_Pages;
    }

    public void setTotal_Pages(int total_Pages) {
        this.total_Pages = total_Pages;
    }
}
