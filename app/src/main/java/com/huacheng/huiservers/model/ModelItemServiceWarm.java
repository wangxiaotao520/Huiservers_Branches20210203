package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 亲情关怀
 * created by wangxiaotao
 * 2019/8/16 0016 上午 9:04
 */
public class ModelItemServiceWarm implements Serializable {
    private int totalPages;


    private List<ModelItemServiceWarm> list;

    /**
     * id : 5
     * p_id : 1
     * p_name : 付志斌
     * i_id : 1
     * i_name : 养老
     * content : 内容
     * user_id : 1
     * user_name : 华晟养老测试
     * addtime : 1566381147
     * head_img :
     * img : [{"id":"7","c_id":"5","img":"huacheng_old/property/19/08/21/5d5d145b49e46.png","addtime":"1566381147"}]
     */

    private String id;
    private String p_id;
    private String p_name;
    private String i_id;
    private String i_name;
    private String content;
    private String user_id;
    private String user_name;
    private String addtime;
    private String head_img;
    private List<ImgBean> img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getI_id() {
        return i_id;
    }

    public void setI_id(String i_id) {
        this.i_id = i_id;
    }

    public String getI_name() {
        return i_name;
    }

    public void setI_name(String i_name) {
        this.i_name = i_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public List<ImgBean> getImg() {
        return img;
    }

    public void setImg(List<ImgBean> img) {
        this.img = img;
    }

    public static class ImgBean {
        /**
         * id : 7
         * c_id : 5
         * img : huacheng_old/property/19/08/21/5d5d145b49e46.png
         * addtime : 1566381147
         */

        private String id;
        private String c_id;
        private String img;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelItemServiceWarm> getList() {
        return list;
    }

    public void setList(List<ModelItemServiceWarm> list) {
        this.list = list;
    }

}
