package wpd2.teamR.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSupplier {


    // CONNECTION DETAILS
    private static final String servername = "localhost";
    private static final int port = 3306;
    private static final String user = "root";
    private static final String pass = "";
    private static final String db = "milestones";
    private static final String connectionString = "jdbc:mysql://" + servername + ":" + port + "/" + db;


    public ConnectionSupplier() {

    }

    public Connection provide() {
        try {

            // LOAD THE MYSQL DRIVER
            Class.forName("com.mysql.jdbc.Driver");

            return DriverManager.getConnection(connectionString, user, pass);

        } catch (SQLException | ClassNotFoundException e) {
            throw new ConnectionSupplierException(e);
        }
    }

    public class ConnectionSupplierException extends RuntimeException {
        ConnectionSupplierException(Exception e) {
            super(e);
        }
    }
}

