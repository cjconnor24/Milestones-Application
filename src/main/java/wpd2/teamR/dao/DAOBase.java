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


//    public List<Object> getAllUsers() throws SQLException {
//
//        final String LIST_PERSONS = "SELECT email FROM users";
//        List<Object> results = new ArrayList<>();
//
//        try (PreparedStatement ps = connection.prepareStatement(LIST_PERSONS)) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                results.add(rs.getString(1));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return results;
//
//    }


    protected Connection getConnection() {

        return connection;

    }

//    public Connection provide() {
//        try {
//            // the driver class must be loaded
//            // so that DriverManager can find the loaded class
//            Class.forName("com.mysql.jdbc.Driver");
//
//            // CONNECTION DETAILS
//            String servername = "localhost";
//            int port = 3306;
//            String user = "root";
//            String pass = "";
//            String db = "milestones";
//            String connectionString = "jdbc:mysql://" + servername + ":" + port + "/" + db;
//
//            return DriverManager.getConnection(connectionString,user, pass);
//
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

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
