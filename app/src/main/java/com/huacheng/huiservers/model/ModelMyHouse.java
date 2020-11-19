package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 我的房产
 * created by wangxiaotao
 * 2018/11/16 0016 下午 5:54
 */
public class ModelMyHouse implements Serializable {
    private int total_Pages;
    private int CountPage;
    private List<ModelMyHouseList> list;

    public int getTotal_Pages() {
        return total_Pages;
    }

    public void setTotal_Pages(int total_Pages) {
        this.total_Pages = total_Pages;
    }

    public int getCountPage() {
        return CountPage;
    }

    public void setCountPage(int countPage) {
        CountPage = countPage;
    }

    public List<ModelMyHouseList> getList() {
        return list;
    }

    public void setList(List<ModelMyHouseList> list) {
        this.list = list;
    }

}
