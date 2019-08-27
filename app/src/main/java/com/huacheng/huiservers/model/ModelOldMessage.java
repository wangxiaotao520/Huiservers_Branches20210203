package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 养老消息
 * created by wangxiaotao
 * 2019/8/27 0027 上午 8:27
 */
public class ModelOldMessage implements Serializable {
    private int totalPages;
    private List<ModelOldMessage> list;
    /**
     * id : 14
     * from_username : 13513541301
     * from_nickname : 天涯倦客
     * from_avatars : data/upload/center_avatars/18/01/10/5a55e1075dfca.jpg
     * content : 您请求关联对方账号
     * state_str : 等待验证
     */

    private String id;
    private String from_username;
    private String from_nickname;
    private String from_avatars;
    private String content;
    private String state_str;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelOldMessage> getList() {
        return list;
    }

    public void setList(List<ModelOldMessage> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom_username() {
        return from_username;
    }

    public void setFrom_username(String from_username) {
        this.from_username = from_username;
    }

    public String getFrom_nickname() {
        return from_nickname;
    }

    public void setFrom_nickname(String from_nickname) {
        this.from_nickname = from_nickname;
    }

    public String getFrom_avatars() {
        return from_avatars;
    }

    public void setFrom_avatars(String from_avatars) {
        this.from_avatars = from_avatars;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState_str() {
        return state_str;
    }

    public void setState_str(String state_str) {
        this.state_str = state_str;
    }
}
