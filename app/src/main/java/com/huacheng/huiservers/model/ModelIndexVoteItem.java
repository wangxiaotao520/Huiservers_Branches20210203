package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description: 投票首页列表
 * created by wangxiaotao
 * 2019/9/3 0003 下午 4:51
 */
public class ModelIndexVoteItem  {
    private String family_count;
    private String message_count;
    private String djstime;
    private String startime;
    private int totalPages;
    private long  current_times;//倒计时时用来的时间
    private List<ModelIndexVoteItem> list;

    public String getStartime() {
        return startime;
    }

    public void setStartime(String startime) {
        this.startime = startime;
    }

    public long getCurrent_times() {
        return current_times;
    }

    public void setCurrent_times(long current_times) {
        this.current_times = current_times;
    }

    public String getFamily_count() {
        return family_count;
    }

    public void setFamily_count(String family_count) {
        this.family_count = family_count;
    }

    public String getMessage_count() {
        return message_count;
    }

    public void setMessage_count(String message_count) {
        this.message_count = message_count;
    }


    public String getDjstime() {
        return djstime;
    }

    public void setDjstime(String djstime) {
        this.djstime = djstime;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelIndexVoteItem> getList() {
        return list;
    }

    public void setList(List<ModelIndexVoteItem> list) {
        this.list = list;
    }
    /**
     * id : 4
     * number : 001
     * title : 123123
     * img : huacheng/social/19/09/05/5d70edf54a7df.jpg
     * content : 123123
     * poll : 0
     * com_id : 64
     * com_name : 回迁小区
     * addtime : 0
     */

    private String id;
    private String number;
    private String title;
    private String img;
    private String content;
    private int poll;
    private String com_id;
    private String com_name;
    private String addtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPoll() {
        return poll;
    }

    public void setPoll(int poll) {
        this.poll = poll;
    }

    public String getCom_id() {
        return com_id;
    }

    public void setCom_id(String com_id) {
        this.com_id = com_id;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

}
