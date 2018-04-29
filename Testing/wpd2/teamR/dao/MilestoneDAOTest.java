package wpd2.teamR.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import wpd2.teamR.models.Milestone;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

public class MilestoneDAOTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createMilestone() throws SQLException {

        MilestoneDAO milestones = new MilestoneDAO();
        Milestone insetM = new Milestone("Test", "Test", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), 6);
        assertTrue(milestones.createMilestone(insetM, 6));
    }


    @Test
    public void getMilestonesById() throws SQLException {
        MilestoneDAO milestones = new MilestoneDAO();
        Milestone milestone = milestones.getMilestonesById(4);
        assertFalse(milestone == null);

    }

    @Test
    public void getMilestoneByIdandUser() {
        MilestoneDAO milestones = new MilestoneDAO();
        Milestone milestone = milestones.getMilestoneByIdandUser(4, "chris@chrisconnor.co.uk");
        assertFalse(milestone == null);
    }

    @Test
    public void getAllMilestonesByProjectAndUser() throws SQLException {
        MilestoneDAO milestones = new MilestoneDAO();
        List<Milestone> milestone = milestones.getAllMilestonesByProjectAndUser(2, "chris@chrisconnor.co.uk");
        assertFalse(milestone == null);
    }


    @Test
    public void getAllMilestonesByProjectId() throws SQLException {
        MilestoneDAO milestones = new MilestoneDAO();
        List<Milestone> milestone = milestones.getAllMilestonesByProjectId(2);
        assertFalse(milestone.isEmpty());
    }
}



