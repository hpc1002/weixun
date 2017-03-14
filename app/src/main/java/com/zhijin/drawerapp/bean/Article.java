package com.zhijin.drawerapp.bean;

import java.io.Serializable;

/**
 * Created by hpc on 2017/3/8.
 */

public class Article implements Serializable{
    public Long id;
    public String title;
    public String source;
    public String firstImg;
    public String mark;
    public String url;
    public Article() {
    }
    public Article(String firstImg, String url, String source, String mark, Long id, String title) {
        this.firstImg = firstImg;
        this.url = url;
        this.source = source;
        this.mark = mark;
        this.id = id;
        this.title = title;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public Long getId() {
        return id;
    }

    public String getMark() {
        return mark;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
