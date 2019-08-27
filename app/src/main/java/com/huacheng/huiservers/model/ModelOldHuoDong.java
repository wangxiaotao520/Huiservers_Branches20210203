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
    /**
     * id : 10
     * top_img : huacheng_old/old/artice/19/08/24/5d60f85f61701.jpg
     * type : 2
     * link : http://test.hui-shenghuo.cn/home/index/campus_index
     */

    private String id;
    private String top_img;
    private String type;
    private String link;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTop_img() {
        return top_img;
    }

    public void setTop_img(String top_img) {
        this.top_img = top_img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
