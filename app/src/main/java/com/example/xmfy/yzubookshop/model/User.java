package com.example.xmfy.yzubookshop.model;

/**
 * Created by xmfy on 2018/1/13.
 */
public class User {
    private String account;
    private String pwd;
    private String username;
    private String gender;
    private String phone;
    private String headshot;

    public User() {
    }

    public User(String account, String pwd, String username, String gender, String phone, String headshot) {
        this.account = account;
        this.pwd = pwd;
        this.username = username;
        this.gender = gender;
        this.phone = phone;
        this.headshot = headshot;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", headshot='" + headshot + '\'' +
                '}';
    }
}
