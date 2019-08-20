package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * 类描述：充电桩
 * 时间：2019/8/20 11:27
 * created by DFF
 */
public class ModelChargeDetail implements Serializable{
    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private boolean isSelect;
}
