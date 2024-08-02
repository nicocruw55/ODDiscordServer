package com.odfin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {

    public static final String SELECT = " SELECT ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";

    public static final String INSERT_INTO = " INSERT INTO ";
    public static final String VALUES = " VALUES ";

    public static final String UPDATE = " UPDATE ";
    public static final String SET = " SET ";

    public static final String DELETE = " DELETE ";

    private static Connection connection = null;

    private DBHelper() { }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(getMySqlUrl(), getMySqlProperties());
        }
        return connection;
    }

    private static Properties getMySqlProperties() {
        Properties mySqlProperties = new Properties();
        mySqlProperties.put("user", "DAO");
        mySqlProperties.put("password", "muehlenhoff187");
        return mySqlProperties;
    }

    private static String getMySqlUrl() {
        String host = ServerHelper.SERVER_NAME;
        int port = 3306;
        String dbName = "ODDiscordViewMissingBackup";

        return String.format("jdbc:mysql://%s:%d/%s?allowMultiQueries=true", host, port, dbName);
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
