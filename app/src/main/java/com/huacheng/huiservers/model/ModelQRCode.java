package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 二维码
 * created by wangxiaotao
 * 2019/8/23 0023 下午 7:20
 */
public class ModelQRCode implements Serializable {
    //type=1的时候
    String gtel ;
   //type=2的时候
    String o_id;

    String price;

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


    public String getO_id() {
        return o_id;
    }

    public void setO_id(String o_id) {
        this.o_id = o_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
