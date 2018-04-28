package wpd2.teamR.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import wpd2.teamR.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDAOTest {

    @Before
    public void setUp() throws Exception {
    }

    @AfterClass
    public static void tearDown() throws Exception {

        ConnectionSupplier cs = new ConnectionSupplier();
        Connection connection = cs.provide();

        String query = "DELETE FROM users WHERE fname = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        //PASS ID TO PREPARED STATEMENT
        ps.setString(1, "Test");

        ps.executeUpdate();
    }

    @Test
    public void registerUser() {
        UserDAO dao = new UserDAO();
        User user = new User("Test", "Test", "Test@Test.com", "Test");
        assertTrue(dao.registerUser(user));
    }

    @Test
    public void checkIsValidUser() throws SQLException {
        UserDAO dao = new UserDAO();
        assertFalse(dao.checkIsValidUser("Test@Test.com", "Test").isEmpty());
    }

    @Test
    public void checkIsNotValidUser() throws SQLException {
        UserDAO dao = new UserDAO();
        assertTrue(dao.checkIsValidUser("JohnnyTravers", "WillyWonka").isEmpty());
    }


}