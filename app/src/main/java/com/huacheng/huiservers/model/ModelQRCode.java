package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 二维码
 * created by wangxiaotao
 * 2019/8/23 0023 下午 7:20
 */
public class ModelQRCode implements Serializable {

    String gtel ; //type=1的时候



    String type;

    public String getGtel() {
        return gtel;
    }

    public void setGtel(String gtel) {
        this.gtel = gtel;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
