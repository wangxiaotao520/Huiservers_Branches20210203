package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 微信eventbus
 * created by wangxiaotao
 * 2018/12/20 0020 下午 2:46
 */
public class ModelEventWX implements Serializable {
    private int type;//0.刷新支付成功的回调

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
