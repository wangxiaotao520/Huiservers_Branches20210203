package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 购物车数量变化
 * created by wangxiaotao
 * 2020/4/17 0017 上午 10:21
 */
public class ModelEventShopCart implements Serializable {

    String text_num;//
    public String getText_num() {
        return text_num;
    }

    public void setText_num(String text_num) {
        this.text_num = text_num;
    }
}
