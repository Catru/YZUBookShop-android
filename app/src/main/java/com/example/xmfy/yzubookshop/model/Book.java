package com.example.xmfy.yzubookshop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xmfy on 2018/1/26.
 */
public class Book implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;
    @SerializedName("category1")
    @Expose
    private int category1;
    @SerializedName("category2")
    @Expose
    private int category2;
    @SerializedName("views")
    @Expose
    private int views;
    @SerializedName("collects")
    @Expose
    private int collects;

    public Book() {
    }

    public Book(int id, String account, String title, String author, float price, String photoUrl, int category1, int category2, int views, int collects) {
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
    }

    public Book(Parcel parcel){
        id = parcel.readInt();
        account = parcel.readString();
        title = parcel.readString();
        author = parcel.readString();
        price = parcel.readFloat();
        photoUrl = parcel.readString();
        category1 = parcel.readInt();
        category2 = parcel.readInt();
        views = parcel.readInt();
        collects = parcel.readInt();
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", photoUrl='" + photoUrl + '\'' +
                ", category1=" + category1 +
                ", category2=" + category2 +
                ", views=" + views +
                ", collects=" + collects +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(account);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeFloat(price);
        parcel.writeString(photoUrl);
        parcel.writeInt(category1);
        parcel.writeInt(category2);
        parcel.writeInt(views);
        parcel.writeInt(collects);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>(){

        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
