package com.huacheng.huiservers.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 调查问卷
 * created by wangxiaotao
 * 2019/4/27 0027 下午 5:06
 */
public class ModelIvestigateInformation implements Serializable{

    private PlanBean plan;
    private List<ModelIvestigateInformation> data;

    private String id;
    private String form_type;
    private String form_title;
    private List<CheckBean> form_val;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type;
    }

    public String getForm_title() {
        return form_title;
    }

    public void setForm_title(String form_title) {
        this.form_title = form_title;
    }

    public List<CheckBean> getForm_val() {
        return form_val;
    }

    public void setForm_val(List<CheckBean> form_val) {
        this.form_val = form_val;
    }

    public PlanBean getPlan() {
        return plan;
    }

    public void setPlan(PlanBean plan) {
        this.plan = plan;
    }

    public static class CheckBean {

        private String id;
        private String val;

        int  selected;//标记是否被选中
        private int status=1 ;//1是正常 2是异常

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public boolean isSelected() {
            return this.selected==1?true:false;
        }

        public void setSelected(boolean selected) {
            if (selected){
                this.selected=1;
            }else {
                this.selected = 0;
            }
        }
        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        @Override
        public boolean equals(Object o) {
            try {
                CheckBean other = (CheckBean) o;
                return CheckBean.this.id.equalsIgnoreCase(other.id);
            }catch (ClassCastException e){
                e.printStackTrace();
            }
            return super.equals(o);
        }
    }
    @Override
    public boolean equals(Object o) {
        try {
            ModelIvestigateInformation other = (ModelIvestigateInformation) o;
            return this.id.equalsIgnoreCase(other.id);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }


    public static class PlanBean {
        /**
         * id : 5
         * title : 问卷调查
         * community : 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,52,53,54,55,56,57,60,61,64,98,99,100,107,108,109
         * admin_name : 华晟科技测试
         * introduce : 测试赛所所所所
         * start_time : 1582819200
         * end_time : 1582991999
         * cycle : 2
         * img : huacheng_property/property/artice/20/02/28/5e58d0614d4ce.png
         * index_img : huacheng_property/property/artice/20/02/28/5e58d0668b9a6.png
         * addtime : 1582878830
         * next_time : 1582905600
         * status : 1
         * display : 1
         * question_id : 6,7,8,9
         * info_id : 2
         */

        @SerializedName("id")
        private String idX;
        private String title;
        private String community;
        private String admin_name;
        private String introduce;
        private String start_time;
        private String end_time;
        private String cycle;
        private String img;
        private String index_img;
        private String addtime;
        private String next_time;
        private String status;
        private String display;
        private String question_id;
        private String info_id;

        public String getIdX() {
            return idX;
        }

        public void setIdX(String idX) {
            this.idX = idX;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getAdmin_name() {
            return admin_name;
        }

        public void setAdmin_name(String admin_name) {
            this.admin_name = admin_name;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getCycle() {
            return cycle;
        }

        public void setCycle(String cycle) {
            this.cycle = cycle;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getIndex_img() {
            return index_img;
        }

        public void setIndex_img(String index_img) {
            this.index_img = index_img;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getNext_time() {
            return next_time;
        }

        public void setNext_time(String next_time) {
            this.next_time = next_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public String getInfo_id() {
            return info_id;
        }

        public void setInfo_id(String info_id) {
            this.info_id = info_id;
        }
    }
    public List<ModelIvestigateInformation> getData() {
        return data;
    }

    public void setData(List<ModelIvestigateInformation> data) {
        this.data = data;
    }

}
