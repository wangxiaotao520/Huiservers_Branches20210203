package com.huacheng.huiservers.model;

import com.huacheng.huiservers.ui.shop.bean.BannerBean;

/**
 * Description:首页圈子
 * created by wangxiaotao
 * 2019/12/3 0003 上午 10:06
 */
public class ModelHomeCircle {

    /**
     * title : 邻里交流
     * slogan : 社区资讯 随时了解
     * c_id : 85
     * index : 1
     */

    private String title;
    private String slogan;
    private int c_id;
    private int index;


    private BannerBean list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BannerBean getList() {
        return list;
    }

    public void setList(BannerBean list) {
        this.list = list;
    }

}
