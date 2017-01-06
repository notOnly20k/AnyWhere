package com.jzdtl.anywhere.bean;

import java.util.List;

/**
 * Created by gcy on 2017/1/5.
 */

public class StrategyContentBean {
    private String title;
    private String content;
    private List<String> photos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public StrategyContentBean(String title, String content, List<String> photos) {
        this.title = title;
        this.content = content;
        this.photos = photos;
    }

    public StrategyContentBean() {
    }
}
