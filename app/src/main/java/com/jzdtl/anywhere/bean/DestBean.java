package com.jzdtl.anywhere.bean;

/**
 * Created by gcy on 2017/1/4.
 */

public class DestBean {
    private String city;
    private String imgUrl;
    private String name;
    private String nameEN;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

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
                "city='" + city + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", nameEN='" + nameEN + '\'' +
                '}';
    }

    public DestBean(String city, String imgUrl, String name, String nameEN) {
        this.city = city;
        this.imgUrl = imgUrl;
        this.name = name;
        this.nameEN = nameEN;
    }

    public DestBean() {

    }
}
