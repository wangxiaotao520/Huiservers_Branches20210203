package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description: 资讯
 * created by wangxiaotao
 * 2019/8/26 0026 上午 11:29
 */
public class ModelArticle {

    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelArticle> getList() {
        return list;
    }

    public void setList(List<ModelArticle> list) {
        this.list = list;
    }

    private List<ModelArticle> list;
}
