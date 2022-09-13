package ua.ip.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseSqlManagerConnector {
    private String url;
    private Properties properties;

    public DatabaseSqlManagerConnector(String host, String port, String databaseName, String user, String password) {
        init(host, port, databaseName, user, password);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, properties);
    }

    private void init(String host, String port, String databaseName, String user, String password) {
        url = String.format("jdbc:postgresql://%s:%s/%s", host,port, databaseName);
        properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
    }
}
