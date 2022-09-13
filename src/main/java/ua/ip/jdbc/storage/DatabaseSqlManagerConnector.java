package ua.ip.jdbc.storage;

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

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void init(String host, String port, String databaseName, String user, String password) {
        url = String.format("jdbc:postgresql://%s:%s/%s", host,port, databaseName);
        properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
    }

    public String getUrl() {
        return url;
    }
    public String getUser(){
        return properties.getProperty("user");
    }
    public String getPassword(){
        return properties.getProperty("password");
    }
}
