package com.huacheng.huiservers.model;

/**
 * Description: 签到页面
 * created by wangxiaotao
 * 2020/11/28 0028 09:00
 */
public class ModelSignRegister {

    /**
     * sign_num : 0
     * sign_rule : 1.用户日常签到可获得积分10、成长值10，连续签到7天后，获得额外积分50、成长值100；
     2.用户前一天0：00-24：00前签到成功，第二日0：00-24：00进入页面并签到成功，则视为连续签到；
     3.前一天0：00-24：00前签到成功，第二日0：00-24：00未签到，则视为打断连续签到，再次进入签到页面时，记录消除，并重新开始计算。
     4.7天为一个连续签到周期，连续签到完成一个周期后，重新开始记录。
     * sign_points : 50
     * sign_rank : 50
     */

    private int sign_num;
    private String sign_rule;
    private String sign_points;
    private String sign_rank;

    private String is_sign;

    public int getSign_num() {
        return sign_num;
    }

    public void setSign_num(int sign_num) {
        this.sign_num = sign_num;
    }

    public String getSign_rule() {
        return sign_rule;
    }

    public void setSign_rule(String sign_rule) {
        this.sign_rule = sign_rule;
    }

    public String getSign_points() {
        return sign_points;
    }

    public void setSign_points(String sign_points) {
        this.sign_points = sign_points;
    }

    public String getSign_rank() {
        return sign_rank;
    }

    public void setSign_rank(String sign_rank) {
        this.sign_rank = sign_rank;
    }

    public String getIs_sign() {
        return is_sign;
    }

    public void setIs_sign(String is_sign) {
        this.is_sign = is_sign;
    }

}
