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
    /**
     * info : {"bmi":"","height":"","weight":"84.1","bloodoxygen":97,"pulse":93,"diastolic":86,"systolic":137,"visceralfat":12,"bone":"2.8","basalMetabolism":1365,"moisturerate":62.5,"muscle":"37.9","adiposerate":"27.9","pdf_url":"http://admin.ebelter.com/pdfjs-dist/web/viewer.html?url=http://report.ebelter.com/bhcp-report-aly/healthRecord/json/getReportDetail?physicalId=BT200104153804160000000001578383914361"}
     * suggest : {"food":[{"str":"用富含饱和脂肪酸的猪油、牛油、洋油、奶油、黄油，不要吃垃圾食品、或者加工过的食品"},{"str":"营养要均衡，多进食一些高蛋白食品，如鱼、鸡蛋、牛奶等。"},{"str":"少吃硬果类食品，此类食品本身含有大量的脂肪，产热量很高，如100g开心果、核桃，或者夏威夷果，热量均大于500千卡"}],"sport":[{"str":"登山，是良好的户外运动，取其景致自然，空气新鲜，加大氧气所占气体容量"},{"str":"将有氧运动与力量练习结合起来，每周做力量练习3次，每次30分钟，有氧运动控制在1个小时左右。"},{"str":"每周打网球3次，每次60分钟"}],"common":[{"str":"气功养生，学会吐纳法：吐气的时候，不能把嘴张得太大，要无声，长气，吐完为止。来回的吐气锻炼肺活量，气体大量的平缓的与身体废气交换。"},{"str":"注意劳逸结合，及时补充足够的水。"},{"str":"不吸烟，不饮酒，避免饮用过多碳酸饮料、浓茶、甜咖啡"}],"doctor":[{"str":"针对每个穴位按摩，会对气血等进行滋养。"},{"str":"可选择疏肝健脾的食疗养生方，如芹菜粥，即取芹菜连根120g，粳米250g，先将芹菜洗净，切成六分长的段，粳米淘净，芹菜，粳米放入锅内，加清水适量，用武火烧沸"},{"str":"可按摩天枢、屋翳、环跳、中府四穴，每天早晚各按摩1次，每次每穴5分钟。"}]}
     */
    //智能硬件体检
    private InfoBean info;
    private SuggestBean suggest;

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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public SuggestBean getSuggest() {
        return suggest;
    }

    public void setSuggest(SuggestBean suggest) {
        this.suggest = suggest;
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

    public static class InfoBean {
        /**
         * bmi :
         * height :
         * weight : 84.1
         * bloodoxygen : 97
         * pulse : 93
         * diastolic : 86
         * systolic : 137
         * visceralfat : 12
         * bone : 2.8
         * basalMetabolism : 1365
         * moisturerate : 62.5
         * muscle : 37.9
         * adiposerate : 27.9
         * pdf_url : http://admin.ebelter.com/pdfjs-dist/web/viewer.html?url=http://report.ebelter.com/bhcp-report-aly/healthRecord/json/getReportDetail?physicalId=BT200104153804160000000001578383914361
         */

        private String bmi;
        private String height;
        private String weight;
        private String bloodoxygen;
        private String pulse;
        private String diastolic;
        private String systolic;
        private String visceralfat;
        private String bone;
        private String basalMetabolism;
        private String moisturerate;
        private String muscle;
        private String adiposerate;
        private String pdf_url;

        public String getBmi() {
            return bmi;
        }

        public void setBmi(String bmi) {
            this.bmi = bmi;
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

        public String getBloodoxygen() {
            return bloodoxygen;
        }

        public void setBloodoxygen(String bloodoxygen) {
            this.bloodoxygen = bloodoxygen;
        }

        public String getPulse() {
            return pulse;
        }

        public void setPulse(String pulse) {
            this.pulse = pulse;
        }

        public String getDiastolic() {
            return diastolic;
        }

        public void setDiastolic(String diastolic) {
            this.diastolic = diastolic;
        }

        public String getSystolic() {
            return systolic;
        }

        public void setSystolic(String systolic) {
            this.systolic = systolic;
        }

        public String getVisceralfat() {
            return visceralfat;
        }

        public void setVisceralfat(String visceralfat) {
            this.visceralfat = visceralfat;
        }

        public String getBone() {
            return bone;
        }

        public void setBone(String bone) {
            this.bone = bone;
        }

        public String getBasalMetabolism() {
            return basalMetabolism;
        }

        public void setBasalMetabolism(String basalMetabolism) {
            this.basalMetabolism = basalMetabolism;
        }

        public String getMoisturerate() {
            return moisturerate;
        }

        public void setMoisturerate(String moisturerate) {
            this.moisturerate = moisturerate;
        }

        public String getMuscle() {
            return muscle;
        }

        public void setMuscle(String muscle) {
            this.muscle = muscle;
        }

        public String getAdiposerate() {
            return adiposerate;
        }

        public void setAdiposerate(String adiposerate) {
            this.adiposerate = adiposerate;
        }

        public String getPdf_url() {
            return pdf_url;
        }

        public void setPdf_url(String pdf_url) {
            this.pdf_url = pdf_url;
        }
    }

    public static class SuggestBean {
        private List<CommonBean> food;
        private List<CommonBean> sport;
        private List<CommonBean> common;
        private List<CommonBean> doctor;

        public List<CommonBean> getFood() {
            return food;
        }

        public void setFood(List<CommonBean> food) {
            this.food = food;
        }

        public List<CommonBean> getSport() {
            return sport;
        }

        public void setSport(List<CommonBean> sport) {
            this.sport = sport;
        }

        public List<CommonBean> getCommon() {
            return common;
        }

        public void setCommon(List<CommonBean> common) {
            this.common = common;
        }

        public List<CommonBean> getDoctor() {
            return doctor;
        }

        public void setDoctor(List<CommonBean> doctor) {
            this.doctor = doctor;
        }


        public static class CommonBean {
            /**
             * str : 气功养生，学会吐纳法：吐气的时候，不能把嘴张得太大，要无声，长气，吐完为止。来回的吐气锻炼肺活量，气体大量的平缓的与身体废气交换。
             */

            private String str;

            public String getStr() {
                return str;
            }

            public void setStr(String str) {
                this.str = str;
            }
        }


    }
}
