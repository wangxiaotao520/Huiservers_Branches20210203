package com.huacheng.huiservers.model;

import java.util.List;

/**
 * @author Created by changyadong on 2020/11/30
 * @description
 */
public class PointLog {

    /**
     * status : 1
     * msg : 获取数据成功！
     * data : {"points":"120","points_log":[{"id":"6","uid":"2399","rule_type":"sign","points":"10","operation":"1","content":"每日签到","addtime":"1606384793"},{"id":"7","uid":"2399","rule_type":"sign","points":"10","operation":"1","content":"每日签到","addtime":"1606384795"},{"id":"8","uid":"2399","rule_type":"continuitySign","points":"50","operation":"1","content":"连续签到","addtime":"1606384795"}],"total_Pages":2}
     * dialog :
     */

    private int status;
    private String msg;
    private DataBean data;
    private String dialog;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public static class DataBean {
        /**
         * points : 120
         * points_log : [{"id":"6","uid":"2399","rule_type":"sign","points":"10","operation":"1","content":"每日签到","addtime":"1606384793"},{"id":"7","uid":"2399","rule_type":"sign","points":"10","operation":"1","content":"每日签到","addtime":"1606384795"},{"id":"8","uid":"2399","rule_type":"continuitySign","points":"50","operation":"1","content":"连续签到","addtime":"1606384795"}]
         * total_Pages : 2
         */

        private String points;
        private int total_Pages;
        private List<PointsLogBean> points_log;

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public int getTotal_Pages() {
            return total_Pages;
        }

        public void setTotal_Pages(int total_Pages) {
            this.total_Pages = total_Pages;
        }

        public List<PointsLogBean> getPoints_log() {
            return points_log;
        }

        public void setPoints_log(List<PointsLogBean> points_log) {
            this.points_log = points_log;
        }

        public static class PointsLogBean {
            /**
             * id : 6
             * uid : 2399
             * rule_type : sign
             * points : 10
             * operation : 1
             * content : 每日签到
             * addtime : 1606384793
             */

            private String id;
            private String uid;
            private String rule_type;
            private String points;
            private String operation;
            private String content;
            private String addtime;

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

            public String getRule_type() {
                return rule_type;
            }

            public void setRule_type(String rule_type) {
                this.rule_type = rule_type;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public String getOperation() {
                return operation;
            }

            public void setOperation(String operation) {
                this.operation = operation;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
        }
    }
}
