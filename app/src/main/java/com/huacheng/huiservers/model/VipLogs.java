package com.huacheng.huiservers.model;

import java.util.List;

/**
 * @author Created by changyadong on 2020/11/30
 * @description
 */
public class VipLogs {



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
         * rank : 0
         * level : Level0
         * level_next : 200
         * sign_rule : 每天最高获得1000点成长值，获取方式如下：
         1.订单支付金额、物业缴费金额、物业服务支付金额等1：1转化为成长值；
         2.完善个人信息、绑定房屋、发布租售房、发帖、评论、认证老人及子女身份可获得相应成长值；
         3.每日签到可连续获得成长值。 注：购买第三方服务产品及理财产品是无法获得成长值的哦~
         * rank_log : []
         * total_Pages : 0
         */

        private String rank;
        private String level;
        private int level_next;
        private String sign_rule;
        private int total_Pages;
        private List<?> rank_log;

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

        public int getLevel_next() {
            return level_next;
        }

        public void setLevel_next(int level_next) {
            this.level_next = level_next;
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

        public List<?> getRank_log() {
            return rank_log;
        }

        public void setRank_log(List<?> rank_log) {
            this.rank_log = rank_log;
        }
    }
}
