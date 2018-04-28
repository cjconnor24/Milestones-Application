package wpd2.teamR.dao;

import org.junit.*;
import wpd2.teamR.models.Link;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class LinkDAOTest {

    @BeforeClass
    public static void setUp() throws Exception {

        LinkDAO testingDAO = new LinkDAO();
        Link link = new Link("testclass@hotmail.com");
        assertTrue(testingDAO.save(link, 6));
    }

    @AfterClass
    public static void tearDown() throws Exception {
        LinkDAO testingDAO = new LinkDAO();
        testingDAO.deleteByEmail("testclass@hotmail.com");
        testingDAO.deleteByEmail("testclass2@hotmail.com");
    }

    @Test
    public void save() throws SQLException {

        LinkDAO testingDAO = new LinkDAO();
        Link link = new Link("testclass2@hotmail.com");
        assertTrue(testingDAO.save(link, 6));
    }

    @Test
    public void findById() throws SQLException {

        LinkDAO testingDAO = new LinkDAO();
        Link link = testingDAO.findById(44);
        assertTrue(link != null);

    }

    @Test
    public void findByProjectId() throws SQLException {
        LinkDAO testingDAO = new LinkDAO();
        List<Link> links = testingDAO.findByProjectId(6);
        assertFalse(links.isEmpty());
    }

    @Test
    public void findByUserId() throws SQLException {
        LinkDAO testingDAO = new LinkDAO();
        List<Link> links = testingDAO.findByUserId(4);
        assertEquals(links.size(), 2);
    }

    @Test
    public void findAll() throws SQLException {
        LinkDAO testingDAO = new LinkDAO();
        List<Link> links = testingDAO.findAll();
        assertFalse(links.isEmpty());
    }


    @Test
    public void deleteByEmail() {
        LinkDAO testingDAO = new LinkDAO();

        assertTrue(testingDAO.deleteByEmail("testclass2@hotmail.com"));
    }
}