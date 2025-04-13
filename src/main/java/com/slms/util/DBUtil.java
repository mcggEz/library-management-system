package com.slms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:sqlite:slms.db"; // this will create slms.db in your project folder

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
