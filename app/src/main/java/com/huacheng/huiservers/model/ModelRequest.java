package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 投诉建议
 * created by wangxiaotao
 * 2019/5/8 0008 上午 9:39
 */
public class ModelRequest implements Serializable{
    private int totalPages;
    private List<ModelRequest> list;


    private String id;
    private String content;
    private String addtime;
    private String complete_time;
    private int status;
    private String nickname;
    private String phone;
    private String complaint_number;
    private String address;
    private String reply_content;
    private String img;
    private List<ModelRequest> img_list ;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getComplaint_number() {
        return complaint_number;
    }

    public void setComplaint_number(String complaint_number) {
        this.complaint_number = complaint_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public List<ModelRequest> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<ModelRequest> img_list) {
        this.img_list = img_list;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }


    public List<ModelRequest> getList() {
        return list;
    }

    public void setList(List<ModelRequest> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getComplete_time() {
        return complete_time;
    }

    public void setComplete_time(String complete_time) {
        this.complete_time = complete_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
