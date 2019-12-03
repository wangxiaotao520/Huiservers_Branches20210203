package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description:首页Model
 * created by wangxiaotao
 * 2018/12/19 0019 下午 3:48
 */
public class ModelHome implements Serializable {

    private List<ModelAds> ad_top_list;//顶部广告
    private List<ModelHomeIndex> menu_list;//导航
    private List<ModelVBaner> p_social_list;//物业公告
    private List<ModelIndex> article_list;//手册协议
    private List<ModelHomeCircle> social_list;//圈子
    private List<HouseRentDetail> houses_list;//租售房
    private List<ModelAds> ad_center_list;//中部广告
    private  List<ModelShopIndex>  pro_list;//商品信息


    public List<ModelAds> getAd_top_list() {
        return ad_top_list;
    }

    public void setAd_top_list(List<ModelAds> ad_top_list) {
        this.ad_top_list = ad_top_list;
    }

    public List<ModelHomeIndex> getMenu_list() {
        return menu_list;
    }

    public void setMenu_list(List<ModelHomeIndex> menu_list) {
        this.menu_list = menu_list;
    }

    public List<ModelVBaner> getP_social_list() {
        return p_social_list;
    }

    public void setP_social_list(List<ModelVBaner> p_social_list) {
        this.p_social_list = p_social_list;
    }

    public List<ModelIndex> getArticle_list() {
        return article_list;
    }

    public void setArticle_list(List<ModelIndex> article_list) {
        this.article_list = article_list;
    }

    public List<ModelHomeCircle> getSocial_list() {
        return social_list;
    }

    public void setSocial_list(List<ModelHomeCircle> social_list) {
        this.social_list = social_list;
    }

    public List<HouseRentDetail> getHouses_list() {
        return houses_list;
    }

    public void setHouses_list(List<HouseRentDetail> houses_list) {
        this.houses_list = houses_list;
    }

    public List<ModelAds> getAd_center_list() {
        return ad_center_list;
    }

    public void setAd_center_list(List<ModelAds> ad_center_list) {
        this.ad_center_list = ad_center_list;
    }

    public List<ModelShopIndex> getPro_list() {
        return pro_list;
    }

    public void setPro_list(List<ModelShopIndex> pro_list) {
        this.pro_list = pro_list;
    }
}
