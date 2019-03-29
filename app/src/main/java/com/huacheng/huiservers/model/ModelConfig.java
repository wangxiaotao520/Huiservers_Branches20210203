package com.huacheng.huiservers.model;

/**
 * Description:
 * created by wangxiaotao
 * 2019/3/13 0013 上午 11:25
 */
public class ModelConfig {

    private String hui_domain_name;//社区慧生活域名
    private String pro_domain_name; //物业域名
    private String hui_database;
    private String pro_database;
    private String company_id;//企业id

    public String getHui_domain_name() {
        return hui_domain_name;
    }

    public void setHui_domain_name(String hui_domain_name) {
        this.hui_domain_name = hui_domain_name;
    }

    public String getPro_domain_name() {
        return pro_domain_name;
    }

    public void setPro_domain_name(String pro_domain_name) {
        this.pro_domain_name = pro_domain_name;
    }

    public String getHui_database() {
        return hui_database;
    }

    public void setHui_database(String hui_database) {
        this.hui_database = hui_database;
    }

    public String getPro_database() {
        return pro_database;
    }

    public void setPro_database(String pro_database) {
        this.pro_database = pro_database;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
}
