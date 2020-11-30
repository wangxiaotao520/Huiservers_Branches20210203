package com.huacheng.huiservers.model;

import java.util.List;

/**
 * @author Created by changyadong on 2020/11/28
 * @description
 */
public class UnsetReason {

    /**
     * status : 1
     * msg : 获取数据成功！
     * data : [{"title":"账号内有未完成状态的订单","content":"订单编号：22547"}]
     * dialog :
     */

    private int status;
    private String msg;
    private String dialog;
    private List<DataBean> data;

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

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 账号内有未完成状态的订单
         * content : 订单编号：22547
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
