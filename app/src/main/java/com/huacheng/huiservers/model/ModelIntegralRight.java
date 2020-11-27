package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description: 我的特权
 * created by wangxiaotao
 * 2020/11/27 0027 10:24
 */
public class ModelIntegralRight {


    /**
     * id : 1
     * name : 签到送积分
     * img :
     * is_vip : 0
     * content :
     * is_my : 1
     */

    private List<ModelIntegralRight> user_right;
    private String id;
    private String name;
    private String img;
    private String is_vip;
    private String content;
    private int is_my;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIs_my() {
        return is_my;
    }

    public void setIs_my(int is_my) {
        this.is_my = is_my;
    }
    public List<ModelIntegralRight> getUser_right() {
        return user_right;
    }

    public void setUser_right(List<ModelIntegralRight> user_right) {
        this.user_right = user_right;
    }

}
