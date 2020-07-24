package com.huacheng.huiservers.ui.servicenew1.inter;

import com.huacheng.huiservers.ui.servicenew.model.ModelOrderList;

/**
 * Description:
 * created by wangxiaotao
 * 2020/7/24 0024 15:41
 */
public interface OnClickServiceListItemListener {
    /**
     * 申请退款
     * @param item
     * @param position
     */
    void onClickRefundApply(ModelOrderList item, int position);

    /**
     * 完成服务
     * @param item
     * @param position
     */
    void onClickFinishService(ModelOrderList item, int position);

    /**
     * 再次购买
     * @param item
     * @param position
     */
    void onClickRebuyService(ModelOrderList item, int position);

    /**
     * 点击评价
     * @param item
     * @param position
     */
    void onClickCommet(ModelOrderList item, int position);

    /**
     * 点击删除
     * @param item
     * @param position
     */
    void onClickdelete(ModelOrderList item, int position);
}
