package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description:工单Model
 * Author: badge
 * Create: 2018/12/13 19:33
 */
public class ModelWorkOrder {


    private int CountPage;
    private List<ModelWorkOrderList> list;

    public int getCountPage() {
        return CountPage;
    }

    public void setCountPage(int CountPage) {
        this.CountPage = CountPage;
    }

    public List<ModelWorkOrderList> getList() {
        return list;
    }

    public void setList(List<ModelWorkOrderList> list) {
        this.list = list;
    }

}
