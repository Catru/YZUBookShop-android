package dto;

import java.sql.Date;

/**
 * Created by xmfy on 2018/3/9.
 */
public class OrderBook {
    private int id;
    private String title;
    private String author;
    private float price;
    private int category1;
    private int category2;
    private String photoUrl;
    private Date updateTime;

    public OrderBook() {
    }

    public OrderBook(int id, String title, String author, float price, int category1, int category2, String photoUrl, Date updateTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.category1 = category1;
        this.category2 = category2;
        this.photoUrl = photoUrl;
        this.updateTime = updateTime;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "OrderBook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", category1=" + category1 +
                ", category2=" + category2 +
                ", photoUrl='" + photoUrl + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
