package com.huacheng.huiservers.model;

import java.util.List;

/**
 * 类描述：收藏商品店铺、服务店铺
 * 时间：2020/12/7 17:18
 * created by DFF
 */
public class ModelCollect {

    private int total_Pages;
    private List<ModelCollect> list;
    /**
     * title : 123
     * title_img : huacheng/product/20/04/23/5ea0f9e1aed81.png
     * description : 111
     * is_vip : 0
     * original : 1.00
     * price : 1.00
     * vip_price : 0.00
     */

    private String title;
    private String title_img;
    private String description;
    private String is_vip;
    private String original;
    private String price;
    private String vip_price;

    public int getTotal_Pages() {
        return total_Pages;
    }

    public void setTotal_Pages(int total_Pages) {
        this.total_Pages = total_Pages;
    }
    public List<ModelCollect> getList() {
        return list;
    }

    public void setList(List<ModelCollect> list) {
        this.list = list;
    }

    /**
     * collection_id : 8
     * p_id : 57
     * merchant_name : 迎宾小区运营中心
     * logo :
     * address : 迎宾街迎宾小区物业部
     */

    private String collection_id;
    private String p_id;
    private String merchant_name;
    private String name;
    private String logo;
    private String address;


    public String getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(String collection_id) {
        this.collection_id = collection_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVip_price() {
        return vip_price;
    }

    public void setVip_price(String vip_price) {
        this.vip_price = vip_price;
    }
}
