package com.example.xmfy.yzubookshop.model;

/**
 * Created by xmfy on 2018/2/3.
 */
public class Orders {
    private int id;
    private String buyer;
    private String seller;
    private String bookIds;
    private String receiver;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String location;
    private int flag;
    private String note;

    public Orders() {
    }

    public Orders(int id, String buyer, String seller, String bookIds, String receiver, String phone, String province, String city, String district, String location, int flag, String note) {
        this.id = id;
        this.buyer = buyer;
        this.seller = seller;
        this.bookIds = bookIds;
        this.receiver = receiver;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.district = district;
        this.location = location;
        this.flag = flag;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBookIds() {
        return bookIds;
    }

    public void setBookIds(String bookIds) {
        this.bookIds = bookIds;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", buyer='" + buyer + '\'' +
                ", seller='" + seller + '\'' +
                ", bookIds='" + bookIds + '\'' +
                ", receiver='" + receiver + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", location='" + location + '\'' +
                ", flag=" + flag +
                ", note='" + note + '\'' +
                '}';
    }
}
