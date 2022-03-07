package dao;

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

public class ProductDAO {

    private final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());

    private static final String GET_ALL = "SELECT productID, productName, image, quantity, price, tblProducts.categoryID, categoryName FROM tblProducts INNER JOIN tblCategories ON tblProducts.categoryID = tblCategories.categoryID WHERE tblProducts.isActive = 1;";
    private static final String CREATE = "INSERT INTO tblProducts (productID, productName, image, quantity, price, categoryID) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String GET = "SELECT productID, productName, image, quantity, price, tblProducts.categoryID, categoryName FROM tblProducts INNER JOIN tblCategories ON tblProducts.categoryID = tblCategories.categoryID WHERE tblProducts.productID = ? AND tblProducts.isActive = 1;";
    private static final String UPDATE = "UPDATE tblProducts SET productName = ?, image = ?, quantity = ?, price = ?, categoryID = ? WHERE productID = ?;";
    private static final String REMOVE = "UPDATE tblProducts SET isActive = 0 WHERE productID = ?;";

    public List<ProductDTO> getAll() throws SQLException {
        List<ProductDTO> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALL);
                rs = ptm.executeQuery();

                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("price");
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");

                    products.add(new ProductDTO(productID, productName, image, quantity, price, categoryID, categoryName));
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "getAll", e);
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

        return products;
    }

    public boolean create(ProductDTO product) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setInt(1, product.getProductID());
                ptm.setString(2, product.getProductName());
                ptm.setString(3, product.getImage());
                ptm.setInt(4, product.getQuantity());
                ptm.setInt(5, product.getPrice());
                ptm.setInt(6, product.getCategoryID());

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

    public ProductDTO get(int productID) throws SQLException {
        ProductDTO product = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET);
                ptm.setInt(1, productID);
                rs = ptm.executeQuery();

                if (rs.next()) {
                    String productName = rs.getString("productName");
                    String image = rs.getString("image");
                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("price");
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");

                    product = new ProductDTO(productID, productName, image, quantity, price, categoryID, categoryName);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "get", e);
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

        return product;
    }

    public boolean update(ProductDTO product) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, product.getProductName());
                ptm.setString(2, product.getImage());
                ptm.setInt(3, product.getQuantity());
                ptm.setInt(4, product.getPrice());
                ptm.setInt(5, product.getCategoryID());
                ptm.setInt(6, product.getProductID());

                result = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "update", e);
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

    public boolean remove(int productID) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(REMOVE);
                ptm.setInt(1, productID);

                result = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "remove", e);
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
}
