package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 极光推送
 * created by badge
 * 2018/12/25 10:54
 */
public class ModelJpushNotifaction implements Serializable {
    //1是机构列表 2是机构派单页 3是机构详情 4是师傅确认页面 5是机构增派页 6是用户详情页
    String url_type;
    String msg;
    ModelJpushNotifaction data;

    String id;


    String par_uid; //老人定位用


    String toView; //toView这个参数 , 如果toView=1表示往慧生活的地图首页跳转 , 如果toView=2表示往居家养老的地图首页跳转
    private String sound_type;

    public String getSound_type() {
        return sound_type;
    }

    public void setSound_type(String sound_type) {
        this.sound_type = sound_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl_type() {
        return url_type;
    }

    public void setUrl_type(String url_type) {
        this.url_type = url_type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ModelJpushNotifaction getData() {
        return data;
    }

    public void setData(ModelJpushNotifaction data) {
        this.data = data;
    }


    public String getPar_uid() {
        return par_uid;
    }

    public void setPar_uid(String par_uid) {
        this.par_uid = par_uid;
    }

    public String getToView() {
        return toView;
    }

    public void setToView(String toView) {
        this.toView = toView;
    }

}
