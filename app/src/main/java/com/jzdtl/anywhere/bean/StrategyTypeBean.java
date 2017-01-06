package com.jzdtl.anywhere.bean;

import java.util.List;

/**
 * Created by gcy on 2017/1/5.
 */

public class StrategyTypeBean {
    private int res;
    private String nameCN;
    private String nameEN;
    private List<String> title;

    public StrategyTypeBean(int res, String nameCN, String nameEN, List<String> title) {
        this.res = res;
        this.nameCN = nameCN;
        this.nameEN = nameEN;
        this.title = title;
    }

    public StrategyTypeBean() {
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getNameCN() {
        return nameCN;
    }

    public void setNameCN(String nameCN) {
        this.nameCN = nameCN;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }
}
