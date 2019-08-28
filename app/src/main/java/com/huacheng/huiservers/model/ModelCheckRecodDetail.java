package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description:体检记录详情
 * created by wangxiaotao
 * 2019/8/28 0028 下午 7:52
 */
public class ModelCheckRecodDetail  {

    /**
     * id : 1
     * type : 1
     * checktime : 123123
     * bp_min : 12
     * bp_max : 23
     * sao2 : 50
     * glu : 122
     * hr : 12
     * describe : 12121212
     * ecg_img : [{"id":"1","c_id":"1","itype":"1","img":"图片路径","addtime":"156035905"}]
     */

    private String id;
    private String type;
    private String checktime;
    private String bp_min;
    private String bp_max;
    private String sao2;
    private String glu;
    private String hr;
    private String describe;
    private List<EcgImgBean> ecg_img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChecktime() {
        return checktime;
    }

    public void setChecktime(String checktime) {
        this.checktime = checktime;
    }

    public String getBp_min() {
        return bp_min;
    }

    public void setBp_min(String bp_min) {
        this.bp_min = bp_min;
    }

    public String getBp_max() {
        return bp_max;
    }

    public void setBp_max(String bp_max) {
        this.bp_max = bp_max;
    }

    public String getSao2() {
        return sao2;
    }

    public void setSao2(String sao2) {
        this.sao2 = sao2;
    }

    public String getGlu() {
        return glu;
    }

    public void setGlu(String glu) {
        this.glu = glu;
    }

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<EcgImgBean> getEcg_img() {
        return ecg_img;
    }

    public void setEcg_img(List<EcgImgBean> ecg_img) {
        this.ecg_img = ecg_img;
    }

    public static class EcgImgBean {
        /**
         * id : 1
         * c_id : 1
         * itype : 1
         * img : 图片路径
         * addtime : 156035905
         */

        private String id;
        private String c_id;
        private String itype;
        private String img;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getItype() {
            return itype;
        }

        public void setItype(String itype) {
            this.itype = itype;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
