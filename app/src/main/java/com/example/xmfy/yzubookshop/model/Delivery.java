package com.example.xmfy.yzubookshop.model;

import java.io.Serializable;

/**
 * Created by xmfy on 2018/1/16.
 */
public class Delivery{

    private int id;
    private String account;
    private String receiver;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String location;
    private Integer defaults;

    public Delivery() {
    }

    public Delivery(int id, String account, String receiver, String phone, String province, String city, String district, String location, Integer defaults) {
        this.id = id;
        this.account = account;
        this.receiver = receiver;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.district = district;
        this.location = location;
        this.defaults = defaults;
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

    public Integer getDefaults() {
        return defaults;
    }

    public void setDefaults(Integer defaults) {
        this.defaults = defaults;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", receiver='" + receiver + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", location='" + location + '\'' +
                ", defaults=" + defaults +
                '}';
    }
}
