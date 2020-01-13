package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 首页上方接口
 * created by wangxiaotao
 * 2019/8/26 0026 上午 9:25
 */
public class ModelOldIndexTop implements Serializable{

    /**
     * old_id :
     * name :
     * birthday :
     * i_id :
     * i_name :
     * call :
     * par_uid :
     * o_company_id :
     * type : 0
     */

    private String old_id;
    private String name;
    private String birthday;
    private String i_id;
    private String i_name;
    private String call;
    private String par_uid;
    private String o_company_id;


    private String is_vip;
    private int type;

    private  String photo ;


    private String sex;//1男 2女

    public String getOld_id() {
        return old_id;
    }

    public void setOld_id(String old_id) {
        this.old_id = old_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getI_id() {
        return i_id;
    }

    public void setI_id(String i_id) {
        this.i_id = i_id;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getPar_uid() {
        return par_uid;
    }

    public void setPar_uid(String par_uid) {
        this.par_uid = par_uid;
    }

    public String getO_company_id() {
        return o_company_id;
    }

    public void setO_company_id(String o_company_id) {
        this.o_company_id = o_company_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

}
