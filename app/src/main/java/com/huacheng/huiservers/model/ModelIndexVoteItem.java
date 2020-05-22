package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description: 投票首页列表
 * created by wangxiaotao
 * 2019/9/3 0003 下午 4:51
 */
public class ModelIndexVoteItem  {
    private String family_count;

    private String vlog_count;
    private String message_count;
    private String end_time;
    private String start_time;
    private int totalPages;
    private long  current_times;//倒计时时用来的时间
    private List<ModelIndexVoteItem> list;

    public String getStartime() {
        return start_time;
    }

    public void setStartime(String startime) {
        this.start_time = startime;
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
        return end_time;
    }

    public void setDjstime(String djstime) {
        this.end_time = djstime;
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

    private String vote_id;//活动id
    private String number;
    private String title;
    private String img;
    private String content;
    private int poll;
    private String com_id;
    private String com_name;
    private String addtime;
    private String link;
    private String top_img;
    private String vote_count;
    private String color; //主色color

    private String canvassing_color;//为他拉票
    private String vote_color;//投他一票
    private String poll_color;//55票
    private String details_img;
    private String message_img;
    private String rank_img;
    public String getVote_id() {
        return vote_id;
    }

    public void setVote_id(String vote_id) {
        this.vote_id = vote_id;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public String getTop_img() {
        return top_img;
    }

    public void setTop_img(String top_img) {
        this.top_img = top_img;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

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

    public String getVlog_count() {
        return vlog_count;
    }

    public void setVlog_count(String vlog_count) {
        this.vlog_count = vlog_count;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDetails_img() {
        return details_img;
    }

    public void setDetails_img(String details_img) {
        this.details_img = details_img;
    }

    public String getMessage_img() {
        return message_img;
    }

    public void setMessage_img(String message_img) {
        this.message_img = message_img;
    }

    public String getRank_img() {
        return rank_img;
    }

    public void setRank_img(String rank_img) {
        this.rank_img = rank_img;
    }

    public String getCanvassing_color() {
        return canvassing_color;
    }

    public void setCanvassing_color(String canvassing_color) {
        this.canvassing_color = canvassing_color;
    }

    public String getVote_color() {
        return vote_color;
    }

    public void setVote_color(String vote_color) {
        this.vote_color = vote_color;
    }

    public String getPoll_color() {
        return poll_color;
    }

    public void setPoll_color(String poll_color) {
        this.poll_color = poll_color;
    }
}
