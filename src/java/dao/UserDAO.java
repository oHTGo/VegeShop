package dao;

import dto.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.DBUtils;

public class UserDAO {

    private final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    private static final String LOGIN = "SELECT userID, email, fullName, role, phone, address FROM tblUsers WHERE userID = ? AND password = ?";
    private static final String LOGIN_BY_EMAIL = "SELECT userID, email, fullName, role, phone, address FROM tblUsers WHERE email = ?";
    private static final String CREATE = "INSERT INTO tblUsers (userID, password, email, fullName, role, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public UserDTO checkLogin(String userID, String password) throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN);
                ptm.setString(1, userID);
                ptm.setString(2, password);

                rs = ptm.executeQuery();

                if (rs.next()) {
                    String email = rs.getString("email");
                    String fullName = rs.getString("fullName");
                    int role = rs.getInt("role");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");

                    user = new UserDTO(userID, "*", email, fullName, phone, address, role);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "checkLogin", e);
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

        return user;
    }

    public UserDTO checkLoginByEmail(String email) throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN_BY_EMAIL);
                ptm.setString(1, email);

                rs = ptm.executeQuery();

                if (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    int role = rs.getInt("role");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");

                    user = new UserDTO(userID, "*", email, fullName, phone, address, role);

                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "checkLoginByEmail", e);
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

        return user;
    }
    
    public boolean create(UserDTO user) throws SQLException {
        boolean error = true;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setString(1, user.getUserID());
                ptm.setString(2, user.getPassword());
                ptm.setString(3, user.getEmail());
                ptm.setString(4, user.getFullName());
                ptm.setInt(5, user.getRole());
                ptm.setString(6, user.getPhone());
                ptm.setString(7, user.getAddress());

                if (ptm.executeUpdate() == 1) {
                    error = false;
                }
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
        
        return !error;
    }
}