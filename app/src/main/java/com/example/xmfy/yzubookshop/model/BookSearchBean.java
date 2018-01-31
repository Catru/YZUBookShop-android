package com.example.xmfy.yzubookshop.model;

/**
 * Created by xmfy on 2018/1/26.
 */
public class BookSearchBean {

    private int id;

    private String account;

    private String title;

    private String author;

    private float price;

    private String keywords;

    private String description;

    private String photoUrl;

    private int category1;

    private int category2;

    private int views;

    private int collects;

    private int isCollected;

    public BookSearchBean() {
    }

    public BookSearchBean(int id, String account, String title, String author, float price, String photoUrl, int category1, int category2, int views, int collects, int isCollected) {
        this.id = id;
        this.account = account;
        this.title = title;
        this.author = author;
        this.price = price;
        this.photoUrl = photoUrl;
        this.category1 = category1;
        this.category2 = category2;
        this.views = views;
        this.collects = collects;
        this.isCollected = isCollected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getCategory1() {
        return category1;
    }

    public void setCategory1(int category1) {
        this.category1 = category1;
    }

    public int getCategory2() {
        return category2;
    }

    public void setCategory2(int category2) {
        this.category2 = category2;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getCollects() {
        return collects;
    }

    public void setCollects(int collects) {
        this.collects = collects;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }

    @Override
    public String toString() {
        return "BookSearchBean{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", keywords='" + keywords + '\'' +
                ", description='" + description + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", category1=" + category1 +
                ", category2=" + category2 +
                ", views=" + views +
                ", collects=" + collects +
                ", isCollected=" + isCollected +
                '}';
    }
}
