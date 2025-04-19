package com.uit.librarymanagementapplication.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uit.librarymanagementapplication.AppConfig;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {

    private static final String URL = AppConfig.getInstance().url;
    private static final String USER = AppConfig.getInstance().user;
    private static final String PASSWORD = AppConfig.getInstance().password;

    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    public static Connection getConnection() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            connectionHolder.set(conn);
        }
        return conn;
    }

    public static void beginTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
    }

    public static void commit() {
        try {
            Connection conn = connectionHolder.get();
            if (conn != null && !conn.getAutoCommit()) {
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback() {
        try {
            Connection conn = connectionHolder.get();
            if (conn != null && !conn.getAutoCommit()) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectionHolder.remove();
            }
        }
    }
}
