package com.huacheng.huiservers.model;

import com.huacheng.huiservers.ui.shop.bean.BannerBean;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：圈子邻里
 * 时间：2019/12/18 10:35
 * created by DFF
 */
public class ModelCircle implements Serializable{
    /**
     * id : 160
     * uid : 14
     * admin_id : 1
     * c_id : 82
     * c_name : 本地头条
     * community_id : 72
     * title : 5pS25Yiw5Zue5aSN5ZWK
     * content : 5pS25Yiw5Zue5aSN5ZWK6Z2e5Yeh5ZOlMTEx
     * click : 17
     * reply_num : 1
     * is_hot : 1
     * addtime : 08-14
     * community_name :
     * nickname : 吴燕妮
     * avatars :
     * img_list : [{"id":"171","img":"huacheng_property/property/artice/19/08/14/5d53ee360755d.jpg","img_size":"1.6201298701299"}]
     * total_Pages : 6
     */

    private String id;
    private String uid;
    private String admin_id;
    private String c_id;
    private String c_name;
    private String community_id;
    private String title;
    private String content;
    private String click;
    private String reply_num;
    private String is_hot;
    private String addtime;
    private String community_name;
    private String nickname;
    private String avatars;
    private int total_Pages;
    private List<BannerBean> img_list;
    private String img;
    private String is_pro;
    private String img_size;

    public String getIs_pro() {
        return is_pro;
    }

    public void setIs_pro(String is_pro) {
        this.is_pro = is_pro;
    }

    public List<BannerBean> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<BannerBean> img_list) {
        this.img_list = img_list;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg_size() {
        return img_size;
    }

    public void setImg_size(String img_size) {
        this.img_size = img_size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getReply_num() {
        return reply_num;
    }

    public void setReply_num(String reply_num) {
        this.reply_num = reply_num;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatars() {
        return avatars;
    }

    public void setAvatars(String avatars) {
        this.avatars = avatars;
    }

    public int getTotal_Pages() {
        return total_Pages;
    }

    public void setTotal_Pages(int total_Pages) {
        this.total_Pages = total_Pages;
    }

}
