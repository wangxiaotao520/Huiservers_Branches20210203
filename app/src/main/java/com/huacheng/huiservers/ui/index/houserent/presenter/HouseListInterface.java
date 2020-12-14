package com.huacheng.huiservers.ui.index.houserent.presenter;

import com.huacheng.huiservers.model.HouseRentTagListBean;
import com.huacheng.huiservers.model.ModelMyHouseList;

import java.util.List;

/**
 * Description: 房屋列表 回调接口
 * created by wangxiaotao
 * 2018/11/8 0008 下午 3:52
 */
public interface HouseListInterface{
    /**
     * 筛选类型的回调
     * @param bean_type//0租金 //1面积 //2房型 //3排序
     * @param datas
     */
    void onGetSearchTagList(int status, String msg, int bean_type, List<HouseRentTagListBean> datas);
    /**
     * 房屋列表的回调
     *
     */
    void onGetHouseList(int status, String msg, int CountPage, List<ModelMyHouseList> datas);
}
