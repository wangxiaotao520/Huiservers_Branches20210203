package com.huacheng.huiservers.ui.servicenew.model;

import java.util.List;

/**
 * Description:
 * Author: badge
 * Create: 2018/9/5 15:26
 */
public class ModelItem {

    private String id;
    private String i_id;
    private String i_name;
    private String title;
    private String category_cn;
    private String title_img;
    private String title_img_size;
    private String price;
    private String logo;
    private int total_Pages;

    public String getTitle_img_size() {
        return title_img_size;
    }

    public void setTitle_img_size(String title_img_size) {
        this.title_img_size = title_img_size;
    }

    public int getTotal_Pages() {
        return total_Pages;
    }

    public void setTotal_Pages(int total_Pages) {
        this.total_Pages = total_Pages;
    }

    private List<ModelInfoCategory> category;


    public List<ModelInfoCategory> getCategory() {
        return category;
    }

    public void setCategory(List<ModelInfoCategory> category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getI_id() {
        return i_id;
    }

    public void setI_id(String i_id) {
        this.i_id = i_id;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory_cn() {
        return category_cn;
    }

    public void setCategory_cn(String category_cn) {
        this.category_cn = category_cn;
    }

    public String getTitle_img() {
        return title_img;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
