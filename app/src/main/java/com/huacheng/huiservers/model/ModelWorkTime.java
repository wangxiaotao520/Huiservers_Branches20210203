package com.huacheng.huiservers.model;

/**
 * Description:工单选择时间
 * created by wangxiaotao
 * 2019/4/8 0008 下午 7:19
 */
public class ModelWorkTime {

    String week;
    String date;
    long timemills;//上方的时间搓

    String time;
    boolean is_selected;
    public ModelWorkTime() {

    }
    public ModelWorkTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }


    public long getTimemills() {
        return timemills;
    }

    public void setTimemills(long timemills) {
        this.timemills = timemills;
    }

}
