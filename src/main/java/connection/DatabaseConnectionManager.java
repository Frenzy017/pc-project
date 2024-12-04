package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static final String URL_TEMPLATE = "jdbc:mysql://localhost:3306/%s";
    private static final String USER = "root";
    private static final String PASSWORD = "alexbi123";

    public Connection getConnection(String databaseName) throws SQLException {
        String url = String.format(URL_TEMPLATE, databaseName);
        return DriverManager.getConnection(url, USER, PASSWORD);
    }
}