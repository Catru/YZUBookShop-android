package com.example.xmfy.yzubookshop.model;

/**
 * Created by xmfy on 2018/1/4.
 */
public class Carousel {

    private Integer id;

    private String title;

    private String path;

    public Carousel() {
    }

    public Carousel(Integer id, String title, String path) {
        this.id = id;
        this.title = title;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Carousel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
