package com.huacheng.huiservers.model;

/**
 * Description:EventBus 工单Model
 * Author: badge
 * Create: 2018/12/17 18:59
 */
public class EventBusWorkOrderModel {

    private int event_type=1;//0是取消订单 1是预付 2.是付款

    private String wo_id;

    public String getWo_id() {
        return wo_id;
    }

    public void setWo_id(String wo_id) {
        this.wo_id = wo_id;
    }

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }


}
