package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 用药提醒
 * created by wangxiaotao
 * 2019/8/27 0027 上午 9:50
 */
public class ModelOldDrugList implements Serializable{

    /**
     * id : 1
     * eatime : 08:00-10:00
     * note : 备注
     * user_name : 张三丰
     * drug_list : [{"id":"1","d_name":"阿莫西林","dose":"1颗","tips":"按时吃哈"},{"id":"3","d_name":"滴丸","dose":"一瓶","tips":"喝了"}]
     */

    private String id;
    private String eatime;
    private String note;
    private String user_name;
    private List<DrugListBean> drug_list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEatime() {
        return eatime;
    }

    public void setEatime(String eatime) {
        this.eatime = eatime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<DrugListBean> getDrug_list() {
        return drug_list;
    }

    public void setDrug_list(List<DrugListBean> drug_list) {
        this.drug_list = drug_list;
    }

    public static class DrugListBean implements Serializable{
        /**
         * id : 1
         * d_name : 阿莫西林
         * dose : 1颗
         * tips : 按时吃哈
         */

        private String id;
        private String d_name;
        private String dose;
        private String tips;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getD_name() {
            return d_name;
        }

        public void setD_name(String d_name) {
            this.d_name = d_name;
        }

        public String getDose() {
            return dose;
        }

        public void setDose(String dose) {
            this.dose = dose;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }
}
