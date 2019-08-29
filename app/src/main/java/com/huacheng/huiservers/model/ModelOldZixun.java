package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 养老资讯
 * created by wangxiaotao
 * 2019/8/28 0028 下午 9:09
 */
public class ModelOldZixun implements Serializable{

    /**
     * id : 805
     * title : 美国特种部队的第一梯队不是海豹特种部队，那是哪支部队呢？??
     * content : <p>
     <span class="bjh-p">&nbsp;&nbsp;&nbsp;&nbsp;众所周知，有了强大的军事实力才可以让国家不被欺负。就好比俄罗斯，虽然苏联解体后俄罗斯的经济大不如之前，但是作为前苏联的“嫡子”，俄罗斯不但继承了先进的科学技术，还拥有了当今世界最大的核武器库，而且俄罗斯的军人都骁勇善战，威猛高大，所以有这么强大的军事实力，也就没有哪个国家敢欺负俄罗斯，由此可见，强大的军事实力对每个国家来说都是非常重要的。1?</span>
     </p>
     <div class="img-container">
     <img src="http://img.hui-shenghuo.cn/huacheng_property/editor/image/20190825/20190825192656_99024.png" alt="" /><br />
     </div>
     <p>
     <span class="bjh-p">说到这里就不得不提一下当今的世界霸主美国，我们都知道，美国的军事力量位于世界第一，而他们众多兵种中，最强大的要数特种兵了。</span>
     </p>
     * from : 百度新闻贴吧?
     * o_company_name : 华晟养老测试
     * click : 0
     * addtime : 2019-08-25 19:27
     */

    private String id;
    private String title;
    private String content;
    private String from;
    private String o_company_name;
    private String click;
    private String addtime;


    private int evevt_type = 1; //1是阅读量 2.是收藏量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getO_company_name() {
        return o_company_name;
    }

    public void setO_company_name(String o_company_name) {
        this.o_company_name = o_company_name;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }


    public int getEvevt_type() {
        return evevt_type;
    }

    public void setEvevt_type(int evevt_type) {
        this.evevt_type = evevt_type;
    }
}
