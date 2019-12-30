package cn.edu.ldu.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static final String DBURL="jdbc:derby://localhost:1527/QQDB";
    private static final String USERNAME="nbuser";
    private static final String PASSWORD="123456";
    public static Connection getConnection() throws SQLException {
      return DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
    }
}