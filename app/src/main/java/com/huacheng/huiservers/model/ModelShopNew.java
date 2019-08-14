package com.huacheng.huiservers.model;

import com.huacheng.huiservers.ui.shop.bean.BannerBean;


import java.util.List;

/**
 * Description:商城首页Model
 * Author: badge
 * Create: 2018/12/21 16:41
 */
public class ModelShopNew {

    private List<ModelAds> ad_hc_shopindex;//头部广告
    private List<BannerBean> ad_hc_shop_center;//中部广告

    private List<ModelShopIndex> cate_list;//分类菜单
    private ModelShopIndex pro_discount_list;//限时抢购
    private List<ModelShopIndex> hot_cate_list;//

    public List<ModelAds> getAd_hc_shopindex() {
        return ad_hc_shopindex;
    }

    public List<BannerBean> getAd_hc_shop_center() {
        return ad_hc_shop_center;
    }

    public void setAd_hc_shop_center(List<BannerBean> ad_hc_shop_center) {
        this.ad_hc_shop_center = ad_hc_shop_center;
    }

    public void setAd_hc_shopindex(List<ModelAds> ad_hc_shopindex) {
        this.ad_hc_shopindex = ad_hc_shopindex;
    }

    public List<ModelShopIndex> getCate_list() {
        return cate_list;
    }

    public void setCate_list(List<ModelShopIndex> cate_list) {
        this.cate_list = cate_list;
    }

    public ModelShopIndex getPro_discount_list() {
        return pro_discount_list;
    }

    public void setPro_discount_list(ModelShopIndex pro_discount_list) {
        this.pro_discount_list = pro_discount_list;
    }

    public List<ModelShopIndex> getHot_cate_list() {
        return hot_cate_list;
    }

    public void setHot_cate_list(List<ModelShopIndex> hot_cate_list) {
        this.hot_cate_list = hot_cate_list;
    }
}
