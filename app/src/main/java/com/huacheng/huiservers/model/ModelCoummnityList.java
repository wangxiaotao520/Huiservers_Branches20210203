package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 小区列表
 * created by wangxiaotao
 * 2019/11/8 0008 上午 11:24
 */
public class ModelCoummnityList implements Serializable{

    String name;
    String id ;

    String address;
    int type ;//1.当前小区//2我的小区//3附近小区


    int position = -1; //在每个type内的位置

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}