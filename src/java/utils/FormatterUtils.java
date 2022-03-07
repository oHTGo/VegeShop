package utils;

public class FormatterUtils {

    public static String formatPrice(int price) {
        String tempPrice = Integer.toString(price);
        String formatedPrice = "";
        int priceLength = tempPrice.length();
        for (int i = 0; i < priceLength; i++) {
            if (i % 3 == 0 && i != 0) {
                formatedPrice = "." + formatedPrice;
            }
            formatedPrice = tempPrice.charAt(priceLength - i - 1) + formatedPrice;
        }

        return formatedPrice;
    }
}
