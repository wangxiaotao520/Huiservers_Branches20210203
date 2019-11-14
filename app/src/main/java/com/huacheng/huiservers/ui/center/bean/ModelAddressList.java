package com.huacheng.huiservers.ui.center.bean;

import java.io.Serializable;

/**
 * Description: 地址列表
 * created by wangxiaotao
 * 2019/11/14 0014 下午 5:59
 */
public class ModelAddressList implements Serializable{

    /**
     * id : 2129
     * uid : 3507
     * consignee_name : 付志斌
     * consignee_mobile : 15603590556
     * region_id : 342.417.418
     * region_cn : 山西省晋中市榆次区
     * community_id : 1
     * community_cn : 迎宾合作住宅区
     * company_id : 1
     * doorplate : 6号
     * states : 0
     * is_default : 1
     * is_house : 0
     * createtime : 1538710636
     * operatingtime : 1538710636
     * is_do : 1
     */

    private String id;
    private String uid;
    private String consignee_name;
    private String consignee_mobile;
    private String region_id;
    private String region_cn;
    private String community_id;
    private String community_cn;
    private String company_id;
    private String doorplate;
    private String states;
    private String is_default;
    private String is_house;
    private String createtime;
    private String operatingtime;
    private int is_do;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getConsignee_name() {
        return consignee_name;
    }

    public void setConsignee_name(String consignee_name) {
        this.consignee_name = consignee_name;
    }

    public String getConsignee_mobile() {
        return consignee_mobile;
    }

    public void setConsignee_mobile(String consignee_mobile) {
        this.consignee_mobile = consignee_mobile;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getRegion_cn() {
        return region_cn;
    }

    public void setRegion_cn(String region_cn) {
        this.region_cn = region_cn;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getCommunity_cn() {
        return community_cn;
    }

    public void setCommunity_cn(String community_cn) {
        this.community_cn = community_cn;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getDoorplate() {
        return doorplate;
    }

    public void setDoorplate(String doorplate) {
        this.doorplate = doorplate;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getIs_house() {
        return is_house;
    }

    public void setIs_house(String is_house) {
        this.is_house = is_house;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getOperatingtime() {
        return operatingtime;
    }

    public void setOperatingtime(String operatingtime) {
        this.operatingtime = operatingtime;
    }

    public int getIs_do() {
        return is_do;
    }

    public void setIs_do(int is_do) {
        this.is_do = is_do;
    }
}
