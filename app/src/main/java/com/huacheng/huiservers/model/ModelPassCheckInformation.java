package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 通行证information
 * created by wangxiaotao
 * 2020/2/29 0029 下午 5:11
 */
public class ModelPassCheckInformation implements Serializable {

    /**
     * pc_info : null
     * question : [{"id":"7","name":"你多大了?","type":"1","answer":[{"id":"7","name":"12","status":"1"},{"id":"8","name":"13","status":"1"}]},{"id":"8","name":"你喜欢什么呢?","type":"2","answer":[{"id":"9","name":"恐龙","status":"1"},{"id":"10","name":"布娃娃","status":"1"},{"id":"11","name":"电影","status":"1"},{"id":"12","name":"美食","status":"1"}]},{"id":"9","name":"你的家乡叫啥名字?","type":"3","answer":[]},{"id":"10","name":"你的美照","type":"4","answer":[]},{"id":"12","name":"你是男是女?","type":"1","answer":[{"id":"16","name":"男","status":"1"},{"id":"17","name":"女","status":"1"}]}]
     */

    private PicInfoBean pc_info;
    private List<QuestionBean> question;

    public PicInfoBean getPc_info() {
        return pc_info;
    }

    public void setPc_info(PicInfoBean pc_info) {
        this.pc_info = pc_info;
    }

    public List<QuestionBean> getQuestion() {
        return question;
    }

    public void setQuestion(List<QuestionBean> question) {
        this.question = question;
    }

    public static class PicInfoBean {

        /**
         * id : 3
         * owner_name : 测试23
         * phone : 18634320256
         * car_number : 的的58885
         * address : 测试小区
         * id_card : 142356456898775
         * note : 是的发生的了开奖号
         */

        private String id;
        private String owner_name;
        private String phone;
        private String car_number;
        private String address;
        private String id_card;
        private String note;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOwner_name() {
            return owner_name;
        }

        public void setOwner_name(String owner_name) {
            this.owner_name = owner_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCar_number() {
            return car_number;
        }

        public void setCar_number(String car_number) {
            this.car_number = car_number;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }
    public static class QuestionBean {
        /**
         * id : 7
         * name : 你多大了?
         * type : 1
         * answer : [{"id":"7","name":"12","status":"1"},{"id":"8","name":"13","status":"1"}]
         */

        private String id;
        private String name;
        private String type;
        private List<AnswerBean> answer;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<AnswerBean> getAnswer() {
            return answer;
        }

        public void setAnswer(List<AnswerBean> answer) {
            this.answer = answer;
        }

        public static class AnswerBean {
            private String id;
            private String name;

            int  selected;//标记是否被选中
            private int status=1 ;//1是正常 2是异常

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVal() {
                return name;
            }

            public void setVal(String val) {
                this.name = val;
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
                    AnswerBean other = (AnswerBean) o;
                    return AnswerBean.this.id.equalsIgnoreCase(other.id);
                }catch (ClassCastException e){
                    e.printStackTrace();
                }
                return super.equals(o);
            }
        }

        @Override
        public boolean equals(Object o) {
            try {
                QuestionBean other = (QuestionBean) o;
                return this.id.equalsIgnoreCase(other.id);
            }catch (ClassCastException e){
                e.printStackTrace();
            }
            return super.equals(o);
        }
    }
}
