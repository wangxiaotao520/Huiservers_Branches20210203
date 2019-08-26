package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description: 活动
 * created by wangxiaotao
 * 2019/8/26 0026 上午 11:33
 */
public class ModelOldHuoDong {

    private int totalPages;
    private List<ModelOldHuoDong> list;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelOldHuoDong> getList() {
        return list;
    }

    public void setList(List<ModelOldHuoDong> list) {
        this.list = list;
    }


}
