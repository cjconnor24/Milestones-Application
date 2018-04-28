package wpd2.teamR.models;

import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class ProjectTest {

    @Test
    public void getPrettyDateCreated() {
        Project project = new Project(1, "test", "test", new Timestamp(946684799), new Timestamp(946684799));
        String evaluate = project.getPrettyDateCreated();
        assertEquals(evaluate, "5 decades ago");
    }

    @Test
    public void getPrettyDateModified() {
        Project project = new Project(1, "test", "test", new Timestamp(946684799), new Timestamp(946684799));
        String evaluate = project.getPrettyDateModified();
        assertEquals(evaluate, "5 decades ago");
    }
}