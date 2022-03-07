package dto;

import utils.FormatterUtils;

public class ProductDTO {

    private int productID;
    private String productName;
    private String image;
    private int quantity;
    private int price;
    private int categoryID;
    private String categoryName;

    public ProductDTO() {
    }

    public ProductDTO(int productID, String productName, String image, int quantity, int price, int categoryID, String categoryName) {
        this.productID = productID;
        this.productName = productName;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getFormattedPrice() {
        return FormatterUtils.formatPrice(price);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object obj) {
        return ((ProductDTO) obj).productID == this.productID;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.productID;
        return hash;
    }

    @Override
    public String toString() {
        return "ProductDTO{" + "productID=" + productID + ", productName=" + productName + ", image=" + image + ", quantity=" + quantity + ", price=" + price + ", categoryName=" + categoryName + '}';
    }

}
