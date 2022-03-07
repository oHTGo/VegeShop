package dto;

import utils.FormatterUtils;

public class OrderDetailDTO {

    private String orderID;
    private int productID;
    private int quantity;
    private int price;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String orderID, int productID, int quantity, int price) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public String getFormattedPrice() {
        return FormatterUtils.formatPrice(price);
    }

}
