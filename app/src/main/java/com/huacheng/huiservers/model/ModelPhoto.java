package com.huacheng.huiservers.model;

import java.io.Serializable;

/**
 * 类描述：  选择图片model
 * 时间：2018/8/27 18:52
 * created by DFF
 */
public class ModelPhoto implements Serializable {
    String id;  //图片id

    String img;//网络路径

    String img_path;//网络路径

    String local_path;//本地路径

    String house_imgs;//本地路径


    String path;//新修改的图片路径
    String house_imgs_name; //图片名称


    int position;

    public String getHouse_imgs() {
        return house_imgs;
    }

    public void setHouse_imgs(String house_imgs) {
        this.house_imgs = house_imgs;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }
    public String getLocal_path() {
        return local_path;
    }

    public void setLocal_path(String local_path) {
        this.local_path = local_path;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHouse_imgs_name() {
        return house_imgs_name;
    }

    public void setHouse_imgs_name(String house_imgs_name) {
        this.house_imgs_name = house_imgs_name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}

