package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description:首页Eventbus
 * created by wangxiaotao
 * 2018/12/24 0024 上午 10:11
 */
public class ModelEventHome implements Serializable{


    int type=0;//0是跳转到物业公告，1是跳转的社区公告  2.销毁当前页
    public ModelEventHome() {

    }
    public ModelEventHome(int type) {

        this.type = type;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
