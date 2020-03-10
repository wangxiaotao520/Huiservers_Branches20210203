package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/3.
 * 维修详情Bean
 */

public class PropertyRepairDetailBean {

    private PropertyRepairBean propertyRepair;

    private List<Imgs> imgs;

    private List<ReplyBean> r_list;

    public List<ReplyBean> getR_list() {
        return r_list;
    }

    public void setR_list(List<ReplyBean> r_list) {
        this.r_list = r_list;
    }

    public PropertyRepairBean getPropertyRepair() {
        return propertyRepair;
    }

    public void setPropertyRepair(PropertyRepairBean propertyRepair) {
        this.propertyRepair = propertyRepair;
    }

    public List<Imgs> getImgs() {
        return imgs;
    }

    public void setImgs(List<Imgs> imgs) {
        this.imgs = imgs;
    }

}

