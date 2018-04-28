package wpd2.teamR.dao;

import org.junit.*;
import wpd2.teamR.models.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ProjectDAOTest {

    @BeforeClass
    public static void setUp() throws Exception {
        ProjectDAO testingDAO = new ProjectDAO();
        Project testingProject = testingDAO.getProjectById(2);
        testingProject.setName("TempTest");
        testingProject.setDescription("TempTest");

        assertTrue(testingDAO.projectUpdate(testingProject));
    }

    @AfterClass
    public static void tearDown() throws Exception {

        ConnectionSupplier cs = new ConnectionSupplier();
        Connection connection = cs.provide();

        String query = "DELETE FROM projects WHERE name = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        //PASS ID TO PREPARED STATEMENT
        ps.setString(1, "Test");

        ps.executeUpdate();
    }

    @Test
    public void createProject() {
        ProjectDAO testingDAO = new ProjectDAO();
        Project project = new Project("Test","Auto Test");

        assertTrue(testingDAO.createProject(project,"chris@chrisconnor.co.uk"));
    }

    @Test
    public void getProjectById() throws SQLException {

        ProjectDAO testingDAO = new ProjectDAO();
        assertTrue(testingDAO.getProjectById(2) != null);

    }

    @Test
    public void deleteProjectById() {
        ProjectDAO testingDAO = new ProjectDAO();
        assertFalse(testingDAO.deleteProjectById(2000));
    }



    @Test
    public void getAllProjects() throws SQLException {
        ProjectDAO testingDAO = new ProjectDAO();
        assertNotNull(testingDAO.getAllProjects());
    }

    @Test
    public void getProjectsbyUser() throws SQLException {
        ProjectDAO testingDAO = new ProjectDAO();
        assertFalse(testingDAO.getProjectsbyUser("g.macleod@domain.com").isEmpty());
    }


    @Test
    public void getProjectByIdAndUser() throws SQLException {

        ProjectDAO testingDAO = new ProjectDAO();
        assertTrue(testingDAO.getProjectByIdAndUser(2,"chris@chrisconnor.co.uk") != null);
    }

    @Test
    public void projectUpdate() throws SQLException {
        ProjectDAO testingDAO = new ProjectDAO();
        Project testingProject = testingDAO.getProjectById(2);
        testingProject.setName("Web Platform Development");
        testingProject.setDescription("This is a brilliant project...");

        assertTrue(testingDAO.projectUpdate(testingProject));

    }
}