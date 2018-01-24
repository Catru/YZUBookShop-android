package com.example.xmfy.yzubookshop.model;

/**
 * Created by xmfy on 2018/1/22.
 */
public class SellingDto {
    private int id;
    private String account;
    private String title;
    private String author;
    private float price;
    private String keywords;
    private int category1;
    private int category2;
    private String Description;
    private String photoUrl;

    public SellingDto() {
    }

    public SellingDto(int id, String account, String title, String author, float price, String keywords, int category1, int category2, String description, String photoUrl) {
        this.id = id;
        this.account = account;
        this.title = title;
        this.author = author;
        this.price = price;
        this.keywords = keywords;
        this.category1 = category1;
        this.category2 = category2;
        Description = description;
        this.photoUrl = photoUrl;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "SellingDto{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", keywords='" + keywords + '\'' +
                ", category1=" + category1 +
                ", category2=" + category2 +
                ", Description='" + Description + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
