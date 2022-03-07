package dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import utils.FormatterUtils;

public class OrderDTO {

    private String orderID;
    private String userID;
    private Timestamp orderDate;
    private String email;
    private String phone;
    private String address;
    private int total;

    public OrderDTO() {
    }

    public OrderDTO(String orderID, String userID, Timestamp orderDate, String email, String phone, String address, int total) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDate = orderDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.total = total;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public String getFormatedOrderDate() {
        String formattedOrderDate = new SimpleDateFormat("HH:mm dd-MM-yyyy").format(orderDate);
        return formattedOrderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotal() {
        return total;
    }
    
    public String getFormattedTotal() {
        return FormatterUtils.formatPrice(total);
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
