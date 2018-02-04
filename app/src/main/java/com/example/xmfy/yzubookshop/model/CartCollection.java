package com.example.xmfy.yzubookshop.model;

import java.util.List;

/**
 * Created by xmfy on 2018/2/2.
 */
public class CartCollection {

    private String sellerId;

    private String seller;

    private List<BookInfo> books;

    public CartCollection() {
    }

    public CartCollection(String sellerId, String seller, List<BookInfo> books) {
        this.sellerId = sellerId;
        this.seller = seller;
        this.books = books;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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
                "sellerId='" + sellerId + '\'' +
                ", seller='" + seller + '\'' +
                ", books=" + books +
                '}';
    }
}
