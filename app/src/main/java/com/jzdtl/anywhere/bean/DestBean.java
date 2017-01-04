package com.jzdtl.anywhere.bean;

/**
 * Created by gcy on 2017/1/4.
 */

public class DestBean {
    private String imgUrl;
    private String name;
    private String nameEN;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    @Override
    public String toString() {
        return "DestBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", nameEN='" + nameEN + '\'' +
                '}';
    }

    public DestBean(String imgUrl, String name, String nameEN) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.nameEN = nameEN;
    }

    public DestBean() {
    }
}
