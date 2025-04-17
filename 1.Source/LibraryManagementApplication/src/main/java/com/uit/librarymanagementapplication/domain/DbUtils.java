package com.uit.librarymanagementapplication.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uit.librarymanagementapplication.domain.model.DbConfiguration;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbUtils {

    private static DbConfiguration dbConfig;
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

 
    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = DbUtils.class.getClassLoader().getResourceAsStream("applicationSetting.json");
            if (is == null) {
                System.out.println("⚠️ applicationSetting.json not found");
            }
            dbConfig = mapper.readValue(is, DbConfiguration.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = connectionHolder.get();
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(dbConfig.url, dbConfig.user, dbConfig.password);
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
