package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static final String URL_TEMPLATE = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public Connection getConnection(String databaseName) throws SQLException {

        if (URL_TEMPLATE == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException("Database environment variables are not set");
        }
        String url = String.format(URL_TEMPLATE, databaseName);
        return DriverManager.getConnection(url, USER, PASSWORD);
    }
}