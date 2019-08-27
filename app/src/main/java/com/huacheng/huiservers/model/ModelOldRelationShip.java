package com.huacheng.huiservers.model;

/**
 * Description: 关联列表
 * created by wangxiaotao
 * 2019/8/26 0026 下午 3:56
 */
public class ModelOldRelationShip {

    /**
     * id : 17
     * par_uid : 2543
     * son_uid : 3507
     * call : 都行
     * nickname : fzb
     * avatars : huacheng/center_avatars/19/06/22/5d0d8e1f793da.jpeg
     */

    private String id;
    private String par_uid;
    private String son_uid;
    private String call;
    private String nickname;
    private String avatars;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPar_uid() {
        return par_uid;
    }

    public void setPar_uid(String par_uid) {
        this.par_uid = par_uid;
    }

    public String getSon_uid() {
        return son_uid;
    }

    public void setSon_uid(String son_uid) {
        this.son_uid = son_uid;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
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
}
