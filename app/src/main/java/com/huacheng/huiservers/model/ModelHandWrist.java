package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 智能硬件列表model
 * created by wangxiaotao
 * 2020/9/30 0030 09:25
 */
public class ModelHandWrist implements Serializable {



    private String id;
    private String p_id;
    private String rrz_kit_sn;
    private String sleepmonitorID;
    private String fzdType;
    private String fzdIMEI;
    /**
     * MID : 863000112309212
     * UID : d4a3335f4ee5bb8b538fb295
     * Lon : 112.7282772
     * Lat : 37.6888656
     * Pro : 山西省
     * City : 晋中市
     * Dist : 榆次区
     * Str : 新建街道汇通路辅路
     * UT : 2020-09-29 15:47:19
     * B : 17
     */
    //定位出来的相关数据
    private String MID;
    private String UID;
    private double Lon;
    private double Lat;
    private String Pro;
    private String City;
    private String Dist;
    private String Str;
    private String UT;
    private int B;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getRrz_kit_sn() {
        return rrz_kit_sn;
    }

    public void setRrz_kit_sn(String rrz_kit_sn) {
        this.rrz_kit_sn = rrz_kit_sn;
    }

    public String getSleepmonitorID() {
        return sleepmonitorID;
    }

    public void setSleepmonitorID(String sleepmonitorID) {
        this.sleepmonitorID = sleepmonitorID;
    }

    public String getFzdType() {
        return fzdType;
    }

    public void setFzdType(String fzdType) {
        this.fzdType = fzdType;
    }

    public String getFzdIMEI() {
        return fzdIMEI;
    }

    public void setFzdIMEI(String fzdIMEI) {
        this.fzdIMEI = fzdIMEI;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double Lon) {
        this.Lon = Lon;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double Lat) {
        this.Lat = Lat;
    }

    public String getPro() {
        return Pro;
    }

    public void setPro(String Pro) {
        this.Pro = Pro;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getDist() {
        return Dist;
    }

    public void setDist(String Dist) {
        this.Dist = Dist;
    }

    public String getStr() {
        return Str;
    }

    public void setStr(String Str) {
        this.Str = Str;
    }

    public String getUT() {
        return UT;
    }

    public void setUT(String UT) {
        this.UT = UT;
    }

    public int getB() {
        return B;
    }

    public void setB(int B) {
        this.B = B;
    }
}
