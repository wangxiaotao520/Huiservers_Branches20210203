package com.huacheng.huiservers.model;

/**
 * Description:登录失效model
 * created by wangxiaotao
 * 2018/8/2 0002 上午 8:50
 */
public class ModelLoginOverTime {
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    int type = 0;//0是登录失效 1是从设置退出登录
}
