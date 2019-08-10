package com.huacheng.huiservers.pay.chinaums;

import com.huacheng.huiservers.ui.center.geren.bean.PayTypeBean;

import java.util.List;

/**
 * Description: 回调接口
 * created by wangxiaotao
 * 2019/7/9 0009 上午 11:25
 */
public interface OnUnifyPayListener {
    void onGetPayTypeDatas(int status, String msg , List<PayTypeBean> mDatas);
    void onGetOrderInformation(int status, String msg ,String json,int typetag);
    void onGetOrderResult(int status, String msg ,String json,String type_params);
}
