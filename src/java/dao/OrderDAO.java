package dao;

import dto.OrderDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.DBUtils;

public class OrderDAO {

    private final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());

    private static final String GET_ALL = "SELECT orderID, date, total FROM tblOrders WHERE userID = ?;";
    private static final String CREATE = "INSERT INTO tblOrders (orderID, userID, date, email, phone, address, total) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String GET = "SELECT orderID, userID FROM tblOrders WHERE orderID = ?;";

    public List<OrderDTO> getAll(String userID) throws SQLException {
        List<OrderDTO> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALL);
                ptm.setString(1, userID);
                rs = ptm.executeQuery();

                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    Timestamp date = rs.getTimestamp("date");
                    int total = rs.getInt("total");

                    orders.add(new OrderDTO(orderID, userID, date, "", "", "", total));
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

        return orders;
    }

    public OrderDTO get(String orderID) throws SQLException {
        OrderDTO order = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET);
                ptm.setString(1, orderID);
                rs = ptm.executeQuery();

                if (rs.next()) {
                    String userID = rs.getString("userID");

                    order = new OrderDTO(orderID, userID, null, "", "", "", 0);
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

        return order;
    }

    public boolean create(OrderDTO order) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setString(1, order.getOrderID());
                ptm.setString(2, order.getUserID());
                ptm.setTimestamp(3, order.getOrderDate());
                ptm.setString(4, order.getEmail());
                ptm.setString(5, order.getPhone());
                ptm.setString(6, order.getAddress());
                ptm.setInt(7, order.getTotal());

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
}
