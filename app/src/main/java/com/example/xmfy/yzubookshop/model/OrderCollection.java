package dto;

import java.util.List;

/**
 * Created by xmfy on 2018/3/9.
 */
public class OrderCollection{

    private String buyer;
    private String seller;
    private String sellerName;
    private String sellerPhone;
    private String orderGroup;
    private String receiver;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String location;
    private List<OrderBook> books;

    public OrderCollection() {
    }

    public OrderCollection(String buyer, String seller, String sellerName, String sellerPhone, String orderGroup, String receiver, String phone, String province, String city, String district, String location, List<OrderBook> books) {
        this.buyer = buyer;
        this.seller = seller;
        this.sellerName = sellerName;
        this.sellerPhone = sellerPhone;
        this.orderGroup = orderGroup;
        this.receiver = receiver;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.district = district;
        this.location = location;
        this.books = books;
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
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

    public List<OrderBook> getBooks() {
        return books;
    }

    public void setBooks(List<OrderBook> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "OrderCollection{" +
                "buyer='" + buyer + '\'' +
                ", seller='" + seller + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", sellerPhone='" + sellerPhone + '\'' +
                ", orderGroup='" + orderGroup + '\'' +
                ", receiver='" + receiver + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", location='" + location + '\'' +
                ", books=" + books +
                '}';
    }
}
