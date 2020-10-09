package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description: 资讯
 * created by wangxiaotao
 * 2019/8/26 0026 上午 11:29
 */
public class ModelArticle {

    private int totalPages;
    private List<ModelArticle> list;
    /**
     * id : 805
     * title : 美国特种部队的第一梯队不是海豹特种部队，那是哪支部队呢？??
     * click : 0
     * addtime : 2019.08.25 19:27
     * img : huacheng_old/old/artice/19/08/25/5d627106ce654.png
     */

    private String id;
    private String title;
    private String click;
    private String addtime;
    private String img;
    /**
     * top_img :
     * is_link : 1
     * link :
     * browse : 5
     */

    private String top_img;
    private String is_link;
    private String link;
    private String browse;


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




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTop_img() {
        return top_img;
    }

    public void setTop_img(String top_img) {
        this.top_img = top_img;
    }

    public String getIs_link() {
        return is_link;
    }

    public void setIs_link(String is_link) {
        this.is_link = is_link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }
}
