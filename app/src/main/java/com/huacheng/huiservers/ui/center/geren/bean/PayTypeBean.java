package com.huacheng.huiservers.ui.center.geren.bean;

/**
 * 类：
 * 时间：2018/1/5 17:53
 * 功能描述:Huiservers
 */
public class PayTypeBean {
    private String id;
    private String typename;
    private String byname;
    private String icon;
    private String app_id;
    private String p_introduction;
    private int recommend;

    private int obvious;



    private boolean isSelected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getByname() {
        return byname;
    }

    public void setByname(String byname) {
        this.byname = byname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getP_introduction() {
        return p_introduction;
    }

    public void setP_introduction(String p_introduction) {
        this.p_introduction = p_introduction;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public int getObvious() {
        return obvious;
    }

    public void setObvious(int obvious) {
        this.obvious = obvious;
    }
}
