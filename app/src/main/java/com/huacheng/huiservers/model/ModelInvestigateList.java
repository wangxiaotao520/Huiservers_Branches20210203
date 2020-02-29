package com.huacheng.huiservers.model;

import java.util.List;

/**
 * Description:问卷调查历史记录
 *  created by wangxiaotao
 * 2020/2/21 0021 下午 4:34
 */
public class ModelInvestigateList {


    private int totalPages;


    private List<ModelInvestigateList> list;

    /**
     * id : 1
     * plan_id : 4
     * plan_info_id : 1
     * addtime : 1582942847
     * title : 测试11
     * introduce : 测试赛
     */

    private String id;
    private String plan_id;
    private String plan_info_id;
    private String addtime;
    private String title;
    private String introduce;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getPlan_info_id() {
        return plan_info_id;
    }

    public void setPlan_info_id(String plan_info_id) {
        this.plan_info_id = plan_info_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }


    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ModelInvestigateList> getList() {
        return list;
    }

    public void setList(List<ModelInvestigateList> list) {
        this.list = list;
    }
}
