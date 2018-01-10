package com.example.xmfy.yzubookshop.model;

/**
 * Created by xmfy on 2018/1/9.
 */
public class News {

    private int id;

    private String title;

    private String description;

    private String iconAddress;

    private String webUrl;

    public News() {
    }

    public News(int id, String title, String description, String iconAddress, String webUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.iconAddress = iconAddress;
        this.webUrl = webUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconAddress() {
        return iconAddress;
    }

    public void setIconAddress(String iconAddress) {
        this.iconAddress = iconAddress;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", iconAddress='" + iconAddress + '\'' +
                ", webUrl='" + webUrl + '\'' +
                '}';
    }
}
