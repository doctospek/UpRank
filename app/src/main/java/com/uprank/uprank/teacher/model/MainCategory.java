package com.uprank.uprank.teacher.model;

import java.io.Serializable;

public class MainCategory implements Serializable {

    private int id;
    private String name,image_url;
    int img_id;

    public MainCategory() {
    }

    public MainCategory(int id, String name, int img_id) {
        this.id = id;
        this.name = name;
        this.img_id = img_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    @Override
    public String toString() {
        return "MainCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image_url='" + image_url + '\'' +
                ", img_id=" + img_id +
                '}';
    }
}
