package com.huacheng.huiservers.ui.shop.adapter;

/**
 * Description: 购物车点击回调
 * created by wangxiaotao
 * 2019/11/16 0016 下午 5:40
 */
public interface OnClickShopCartListener {
    /**
     * 点击item
     * @param position
     */
    void onClickItem (int position);

    /**
     * 点击添加
     * @param position
     */
    void onClickAdd(int position);

    /**
     * 点击减少
     */
    void onClickReduce(int position);
}
