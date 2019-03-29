package com.huacheng.huiservers.ui.servicenew.model;

import java.util.List;

/**
 * Description:
 * Author: badge
 * Create: 2018/9/5 15:25
 */
public class ModelInfo {

    private String id;
    private String name;
    private String index_img;
    private String logo;
    private List<ModelInfoCategory> category;

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

    public String getIndex_img() {
        return index_img;
    }

    public void setIndex_img(String index_img) {
        this.index_img = index_img;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<ModelInfoCategory> getCategory() {
        return category;
    }

    public void setCategory(List<ModelInfoCategory> category) {
        this.category = category;
    }
}
