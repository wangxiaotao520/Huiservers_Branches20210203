package com.huacheng.huiservers.model;

/**
 * Description: 筛选条件的bean
 * created by wangxiaotao
 * 2018/11/6 0006 下午 7:45
 */
public class HouseRentTagListBean {

    private  int bean_type;//0租金 //1面积 //2房型 //3排序

    private boolean isSelected; //是否被选中
    private String id;
    private String price;//价格区间
    private String size;//面积
    private String status;//排序方式
    private String type;//房型列表

    public int getBean_type() {
        return bean_type;
    }

    public void setBean_type(int bean_type) {
        this.bean_type = bean_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
