package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * 类描述：老人档案
 * 时间：2019/8/17 10:24
 * created by DFF
 */
public class ModelOldFile implements Serializable{
    private boolean iscancel;
    public boolean isIscancel() {
        return iscancel;
    }

    public void setIscancel(boolean iscancel) {
        this.iscancel = iscancel;
    }


}
