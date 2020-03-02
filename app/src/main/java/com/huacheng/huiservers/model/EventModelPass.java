package com.huacheng.huiservers.model;

/**
 * 类描述：通行证event
 * 时间：2020/3/1 19:49
 * created by DFF
 */
public class EventModelPass {
    private String status;//1不需要审核 2 需要审核

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
