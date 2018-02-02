package com.example.xmfy.yzubookshop.model;

import java.util.List;

/**
 * Created by xmfy on 2018/2/2.
 */
public class CartCollection {

    private String seller;

    private List<BookInfo> books;

    public CartCollection() {
    }

    public CartCollection(String seller, List<BookInfo> books) {
        this.seller = seller;
        this.books = books;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public List<BookInfo> getBooks() {
        return books;
    }

    public void setBooks(List<BookInfo> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "CartCollection{" +
                "seller='" + seller + '\'' +
                ", books=" + books +
                '}';
    }
}
