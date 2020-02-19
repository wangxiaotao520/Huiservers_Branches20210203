package com.huacheng.huiservers.model;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 调查问卷
 * created by wangxiaotao
 * 2019/4/27 0027 下午 5:06
 */
public class ModelIvestigateInformation implements Serializable{


    private String id;
    private String form_type;
    private String form_title;
    private List<CheckBean> form_val;




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

    public String getForm_title() {
        return form_title;
    }

    public void setForm_title(String form_title) {
        this.form_title = form_title;
    }

    public List<CheckBean> getForm_val() {
        return form_val;
    }

    public void setForm_val(List<CheckBean> form_val) {
        this.form_val = form_val;
    }

    public static class CheckBean {

        private String id;
        private String val;

        int  selected;//标记是否被选中
        private int status=1 ;//1是正常 2是异常

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public boolean isSelected() {
            return this.selected==1?true:false;
        }

        public void setSelected(boolean selected) {
            if (selected){
                this.selected=1;
            }else {
                this.selected = 0;
            }
        }
        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        @Override
        public boolean equals(Object o) {
            try {
                CheckBean other = (CheckBean) o;
                return CheckBean.this.id.equalsIgnoreCase(other.id);
            }catch (ClassCastException e){
                e.printStackTrace();
            }
            return super.equals(o);
        }
    }
    @Override
    public boolean equals(Object o) {
        try {
            ModelIvestigateInformation other = (ModelIvestigateInformation) o;
            return this.id.equalsIgnoreCase(other.id);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }


}
