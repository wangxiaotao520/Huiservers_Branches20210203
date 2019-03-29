package com.huacheng.huiservers.ui.shop.bean;

import com.huacheng.huiservers.ui.fragment.bean.ModelShopIndex;

import java.util.List;

/**
 * Description: 限时抢购 列表
 * created by wangxiaotao
 * 2018/11/1 000 下午 5:03
 */
public class ModelSeckill {
    List <ModelShopIndex>   list;
    List <CateBean>     class_name;

    public List<ModelShopIndex> getList() {
        return list;
    }

    public void setList(List<ModelShopIndex> list) {
        this.list = list;
    }

    public List<CateBean> getClass_name() {
        return class_name;
    }

    public void setClass_name(List<CateBean> class_name) {
        this.class_name = class_name;
    }



}
