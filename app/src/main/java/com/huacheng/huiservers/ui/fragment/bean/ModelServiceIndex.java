package com.huacheng.huiservers.ui.fragment.bean;

import com.huacheng.huiservers.ui.servicenew.model.ModelCategory;
import com.huacheng.huiservers.ui.servicenew.model.ModelCategoryService;
import com.huacheng.huiservers.ui.servicenew.model.ModelInfo;
import com.huacheng.huiservers.ui.servicenew.model.ModelItem;

import java.util.List;

/**
 * Description:
 * Author: badge
 * Create: 2018/9/4 16:39
 */
public class ModelServiceIndex {
    private List<ModelCategory> category;
    private List<ModelCategoryService> category_service;
    private List<ModelInfo> info;
    private List<ModelItem> item;

    public List<ModelCategory> getCategory() {
        return category;
    }

    public void setCategory(List<ModelCategory> category) {
        this.category = category;
    }

    public List<ModelCategoryService> getCategory_service() {
        return category_service;
    }

    public void setCategory_service(List<ModelCategoryService> category_service) {
        this.category_service = category_service;
    }

    public List<ModelInfo> getInfo() {
        return info;
    }

    public void setInfo(List<ModelInfo> info) {
        this.info = info;
    }

    public List<ModelItem> getItem() {
        return item;
    }

    public void setItem(List<ModelItem> item) {
        this.item = item;
    }

}
