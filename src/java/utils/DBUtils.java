package utils;

import configurations.EnvConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String url = EnvConfig.getDB_URL();
        conn = DriverManager.getConnection(url, EnvConfig.getDB_USERNAME(), EnvConfig.getDB_PASSWORD());

        return conn;
    }
}
