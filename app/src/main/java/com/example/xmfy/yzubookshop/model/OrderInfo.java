package dto;

/**
 * Created by xmfy on 2018/3/9.
 */
public class OrderInfo {
    private String buyer;
    private String seller;
    private int bookId;
    private String orderGroup;
    private String receiver;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String location;

    public OrderInfo() {
    }

    public OrderInfo(String buyer, String seller, int bookId, String orderGroup, String receiver, String phone, String province, String city, String district, String location) {
        this.buyer = buyer;
        this.seller = seller;
        this.bookId = bookId;
        this.orderGroup = orderGroup;
        this.receiver = receiver;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.district = district;
        this.location = location;
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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    @Override
    public String toString() {
        return "OrderInfo{" +
                "buyer='" + buyer + '\'' +
                ", seller='" + seller + '\'' +
                ", bookId=" + bookId +
                ", orderGroup='" + orderGroup + '\'' +
                ", receiver='" + receiver + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
