package dao;

import dto.CategoryDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import utils.DBUtils;

public class CategoryDAO {

    private final Logger LOGGER = Logger.getLogger(CategoryDAO.class.getName());

    private static final String GET_ALL = "SELECT categoryID, categoryName FROM tblCategories;";
    private static final String CREATE = "INSERT INTO tblCategories (categoryID, categoryName) VALUES (?, ?);";
    private static final String UPDATE = "UPDATE tblCategories SET categoryName = ? WHERE categoryID = ?;";

    public List<CategoryDTO> getAll() throws SQLException {
        List<CategoryDTO> categories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALL);
                rs = ptm.executeQuery();

                while (rs.next()) {
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getString("categoryName");

                    categories.add(new CategoryDTO(categoryID, categoryName));
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

        return categories;
    }

    public boolean create(int categoryID, String categoryName) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setInt(1, categoryID);
                ptm.setString(2, categoryName);

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

    public boolean update(int categoryID, String categoryName) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, categoryName);
                ptm.setInt(2, categoryID);

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
}
