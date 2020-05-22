package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: vlog排行榜
 * created by wangxiaotao
 * 2020/1/3 0003 上午 10:36
 */
public class ModelVlogRankList implements Serializable{


    /**
     * id : 2
     * poll : 1
     * img : huacheng/social/20/01/02/5e0db3e6bd4f9.png
     * title : 111
     * number : 01
     * ranking : 1
     */

    private String id;
    private String poll;
    private String img;
    private String title;
    private String number;
    private int ranking;
    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelVlogRankList> getList() {
        return list;
    }

    public void setList(List<ModelVlogRankList> list) {
        this.list = list;
    }

    private List<ModelVlogRankList> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoll() {
        return poll;
    }

    public void setPoll(String poll) {
        this.poll = poll;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
