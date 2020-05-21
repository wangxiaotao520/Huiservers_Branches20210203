package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 分享的model
 * created by wangxiaotao
 * 2020/5/21 0021 11:15
 */
public class ModelShareParam implements Serializable {

    /**
     * title : 你好
     * isShare_exist : 1
     * share_title : 兄弟是我
     * share_desc : 你是个锤子
     * share_img : http://img.hui-shenghuo.cn/huacheng/social/20/01/17/5e211e5d30485.PNG
     * share_link : vote/vote_index/id/1?linkedme=https://lkme.cc/LQD/WmyqmueIO
     */

    private String title;
    private int isShare_exist;
    private String share_title;
    private String share_desc;
    private String share_img;
    private String share_link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsShare_exist() {
        return isShare_exist;
    }

    public void setIsShare_exist(int isShare_exist) {
        this.isShare_exist = isShare_exist;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_desc() {
        return share_desc;
    }

    public void setShare_desc(String share_desc) {
        this.share_desc = share_desc;
    }

    public String getShare_img() {
        return share_img;
    }

    public void setShare_img(String share_img) {
        this.share_img = share_img;
    }

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }
}
