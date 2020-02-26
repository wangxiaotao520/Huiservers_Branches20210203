package com.huacheng.huiservers.model;

/**
 * Description:启动页
 * *created by wangxiaotao
 * 2020/2/25 0025 下午 4:22
 */
public class ModelSplashImg {


    /**
     * guide_img_display : 0
     * guide_time_display : 1
     * guide_android_img : huacheng/guide/20/02/25/5e54c628f27b5.png
     * guide_img_ios_max : huacheng/guide/20/02/25/5e54c652bfbc3.png
     * guide_img_ios_x : huacheng/guide/20/02/25/5e54c675bc28c.png
     * guide_img_ios_min : huacheng/guide/20/02/25/5e54c652be8b0.png
     */

    private String guide_img_display;
    private String guide_time_display;
    private String guide_android_img;
    private String guide_img_ios_max;
    private String guide_img_ios_x;
    private String guide_img_ios_min;


    private String guide_url_type_id;//启动页导航链接类型(跟banner一致)
    private String guide_type_name; //启动页内部跳转链接(跟banner一致)

    public String getGuide_img_display() {
        return guide_img_display;
    }

    public void setGuide_img_display(String guide_img_display) {
        this.guide_img_display = guide_img_display;
    }

    public String getGuide_time_display() {
        return guide_time_display;
    }

    public void setGuide_time_display(String guide_time_display) {
        this.guide_time_display = guide_time_display;
    }

    public String getGuide_android_img() {
        return guide_android_img;
    }

    public void setGuide_android_img(String guide_android_img) {
        this.guide_android_img = guide_android_img;
    }

    public String getGuide_img_ios_max() {
        return guide_img_ios_max;
    }

    public void setGuide_img_ios_max(String guide_img_ios_max) {
        this.guide_img_ios_max = guide_img_ios_max;
    }

    public String getGuide_img_ios_x() {
        return guide_img_ios_x;
    }

    public void setGuide_img_ios_x(String guide_img_ios_x) {
        this.guide_img_ios_x = guide_img_ios_x;
    }

    public String getGuide_img_ios_min() {
        return guide_img_ios_min;
    }

    public void setGuide_img_ios_min(String guide_img_ios_min) {
        this.guide_img_ios_min = guide_img_ios_min;
    }

    public String getGuide_url_type_id() {
        return guide_url_type_id;
    }

    public void setGuide_url_type_id(String guide_url_type_id) {
        this.guide_url_type_id = guide_url_type_id;
    }

    public String getGuide_type_name() {
        return guide_type_name;
    }

    public void setGuide_type_name(String guide_type_name) {
        this.guide_type_name = guide_type_name;
    }

}
