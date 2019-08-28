package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 老人档案 详情
 * created by wangxiaotao
 * 2019/8/28 0028 下午 3:27
 */
public class ModelOldFileDetail  implements Serializable{


    /**
     * basis : {"id":"1","photo":"huacheng_old/old/people/19/08/23/5d5faef158d45.jpg","name":"付志斌","sex":"1","birthday":74,"idcard":"222222222","contact":"","telphone":"","education_cn":"","political_cn":""}
     * archives : {"id":"1","p_id":"1","i_id":"1","height":"11111","weight":"22222","blood":"1","ill":null,"allergy":null,"body":"1111","addtime":"1566972020","uptime":"1566972198","checkuptime":"123123"}
     */

    private BasisBean basis;
    private ArchivesBean archives;

    public BasisBean getBasis() {
        return basis;
    }

    public void setBasis(BasisBean basis) {
        this.basis = basis;
    }

    public ArchivesBean getArchives() {
        return archives;
    }

    public void setArchives(ArchivesBean archives) {
        this.archives = archives;
    }

    public static class BasisBean {
        /**
         * id : 1
         * photo : huacheng_old/old/people/19/08/23/5d5faef158d45.jpg
         * name : 付志斌
         * sex : 1
         * birthday : 74
         * idcard : 222222222
         * contact :
         * telphone :
         * education_cn :
         * political_cn :
         */

        private String id;
        private String photo;
        private String name;
        private String sex;
        private int birthday;
        private String idcard;
        private String contact;
        private String telphone;
        private String education_cn;
        private String political_cn;

        private String chusheng;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getBirthday() {
            return birthday;
        }

        public void setBirthday(int birthday) {
            this.birthday = birthday;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getEducation_cn() {
            return education_cn;
        }

        public void setEducation_cn(String education_cn) {
            this.education_cn = education_cn;
        }

        public String getPolitical_cn() {
            return political_cn;
        }

        public void setPolitical_cn(String political_cn) {
            this.political_cn = political_cn;
        }

        public String getChusheng() {
            return chusheng;
        }

        public void setChusheng(String chusheng) {
            this.chusheng = chusheng;
        }

    }

    public static class ArchivesBean {
        /**
         * id : 1
         * p_id : 1
         * i_id : 1
         * height : 11111
         * weight : 22222
         * blood : 1
         * ill : null
         * allergy : null
         * body : 1111
         * addtime : 1566972020
         * uptime : 1566972198
         * checkuptime : 123123
         */

        private String id;
        private String p_id;
        private String i_id;
        private String height;
        private String weight;
        private String blood;
        private List<FlowBean> ill;
        private List<FlowBean> allergy;
        private String body;
        private String addtime;
        private String uptime;
        private String checkuptime;

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

        public String getI_id() {
            return i_id;
        }

        public void setI_id(String i_id) {
            this.i_id = i_id;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getBlood() {
            return blood;
        }

        public void setBlood(String blood) {
            this.blood = blood;
        }

        public List<FlowBean> getIll() {
            return ill;
        }

        public void setIll(List<FlowBean> ill) {
            this.ill = ill;
        }

        public List<FlowBean> getAllergy() {
            return allergy;
        }

        public void setAllergy(List<FlowBean> allergy) {
            this.allergy = allergy;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getUptime() {
            return uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }

        public String getCheckuptime() {
            return checkuptime;
        }

        public void setCheckuptime(String checkuptime) {
            this.checkuptime = checkuptime;
        }
    }
    public static class FlowBean {
        private  String c_name;

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }
    }
}
