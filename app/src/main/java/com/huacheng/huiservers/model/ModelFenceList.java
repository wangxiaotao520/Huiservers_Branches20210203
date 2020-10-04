package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 电子围栏
 * created by wangxiaotao
 * 2020/10/2 0002 10:11
 */
public class ModelFenceList implements Serializable {

    /**
     * id : 3
     * p_id : 191
     * title : 围栏1
     * lon : 123.12313
     * lat : 232.232
     * address : 迎宾街
     * radius : 23
     * company_id : 21
     * addtime : 1601385173
     * uptime : 1601385173
     */

    private String id;
    private String p_id;
    private String title;
    private String lon;
    private String lat;
    private String address;
    private String radius;
    private String company_id;
    private String addtime;
    private String uptime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
}
