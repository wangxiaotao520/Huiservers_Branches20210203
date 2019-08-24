package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description:
 * created by wangxiaotao
 * 2019/8/23 0023 上午 10:12
 */
public class ModelChargeGrid implements Serializable {
    private boolean select;


    private int status ;//充电桩通道的状态  0-未使用，1-使用中




    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
