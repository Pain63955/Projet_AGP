package persistence.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {

    private static String url = DatabaseConfig.getProperty("db.url");
    private static String user = DatabaseConfig.getProperty("db.user");
    private static String password = DatabaseConfig.getProperty("db.password");

    public static Connection connection;

    public static Connection getConnection() { 
        if (connection == null) {
            try {
                //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                System.err.println("Connection failed : " + e.getMessage());
            }
        }
        return connection;
    }
}
