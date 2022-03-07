package dao;

import dto.OrderDetailDTO;
import dto.ProductDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.DBUtils;

public class OrderDetailDAO {

    private final Logger LOGGER = Logger.getLogger(OrderDetailDAO.class.getName());

    private static final String CREATE = "INSERT INTO tblOrderDetails (orderID, productID, quantity, price) VALUES (?, ?, ?, ?);";
    private static final String GET_PRODUCT = "SELECT tblOrderDetails.productID, productName, tblOrderDetails.quantity, tblOrderDetails.price FROM tblOrderDetails INNER JOIN tblProducts ON tblOrderDetails.productID = tblProducts.productID WHERE orderID = ?;";

    public boolean create(OrderDetailDTO orderDetail) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setString(1, orderDetail.getOrderID());
                ptm.setInt(2, orderDetail.getProductID());
                ptm.setInt(3, orderDetail.getQuantity());
                ptm.setInt(4, orderDetail.getPrice());
                result = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "create", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public List<ProductDTO> getOrderProducts(String orderID) throws SQLException {
        List<ProductDTO> orderProducts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_PRODUCT);
                ptm.setString(1, orderID);
                rs = ptm.executeQuery();

                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("price");

                    orderProducts.add(new ProductDTO(productID, productName, null, quantity, price, 0, null));
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "getOrderProducts", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return orderProducts;
    }
}
