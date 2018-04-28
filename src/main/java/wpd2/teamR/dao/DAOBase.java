package wpd2.teamR.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DAOBase implements AutoCloseable {

    protected Connection connection;


    public DAOBase() {
        ConnectionSupplier cs = new ConnectionSupplier();
        this.connection = cs.provide();
    }

    public synchronized void close() throws SQLException {

        if (connection != null) {
            connection.close();
            connection = null;
        }

    }


    protected Connection getConnection() {

        return connection;

    }


    protected static void execute(Connection connection, String cmd) {
        try {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                statement.execute(cmd);
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected void errIfClosed() {
        if (getConnection() == null) {
            throw new NullPointerException("MySQL Connection connection is closed");
        }
    }

}
