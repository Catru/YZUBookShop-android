package com.example.xmfy.yzubookshop.model;

/**
 * Created by xmfy on 2018/2/2.
 */
public class BookInfo{
    private int id;
    private int bookId;
    private String urls;
    private String title;
    private String author;
    private int category1;
    private int category2;
    private float price;

    public BookInfo() {
    }

    public BookInfo(int id, int bookId, String urls, String title, String author, int category1, int category2, float price) {
        this.id = id;
        this.bookId = bookId;
        this.urls = urls;
        this.title = title;
        this.author = author;
        this.category1 = category1;
        this.category2 = category2;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", urls='" + urls + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category1=" + category1 +
                ", category2=" + category2 +
                ", price=" + price +
                '}';
    }
}
