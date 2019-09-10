package com.huacheng.huiservers.model;

/**
 * 类描述：活动投票EventBus
 * 时间：2019/9/7 11:48
 * created by DFF
 */
public class ModelVoteEvent {
    private int type; //type 0  活动留言刷新  1为详情投票返回首页刷新
    private int ispoll;//当亲票数
    private int ispiao;//剩余投票次数
    private String id;//家庭id

    public int getIspiao() {
        return ispiao;
    }

    public void setIspiao(int ispiao) {
        this.ispiao = ispiao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIspoll() {
        return ispoll;
    }

    public void setIspoll(int ispoll) {
        this.ispoll = ispoll;
    }
}
