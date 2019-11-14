package com.huacheng.huiservers.model;

import java.util.List;

/**
 * 类描述：
 * 时间：2019/11/14 21:24
 * created by DFF
 */
public class ModelZQInfo {

    private List<ModelZQInfo> list;
    private int total_Pages;
    private String id;
    private String m_id;
    private String title;
    private String orders;
    private String img;
    private String content;
    private String state;
    private String addtime;
    private String uptime;

    public List<ModelZQInfo> getList() {
        return list;
    }

    public void setList(List<ModelZQInfo> list) {
        this.list = list;
    }

    public int getTotal_Pages() {
        return total_Pages;
    }

    public void setTotal_Pages(int total_Pages) {
        this.total_Pages = total_Pages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
