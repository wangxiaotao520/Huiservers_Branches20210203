package com.huacheng.huiservers.model;

import java.util.List;

/**
 * @author Created by changyadong on 2020/11/28
 * @description
 */
public class BaseResp {

    /**
     * status : 1
     * msg : 获取数据成功！
     * data : []
     * dialog :
     */

    private int status;
    private String msg;
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

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }


}
