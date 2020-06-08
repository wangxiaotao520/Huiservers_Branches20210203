package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 新的优惠券model
 * created by wangxiaotao
 * 2020/6/5 0005 15:14
 */
public class ModelCouponNew implements Serializable {

    /**
     * id : 19
     * display : 1
     * name : 点击领取优惠券2
     * category_id : 54
     * category_name : 商城优惠券
     * rule_id : 2
     * rule_name : 点击领取
     * rule_alias : activity
     * shop_cate : 0
     * shop_id : 0
     * rebate_shop_id :
     * amount : 2.00
     * fulfil_amount : 10.00
     * total_num : 100
     * used_num : 2
     * condition : 使用条件
     * userule :
     * repeat : 1
     * address : null
     * topclass : 342
     * topclass_cn : 山西省
     * category : 417
     * category_cn : 晋中市
     * subclass : 418
     * subclass_cn : 榆次区
     * photo : huacheng/coupon/20/05/21/5ec5e9d4b8ecc.jpg
     * box_img :
     * share_amount : 0
     * orderby : 0
     * startime : 1589299200
     * endtime : 1591545600
     * addtime : 1590028756
     */
    private int totalPages;
    private List<ModelCouponNew> list;

    private String id;
    private String display;
    private String name;
    private String category_id;
    private String category_name;
    private String rule_id;
    private String rule_name;
    private String rule_alias;
    private String shop_cate;
    private String shop_id;
    private String rebate_shop_id;
    private String amount;
    private String fulfil_amount;
    private String total_num;
    private String used_num;
    private String condition;
    private String userule;
    private String repeat;
    private Object address;
    private String topclass;
    private String topclass_cn;
    private String category;
    private String category_cn;
    private String subclass;
    private String subclass_cn;
    private String photo;
    private String box_img;
    private String share_amount;
    private String orderby;
    private String startime;
    private String endtime;
    private String addtime;

    private String status; //1是未领取 2是已领取

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRule_id() {
        return rule_id;
    }

    public void setRule_id(String rule_id) {
        this.rule_id = rule_id;
    }

    public String getRule_name() {
        return rule_name;
    }

    public void setRule_name(String rule_name) {
        this.rule_name = rule_name;
    }

    public String getRule_alias() {
        return rule_alias;
    }

    public void setRule_alias(String rule_alias) {
        this.rule_alias = rule_alias;
    }

    public String getShop_cate() {
        return shop_cate;
    }

    public void setShop_cate(String shop_cate) {
        this.shop_cate = shop_cate;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getRebate_shop_id() {
        return rebate_shop_id;
    }

    public void setRebate_shop_id(String rebate_shop_id) {
        this.rebate_shop_id = rebate_shop_id;
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

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public String getUsed_num() {
        return used_num;
    }

    public void setUsed_num(String used_num) {
        this.used_num = used_num;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getUserule() {
        return userule;
    }

    public void setUserule(String userule) {
        this.userule = userule;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getTopclass() {
        return topclass;
    }

    public void setTopclass(String topclass) {
        this.topclass = topclass;
    }

    public String getTopclass_cn() {
        return topclass_cn;
    }

    public void setTopclass_cn(String topclass_cn) {
        this.topclass_cn = topclass_cn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_cn() {
        return category_cn;
    }

    public void setCategory_cn(String category_cn) {
        this.category_cn = category_cn;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public String getSubclass_cn() {
        return subclass_cn;
    }

    public void setSubclass_cn(String subclass_cn) {
        this.subclass_cn = subclass_cn;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBox_img() {
        return box_img;
    }

    public void setBox_img(String box_img) {
        this.box_img = box_img;
    }

    public String getShare_amount() {
        return share_amount;
    }

    public void setShare_amount(String share_amount) {
        this.share_amount = share_amount;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getStartime() {
        return startime;
    }

    public void setStartime(String startime) {
        this.startime = startime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }


    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelCouponNew> getList() {
        return list;
    }

    public void setList(List<ModelCouponNew> list) {
        this.list = list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
