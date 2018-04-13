package com.ef;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public final class SqlConnect implements Closeable {

    public Connection conn;
    private Statement statement;
    public static SqlConnect db;

    private SqlConnect() {
        try {
            this.conn = DriverManager.getConnection(Constants.mySqlUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized SqlConnect getDbConnection() {
        if (db == null) {
            db = new SqlConnect();
        }
        return db;

    }

    public void createTable() throws SQLException {
        System.out.println("Creating PARSED_LOGS table if it does not exist");
        statement = db.conn.createStatement();
        statement.executeUpdate(Constants.createLogsTableQuery);
    }

    public ResultSet query(String query) throws SQLException {
        statement = db.conn.createStatement();
        return statement.executeQuery(query);
    }

    public ResultSet query(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeQuery();
    }

    public int insert(PreparedStatement preparedStatement) throws SQLException {
        return preparedStatement.executeUpdate();
    }

    @Override
    public void close() throws IOException {
        if (conn == null) {
            System.out.println("No connection found");
        } else {
            try {
                conn.close();
                conn = null;
                if (statement != null) {
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
