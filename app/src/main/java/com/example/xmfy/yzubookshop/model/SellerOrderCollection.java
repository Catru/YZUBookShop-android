package dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by xmfy on 2018/3/9.
 */
public class SellerOrderCollection {

    private String seller;
    private String buyer;
    private String buyerName;
    private String buyerPhone;
    private String orderGroup;
    private String receiver;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String location;
    private Timestamp updateTime;
    private List<OrderBook> books;

    public SellerOrderCollection() {
    }

    public SellerOrderCollection(String seller, String buyer, String buyerName, String buyerPhone, String orderGroup, String receiver, String phone, String province, String city, String district, String location, Timestamp updateTime, List<OrderBook> books) {
        this.seller = seller;
        this.buyer = buyer;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.orderGroup = orderGroup;
        this.receiver = receiver;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.district = district;
        this.location = location;
        this.updateTime = updateTime;
        this.books = books;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getOrderGroup() {
        return orderGroup;
    }

    public void setOrderGroup(String orderGroup) {
        this.orderGroup = orderGroup;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderBook> getBooks() {
        return books;
    }

    public void setBooks(List<OrderBook> books) {
        this.books = books;
    }
}
