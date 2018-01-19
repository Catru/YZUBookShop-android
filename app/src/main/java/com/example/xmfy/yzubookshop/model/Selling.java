package com.example.xmfy.yzubookshop.model;

import java.util.Date;

/**
 * Created by xmfy on 2018/1/19.
 */
public class Selling {
    private int id;
    private String account;
    private String title;
    private String author;
    private float price;
    private String keywords;
    private String photoUrl;
    private int category1;
    private int category2;
    private Date datetime;
    private int onsell;
    private String promotion;
    private int views;
    private int collects;

    public Selling() {
    }

    public Selling(int id, String account, String title, String author, float price, String keywords, String photoUrl, int category1, int category2, Date datetime, int onsell, String promotion, int views, int collects) {
        this.id = id;
        this.account = account;
        this.title = title;
        this.author = author;
        this.price = price;
        this.keywords = keywords;
        this.photoUrl = photoUrl;
        this.category1 = category1;
        this.category2 = category2;
        this.datetime = datetime;
        this.onsell = onsell;
        this.promotion = promotion;
        this.views = views;
        this.collects = collects;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getOnsell() {
        return onsell;
    }

    public void setOnsell(int onsell) {
        this.onsell = onsell;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
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

    @Override
    public String toString() {
        return "Selling{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", keywords='" + keywords + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", category1=" + category1 +
                ", category2=" + category2 +
                ", datetime=" + datetime +
                ", onsell=" + onsell +
                ", promotion='" + promotion + '\'' +
                ", views=" + views +
                ", collects=" + collects +
                '}';
    }
}
