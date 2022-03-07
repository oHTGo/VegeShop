package dto;

import java.util.HashMap;
import java.util.Map;
import utils.FormatterUtils;

public class CartDTO {

    private HashMap<ProductDTO, Integer> products = new HashMap<>();

    public CartDTO() {
    }

    public HashMap<ProductDTO, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<ProductDTO, Integer> products) {
        this.products = products;
    }

    public int getTotal() {
        int total = 0;
        for (Map.Entry<ProductDTO, Integer> p : products.entrySet()) {
            ProductDTO product = p.getKey();
            Integer quantity = p.getValue();

            total += product.getPrice() * quantity;
        }

        return total;
    }
    
    public String getFormattedTotal() {
        return FormatterUtils.formatPrice(this.getTotal());
    }

}
