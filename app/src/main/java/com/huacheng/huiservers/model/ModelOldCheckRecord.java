package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description:体检记录列表
 * created by wangxiaotao
 * 2019/8/28 0028 下午 7:04
 */
public class ModelOldCheckRecord {
    private int totalPages;
    /**
     * id : 1
     * type : 1
     * describe : 12121212
     * checktime : 123123
     */

    private String id;
    private String type;
    private String describe;
    private String checktime;


    private int list_type ;//0是正常//1是造的空数据


    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelOldCheckRecord> getList() {
        return list;
    }

    public void setList(List<ModelOldCheckRecord> list) {
        this.list = list;
    }

    private List<ModelOldCheckRecord> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getChecktime() {
        return checktime;
    }

    public void setChecktime(String checktime) {
        this.checktime = checktime;
    }

    public int getList_type() {
        return list_type;
    }

    public void setList_type(int list_type) {
        this.list_type = list_type;
    }
}
