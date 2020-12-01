package com.huacheng.huiservers.model;

import java.util.List;

/**
 * @author Created by changyadong on 2020/11/30
 * @description
 */
public class VipLogs {


    /**
     * status : 1
     * msg : 获取数据成功！
     * data : {"rank":"10","level":"Level0","next_level":190,"sign_rule":"每天最高获得1000点成长值，获取方式如下： \n1.订单支付金额、物业缴费金额、物业服务支付金额等1：1转化为成长值； \n2.完善个人信息、绑定房屋、发布租售房、发帖、评论、认证老人及子女身份可获得相应成长值； \n3.每日签到可连续获得成长值。 注：购买第三方服务产品及理财产品是无法获得成长值的哦~","rank_log":[{"id":"45","uid":"14661","rule_type":"sign","rule_type_cn":"","rank":"10","operation":"1","content":"每日签到","addtime":"1606821046"}],"total_Pages":1}
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
         * rank : 10
         * level : Level0
         * next_level : 190
         * sign_rule : 每天最高获得1000点成长值，获取方式如下：
         1.订单支付金额、物业缴费金额、物业服务支付金额等1：1转化为成长值；
         2.完善个人信息、绑定房屋、发布租售房、发帖、评论、认证老人及子女身份可获得相应成长值；
         3.每日签到可连续获得成长值。 注：购买第三方服务产品及理财产品是无法获得成长值的哦~
         * rank_log : [{"id":"45","uid":"14661","rule_type":"sign","rule_type_cn":"","rank":"10","operation":"1","content":"每日签到","addtime":"1606821046"}]
         * total_Pages : 1
         */

        private String rank;
        private String level;
        private int next_level;
        private String sign_rule;
        private int total_Pages;
        private List<RankLogBean> rank_log;

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getNext_level() {
            return next_level;
        }

        public void setNext_level(int next_level) {
            this.next_level = next_level;
        }

        public String getSign_rule() {
            return sign_rule;
        }

        public void setSign_rule(String sign_rule) {
            this.sign_rule = sign_rule;
        }

        public int getTotal_Pages() {
            return total_Pages;
        }

        public void setTotal_Pages(int total_Pages) {
            this.total_Pages = total_Pages;
        }

        public List<RankLogBean> getRank_log() {
            return rank_log;
        }

        public void setRank_log(List<RankLogBean> rank_log) {
            this.rank_log = rank_log;
        }

        public static class RankLogBean {
            /**
             * id : 45
             * uid : 14661
             * rule_type : sign
             * rule_type_cn :
             * rank : 10
             * operation : 1
             * content : 每日签到
             * addtime : 1606821046
             */

            private String id;
            private String uid;
            private String rule_type;
            private String rule_type_cn;
            private String rank;
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

            public String getRule_type_cn() {
                return rule_type_cn;
            }

            public void setRule_type_cn(String rule_type_cn) {
                this.rule_type_cn = rule_type_cn;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
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
