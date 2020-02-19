package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * Description: 调查问卷详情
 * created by wangxiaotao
 * 2019/4/27 0027 下午 5:13
 */
public class ModelIvestigateCommit implements Serializable{
    private String id;
    private String form_type;
    private String form_val;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type;
    }


    public String getForm_val() {
        return form_val;
    }

    public void setForm_val(String form_val) {
        this.form_val = form_val;
    }
    @Override
    public boolean equals(Object o) {
        try {
            ModelIvestigateCommit other = (ModelIvestigateCommit) o;
            return this.id.equalsIgnoreCase(other.id);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }

}
